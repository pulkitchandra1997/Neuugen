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

public class SellPlots extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView backtopost4;

    TextInputEditText areasp,citysp,landmarksp,plotarea,plotprice,plotno,lengthofplot,widthofplot,widthofroad,propertytypesp;

    MaterialButton submitspform,addimgsp1,addimgsp2;

    ChipGroup posesnstatus;

    Chip immediate,infuture;

    LinearLayout spmainlayout;

    AppCompatImageView imgsp1,imgsp2;

    String areasptext,citysptext,landmarksptext,plotareatext,plotpricetext,plotnotext,lengthofplottext,widthofplottext,widthofroadtext,propertytypesptext,posesnstatustext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_plots);

        idLink();
        listenerLink();
        hideSoftKeyboard();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backtopost4.setTypeface(font);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void listenerLink()
    {
        backtopost4.setOnClickListener(this);
        submitspform.setOnClickListener(this);
        addimgsp1.setOnClickListener(this);
        addimgsp2.setOnClickListener(this);
    }

    public void idLink()
    {
        backtopost4=findViewById(R.id.backtopost4);
        areasp=findViewById(R.id.areasp);
        citysp=findViewById(R.id.citysp);
        landmarksp=findViewById(R.id.landmarksp);
        plotarea=findViewById(R.id.plotarea);
        plotno=findViewById(R.id.plotno);
        plotprice=findViewById(R.id.plotprice);
        submitspform=findViewById(R.id.submitspform);
        lengthofplot=findViewById(R.id.lengthplot);
        widthofplot=findViewById(R.id.widthplot);
        widthofroad=findViewById(R.id.widthofroad);
        propertytypesp=findViewById(R.id.propertytypesp);
        posesnstatus=findViewById(R.id.posesnstatus);
        immediate=findViewById(R.id.immediate);
        infuture=findViewById(R.id.infuture);
        spmainlayout=findViewById(R.id.spmainlayout);
        imgsp1=findViewById(R.id.imgsp1);
        imgsp2=findViewById(R.id.imgsp2);
        addimgsp1=findViewById(R.id.addimgsp1);
        addimgsp2=findViewById(R.id.addimgsp2);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backtopost4)
        {
            onBackPressed();
        }
        if (v.getId()==R.id.submitspform)
        {
            areasptext=areasp.getText().toString().trim();
            plotnotext=plotno.getText().toString().trim();
            citysptext=citysp.getText().toString().trim();
            landmarksptext=landmarksp.getText().toString().trim();
            plotareatext=plotarea.getText().toString().trim();
            plotpricetext=plotprice.getText().toString().trim();
            propertytypesptext=propertytypesp.getText().toString().trim();
            lengthofplottext=lengthofplot.getText().toString().trim();
            widthofplottext=widthofplot.getText().toString().trim();
            widthofroadtext=widthofroad.getText().toString().trim();
            if (posesnstatus.getCheckedChipId()==R.id.immediate)
            {
                posesnstatustext="Immediate";
            }else
            if (posesnstatus.getCheckedChipId()==R.id.infuture)
            {
                posesnstatustext="In Future";
            }

            if (TextUtils.isEmpty(areasptext) || TextUtils.isEmpty(plotnotext)||TextUtils.isEmpty(citysptext)||TextUtils.isEmpty(plotareatext)||TextUtils.isEmpty(plotpricetext)||propertytypesptext.equalsIgnoreCase("Select Property Type")||!(posesnstatus.getCheckedChipId()==R.id.undercon)&&!(posesnstatus.getCheckedChipId()==R.id.readytomove))
            {
                if (TextUtils.isEmpty(areasptext) )
                {
                    areasp.setError("Enter Area");
                    areasp.requestFocus();
                }
                if (TextUtils.isEmpty(plotnotext) )
                {
                    plotno.setError("Enter Plot Number");
                    plotno.requestFocus();
                }
                if (TextUtils.isEmpty(citysptext) )
                {
                    citysp.setError("Enter City");
                    citysp.requestFocus();
                }
                if (TextUtils.isEmpty(plotareatext) )
                {
                    plotarea.setError("Enter Plot Area");
                    plotarea.requestFocus();
                }
                if (TextUtils.isEmpty(plotpricetext) )
                {
                    plotprice.setError("Enter Plot Price");
                    plotprice.requestFocus();
                }

                if (propertytypesptext.equalsIgnoreCase("Select Property Type"))
                {
                    Snackbar.make(spmainlayout, "Select Property Type", Snackbar.LENGTH_LONG)
                            .show();

                    propertytypesp.requestFocus();
                }

                if (!(posesnstatus.getCheckedChipId()==R.id.undercon)&&!(posesnstatus.getCheckedChipId()==R.id.readytomove)) {
                    Snackbar.make(spmainlayout, "Select Possession Status", Snackbar.LENGTH_LONG)
                            .show();

                    posesnstatus.requestFocus();
                }

            }
            else
            {

            }
        }

    }
}
