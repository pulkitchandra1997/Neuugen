package peoplecitygroup.neuugen.HomeServices.HomeRenovationServices;

        import androidx.appcompat.app.AlertDialog;
        import androidx.appcompat.app.AppCompatActivity;
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

public class HomeRenovationForm extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String servicetext=null,serviceId=null;
    AppCompatTextView renovationformhead;
    AppCompatSpinner plumbingtype,carpentrytype,electriciantype;
    TextInputEditText areaHR,cityHR,landmarkHR,pincodeHR,dos,housenoHR;
    MaterialButton requestserviceHR;
    int day,year,month;
    String city="";
    String mobileno="";
    String areatext,landmarktext,dostext,pincodetext,housenotext,servicetypetext;
    LinearLayout renovationlayout;
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
        setContentView(R.layout.activity_home_renovation_form);

        intent=getIntent();
        servicetext=intent.getStringExtra("servicetext");
        serviceId=intent.getStringExtra("serviceid");

        idLink();
        listenerLink();
        hideSoftKeyboard();
        SharedPreferences sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            city = sp.getString("city", null);
            mobileno=sp.getString("mobileno",null);
        }
        cityHR.setText(city);
        cityHR.setEnabled(false);
        if (servicetext.equalsIgnoreCase("Plumbing Service"))
        {
            plumbingtype.setVisibility(View.VISIBLE);
            carpentrytype.setVisibility(View.GONE);
            electriciantype.setVisibility(View.GONE);
        }else
        if (servicetext.equalsIgnoreCase("Carpentry Service"))
        {
            plumbingtype.setVisibility(View.GONE);
            carpentrytype.setVisibility(View.VISIBLE);
            electriciantype.setVisibility(View.GONE);
        }else
        if (servicetext.equalsIgnoreCase("Electrician Service"))
        {
            plumbingtype.setVisibility(View.GONE);
            carpentrytype.setVisibility(View.GONE);
            electriciantype.setVisibility(View.VISIBLE);
        }
        renovationformhead.setText(servicetext);
        loading = new ProgressDialog(HomeRenovationForm.this,R.style.AppCompatAlertDialogStyle);
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
        renovationformhead=findViewById(R.id.renovationformhead);
        plumbingtype=findViewById(R.id.plumbingtype);
        electriciantype=findViewById(R.id.electriciantype);
        carpentrytype=findViewById(R.id.carpentrytype);
        dos=findViewById(R.id.dosHR);
        areaHR=findViewById(R.id.areaHR);
        cityHR=findViewById(R.id.cityHR);
        landmarkHR=findViewById(R.id.landmarkHR);
        pincodeHR=findViewById(R.id.pincodeHR);
        requestserviceHR=findViewById(R.id.requestserviceHR);
        housenoHR=findViewById(R.id.housenoHR);
        renovationlayout=findViewById(R.id.renovationlayout);

    }
    public void listenerLink()
    {
        requestserviceHR.setOnClickListener(this);
        dos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dosHR) {
            Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog mDatePicker = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                    day = selectedday;
                    month = selectedmonth;
                    year = selectedyear;
                    dos.setText(getDate());
                }
            }, mYear, mMonth, mDay);
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
        if (v.getId() == R.id.requestserviceHR) {
            areatext = areaHR.getText().toString().trim();
            housenotext = housenoHR.getText().toString().trim();
            landmarktext = landmarkHR.getText().toString().trim();
            pincodetext = pincodeHR.getText().toString().trim();
            dostext = dos.getText().toString().trim();
            if (servicetext.equalsIgnoreCase("Plumbing Service")) {
                servicetypetext = plumbingtype.getSelectedItem().toString().trim();
            } else if (servicetext.equalsIgnoreCase("Carpentry Service")) {
                servicetypetext = carpentrytype.getSelectedItem().toString().trim();
            } else if (servicetext.equalsIgnoreCase("Electrician Service")) {
                servicetypetext = electriciantype.getSelectedItem().toString().trim();
            }
            if (TextUtils.isEmpty(areatext) || TextUtils.isEmpty(housenotext) || TextUtils.isEmpty(pincodetext) || TextUtils.isEmpty(dostext) || servicetext.equalsIgnoreCase("Plumbing Service") && servicetypetext.equalsIgnoreCase("Select Service Type") || servicetext.equalsIgnoreCase("Carpentry Service") && servicetypetext.equalsIgnoreCase("Select Service Type") || servicetext.equalsIgnoreCase("Electrician Service") && servicetypetext.equalsIgnoreCase("Select Service Type")) {
                if (TextUtils.isEmpty(housenotext)) {
                    housenoHR.setError("Enter House Number");
                    housenoHR.requestFocus();
                } else if (TextUtils.isEmpty(areatext)) {
                    areaHR.setError("Enter Area");
                    areaHR.requestFocus();
                } else if (TextUtils.isEmpty(pincodetext)) {
                    pincodeHR.setError("Enter Pincode");
                    pincodeHR.requestFocus();
                } else if (TextUtils.isEmpty(dostext)) {
                    dos.setError("Select Date");
                    dos.requestFocus();
                } else if (servicetext.equalsIgnoreCase("Carpentry Service") && servicetypetext.equalsIgnoreCase("Select Service Type")) {
                    Snackbar.make(renovationlayout, "Select Service Type", Snackbar.LENGTH_LONG)
                            .show();

                    carpentrytype.requestFocus();
                } else if (servicetext.equalsIgnoreCase("Plumbing Service") && servicetypetext.equalsIgnoreCase("Select Service Type")) {
                    Snackbar.make(renovationlayout, "Select Service Type", Snackbar.LENGTH_LONG)
                            .show();

                    plumbingtype.requestFocus();
                } else if (servicetext.equalsIgnoreCase("Electrician Service") && servicetypetext.equalsIgnoreCase("Select Service Type")) {
                    Snackbar.make(renovationlayout, "Select Service Type", Snackbar.LENGTH_LONG)
                            .show();

                    electriciantype.requestFocus();
                }

            } else {
                if (pincodetext.length() == 6) {
                    sendData();
                } else {
                    pincodeHR.setError("Enter Valid Pincode");
                    pincodeHR.requestFocus();
                }
            }

        }
    }
    private void sendData() {
        loading.show();
        java.sql.Date d=new java.sql.Date(year,month,day);
        final long time=d.getTime();
        StringRequest stringRequest=new StringRequest(Request.Method.POST, UrlNeuugen.requestservice_Homerenovation, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if(response.toLowerCase().contains("error")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeRenovationForm.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeRenovationForm.this);
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
                        AlertDialog.Builder builder = new AlertDialog.Builder(HomeRenovationForm.this);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(HomeRenovationForm.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                else {
                    AlertDialog alertDialog = new AlertDialog.Builder(HomeRenovationForm.this).create();
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
                params.put("servicetype", servicetypetext);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeRenovationForm.this);

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
        MySingleton.getInstance(HomeRenovationForm.this).addToRequestQueue(stringRequest);
    }
    private void sendSMS(String mobileno,String name) {
        SendMsg sendMsg=new SendMsg();
        String msg="Dear "+name.trim()+", your "+servicetext.trim()+" has been requested. Our Customer Executive will contact you soon.";
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
        String subject="NG: "+servicetext.trim()+" Request";
        String body="Dear "+name+",<br><p>Thanks for choosing NEUUGEN. Your "+servicetext.trim()+" has been requested.</p><br>Our Customer Executive/Agent will reach out to you for the same. The Service Requested will be provided soon.";
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
