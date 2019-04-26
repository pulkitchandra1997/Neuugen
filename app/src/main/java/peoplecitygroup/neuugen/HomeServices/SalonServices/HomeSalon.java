package peoplecitygroup.neuugen.HomeServices.SalonServices;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.service.ServiceCheck;
import peoplecitygroup.neuugen.service.UrlNeuugen;
import peoplecitygroup.neuugen.service.VolleyCallback;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class HomeSalon extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView trainericon,timericon,cleanicon,mensalonicon,womensalonicon,mensalonmsg,womensalonmsg;
    CardView mensalonservice,womensalonservice;
    AppCompatImageView hsimg1,hsimg2,hsimg3;
    ProgressDialog loading = null;
    SharedPreferences sp;
    String[] ownId=new String[]{UrlNeuugen.salonServiceId,UrlNeuugen.mensalonServiceId,UrlNeuugen.womensalonServiceId};
    String[] ownparentId=new String[]{"0",UrlNeuugen.salonServiceId,UrlNeuugen.salonServiceId};
    ArrayList<JSONObject>childclick=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_salon);

        idLink();
        listenerLink();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        timericon.setTypeface(font);
        cleanicon.setTypeface(font);
        trainericon.setTypeface(font);
        mensalonicon.setTypeface(font);
        womensalonicon.setTypeface(font);

        loading = new ProgressDialog(HomeSalon.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        checkActive();
    }

    private void checkActive() {
        String city=sp.getString("city",null);
        loading.show();
        ServiceCheck serviceCheck=new ServiceCheck();
        serviceCheck.check(UrlNeuugen.salonServiceId,city, this, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                loading.dismiss();
                serviceDecode(result);
                Log.d("receivedmsg",result);
            }

            @Override
            public void onError(String response) {
                loading.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeSalon.this);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeSalon.this);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeSalon.this);

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

    private void checkSubActive(JSONArray serviceId, JSONArray parentserviceid, JSONArray servicename, JSONArray status, JSONArray cost, JSONArray pic1, JSONArray pic2, JSONArray pic3, JSONArray cityactive) {
        try {
            for (int i = 1; i < ownId.length; i++) {
                int index=findIndex(serviceId,ownId[i]);
                if(index>0){
                    if(parentserviceid.getString(index).equalsIgnoreCase(ownparentId[i])){
                        if(cityactive.getString(index).equalsIgnoreCase("1")&&status.getString(index).equalsIgnoreCase("1"))
                            changeService(ownId[i], true, serviceId.getString(index), parentserviceid.getString(index), servicename.getString(index), status.getString(index), cost.getString(index), pic1.getString(index), pic2.getString(index), pic3.getString(index), cityactive.getString(index));

                        else
                            changeService(ownId[i], false, serviceId.getString(index), parentserviceid.getString(index), servicename.getString(index), status.getString(index), cost.getString(index), pic1.getString(index), pic2.getString(index), pic3.getString(index), cityactive.getString(index));

                        JSONArray childserviceId=new JSONArray();
                        JSONArray childparentserviceid=new JSONArray();
                        JSONArray childservicename=new JSONArray();
                        JSONArray childstatus=new JSONArray();
                        JSONArray childcost=new JSONArray();
                        JSONArray childpic1=new JSONArray();
                        JSONArray childpic2=new JSONArray();
                        JSONArray childpic3=new JSONArray();
                        JSONArray childcityactive=new JSONArray();
                        childserviceId.put(serviceId.getString(i));
                        childparentserviceid.put(parentserviceid.getString(i));
                        childservicename.put(servicename.getString(i));
                        childstatus.put(status.getString(i));
                        childcost.put(cost.getString(i));
                        childpic1.put(pic1.getString(i));
                        childpic2.put(pic2.getString(i));
                        childpic3.put(pic3.getString(i));
                        childcityactive.put(cityactive.getString(i));
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
                        Log.d("childclick",temp.toString());
                        childclick.add(temp);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeSalon.this);

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

    public void changeService(String ownid,boolean flag,String serviceid,String parentserviceid,String servicename,String status,String cost,String pic1,String pic2,String pic3,String cityactive) {
        Character c=ownid.trim().charAt(0);
        if(c==UrlNeuugen.mensalonServiceId.trim().charAt(0)){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    mensalonmsg.setVisibility(View.VISIBLE);
                    mensalonmsg.setText(cost.trim());
                }
            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    mensalonmsg.setText("Service not available in this City. Will Come Soon!");
                else
                    mensalonmsg.setText("Service is currently unavailable");
                mensalonmsg.setVisibility(View.VISIBLE);
                mensalonservice.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                mensalonservice.setClickable(false);
            }
        }
        if(c==UrlNeuugen.womensalonServiceId.trim().charAt(0)){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    womensalonmsg.setVisibility(View.VISIBLE);
                    womensalonmsg.setText(cost.trim());
                }
            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    womensalonmsg.setText("Service not available in this City. Will Come Soon!");
                else
                    womensalonmsg.setText("Service is currently unavailable");
                womensalonmsg.setVisibility(View.VISIBLE);
                womensalonservice.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                womensalonservice.setClickable(false);
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


    private boolean checkParentActive(String serviceid,String parentserviceid,String servicename,String status,String cost,String pic1,String pic2,String pic3,String cityactive) {
        if(serviceid.equalsIgnoreCase(ownId[0])){
            pic1=pic1.trim();
            pic2=pic2.trim();
            pic3=pic3.trim();
            if(pic1!=null&&pic1!=""){
                Picasso.with(this).load(pic1).fit().centerCrop()
                        .placeholder(R.drawable.imgplaceholder)
                        .error(R.drawable.imgplaceholder)
                        .into(hsimg1);
            }
            if(pic2!=null&&pic2!=""){
                Picasso.with(this).load(pic2).fit().centerCrop()
                        .placeholder(R.drawable.imgplaceholder)
                        .error(R.drawable.imgplaceholder)
                        .into(hsimg2);
            }
            if(pic3!=null&&pic3!=""){
                Picasso.with(this).load(pic3).fit().centerCrop()
                        .placeholder(R.drawable.imgplaceholder)
                        .error(R.drawable.imgplaceholder)
                        .into(hsimg3);
            }
            rotateImg();
            if(status.trim().equalsIgnoreCase("1")){
                if(cityactive.trim().equalsIgnoreCase("1")){
                    //other task with name and cost
                    return true;
                }
                else{
                    //ALERT
                    AlertDialog.Builder builder = new AlertDialog.Builder(HomeSalon.this);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(HomeSalon.this);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeSalon.this);

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

    public void listenerLink() {
        mensalonservice.setOnClickListener(this);
        womensalonservice.setOnClickListener(this);

    }


    public void idLink()
    {
        cleanicon=findViewById(R.id.cleanicon);
        trainericon=findViewById(R.id.trainericon);
        timericon=findViewById(R.id.timericon);
        mensalonicon=findViewById(R.id.mensalonicon);
        womensalonicon=findViewById(R.id.womensalonicon);
        mensalonservice=findViewById(R.id.mensalonservice);
        womensalonservice=findViewById(R.id.womensalonservice);
        hsimg1=findViewById(R.id.hsimg1);
        hsimg2=findViewById(R.id.hsimg2);
        hsimg3=findViewById(R.id.hsimg3);
        mensalonmsg=findViewById(R.id.mensalonmsg);
        womensalonmsg=findViewById(R.id.womensalonmsg);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.mensalonservice||v.getId()==R.id.womensalonservice) {
            Intent intent = new Intent(HomeSalon.this, SalonServices.class);
            if (v.getId() == R.id.mensalonservice) {
                intent.putExtra("salonservicetext", "Men Salon Services");
                intent.putExtra("jsonobject", childclick.get(0).toString());
            }
            if (v.getId() == R.id.womensalonservice) {
                intent.putExtra("salonservicetext", "Women Salon Services");
                intent.putExtra("jsonobject", childclick.get(1).toString());
            }
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(HomeSalon.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }
}
