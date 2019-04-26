package peoplecitygroup.neuugen.HomeServices.SalonServices;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.MySingleton;
import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.service.SendMail;
import peoplecitygroup.neuugen.service.SendMsg;
import peoplecitygroup.neuugen.service.UrlNeuugen;
import peoplecitygroup.neuugen.service.VolleyCallback;

public class SalonServiceForm extends AppCompatActivity implements View.OnClickListener {

    String servicetypetext=null,servicepricetext=null,areatext,citytext,landmarktext,dostext,pincodetext,housenotext,serviceId;
    Intent intent;
    TextInputEditText areaSS,citySS,landmarkSS,pincodeSS,salondos,housenoSS;
    AppCompatTextView servicetype,serviceprice;
    MaterialButton requestservice;
    int day,year,month;
    String city="";
    String mobileno="";
    ProgressDialog loading = null;

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
        setContentView(R.layout.activity_salon_service_form);

        intent=getIntent();
        servicetypetext=intent.getStringExtra("servicetype");
        servicepricetext=intent.getStringExtra("serviceprice");
        serviceId=intent.getStringExtra("serviceid");
        idLink();
        listenerLink();
        hideSoftKeyboard();
        SharedPreferences sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            city = sp.getString("city", null);
            mobileno=sp.getString("mobileno",null);
        }
        citySS.setText(city);
        citySS.setEnabled(false);
        servicetype.setText(servicetypetext);
        serviceprice.setText(servicepricetext);
        loading = new ProgressDialog(SalonServiceForm.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
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
        salondos=findViewById(R.id.salondos);
        areaSS=findViewById(R.id.areaSS);
        citySS=findViewById(R.id.citySS);
        landmarkSS=findViewById(R.id.landmarkSS);
        pincodeSS=findViewById(R.id.pincodeSS);
        requestservice=findViewById(R.id.requestserviceSS);
        housenoSS=findViewById(R.id.housenoSS);
        serviceprice=findViewById(R.id.serviceprice);
        servicetype=findViewById(R.id.servicetype);
        

    }
    public void listenerLink()
    {
        requestservice.setOnClickListener(this);
        salondos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.requestserviceSS)
        {
            areatext=areaSS.getText().toString().trim();
            housenotext=housenoSS.getText().toString().trim();
            citytext=citySS.getText().toString().trim();
            landmarktext=landmarkSS.getText().toString().trim();
            pincodetext=pincodeSS.getText().toString().trim();
            dostext=salondos.getText().toString().trim();
            
            if (TextUtils.isEmpty(areatext) || TextUtils.isEmpty(housenotext)||TextUtils.isEmpty(citytext)||TextUtils.isEmpty(pincodetext)||TextUtils.isEmpty(dostext))
            {
                if (TextUtils.isEmpty(housenotext) )
                {
                    housenoSS.setError("Enter House Number");
                    housenoSS.requestFocus();
                }else
                if (TextUtils.isEmpty(areatext) )
                {
                    areaSS.setError("Enter Area");
                    areaSS.requestFocus();
                }else

                if (TextUtils.isEmpty(citytext) )
                {
                    citySS.setError("Enter City");
                    citySS.requestFocus();
                }else
                if (TextUtils.isEmpty(pincodetext) )
                {
                    pincodeSS.setError("Enter Pincode");
                    pincodeSS.requestFocus();
                }else
                if (TextUtils.isEmpty(dostext) )
                {
                    salondos.setError("Select Date");
                    salondos.requestFocus();
                }

            }else
            {
                if(pincodetext.length()==6){
                    sendData();
                }
                else{
                    pincodeSS.setError("Enter Valid Pincode");
                    pincodeSS.requestFocus();
                }
            }

        }
        if (v.getId() == R.id.salondos)
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
                    salondos.setText(getDate());
                }
            },mYear, mMonth, mDay);
            mDatePicker.getDatePicker().setMinDate(new Date().getTime());
            final Calendar maxcalendar = Calendar.getInstance();
            maxcalendar.add(Calendar.DATE,90);
            mDatePicker.getDatePicker().setMaxDate(maxcalendar.getTimeInMillis());                              //3 MONTHS
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
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.requestservice_Salon, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if(response.toLowerCase().contains("error")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SalonServiceForm.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(SalonServiceForm.this);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(SalonServiceForm.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SalonServiceForm.this).create();
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
                params.put("city",citytext);
                params.put("houseno", mobileno);
                params.put("area", mobileno);
                params.put("landmark", mobileno);
                params.put("pincode", mobileno);
                params.put("dateofservice", dostext);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(SalonServiceForm.this);

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
        MySingleton.getInstance(SalonServiceForm.this).addToRequestQueue(stringRequest);
    }

    private void sendSMS(String mobileno,String name) {
        SendMsg sendMsg=new SendMsg();
        String msg="Dear "+name.trim()+", your salon service has been requested. Our Customer Executive will contact you soon.";
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
            String subject="NG: Salon Service Request";
            String body="Dear "+name+",<br><p>Thanks for choosing NEUUGEN. Your Salon Service has been requested.</p><br>Our Customer Executive/Agent will reach out to you for the same. The Service Requested will be provided soon.";
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
