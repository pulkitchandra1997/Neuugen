package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;

import peoplecitygroup.neuugen.service.UrlNeuugen;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class PostAd extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView backbutton;
    MaterialButton nextbutton;
    RadioGroup propertyoptions;
    AppCompatRadioButton renthouses,sellhouses,rentoffice,sellplots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_ad);

        idLink();
        listenerLink();
        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backbutton.setTypeface(font);
    }

    public void idLink()
    {
        backbutton=findViewById(R.id.backbutton);
        nextbutton=findViewById(R.id.nextbutton);
        propertyoptions=findViewById(R.id.propertyoptions);
        renthouses=findViewById(R.id.renthouses);
        rentoffice=findViewById(R.id.rentoffice);
        sellhouses=findViewById(R.id.sellhouses);
        sellplots=findViewById(R.id.sellplots);

    }

    public void listenerLink()
    {
        backbutton.setOnClickListener(this);
        nextbutton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backbutton)
        {
            onBackPressed();
        }
        if (v.getId()==R.id.nextbutton)
        {
            if (rentoffice.isChecked())
            {
                Intent intent = new Intent(PostAd.this, PropertiesCityCheck.class);
                intent.putExtra("adtype","Rent Office");
                intent.putExtra("propertiesid", UrlNeuugen.propertiesserviceid);
                if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(PostAd.this, R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }

            }
            else
            if (renthouses.isChecked())
            {
                Intent intent = new Intent(PostAd.this, PropertiesCityCheck.class);
                intent.putExtra("adtype","Rent Houses");
                intent.putExtra("propertiesid",UrlNeuugen.propertiesserviceid);
                if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(PostAd.this, R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }else
            if (sellhouses.isChecked())
            {
                Intent intent = new Intent(PostAd.this, PropertiesCityCheck.class);
                intent.putExtra("adtype","Sell Houses");
                intent.putExtra("propertiesid",UrlNeuugen.propertiesserviceid);
                if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(PostAd.this, R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }else
            if (sellplots.isChecked())
            {
                Intent intent = new Intent(PostAd.this, PropertiesCityCheck.class);
                intent.putExtra("adtype","Sell Plots");
                intent.putExtra("propertiesid",UrlNeuugen.propertiesserviceid);
                if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(PostAd.this, R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }

            if (!rentoffice.isChecked()&&!renthouses.isChecked()&&!sellplots.isChecked()&&!sellhouses.isChecked())
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage("Select atleast one option");
                alertDialog.setIcon(R.mipmap.ic_launcher_round);
                alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                alertDialog.show();

                propertyoptions.requestFocus();
            }
        }
    }
}
