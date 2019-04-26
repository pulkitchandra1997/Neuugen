package peoplecitygroup.neuugen.HomeServices.EventServices;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
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

public class EventPhotographyForm extends AppCompatActivity implements View.OnClickListener {
    Intent intent;
    String servicetypetext=null;
    AppCompatSpinner bothservicepv,eventnumofdays;
    TextInputEditText areaEP,cityEP,landmarkEP,pincodeEP,dosEP,housenoEP;
    MaterialButton requestserviceEP;
    int day,year,month;
    String areatext,citytext,landmarktext,dostext,pincodetext,housenotext,bothservicepvtext,eventnumofdaystext,birthdayid,corporateid,anniversaryid,picnicid,otherpartyid;
    LinearLayout eventphotolayout;

    AppCompatCheckBox corporate,birthday,anniversary,picnic,otherparty;

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
            eventnumofdaystext=eventnumofdays.getSelectedItem().toString().trim();
            if (birthday.isChecked())
            {
               birthdayid="1";
            }else
                birthdayid="0";

            if (corporate.isChecked())
            {
                corporateid="1";
            }else
                corporateid="0";
            if (anniversary.isChecked())
            {
                anniversaryid="1";
            }else
                anniversaryid="0";
            if (picnic.isChecked())
            {
                picnicid="1";
            }else
                picnicid="0";
            if (otherparty.isChecked())
            {
                otherpartyid="1";
            }else
                otherpartyid="0";

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
}
