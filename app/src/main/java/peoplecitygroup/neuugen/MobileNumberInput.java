package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.resources.MaterialResources;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.CALL_PHONE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.GET_ACCOUNTS;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class MobileNumberInput extends AppCompatActivity implements View.OnClickListener {

    com.google.android.material.textfield.TextInputEditText phonenum;
    com.google.android.material.button.MaterialButton nextbtn;
    String phonetext;
    private static final int PERMISSION_REQUEST_CODE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_input);

        idLink();
        listenerLink();
        checkPermission();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkMobileNumber();
            }
        },1000);
    }

    private void checkMobileNumber() {
        String mobile_no=null;
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();
        for (Account ac : accounts)
        {
            String acname = ac.name;

            if (acname.startsWith("+91")) {
                mobile_no = acname;
            }
        }
        if (mobile_no!=null){
            String num="";
            for(int i=0;i<mobile_no.length();i++)
                if(mobile_no.charAt(i)!=' ')
                    num+=mobile_no.charAt(i);
            if(num.startsWith("+91"))
                num=num.substring(3);
            if(num.startsWith("91")&&num.length()==12)
                num=num.substring(2);
            if(num.length()==10)
                createDialog(num);
                        }
    }

    private void createDialog(String num) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MobileNumberInput.this);
        builder.setMessage(num);
        builder.setTitle("Select Mobile Number");
        builder.setPositiveButton("Next", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //do things
            }
        });

        builder.setNeutralButton("CANCEL", new DialogInterface.OnClickListener()     {
            public void onClick(DialogInterface dialog, int id) {
                //do things
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void idLink() {
        phonenum = findViewById(R.id.phonenum);
        nextbtn = findViewById(R.id.nextbtn);
    }

    public void listenerLink() {
        nextbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nextbtn) {
            phonetext = phonenum.getText().toString().trim();
            if (TextUtils.isEmpty(phonetext)) {
                phonenum.setError("Enter phone number");
                phonenum.requestFocus();
            } else {
                if (!Validation.isValidPhone(phonetext)) {
                    phonenum.setError("Enter Valid Phone no");
                    phonenum.requestFocus();
                } else {
                    Intent intent = new Intent(MobileNumberInput.this, OtpInputActivity.class);
                    intent.putExtra("phone",phonetext);
                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(MobileNumberInput.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
            }

        }
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
        if (!(result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED && result6 == PackageManager.PERMISSION_GRANTED && result7 == PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, new String[]{CAMERA,READ_EXTERNAL_STORAGE,CALL_PHONE,WRITE_EXTERNAL_STORAGE,ACCESS_NETWORK_STATE,INTERNET,READ_CONTACTS,GET_ACCOUNTS,READ_SMS}, PERMISSION_REQUEST_CODE);
        }
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
                    if (!(camera&&readExternalStorage&&callPhone&&writeExternalStorage&&accessNetworkState&&internet&&readContacts&&getAccounts&&readSms))
                    {
                        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                        alertDialog.setMessage("Permission Denied");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                        alertDialog.show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to all the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA,READ_EXTERNAL_STORAGE,CALL_PHONE,WRITE_EXTERNAL_STORAGE,ACCESS_NETWORK_STATE,INTERNET,READ_CONTACTS,GET_ACCOUNTS,READ_SMS},
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
        new AlertDialog.Builder(MobileNumberInput.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"))
                .create()
                .show();
    }
}

