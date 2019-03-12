package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class ViewProfile extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView editprofileicon,backtoaccount,emailtext,phonetext,citytext,addresstext,statetext,pincodetext,emailicon,phoneicon,addressicon,cityicon,nametext,gendertext,gendericon,bdaytext,bdayicon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        idLink();
        listenerLink();

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


    }
    public void listenerLink()
    {
        editprofileicon.setOnClickListener(this);
        backtoaccount.setOnClickListener(this);
    }

    public void fill()
    {

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
