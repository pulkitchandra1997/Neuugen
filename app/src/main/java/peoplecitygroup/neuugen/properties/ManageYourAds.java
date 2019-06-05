package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.AD;
import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class ManageYourAds extends AppCompatActivity implements View.OnClickListener {
    ProgressDialog loading = null;
    String number="";
    LinearLayout noadsfoundlayout,filterlayoutmanage;
    ScrollView resultlist;
    MaterialButton postad;
    RecyclerView adslistview;
    ManageYourAds_Adapter manageYourAds_adapter;
    ArrayList<AD>adsArrayList;
    LinearLayout filtersmanage;
    MaterialButton cancel,apply,showall;
    SwitchCompat verified,available;
    boolean filterflag=false,verifiedflag=false,availableflag=false,filterverified,filteravailable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_your_ads);
        loading = new ProgressDialog(ManageYourAds.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Acquiring Result...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        adsArrayList=new ArrayList<AD>();
        link();
        listener();
        manageYourAds_adapter=new ManageYourAds_Adapter(this,adsArrayList);
        adslistview.setAdapter(manageYourAds_adapter);
        adslistview.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        number=sp.getString("mobileno", null);
        sendData(0,2,2,true);
    }

    private void listener() {
        postad.setOnClickListener(this);
        filtersmanage.setOnClickListener(this);
        cancel.setOnClickListener(this);
        apply.setOnClickListener(this);
        showall.setOnClickListener(this);
        verified.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    filterverified=true;
                else
                    filterverified=false;
            }
        });
        available.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    filteravailable=true;
                else
                    filteravailable=false;
            }
        });
        /*adslistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showResult(position);
            }
        });*/
    }

    private void showResult(int position) {
    }

    private void link() {
        noadsfoundlayout=findViewById(R.id.noadsfoundlayout);
        postad=findViewById(R.id.postad);
        adslistview=findViewById(R.id.managelistview);
        resultlist=findViewById(R.id.resultlist);
        filtersmanage=findViewById(R.id.filtersmanage);
        filterlayoutmanage=findViewById(R.id.filterlayoutmanage);
        cancel=findViewById(R.id.cancelbtnm);
        apply=findViewById(R.id.applybtnm);
        showall=findViewById(R.id.showallbtnm);
        verified=findViewById(R.id.verifiedtoggle);
        available=findViewById(R.id.availabletoggle);
    }

    private void sendData(final int resultShown, final int verified, final int available,boolean fresh) {
        loading.show();
        if(fresh){
            adsArrayList.clear();
            manageYourAds_adapter.notifyDataSetChanged();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.getOwnAds, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if (response.toLowerCase().contains("error")) {
                    AlertDialog alertDialog = new AlertDialog.Builder(ManageYourAds.this).create();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(ManageYourAds.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(ManageYourAds.this).create();
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
                params.put("verified", String.valueOf(verified));
                params.put("available", String.valueOf(available));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(ManageYourAds.this);
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
        MySingleton.getInstance(ManageYourAds.this).addToRequestQueue(stringRequest);
    }

    private void showAds(String response) {
        try {
            Log.d("json",response);
            JSONArray results=new JSONArray(response);
            for(int i=0;i<results.length();i++) {
                JSONObject result = results.getJSONObject(i);
                adsArrayList.add(new AD(result.getString("uniqueid"), number, result.getString("adtype"), result.getString("houseno"), result.getString("area"), result.getString("city"), result.getString("city_id"), result.getString("landmark"), result.getString("pincode"), result.getString("propertytype"), result.getString("bedrooms"), result.getString("bathrooms"), result.getString("furnishtype"), result.getString("builtuparea"), result.getString("price"), result.getString("constructionstatus"), result.getString("ageofproperty"), result.getString("possessionstatus"), result.getString("length"), result.getString("width"), result.getString("widthoffacingroad"), result.getString("pic1"), result.getString("pic2"), result.getString("pic3"), result.getString("verified"), result.getString("available"), result.getString("created")));
            }

            resultlist.setVisibility(View.VISIBLE);
            noadsfoundlayout.setVisibility(View.GONE);
            manageYourAds_adapter.notifyDataSetChanged();
        }catch(Exception e){
            Log.d("jsonerror",e.toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(ManageYourAds.this);
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

    @Override
    public void onClick(View v) {
        Log.d("clickcheck",String.valueOf(v.getId()));
        if(v.getId()==R.id.postad){
            Intent intent = new Intent(ManageYourAds.this, PostAd.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(ManageYourAds.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if(v.getId()==R.id.filtersmanage){
            if(filterflag){
                filterlayoutmanage.setVisibility(View.GONE);
                filterflag=false;
            }
            else{
                filterlayoutmanage.setVisibility(View.VISIBLE);
                filterflag=true;
                if(verifiedflag)
                    verified.setChecked(true);
                else
                    verified.setChecked(false);
                if(availableflag)
                    available.setChecked(true);
                else
                    available.setChecked(false);
            }

            //propertylayout.setVisibility(View.GONE);
            //filtersma.setVisibility(View.GONE);
        }
        if(v.getId()==R.id.applybtnm){
            verifiedflag=filterverified;
            availableflag=filteravailable;
            int i,j;
            if(verifiedflag)
                i=1;
            else
                i=0;
            if(availableflag)
                j=1;
            else
                j=0;
            sendData(0,i,j,true);
            filterlayoutmanage.setVisibility(View.GONE);

        }
        if(v.getId()==R.id.cancelbtnm){
            if(verifiedflag)
                verified.setChecked(true);
            else
                verified.setChecked(false);
            if(availableflag)
                available.setChecked(true);
            else
                available.setChecked(false);
            filterlayoutmanage.setVisibility(View.GONE);
        }
        if(v.getId()==R.id.showallbtnm){
            verifiedflag=availableflag=false;
            verified.setChecked(false);
            available.setChecked(false);
            sendData(0,2,2,true);
            filterlayoutmanage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(filterflag)
            filterlayoutmanage.setVisibility(View.GONE);
    }
}
