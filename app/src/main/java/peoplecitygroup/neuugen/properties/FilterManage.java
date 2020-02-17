package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.appyvet.materialrangebar.RangeBar;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.SearchResult;
import peoplecitygroup.neuugen.common_req_files.VolleyCallback;

public class FilterManage extends AppCompatActivity implements View.OnClickListener {
    LinearLayout filterlayoutmanagelist,filtermanage;
    RadioGroup propertytagslist;
    String number,MinPrice,MaxPrice;
    ProgressDialog loading = null;
    String results;
    RadioButton buybtnlist,rentbtnlist;
    CardView furnishtypeform,propertytypecardlist,constatuscardlist,posesnstatuscardlist,pricecardlist,bedcardlist,bathcardlist;
    Spinner renttypeFIlist,buytypeFIlist;
    CheckBox fullfurnishFIlist,semifurnishFIlist,unfurnishFIlist,readytomoveFIlist,underconFIlist,immediateFIlist,infutureFIlist;
    CrystalRangeSeekbar bedrangeSeekbar,bathrangeSeekbar;
    TextView minprice,maxprice,minbed,maxbed,minbath,maxbath;
    SwitchCompat verifiedtogglelist,availabletogglelist;
    MaterialButton cancelbtnmlist,applybtnmlist,showallbtnmlist;
    AppCompatSpinner buypropertytype,rentpropertytype;
    ArrayList<String> propertytypes=new ArrayList<String>(),city=new ArrayList<String>(),bedrooms=new ArrayList<String>(),bathrooms=new ArrayList<String>(),furnishtype=new ArrayList<String>(),price=new ArrayList<String>(),constructionstatus=new ArrayList<String>(),possessionastatus=new ArrayList<String>();
    String adtype=null,propertytype=null,verified="1",available="1";
    Boolean flag_bath_bed=false;
    Intent resultIntent = null;
    RangeBar pricerangeSeekbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_manage);
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        number=sp.getString("mobileno", null);
        loading = new ProgressDialog(FilterManage.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        resultIntent = new Intent(FilterManage.this,PropertyList.class);
        idlink();
        listenerlink();
        applybtnmlist.setEnabled(false);

        Intent intent=getIntent();
        if(intent==null){
            finish();
        }
        MinPrice=intent.getStringExtra("minprice");
        MaxPrice=intent.getStringExtra("maxprice");

       /* pricerangeSeekbar.setMinStartValue(Integer.parseInt(MinPrice));
        pricerangeSeekbar.setMaxStartValue(Integer.parseInt(MaxPrice));

        pricerangeSeekbar.setLeft(Integer.parseInt(MinPrice));
        pricerangeSeekbar.setRight(Integer.parseInt(MaxPrice));

        pricerangeSeekbar.setMinValue(Integer.parseInt(MinPrice));
        pricerangeSeekbar.setMaxValue(Integer.parseInt(MaxPrice));*/



    }
    public void idlink()
    {
        filtermanage=findViewById(R.id.filtermanage);
        filterlayoutmanagelist=findViewById(R.id.filterlayoutmanagelist);
        propertytagslist=findViewById(R.id.propertytagslist);
        buybtnlist=findViewById(R.id.buybtnlist);
        rentbtnlist=findViewById(R.id.rentbtnlist);
        propertytypecardlist=findViewById(R.id.propertytypecardlist);
        constatuscardlist=findViewById(R.id.constatuscardlist);
        posesnstatuscardlist=findViewById(R.id.posesnstatuscardlist);
        pricecardlist=findViewById(R.id.pricecardlist);
        bedcardlist=findViewById(R.id.bedcardlist);
        bathcardlist=findViewById(R.id.bathcardlist);
        renttypeFIlist=findViewById(R.id.renttypeFIlist);
        buytypeFIlist=findViewById(R.id.buytypeFIlist);
        fullfurnishFIlist=findViewById(R.id.fullfurnishFIlist);
        semifurnishFIlist=findViewById(R.id.semifurnishFIlist);
        unfurnishFIlist=findViewById(R.id.unfurnishFIlist);
        readytomoveFIlist=findViewById(R.id.readytomoveFIlist);
        underconFIlist=findViewById(R.id.underconFIlist);
        immediateFIlist=findViewById(R.id.immediateFIlist);
        infutureFIlist=findViewById(R.id.infutureFIlist);
        pricerangeSeekbar=findViewById(R.id.pricerangeSeekbar);
        bathrangeSeekbar=findViewById(R.id.bathrangeSeekbar);
        bedrangeSeekbar=findViewById(R.id.bedrangeSeekbar);
        minprice=findViewById(R.id.minprice);
        maxprice=findViewById(R.id.maxprice);
        minbath=findViewById(R.id.minbath);
        maxbed=findViewById(R.id.maxbed);
        minbed=findViewById(R.id.minbed);
        maxbath=findViewById(R.id.maxbath);
        verifiedtogglelist=findViewById(R.id.verifiedtogglelist);
        availabletogglelist=findViewById(R.id.availabletogglelist);
        applybtnmlist=findViewById(R.id.applybtnmlist);
        cancelbtnmlist=findViewById(R.id.cancelbtnmlist);
        showallbtnmlist=findViewById(R.id.showallbtnmlist);
        rentpropertytype=findViewById(R.id.rentpropertytypeli);
        buypropertytype=findViewById(R.id.buypropertytypeli);
        furnishtypeform=findViewById(R.id.furnishtypeform);


    }
    public void listenerlink(){
        showallbtnmlist.setOnClickListener(this);
        cancelbtnmlist.setOnClickListener(this);
        applybtnmlist.setOnClickListener(this);
        rentbtnlist.setOnClickListener(this);
        buybtnlist.setOnClickListener(this);
        propertytagslist.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                constatuscardlist.setVisibility(View.GONE);
                furnishtypeform.setVisibility(View.GONE);
                posesnstatuscardlist.setVisibility(View.GONE);
                bedcardlist.setVisibility(View.GONE);
                bathcardlist.setVisibility(View.GONE);
                furnishTypeDefault(false);
                constructionDefault(false);
                possessionDefault(false);
                bedDefault(false);
                bathDefault(false);
                flag_bath_bed=false;
                propertytype=null;
                if(checkedId == R.id.buybtnlist)
                    adtype="1";
                if (checkedId == R.id.rentbtnlist)
                    adtype="0";
                applybtnmlist.setEnabled(true);
            }
        });
        rentpropertytype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0) {
                    if (position < 5) {
                        furnishtypeform.setVisibility(View.VISIBLE);
                        furnishTypeDefault(false);

                        constatuscardlist.setVisibility(View.GONE);
                        constructionDefault(false);

                        bedcardlist.setVisibility(View.VISIBLE);
                        bathcardlist.setVisibility(View.VISIBLE);
                        bedDefault(false);
                        bathDefault(false);
                        flag_bath_bed=true;
                    } else {
                        furnishtypeform.setVisibility(View.GONE);
                        furnishTypeDefault(false);
                        constatuscardlist.setVisibility(View.VISIBLE);
                        constructionDefault(false);

                        bedcardlist.setVisibility(View.GONE);
                        bathcardlist.setVisibility(View.GONE);
                        bedDefault(false);
                        bathDefault(false);
                        flag_bath_bed=false;
                    }
                }
                else{
                    constatuscardlist.setVisibility(View.GONE);
                    furnishtypeform.setVisibility(View.GONE);
                    posesnstatuscardlist.setVisibility(View.GONE);
                    bedcardlist.setVisibility(View.GONE);
                    bathcardlist.setVisibility(View.GONE);
                    flag_bath_bed=false;
                    furnishTypeDefault(false);
                    constructionDefault(false);
                    possessionDefault(false);
                    bedDefault(false);
                    bathDefault(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        buypropertytype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    if (position < 4) {
                        furnishtypeform.setVisibility(View.VISIBLE);
                        furnishTypeDefault(false);

                        constatuscardlist.setVisibility(View.VISIBLE);
                        constructionDefault(false);

                        posesnstatuscardlist.setVisibility(View.GONE);
                        possessionDefault(false);
                        bedcardlist.setVisibility(View.VISIBLE);
                        bathcardlist.setVisibility(View.VISIBLE);
                        flag_bath_bed=true;
                        bedDefault(false);
                        bathDefault(false);
                    } else {
                        furnishtypeform.setVisibility(View.GONE);
                        constatuscardlist.setVisibility(View.GONE);
                        furnishTypeDefault(false);
                        constructionDefault(false);
                        posesnstatuscardlist.setVisibility(View.VISIBLE);
                        possessionDefault(false);

                        bedcardlist.setVisibility(View.GONE);
                        bathcardlist.setVisibility(View.GONE);
                        flag_bath_bed=false;
                        bedDefault(false);
                        bathDefault(false);
                    }
                }
                else{
                    furnishtypeform.setVisibility(View.GONE);
                    constatuscardlist.setVisibility(View.GONE);
                    posesnstatuscardlist.setVisibility(View.GONE);
                    bedcardlist.setVisibility(View.GONE);
                    bathcardlist.setVisibility(View.GONE);
                    flag_bath_bed=false;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.rentbtnlist)
        {
            rentpropertytype.setVisibility(View.VISIBLE);
            buypropertytype.setVisibility(View.GONE);
        }
        if (v.getId()==R.id.buybtnlist)
        {
            rentpropertytype.setVisibility(View.GONE);
            buypropertytype.setVisibility(View.VISIBLE);
        }
        if(v.getId() == R.id.applybtnmlist){
            sendDataBack(true);
        }
        if(v.getId()==R.id.showallbtnmlist){
            sendDataBack(false);
        }
    }

    private void sendDataBack(boolean b) {

        if(b){
            if(rentbtnlist.isChecked()||buybtnlist.isChecked()&&(rentpropertytype.getSelectedItemPosition()!=0||buypropertytype.getSelectedItemPosition()!=0)) {
                if (rentbtnlist.isChecked()) {
                    adtype="0";
                    if(rentpropertytype.getSelectedItem().toString().trim().equalsIgnoreCase("Select Property Type")){
                        Snackbar.make(findViewById(R.id.filtermanage), "Select property type.", Snackbar.LENGTH_LONG)
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
                    }
                }
                if (buybtnlist.isChecked()) {
                    adtype="1";
                    if(buypropertytype.getSelectedItem().toString().trim().equalsIgnoreCase("Select Property Type")){
                        Snackbar.make(findViewById(R.id.filtermanage), "Select property type.", Snackbar.LENGTH_LONG)
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
                    }
                }
            }
            else{
                Log.i("INFO", "sendDataBack: here2");
                setResult(RESULT_CANCELED);
                finish();
            }
        }
        else{
            propertytype=null;
            fillArray();
        }
        /*resultIntent.putExtra("results", 0);
        resultIntent.putExtra("adtype",adtype);
        resultIntent.putExtra("propertytype",propertytypes);
        resultIntent.putExtra("city",city);
        resultIntent.putExtra("bedrooms",bedrooms);
        resultIntent.putExtra("bathrooms",bathrooms);
        resultIntent.putExtra("furnishtype",furnishtype);
        resultIntent.putExtra("price",price);
        resultIntent.putExtra("constructionstatus",constructionstatus);
        resultIntent.putExtra("possessionastatus",possessionastatus);
        resultIntent.putExtra("verified","1");
        resultIntent.putExtra("available","1");*/
        requestnewData();

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
        if(propertytype!=null)
            propertytypes.add(propertytype);
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        city.add(sp.getString("city",null));
        /*price.add(pricerangeSeekbar.getSelectedMinValue().toString());
        price.add(pricerangeSeekbar.getSelectedMaxValue().toString());*/
        if(fullfurnishFIlist.isChecked())
            furnishtype.add("0");
        if(semifurnishFIlist.isChecked())
            furnishtype.add("1");
        if(unfurnishFIlist.isChecked())
            furnishtype.add("2");
        if(readytomoveFIlist.isChecked())
            constructionstatus.add("0");
        if(underconFIlist.isChecked())
            constructionstatus.add("1");
        if(immediateFIlist.isChecked())
            possessionastatus.add("0");
        if(infutureFIlist.isChecked())
            possessionastatus.add("1");
        if(flag_bath_bed){

            bedrooms.add(bedrangeSeekbar.getSelectedMinValue().toString());
            bedrooms.add(bedrangeSeekbar.getSelectedMaxValue().toString());
            bathrooms.add(bathrangeSeekbar.getSelectedMinValue().toString());
            bathrooms.add(bathrangeSeekbar.getSelectedMaxValue().toString());
        }
    }

    void furnishTypeDefault(boolean value){
        fullfurnishFIlist.setSelected(value);
        semifurnishFIlist.setSelected(value);
        unfurnishFIlist.setSelected(value);
    }
    void constructionDefault(boolean value){
        readytomoveFIlist.setSelected(value);
        underconFIlist.setSelected(value);
    }
    void possessionDefault(boolean value){
        immediateFIlist.setSelected(value);
        infutureFIlist.setSelected(value);
    }
    void bedDefault(boolean value){
        bedrangeSeekbar.setMinValue(0);
        bedrangeSeekbar.setMaxValue(10);
    }
    void bathDefault(boolean value){
        bathrangeSeekbar.setMinValue(0);
        bathrangeSeekbar.setMaxValue(10);
    }


    private void requestnewData() {
        SearchResult searchResult=new SearchResult();
        searchResult.SearchAd(adtype,propertytypes.toArray(new String[propertytypes.size()]),city.toArray(new String[city.size()]),bedrooms.toArray(new String[bedrooms.size()]),bathrooms.toArray(new String[bathrooms.size()]),furnishtype.toArray(new String[furnishtype.size()]),price.toArray(new String[price.size()]),constructionstatus.toArray(new String[constructionstatus.size()]),possessionastatus.toArray(new String[possessionastatus.size()]),0,Integer.parseInt(verified),Integer.parseInt(available),number,this, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                Log.i("CheckResult",result);
                results=result;
                loading.dismiss();
                resultIntent.putExtra("result",results);
                setResult(RESULT_OK,resultIntent);
                finish();
            }

            @Override
            public void onError(String response) {
                loading.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(FilterManage.this);
                if (response.toLowerCase().trim().contains("error:")) {
                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Error in server. Try Again")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish
                                    //resultIntent.putExtra("result",results);
                                    setResult(RESULT_CANCELED,resultIntent);
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

            @Override
            public void onVolleyError() {
                loading.dismiss();
            }
        });
    }
}
