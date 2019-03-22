package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
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
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class EventArrangementForm extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String servicetypetext=null;
    AppCompatSpinner eventnumofdays;
    TextInputEditText areaEAF,cityEAF,landmarkEAF,pincodeEAF,dosEAF,housenoEAF;
    MaterialButton requestserviceEAF;
    int day,year,month;
    String areatext,citytext,landmarktext,dostext,pincodetext,housenotext,eventnumofdaystext;
    LinearLayout eventarrformlayout;
    
    AppCompatTextView eventarrtype;


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
        setContentView(R.layout.activity_event_arrangement_form);

        intent=getIntent();
        servicetypetext=intent.getStringExtra("servicetype");

        idLink();
        listenerLink();
        hideSoftKeyboard();

        eventarrtype.setText(servicetypetext);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void idLink()
    {
        
        dosEAF=findViewById(R.id.dosEAF);
        areaEAF=findViewById(R.id.areaEAF);
        cityEAF=findViewById(R.id.cityEAF);
        landmarkEAF=findViewById(R.id.landmarkEAF);
        pincodeEAF=findViewById(R.id.pincodeEAF);
        requestserviceEAF=findViewById(R.id.requestserviceEAF);
        housenoEAF=findViewById(R.id.housenoEAF);
        eventarrformlayout=findViewById(R.id.eventarrformlayout);
        eventnumofdays=findViewById(R.id.eventarrnumofdays);
        eventarrtype=findViewById(R.id.eventarrangementtype);

    }
    public void listenerLink()
    {
        requestserviceEAF.setOnClickListener(this);
        dosEAF.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.requestserviceEAF)
        {
            areatext=areaEAF.getText().toString().trim();
            housenotext=housenoEAF.getText().toString().trim();
            citytext=cityEAF.getText().toString().trim();
            landmarktext=landmarkEAF.getText().toString().trim();
            pincodetext=pincodeEAF.getText().toString().trim();
            dostext=dosEAF.getText().toString().trim();
            eventnumofdaystext=String.valueOf(eventnumofdays.getSelectedItemPosition());


            if (TextUtils.isEmpty(areatext) || TextUtils.isEmpty(housenotext)||TextUtils.isEmpty(citytext)||TextUtils.isEmpty(pincodetext)||TextUtils.isEmpty(dostext)||eventnumofdays.getSelectedItem().toString().equalsIgnoreCase("Select Number of Days"))
            {
                if (TextUtils.isEmpty(areatext) )
                {
                    areaEAF.setError("Enter Area");
                    areaEAF.requestFocus();
                }
                if (TextUtils.isEmpty(housenotext) )
                {
                    housenoEAF.setError("Enter House Number");
                    housenoEAF.requestFocus();
                }
                if (TextUtils.isEmpty(citytext) )
                {
                    cityEAF.setError("Enter City");
                    cityEAF.requestFocus();
                }
                if (TextUtils.isEmpty(pincodetext) )
                {
                    pincodeEAF.setError("Enter Pincode");
                    pincodeEAF.requestFocus();
                }
                if (TextUtils.isEmpty(dostext) )
                {
                    dosEAF.setError("Select Date");
                    dosEAF.requestFocus();
                }


                if (eventnumofdaystext.equalsIgnoreCase("Select Number of Days"))
                {
                    Snackbar.make(eventarrformlayout, "Select Number of Days", Snackbar.LENGTH_LONG)
                            .show();
                    eventnumofdays.requestFocus();
                }

            }else
            {

            }

        }
        if (v.getId() == R.id.dosEAF)
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
                    dosEAF.setText(getDate());
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
