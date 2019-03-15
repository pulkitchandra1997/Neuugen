package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class SellHouses extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView backtopost3;

    TextInputEditText areash,citysh,landmarksh,builtareash,costsh,housenosh,ageofpropertysh;

    MaterialButton submitshform;

    AppCompatSpinner propertytypesh,numofbedsh,numofbathsh;

    ChipGroup furnishtypesh,constatus;

    Chip shfullyfurnish,shsemifurnish,shunfurnish,readytomove,undercon;

    LinearLayout shmainlayout;

    String areashtext,cityshtext,landmarkshtext,builtareashtext,costshtext,housenoshtext,propertytypeshtext,numofbedshtext,numofbathshtext,shfurnishtyypetext,constatustext,ageofpropertyshtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_houses);

        idLink();
        listenerLink();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backtopost3.setTypeface(font);
    }

    public void listenerLink()
    {
        backtopost3.setOnClickListener(this);
        submitshform.setOnClickListener(this);
    }

    public void idLink()
    {
        backtopost3=findViewById(R.id.backtopost3);
        areash=findViewById(R.id.areash);
        citysh=findViewById(R.id.citysh);
        landmarksh=findViewById(R.id.landmarksh);
        builtareash=findViewById(R.id.builtareash);
        costsh=findViewById(R.id.costsh);
        propertytypesh=findViewById(R.id.propertytypesh);
        numofbedsh=findViewById(R.id.numofbedsh);
        numofbathsh=findViewById(R.id.numofbathsh);
        submitshform=findViewById(R.id.submitshform);
        furnishtypesh=findViewById(R.id.furnishtypesh);
        shfullyfurnish=findViewById(R.id.shfullyfurnish);
        shsemifurnish=findViewById(R.id.shsemifurnish);
        shunfurnish=findViewById(R.id.shunfurnish);
        housenosh=findViewById(R.id.housenosh);
        constatus=findViewById(R.id.constatus);
        readytomove=findViewById(R.id.readytomove);
        undercon=findViewById(R.id.undercon);
        ageofpropertysh=findViewById(R.id.ageofpropertysh);
        shmainlayout=findViewById(R.id.shmainlayout);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backtopost3)
        {
            onBackPressed();
        }
        if (v.getId()==R.id.submitshform)
        {
            areashtext=areash.getText().toString().trim();
            housenoshtext=housenosh.getText().toString().trim();
            cityshtext=citysh.getText().toString().trim();
            landmarkshtext=landmarksh.getText().toString().trim();
            builtareashtext=builtareash.getText().toString().trim();
            costshtext=costsh.getText().toString().trim();
            propertytypeshtext=propertytypesh.getSelectedItem().toString();
            numofbedshtext=numofbedsh.getSelectedItem().toString();
            numofbathshtext=numofbathsh.getSelectedItem().toString();
            ageofpropertyshtext=ageofpropertysh.getText().toString().trim();
            if (furnishtypesh.getCheckedChipId()==R.id.shfullyfurnish)
            {
                shfurnishtyypetext="Fully furnished";
            }else
            if (furnishtypesh.getCheckedChipId()==R.id.shsemifurnish)
            {
                shfurnishtyypetext="Semi furnished";
            }else
            if (furnishtypesh.getCheckedChipId()==R.id.shunfurnish)
            {
                shfurnishtyypetext="Unfurnished";
            }
            if (constatus.getCheckedChipId()==R.id.readytomove)
            {
                constatustext="Ready to move";
            }else
            if (constatus.getCheckedChipId()==R.id.undercon)
            {
                constatustext="Under Construction";
            }

            if (TextUtils.isEmpty(areashtext) || TextUtils.isEmpty(housenoshtext)||TextUtils.isEmpty(cityshtext)||TextUtils.isEmpty(builtareashtext)||TextUtils.isEmpty(costshtext)||propertytypeshtext.equalsIgnoreCase("Select Property Type")||numofbathshtext.equalsIgnoreCase("Select Number")||numofbedshtext.equalsIgnoreCase("Select Number")||TextUtils.isEmpty(ageofpropertyshtext)||!(furnishtypesh.getCheckedChipId()==R.id.shfullyfurnish)&&!(furnishtypesh.getCheckedChipId()==R.id.shsemifurnish)&&!(furnishtypesh.getCheckedChipId()==R.id.shunfurnish)||!(constatus.getCheckedChipId()==R.id.undercon)&&!(constatus.getCheckedChipId()==R.id.readytomove))
            {
                if (TextUtils.isEmpty(areashtext) )
                {
                    areash.setError("Enter Area");
                    areash.requestFocus();
                }
                if (TextUtils.isEmpty(housenoshtext) )
                {
                    housenosh.setError("Enter House Number");
                    housenosh.requestFocus();
                }
                if (TextUtils.isEmpty(cityshtext) )
                {
                    citysh.setError("Enter City");
                    citysh.requestFocus();
                }
                if (TextUtils.isEmpty(builtareashtext) )
                {
                    builtareash.setError("Enter Built Area");
                    builtareash.requestFocus();
                }
                if (TextUtils.isEmpty(costshtext) )
                {
                    costsh.setError("Enter Cost");
                    costsh.requestFocus();
                }
                if (TextUtils.isEmpty(ageofpropertyshtext) )
                {
                    ageofpropertysh.setError("Enter Age of Property");
                    ageofpropertysh.requestFocus();
                }
                if (propertytypeshtext.equalsIgnoreCase("Select Property Type"))
                {
                    Snackbar.make(shmainlayout, "Select Property Type", Snackbar.LENGTH_LONG)
                            .show();

                    propertytypesh.requestFocus();
                }
                if (numofbedshtext.equalsIgnoreCase("Select Number"))
                {
                    Snackbar.make(shmainlayout, "Select Number of Bedrooms", Snackbar.LENGTH_LONG)
                            .show();

                    numofbedsh.requestFocus();
                }
                if (numofbathshtext.equalsIgnoreCase("Select Number"))
                {
                    Snackbar.make(shmainlayout, "Select Number of Bathrooms", Snackbar.LENGTH_LONG)
                            .show();

                    numofbathsh.requestFocus();
                }
                if (!(furnishtypesh.getCheckedChipId()==R.id.shfullyfurnish)&&!(furnishtypesh.getCheckedChipId()==R.id.shsemifurnish)&&!(furnishtypesh.getCheckedChipId()==R.id.shunfurnish))
                {
                    Snackbar.make(shmainlayout, "Select Furnish Type", Snackbar.LENGTH_LONG)
                            .show();

                    furnishtypesh.requestFocus();
                }
                if (!(constatus.getCheckedChipId()==R.id.undercon)&&!(constatus.getCheckedChipId()==R.id.readytomove)) {
                    Snackbar.make(shmainlayout, "Select Construction Status", Snackbar.LENGTH_LONG)
                            .show();

                    constatus.requestFocus();
                }

            }
            else
            {

            }
        }
    }
}
