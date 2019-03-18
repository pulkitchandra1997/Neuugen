package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class RentHouses extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView backtopost;

    TextInputEditText arearh,cityrh,landmarkrh,builtarearh,monthlyrentrh,housenorh,pincoderh;

    MaterialButton submitrhform,addimgrh1,addimgrh2,addimgrh3;

    AppCompatSpinner propertytyperh,numofbedrh,numofbathrh;

    ChipGroup rhfurnishtype;

    Chip rhfullyfurnish,rhsemifurnish,rhunfurnish;

    LinearLayout rhmainlayout;

    AppCompatImageView imgrh1,imgrh2,imgrh3;

    String arearhtext,cityrhtext,landmarkrhtext,builtarearhtext,pincoderhtext,monthlyrentrhtext,housenorhtext,propertytyperhtext,numofbedrhtext,numofbathrhtext,rhfurnishtyypetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_houses);

        idLink();
        listenerLink();
        hideSoftKeyboard();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backtopost.setTypeface(font);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void listenerLink()
    {
        backtopost.setOnClickListener(this);
        submitrhform.setOnClickListener(this);
        addimgrh1.setOnClickListener(this);
        addimgrh2.setOnClickListener(this);
        addimgrh3.setOnClickListener(this);

    }

    public void idLink()
    {
        backtopost=findViewById(R.id.backtopost);
        arearh=findViewById(R.id.arearh);
        cityrh=findViewById(R.id.cityrh);
        landmarkrh=findViewById(R.id.landmarkrh);
        builtarearh=findViewById(R.id.builtarearh);
        monthlyrentrh=findViewById(R.id.monthlyrentrh);
        propertytyperh=findViewById(R.id.propertytyperh);
        numofbedrh=findViewById(R.id.numofbedrh);
        numofbathrh=findViewById(R.id.numofbathrh);
        submitrhform=findViewById(R.id.submitrhform);
        rhfurnishtype=findViewById(R.id.rhfurnishtype);
        rhfullyfurnish=findViewById(R.id.rhfullyfurnish);
        rhsemifurnish=findViewById(R.id.rhsemifurnish);
        rhunfurnish=findViewById(R.id.rhunfurnish);
        housenorh=findViewById(R.id.housenorh);
        rhmainlayout=findViewById(R.id.rhmainlayout);
        imgrh1=findViewById(R.id.imgrh1);
        imgrh2=findViewById(R.id.imgrh2);
        imgrh3=findViewById(R.id.imgrh3);
        addimgrh1=findViewById(R.id.addimgrh1);
        addimgrh2=findViewById(R.id.addimgrh2);
        addimgrh3=findViewById(R.id.addimgrh3);
        pincoderh=findViewById(R.id.pincoderh);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backtopost)
        {
            onBackPressed();
        }
        if (v.getId()==R.id.submitrhform)
        {
            arearhtext=arearh.getText().toString().trim();
            housenorhtext=housenorh.getText().toString().trim();
            cityrhtext=cityrh.getText().toString().trim();
            landmarkrhtext=landmarkrh.getText().toString().trim();
            builtarearhtext=builtarearh.getText().toString().trim();
            monthlyrentrhtext=monthlyrentrh.getText().toString().trim();
            propertytyperhtext=propertytyperh.getSelectedItem().toString();
            numofbedrhtext=numofbedrh.getSelectedItem().toString();
            numofbathrhtext=numofbathrh.getSelectedItem().toString();
            pincoderhtext=pincoderh.getText().toString().trim();

            if (rhfurnishtype.getCheckedChipId()==R.id.rhfullyfurnish)
            {
                rhfurnishtyypetext="Fully furnished";
            }else
            if (rhfurnishtype.getCheckedChipId()==R.id.rhsemifurnish)
            {
                rhfurnishtyypetext="Semi furnished";
            }else
            if (rhfurnishtype.getCheckedChipId()==R.id.rhunfurnish)
            {
                rhfurnishtyypetext="Unfurnished";
            }

            if (TextUtils.isEmpty(pincoderhtext) ||TextUtils.isEmpty(arearhtext) || TextUtils.isEmpty(housenorhtext)||TextUtils.isEmpty(cityrhtext)||TextUtils.isEmpty(builtarearhtext)||TextUtils.isEmpty(monthlyrentrhtext)||propertytyperhtext.equalsIgnoreCase("Select Property Type")||numofbathrhtext.equalsIgnoreCase("Select Number")||numofbedrhtext.equalsIgnoreCase("Select Number")||!(rhfurnishtype.getCheckedChipId()==R.id.rhfullyfurnish)&&!(rhfurnishtype.getCheckedChipId()==R.id.rhsemifurnish)&&!(rhfurnishtype.getCheckedChipId()==R.id.rhunfurnish))
            {
                if (TextUtils.isEmpty(arearhtext) )
                {
                    arearh.setError("Enter Area");
                    arearh.requestFocus();
                }
                if (TextUtils.isEmpty(housenorhtext) )
                {
                    housenorh.setError("Enter House Number");
                    housenorh.requestFocus();
                }
                if (TextUtils.isEmpty(cityrhtext) )
                {
                    cityrh.setError("Enter City");
                    cityrh.requestFocus();
                }
                if (TextUtils.isEmpty(pincoderhtext) )
                {
                    pincoderh.setError("Enter Pincode");
                    pincoderh.requestFocus();
                }
                if (TextUtils.isEmpty(builtarearhtext) )
                {
                    builtarearh.setError("Enter Built Area");
                    builtarearh.requestFocus();
                }
                if (TextUtils.isEmpty(monthlyrentrhtext) )
                {
                    monthlyrentrh.setError("Enter Monthly Rent");
                    monthlyrentrh.requestFocus();
                }
                if (propertytyperhtext.equalsIgnoreCase("Select Property Type"))
                {
                    Snackbar.make(rhmainlayout, "Select Property Type", Snackbar.LENGTH_LONG)
                            .show();

                    propertytyperh.requestFocus();
                }
                if (numofbedrhtext.equalsIgnoreCase("Select Number"))
                {
                    Snackbar.make(rhmainlayout, "Select Number of Bedrooms", Snackbar.LENGTH_LONG)
                            .show();

                    numofbedrh.requestFocus();
                }
                if (numofbathrhtext.equalsIgnoreCase("Select Number"))
                {
                    Snackbar.make(rhmainlayout, "Select Number of Bathrooms", Snackbar.LENGTH_LONG)
                            .show();

                    numofbathrh.requestFocus();
                }
                if (!(rhfurnishtype.getCheckedChipId()==R.id.rhfullyfurnish)&&!(rhfurnishtype.getCheckedChipId()==R.id.rhsemifurnish)&&!(rhfurnishtype.getCheckedChipId()==R.id.rhunfurnish))
                {
                    Snackbar.make(rhmainlayout, "Select Furnish Type", Snackbar.LENGTH_LONG)
                            .show();

                    rhfurnishtype.requestFocus();
                }

            }
            else
            {

            }
        }

    }
}
