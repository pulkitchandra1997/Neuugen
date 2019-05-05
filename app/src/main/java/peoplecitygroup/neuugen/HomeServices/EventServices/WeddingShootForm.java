package peoplecitygroup.neuugen.HomeServices.EventServices;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.SendMail;
import peoplecitygroup.neuugen.common_req_files.SendMsg;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.common_req_files.VolleyCallback;

public class WeddingShootForm extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    AppCompatSpinner wednumofdays;
    TextInputEditText areaWP,cityWP,landmarkWP,pincodeWP,dosWP,housenoWP;
    MaterialButton requestserviceWP;
    int day,year,month;
    String areatext,citytext,landmarktext,dostext,pincodetext,housenotext,wednumofdaystext,wedeventtypetext,wedpackagetext,wedpackagepricetext,engagementid,mehendiid,sangeetid,marriageid,receptionid,othersid,serviceId;
    LinearLayout weddingformlayout;
    AppCompatTextView wedpackage,wedpackageprice;
    String city="";
    String mobileno="";
    ProgressDialog loading = null;
    AppCompatCheckBox engagement,mehendi,sangeet,marriage,reception,others;

    public String getDate(){
        StringBuilder builder=new StringBuilder();
        builder.append(day+"/");
        builder.append((month + 1)+"/");//month is 0 based
        builder.append(year);
        return builder.toString();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_shoot_form);

        intent=getIntent();
        wedpackagetext=intent.getStringExtra("weddingpackage");
        wedpackagepricetext=intent.getStringExtra("weddingpackageprice");
        serviceId=intent.getStringExtra("serviceid");

        idLink();
        listenerLink();
        hideSoftKeyboard();
        intent=getIntent();
        SharedPreferences sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            city = sp.getString("city", null);
            mobileno=sp.getString("mobileno",null);
        }
        cityWP.setText(city);
        cityWP.setEnabled(false);
        wedpackage.setText(wedpackagetext);
        wedpackageprice.setText(wedpackagepricetext);
        loading = new ProgressDialog(WeddingShootForm.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Sending Request...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void idLink()
    {
        dosWP=findViewById(R.id.dosWP);
        areaWP=findViewById(R.id.areaWP);
        cityWP=findViewById(R.id.cityWP);
        landmarkWP=findViewById(R.id.landmarkWP);
        pincodeWP=findViewById(R.id.pincodeWP);
        requestserviceWP=findViewById(R.id.requestserviceWP);
        housenoWP=findViewById(R.id.housenoWP);
        weddingformlayout=findViewById(R.id.weddingformlayout);
        wednumofdays=findViewById(R.id.wednumofdays);
        engagement=findViewById(R.id.engagement);
        mehendi=findViewById(R.id.mehendi);
        sangeet=findViewById(R.id.sangeet);
        marriage=findViewById(R.id.marriage);
        reception=findViewById(R.id.reception);
        others=findViewById(R.id.others);
        wedpackage=findViewById(R.id.wedpackage);
        wedpackageprice=findViewById(R.id.wedpackageprice);

    }
    public void listenerLink()
    {
        requestserviceWP.setOnClickListener(this);
        dosWP.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.requestserviceWP)
        {
            areatext=areaWP.getText().toString().trim();
            housenotext=housenoWP.getText().toString().trim();
            citytext=cityWP.getText().toString().trim();
            landmarktext=landmarkWP.getText().toString().trim();
            pincodetext=pincodeWP.getText().toString().trim();
            dostext=dosWP.getText().toString().trim();
            
            wednumofdaystext=String.valueOf(wednumofdays.getSelectedItemPosition());
            if (engagement.isChecked())
            {
               engagementid="1";
            }else
                engagementid="0";
            if (mehendi.isChecked())
            {
                mehendiid="1";
            }else
                mehendiid="0";
            if (sangeet.isChecked())
            {
                sangeetid="1";
            }else
                sangeetid="0";
            if (marriage.isChecked())
            {
                marriageid="1";
            }else
                marriageid="0";
            if (reception.isChecked())
            {
                receptionid="1";
            }else
                receptionid="0";
            if (others.isChecked())
            {
                othersid="1";
            }else
                othersid="0";

            if (TextUtils.isEmpty(areatext) || TextUtils.isEmpty(housenotext)||TextUtils.isEmpty(citytext)||TextUtils.isEmpty(pincodetext)||TextUtils.isEmpty(dostext)||wednumofdays.getSelectedItem().toString().equalsIgnoreCase("Select Number of Days")||!engagement.isChecked()&&!mehendi.isChecked()&&!sangeet.isChecked()&&!marriage.isChecked()&&!others.isChecked()&&!reception.isChecked())
            {
                if (TextUtils.isEmpty(housenotext) )
                {
                    housenoWP.setError("Enter House Number");
                    housenoWP.requestFocus();
                }else
                if (TextUtils.isEmpty(areatext) )
                {
                    areaWP.setError("Enter Area");
                    areaWP.requestFocus();
                }else

                if (TextUtils.isEmpty(citytext) )
                {
                    cityWP.setError("Enter City");
                    cityWP.requestFocus();
                }else
                if (TextUtils.isEmpty(pincodetext) )
                {
                    pincodeWP.setError("Enter Pincode");
                    pincodeWP.requestFocus();
                }else
                if (TextUtils.isEmpty(dostext) )
                {
                    dosWP.setError("Select Date");
                    dosWP.requestFocus();
                }else
                if (!engagement.isChecked()&&!mehendi.isChecked()&&!sangeet.isChecked()&&!marriage.isChecked()&&!others.isChecked()&&!reception.isChecked())
                {
                    Snackbar.make(weddingformlayout, "Select Event Type", Snackbar.LENGTH_LONG)
                            .show();
                }else
                if (wednumofdaystext.equalsIgnoreCase("Select Number of Days"))
                {
                    Snackbar.make(weddingformlayout, "Select  Number of Days", Snackbar.LENGTH_LONG)
                            .show();
                    wednumofdays.requestFocus();
                }

            }else
            {
                if(pincodetext.length()==6){
                    sendData();
                }
                else{
                    pincodeWP.setError("Enter Valid Pincode");
                    pincodeWP.requestFocus();
                }
            }

        }
        if (v.getId() == R.id.dosWP)
        {
            Calendar mcurrentDate=Calendar.getInstance();
            int mYear=mcurrentDate.get(Calendar.YEAR);
            int mMonth=mcurrentDate.get(Calendar.MONTH);
            int mDay=mcurrentDate.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog mDatePicker=new DatePickerDialog(this,R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    day=selectedday;
                    month=selectedmonth;
                    year=selectedyear;
                    dosWP.setText(getDate());
                }
            },mYear, mMonth, mDay);
            mDatePicker.getDatePicker().setMinDate(new Date().getTime());
            mDatePicker.setTitle("Select Date");
            mDatePicker.show();

            Button positiveButton = mDatePicker.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = mDatePicker.getButton(AlertDialog.BUTTON_NEGATIVE);

            positiveButton.setBackground(ColorDrawable.createFromPath("#fff"));
            positiveButton.setTextSize(15);
            positiveButton.setTextColor(Color.BLUE);

            negativeButton.setBackground(ColorDrawable.createFromPath("#fff"));
            negativeButton.setTextSize(15);
            negativeButton.setTextColor(Color.BLUE);
        }
    }
    private void sendData() {
        loading.show();
        java.sql.Date d=new java.sql.Date(year,month,day);
        final long time=d.getTime();
        wedeventtypetext=engagementid+mehendiid+sangeetid+marriageid+receptionid+othersid;
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.requestservice_WeddingShootForm, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if(response.toLowerCase().contains("error")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(WeddingShootForm.this);
                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Error in server. Try Again")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

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
                        //SUCCEESS
                        SharedPreferences sp;
                        sp = getSharedPreferences("NeuuGen_data", MODE_PRIVATE);
                        if (sp != null) {
                            final String email=sp.getString("email",null);
                            final String name=sp.getString("name",null).toUpperCase();
                            final String mobileno=sp.getString("mobileno",null).toUpperCase();
                            sendMail(email,name);
                            sendSMS(mobileno,name);
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(WeddingShootForm.this);
                        builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        builder.setMessage("Service Requested. Our Agent will contact you soon regarding same.")
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(WeddingShootForm.this);
                        builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        builder.setMessage(response)
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
                    AlertDialog alertDialog = new AlertDialog.Builder(WeddingShootForm.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(WeddingShootForm.this).create();
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
                params.put("mobileno", mobileno);
                params.put("serviceid", serviceId);
                params.put("city",city);
                params.put("houseno", housenotext);
                params.put("area", areatext);
                params.put("landmark", landmarktext);
                params.put("pincode", pincodetext);
                params.put("dateofservice", String.valueOf(time));
                params.put("noofdays", wednumofdaystext);
                params.put("eventtype",wedeventtypetext);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(WeddingShootForm.this);

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
        MySingleton.getInstance(WeddingShootForm.this).addToRequestQueue(stringRequest);
    }

    private void sendSMS(String mobileno,String name) {
        SendMsg sendMsg=new SendMsg();
        String msg="Dear "+name.trim()+", your Wedding Shoot service has been requested. Our Customer Executive will contact you soon.";
        sendMsg.SendCustomMsg(mobileno, msg, this, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(String response) {

            }

            @Override
            public void onVolleyError() {

            }
        });
    }

    private void sendMail(String email,String name) {
        SendMail sendMail=new SendMail();
        String subject="NG: Wedding Shoot Service Request";
        String body="Dear "+name+",<br><p>Thanks for choosing NEUUGEN. Your Wedding Shoot Service has been requested.</p><br>Our Customer Executive/Agent will reach out to you for the same. The Service Requested will be provided soon.";
        sendMail.sendMailmethod(email, subject, body, this, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String response) {

            }

            @Override
            public void onVolleyError() {
                loading.dismiss();
            }
        });
    }


}
