package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONException;
import org.json.JSONObject;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class HomeSalon extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView trainericon,timericon,cleanicon,mensalonicon,womensalonicon,mensalonmsg,womensalonmsg;
    CardView mensalonservice,womensalonservice;
    AppCompatImageView hsimg1,hsimg2,hsimg3;
    ProgressDialog loading = null;
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

        loading = new ProgressDialog(HomeSalon.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        checkActive();
    }

    private void checkActive() {
        loading.show();
        ServiceCheck serviceCheck=new ServiceCheck();
        serviceCheck.check(UrlNeuugen.salonServiceId, this, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                loading.dismiss();
                serviceDecode(result);
            }

            @Override
            public void onError(String response) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeSalon.this);

                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Error in server. Try Again")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
            }
        });
    }

    private void serviceDecode(String result) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            String id[]=jsonObject.getString("id").split("#");
            String title[]=jsonObject.getString("id").split("#");
            String active[]=jsonObject.getString("id").split("#");
            String cost[]=jsonObject.getString("id").split("#");
            String pic1[]=jsonObject.getString("id").split("#");
            String pic2[]=jsonObject.getString("id").split("#");
            String pic3[]=jsonObject.getString("id").split("#");
            checkParentActive(active[0]);
            if(active.length>1){
                for(int i=1;i<active.length;i++){
                    checkSubActive(id[i],active[i]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void checkSubActive(String id, String active) {
        if(active.equalsIgnoreCase("0")){
            //inactive
            //get id
        }
    }

    private void checkParentActive(String s) {
        if(s.equalsIgnoreCase("0")){
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeSalon.this);

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Service is currently not available. Try after some time OR contact info@neuugen.com")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
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
        hsimg1=findViewById(R.id.hsimg1);
        hsimg2=findViewById(R.id.hsimg2);
        hsimg3=findViewById(R.id.hsimg3);
        mensalonmsg=findViewById(R.id.mensalonmsg);
        womensalonmsg=findViewById(R.id.womensalonmsg);

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
