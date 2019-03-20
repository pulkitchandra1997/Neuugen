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

public class HomeRenovationForm extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String servicetext=null;
    AppCompatTextView renovationformhead;
    AppCompatSpinner plumbingtype,carpentrytype,electriciantype;
    TextInputEditText areaHR,cityHR,landmarkHR,pincodeHR,dos,housenoHR;
    MaterialButton requestserviceHR;
    int day,year,month;
    String areatext,citytext,landmarktext,dostext,pincodetext,housenotext,servicetypetext;
    LinearLayout renovationlayout;

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

        idLink();
        listenerLink();
        hideSoftKeyboard();

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
        if (v.getId() == R.id.dos)
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
                    dos.setText(getDate());
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
        if (v.getId()==R.id.requestserviceHR)
        {
            areatext=areaHR.getText().toString().trim();
            housenotext=housenoHR.getText().toString().trim();
            citytext=cityHR.getText().toString().trim();
            landmarktext=landmarkHR.getText().toString().trim();
            pincodetext=pincodeHR.getText().toString().trim();
            dostext=dos.getText().toString().trim();
            if (servicetext.equalsIgnoreCase("Plumbing Service"))
            {
                servicetypetext=plumbingtype.getSelectedItem().toString().trim();
            }else
            if (servicetext.equalsIgnoreCase("Carpentry Service"))
            {
                servicetypetext=carpentrytype.getSelectedItem().toString().trim();
            }else
            if (servicetext.equalsIgnoreCase("Electrician Service"))
            {
                servicetypetext=electriciantype.getSelectedItem().toString().trim();
            }
            if (TextUtils.isEmpty(areatext) || TextUtils.isEmpty(housenotext)||TextUtils.isEmpty(citytext)||TextUtils.isEmpty(pincodetext)||TextUtils.isEmpty(dostext)||servicetext.equalsIgnoreCase("Plumbing Service")&&servicetypetext.equalsIgnoreCase("Select Service Type")||servicetext.equalsIgnoreCase("Carpentry Service")&&servicetypetext.equalsIgnoreCase("Select Service Type")||servicetext.equalsIgnoreCase("Electrician Service")&&servicetypetext.equalsIgnoreCase("Select Service Type"))
            {
                if (TextUtils.isEmpty(areatext) )
                {
                    areaHR.setError("Enter Area");
                    areaHR.requestFocus();
                }
                if (TextUtils.isEmpty(housenotext) )
                {
                    housenoHR.setError("Enter House Number");
                    housenoHR.requestFocus();
                }
                if (TextUtils.isEmpty(citytext) )
                {
                    cityHR.setError("Enter City");
                    cityHR.requestFocus();
                }
                if (TextUtils.isEmpty(pincodetext) )
                {
                    pincodeHR.setError("Enter Pincode");
                    pincodeHR.requestFocus();
                }
                if (TextUtils.isEmpty(dostext) )
                {
                    dos.setError("Select Date");
                    dos.requestFocus();
                }
                if (servicetext.equalsIgnoreCase("Carpentry Service")&&servicetypetext.equalsIgnoreCase("Select Service Type"))
                {
                    Snackbar.make(renovationlayout, "Select Service Type", Snackbar.LENGTH_LONG)
                            .show();

                    carpentrytype.requestFocus();
                }
                if (servicetext.equalsIgnoreCase("Plumbing Service")&&servicetypetext.equalsIgnoreCase("Select Service Type"))
                {
                    Snackbar.make(renovationlayout, "Select Service Type", Snackbar.LENGTH_LONG)
                            .show();

                    plumbingtype.requestFocus();
                }
                if (servicetext.equalsIgnoreCase("Electrician Service")&&servicetypetext.equalsIgnoreCase("Select Service Type"))
                {
                    Snackbar.make(renovationlayout, "Select Service Type", Snackbar.LENGTH_LONG)
                            .show();

                    electriciantype.requestFocus();
                }

            }else
            {

            }

        }
    }
}
