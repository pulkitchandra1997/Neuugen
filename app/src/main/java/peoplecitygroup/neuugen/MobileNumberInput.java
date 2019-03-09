package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.android.material.resources.MaterialResources;

import java.util.ArrayList;
import java.util.List;

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
    List<String> numbers=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_input);


        idLink();
        listenerLink();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkMobileNumber();
            }
        }, 1000);
    }

    private void checkMobileNumber() {
        fetchNumber();
        String mobile_no = null;
        AccountManager am = AccountManager.get(this);
        Account[] accounts = am.getAccounts();
        for (Account ac : accounts) {
            String acname = ac.name;

            if (acname.startsWith("+91")) {
                mobile_no = acname;
            }
        }
        if (mobile_no != null) {
            clearnumber(mobile_no);
        }
        createDialog();
    }

    private void fetchNumber() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            SubscriptionManager subscriptionManager = SubscriptionManager.from(getApplicationContext());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                }
                else{
                    List<SubscriptionInfo> subsInfoList = subscriptionManager.getActiveSubscriptionInfoList();
                    if(subsInfoList!=null)
                    for (SubscriptionInfo subscriptionInfo : subsInfoList) {
                        String number = subscriptionInfo.getNumber();
                        clearnumber(number);
                        Log.d("Test", " Number is  " + number);
                }
            }
            }
        }
    }

    private void clearnumber(String mobile_no) {
        if(mobile_no!=null&&mobile_no!="") {
            String num = "";
            for (int i = 0; i < mobile_no.length(); i++)
                if (mobile_no.charAt(i) != ' ')
                    num += mobile_no.charAt(i);
            if (num.startsWith("+91"))
                num = num.substring(3);
            if (num.startsWith("91") && num.length() == 12)
                num = num.substring(2);
            if (num.length() == 10)
                if (!numbers.contains(num))
                    numbers.add(num);
        }
    }

    private void createDialog() {
        if(!numbers.isEmpty()) {
            AlertDialog.Builder builderSingle = new AlertDialog.Builder(MobileNumberInput.this,R.style.Theme_AppCompat_DayNight_Dialog);
            builderSingle.setIcon(R.mipmap.ic_launcher);
            builderSingle.setTitle("Select Mobile Number:-");

            final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(MobileNumberInput.this, android.R.layout.simple_list_item_single_choice);
            for(String num:numbers)
                arrayAdapter.add(num);
            builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    String strName = arrayAdapter.getItem(which);
                    nextActivity(strName);
                }
            });
            builderSingle.show();
        }
    }

    private void nextActivity(String strName) {
        Intent intent = new Intent(MobileNumberInput.this, OtpInputActivity.class);
        intent.putExtra("phone",strName);
        if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(MobileNumberInput.this, R.anim.fade_in, R.anim.fade_out);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
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
                } else
                    nextActivity(phonetext);
            }
        }
    }
}

