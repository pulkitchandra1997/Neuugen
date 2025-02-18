package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.UserMainActivity;
import peoplecitygroup.neuugen.common_req_files.AD;
import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.SendMail;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.common_req_files.Validation;
import peoplecitygroup.neuugen.common_req_files.VolleyCallback;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class RentHouses extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView backtopost;

    TextInputEditText arearh,cityrh,landmarkrh,builtarearh,monthlyrentrh,housenorh,pincoderh;
    boolean chk = true;
    MaterialButton submitrhform,addimgrh1,addimgrh2,addimgrh3;

    AppCompatSpinner propertytyperh,numofbedrh,numofbathrh;

    ChipGroup rhfurnishtype;

    Chip rhfullyfurnish,rhsemifurnish,rhunfurnish;

    LinearLayout rhmainlayout;

    AppCompatImageView imgrh1,imgrh2,imgrh3;
    int imageflag=0;
    Bitmap img1,img2,img3;
    String propertytype,arearhtext,cityrhtext,landmarkrhtext,builtarearhtext,pincoderhtext,monthlyrentrhtext,housenorhtext,propertytyperhtext,numofbedrhtext,numofbathrhtext,rhfurnishtyypetext;
    ProgressDialog loading = null;
JSONObject jsonObject= new JSONObject();
String uniqueid=null;
Intent intent=null;
AD ad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_houses);

        idLink();
        listenerLink();
        hideSoftKeyboard();
        loading = new ProgressDialog(RentHouses.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backtopost.setTypeface(font);

        intent=getIntent();
        cityrhtext=intent.getStringExtra("city").toUpperCase();
        cityrh.setText(cityrhtext.toUpperCase());
        cityrh.setEnabled(false);

        Intent intent=getIntent();
        if(intent!=null) {
            ad = (AD) intent.getSerializableExtra("object");
            //fill(ad);
        }
    }

    /*private void fill(AD ad) {
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
            checkStatus();
            //contactbtn.setText("SHOW INTEREST");
            flag=false;
        }
    }*/

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void listenerLink()
    {
        backtopost.setOnClickListener(this);
        submitrhform.setOnClickListener(this);
        addimgrh1.setOnClickListener(this);
        addimgrh2.setOnClickListener(this);
        addimgrh3.setOnClickListener(this);

    }

    public void idLink()
    {
        backtopost=findViewById(R.id.backtopost);
        arearh=findViewById(R.id.arearh);
        cityrh=findViewById(R.id.cityrh);
        landmarkrh=findViewById(R.id.landmarkrh);
        builtarearh=findViewById(R.id.builtarearh);
        monthlyrentrh=findViewById(R.id.monthlyrentrh);
        propertytyperh=findViewById(R.id.propertytyperh);
        numofbedrh=findViewById(R.id.numofbedrh);
        numofbathrh=findViewById(R.id.numofbathrh);
        submitrhform=findViewById(R.id.submitrhform);
        rhfurnishtype=findViewById(R.id.rhfurnishtype);
        rhfullyfurnish=findViewById(R.id.rhfullyfurnish);
        rhsemifurnish=findViewById(R.id.rhsemifurnish);
        rhunfurnish=findViewById(R.id.rhunfurnish);
        housenorh=findViewById(R.id.housenorh);
        rhmainlayout=findViewById(R.id.rhmainlayout);
        imgrh1=findViewById(R.id.imgrh1);
        imgrh2=findViewById(R.id.imgrh2);
        imgrh3=findViewById(R.id.imgrh3);
        addimgrh1=findViewById(R.id.addimgrh1);
        addimgrh2=findViewById(R.id.addimgrh2);
        addimgrh3=findViewById(R.id.addimgrh3);
        pincoderh=findViewById(R.id.pincoderh);

    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                switch (imageflag){
                    case 1:img1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgrh1.setImageBitmap(img1);
                            break;
                    case 2:img2=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgrh2.setImageBitmap(img2);
                            break;
                    case 3:img3=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                            imgrh3.setImageBitmap(img3);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                imageflag=0;
            }
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.addimgrh1){
            imageflag=1;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
        }
        if (v.getId()==R.id.addimgrh2){
            imageflag=2;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
        }
        if (v.getId()==R.id.addimgrh3){
            imageflag=3;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
        }
        if(v.getId()==R.id.backtopost)
        {
            onBackPressed();
        }
        if (v.getId()==R.id.submitrhform)
        {

            arearhtext=arearh.getText().toString().trim();
            housenorhtext=housenorh.getText().toString().trim();
            cityrhtext=cityrh.getText().toString().trim();
            landmarkrhtext=landmarkrh.getText().toString().trim();
            builtarearhtext=builtarearh.getText().toString().trim();
            monthlyrentrhtext=monthlyrentrh.getText().toString().trim();
            propertytyperhtext=propertytyperh.getSelectedItem().toString();
            numofbedrhtext=String.valueOf(numofbedrh.getSelectedItemPosition());
            numofbathrhtext=String.valueOf(numofbathrh.getSelectedItemPosition());
            pincoderhtext=pincoderh.getText().toString().trim();

            if (propertytyperhtext.equalsIgnoreCase("Apartment"))
            {
                propertytype="0";
            }else
            if (propertytyperhtext.equalsIgnoreCase("Independent House"))
            {
                propertytype="1";
            }else
            if (propertytyperhtext.equalsIgnoreCase("Villa"))
            {
                propertytype="2";
            }else
            if (propertytyperhtext.equalsIgnoreCase("Hostel"))
            {
                propertytype="3";
            }
            if (rhfurnishtype.getCheckedChipId()==R.id.rhfullyfurnish)
            {
                rhfurnishtyypetext="0";
            }else
            if (rhfurnishtype.getCheckedChipId()==R.id.rhsemifurnish)
            {
                rhfurnishtyypetext="1";
            }else
            if (rhfurnishtype.getCheckedChipId()==R.id.rhunfurnish)
            {
                rhfurnishtyypetext="2";
            }

            if (TextUtils.isEmpty(pincoderhtext) ||TextUtils.isEmpty(arearhtext) || TextUtils.isEmpty(housenorhtext)||TextUtils.isEmpty(cityrhtext)||TextUtils.isEmpty(builtarearhtext)||TextUtils.isEmpty(monthlyrentrhtext)||propertytyperhtext.equalsIgnoreCase("Select Property Type")||numofbathrh.getSelectedItem().toString().equalsIgnoreCase("Select Number")||numofbedrh.getSelectedItem().toString().equalsIgnoreCase("Select Number")||!(rhfurnishtype.getCheckedChipId()==R.id.rhfullyfurnish)&&!(rhfurnishtype.getCheckedChipId()==R.id.rhsemifurnish)&&!(rhfurnishtype.getCheckedChipId()==R.id.rhunfurnish)||(img1==null)||(img2==null)||(img3==null))
            {
                if (TextUtils.isEmpty(housenorhtext) )
                {
                    housenorh.setError("Enter House Number");
                    housenorh.requestFocus();
                }else
                if (TextUtils.isEmpty(arearhtext) )
                {
                    arearh.setError("Enter Area");
                    arearh.requestFocus();
                }else

                if (TextUtils.isEmpty(cityrhtext) )
                {
                    cityrh.setError("Enter City");
                    cityrh.requestFocus();
                }else
                if (TextUtils.isEmpty(pincoderhtext) )
                {
                    pincoderh.setError("Enter Pincode");
                    pincoderh.requestFocus();
                }else
                if (TextUtils.isEmpty(builtarearhtext) )
                {
                    builtarearh.setError("Enter Built Area");
                    builtarearh.requestFocus();
                }else
                if (TextUtils.isEmpty(monthlyrentrhtext) )
                {
                    monthlyrentrh.setError("Enter Monthly Rent");
                    monthlyrentrh.requestFocus();
                }else
                if (propertytyperhtext.equalsIgnoreCase("Select Property Type"))
                {
                    Snackbar.make(rhmainlayout, "Select Property Type", Snackbar.LENGTH_LONG)
                            .show();

                    propertytyperh.requestFocus();
                }else
                if (numofbedrhtext.equalsIgnoreCase("Select Number"))
                {
                    Snackbar.make(rhmainlayout, "Select Number of Bedrooms", Snackbar.LENGTH_LONG)
                            .show();

                    numofbedrh.requestFocus();
                }else
                if (numofbathrhtext.equalsIgnoreCase("Select Number"))
                {
                    Snackbar.make(rhmainlayout, "Select Number of Bathrooms", Snackbar.LENGTH_LONG)
                            .show();

                    numofbathrh.requestFocus();
                }else
                if (!(rhfurnishtype.getCheckedChipId()==R.id.rhfullyfurnish)&&!(rhfurnishtype.getCheckedChipId()==R.id.rhsemifurnish)&&!(rhfurnishtype.getCheckedChipId()==R.id.rhunfurnish))
                {
                    Snackbar.make(rhmainlayout, "Select Furnish Type", Snackbar.LENGTH_LONG)
                            .show();
                    rhfurnishtype.requestFocus();
                }
                if ((img1==null)||(img2==null)||(img3==null))
                {
                    AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setMessage(Html.fromHtml("<b>Select all images</b>"));
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                    alertDialog.show();

                }
            }
            else
            {
                if(Validation.isValidCity(cityrhtext)){
                    if(pincoderhtext.charAt(0)!='0'&&pincoderhtext.length()==6)
                        toServer();
                    else{
                        Snackbar.make(rhmainlayout, "Enter Valid Pincode", Snackbar.LENGTH_LONG)
                                .show();

                        pincoderh.requestFocus();
                    }
                }
                else {
                    Snackbar.make(rhmainlayout, "Enter Valid City Name", Snackbar.LENGTH_LONG)
                            .show();

                    cityrh.requestFocus();
                }
            }
        }

    }

    private void toServer() {
        loading.show();
        if (createJsonObject()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_RentHouses, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toLowerCase().contains("error")) {
                        loading.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(RentHouses.this).create();
                        alertDialog.setMessage(response);
                        alertDialog.setButton(BUTTON_POSITIVE,"Ok", (DialogInterface.OnClickListener) null);
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                        Button positiveButton = alertDialog.getButton(BUTTON_POSITIVE);
                        positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                        
                    } else {
                        if(response.toLowerCase().contains("success")){
                            uniqueid=response.substring(7);
                            loading.dismiss();
                            picuploadfuntion(1);

                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    boolean haveConnectedWifi = false;
                    boolean haveConnectedMobile = false;
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                    for (NetworkInfo ni : netInfo) {
                        if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                            if (ni.isConnected())
                                haveConnectedWifi = true;
                        if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                            if (ni.isConnected())
                                haveConnectedMobile = true;
                    }
                    if (!haveConnectedWifi && !haveConnectedMobile) {
                        AlertDialog alertDialog = new AlertDialog.Builder(RentHouses.this).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(RentHouses.this).create();
                        alertDialog.setMessage("Connection Error!");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    }
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    Log.i("dataflag",jsonObject.toString());
                    params.put("data", jsonObject.toString());
                    params.put("flag", "1");
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }
                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }
                @Override
                public void retry(VolleyError error) throws VolleyError {
                    AlertDialog.Builder builder = new AlertDialog.Builder(RentHouses.this);
                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Connection")
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
            });
            MySingleton.getInstance(RentHouses.this).addToRequestQueue(stringRequest);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(RentHouses.this);

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error in connection.")
                    .setPositiveButton("OK", null)
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
    }

    private void picuploadfuntion(int flag) {
        switch (flag){
            case 1:uploadPic(img1,"Uploading 1st Image.Please Wait!",1);
                    break;
            case 2: uploadPic(img2,"Uploading 2nd Image.Please Wait!",2);
            break;
            case 3: uploadPic(img3,"Uploading last Image.Please Wait!",3);
            break;
            case 4: success();
        }
    }

    private void success() {
        sendMail();
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setMessage(Html.fromHtml("<b>Your ad has been posted. Our customer executive will contact you soon to verify your property.</b>"));
        alertDialog.setIcon(R.mipmap.ic_launcher_round);
        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
        alertDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(RentHouses.this, UserMainActivity.class);
                 if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(RentHouses.this, R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent, options.toBundle());
                } else {
                    startActivity(intent);
                }
                finish();
            }
        },4000);
 }

    private void sendMail() {
        SharedPreferences sp;
        sp = getSharedPreferences("NeuuGen_data", MODE_PRIVATE);
        if (sp != null) {
            final String email=sp.getString("email",null);
            final String name=sp.getString("name",null).toUpperCase();
            SendMail sendMail=new SendMail();
            String subject="NG: Your AD has been posted.";
            String body="Dear "+name+",<br><p>Thanks for choosing NEUUGEN. Your AD has been posted.</p><br>Our Customer Executive/Agent will reach out to you for verification of the posted ad. The Service Requested will be provided soon.";
            sendMail.sendMailmethod(email, subject, body, this, new VolleyCallback() {
                @Override
                public void onSuccess(String result) {

                }

                @Override
                public void onError(String response) {

                }

                @Override
                public void onVolleyError() {
                    loading.dismiss();
                }
            });
        }
    }

    private void uploadPic(Bitmap img,final String message,final int flag ) {
        if (img != null) {
            ByteArrayOutputStream byteArrayOutputStreamObject;
            byteArrayOutputStreamObject = new ByteArrayOutputStream();
            img.compress(Bitmap.CompressFormat.JPEG, 40, byteArrayOutputStreamObject);
            byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
            final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
            class AsyncTaskUploadClass extends AsyncTask<Void, Void, String> {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading.setMessage(message);
                    loading.show();
                }
                @Override
                protected void onPostExecute(String string1) {
                    super.onPostExecute(string1);
                    Log.i("formerror1",string1);
                    // Dismiss the progress dialog after done uploading.
                    if(string1.toLowerCase().contains("success")){
                        loading.dismiss();
                        picuploadfuntion(flag+1);
                    }
                    else {
                        if(string1.contains("error")){
                            //loading.dismiss();
                            errorReceived();
                        }
                    }
                }
                @Override
                protected String doInBackground(Void... params) {
                    ImageProcessClass imageProcessClass = new ImageProcessClass();
                    HashMap<String, String> HashMapParams = new HashMap<String, String>();
                    Log.i("formerror2",ConvertImage);
                    HashMapParams.put("uniqueid", uniqueid);
                    HashMapParams.put("image_path", ConvertImage);
                    HashMapParams.put("dbselect", "ads");
                    String FinalData = imageProcessClass.ImageHttpRequest(UrlNeuugen.uploadPic, HashMapParams);
                    return FinalData;
                }
            }
            AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
            AsyncTaskUploadClassOBJ.execute();
        }
    }

    private void errorReceived() {
        //clear predata
        loading.dismiss();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_RentHouses, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toLowerCase().contains("error")) {
                    loading.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(RentHouses.this);
                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Error in server. Try Again")
                            .setPositiveButton("OK",null)
                            .setIcon(R.mipmap.ic_launcher_round);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                } else {
                    if(response.toLowerCase().contains("success")){
                        loading.dismiss();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                boolean haveConnectedWifi = false;
                boolean haveConnectedMobile = false;
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            haveConnectedWifi = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            haveConnectedMobile = true;
                }
                if (!haveConnectedWifi && !haveConnectedMobile) {
                    AlertDialog alertDialog = new AlertDialog.Builder(RentHouses.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(RentHouses.this).create();
                    alertDialog.setMessage("Connection Error!");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("uniqueid", uniqueid);
                params.put("flag", "0");
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError {
                AlertDialog.Builder builder = new AlertDialog.Builder(RentHouses.this);
                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Connection")
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
        });
        MySingleton.getInstance(RentHouses.this).addToRequestQueue(stringRequest);
    }

    private boolean createJsonObject() {
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            try {
                jsonObject.put("mobileno",sp.getString("mobileno",null));
                jsonObject.put("houseno",housenorhtext);
                jsonObject.put("area",arearhtext);
                jsonObject.put("city",cityrhtext);
                jsonObject.put("landmark",landmarkrhtext);
                jsonObject.put("pincode",pincoderhtext);
                jsonObject.put("propertytype",propertytype);
                jsonObject.put("bedrooms",numofbedrhtext);
                jsonObject.put("bathrooms",numofbathrhtext);
                jsonObject.put("furnishtype",rhfurnishtyypetext);
                jsonObject.put("builtuparea",builtarearhtext);
                jsonObject.put("price",monthlyrentrhtext);
                return true;
            } catch (Exception e) {
                Log.i("errorrent",e.getMessage());
                return false;
            }
        }
        return false;
    }

    public class ImageProcessClass{
        public String ImageHttpRequest(String requestURL,HashMap<String, String> PData) {
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url;
                HttpURLConnection httpURLConnectionObject ;
                OutputStream OutPutStream;
                BufferedWriter bufferedWriterObject ;
                BufferedReader bufferedReaderObject ;
                int RC ;
                url = new URL(requestURL);
                httpURLConnectionObject = (HttpURLConnection) url.openConnection();
                httpURLConnectionObject.setReadTimeout(19000);
                httpURLConnectionObject.setConnectTimeout(19000);
                httpURLConnectionObject.setRequestMethod("POST");
                httpURLConnectionObject.setDoInput(true);
                httpURLConnectionObject.setDoOutput(true);
                OutPutStream = httpURLConnectionObject.getOutputStream();
                bufferedWriterObject = new BufferedWriter(
                        new OutputStreamWriter(OutPutStream, "UTF-8"));
                bufferedWriterObject.write(bufferedWriterDataFN(PData));
                bufferedWriterObject.flush();
                bufferedWriterObject.close();
                OutPutStream.close();
                RC = httpURLConnectionObject.getResponseCode();
                if (RC == HttpsURLConnection.HTTP_OK) {
                    bufferedReaderObject = new BufferedReader(new InputStreamReader(httpURLConnectionObject.getInputStream()));
                    stringBuilder = new StringBuilder();
                    String RC2;
                    while ((RC2 = bufferedReaderObject.readLine()) != null)
                        stringBuilder.append(RC2);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
        private String bufferedWriterDataFN(HashMap<String, String> HashMapParams) throws UnsupportedEncodingException {
            StringBuilder stringBuilderObject;
            stringBuilderObject = new StringBuilder();
            for (Map.Entry<String, String> KEY : HashMapParams.entrySet()) {
                if (chk==true)
                    chk = false;
                else
                    stringBuilderObject.append("&");
                stringBuilderObject.append(URLEncoder.encode(KEY.getKey(), "UTF-8"));
                stringBuilderObject.append("=");
                stringBuilderObject.append(URLEncoder.encode(KEY.getValue(), "UTF-8"));
            }
            return stringBuilderObject.toString();
        }
    }
}

