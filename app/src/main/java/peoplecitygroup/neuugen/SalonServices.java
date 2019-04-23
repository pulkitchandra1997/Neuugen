package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class SalonServices extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView salonservicehead,menhaircutprice,menhaircutmsg,menbeardprice,menbeardmsg,menpartymsg,menpartyprice,womenhaircutprice,womenhaircutmsg,womenpartymsg,womenpartyprice,womenwedprice,womenwedmsg;
    MaterialButton menhaircutbtn,menbeardbtn,menpartybtn,womenhaircutbtn,womenpartymakeupbtn,womenweddingmakeupbtn;
    Intent intent;
    String servicetext;
    CardView menhaircutcard,menbeardcard,menpartycard,womenhaircutcard,womenpartymakeupcard,womenweddingmakeupcard;
    LinearLayout womensalonlayout,mensalonlayout;
    String[] ownId;
    String[] ownparentId;
    ArrayList<JSONObject> childclick=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_services);

        intent=getIntent();
        servicetext=intent.getStringExtra("salonservicetext");
        String result=intent.getStringExtra("jsonobject");
        idLink();
        listenerLink();
        if (servicetext.equalsIgnoreCase("Men Salon Services"))
        {
            mensalonlayout.setVisibility(View.VISIBLE);
            womensalonlayout.setVisibility(View.GONE);
            ownId=new String[]{UrlNeuugen.mensalonServiceId,UrlNeuugen.menHaircutId,UrlNeuugen.menHaircutBeardId,UrlNeuugen.menPartymakeupId};
            ownparentId=new String[]{UrlNeuugen.salonServiceId,UrlNeuugen.mensalonServiceId,UrlNeuugen.mensalonServiceId,UrlNeuugen.mensalonServiceId};
        }else
        if (servicetext.equalsIgnoreCase("Women Salon Services"))
        {
            womensalonlayout.setVisibility(View.VISIBLE);
            mensalonlayout.setVisibility(View.GONE);
            ownId=new String[]{UrlNeuugen.womensalonServiceId,UrlNeuugen.womenhaircutId,UrlNeuugen.womenPartymakeupId,UrlNeuugen.womenWeddingmakeupId};
            ownparentId=new String[]{UrlNeuugen.salonServiceId,UrlNeuugen.womensalonServiceId,UrlNeuugen.womensalonServiceId,UrlNeuugen.womensalonServiceId};
        }
        salonservicehead.setText(servicetext);
        serviceDecode(result);
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
                AlertDialog.Builder builder = new AlertDialog.Builder(SalonServices.this);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(SalonServices.this);

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
        Character c=ownid.trim().charAt(0);
        if(c==UrlNeuugen.menHaircutId.trim().charAt(0)){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    menhaircutprice.setVisibility(View.VISIBLE);
                    menhaircutprice.setText(cost.trim());
                }
            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    menhaircutmsg.setText("Service not available in this City. Will Come Soon!");
                else
                    womenhaircutmsg.setText("Service is currently unavailable");
                menhaircutmsg.setVisibility(View.VISIBLE);
                menhaircutcard.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                menhaircutbtn.setClickable(false);
            }
        }
        if(c==UrlNeuugen.menHaircutBeardId.trim().charAt(0)){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    menbeardprice.setVisibility(View.VISIBLE);
                    menbeardprice.setText(cost.trim());
                }
            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    menbeardmsg.setText("Service not available in this City. Will Come Soon!");
                else
                    menbeardmsg.setText("Service is currently unavailable");
                menbeardmsg.setVisibility(View.VISIBLE);
                menbeardcard.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                menbeardbtn.setClickable(false);
            }
        }
        if(c==UrlNeuugen.menPartymakeupId.trim().charAt(0)){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    menpartyprice.setVisibility(View.VISIBLE);
                    menpartyprice.setText(cost.trim());
                }

            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    menpartymsg.setText("Service not available in this City. Will Come Soon!");
                else
                    menpartymsg.setText("Service is currently unavailable");
                menpartymsg.setVisibility(View.VISIBLE);
                menpartycard.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                menpartybtn.setClickable(false);
            }
        }
        if(c==UrlNeuugen.womenhaircutId.trim().charAt(0)){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    womenhaircutprice.setVisibility(View.VISIBLE);
                    womenhaircutprice.setText(cost.trim());
                }

            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    womenhaircutmsg.setText("Service not available in this City. Will Come Soon!");
                else
                    womenhaircutmsg.setText("Service is currently unavailable");
                womenhaircutmsg.setVisibility(View.VISIBLE);
                womenhaircutcard.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                womenhaircutbtn.setClickable(false);
            }
        }
        if(c==UrlNeuugen.womenPartymakeupId.trim().charAt(0)){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    womenpartyprice.setVisibility(View.VISIBLE);
                    womenpartyprice.setText(cost.trim());
                }

            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    womenpartymsg.setText("Service not available in this City. Will Come Soon!");
                else
                    womenpartymsg.setText("Service is currently unavailable");
                womenpartymsg.setVisibility(View.VISIBLE);
                womenpartymakeupcard.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                womenpartymakeupbtn.setClickable(false);
            }
        }
        if(c==UrlNeuugen.womenWeddingmakeupId.trim().charAt(0)){
            if(flag){
                if(cost!=null&&cost.trim()!=""&&!cost.equalsIgnoreCase("null")) {
                    womenwedprice.setVisibility(View.VISIBLE);
                    womenwedprice.setText(cost.trim());
                }
            }
            else{
                if(cityactive.equalsIgnoreCase("0"))
                    womenwedmsg.setText("Service not available in this City. Will Come Soon!");
                else
                    womenwedmsg.setText("Service is currently unavailable");
                womenwedmsg.setVisibility(View.VISIBLE);
                womenweddingmakeupcard.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                womenweddingmakeupbtn.setClickable(false);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(SalonServices.this);

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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SalonServices.this);

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
                AlertDialog.Builder builder = new AlertDialog.Builder(SalonServices.this);

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
            AlertDialog.Builder builder = new AlertDialog.Builder(SalonServices.this);

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
    public void idLink()
    {
        salonservicehead=findViewById(R.id.salonservicehead);
        mensalonlayout=findViewById(R.id.mensalonlayout);
        womensalonlayout=findViewById(R.id.womensalonlayout);
        menhaircutbtn=findViewById(R.id.menhaircutbtn);
        menbeardbtn=findViewById(R.id.menbeardbtn);
        menpartybtn=findViewById(R.id.menpartybtn);
        womenhaircutbtn=findViewById(R.id.womenhaircutbtn);
        womenpartymakeupbtn=findViewById(R.id.womenpartymakeupbtn);
        womenweddingmakeupbtn=findViewById(R.id.womenweddingmakeupbtn);
        menhaircutprice=findViewById(R.id.menhaircutprice);
        womenhaircutprice=findViewById(R.id.womenhaircutprice);
        menbeardprice=findViewById(R.id.menbeardprice);
        womenpartyprice=findViewById(R.id.womenpartymakeupprice);
        menhaircutmsg=findViewById(R.id.menhaircutmsg);
        menbeardmsg=findViewById(R.id.menbeardmsg);
        menpartymsg=findViewById(R.id.menpartymsg);
        womenwedmsg=findViewById(R.id.womenweddingmsg);
        womenwedprice=findViewById(R.id.womenweddingmakeupprice);
        womenpartymsg=findViewById(R.id.womenpartymsg);
        womenhaircutmsg=findViewById(R.id.womenhaircutmsg);
        menpartyprice=findViewById(R.id.menpartyprice);
        menbeardcard=findViewById(R.id.menbeardcard);
        menhaircutcard=findViewById(R.id.menhaircutcard);
        menpartycard=findViewById(R.id.menpartycard);
        womenpartymakeupcard=findViewById(R.id.womenpartymakeupcard);
        womenhaircutcard=findViewById(R.id.womenhaircutcard);
        womenweddingmakeupcard=findViewById(R.id.womenweddingmakeupcard);
    }

    public void listenerLink()
    {
        menhaircutbtn.setOnClickListener(this);
        menbeardbtn.setOnClickListener(this);
        menpartybtn.setOnClickListener(this);
        womenweddingmakeupbtn.setOnClickListener(this);
        womenpartymakeupbtn.setOnClickListener(this);
        womenhaircutbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.menhaircutbtn||v.getId()==R.id.menbeardbtn||v.getId()==R.id.menpartybtn||v.getId()==R.id.womenhaircutbtn||v.getId()==R.id.womenpartymakeupbtn||v.getId()==R.id.womenweddingmakeupbtn) {
            Intent intent = new Intent(SalonServices.this, SalonServiceForm.class);
            if (v.getId() == R.id.menhaircutbtn) {
                intent.putExtra("servicetype", "Men's Haircut");
                intent.putExtra("serviceprice", menhaircutprice.getText().toString().trim());
                intent.putExtra("serviceid", UrlNeuugen.menHaircutId);
            }
            if (v.getId() == R.id.menbeardbtn) {
                intent.putExtra("servicetype", "Men's Haircut & Beard");
                intent.putExtra("serviceprice", menbeardprice.getText().toString().trim());
                intent.putExtra("serviceid", UrlNeuugen.menHaircutBeardId);
            }
            if (v.getId() == R.id.menpartybtn) {
                intent.putExtra("servicetype", "Men's Party Grooming");
                intent.putExtra("serviceprice", menpartyprice.getText().toString().trim());
                intent.putExtra("serviceid", UrlNeuugen.menPartymakeupId);
            }

            if (v.getId() == R.id.womenhaircutbtn) {
                intent.putExtra("servicetype", "Women's Haircut & styling");
                intent.putExtra("serviceprice", womenhaircutprice.getText().toString().trim());
                intent.putExtra("serviceid", UrlNeuugen.womenhaircutId);
            }
            if (v.getId() == R.id.womenpartymakeupbtn) {
                intent.putExtra("servicetype", "Women's Party makeup");
                intent.putExtra("serviceprice", womenpartyprice.getText().toString().trim());
                intent.putExtra("serviceid", UrlNeuugen.womenPartymakeupId);
            }
            if (v.getId() == R.id.womenweddingmakeupbtn) {
                intent.putExtra("servicetype", "Women's Wedding makeup ");
                intent.putExtra("serviceprice", womenwedprice.getText().toString().trim());
                intent.putExtra("serviceid", UrlNeuugen.womenWeddingmakeupId);
            }
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(SalonServices.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }
}
