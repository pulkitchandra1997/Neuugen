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
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
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
            if (checkLoggedInProfile()) {
                intent = new Intent(WelcomeScreen.this, null);
            } else
                intent = new Intent(WelcomeScreen.this, MobileNumberInput.class);
            openNextActivity(intent);
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
        },2000);
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
            alertDialog.setMessage("No Internet Connection");
            alertDialog.setIcon(R.mipmap.ic_launcher_round);
            alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            alertDialog.show();
            return false;
        }
        else
            return true;
    }
}
