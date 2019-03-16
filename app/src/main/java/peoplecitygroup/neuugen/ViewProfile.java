package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class ViewProfile extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView emailverify,addverify,phoneverify,editprofileicon,backtoaccount,emailtext,phonetext,citytext,addresstext,statetext,pincodetext,emailicon,phoneicon,addressicon,cityicon,nametext,gendertext,gendericon,bdaytext,bdayicon;

    BootstrapCircleThumbnail profileimg,profileimgbtn;

    SharedPreferences sp;

    SharedPreferences.Editor se;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        idLink();
        listenerLink();

        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        se=sp.edit();

        fill();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        editprofileicon.setTypeface(font);
        backtoaccount.setTypeface(font);
        emailicon.setTypeface(font);
        phoneicon.setTypeface(font);
        cityicon.setTypeface(font);
        addressicon.setTypeface(font);
        gendericon.setTypeface(font);
        bdayicon.setTypeface(font);

    }

    public void fill()
    {
        nametext.setText(sp.getString("name","name"));
        emailtext.setText(sp.getString("email","email"));
        phonetext.setText(sp.getString("mobileno","mobileno"));
        citytext.setText(sp.getString("city","city"));
        if (sp.getString("address","address").equalsIgnoreCase("null"))
        {
            addresstext.setText("Address not yet filled");
        }else
        {
            addresstext.setText(sp.getString("address",""));
        }
        if (sp.getString("state","state").equalsIgnoreCase("null"))
        {
            statetext.setText("");
        }
        else
        {
            statetext.setText(sp.getString("state", " "));
        }
        if (sp.getString("gender","gender").equalsIgnoreCase("null"))
        {
            gendertext.setText("Gender not yet filled");
        }
        else
        {
            gendertext.setText(sp.getString("gender", "gender not yet filled"));
        }
        if (sp.getString("pincode","pincode").equalsIgnoreCase("null"))
        {
            pincodetext.setText(" ");
        }
        else
        {
            pincodetext.setText(sp.getString("pincode"," "));
        }
        if (sp.getString("dob","dob").equalsIgnoreCase("null"))
        {
            bdaytext.setText("DOB not yet filled");
        }
        else
        {
            bdaytext.setText(sp.getString("dob",""));
        }
        if (sp.getString("emailverified","emailverified").equalsIgnoreCase("1"))
        {
            emailverify.setText("Verified");
        }
        else
        {
            emailverify.setText("Not Verified");
        }
        if (sp.getString("addressverified","addressverified").equalsIgnoreCase("1"))
        {
            addverify.setText("Verified");
        }
        else
        {
            addverify.setText("Not Verified");
        }


    }

    public void idLink()
    {
        editprofileicon=findViewById(R.id.editprofileicon);
        backtoaccount=findViewById(R.id.backtoaccount);
        emailicon=findViewById(R.id.emailicon);
        phoneicon=findViewById(R.id.phoneicon);
        cityicon=findViewById(R.id.cityicon);
        addressicon=findViewById(R.id.addressicon);
        emailtext=findViewById(R.id.emailtext);
        phonetext=findViewById(R.id.phonetext);
        addresstext=findViewById(R.id.addresstext);
        citytext=findViewById(R.id.citytext);
        statetext=findViewById(R.id.statetext);
        pincodetext=findViewById(R.id.pincodetext);
        nametext=findViewById(R.id.nametext);
        bdayicon=findViewById(R.id.bdayicon);
        bdaytext=findViewById(R.id.bdaytext);
        gendericon=findViewById(R.id.gendericon);
        gendertext=findViewById(R.id.gendertext);
        profileimg=findViewById(R.id.profileimg);
        profileimgbtn=findViewById(R.id.profileimgbtn);
        emailverify=findViewById(R.id.emailverify);
        phoneverify=findViewById(R.id.phoneverify);
        addverify=findViewById(R.id.addverify);


    }
    public void listenerLink()
    {
        editprofileicon.setOnClickListener(this);
        backtoaccount.setOnClickListener(this);
        profileimgbtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backtoaccount)
        {
            onBackPressed();
        }
        if(v.getId()==R.id.editprofileicon)
        {
            Intent intent = new Intent(ViewProfile.this, EditProfile.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(ViewProfile.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }
}
