package peoplecitygroup.neuugen.HomeServices.EventServices;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class WeddingPackages extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView trainericon,timericon,cleanicon,normalpkgicon,standardpkgicon,premiumpkgicon,normalprice,standardprice,premiumprice;
    CardView normalpkg,standardpkg,premiumpkg;
    String normalpricetext,standardpricetext,premiumpricetext;
    Intent intent;
    String servicetext;
    String[] ownId=new String[]{UrlNeuugen.weddingShootId,UrlNeuugen.normalWedShootId,UrlNeuugen.standardWedShootId,UrlNeuugen.premiumWedShootId};
    String[] ownparentId=new String[]{UrlNeuugen.photoVideoServiceId,UrlNeuugen.weddingShootId,UrlNeuugen.weddingShootId,UrlNeuugen.weddingShootId};
    ArrayList<JSONObject> childclick=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wedding_packages);

        idLink();
        listenerLink();
        intent=getIntent();
        servicetext=intent.getStringExtra("salonservicetext");
        String result=intent.getStringExtra("jsonobject");

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        timericon.setTypeface(font);
        cleanicon.setTypeface(font);
        trainericon.setTypeface(font);
        premiumpkgicon.setTypeface(font);
        standardpkgicon.setTypeface(font);
        normalpkgicon.setTypeface(font);
        serviceDecode(result);

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
        if(v.getId()==R.id.normalpkg||v.getId()==R.id.standardpkg||v.getId()==R.id.premiumpkg) {
            Intent intent = new Intent(WeddingPackages.this, WeddingShootForm.class);
            if (v.getId() == R.id.normalpkg) {
                intent.putExtra("weddingpackage", "Normal Package");
                intent.putExtra("weddingpackageprice", normalprice.getText().toString().trim());
                intent.putExtra("serviceid", UrlNeuugen.normalWedShootId);

            }
            if (v.getId() == R.id.standardpkg) {
                intent.putExtra("weddingpackage", "Standard Package");
                intent.putExtra("serviceid", UrlNeuugen.standardWedShootId);
                intent.putExtra("weddingpackageprice", standardprice.getText().toString().trim());
            }
            if (v.getId() == R.id.premiumpkg) {
                intent.putExtra("weddingpackage", "Premium Package");
                intent.putExtra("serviceid", UrlNeuugen.premiumWedShootId);
                intent.putExtra("weddingpackageprice", premiumprice.getText().toString().trim());
            }
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(WeddingPackages.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }
    private void serviceDecode(String result) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray serviceId=jsonObject.getJSONArray("serviceid");
            JSONArray parentserviceid=jsonObject.getJSONArray("parentserviceid");
            JSONArray servicename=jsonObject.getJSONArray("servicename");
            JSONArray status=jsonObject.getJSONArray("status");
            JSONArray cost=jsonObject.getJSONArray("cost");
            JSONArray pic1=jsonObject.getJSONArray("pic1");
            JSONArray pic2=jsonObject.getJSONArray("pic2");
            JSONArray pic3=jsonObject.getJSONArray("pic3");
            JSONArray cityactive=jsonObject.getJSONArray("cityactive");
            int lengths[]=new int[]{serviceId.length(),parentserviceid.length(),servicename.length(),status.length(),cost.length(),pic1.length(),pic2.length(),pic3.length(),cityactive.length()};
            boolean flag=true;
            int L=serviceId.length();
            for(int l:lengths)
                if(L!=l) {
                    flag = false;
                    return;
                }
            if(flag){
                if(checkParentActive(serviceId.getString(0),parentserviceid.getString(0),servicename.getString(0),status.getString(0),cost.getString(0),pic1.getString(0),pic2.getString(0),pic3.getString(0),cityactive.getString(0)))
                    checkSubActive(serviceId,parentserviceid,servicename,status,cost,pic1,pic2,pic3,cityactive);
            }
            else {
                //ALERT
                AlertDialog.Builder builder = new AlertDialog.Builder(WeddingPackages.this);
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
        } catch (JSONException e) {
            //ALERT DIALOG
            AlertDialog.Builder builder = new AlertDialog.Builder(WeddingPackages.this);

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
            e.printStackTrace();
            Log.d("receivedmsg",e.toString());
        }
    }
    public void changeService(String ownid,boolean flag,String serviceid,String parentserviceid,String servicename,String status,String cost,String pic1,String pic2,String pic3,String cityactive) {
        String c=ownid.trim();
        if(c== UrlNeuugen.normalWedShootId.trim()){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    normalprice.setVisibility(View.VISIBLE);
                    normalprice.setText(cost.trim());
                }
            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    normalprice.setText("Service not available in this City. Will Come Soon!");
                else
                    normalprice.setText("Service is currently unavailable");
                normalpkg.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                normalpkg.setClickable(false);
            }
        }
        if(c==UrlNeuugen.standardWedShootId.trim()){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    standardprice.setVisibility(View.VISIBLE);
                    standardprice.setText(cost.trim());
                }
            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    standardprice.setText("Service not available in this City. Will Come Soon!");
                else
                    standardprice.setText("Service is currently unavailable");
                standardpkg.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                standardpkg.setClickable(false);
            }
        }
        if(c==UrlNeuugen.premiumWedShootId.trim()){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    premiumprice.setVisibility(View.VISIBLE);
                    premiumprice.setText(cost.trim());
                }

            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    premiumprice.setText("Service not available in this City. Will Come Soon!");
                else
                    premiumprice.setText("Service is currently unavailable");
                premiumpkg.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                premiumpkg.setClickable(false);
            }
        }
    }

    private int findIndex(JSONArray serviceId, String s) throws JSONException {
        int index=-1;
        for(int i=1;i<serviceId.length();i++)
            if(serviceId.getString(i).trim().equalsIgnoreCase(s))
                index=i;
        return index;
    }
    private void checkSubActive(JSONArray serviceId, JSONArray parentserviceid, JSONArray servicename, JSONArray status, JSONArray cost, JSONArray pic1, JSONArray pic2, JSONArray pic3, JSONArray cityactive) {
        try {
            for (int i = 1; i < ownId.length; i++) {
                int index=findIndex(serviceId,ownId[i]);
                if(index>0){
                    if(parentserviceid.getString(index).equalsIgnoreCase(ownparentId[i])){
                        if(cityactive.getString(index).equalsIgnoreCase("1")&&status.getString(index).equalsIgnoreCase("1")) {
                            changeService(ownId[i], true, serviceId.getString(index), parentserviceid.getString(index), servicename.getString(index), status.getString(index), cost.getString(index), pic1.getString(index), pic2.getString(index), pic3.getString(index), cityactive.getString(index));
                            JSONArray childserviceId=new JSONArray();
                            JSONArray childparentserviceid=new JSONArray();
                            JSONArray childservicename=new JSONArray();
                            JSONArray childstatus=new JSONArray();
                            JSONArray childcost=new JSONArray();
                            JSONArray childpic1=new JSONArray();
                            JSONArray childpic2=new JSONArray();
                            JSONArray childpic3=new JSONArray();
                            JSONArray childcityactive=new JSONArray();
                            for(int j=1;j<parentserviceid.length();j++){
                                if(parentserviceid.getString(j).equalsIgnoreCase(ownId[i])){
                                    childserviceId.put(serviceId.getString(j));
                                    childparentserviceid.put(parentserviceid.getString(j));
                                    childservicename.put(servicename.getString(j));
                                    childstatus.put(status.getString(j));
                                    childcost.put(cost.getString(j));
                                    childpic1.put(pic1.getString(j));
                                    childpic2.put(pic2.getString(j));
                                    childpic3.put(pic3.getString(j));
                                    childcityactive.put(cityactive.getString(j));
                                }
                            }
                            JSONObject temp=new JSONObject();
                            temp.put("serviceid",childserviceId);
                            temp.put("parentserviceid",childparentserviceid);
                            temp.put("servicename",childservicename);
                            temp.put("status",childstatus);
                            temp.put("cost",childcost);
                            temp.put("pic1",childpic1);
                            temp.put("pic2",childpic2);
                            temp.put("pic3",childpic3);
                            temp.put("cityactive",childcityactive);
                            childclick.add(temp);
                        }
                        else
                            changeService(ownId[i],false,serviceId.getString(index),parentserviceid.getString(index),servicename.getString(index),status.getString(index),cost.getString(index),pic1.getString(index),pic2.getString(index),pic3.getString(index),cityactive.getString(index));
                    }
                    else{
                        //SERVICE NOT FOUND
                        changeService(ownId[i],false,null,null,null,null,null,null,null,null,null);
                    }
                }
                else {
                    //SERVICE NOT FOUND
                    changeService(ownId[i],false,null,null,null,null,null,null,null,null,null);
                }
            }
            if(serviceId.length()>ownId.length){
            }
        }catch(Exception e){
            //ALERT
            AlertDialog.Builder builder = new AlertDialog.Builder(WeddingPackages.this);

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
    private boolean checkParentActive(String serviceid,String parentserviceid,String servicename,String status,String cost,String pic1,String pic2,String pic3,String cityactive) {
        if(serviceid.equalsIgnoreCase(ownId[0])){
            pic1=pic1.trim();
            pic2=pic2.trim();
            pic3=pic3.trim();
            /*if(pic1!=null&&pic1!=""){
                Picasso.with(this).load(pic1).fit().centerCrop()
                        .placeholder(R.drawable.imgplaceholder)
                        .error(R.drawable.imgplaceholder)
                        .into(hsimg1);
            }
            if(pic2!=null&&pic2!=""){
                Picasso.with(this).load(pic1).fit().centerCrop()
                        .placeholder(R.drawable.imgplaceholder)
                        .error(R.drawable.imgplaceholder)
                        .into(hsimg2);
            }
            if(pic3!=null&&pic3!=""){
                Picasso.with(this).load(pic1).fit().centerCrop()
                        .placeholder(R.drawable.imgplaceholder)
                        .error(R.drawable.imgplaceholder)
                        .into(hsimg3);
            }*/
            rotateImg();
            if(status.trim().equalsIgnoreCase("1")){
                if(cityactive.trim().equalsIgnoreCase("1")){
                    //other task with name and cost
                    return true;
                }
                else{
                    //ALERT
                    AlertDialog.Builder builder = new AlertDialog.Builder(WeddingPackages.this);

                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Service not available in this city. Will come Soon!")
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
            else{
                //ALERT
                AlertDialog.Builder builder = new AlertDialog.Builder(WeddingPackages.this);

                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Service is not available")
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
        else{
            //ALERT
            AlertDialog.Builder builder = new AlertDialog.Builder(WeddingPackages.this);

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
        return false;
    }
    private void rotateImg() {
    }
}
