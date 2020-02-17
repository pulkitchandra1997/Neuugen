package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.properties.RentOffice;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class Customercare extends AppCompatActivity implements View.OnClickListener {

    MaterialButton callbtn,mailbtn,msgbtn;
    TextInputEditText name,email,message,subject;
    String nametext,emailtext,msgtext,subtext;
    ProgressDialog loading = null;
    JSONObject jsonObject= new JSONObject();
    String uniqueid=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customercare);

        idLink();
        listenerLink();
        hideSoftKeyboard();
        loading = new ProgressDialog(Customercare.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        callbtn.setTypeface(font);
        mailbtn.setTypeface(font);
    }
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    private void idLink() {
        callbtn=findViewById(R.id.callbtn);
        mailbtn=findViewById(R.id.mailbtn);
        msgbtn=findViewById(R.id.msgbtn);
        name=findViewById(R.id.namecs);
        email=findViewById(R.id.emailcs);
        message=findViewById(R.id.messagecs);
        subject=findViewById(R.id.subjectcs);
    }

    private void listenerLink() {
        callbtn.setOnClickListener(this);
        mailbtn.setOnClickListener(this);
        msgbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.callbtn)
        {

                Intent i = new Intent(Intent.ACTION_DIAL);
                Uri u = Uri.parse("tel:"+ UrlNeuugen.csphone);
                i.setData(u);
                /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }*/
                startActivity(i);

        }
        if (v.getId()==R.id.mailbtn)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String[] recipients={UrlNeuugen.csemail};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT,subtext);
        /*intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
        intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");*/
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, "Send mail"));
        }
        if (v.getId()==R.id.msgbtn)
        {
            msgtext=message.getText().toString().trim();
            nametext=name.getText().toString().trim();
            emailtext=email.getText().toString().trim();
            subtext=subject.getText().toString().trim();
            if (TextUtils.isEmpty(nametext) || TextUtils.isEmpty(emailtext) || TextUtils.isEmpty(subtext)||TextUtils.isEmpty(msgtext)) {
                if (TextUtils.isEmpty(nametext)) {
                    name.setError("Enter Name");
                    name.requestFocus();
                } else if (TextUtils.isEmpty(emailtext)) {
                    email.setError("Enter Email");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(subtext)) {
                    subject.setError("Enter Subject");
                    subject.requestFocus();
                } else if (TextUtils.isEmpty(msgtext)) {
                    message.setError("Enter Message");
                    message.requestFocus();
                }
            }else {
                toserver();
            }

        }

    }
    private boolean createJsonObject() {
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            try {
                jsonObject.put("mobileno",sp.getString("mobileno",null));
                jsonObject.put("email",emailtext);
                jsonObject.put("name",nametext);
                jsonObject.put("message",msgtext);
                jsonObject.put("subject",subtext);

                return true;
            } catch (Exception e) {
                Log.i("errorrent",e.getMessage());
                return false;

            }
        }
        return false;
    }
    private void toserver() {
        loading.show();
        if (createJsonObject()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_Customercare, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toLowerCase().contains("error")) {
                        loading.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(Customercare.this).create();
                        alertDialog.setMessage(response);
                        alertDialog.setButton(BUTTON_POSITIVE,"Ok", (DialogInterface.OnClickListener) null);
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                        Button positiveButton = alertDialog.getButton(BUTTON_POSITIVE);
                        positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

                    } else {
                        if(response.toLowerCase().contains("success")){
                            uniqueid=response.substring(7);
                            loading.dismiss();
                            AlertDialog alertDialog = new AlertDialog.Builder(Customercare.this).create();
                            alertDialog.setMessage(Html.fromHtml("<b>Your message has been sent successfully.</b>"));
                            alertDialog.setIcon(R.mipmap.ic_launcher_round);
                            alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                            alertDialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(Customercare.this, UserMainActivity.class);
                                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                                        ActivityOptions options = ActivityOptions.makeCustomAnimation(Customercare.this, R.anim.fade_in, R.anim.fade_out);
                                        startActivity(intent, options.toBundle());
                                    } else {
                                        startActivity(intent);
                                    }
                                    finish();
                                }
                            },2000);
                        }
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
                        AlertDialog alertDialog = new AlertDialog.Builder(Customercare.this).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(Customercare.this).create();
                        alertDialog.setMessage(error.toString());
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    Log.i("dataflag",jsonObject.toString());
                    params.put("data", jsonObject.toString());
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Customercare.this);
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
            MySingleton.getInstance(Customercare.this).addToRequestQueue(stringRequest);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(Customercare.this);

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error in connection.")
                    .setPositiveButton("OK", null)
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
    }
}
