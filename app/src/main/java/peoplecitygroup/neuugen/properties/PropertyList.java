package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.yahoo.mobile.client.android.util.rangeseekbar.RangeSeekBar;

import peoplecitygroup.neuugen.R;

public class PropertyList extends AppCompatActivity {

    RangeSeekBar pricerangeSeekBar,bedrangeseekBar,bathrangeseekbar;
    LinearLayout filterlayout,propertylayout;
    RadioGroup furnishtypeFI,constatusFI,posesnstatusFI;
    AppCompatRadioButton fullfurnishFI,semifurnishFI,unfurnishFI,immediateFI,infutureFI,readytomoveFI,underconFI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        idlink();
        pricerangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                //Now you have the minValue and maxValue of your RangeSeekbar
                Toast.makeText(PropertyList.this, minValue + "-" + maxValue, Toast.LENGTH_LONG).show();
            }
        });
// Get noticed while dragging
        pricerangeSeekBar.setNotifyWhileDragging(true);
    }

    public void idlink()
    {
        pricerangeSeekBar=findViewById(R.id.pricerangeSeekbar);
        bedrangeseekBar=findViewById(R.id.bedsrangeSeekbar);
        bathrangeseekbar=findViewById(R.id.bathsrangeSeekbar);
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

    }
}
