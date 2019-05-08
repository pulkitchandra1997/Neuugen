package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.ArrayList;

import peoplecitygroup.neuugen.R;

public class PropertyList extends AppCompatActivity implements View.OnClickListener {

    CrystalRangeSeekbar pricerangebar,bedrangebar,bathrangebar;

    AppCompatTextView minbed,maxbed,minprice,maxprice,minbath,maxbath;

    CardView furnishcard,constatuscard,posesnstatuscard,bedcard,bathcard,pricecard,adtypecard,propertytypecard;

    AppCompatSpinner buytypeFI,renttypeFI;

    MaterialButton applybtn,cancelbtn;
    LinearLayout filterlayout,propertylayout;
    RadioGroup adtypeFI;
    AppCompatCheckBox fullfurnishFI,semifurnishFI,unfurnishFI,immediateFI,infutureFI,readytomoveFI,underconFI;
    AppCompatRadioButton buyFI,rentFI;
    FloatingActionButton filters;
    Intent intent;
    String adtype,propertytype;
    ProgressDialog loading = null;
    ArrayList<String> results=new ArrayList<String>();
    String verified,available;
    ArrayList<String> propertytypes,city,bedrooms,bathrooms,furnishtype,price,constructionstatus,possessionastatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        idlink();
        listenerlink();

        intent=getIntent();
        if(intent==null)
            finish();
        adtype=intent.getStringExtra("adtype");
        results=intent.getStringArrayListExtra("results");
        propertytypes=intent.getStringArrayListExtra("propertytype");
        city=intent.getStringArrayListExtra("city");
        bedrooms=intent.getStringArrayListExtra("bedrooms");
        bathrooms=intent.getStringArrayListExtra("bathrooms");
        furnishtype=intent.getStringArrayListExtra("furnishtype");
        price=intent.getStringArrayListExtra("price");
        constructionstatus=intent.getStringArrayListExtra("constructionstatus");
        possessionastatus=intent.getStringArrayListExtra("possessionastatus");
        verified=intent.getStringExtra("verified");
        available=intent.getStringExtra("available");

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
        furnishcard=findViewById(R.id.furnishcard);
        posesnstatuscard=findViewById(R.id.posesnstatuscard);
        constatuscard=findViewById(R.id.constatuscard);
        pricecard=findViewById(R.id.pricecard);
        bedcard=findViewById(R.id.bedcard);
        bathcard=findViewById(R.id.bathcard);
        adtypeFI=findViewById(R.id.adtypeFI);
        buyFI=findViewById(R.id.buyFI);
        rentFI=findViewById(R.id.rentFI);
        adtypecard=findViewById(R.id.adtypecard);
        buytypeFI=findViewById(R.id.buytypeFI);
        renttypeFI=findViewById(R.id.renttypeFI);
        propertytypecard=findViewById(R.id.propertytypecard);
    }

    public void listenerlink(){

        filters.setOnClickListener(this);
        applybtn.setOnClickListener(this);
        cancelbtn.setOnClickListener(this);
        buyFI.setOnClickListener(this);
        rentFI.setOnClickListener(this);
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
        if (v.getId()==R.id.buyFI)
        {
            propertytypecard.setVisibility(View.VISIBLE);
            renttypeFI.setVisibility(View.GONE);
            buytypeFI.setVisibility(View.VISIBLE);
        }
        if(v.getId()==R.id.rentFI)
        {
            propertytypecard.setVisibility(View.VISIBLE);
            renttypeFI.setVisibility(View.VISIBLE);
            buytypeFI.setVisibility(View.GONE);
        }
    }
}
