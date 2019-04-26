package peoplecitygroup.neuugen.HomeServices.EventServices;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

import peoplecitygroup.neuugen.R;

public class PreWedShootForm extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    String servicetypetext=null;
    AppCompatSpinner bothpvservice;
    TextInputEditText areaPWS,cityPWS,landmarkPWS,pincodePWS,dosPWS,housenoPWS;
    MaterialButton requestservicePWS;
    int day,year,month;
    String areatext,citytext,landmarktext,dostext,pincodetext,housenotext,bothpvservicetext;
    LinearLayout prewedshootlayout;

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
        setContentView(R.layout.activity_pre_wed_shoot_form);

        intent=getIntent();
        servicetypetext=intent.getStringExtra("servicetype");

        idLink();
        listenerLink();
        hideSoftKeyboard();

    }
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void idLink()
    {
        bothpvservice=findViewById(R.id.bothpvservice);
        dosPWS=findViewById(R.id.dosPWS);
        areaPWS=findViewById(R.id.areaPWS);
        cityPWS=findViewById(R.id.cityPWS);
        landmarkPWS=findViewById(R.id.landmarkPWS);
        pincodePWS=findViewById(R.id.pincodePWS);
        requestservicePWS=findViewById(R.id.requestservicePWS);
        housenoPWS=findViewById(R.id.housenoPWS);
        prewedshootlayout=findViewById(R.id.prewedshootlayout);

    }
    public void listenerLink()
    {
        requestservicePWS.setOnClickListener(this);
        dosPWS.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.requestservicePWS)
        {
            areatext=areaPWS.getText().toString().trim();
            housenotext=housenoPWS.getText().toString().trim();
            citytext=cityPWS.getText().toString().trim();
            landmarktext=landmarkPWS.getText().toString().trim();
            pincodetext=pincodePWS.getText().toString().trim();
            dostext=dosPWS.getText().toString().trim();
            bothpvservicetext=bothpvservice.getSelectedItem().toString().trim();

            if (TextUtils.isEmpty(areatext) || TextUtils.isEmpty(housenotext)||TextUtils.isEmpty(citytext)||TextUtils.isEmpty(pincodetext)||TextUtils.isEmpty(dostext)||bothpvservicetext.equalsIgnoreCase("Select Service Type"))
            {
                if (TextUtils.isEmpty(housenotext) )
                {
                    housenoPWS.setError("Enter House Number");
                    housenoPWS.requestFocus();
                }else
                if (TextUtils.isEmpty(areatext) )
                {
                    areaPWS.setError("Enter Area");
                    areaPWS.requestFocus();
                }else

                if (TextUtils.isEmpty(citytext) )
                {
                    cityPWS.setError("Enter City");
                    cityPWS.requestFocus();
                }else
                if (TextUtils.isEmpty(pincodetext) )
                {
                    pincodePWS.setError("Enter Pincode");
                    pincodePWS.requestFocus();
                }else
                if (TextUtils.isEmpty(dostext) )
                {
                    dosPWS.setError("Select Date");
                    dosPWS.requestFocus();
                }else
                if (bothpvservicetext.equalsIgnoreCase("Select Service Type"))
                {
                    Snackbar.make(prewedshootlayout, "Select Service Type", Snackbar.LENGTH_LONG)
                            .show();

                    bothpvservice.requestFocus();
                }
            }else
            {

            }

        }
        if (v.getId() == R.id.dosPWS)
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
                    dosPWS.setText(getDate());
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
}
