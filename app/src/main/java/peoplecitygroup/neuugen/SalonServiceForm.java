package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

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

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class SalonServiceForm extends AppCompatActivity implements View.OnClickListener {

    String servicetypetext=null,servicepricetext=null,areatext,citytext,landmarktext,dostext,pincodetext,housenotext;
    Intent intent;
    TextInputEditText areaSS,citySS,landmarkSS,pincodeSS,salondos,housenoSS;
    AppCompatTextView servicetype,serviceprice;
    MaterialButton requestservice;
    int day,year,month;


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

        idLink();
        listenerLink();
        hideSoftKeyboard();

        servicetype.setText(servicetypetext);
        serviceprice.setText(servicepricetext);

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
