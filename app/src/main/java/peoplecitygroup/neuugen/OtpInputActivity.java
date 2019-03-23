package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chaos.view.PinView;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class OtpInputActivity extends AppCompatActivity implements View.OnClickListener {

    androidx.appcompat.widget.AppCompatTextView timer,phone,resendotp;
    PinView pinView;
    String phonetext;
    int otptext;
    LinearLayout linearLayout;
    private static final int PERMISSION_REQUEST_CODE = 200;
    ProgressDialog loading = null;
    PROFILE profile=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_input);
        cancelTimer();
        idLink();
        listenerLink();

        loading = new ProgressDialog(OtpInputActivity.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        resendotp.setVisibility(View.INVISIBLE);
        Intent intent=getIntent();
        phonetext=intent.getStringExtra("phone");
        phone.setText(phonetext);
        otptext=OTP_GENERATION.generateRandomNumber();
        sendOtp();
        pinView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(pinView.getText().toString().length()==4){
                    boolean isFound = pinView.getText().toString().indexOf(String.valueOf(otptext)) !=-1? true: false;
                    if(isFound)
                        nextActivity();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private String parseCode(String message) {
        boolean isFound = message.indexOf(getResources().getString(R.string.OTP_PRETEXT)) !=-1? true: false;
        if(isFound)
            return message.substring(message.length()-6);
        else
            return "";
    }

    private void sendOtp() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.send_otp, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                response.trim();
                Log.i("OTP: Response",response);
                if(response.length()==24){
                    resendotp.setVisibility(View.INVISIBLE);

                    Snackbar.make(findViewById(R.id.parentview), "OTP sent", Snackbar.LENGTH_LONG)
                            .show();
                    checkPermission();
                    startTimer();

                }
                else{
                    new AlertDialog.Builder(OtpInputActivity.this,R.style.Theme_AppCompat_DayNight_Dialog_Alert)
                            .setMessage("Error in sendind OTP. Try Again!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round)
                            .setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"))
                            .create()
                            .show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
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
                    AlertDialog alertDialog = new AlertDialog.Builder(OtpInputActivity.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(OtpInputActivity.this).create();
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
                params.put("number", phonetext);
                params.put("otp", String.valueOf(otptext));
                return params;
            }
        };
        MySingleton.getInstance(OtpInputActivity.this).addToRequestQueue(stringRequest);
    }

    private void checkPermission() {
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECEIVE_SMS);
        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
        if (!(result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{RECEIVE_SMS,READ_SMS}, PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean readSms=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    boolean receiveSms=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if (!(receiveSms&&readSms))
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setMessage("Permission Denied");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                        alertDialog.show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(RECEIVE_SMS)) {
                                showMessageOKCancel("You need to allow access to all the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{RECEIVE_SMS,READ_SMS},
                                                            PERMISSION_REQUEST_CODE);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(OtpInputActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"))
                .create()
                .show();
    }
    BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase("otp")) {
                final String message = intent.getStringExtra("message");
                autoDetectOtp(message);
            }
        }
    };

    private void autoDetectOtp(String message) {
        String code=parseCode(message);
        boolean isFound = code.indexOf(String.valueOf(otptext)) !=-1? true: false;
        if(isFound)
            pinView.setText(code);
    }

    private void nextActivity() {

        loading.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.get_profile_login, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                Log.i("checkerror",response);
                loading.dismiss();
                if(response.toLowerCase().contains("error in server")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());

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
                }
                else{
                    if(response.toLowerCase().contains("noprofile"))
                        if(response.toLowerCase().equalsIgnoreCase("noprofile"))
                            nextActivity(true);
                        else
                            checkProfile(response);
                    else
                        checkProfile(response);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(OtpInputActivity.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(OtpInputActivity.this).create();
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
                params.put("number", phonetext);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());

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
        MySingleton.getInstance(OtpInputActivity.this).addToRequestQueue(stringRequest);
    }

    private void nextActivity(boolean b) {
        Log.i("checkerror",String.valueOf(b));
        Intent intent=null;
        if(b){//true: new profile
            intent = new Intent(OtpInputActivity.this, UserDetails.class);
            intent.putExtra("number",phonetext);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(OtpInputActivity.this, R.anim.fade_in, R.anim.fade_out);
            startActivity(intent, options.toBundle());
        }
        else{
            intent = new Intent(OtpInputActivity.this, UserMainActivity.class);
            intent.putExtra("profile",profile);
            ActivityOptions options = ActivityOptions.makeCustomAnimation(OtpInputActivity.this, R.anim.fade_in, R.anim.fade_out);
            startActivity(intent);
        }
    }

    private void checkProfile(String response) {
        try {
            JSONObject jsonObject=new JSONObject(response);
            tosharedpreference(jsonObject);
            profile=new PROFILE(phonetext,jsonObject.getString("name"),jsonObject.getString("email"),jsonObject.getString("city"),jsonObject.getString("address"),jsonObject.getString("state"),jsonObject.getString("pincode"),jsonObject.getString("gender"),jsonObject.getString("dob"),jsonObject.getString("emailverified"),jsonObject.getString("profileflag"),jsonObject.getString("addressverified"),jsonObject.getString("pic"));
            nextActivity(false);
        } catch (JSONException e) {
            e.printStackTrace();

        }
    }

    private void tosharedpreference(JSONObject jsonObject) {
        SharedPreferences sp;
        SharedPreferences.Editor se;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        se=sp.edit();
        try {
            se.putString("mobileno", phonetext);
            se.putString("name", jsonObject.getString("name"));
            se.putString("email", jsonObject.getString("email"));
            se.putString("city", jsonObject.getString("city"));
            se.putString("address", jsonObject.getString("address"));
            se.putString("state", jsonObject.getString("state"));
            se.putString("pincode", jsonObject.getString("pincode"));
            se.putString("gender", jsonObject.getString("gender"));
            se.putString("dob", jsonObject.getString("dob"));
            se.putString("emailverified", jsonObject.getString("emailverified"));
            se.putString("profileflag", jsonObject.getString("profileflag"));
            se.putString("addressverified", jsonObject.getString("addressverified"));
            if(jsonObject.getString("pic")!=null&&jsonObject.getString("pic")!=""){
                Picasso.with(this).load(jsonObject.getString("pic")).into(picassoImageTarget(getApplicationContext(), "imageDir", "profilepic.jpeg"));
            }
            se.putString("pic",jsonObject.getString("pic"));
            se.commit();
        } catch (JSONException e) {
            e.printStackTrace();
            AlertDialog.Builder builder = new AlertDialog.Builder(getApplication());

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error. Try again!!")
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
    }
    private Target picassoImageTarget(Context context, final String imageDir, final String imageName) {
        ContextWrapper cw = new ContextWrapper(context);
        final File directory = cw.getDir(imageDir, Context.MODE_PRIVATE); // path to /data/data/yourapp/app_imageDir
        return new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final File myImageFile = new File(directory, imageName); // Create image file
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(myImageFile);
                            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.i("image", "image saved to >>>" + myImageFile.getAbsolutePath());
                    }
                }).start();
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                if (placeHolderDrawable != null) {}
            }
        };
    }

    public void idLink() {
        phone = findViewById(R.id.phone);
        timer = findViewById(R.id.timer);
        resendotp=findViewById(R.id.resendotpbtn);
        pinView=findViewById(R.id.pinview);
        linearLayout=findViewById(R.id.parentview);
    }

    public void listenerLink() {
        resendotp.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        sendOtp();
        resendotp.setVisibility(View.INVISIBLE);
    }



    //Declare timer
    CountDownTimer cTimer = null;

    //start timer function
    void startTimer() {
        cTimer = new CountDownTimer(120000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(" in "+String.valueOf( millisUntilFinished/1000)+" secs");
            }
            public void onFinish() {
                timer.setText("");
                resendotp.setVisibility(View.VISIBLE);
            }
        };
        cTimer.start();
    }


    //cancel timer
    void cancelTimer() {
        if(cTimer!=null)
            cTimer.cancel();
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).
                registerReceiver(receiver, new IntentFilter("otp"));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }



}
