package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import peoplecitygroup.neuugen.HomeServices.ApplianceRepairServices.Appliancerepair;
import peoplecitygroup.neuugen.HomeServices.EventServices.EventsActivity;
import peoplecitygroup.neuugen.HomeServices.SparePartsActivity;
import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.SearchResult;
import peoplecitygroup.neuugen.common_req_files.ServiceCheck;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.common_req_files.VolleyCallback;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class PropertiesForm extends AppCompatActivity implements View.OnClickListener {

    RadioGroup propertytags;
    AppCompatRadioButton buybtn,rentbtn;
    AppCompatSpinner buypropertytype,rentpropertytype;
    MaterialButton searchbutton;
    String adtype,propertytype;
    ProgressDialog loading = null;
    String results;
    ArrayList<String> propertytypes,city,bedrooms,bathrooms,furnishtype,price,constructionstatus,possessionastatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties_form);

        idLink();
        listenerLink();
        loading = new ProgressDialog(PropertiesForm.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        propertytypes=new ArrayList<String>();city=new ArrayList<String>();bedrooms=new ArrayList<String>();bathrooms=new ArrayList<String>();furnishtype=new ArrayList<String>();price=new ArrayList<String>();constructionstatus=new ArrayList<String>();possessionastatus=new ArrayList<String>();
    }

    private void idLink() {

        propertytags=findViewById(R.id.propertytags);
        buybtn=findViewById(R.id.buybtn);
        rentbtn=findViewById(R.id.rentbtn);
        rentpropertytype=findViewById(R.id.rentpropertytype);
        buypropertytype=findViewById(R.id.buypropertytype);
        searchbutton=findViewById(R.id.searchbutton);
    }

    private void listenerLink() {
        searchbutton.setOnClickListener(this);
        rentbtn.setOnClickListener(this);
        buybtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.rentbtn)
        {
            rentpropertytype.setVisibility(View.VISIBLE);
            buypropertytype.setVisibility(View.GONE);
        }
        if (v.getId()==R.id.buybtn)
        {
            rentpropertytype.setVisibility(View.GONE);
            buypropertytype.setVisibility(View.VISIBLE);
        }
        if(v.getId()==R.id.searchbutton){
            if(rentbtn.isChecked()||buybtn.isChecked()){
                if(rentbtn.isChecked()){
                    adtype="0";
                    if(rentpropertytype.getSelectedItem().toString().trim().equalsIgnoreCase("Select Property Type")){
                        Snackbar.make(findViewById(R.id.propertyformlayout), "Select property type.", Snackbar.LENGTH_LONG)
                                .show();
                        rentpropertytype.requestFocus();
                    }
                    else{
                        String temp=rentpropertytype.getSelectedItem().toString().trim();
                        if(temp.equalsIgnoreCase("Apartment"))
                            propertytype="0";
                        if(temp.equalsIgnoreCase("Independent House"))
                            propertytype="1";
                        if(temp.equalsIgnoreCase("Villa"))
                            propertytype="2";
                        if(temp.equalsIgnoreCase("Hostel"))
                            propertytype="3";
                        if(temp.equalsIgnoreCase("Office Area"))
                            propertytype="4";
                        if(temp.equalsIgnoreCase("Shop Area"))
                            propertytype="5";
                        fillArray();
                        /*if(propertytype=="0"||propertytype=="1"||propertytype=="2"||propertytype=="3"){
                            for(int i=1;i<11;i++) {
                                bedrooms.add(String.valueOf(i));
                                bathrooms.add(String.valueOf(i));
                            }
                            furnishtype.add("0");
                            furnishtype.add("1");
                            furnishtype.add("2");
                        }
                        if(propertytype=="4"||propertytype=="5"){
                            constructionstatus.add("0");
                            constructionstatus.add("1");
                        }*/
                        requestData(0,1,1);
                    }
                }
                else{
                    if(buybtn.isChecked()){
                        adtype="1";
                        if(buypropertytype.getSelectedItem().toString().trim().equalsIgnoreCase("Select Property Type")){
                            Snackbar.make(findViewById(R.id.propertyformlayout), "Select property type.", Snackbar.LENGTH_LONG)
                                    .show();
                            buypropertytype.requestFocus();
                        }
                        else{
                            String temp=buypropertytype.getSelectedItem().toString().trim();
                            if(temp.equalsIgnoreCase("Apartment"))
                                propertytype="0";
                            if(temp.equalsIgnoreCase("Independent House"))
                                propertytype="1";
                            if(temp.equalsIgnoreCase("Villa"))
                                propertytype="2";
                            if(temp.equalsIgnoreCase("Plots"))
                                propertytype="6";
                            fillArray();
                            /*if(propertytype=="0"||propertytype=="1"||propertytype=="2"){
                                for(int i=1;i<11;i++) {
                                    bedrooms.add(String.valueOf(i));
                                    bathrooms.add(String.valueOf(i));
                                }
                                furnishtype.add("0");
                                furnishtype.add("1");
                                furnishtype.add("2");
                                constructionstatus.add("0");
                                constructionstatus.add("1");
                            }
                            if(propertytype=="6"){
                                possessionastatus.add("0");
                                possessionastatus.add("1");
                            }*/
                            requestData(0,1,1);
                        }
                    }
                }
            }
        }
    }

    private void requestData(final int resultshown,final int verified,final int available) {
        loading.setMessage("Searching for Ads...");
        loading.show();
        SearchResult searchResult=new SearchResult();
        searchResult.SearchAd(adtype,propertytypes.toArray(new String[propertytypes.size()]),city.toArray(new String[city.size()]),bedrooms.toArray(new String[bedrooms.size()]),bathrooms.toArray(new String[bathrooms.size()]),furnishtype.toArray(new String[furnishtype.size()]),price.toArray(new String[price.size()]),constructionstatus.toArray(new String[constructionstatus.size()]),possessionastatus.toArray(new String[possessionastatus.size()]),resultshown,verified,available,this, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.d("receivedmsg",result);
                results=result;
                    loading.dismiss();
                    nextActivity();

            }

            @Override
            public void onError(String response) {
                loading.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(PropertiesForm.this);
                if (response.equalsIgnoreCase("error")) {
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
            }

            @Override
            public void onVolleyError() {
                loading.dismiss();
            }
        });
    }

    private void nextActivity() {
        Intent intent = new Intent(PropertiesForm.this, PropertyList.class);
        intent.putExtra("results", results);
        intent.putExtra("adtype",adtype);
        intent.putExtra("propertytype",propertytypes);
        intent.putExtra("city",city);
        intent.putExtra("bedrooms",bedrooms);
        intent.putExtra("bathrooms",bathrooms);
        intent.putExtra("furnishtype",furnishtype);
        intent.putExtra("price",price);
        intent.putExtra("constructionstatus",constructionstatus);
        intent.putExtra("possessionastatus",possessionastatus);
        intent.putExtra("verified","1");
        intent.putExtra("available","1");
        if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(PropertiesForm.this, R.anim.fade_in, R.anim.fade_out);
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    private void fillArray() {
        propertytypes.clear();
        city.clear();
        bedrooms.clear();
        bathrooms.clear();
        furnishtype.clear();
        price.clear();
        constructionstatus.clear();
        possessionastatus.clear();
        propertytypes.add(propertytype);
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        city.add(sp.getString("city",null));
        price.add("0");
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
