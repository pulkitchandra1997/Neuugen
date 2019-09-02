package peoplecitygroup.neuugen;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.Adapters.Bookings_Adapter;
import peoplecitygroup.neuugen.common_req_files.Bookings;
import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;

import static android.content.Context.MODE_PRIVATE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class BookingsFrag extends Fragment implements View.OnClickListener {


    ProgressDialog loading = null;
    ScrollView bookinglist;
    RecyclerView currentlist,previouslist;
    CardView adscard,noItems;
    Bookings_Adapter cbookings_adapter,pbookings_adapter;
    ArrayList<Bookings> cbookingsArrayList;
    ArrayList<Bookings> pbookingsArrayList;
    String number;
    int flag=0;
    TabLayout tabLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bookingsfrag, container, false);
        loading = new ProgressDialog(getActivity(),R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setCanceledOnTouchOutside(false);
        loading.setMessage("Fetching data...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        idLink(v);
        listenerLink();

        SharedPreferences sp;
        sp=getActivity().getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        number=sp.getString("mobileno", null);
        cbookingsArrayList=new ArrayList<Bookings>();
        pbookingsArrayList=new ArrayList<Bookings>();
        cbookings_adapter=new Bookings_Adapter(getActivity(),cbookingsArrayList,pbookingsArrayList,BookingsFrag.this);
        pbookings_adapter=new Bookings_Adapter(getActivity(),pbookingsArrayList,cbookingsArrayList,BookingsFrag.this);
        currentlist.setAdapter(cbookings_adapter);
        previouslist.setAdapter(pbookings_adapter);
        currentlist.setLayoutManager(new LinearLayoutManager(getActivity()));
        previouslist.setLayoutManager(new LinearLayoutManager(getActivity()));
        currentlist.setVisibility(View.GONE);
        previouslist.setVisibility(View.GONE);
        noItems.setVisibility(View.GONE);
        requestData();
        return v;
    }
    public void idLink(View v)
    {
        bookinglist=v.findViewById(R.id.bookinglist);
        currentlist=v.findViewById(R.id.currentlist);
        previouslist=v.findViewById(R.id.previouslist);
        adscard=v.findViewById(R.id.adscard);
        tabLayout=v.findViewById(R.id.tablayout);
        noItems=v.findViewById(R.id.noitems);
    }

    public void listenerLink()
    {
        adscard.setOnClickListener(this);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switchtab(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void switchtab(int position) {
        noItems.setVisibility(View.GONE);
        if(position==0){
            if(flag!=0){
                previouslist.setVisibility(View.GONE);
                if(cbookingsArrayList.size()>0)
                    currentlist.setVisibility(View.VISIBLE);
                else
                    noItems.setVisibility(View.VISIBLE);
                flag=0;
            }
        }
        if(position==1){
            if(flag!=1){
                currentlist.setVisibility(View.GONE);
                if(pbookingsArrayList.size()>0)
                    previouslist.setVisibility(View.VISIBLE);
                else
                    noItems.setVisibility(View.VISIBLE);
                flag=1;
            }
        }
    }

    private void requestData() {
        loading.show();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.viewBookings, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if (response.toLowerCase().contains("error")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Error in server. Try Again")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().getFragmentManager().popBackStack();
                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                } else {
                    if (response.toLowerCase().contains("nobookings")) {
                        noItems.setVisibility(View.VISIBLE);
                        bookinglist.setVisibility(View.GONE);
                    }
                    else{
                        bookinglist.setVisibility(View.VISIBLE);
                        //ADAPTER
                        showResult(response);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                loading.dismiss();
                boolean haveConnectedWifi = false;
                boolean haveConnectedMobile = false;
                ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            haveConnectedWifi = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            haveConnectedMobile = true;
                }
                if( !haveConnectedWifi && !haveConnectedMobile)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                    alertDialog.setMessage("Connection Error!");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("number", number);
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Connection")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().getFragmentManager().popBackStack();
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

            }
        });
        MySingleton.getInstance(getActivity()).addToRequestQueue(stringRequest);
    }
    private void showResult(String response) {
        try {
            Log.d("json",response);
            JSONArray results=new JSONArray(response);
            for(int i=0;i<results.length();i++) {
                JSONObject result = results.getJSONObject(i);
                if(result.getString("status").trim().equalsIgnoreCase("3")||result.getString("status").trim().equalsIgnoreCase("2")){
                    pbookingsArrayList.add(new Bookings(result.getString("requestid"),number,result.getString("serviceid"),result.getString("houseno"),result.getString("area"),result.getString("city"),result.getString("city_id"),result.getString("landmark"),result.getString("pincode"),result.getString("dateofservice"),result.getString("dateofrequest"),result.getString("status"),result.getString("completiondate"),result.getString("remark"),result.getString("servicetype"),result.getString("noofdays"),result.getString("eventtype")));
                }
                else{
                    cbookingsArrayList.add(new Bookings(result.getString("requestid"),number,result.getString("serviceid"),result.getString("houseno"),result.getString("area"),result.getString("city"),result.getString("city_id"),result.getString("landmark"),result.getString("pincode"),result.getString("dateofservice"),result.getString("dateofrequest"),result.getString("status"),result.getString("completiondate"),result.getString("remark"),result.getString("servicetype"),result.getString("noofdays"),result.getString("eventtype")));
                }
            }

            showListView(pbookingsArrayList,cbookingsArrayList);
        }catch(Exception e){
            Log.d("jsonerror",e.toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error in Server.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
    }

    public void showListView(ArrayList<Bookings> pbookingsArray, ArrayList<Bookings> cbookingsArray) {
        this.pbookingsArrayList=pbookingsArray;
        this.cbookingsArrayList=cbookingsArray;
        bookinglist.setVisibility(View.VISIBLE);
        noItems.setVisibility(View.GONE);
        cbookings_adapter.notifyDataSetChanged();
        pbookings_adapter.notifyDataSetChanged();
        if(flag==0) {
            currentlist.setVisibility(View.VISIBLE);
            if(cbookingsArrayList.size()==0){
                currentlist.setVisibility(View.GONE);
                noItems.setVisibility(View.VISIBLE);
            }
        }
        else {
            previouslist.setVisibility(View.VISIBLE);
            if(pbookingsArrayList.size()==0){
                currentlist.setVisibility(View.GONE);
                noItems.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.adscard){
            Intent intent = new Intent(getActivity(), ViewInterestedAds.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }


}
