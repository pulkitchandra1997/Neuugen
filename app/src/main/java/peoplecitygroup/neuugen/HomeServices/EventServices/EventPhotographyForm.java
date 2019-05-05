package peoplecitygroup.neuugen.HomeServices.EventServices;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;

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
import android.widget.RadioGroup;

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
import peoplecitygroup.neuugen.service.MySingleton;
import peoplecitygroup.neuugen.service.SendMail;
import peoplecitygroup.neuugen.service.SendMsg;
import peoplecitygroup.neuugen.service.UrlNeuugen;
import peoplecitygroup.neuugen.service.VolleyCallback;

public class EventPhotographyForm extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    String servicetypetext=null;
    AppCompatSpinner bothservicepv,eventnumofdays;
    TextInputEditText areaEP,cityEP,landmarkEP,pincodeEP,dosEP,housenoEP;
    MaterialButton requestserviceEP;
    int day,year,month;
    String areatext,citytext,landmarktext,dostext,pincodetext,housenotext,bothservicepvtext,eventtypetext,eventnumofdaystext,birthdayid,corporateid,anniversaryid,picnicid,otherpartyid,serviceId;
    LinearLayout eventphotolayout;
    String city="";
    String mobileno="";
    ProgressDialog loading = null;
    RadioGroup eventtypeoptions;
    AppCompatRadioButton corporate,birthday,anniversary,picnic,otherparty;

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
        setContentView(R.layout.activity_event_photography_form);


        intent=getIntent();
        servicetypetext=intent.getStringExtra("servicetype");
        serviceId=intent.getStringExtra("serviceid");

        idLink();
        listenerLink();
        hideSoftKeyboard();
        SharedPreferences sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            city = sp.getString("city", null);
            mobileno=sp.getString("mobileno",null);
        }
        cityEP.setText(city);
        cityEP.setEnabled(false);
        loading = new ProgressDialog(EventPhotographyForm.this,R.style.AppCompatAlertDialogStyle);
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
        bothservicepv=findViewById(R.id.bothservicepv);
        dosEP=findViewById(R.id.dosEP);
        areaEP=findViewById(R.id.areaEP);
        cityEP=findViewById(R.id.cityEP);
        landmarkEP=findViewById(R.id.landmarkEP);
        pincodeEP=findViewById(R.id.pincodeEP);
        requestserviceEP=findViewById(R.id.requestserviceEP);
        housenoEP=findViewById(R.id.housenoEP);
        eventphotolayout=findViewById(R.id.eventphotolayout);
        eventnumofdays=findViewById(R.id.eventnumofdays);
        birthday=findViewById(R.id.birthday);
        anniversary=findViewById(R.id.anniversary);
        picnic=findViewById(R.id.picnic);
        corporate=findViewById(R.id.corporate);
        otherparty=findViewById(R.id.otherparty);
        eventtypeoptions=findViewById(R.id.eventtypeoptions);

    }
    public void listenerLink()
    {
        requestserviceEP.setOnClickListener(this);
        dosEP.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.requestserviceEP)
        {
            areatext=areaEP.getText().toString().trim();
            housenotext=housenoEP.getText().toString().trim();
            citytext=cityEP.getText().toString().trim();
            landmarktext=landmarkEP.getText().toString().trim();
            pincodetext=pincodeEP.getText().toString().trim();
            dostext=dosEP.getText().toString().trim();
            bothservicepvtext=bothservicepv.getSelectedItem().toString().trim();
            eventnumofdaystext=String.valueOf(eventnumofdays.getSelectedItemPosition());;
            if (birthday.isChecked())
            {
               eventtypetext="Birthday";
            }else
            if (corporate.isChecked())
            {
                eventtypetext="Corporate";
            }else
            if (anniversary.isChecked())
            {
                eventtypetext="Anniversary";
            }else
            if (picnic.isChecked())
            {
                eventtypetext="Picnic";
            }else
            if (otherparty.isChecked())
            {
                eventtypetext="Other Parties";
            }
            if (TextUtils.isEmpty(areatext) || TextUtils.isEmpty(housenotext)||TextUtils.isEmpty(citytext)||TextUtils.isEmpty(pincodetext)||TextUtils.isEmpty(dostext)||bothservicepvtext.equalsIgnoreCase("Select Service Type")||eventnumofdaystext.equalsIgnoreCase("Select Number of Days")||!birthday.isChecked()&&!corporate.isChecked()&&!anniversary.isChecked()&&!picnic.isChecked()&&!otherparty.isChecked())
            {
                if (TextUtils.isEmpty(housenotext) )
                {
                    housenoEP.setError("Enter House Number");
                    housenoEP.requestFocus();
                }else
                if (TextUtils.isEmpty(areatext) )
                {
                    areaEP.setError("Enter Area");
                    areaEP.requestFocus();
                }else

                if (TextUtils.isEmpty(citytext) )
                {
                    cityEP.setError("Enter City");
                    cityEP.requestFocus();
                }else
                if (TextUtils.isEmpty(pincodetext) )
                {
                    pincodeEP.setError("Enter Pincode");
                    pincodeEP.requestFocus();
                }else
                if (TextUtils.isEmpty(dostext) )
                {
                    dosEP.setError("Select Date");
                    dosEP.requestFocus();
                }else
                if (bothservicepvtext.equalsIgnoreCase("Select Service Type"))
                {
                    Snackbar.make(eventphotolayout, "Select Service Type", Snackbar.LENGTH_LONG)
                            .show();

                    bothservicepv.requestFocus();
                }else
                if (!birthday.isChecked()&&!corporate.isChecked()&&!anniversary.isChecked()&&!picnic.isChecked()&&!otherparty.isChecked())
                {
                    Snackbar.make(eventphotolayout, "Select Event Type", Snackbar.LENGTH_LONG)
                            .show();
                }else
                if (eventnumofdaystext.equalsIgnoreCase("Select Number of Days"))
                {
                    Snackbar.make(eventphotolayout, "Select Number of Days", Snackbar.LENGTH_LONG)
                            .show();
                    eventnumofdays.requestFocus();
                }

            }else
            {
                if(pincodetext.length()==6){
                    sendData();
                }
                else{
                    pincodeEP.setError("Enter Valid Pincode");
                    pincodeEP.requestFocus();
                }
            }

        }
        if (v.getId() == R.id.dosEP)
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
                    dosEP.setText(getDate());
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
/*        final String eventtype=corporateid+picnicid+birthdayid+anniversaryid+otherpartyid;*/
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.requestservice_EventPhotographyForm, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if(response.toLowerCase().contains("error")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EventPhotographyForm.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(EventPhotographyForm.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(EventPhotographyForm.this);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(EventPhotographyForm.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(EventPhotographyForm.this).create();
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
                params.put("noofdays", eventnumofdaystext);
                params.put("servicetype", servicetypetext);
                params.put("eventtype",eventtypetext);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(EventPhotographyForm.this);

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
        MySingleton.getInstance(EventPhotographyForm.this).addToRequestQueue(stringRequest);
    }
    private void sendSMS(String mobileno,String name) {
        SendMsg sendMsg=new SendMsg();
        String msg="Dear "+name.trim()+", your "+servicetypetext.trim()+" service has been requested. Our Customer Executive will contact you soon.";
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
        String subject="NG: "+servicetypetext.trim()+" Service Request";
        String body="Dear "+name+",<br><p>Thanks for choosing NEUUGEN. Your "+servicetypetext.trim()+" Service has been requested.</p><br>Our Customer Executive/Agent will reach out to you for the same. The Service Requested will be provided soon.";
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
