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

public class ApplianceRepairform extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String servicetext=null;
    AppCompatTextView applianceformhead;
    AppCompatSpinner repairappliances,installappliances;
    TextInputEditText areaAS,cityAS,landmarkAS,pincodeAS,dos,housenoAS;
    MaterialButton requestservice;
    int day,year,month;
    String areatext,citytext,landmarktext,dostext,pincodetext,housenotext,appliancetext;
    LinearLayout appliancelayout;


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
        setContentView(R.layout.activity_appliance_repairform);

        intent=getIntent();
        servicetext=intent.getStringExtra("servicetext");

        idLink();
        listenerLink();
        hideSoftKeyboard();

        if (servicetext.equalsIgnoreCase("Repairing Service"))
        {
            repairappliances.setVisibility(View.VISIBLE);
            installappliances.setVisibility(View.GONE);
        }else
        if (servicetext.equalsIgnoreCase("Installation Service"))
        {
            installappliances.setVisibility(View.VISIBLE);
            repairappliances.setVisibility(View.GONE);
        }

        applianceformhead.setText(servicetext);

    }


    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void idLink()
    {
        applianceformhead=findViewById(R.id.applianceformhead);
        repairappliances=findViewById(R.id.repairappliances);
        installappliances=findViewById(R.id.installappliances);
        dos=findViewById(R.id.dos);
        areaAS=findViewById(R.id.areaAS);
        cityAS=findViewById(R.id.cityAS);
        landmarkAS=findViewById(R.id.landmarkAS);
        pincodeAS=findViewById(R.id.pincodeAS);
        requestservice=findViewById(R.id.requestservice);
        housenoAS=findViewById(R.id.housenoAS);
        appliancelayout=findViewById(R.id.appliancelayout);

    }
    public void listenerLink()
    {
        requestservice.setOnClickListener(this);
        dos.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.requestservice)
        {
            areatext=areaAS.getText().toString().trim();
            housenotext=housenoAS.getText().toString().trim();
            citytext=cityAS.getText().toString().trim();
            landmarktext=landmarkAS.getText().toString().trim();
            pincodetext=pincodeAS.getText().toString().trim();
            dostext=dos.getText().toString().trim();
            if (servicetext.equalsIgnoreCase("Repairing Service"))
            {
                appliancetext=repairappliances.getSelectedItem().toString().trim();
            }else
            if (servicetext.equalsIgnoreCase("Installation Service"))
            {
                appliancetext=installappliances.getSelectedItem().toString().trim();
            }
            if (TextUtils.isEmpty(areatext) || TextUtils.isEmpty(housenotext)||TextUtils.isEmpty(citytext)||TextUtils.isEmpty(pincodetext)||TextUtils.isEmpty(dostext)||servicetext.equalsIgnoreCase("Repairing Service")&&appliancetext.equalsIgnoreCase("Select Appliance Type")||servicetext.equalsIgnoreCase("Installation Service")&&appliancetext.equalsIgnoreCase("Select Appliance Type"))
            {
                if (TextUtils.isEmpty(areatext) )
                {
                    areaAS.setError("Enter Area");
                    areaAS.requestFocus();
                }
                if (TextUtils.isEmpty(housenotext) )
                {
                    housenoAS.setError("Enter House Number");
                    housenoAS.requestFocus();
                }
                if (TextUtils.isEmpty(citytext) )
                {
                    cityAS.setError("Enter City");
                    cityAS.requestFocus();
                }
                if (TextUtils.isEmpty(pincodetext) )
                {
                    pincodeAS.setError("Enter Pincode");
                    pincodeAS.requestFocus();
                }
                if (servicetext.equalsIgnoreCase("Repairing Service")&&appliancetext.equalsIgnoreCase("Select Appliance Type"))
                {
                    Snackbar.make(appliancelayout, "Select Appliance Type", Snackbar.LENGTH_LONG)
                            .show();

                    repairappliances.requestFocus();
                }
                if (servicetext.equalsIgnoreCase("Installation Service")&&appliancetext.equalsIgnoreCase("Select Appliance Type"))
                {
                    Snackbar.make(appliancelayout, "Select Appliance Type", Snackbar.LENGTH_LONG)
                            .show();

                    installappliances.requestFocus();
                }

            }else
            {

            }

        }
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
    }
}
