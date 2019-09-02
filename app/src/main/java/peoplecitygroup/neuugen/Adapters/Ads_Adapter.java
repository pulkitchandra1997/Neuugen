package peoplecitygroup.neuugen.Adapters;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.ViewInterestedAds;
import peoplecitygroup.neuugen.common_req_files.AD;
import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.properties.ManageYourAds;
import peoplecitygroup.neuugen.properties.PropertyDetails;
import peoplecitygroup.neuugen.properties.PropertyList;

import static android.content.Context.MODE_PRIVATE;
import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class Ads_Adapter extends RecyclerView.Adapter<Ads_Adapter.MyViewHolder> {
     ArrayList<AD> adList;
     Activity activity;
     int flag;
    ProgressDialog loading = null;
    ManageYourAds manageYourAds; PropertyList propertyList; ViewInterestedAds viewInterestedAds;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= activity.getLayoutInflater().inflate(R.layout.manage_ad_cardview,null);

        Log.d("checkview","yes");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final AD ad = adList.get(position);
        String temp="";
        switch (Integer.parseInt(ad.getPropertytype().trim())){
            case 0:temp="Apartment";break;
            case 1:temp="Independent House";break;
            case 2:temp="Villa";break;
            case 3:temp="Hostel";break;
            case 4:temp="Office Area";break;
            case 5:temp="Shop Area";break;
            case 6:temp="Plot";break;
        }
        holder.propertytype.setText(temp);
        holder.cost.setText(ad.getPrice());
        holder.area.setText(ad.getArea().trim()+", "+ad.getCity().trim());
        Log.d("verifiedcheck",ad.getPrice()+ad.getVerified());
        if(ad.getVerified().trim().equalsIgnoreCase("1")) {
            holder.verified.setVisibility(View.VISIBLE);
            holder.notverified.setVisibility(View.GONE);
        }
        else{ holder.notverified.setVisibility(View.VISIBLE);holder.verified.setVisibility(View.GONE);
        }
        if(flag==1||flag==2)
            if(ad.getAvailable().trim().equalsIgnoreCase("1")) {
                holder.available.setVisibility(View.VISIBLE);holder.notavailable.setVisibility(View.GONE);
            }
            else {holder.notavailable.setVisibility(View.VISIBLE);holder.available.setVisibility(View.GONE);}
        if(ad.getPic1()!=null||ad.getPic1().trim()!="")
            Picasso.with(activity).load(ad.getPic1())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.image1);
        /*if(ad.getPic2()!=null||ad.getPic2().trim()!="")
            Picasso.with(activity).load(ad.getPic2())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.image2);
        if(ad.getPic3()!=null||ad.getPic3().trim()!="")
            Picasso.with(activity).load(ad.getPic3())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.image3);*/
        if(flag==0){
            holder.deletebtn.setText("Cancel Interest");
            holder.deletebtn.setTextSize(10);
        }
        if(flag==2){
            holder.deletebtn.setVisibility(View.GONE);
            holder.viewbtn.setVisibility(View.GONE);
        }
        holder.viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VIEWBTN
                viewAd(ad);
            }
        });
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DELETEBTN
                actionClick(holder.deletebtn,ad,position);
            }
        });
        if(flag==2)
            holder.cardView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    viewAd(ad);
                }
            });
    }

    private void actionClick(MaterialButton deletebtn, final AD ad, final int position) {
        String message="Are you sure you want to delete this Ad?";
        if(flag==1)
            message="Are you sure you want to cancel the interest?";
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
        builder.setMessage(message)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(flag==0)
                            deleteAd(ad,position);
                        if(flag==1)
                            cancelInterest(ad,position);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.mipmap.ic_launcher_round);
        AlertDialog dialog = builder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        Button negativeButton = dialog.getButton(BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FF12B2FA"));

    }

    private void cancelInterest(final AD ad, final int position) {
        loading.show();
        SharedPreferences sp;
        sp=activity.getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        final String number=sp.getString("mobileno", null);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.cancelInterestedAd, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.i("checkerror",response);
                loading.dismiss();
                if(response.toLowerCase().contains("error")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity.getApplication());

                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage(response.trim())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                }
                else{
                    if(response.trim().equalsIgnoreCase("success")){
                        adList.remove(position);
                        viewInterestedAds.showListView(adList);
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
                ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
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
                params.put("number",number);
                params.put("uniqueid",ad.getUniqueid());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(activity.getApplication());

                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Connection")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

            }
        });
        MySingleton.getInstance(activity).addToRequestQueue(stringRequest);
    }

    private void deleteAd(final AD ad, final int position) {
        loading.show();
        SharedPreferences sp;
        sp=activity.getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        final String number=sp.getString("mobileno", null);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.deleteAd, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.i("checkerror",response);
                loading.dismiss();
                if(response.toLowerCase().contains("error")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity.getApplication());

                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage(response.trim())
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                }
                else{
                    if(response.trim().equalsIgnoreCase("success")){
                        adList.remove(position);
                        manageYourAds.showListView(adList);
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
                ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
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
                params.put("uniqueid",ad.getUniqueid());
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
                AlertDialog.Builder builder = new AlertDialog.Builder(activity.getApplication());

                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Connection")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

            }
        });
        MySingleton.getInstance(activity).addToRequestQueue(stringRequest);
    }

    private void viewAd(AD ad) {
        Intent intent = new Intent(activity, PropertyDetails.class);
        intent.putExtra("flag","object");
        intent.putExtra("object", ad);
        if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.fade_in, R.anim.fade_out);
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return adList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView propertytype,cost,area,rupeeicon;
        public LinearLayout verified,notverified,available,notavailable;
        public ImageView image1;
        public MaterialButton viewbtn,deletebtn;
        public CardView cardView;
        public MyViewHolder(View view) {
            super(view);

            loading = new ProgressDialog(activity,R.style.AppCompatAlertDialogStyle);
            loading.setCancelable(false);
            loading.setMessage("Loading...");
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            propertytype = (TextView) view.findViewById(R.id.properttype);
            cost = (TextView) view.findViewById(R.id.cost);
            area = (TextView) view.findViewById(R.id.area);
            verified = (LinearLayout) view.findViewById(R.id.verified);
            notverified = (LinearLayout) view.findViewById(R.id.notverified);
            available = (LinearLayout) view.findViewById(R.id.available);
            notavailable = (LinearLayout) view.findViewById(R.id.notavailable);
            image1 = (ImageView) view.findViewById(R.id.image1);
            //image2 = (ImageView) view.findViewById(R.id.image2);
            //image3 = (ImageView) view.findViewById(R.id.image3);
            viewbtn=(MaterialButton) view.findViewById(R.id.viewbtn);
            deletebtn=(MaterialButton)view.findViewById(R.id.deletebtn);
            rupeeicon=(TextView)view.findViewById(R.id.rupeeicon);
            cardView=(CardView)view.findViewById(R.id.customcard);
            Typeface font = Typeface.createFromAsset(activity.getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
            rupeeicon.setTypeface(font);

        }
    }
    public Ads_Adapter(Activity activity, ArrayList<AD> adList, String flag, ManageYourAds manageYourAds, PropertyList propertyList, ViewInterestedAds viewInterestedAds){
        //this.mContext=mContext;
        this.adList=adList;
        this.manageYourAds=manageYourAds;
        this.propertyList=propertyList;
        this.viewInterestedAds=viewInterestedAds;
        this.activity=activity;
        if(flag.trim().equalsIgnoreCase("ViewInterestedAds"))
            this.flag=0;
        else
            if (flag.trim().equalsIgnoreCase("ManageYourAds"))
                this.flag=1;
            else
                if (flag.trim().equalsIgnoreCase("PropertyList"))
                    this.flag=2;
    }

}
