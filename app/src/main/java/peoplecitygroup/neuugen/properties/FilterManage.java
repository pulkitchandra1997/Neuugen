package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.button.MaterialButton;

import peoplecitygroup.neuugen.R;

public class FilterManage extends AppCompatActivity implements View.OnClickListener {
    LinearLayout filterlayoutmanagelist;
    RadioGroup propertytagslist;
    RadioButton buybtnlist,rentbtnlist;
    CardView propertytypecardlist,constatuscardlist,posesnstatuscardlist,pricecardlist,bedcardlist,bathcardlist;
    Spinner renttypeFIlist,buytypeFIlist;
    CheckBox fullfurnishFIlist,semifurnishFIlist,unfurnishFIlist,readytomoveFIlist,underconFIlist,immediateFIlist,infutureFIlist;
    CrystalRangeSeekbar pricerangeSeekbar,bedrangeSeekbar,bathrangeSeekbar;
    TextView minprice,maxprice,minbed,maxbed,minbath,maxbath;
    SwitchCompat verifiedtogglelist,availabletogglelist;
    MaterialButton cancelbtnmlist,applybtnmlist,showallbtnmlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_manage);
        idlink();
    }
    public void idlink()
    {
        filterlayoutmanagelist=findViewById(R.id.filterlayoutmanagelist);
        propertytagslist=findViewById(R.id.propertytagslist);
        buybtnlist=findViewById(R.id.buybtnlist);
        rentbtnlist=findViewById(R.id.rentbtnlist);
        propertytypecardlist=findViewById(R.id.propertytypecardlist);
        constatuscardlist=findViewById(R.id.constatuscardlist);
        posesnstatuscardlist=findViewById(R.id.posesnstatuscardlist);
        pricecardlist=findViewById(R.id.pricecardlist);
        bedcardlist=findViewById(R.id.bedcardlist);
        bathcardlist=findViewById(R.id.bathcardlist);
        renttypeFIlist=findViewById(R.id.renttypeFIlist);
        buytypeFIlist=findViewById(R.id.buytypeFIlist);
        fullfurnishFIlist=findViewById(R.id.fullfurnishFIlist);
        semifurnishFIlist=findViewById(R.id.semifurnishFIlist);
        unfurnishFIlist=findViewById(R.id.unfurnishFIlist);
        readytomoveFIlist=findViewById(R.id.readytomoveFIlist);
        underconFIlist=findViewById(R.id.underconFIlist);
        immediateFIlist=findViewById(R.id.immediateFIlist);
        infutureFIlist=findViewById(R.id.infutureFIlist);
        pricerangeSeekbar=findViewById(R.id.pricerangeSeekbar);
        bathrangeSeekbar=findViewById(R.id.bathrangeSeekbar);
        bedrangeSeekbar=findViewById(R.id.bedrangeSeekbar);
        minprice=findViewById(R.id.minprice);
        maxprice=findViewById(R.id.maxprice);
        minbath=findViewById(R.id.minbath);
        maxbed=findViewById(R.id.maxbed);
        minbed=findViewById(R.id.minbed);
        maxbath=findViewById(R.id.maxbath);
        verifiedtogglelist=findViewById(R.id.verifiedtogglelist);
        availabletogglelist=findViewById(R.id.availabletogglelist);
        applybtnmlist=findViewById(R.id.applybtnmlist);
        cancelbtnmlist=findViewById(R.id.cancelbtnmlist);
        showallbtnmlist=findViewById(R.id.showallbtnmlist);
    }
    public void listenerlink(){
        showallbtnmlist.setOnClickListener(this);
        cancelbtnmlist.setOnClickListener(this);
        applybtnmlist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
