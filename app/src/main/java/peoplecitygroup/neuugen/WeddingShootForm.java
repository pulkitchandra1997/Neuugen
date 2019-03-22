package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
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

public class WeddingShootForm extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    AppCompatSpinner wednumofdays;
    TextInputEditText areaWP,cityWP,landmarkWP,pincodeWP,dosWP,housenoWP;
    MaterialButton requestserviceWP;
    int day,year,month;
    String areatext,citytext,landmarktext,dostext,pincodetext,housenotext,wednumofdaystext,wedeventtypetext,wedpackagetext,wedpackagepricetext,engagementid,mehendiid,sangeetid,marriageid,receptionid,othersid;
    LinearLayout weddingformlayout;
    AppCompatTextView wedpackage,wedpackageprice;

    AppCompatCheckBox engagement,mehendi,sangeet,marriage,reception,others;

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
        setContentView(R.layout.activity_wedding_shoot_form);

        intent=getIntent();
        wedpackagetext=intent.getStringExtra("weddingpackage");
        wedpackagepricetext=intent.getStringExtra("weddingpackageprice");

        idLink();
        listenerLink();
        hideSoftKeyboard();

        wedpackage.setText(wedpackagetext);
        wedpackageprice.setText(wedpackagepricetext);
        
    }
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void idLink()
    {
        dosWP=findViewById(R.id.dosWP);
        areaWP=findViewById(R.id.areaWP);
        cityWP=findViewById(R.id.cityWP);
        landmarkWP=findViewById(R.id.landmarkWP);
        pincodeWP=findViewById(R.id.pincodeWP);
        requestserviceWP=findViewById(R.id.requestserviceWP);
        housenoWP=findViewById(R.id.housenoWP);
        weddingformlayout=findViewById(R.id.weddingformlayout);
        wednumofdays=findViewById(R.id.wednumofdays);
        engagement=findViewById(R.id.engagement);
        mehendi=findViewById(R.id.mehendi);
        sangeet=findViewById(R.id.sangeet);
        marriage=findViewById(R.id.marriage);
        reception=findViewById(R.id.reception);
        others=findViewById(R.id.others);
        wedpackage=findViewById(R.id.wedpackage);
        wedpackageprice=findViewById(R.id.wedpackageprice);

    }
    public void listenerLink()
    {
        requestserviceWP.setOnClickListener(this);
        dosWP.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.requestserviceWP)
        {
            areatext=areaWP.getText().toString().trim();
            housenotext=housenoWP.getText().toString().trim();
            citytext=cityWP.getText().toString().trim();
            landmarktext=landmarkWP.getText().toString().trim();
            pincodetext=pincodeWP.getText().toString().trim();
            dostext=dosWP.getText().toString().trim();
            
            wednumofdaystext=String.valueOf(wednumofdays.getSelectedItemPosition());
            if (engagement.isChecked())
            {
               engagementid="1";
            }else
                engagementid="0";
            if (mehendi.isChecked())
            {
                mehendiid="1";
            }else
                mehendiid="0";
            if (sangeet.isChecked())
            {
                sangeetid="1";
            }else
                sangeetid="0";
            if (marriage.isChecked())
            {
                marriageid="1";
            }else
                marriageid="0";
            if (reception.isChecked())
            {
                receptionid="1";
            }else
                receptionid="0";
            if (others.isChecked())
            {
                othersid="1";
            }else
                othersid="0";

            if (TextUtils.isEmpty(areatext) || TextUtils.isEmpty(housenotext)||TextUtils.isEmpty(citytext)||TextUtils.isEmpty(pincodetext)||TextUtils.isEmpty(dostext)||wednumofdays.getSelectedItem().toString().equalsIgnoreCase("Select Number of Days")||!engagement.isChecked()&&!mehendi.isChecked()&&!sangeet.isChecked()&&!marriage.isChecked()&&!others.isChecked()&&!reception.isChecked())
            {
                if (TextUtils.isEmpty(areatext) )
                {
                    areaWP.setError("Enter Area");
                    areaWP.requestFocus();
                }
                if (TextUtils.isEmpty(housenotext) )
                {
                    housenoWP.setError("Enter House Number");
                    housenoWP.requestFocus();
                }
                if (TextUtils.isEmpty(citytext) )
                {
                    cityWP.setError("Enter City");
                    cityWP.requestFocus();
                }
                if (TextUtils.isEmpty(pincodetext) )
                {
                    pincodeWP.setError("Enter Pincode");
                    pincodeWP.requestFocus();
                }
                if (TextUtils.isEmpty(dostext) )
                {
                    dosWP.setError("Select Date");
                    dosWP.requestFocus();
                }
                if (!engagement.isChecked()&&!mehendi.isChecked()&&!sangeet.isChecked()&&!marriage.isChecked()&&!others.isChecked()&&!reception.isChecked())
                {
                    Snackbar.make(weddingformlayout, "Select Event Type", Snackbar.LENGTH_LONG)
                            .show();
                }
                if (wednumofdaystext.equalsIgnoreCase("Select Number of Days"))
                {
                    Snackbar.make(weddingformlayout, "Select  Number of Days", Snackbar.LENGTH_LONG)
                            .show();
                    wednumofdays.requestFocus();
                }

            }else
            {

            }

        }
        if (v.getId() == R.id.dosWP)
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
                    dosWP.setText(getDate());
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
