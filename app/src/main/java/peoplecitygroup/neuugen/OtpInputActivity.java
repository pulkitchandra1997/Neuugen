package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
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
import com.msg91.sendotp.library.SendOtpVerification;
import com.msg91.sendotp.library.Verification;
import com.msg91.sendotp.library.VerificationListener;

import java.util.HashMap;
import java.util.Map;

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

public class OtpInputActivity extends AppCompatActivity implements View.OnClickListener, VerificationListener {

    androidx.appcompat.widget.AppCompatTextView timer,phone,resendotp;
    PinView pinView;
    String phonetext,timertext;
    int otptext;
    private static final int PERMISSION_REQUEST_CODE = 200;

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
    }

    private void sendOtp() {
        Verification mVerification= SendOtpVerification.createSmsVerification
                (SendOtpVerification
                        .config("+91" + phonetext)
                        .context(this)
                        .autoVerification(true)
                        .build(), this);
        mVerification.initiate();
        checkPermission();
    }

    private void checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), READ_SMS);
        if(result!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{READ_SMS},PERMISSION_REQUEST_CODE);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean readSms=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    if (!(readSms))
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setMessage("Permission Denied");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                        alertDialog.show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(READ_SMS)) {
                                showMessageOKCancel("You need to allow access to auto detect OTP",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{READ_SMS},
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

    @Override
    public void onInitiated(String response) {
        Log.i( "OTP: Initialized!" , response);
    }

    @Override
    public void onInitiationFailed(Exception paramException) {
        Log.i( "OTP:initialization fail" , paramException.getMessage());
    }

    @Override
    public void onVerified(String response) {
        Log.i("OTP: Verified!" , response);
    }

    @Override
    public void onVerificationFailed(Exception paramException) {
        Log.i("OTP: Verification fail" , paramException.getMessage());
    }
}
