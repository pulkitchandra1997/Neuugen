package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.common_req_files.AD;
import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.Adapters.Ads_Adapter;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class ViewInterestedAds extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog loading = null;
    String number="";
    LinearLayout noadsfoundlayout;
    ScrollView resultlist;
    RecyclerView adslistview;
    Ads_Adapter ads_adapter;
    ArrayList<AD>adsArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_interested_ads);
        loading = new ProgressDialog(ViewInterestedAds.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Acquiring Result...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        adsArrayList=new ArrayList<AD>();
        link();
        listener();
        ads_adapter =new Ads_Adapter(this,adsArrayList,"ViewInterestedAds",null,null,ViewInterestedAds.this);
        adslistview.setAdapter(ads_adapter);
        adslistview.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        number=sp.getString("mobileno", null);
        sendData(0,2,2,true);
    }

    private void listener() {
    }

    private void showResult(int position) {

    }

    private void link() {
        noadsfoundlayout=findViewById(R.id.noadsfoundlayoutia);
        adslistview=findViewById(R.id.managelistviewia);
        resultlist=findViewById(R.id.resultlistia);
    }

    private void sendData(final int resultShown, final int verified, final int available,boolean fresh) {
        loading.show();
        if(fresh){
            adsArrayList.clear();
            ads_adapter.notifyDataSetChanged();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.viewInterestedAds, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if (response.toLowerCase().contains("error")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ViewInterestedAds.this).create();
                    alertDialog.setMessage(response);
                    alertDialog.setButton(BUTTON_POSITIVE,"Ok", (DialogInterface.OnClickListener) null);
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                    Button positiveButton = alertDialog.getButton(BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                } else {
                    if(response.trim().contains("noadsposted")){
                        noadsfoundlayout.setVisibility(View.VISIBLE);
                        resultlist.setVisibility(View.GONE);
                    }
                    else
                        showAds(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                boolean haveConnectedWifi = false;
                boolean haveConnectedMobile = false;
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            haveConnectedWifi = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            haveConnectedMobile = true;
                }
                if (!haveConnectedWifi && !haveConnectedMobile) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ViewInterestedAds.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ViewInterestedAds.this).create();
                    alertDialog.setMessage("Connection Error!");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("number", number);
                params.put("resultshown", String.valueOf(resultShown));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ViewInterestedAds.this);
                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Connection")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
            }
        });
        MySingleton.getInstance(ViewInterestedAds.this).addToRequestQueue(stringRequest);
    }

    private void showAds(String response) {
        try {
            Log.d("json",response);
            JSONArray results=new JSONArray(response);
            for(int i=0;i<results.length();i++) {
                JSONObject result = results.getJSONObject(i);
                adsArrayList.add(new AD(result.getString("uniqueid"), result.getString("mobileno"), result.getString("adtype"), result.getString("houseno"), result.getString("area"), result.getString("city"), result.getString("city_id"), result.getString("landmark"), result.getString("pincode"), result.getString("propertytype"), result.getString("bedrooms"), result.getString("bathrooms"), result.getString("furnishtype"), result.getString("builtuparea"), result.getString("price"), result.getString("constructionstatus"), result.getString("ageofproperty"), result.getString("possessionstatus"), result.getString("length"), result.getString("width"), result.getString("widthoffacingroad"), result.getString("pic1"), result.getString("pic2"), result.getString("pic3"), result.getString("verified"), result.getString("available"), result.getString("created"),result.getString("status")));
            }

            showListView(adsArrayList);

        }catch(Exception e){
            Log.d("jsonerror",e.toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewInterestedAds.this);
            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error in Server.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
    }

    public void showListView(ArrayList<AD> adsArray) {
        adsArrayList=adsArray;
        resultlist.setVisibility(View.VISIBLE);
        noadsfoundlayout.setVisibility(View.GONE);
        if(adsArrayList.size()==0){
            resultlist.setVisibility(View.GONE);
            noadsfoundlayout.setVisibility(View.VISIBLE);
        }
        ads_adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        Log.d("clickcheck",String.valueOf(v.getId()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
