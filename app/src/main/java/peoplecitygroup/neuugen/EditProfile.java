package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.app.ActivityOptions;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
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

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.common_req_files.Validation;
import peoplecitygroup.neuugen.properties.RentHouses;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    androidx.appcompat.widget.AppCompatTextView backtoprofile;
    MaterialButton update;
    TextInputEditText name,city,email,address,state,pincode,dob;
    String nametext,citytext,emailtext,addresstext,statetext,pincodetext,dobtext,gendertext;
    int day,year,month;
    RadioGroup gender;
    AppCompatRadioButton male,female;

    ProgressDialog loading = null;
    SharedPreferences sp;
    JSONObject jsonObject= new JSONObject();

    String uniqueid=null;
    SharedPreferences.Editor se;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        idLink();
        listenerLink();
        hideSoftKeyboard();
        loading = new ProgressDialog(EditProfile.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        se=sp.edit();
        fill();


        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backtoprofile.setTypeface(font);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void idLink()
    {
        backtoprofile=findViewById(R.id.backtoprofile);
        name=findViewById(R.id.name);
        city=findViewById(R.id.city);
        email=findViewById(R.id.email);
        address=findViewById(R.id.address);
        state=findViewById(R.id.state);
        pincode=findViewById(R.id.pincode);
        dob=findViewById(R.id.dob);
        gender=findViewById(R.id.gender);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        update=findViewById(R.id.update);
    }
    public void listenerLink()
    {
        backtoprofile.setOnClickListener(this);
        update.setOnClickListener(this);
        dob.setOnClickListener(this);
    }

    public void fill()
    {
        name.setText(sp.getString("name","name"));
        email.setText(sp.getString("email","email"));
        city.setText(sp.getString("city","city"));
        dob.setText(sp.getString("dob","dob"));
        if (sp.getString("address","address").equalsIgnoreCase("null"))
        {
        }else
        {
            address.setText(sp.getString("address",""));
        }
        if (sp.getString("state","state").equalsIgnoreCase("null"))
        {
        }
        else
        {
            state.setText(sp.getString("state", " "));
        }
        if (sp.getString("pincode","pincode").equalsIgnoreCase("null"))
        {
        }
        else
        {
            pincode.setText(sp.getString("pincode"," "));
        }
        if (sp.getString("dob","dob").equalsIgnoreCase("null"))
        {
            dob.setText("");
        }
        else
        {
            dob.setText(sp.getString("dob",""));
        }
    }

    public String getDate(){
        StringBuilder builder=new StringBuilder();
        builder.append(day+"/");
        builder.append((month + 1)+"/");//month is 0 based
        builder.append(year);
        return builder.toString();
    }



    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backtoprofile)
        {
            onBackPressed();
        }
        if (v.getId()==R.id.update)
        {
            addresstext=address.getText().toString().trim();
            pincodetext=pincode.getText().toString().trim();
            citytext=city.getText().toString().trim();
            nametext=name.getText().toString().trim();
            emailtext=email.getText().toString().trim();
            statetext=state.getText().toString().trim();
            dobtext=dob.getText().toString().trim();
            if (male.isChecked())
            {
                gendertext=male.getText().toString();
            }
            else if (female.isChecked())
            {
                gendertext=female.getText().toString();
            }

            if (TextUtils.isEmpty(nametext) || TextUtils.isEmpty(emailtext) || TextUtils.isEmpty(dobtext)||TextUtils.isEmpty(pincodetext)||TextUtils.isEmpty(statetext)||TextUtils.isEmpty(citytext)||TextUtils.isEmpty(addresstext)||!male.isChecked() && !female.isChecked())
            {
                if(TextUtils.isEmpty(nametext)){
                    name.setError("Enter Name");
                    name.requestFocus();
                }else
                if (TextUtils.isEmpty(emailtext)) {
                    email.setError("Enter Email");
                    email.requestFocus();
                }else
                if (TextUtils.isEmpty(pincodetext)) {
                    pincode.setError("Enter phone number");
                    pincode.requestFocus();
                }else
                if (TextUtils.isEmpty(dobtext)) {
                    dob.setError("Select DOB");
                    dob.requestFocus();
                }else
                if (TextUtils.isEmpty(addresstext)) {
                    address.setError("Enter Address");
                    address.requestFocus();
                }else
                if (TextUtils.isEmpty(citytext)) {
                    city.setError("Enter City");
                    city.requestFocus();
                }else
                if (TextUtils.isEmpty(statetext)) {
                    state.setError("Enter State");
                    state.requestFocus();
                }
                else
                if (!male.isChecked()&& !female.isChecked())
                {
                    Snackbar.make(findViewById(R.id.editlayout), "Please Select Gender", Snackbar.LENGTH_LONG)
                            .show();
                    gender.requestFocus();
                }
            } else
            if(!Validation.isValidName(nametext)||!Validation.isValidEmail(emailtext)){

                if(!Validation.isValidPhone(emailtext)){
                    email.setError("Enter Valid Email Id");
                    email.requestFocus();
                }else
                if(!Validation.isValidName(nametext)){
                    name.setError("Enter Valid Name");
                    name.requestFocus();
                }
            }else
            {
                toserver();
            }


        }
        if (v.getId() == R.id.dob)
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
                    dob.setText(getDate());
                }
            },mYear, mMonth, mDay);
            mDatePicker.getDatePicker().setMaxDate(new Date().getTime());
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
    private boolean createJsonObject() {
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            try {
                jsonObject.put("mobileno",sp.getString("mobileno",null));
                jsonObject.put("email",emailtext);
                jsonObject.put("name",nametext);
                jsonObject.put("address",addresstext);
                jsonObject.put("city",citytext);
                jsonObject.put("pincode",pincodetext);
                jsonObject.put("state",statetext);
                jsonObject.put("dob",dobtext);
                jsonObject.put("gender",gendertext);
                return true;
            } catch (Exception e) {
                Log.i("errorrent",e.getMessage());
                return false;

            }
        }
        return false;
    }

    private void toSharedPreferences() {
        SharedPreferences sp;
        SharedPreferences.Editor se;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        se=sp.edit();
        se.putString("name", nametext);
        se.putString("email", emailtext);
        se.putString("city", citytext);
        se.putString("address", addresstext);
        se.putString("state", statetext);
        se.putString("pincode", pincodetext);
        se.putString("gender", gendertext);
        se.putString("dob", dobtext);
        se.commit();
    }

    public void toserver() {
        loading.show();
        if (createJsonObject()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_EditProfile, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toLowerCase().contains("error")) {
                        loading.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(EditProfile.this).create();
                        alertDialog.setMessage(response);
                        alertDialog.setButton(BUTTON_POSITIVE,"Ok", (DialogInterface.OnClickListener) null);
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                        Button positiveButton = alertDialog.getButton(BUTTON_POSITIVE);
                        positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

                    } else {
                        if(response.toLowerCase().contains("success")){
                            uniqueid=response.substring(7);
                            loading.dismiss();
                            toSharedPreferences();
                            AlertDialog alertDialog = new AlertDialog.Builder(EditProfile.this).create();
                            alertDialog.setMessage(Html.fromHtml("<b>Your profile has been updated successfully.</b>"));
                            alertDialog.setIcon(R.mipmap.ic_launcher_round);
                            alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                            alertDialog.show();
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(EditProfile.this, UserMainActivity.class);
                                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                                        ActivityOptions options = ActivityOptions.makeCustomAnimation(EditProfile.this, R.anim.fade_in, R.anim.fade_out);
                                        startActivity(intent, options.toBundle());
                                    } else {
                                        startActivity(intent);
                                    }
                                    finish();
                                }
                            },2000);
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
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
                    if (!haveConnectedWifi && !haveConnectedMobile) {
                        AlertDialog alertDialog = new AlertDialog.Builder(EditProfile.this).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(EditProfile.this).create();
                        alertDialog.setMessage(error.toString());
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    Log.i("dataflag",jsonObject.toString());
                    params.put("data", jsonObject.toString());
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);
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
                    Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                }
            });
            MySingleton.getInstance(EditProfile.this).addToRequestQueue(stringRequest);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(EditProfile.this);

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error in connection.")
                    .setPositiveButton("OK", null)
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
    }
}
