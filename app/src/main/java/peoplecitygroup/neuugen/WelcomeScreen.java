package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.WindowManager;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.service.MySingleton;
import peoplecitygroup.neuugen.service.PROFILE;
import peoplecitygroup.neuugen.service.UrlNeuugen;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_NUMBERS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class WelcomeScreen extends AppCompatActivity {
    Intent intent=null;
    PROFILE profile=null;
    ProgressDialog loading = null;
    static boolean flag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        loading = new ProgressDialog(WelcomeScreen.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Checking Latest Version of app...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        if(haveNetworkConnection()) {
            if(checkLatestVersion())
                new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    checkPermission();
                }
            },3000);
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },3000);
        }



    }
    public void checkspcheck(){
        if (checkLoggedInProfile()) {
            intent = new Intent(WelcomeScreen.this, UserMainActivity.class);
            intent.putExtra("profile", profile);
        } else
            intent = new Intent(WelcomeScreen.this, MobileNumberInput.class);
        openNextActivity(intent);
    }
    private boolean checkLatestVersion() {
        flag=false;
        loading.show();
        final int versionCode = BuildConfig.VERSION_CODE;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.checkappversion, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if (response.toLowerCase().contains("error")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeScreen.this);
                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Error in server. Try Again")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                    flag=false;
                } else {
                    if (response.toLowerCase().contains("link:")) {
                        final String link = response.substring(response.indexOf(':') + 1);
                        AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeScreen.this);
                        builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        builder.setMessage("Update the app to latest version.")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        Intent browserIntent = new Intent(
                                                Intent.ACTION_VIEW,
                                                Uri.parse(link.trim()));
                                        startActivity(browserIntent);
                                    }
                                })
                                .setIcon(R.mipmap.ic_launcher_round);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                        flag=true;
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
                if( !haveConnectedWifi && !haveConnectedMobile)
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(WelcomeScreen.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(WelcomeScreen.this).create();
                    alertDialog.setMessage("Connection Error!");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                flag=false;
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                Map<String, String> params = new HashMap<>();
                params.put("versioncode", String.valueOf(versionCode));
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
                AlertDialog.Builder builder = new AlertDialog.Builder(WelcomeScreen.this);

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
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

            }
        });
        MySingleton.getInstance(WelcomeScreen.this).addToRequestQueue(stringRequest);
        return !flag;
    }

    private void openNextActivity(final Intent intent) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchScreen(intent);
            }
        },3000);
    }

    private void switchScreen(Intent intent) {
        ActivityOptions options = ActivityOptions.makeCustomAnimation(WelcomeScreen.this, R.anim.fade_in, R.anim.fade_out);
        startActivity(intent, options.toBundle());
        finish();
    }

    private void checkFirstTime()
    {
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp.getString("firsttime",null)==null) {

        }
        else{

        }

    }

    private boolean checkLoggedInProfile() {
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null){
            profile = new PROFILE(
                    sp.getString("mobileno", null),sp.getString("name", null),
                    sp.getString("email", null),
                    sp.getString("city", null),
                    sp.getString("address", null),
                    sp.getString("state", null),
                    sp.getString("pincode", null),
                    sp.getString("gender", null),
                    sp.getString("dob", null),
                    sp.getString("emailverified", null),
                    sp.getString("profileflag",null),sp.getString("addressverified",null),sp.getString("pic",null));
            if(sp.getString("mobileno",null)!=null&&sp.getString("name",null)!=null&&sp.getString("email",null)!=null&&sp.getString("city",null)!=null) {
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }




    //INTERNET CONNECTION CHECK
    private boolean haveNetworkConnection() {
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
        if( !haveConnectedWifi && !haveConnectedMobile)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setMessage("No Internet Connection! Check your connection and Try again");
            alertDialog.setIcon(R.mipmap.ic_launcher_round);
            alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            alertDialog.show();

            return false;
        }
        else
            return true;
    }



    //PERMISSION CHECK AND ASK FOR IT
    private void checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), CALL_PHONE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_NETWORK_STATE);
        int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int result7 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_CONTACTS);
        int result8 = ContextCompat.checkSelfPermission(getApplicationContext(), GET_ACCOUNTS);
        int result9 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
        int result10=ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int result11=ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_NUMBERS);
        if (!(result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED && result6 == PackageManager.PERMISSION_GRANTED && result7 == PackageManager.PERMISSION_GRANTED && result8 == PackageManager.PERMISSION_GRANTED && result9 == PackageManager.PERMISSION_GRANTED && result10 == PackageManager.PERMISSION_GRANTED && result11 == PackageManager.PERMISSION_GRANTED)){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Grant Permission to access all features of the App.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent=new Intent(WelcomeScreen.this,UserPermission.class);
                            startActivity(intent);
                        }
                    })
                    .setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //openNextActivity(intent);
                        checkspcheck();
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button neutralButton = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);

            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

            neutralButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
        else
            //openNextActivity(intent);
            checkspcheck();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //checkPermission();
        checkspcheck();
    }
}
