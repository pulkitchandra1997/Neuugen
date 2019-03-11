package peoplecitygroup.neuugen;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if(haveNetworkConnection()) {
            checkLatestVersion();
            if (checkLoggedInProfile())
                intent = new Intent(WelcomeScreen.this, null);
            else
                intent = new Intent(WelcomeScreen.this, MobileNumberInput.class);
            //openNextActivity(intent);
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
            },5000);
        }



    }

    private void checkLatestVersion() {
    }

    private void openNextActivity(Intent intent) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switchScreen();
            }
        },3000);
    }

    private void switchScreen() {
        ActivityOptions options = ActivityOptions.makeCustomAnimation(WelcomeScreen.this, R.anim.fade_in, R.anim.fade_out);
        startActivity(intent, options.toBundle());
        finish();
    }

    private boolean checkLoggedInProfile() {
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
                            openNextActivity(intent);
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
            openNextActivity(intent);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkPermission();
    }
}
