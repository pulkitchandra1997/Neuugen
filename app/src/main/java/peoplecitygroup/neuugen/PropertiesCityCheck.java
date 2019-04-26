package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.service.LinkCities;
import peoplecitygroup.neuugen.service.MySingleton;
import peoplecitygroup.neuugen.service.UrlNeuugen;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class PropertiesCityCheck extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String adtypetext=null,citytext,propertiesID;
    TextInputEditText citycheck;
    MaterialButton next;
    ProgressDialog loading = null;
    ArrayList<String>allcities=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties_city_check);

        idLink();
        listenerLink();
        hideSoftKeyboard();

        intent=getIntent();
        adtypetext=intent.getStringExtra("adtype");
        propertiesID=intent.getStringExtra("propertiesid");

        loading = new ProgressDialog(PropertiesCityCheck.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Please Wait!");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        allcities=new ArrayList<String>();
        String[] ar= LinkCities.cities.split(",");
        for(String c:ar)
            allcities.add(c.toLowerCase().trim());
        citycheck.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }







    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void idLink() {
        citycheck=findViewById(R.id.citycheck);
        next=findViewById(R.id.next);
    }

    private void listenerLink() {
        next.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.next)
        {
            citytext=citycheck.getText().toString().trim();
            if (TextUtils.isEmpty(citytext))
            {
                  citycheck.setError("Enter City");
                  citycheck.requestFocus();
            }
            else{
                if(allcities.contains(citytext.toLowerCase().trim())){
                    checkActive();
                }
                else{
                    citycheck.setError("No such city found");
                    citycheck.requestFocus();
                }
            }
        }

    }

    private void checkActive() {
        loading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.checkCityService, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if (response.toLowerCase().contains("error")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(PropertiesCityCheck.this);
                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Connection Error!")
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

                } else {
                    if (response.toLowerCase().equalsIgnoreCase("serviceactive"))
                        nextActivity();
                    else
                        if(response.toLowerCase().equalsIgnoreCase("servicenotactive")){
                            //ALERT DIALOG SERVICE NOT ACTIVE AND SEND BACK
                            AlertDialog.Builder builder = new AlertDialog.Builder(PropertiesCityCheck.this);
                            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                            builder.setMessage("Sorry , currently this service is not active in this city.")
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
                        if(response.toLowerCase().equalsIgnoreCase("servicenotactive:city")){
                            //ALERT DIALOG WITH MSG: COMING SOON TO YOUR CITY
                            AlertDialog.Builder builder = new AlertDialog.Builder(PropertiesCityCheck.this);
                            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                            builder.setMessage("We do not provide service in this city. We'll soon reach your city.")
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
                        if(response.toLowerCase().equalsIgnoreCase("citynotpresent")){
                            //ALERT DIALOG MSG: entered city is not present in database
                            AlertDialog.Builder builder = new AlertDialog.Builder(PropertiesCityCheck.this);
                            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                            builder.setMessage("Enter valid City")
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
                        //MORE CODE for more than one city
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(PropertiesCityCheck.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(PropertiesCityCheck.this).create();
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
                params.put("city",citytext.toUpperCase());
                params.put("serviceid", propertiesID);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(PropertiesCityCheck.this);
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
        MySingleton.getInstance(PropertiesCityCheck.this).addToRequestQueue(stringRequest);
    }

    private void nextActivity() {
        Intent intent=null;
        if(adtypetext.equalsIgnoreCase("rent office"))
            intent=new Intent(PropertiesCityCheck.this,RentOffice.class);
        if(adtypetext.equalsIgnoreCase("rent houses"))
            intent=new Intent(PropertiesCityCheck.this,RentHouses.class);
        if(adtypetext.equalsIgnoreCase("sell houses"))
            intent=new Intent(PropertiesCityCheck.this,SellHouses.class);
        if(adtypetext.equalsIgnoreCase("sell plots"))
            intent=new Intent(PropertiesCityCheck.this,SellPlots.class);
        intent.putExtra("city",citytext);
        if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(PropertiesCityCheck.this, R.anim.fade_in, R.anim.fade_out);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }

    }

}
