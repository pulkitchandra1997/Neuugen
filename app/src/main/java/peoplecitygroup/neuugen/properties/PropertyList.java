package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.AD;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class PropertyList extends AppCompatActivity implements View.OnClickListener {

    ScrollView resultlistlist;
    LinearLayout filtersmanagelist,noadsfoundlayout;
    RecyclerView managelistviewlist;

    Intent intent;
    String adtype,results;
    ProgressDialog loading = null;
    String verified,available;
    ArrayList<String> propertytypes,city,bedrooms,bathrooms,furnishtype,price,constructionstatus,possessionastatus;
    private Ad_Adapter Ad_Adapter;
    ArrayList<AD>adsArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_list);

        idlink();
        listenerlink();
        adsArrayList=new ArrayList<AD>();
        Ad_Adapter=new Ad_Adapter(this,adsArrayList);
        managelistviewlist.setAdapter(Ad_Adapter);
        managelistviewlist.setLayoutManager(new LinearLayoutManager(this));
        intent=getIntent();
        if(intent==null)
            finish();
        adtype=intent.getStringExtra("adtype");
        results=intent.getStringExtra("results");
        propertytypes=intent.getStringArrayListExtra("propertytype");
        city=intent.getStringArrayListExtra("city");
        bedrooms=intent.getStringArrayListExtra("bedrooms");
        bathrooms=intent.getStringArrayListExtra("bathrooms");
        furnishtype=intent.getStringArrayListExtra("furnishtype");
        price=intent.getStringArrayListExtra("price");
        constructionstatus=intent.getStringArrayListExtra("constructionstatus");
        possessionastatus=intent.getStringArrayListExtra("possessionastatus");
        verified=intent.getStringExtra("verified");
        available=intent.getStringExtra("available");
        if(results.trim().equalsIgnoreCase("No Result Found.")){
            noadsfoundlayout.setVisibility(View.VISIBLE);
            resultlistlist.setVisibility(View.GONE);
        }
        else{
            noadsfoundlayout.setVisibility(View.GONE);
            resultlistlist.setVisibility(View.VISIBLE);
            showResult(results);
        }
    }

    public void idlink()
    {
        resultlistlist=findViewById(R.id.resultlistlist);
        filtersmanagelist=findViewById(R.id.filtersmanagelist);
        noadsfoundlayout=findViewById(R.id.noadsfoundlayout);
        managelistviewlist=findViewById(R.id.managelistviewlist);
    }
    private void showResult(String results) {
        try {
            JSONArray resultarray=new JSONArray(results.toString());
            for(int i=0;i<resultarray.length();i++) {
                JSONObject result = resultarray.getJSONObject(i);
                Log.d("checkdata",result.toString());
                adsArrayList.add(new AD(result.getString("uniqueid"), result.getString("mobileno"), result.getString("adtype"), result.getString("houseno"), result.getString("area"), result.getString("city"), result.getString("city_id"), result.getString("landmark"), result.getString("pincode"), result.getString("propertytype"), result.getString("bedrooms"), result.getString("bathrooms"), result.getString("furnishtype"), result.getString("builtuparea"), result.getString("price"), result.getString("constructionstatus"), result.getString("ageofproperty"), result.getString("possessionstatus"), result.getString("length"), result.getString("width"), result.getString("widthoffacingroad"), result.getString("pic1"), result.getString("pic2"), result.getString("pic3"), result.getString("verified"), result.getString("available"), result.getString("created")));
            }
            managelistviewlist.setVisibility(View.VISIBLE);
            noadsfoundlayout.setVisibility(View.GONE);
            Ad_Adapter.notifyDataSetChanged();
        }
        catch (Exception e){
            Log.d("jsonerror",e.toString());
            AlertDialog.Builder builder = new AlertDialog.Builder(PropertyList.this);
            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error in Server.")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
    }



    public void listenerlink(){
        filtersmanagelist.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.filtersmanagelist){
            Intent intent = new Intent(PropertyList.this, FilterManage.class);
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
                ActivityOptions options = ActivityOptions.makeCustomAnimation(PropertyList.this, R.anim.fade_in, R.anim.fade_out);
                startActivityForResult(intent,101, options.toBundle());
            } else {
                startActivityForResult(intent,101);
            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 101) {
            if (resultCode == RESULT_OK) {
                adtype=intent.getStringExtra("adtype");
                propertytypes=intent.getStringArrayListExtra("propertytype");
                city=intent.getStringArrayListExtra("city");
                bedrooms=intent.getStringArrayListExtra("bedrooms");
                bathrooms=intent.getStringArrayListExtra("bathrooms");
                furnishtype=intent.getStringArrayListExtra("furnishtype");
                price=intent.getStringArrayListExtra("price");
                constructionstatus=intent.getStringArrayListExtra("constructionstatus");
                possessionastatus=intent.getStringArrayListExtra("possessionastatus");
                verified=intent.getStringExtra("verified");
                available=intent.getStringExtra("available");
            }
        }
    }
}
