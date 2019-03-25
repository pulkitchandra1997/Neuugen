package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class Appliancerepair extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView repairicon,installicon,servicegrntyicon,sparepartsicon,custprotecticon;
    CardView repairingservice,installationservice;
    AppCompatImageView arimg1,arimg2,arimg3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliancerepair);

        idLink();
        listenerLink();
        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        repairicon.setTypeface(font);
        installicon.setTypeface(font);
        servicegrntyicon.setTypeface(font);
        custprotecticon.setTypeface(font);
        sparepartsicon.setTypeface(font);

    }

    public void idLink()
    {
        repairicon=findViewById(R.id.repairicon);
        installicon=findViewById(R.id.installicon);
        custprotecticon=findViewById(R.id.custprotecticon);
        sparepartsicon=findViewById(R.id.sparepartsicon);
        servicegrntyicon=findViewById(R.id.servicegrntyicon);
        repairingservice=findViewById(R.id.repairingservice);
        installationservice=findViewById(R.id.installationservice);
        arimg1=findViewById(R.id.arimg1);
        arimg2=findViewById(R.id.arimg2);
        arimg3=findViewById(R.id.arimg3);

    }
    public void listenerLink()
    {
        repairingservice.setOnClickListener(this);
        installationservice.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.repairingservice){
            Intent intent = new Intent(Appliancerepair.this, ApplianceRepairform.class);
            intent.putExtra("servicetext","Repairing Service");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(Appliancerepair.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.installationservice){
            Intent intent = new Intent(Appliancerepair.this, ApplianceRepairform.class);
            intent.putExtra("servicetext","Installation Service");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(Appliancerepair.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }


    }
}
