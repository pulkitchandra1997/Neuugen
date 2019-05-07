package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import peoplecitygroup.neuugen.R;

public class PropertyList extends AppCompatActivity implements View.OnClickListener {

    CrystalRangeSeekbar pricerangebar,bedrangebar,bathrangebar;

    AppCompatTextView minbed,maxbed,minprice,maxprice,minbath,maxbath;

    MaterialButton applybtn,cancelbtn;
    LinearLayout filterlayout,propertylayout;
    RadioGroup furnishtypeFI,constatusFI,posesnstatusFI;
    AppCompatRadioButton fullfurnishFI,semifurnishFI,unfurnishFI,immediateFI,infutureFI,readytomoveFI,underconFI;
    FloatingActionButton filters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        idlink();
        listenerlink();


        pricerangebar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minprice.setText(String.valueOf(minValue));
                maxprice.setText(String.valueOf(maxValue));
            }
        });

        // set final value listener
        pricerangebar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Toast.makeText(PropertyList.this, String.valueOf(minValue)+String.valueOf(maxValue), Toast.LENGTH_SHORT).show();
            }
        });

        bedrangebar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minbed.setText(String.valueOf(minValue));
                maxbed.setText(String.valueOf(maxValue));
            }
        });

        // set final value listener
        bedrangebar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Toast.makeText(PropertyList.this, String.valueOf(minValue)+String.valueOf(maxValue), Toast.LENGTH_SHORT).show();
            }
        });

        bathrangebar.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                minbath.setText(String.valueOf(minValue));
                maxbath.setText(String.valueOf(maxValue));
            }
        });

        // set final value listener
        bathrangebar.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Toast.makeText(PropertyList.this, String.valueOf(minValue)+String.valueOf(maxValue), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void idlink()
    {
        pricerangebar=findViewById(R.id.pricerangeSeekbar);
        minbed=findViewById(R.id.minbed);
        maxbed=findViewById(R.id.maxbed);
        minbath=findViewById(R.id.minbath);
        maxbath=findViewById(R.id.maxbath);
        minprice=findViewById(R.id.minprice);
        maxprice=findViewById(R.id.maxprice);
        bedrangebar=findViewById(R.id.bedrangeSeekbar);
        bathrangebar=findViewById(R.id.bathrangeSeekbar);
        filterlayout=findViewById(R.id.filterlayout);
        propertylayout=findViewById(R.id.propertylayout);
        furnishtypeFI=findViewById(R.id.furnishtypeFI);
        constatusFI=findViewById(R.id.constatusFI);
        posesnstatusFI=findViewById(R.id.posesnstatusFi);
        fullfurnishFI=findViewById(R.id.fullfurnishFI);
        semifurnishFI=findViewById(R.id.semifurnishFI);
        unfurnishFI=findViewById(R.id.unfurnishFI);
        readytomoveFI=findViewById(R.id.readytomoveFI);
        infutureFI=findViewById(R.id.infutureFI);
        immediateFI=findViewById(R.id.immediateFI);
        underconFI=findViewById(R.id.underconFI);
        filters=findViewById(R.id.filters);
        applybtn=findViewById(R.id.applybtn);
        cancelbtn=findViewById(R.id.cancelbtn);

    }

    public void listenerlink(){

        filters.setOnClickListener(this);
        applybtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.filters){
            filterlayout.setVisibility(View.VISIBLE);
            propertylayout.setVisibility(View.GONE);
            filters.setVisibility(View.GONE);
        }
        if (v.getId()==R.id.cancelbtn)
        {
            filterlayout.setVisibility(View.GONE);
            propertylayout.setVisibility(View.VISIBLE);
            filters.setVisibility(View.VISIBLE);
        }
    }
}
