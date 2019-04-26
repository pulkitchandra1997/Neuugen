package peoplecitygroup.neuugen.HomeServices.EventServices;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

import peoplecitygroup.neuugen.R;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class WeddingPackages extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView trainericon,timericon,cleanicon,normalpkgicon,standardpkgicon,premiumpkgicon,normalprice,standardprice,premiumprice;
    CardView normalpkg,standardpkg,premiumpkg;
    String normalpricetext,standardpricetext,premiumpricetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_packages);

        idLink();
        listenerLink();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        timericon.setTypeface(font);
        cleanicon.setTypeface(font);
        trainericon.setTypeface(font);
        premiumpkgicon.setTypeface(font);
        standardpkgicon.setTypeface(font);
        normalpkgicon.setTypeface(font);

    }
    public void listenerLink() {
        normalpkg.setOnClickListener(this);
        premiumpkg.setOnClickListener(this);
        standardpkg.setOnClickListener(this);
    }


    public void idLink()
    {
        cleanicon=findViewById(R.id.cleanicon);
        trainericon=findViewById(R.id.trainericon);
        timericon=findViewById(R.id.timericon);
        normalpkg=findViewById(R.id.normalpkg);
        premiumpkg=findViewById(R.id.premiumpkg);
        standardpkg=findViewById(R.id.standardpkg);
        normalpkgicon=findViewById(R.id.normalpkgicon);
        premiumpkgicon=findViewById(R.id.premiumpkgicon);
        standardpkgicon=findViewById(R.id.standardpkgicon);
        normalprice=findViewById(R.id.normalprice);
        standardprice=findViewById(R.id.standardprice);
        premiumprice=findViewById(R.id.premiumprice);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.normalpkg){
            Intent intent = new Intent(WeddingPackages.this, WeddingShootForm.class);
            intent.putExtra("weddingpackage","Normal Package");
            intent.putExtra("weddingpackageprice",normalpricetext);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(WeddingPackages.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.standardpkg){
            Intent intent = new Intent(WeddingPackages.this, WeddingShootForm.class);
            intent.putExtra("weddingpackage","Standard Package");
            intent.putExtra("weddingpackageprice",standardpricetext);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(WeddingPackages.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.premiumpkg){
            Intent intent = new Intent(WeddingPackages.this, WeddingShootForm.class);
            intent.putExtra("weddingpackage","Premium Package");
            intent.putExtra("weddingpackageprice",premiumpricetext);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(WeddingPackages.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

    }
}
