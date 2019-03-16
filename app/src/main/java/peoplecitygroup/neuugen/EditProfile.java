package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;
import java.util.Date;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    androidx.appcompat.widget.AppCompatTextView backtoprofile;
    MaterialButton update;
    TextInputEditText name,city,email,address,state,pincode,dob;
    String nametext,citytext,emailtext,addresstext,statetext,pincodetext,dobtext,gendertext;
    int day,year,month;
    RadioGroup gender;
    AppCompatRadioButton male,female;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        idLink();
        listenerLink();
        hideSoftKeyboard();
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
                }
                if (TextUtils.isEmpty(emailtext)) {
                    email.setError("Enter Email");
                    email.requestFocus();
                }
                if (TextUtils.isEmpty(pincodetext)) {
                    pincode.setError("Enter phone number");
                    pincode.requestFocus();
                }
                if (TextUtils.isEmpty(dobtext)) {
                    dob.setError("Select DOB");
                    dob.requestFocus();
                }
                if (TextUtils.isEmpty(addresstext)) {
                    address.setError("Enter Address");
                    address.requestFocus();
                }
                if (TextUtils.isEmpty(citytext)) {
                    city.setError("Enter City");
                    city.requestFocus();
                }
                if (TextUtils.isEmpty(statetext)) {
                    state.setError("Enter State");
                    state.requestFocus();
                }

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
                }
                if(!Validation.isValidName(nametext)){
                    name.setError("Enter Valid Name");
                    name.requestFocus();
                }
            }else
            {

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
        if (v.getId()==R.id.update)
        {


        }

    }
}
