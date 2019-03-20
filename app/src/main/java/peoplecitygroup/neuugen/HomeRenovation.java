package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class HomeRenovation extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView electricianicon,plumbingicon,carpentryicon,servicegrntyiconHR,sparepartsiconHR,custprotecticonHR;
    CardView electricianservice,plumbingservice,carpentryservice;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_renovation);

        idLink();
        listenerLink();
        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        electricianicon.setTypeface(font);
        plumbingicon.setTypeface(font);
        carpentryicon.setTypeface(font);
        servicegrntyiconHR.setTypeface(font);
        sparepartsiconHR.setTypeface(font);
        custprotecticonHR.setTypeface(font);

    }
    public void idLink()
    {
        electricianicon=findViewById(R.id.electricianicon);
        plumbingicon=findViewById(R.id.plumbingicon);
        carpentryicon=findViewById(R.id.carpentryicon);
        sparepartsiconHR=findViewById(R.id.sparepartsiconHR);
        servicegrntyiconHR=findViewById(R.id.servicegrntyiconHR);
        custprotecticonHR=findViewById(R.id.custprotecticonHR);
        plumbingservice=findViewById(R.id.plumbingservice);
        electricianservice=findViewById(R.id.electricianservice);
        carpentryservice=findViewById(R.id.carpentryservice);


    }
    public void listenerLink()
    {
        plumbingservice.setOnClickListener(this);
        carpentryservice.setOnClickListener(this);
        electricianservice.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.plumbingservice){
            Intent intent = new Intent(HomeRenovation.this, HomeRenovationForm.class);
            intent.putExtra("servicetext","Plumbing Service");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeRenovation.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.electricianservice){
            Intent intent = new Intent(HomeRenovation.this, HomeRenovationForm.class);
            intent.putExtra("servicetext","Electrician Service");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeRenovation.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.carpentryservice){
            Intent intent = new Intent(HomeRenovation.this, HomeRenovationForm.class);
            intent.putExtra("servicetext","Carpentry Service");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeRenovation.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }
}
