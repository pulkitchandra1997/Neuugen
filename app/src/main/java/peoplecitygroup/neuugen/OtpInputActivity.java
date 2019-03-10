package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.chaos.view.PinView;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Map;

public class OtpInputActivity extends AppCompatActivity implements View.OnClickListener {

    androidx.appcompat.widget.AppCompatTextView timer,phone,resendotp;
    PinView pinView;
    String phonetext,timertext;
    int otptext;
    boolean FLAG=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_input);

        idLink();
        listenerLink();

        Intent intent=getIntent();
        phonetext=intent.getStringExtra("phone");
        phone.setText(phonetext);
        otptext=OTP_GENERATION.generateRandomNumber();
        sendOtp();
        if(FLAG){

        }
        else{

        }
    }

    private void sendOtp() {
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.send_otp, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {

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
                    AlertDialog builder = new AlertDialog.Builder(OtpInputActivity.this).create();
                    builder.setIcon(R.mipmap.ic_launcher_round);
                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>RentZHub</font>"));
                    builder.setMessage("Connection error! Retry");
                    builder.show();
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

    public void idLink() {
        phone = findViewById(R.id.phone);
        timer = findViewById(R.id.timer);
        resendotp=findViewById(R.id.resendotpbtn);
        pinView=findViewById(R.id.pinview);
    }

    public void listenerLink() {
        resendotp.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {

    }
}
