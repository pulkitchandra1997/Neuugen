package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.material.button.MaterialButton;

public class PropertyDetails extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageView houseimg1,houseimg2,houseimg3;

    MaterialButton contactbtn;

    AppCompatTextView bedicon,bathicon,furnishicon,consnicon,posesnicon,costicon,renticon,builtareaicon,plotareaicon,lengthicon,widthicon,roadicon,addicon;

    CardView bedbathcard,fcpcard;

    LinearLayout furnishlayout,consnlayout,posesnlayout,costlayout,monthlyrentlayout,builtarealayout,plotarealayout;

    AppCompatTextView numofbeds,numofbath,furnishtype,constructiontatus,possessionstatus,price,monthlyrent,builtarea,plotarea,lengthofplot,widthofplot,widthroad,landmarkpd,localitypd,citypd,pincodepd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        
        fill();
        idLink();
        listenerLink();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        bedicon.setTypeface(font);
        bathicon.setTypeface(font);
        furnishicon.setTypeface(font);
        consnicon.setTypeface(font);
        posesnicon.setTypeface(font);
        costicon.setTypeface(font);
        renticon.setTypeface(font);
        builtareaicon.setTypeface(font);
        plotareaicon.setTypeface(font);
        lengthicon.setTypeface(font);
        widthicon.setTypeface(font);
        roadicon.setTypeface(font);
        addicon.setTypeface(font);
        
        
    }

    private void fill() {
    }

    private void idLink() {
        houseimg1=findViewById(R.id.houseimg1);
        houseimg2=findViewById(R.id.houseimg2);
        houseimg3=findViewById(R.id.houseimg3);
        contactbtn=findViewById(R.id.contactbtn);
        bedicon=findViewById(R.id.bedicon);
        bathicon=findViewById(R.id.bathicon);
        furnishicon=findViewById(R.id.furnishicon);
        consnicon=findViewById(R.id.consnicon);
        posesnicon=findViewById(R.id.posesnicon);
        costicon=findViewById(R.id.costicon);
        renticon=findViewById(R.id.renticon);
        builtareaicon=findViewById(R.id.builtareaicon);
        plotareaicon=findViewById(R.id.plotareaicon);
        lengthicon=findViewById(R.id.lengthicon);
        widthicon=findViewById(R.id.widthicon);
        roadicon=findViewById(R.id.roadicon);
        addicon=findViewById(R.id.addicon);
        bedbathcard=findViewById(R.id.bedbathcard);
        fcpcard=findViewById(R.id.fcpcard);
        furnishlayout=findViewById(R.id.furnishlayout);
        consnlayout=findViewById(R.id.consnlayout);
        posesnlayout=findViewById(R.id.posesnlayout);
        costlayout=findViewById(R.id.costlayout);
        monthlyrentlayout=findViewById(R.id.monthlyrentlayout);
        builtarealayout=findViewById(R.id.builtarealayout);
        plotarealayout=findViewById(R.id.plotarealayout);
        furnishtype=findViewById(R.id.furnishtype);
        constructiontatus=findViewById(R.id.constructiontatus);
        possessionstatus=findViewById(R.id.possessionstatus);
        monthlyrent=findViewById(R.id.monthlyrent);
        price=findViewById(R.id.price);
        builtarea=findViewById(R.id.builtarea);
        plotarea=findViewById(R.id.plotarea);
        lengthofplot=findViewById(R.id.lengthofplot);
        widthofplot=findViewById(R.id.widthofplot);
        numofbath=findViewById(R.id.numofbath);
        numofbeds=findViewById(R.id.numofbeds);
        widthroad=findViewById(R.id.widthroad);
        landmarkpd=findViewById(R.id.landmarkpd);
        localitypd=findViewById(R.id.localitypd);
        pincodepd=findViewById(R.id.pincodepd);
        citypd=findViewById(R.id.citypd);
        
    }

    private void listenerLink() {
        contactbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
