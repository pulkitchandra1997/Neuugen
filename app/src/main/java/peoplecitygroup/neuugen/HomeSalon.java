package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class HomeSalon extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView trainericon,timericon,cleanicon,mensalonicon,womensalonicon;
    CardView mensalonservice,womensalonservice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_salon);

        idLink();
        listenerLink();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        timericon.setTypeface(font);
        cleanicon.setTypeface(font);
        trainericon.setTypeface(font);
        mensalonicon.setTypeface(font);
        womensalonicon.setTypeface(font);
        checkActive();
    }

    private void checkActive() {
        ServiceCheck serviceCheck=new ServiceCheck();
        serviceCheck.check(UrlNeuugen.salonServiceId,this);
        try {
            JSONObject jsonObject=new JSONObject(serviceCheck.finalResponse);
            Log.i("checkservice",jsonObject.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void listenerLink() {
        mensalonservice.setOnClickListener(this);
        womensalonservice.setOnClickListener(this);

    }


    public void idLink()
    {
        cleanicon=findViewById(R.id.cleanicon);
        trainericon=findViewById(R.id.trainericon);
        timericon=findViewById(R.id.timericon);
        mensalonicon=findViewById(R.id.mensalonicon);
        womensalonicon=findViewById(R.id.womensalonicon);
        mensalonservice=findViewById(R.id.mensalonservice);
        womensalonservice=findViewById(R.id.womensalonservice);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.mensalonservice){
            Intent intent = new Intent(HomeSalon.this, SalonServices.class);
            intent.putExtra("salonservicetext","Men Salon Services");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeSalon.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.womensalonservice){
            Intent intent = new Intent(HomeSalon.this, SalonServices.class);
            intent.putExtra("salonservicetext","Women Salon Services");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeSalon.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

    }
}
