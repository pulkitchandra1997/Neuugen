package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class SalonServices extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView salonservicehead;

    MaterialButton menhaircutbtn,menbeardbtn,menpartybtn,womenhaircutbtn,womenpartymakeupbtn,womenweddingmakeupbtn;

    Intent intent;

    String servicetext;

    LinearLayout womensalonlayout,mensalonlayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_services);

        intent=getIntent();
        servicetext=intent.getStringExtra("salonservicetext");

        idLink();
        listenerLink();
        if (servicetext.equalsIgnoreCase("Men Salon Services"))
        {
            mensalonlayout.setVisibility(View.VISIBLE);
            womensalonlayout.setVisibility(View.GONE);
        }else
        if (servicetext.equalsIgnoreCase("Women Salon Services"))
        {
            womensalonlayout.setVisibility(View.VISIBLE);
            mensalonlayout.setVisibility(View.GONE);
        }


        salonservicehead.setText(servicetext);
    }

    public void idLink()
    {
        salonservicehead=findViewById(R.id.salonservicehead);
        mensalonlayout=findViewById(R.id.mensalonlayout);
        womensalonlayout=findViewById(R.id.womensalonlayout);
        menhaircutbtn=findViewById(R.id.menhaircutbtn);
        menbeardbtn=findViewById(R.id.menbeardbtn);
        menpartybtn=findViewById(R.id.menpartybtn);

        womenhaircutbtn=findViewById(R.id.womenhaircutbtn);
        womenpartymakeupbtn=findViewById(R.id.womenpartymakeupbtn);
        womenweddingmakeupbtn=findViewById(R.id.womenweddingmakeupbtn);
    }

    public void listenerLink()
    {
        menhaircutbtn.setOnClickListener(this);
        menbeardbtn.setOnClickListener(this);
        menpartybtn.setOnClickListener(this);
        womenweddingmakeupbtn.setOnClickListener(this);
        womenpartymakeupbtn.setOnClickListener(this);
        womenhaircutbtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.menhaircutbtn){
            Intent intent = new Intent(SalonServices.this, SalonServiceForm.class);
            intent.putExtra("servicetype","Men's Haircut");
            intent.putExtra("serviceprice","Rs. 399");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(SalonServices.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.menbeardbtn){
            Intent intent = new Intent(SalonServices.this, SalonServiceForm.class);
            intent.putExtra("servicetype","Men's Haircut & Beard");
            intent.putExtra("serviceprice","Rs. 499");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(SalonServices.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.menpartybtn){
            Intent intent = new Intent(SalonServices.this, SalonServiceForm.class);
            intent.putExtra("servicetype","Men's Party Grooming");
            intent.putExtra("serviceprice","Rs. 1999");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(SalonServices.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

        if (v.getId()==R.id.womenhaircutbtn){
            Intent intent = new Intent(SalonServices.this, SalonServiceForm.class);
            intent.putExtra("servicetype","Women's Haircut & styling");
            intent.putExtra("serviceprice","Rs. 599");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(SalonServices.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.womenpartymakeupbtn){
            Intent intent = new Intent(SalonServices.this, SalonServiceForm.class);
            intent.putExtra("servicetype","Women's Party makeup");
            intent.putExtra("serviceprice","Rs. 2999");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(SalonServices.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.womenweddingmakeupbtn){
            Intent intent = new Intent(SalonServices.this, SalonServiceForm.class);
            intent.putExtra("servicetype","Women's Wedding makeup ");
            intent.putExtra("serviceprice","Rs. 5499");
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(SalonServices.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

    }
}
