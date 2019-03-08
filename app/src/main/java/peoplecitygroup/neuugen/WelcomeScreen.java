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
    private static final int PERMISSION_REQUEST_CODE = 200;

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
            checkPermission();
        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                }
            },6000);
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
        if (!(result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED && result6 == PackageManager.PERMISSION_GRANTED && result7 == PackageManager.PERMISSION_GRANTED) && result8 == PackageManager.PERMISSION_GRANTED && result9 == PackageManager.PERMISSION_GRANTED && result10 == PackageManager.PERMISSION_GRANTED && result11 == PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{CAMERA,READ_EXTERNAL_STORAGE,CALL_PHONE,WRITE_EXTERNAL_STORAGE,ACCESS_NETWORK_STATE,INTERNET,READ_CONTACTS,GET_ACCOUNTS,READ_SMS,READ_PHONE_STATE,READ_PHONE_NUMBERS}, PERMISSION_REQUEST_CODE);
        }
        else
            openNextActivity(intent);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean callPhone = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean camera = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean writeExternalStorage=grantResults[3]==PackageManager.PERMISSION_GRANTED;
                    boolean accessNetworkState=grantResults[4]==PackageManager.PERMISSION_GRANTED;
                    boolean internet=grantResults[5]==PackageManager.PERMISSION_GRANTED;
                    boolean readContacts=grantResults[6]==PackageManager.PERMISSION_GRANTED;
                    boolean getAccounts=grantResults[7]==PackageManager.PERMISSION_GRANTED;
                    boolean readSms=grantResults[8]==PackageManager.PERMISSION_GRANTED;
                    boolean readPhoneState=grantResults[9]==PackageManager.PERMISSION_GRANTED;
                    boolean readPhoneNumbers=grantResults[10]==PackageManager.PERMISSION_GRANTED;
                    if (!(camera&&readExternalStorage&&callPhone&&writeExternalStorage&&accessNetworkState&&internet&&readContacts&&getAccounts&&readSms&&readPhoneState&&readPhoneNumbers))
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setMessage("Permission Denied");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                //openNextActivity(intent);
                            }
                        });
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                        alertDialog.show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to all the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA,READ_EXTERNAL_STORAGE,CALL_PHONE,WRITE_EXTERNAL_STORAGE,ACCESS_NETWORK_STATE,INTERNET,READ_CONTACTS,GET_ACCOUNTS,READ_SMS,READ_PHONE_STATE,READ_PHONE_NUMBERS},
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
            case 123:
                Toast.makeText(this, "check", Toast.LENGTH_SHORT).show();
                break;
        }
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
      new AlertDialog.Builder(WelcomeScreen.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .setIcon(R.mipmap.ic_launcher_round)
              .setOnDismissListener(new DialogInterface.OnDismissListener() {
              @Override
                  public void onDismiss(DialogInterface dialog) {
                     openNextActivity(intent);
                }
                 })
                .setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"))
                .create()
                .show();
    }

}
