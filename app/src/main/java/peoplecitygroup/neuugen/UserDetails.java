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
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Html;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;

import java.util.HashMap;
import java.util.Map;

public class UserDetails extends AppCompatActivity implements View.OnClickListener {

    com.google.android.material.textfield.TextInputEditText name,city,email;
    androidx.appcompat.widget.AppCompatTextView autodetect,autodetecticon;
    com.google.android.material.button.MaterialButton submit;
    String nametext,emailtext,citytext,number;
    ProgressDialog loading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        idLink();
        listenerLink();
        hideSoftKeyboard();
        loading = new ProgressDialog(UserDetails.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Intent intent=getIntent();
        number=intent.getStringExtra("number");
        if (number == null)
            finish();
        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        autodetecticon.setTypeface(font);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void idLink() {

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        city=findViewById(R.id.city);
        submit=findViewById(R.id.submit);
        autodetect=findViewById(R.id.autodetect);
        autodetecticon=findViewById(R.id.autodetecticon);
    }

    public void listenerLink() {
        autodetect.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.autodetect)
        {

        }
        if (v.getId()==R.id.submit)
        {
            if(checkData()){
             sendData();
            }
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(UserDetails.this);

        builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
        builder.setMessage("Are you sure you want to end the registration?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(UserDetails.this, MobileNumberInput.class);
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(UserDetails.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.mipmap.ic_launcher_round);
        AlertDialog dialog = builder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FF12B2FA"));
    }

    private void sendData() {
        loading.show();

        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.set_basic_userdetails, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if(response.toLowerCase().contains("error")){
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
                    if(response.toLowerCase().equalsIgnoreCase("success")){
                        toSharedPreferences();
                        nextActivity();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(UserDetails.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(UserDetails.this).create();
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
                params.put("mobileno", number);
                params.put("name",nametext);
                params.put("email",emailtext);
                params.put("city",citytext);
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
        MySingleton.getInstance(UserDetails.this).addToRequestQueue(stringRequest);
    }

    private void nextActivity() {
        PROFILE profile=new PROFILE(number,nametext,emailtext,citytext,"","","","","","0","0","0","");
        Intent intent = new Intent(UserDetails.this, UserMainActivity.class);
        intent.putExtra("profile",profile);
        ActivityOptions options = ActivityOptions.makeCustomAnimation(UserDetails.this, R.anim.fade_in, R.anim.fade_out);
        startActivity(intent);
    }

    private void toSharedPreferences() {
        SharedPreferences sp;
        SharedPreferences.Editor se;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        se=sp.edit();
            se.putString("mobileno", number);
            se.putString("name", nametext);
            se.putString("email", emailtext);
            se.putString("city", citytext);
            se.putString("address", null);
            se.putString("state", null);
            se.putString("pincode", null);
            se.putString("gender", null);
            se.putString("dob", null);
            se.putString("emailverified", "0");
            se.putString("profileflag", "0");
            se.putString("addressverified", "0");
            se.putString("pic",null);
            se.commit();
    }

    private boolean checkData() {
        nametext=name.getText().toString().trim();
        emailtext=email.getText().toString().trim();
        citytext=city.getText().toString().trim();
        if(nametext==null||emailtext==null||citytext==null||nametext==""||emailtext==""||citytext==""){
            if(nametext==null||nametext==""){
                name.setError("Enter Name");
                name.requestFocus();
            }else{
                if(emailtext==null||emailtext==""){
                    email.setError("Enter Email ID");
                    email.requestFocus();
                }
                else{
                    city.setError("Enter City");
                    city.requestFocus();
                }
            }
        }
        else{
            if(!Validation.isValidName(nametext)) {
                name.setError("Enter Valid Name");
                name.requestFocus();
            }else{
                if(!Validation.isValidEmail(emailtext)){
                    email.setError("Enter Valid Email");
                    email.requestFocus();
                }else{
                    if(!Validation.isValidCity(citytext)){
                        city.setError("Enter Valid City");
                        city.requestFocus();
                    }else{
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
