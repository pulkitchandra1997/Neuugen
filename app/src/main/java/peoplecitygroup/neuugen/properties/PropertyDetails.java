package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.AD;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class PropertyDetails extends AppCompatActivity implements View.OnClickListener {

    AppCompatImageView houseimg1,houseimg2,houseimg3;

    MaterialButton contactbtn;

    AppCompatTextView bedicon,bathicon,furnishicon,consnicon,posesnicon,costicon,renticon,builtareaicon,plotareaicon,lengthicon,widthicon,roadicon,addicon;

    CardView bedbathcard,fcpcard;

    LinearLayout furnishlayout,consnlayout,posesnlayout,costlayout,monthlyrentlayout,builtarealayout,plotarealayout;

    AppCompatTextView numofbeds,numofbath,furnishtype,constructiontatus,possessionstatus,price,monthlyrent,builtarea,plotarea,lengthofplot,widthofplot,widthroad,landmarkpd,localitypd,citypd,pincodepd;
    AD ad;
    boolean flag=false;//ownAD ornot?
    LinearLayout bathbedcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_details);
        
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

        Intent intent=getIntent();
        if(intent==null)
            finish();
        String flag=intent.getStringExtra("flag");
        if(flag.trim().equalsIgnoreCase("object")){
            ad=(AD)intent.getSerializableExtra("object");
            fill(ad);
        }
    }

    private void fill(AD ad) {
        if(ad.getPic1()!=null||ad.getPic1().trim()!="")
            Picasso.with(this).load(ad.getPic1())
                    .placeholder(R.drawable.placeholder)
                    .into(houseimg1);
        if(ad.getPic2()!=null||ad.getPic2().trim()!="")
            Picasso.with(this).load(ad.getPic2())
                    .placeholder(R.drawable.placeholder)
                    .into(houseimg2);
        if(ad.getPic2()!=null||ad.getPic2().trim()!="")
            Picasso.with(this).load(ad.getPic3())
                    .placeholder(R.drawable.placeholder)
                    .into(houseimg3);
        if (ad.getPropertytype().trim()=="0"||ad.getPropertytype().trim()=="1"||ad.getPropertytype().trim()=="2"||ad.getPropertytype().trim()=="3"){
            if(ad.getBedrooms()!=null||ad.getBedrooms().trim()!="")
                numofbeds.setText(ad.getBedrooms());
            else
                numofbeds.setText("Data not available");
            if(ad.getBathrooms()!=null||ad.getBathrooms().trim()!="")
                numofbath.setText(ad.getBathrooms());
            else numofbath.setText("Data not available");
            if(ad.getFurnishtype()!=null||ad.getFurnishtype().trim()!="")
                furnishtype.setText(ad.getFurnishtype());
            else
                furnishtype.setText("Data not available");
        }
        else{
            bedbathcard.setVisibility(View.GONE);
            furnishlayout.setVisibility(View.GONE);
        }
        if(ad.getConstructionstatus()!=null||ad.getConstructionstatus().trim()!=""){
            if(ad.getConstructionstatus().trim()=="0")
                constructiontatus.setText("Ready to move");
            else
                constructiontatus.setText("Under Construction");
        }
        else
            consnlayout.setVisibility(View.GONE);
        if(ad.getPossessionstatus()!=null||ad.getPossessionstatus().trim()!=""){
            if(ad.getPossessionstatus().trim()=="0")
                possessionstatus.setText("Immediate");
            else
                possessionstatus.setText("In future");
        }
        else
            posesnlayout.setVisibility(View.GONE);
        if (ad.getAdtype().trim()=="0"){
            monthlyrent.setText(ad.getPrice().trim());
            costlayout.setVisibility(View.GONE);
        }
        else{
            monthlyrentlayout.setVisibility(View.GONE);
            price.setText(ad.getPrice().trim());
        }
        if(ad.getPropertytype().trim()=="6"){
            builtarealayout.setVisibility(View.GONE);
            if(ad.getBuiltuparea()!=null||ad.getBuiltuparea().trim()!="")
                plotarea.setText(ad.getBuiltuparea());
            else
                plotarea.setText("Data not Available");
            if(ad.getLength()!=null||ad.getLength().trim()!="")
                lengthofplot.setText(ad.getLength());
            else
                lengthofplot.setText("Data not Available");
            if(ad.getWidth()!=null||ad.getWidth().trim()!="")
                widthofplot.setText(ad.getWidth());
            else
                widthofplot.setText("Data not Available");
            if(ad.getWidthoffacingroad()!=null||ad.getWidthoffacingroad().trim()!="")
                widthroad.setText(ad.getWidthoffacingroad());
            else
                widthroad.setText("Data not Available");
        }
        else{
            if(ad.getBuiltuparea()!=null||ad.getBuiltuparea().trim()!="")
                builtarea.setText(ad.getBuiltuparea());
            else
                builtarea.setText("Data not Available");
            plotarealayout.setVisibility(View.GONE);
        }
        if(ad.getArea()!=null||ad.getArea().trim()!="")
            localitypd.setText(ad.getArea());
        if(ad.getLandmark()!=null||ad.getLandmark().trim()!="")
            landmarkpd.setText(ad.getLandmark());
        if(ad.getCity()!=null||ad.getCity().trim()!="")
            citypd.setText(ad.getCity());
        if(ad.getPincode()!=null||ad.getPincode().trim()!="")
            pincodepd.setText(ad.getPincode());

        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        String number=sp.getString("mobileno", null);
        if(number.trim().equalsIgnoreCase(ad.getMobileno().trim())){
            contactbtn.setText("EDIT");
            flag=true;
        }
        else{
            contactbtn.setText("SHOW INTEREST");
            flag=false;
        }
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
        bathbedcard=findViewById(R.id.bathbedcard);
    }

    private void listenerLink() {
        contactbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.contactbtn){
            if(flag){
                Intent intent;
                if(ad.getAdtype().trim()=="0"){
                    if(ad.getPropertytype().trim()=="4"||ad.getPropertytype().trim()=="5")
                        intent=new Intent(PropertyDetails.this,RentOffice.class);
                    else
                        intent=new Intent(PropertyDetails.this,RentHouses.class);
                }
                else{
                    if(ad.getPropertytype().trim()=="6")
                        intent=new Intent(PropertyDetails.this,SellPlots.class);
                    else
                        intent=new Intent(PropertyDetails.this,SellHouses.class);
                }
                intent.putExtra("object",ad);
                if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(PropertyDetails.this, R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
            }
        }
    }
}
