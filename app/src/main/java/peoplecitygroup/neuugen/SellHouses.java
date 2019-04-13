package peoplecitygroup.neuugen;

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

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class SellHouses extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView backtopost3;

    boolean chk = true;

    TextInputEditText areash,citysh,landmarksh,builtareash,costsh,housenosh,ageofpropertysh,pincodesh;

    MaterialButton submitshform,addimgsh1,addimgsh2,addimgsh3;

    AppCompatSpinner propertytypesh,numofbedsh,numofbathsh;

    ChipGroup furnishtypesh,constatus;

    Chip shfullyfurnish,shsemifurnish,shunfurnish,readytomove,undercon;

    LinearLayout shmainlayout;

    AppCompatImageView imgsh1,imgsh2,imgsh3;

    int imageflag=0;
    Bitmap img1,img2,img3;

    String propertytype,areashtext,cityshtext,landmarkshtext,builtareashtext,costshtext,housenoshtext,pincodeshtext,propertytypeshtext,numofbedshtext,numofbathshtext,shfurnishtyypetext,constatustext,ageofpropertyshtext;

    ProgressDialog loading = null;
    JSONObject jsonObject= new JSONObject();
    String uniqueid=null;
Intent intent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_houses);

        idLink();
        listenerLink();
        hideSoftKeyboard();
        loading = new ProgressDialog(SellHouses.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backtopost3.setTypeface(font);


        intent=getIntent();
        cityshtext=intent.getStringExtra("city").toUpperCase();
        citysh.setText(cityshtext.toUpperCase());
        citysh.setEnabled(false);
    }
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                switch (imageflag){
                    case 1:img1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgsh1.setImageBitmap(img1);
                        break;
                    case 2:img2=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgsh2.setImageBitmap(img2);
                        break;
                    case 3:img3=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgsh3.setImageBitmap(img3);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                imageflag=0;
            }
        }
    }

    public void listenerLink()
    {
        backtopost3.setOnClickListener(this);
        submitshform.setOnClickListener(this);
        addimgsh1.setOnClickListener(this);
        addimgsh2.setOnClickListener(this);
        addimgsh3.setOnClickListener(this);
    }

    public void idLink()
    {
        backtopost3=findViewById(R.id.backtopost3);
        areash=findViewById(R.id.areash);
        citysh=findViewById(R.id.citysh);
        landmarksh=findViewById(R.id.landmarksh);
        builtareash=findViewById(R.id.builtareash);
        costsh=findViewById(R.id.costsh);
        propertytypesh=findViewById(R.id.propertytypesh);
        numofbedsh=findViewById(R.id.numofbedsh);
        numofbathsh=findViewById(R.id.numofbathsh);
        submitshform=findViewById(R.id.submitshform);
        furnishtypesh=findViewById(R.id.furnishtypesh);
        shfullyfurnish=findViewById(R.id.shfullyfurnish);
        shsemifurnish=findViewById(R.id.shsemifurnish);
        shunfurnish=findViewById(R.id.shunfurnish);
        housenosh=findViewById(R.id.housenosh);
        constatus=findViewById(R.id.constatus);
        readytomove=findViewById(R.id.readytomove);
        undercon=findViewById(R.id.undercon);
        ageofpropertysh=findViewById(R.id.ageofpropertysh);
        shmainlayout=findViewById(R.id.shmainlayout);
        imgsh1=findViewById(R.id.imgsh1);
        imgsh2=findViewById(R.id.imgsh2);
        imgsh3=findViewById(R.id.imgsh3);
        addimgsh1=findViewById(R.id.addimgsh1);
        addimgsh2=findViewById(R.id.addimgsh2);
        addimgsh3=findViewById(R.id.addimgsh3);
        pincodesh=findViewById(R.id.pincodesh);

    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.addimgsh1){
            imageflag=1;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
        }
        if (v.getId()==R.id.addimgsh2){
            imageflag=2;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
        }
        if (v.getId()==R.id.addimgsh3){
            imageflag=3;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
        }
        if(v.getId()==R.id.backtopost3)
        {
            onBackPressed();
        }
        if (v.getId()==R.id.submitshform)
        {

            areashtext=areash.getText().toString().trim();
            housenoshtext=housenosh.getText().toString().trim();
            cityshtext=citysh.getText().toString().trim();
            landmarkshtext=landmarksh.getText().toString().trim();
            builtareashtext=builtareash.getText().toString().trim();
            costshtext=costsh.getText().toString().trim();
            propertytypeshtext=propertytypesh.getSelectedItem().toString();
            numofbedshtext=String.valueOf(numofbedsh.getSelectedItemPosition());
            numofbathshtext=String.valueOf(numofbathsh.getSelectedItemPosition());
            ageofpropertyshtext=ageofpropertysh.getText().toString().trim();
            pincodeshtext=pincodesh.getText().toString().trim();

            if (propertytypeshtext.equalsIgnoreCase("Apartment"))
            {
                propertytype="0";
            }else
            if (propertytypeshtext.equalsIgnoreCase("Independent House"))
            {
                propertytype="1";
            }else
            if (propertytypeshtext.equalsIgnoreCase("Villa"))
            {
                propertytype="2";
            }

            if (furnishtypesh.getCheckedChipId()==R.id.shfullyfurnish)
            {
                shfurnishtyypetext="0";
            }else
            if (furnishtypesh.getCheckedChipId()==R.id.shsemifurnish)
            {
                shfurnishtyypetext="1";
            }else
            if (furnishtypesh.getCheckedChipId()==R.id.shunfurnish)
            {
                shfurnishtyypetext="2";
            }
            if (constatus.getCheckedChipId()==R.id.readytomove)
            {
                constatustext="0";
            }else
            if (constatus.getCheckedChipId()==R.id.undercon)
            {
                constatustext="1";
            }

            if (TextUtils.isEmpty(pincodeshtext) ||TextUtils.isEmpty(areashtext) || TextUtils.isEmpty(housenoshtext)||TextUtils.isEmpty(cityshtext)||TextUtils.isEmpty(builtareashtext)||TextUtils.isEmpty(costshtext)||propertytypeshtext.equalsIgnoreCase("Select Property Type")||numofbathsh.getSelectedItem().toString().equalsIgnoreCase("Select Number")||numofbedsh.getSelectedItem().toString().equalsIgnoreCase("Select Number")||TextUtils.isEmpty(ageofpropertyshtext)||!(furnishtypesh.getCheckedChipId()==R.id.shfullyfurnish)&&!(furnishtypesh.getCheckedChipId()==R.id.shsemifurnish)&&!(furnishtypesh.getCheckedChipId()==R.id.shunfurnish)||!(constatus.getCheckedChipId()==R.id.undercon)&&!(constatus.getCheckedChipId()==R.id.readytomove)||(img1==null)||(img2==null)||(img3==null))
            {
                if (TextUtils.isEmpty(housenoshtext) )
                {
                    housenosh.setError("Enter House Number");
                    housenosh.requestFocus();
                }else
                if (TextUtils.isEmpty(areashtext) )
                {
                    areash.setError("Enter Area");
                    areash.requestFocus();
                }else

                if (TextUtils.isEmpty(cityshtext) )
                {
                    citysh.setError("Enter City");
                    citysh.requestFocus();
                }else
                if (TextUtils.isEmpty(pincodeshtext) )
                {
                    pincodesh.setError("Enter Pincode");
                    pincodesh.requestFocus();
                }else
                if (TextUtils.isEmpty(builtareashtext) )
                {
                    builtareash.setError("Enter Built Area");
                    builtareash.requestFocus();
                }else
                if (TextUtils.isEmpty(costshtext) )
                {
                    costsh.setError("Enter Cost");
                    costsh.requestFocus();
                }else
                if (TextUtils.isEmpty(ageofpropertyshtext) )
                {
                    ageofpropertysh.setError("Enter Age of Property");
                    ageofpropertysh.requestFocus();
                }else
                if (propertytypeshtext.equalsIgnoreCase("Select Property Type"))
                {
                    Snackbar.make(shmainlayout, "Select Property Type", Snackbar.LENGTH_LONG)
                            .show();

                    propertytypesh.requestFocus();
                }else
                if (numofbedshtext.equalsIgnoreCase("Select Number"))
                {
                    Snackbar.make(shmainlayout, "Select Number of Bedrooms", Snackbar.LENGTH_LONG)
                            .show();

                    numofbedsh.requestFocus();
                }else
                if (numofbathshtext.equalsIgnoreCase("Select Number"))
                {
                    Snackbar.make(shmainlayout, "Select Number of Bathrooms", Snackbar.LENGTH_LONG)
                            .show();

                    numofbathsh.requestFocus();
                }else
                if (!(furnishtypesh.getCheckedChipId()==R.id.shfullyfurnish)&&!(furnishtypesh.getCheckedChipId()==R.id.shsemifurnish)&&!(furnishtypesh.getCheckedChipId()==R.id.shunfurnish))
                {
                    Snackbar.make(shmainlayout, "Select Furnish Type", Snackbar.LENGTH_LONG)
                            .show();

                    furnishtypesh.requestFocus();
                }else
                if (!(constatus.getCheckedChipId()==R.id.undercon)&&!(constatus.getCheckedChipId()==R.id.readytomove)) {
                    Snackbar.make(shmainlayout, "Select Construction Status", Snackbar.LENGTH_LONG)
                            .show();

                    constatus.requestFocus();
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
                if(Validation.isValidCity(cityshtext)){
                    if(pincodeshtext.charAt(0)!='0'&&pincodeshtext.length()==6)
                        toServer();
                    else{
                        Snackbar.make(shmainlayout, "Enter Valid Pincode", Snackbar.LENGTH_LONG)
                                .show();

                        pincodesh.requestFocus();
                    }
                }
                else {
                    Snackbar.make(shmainlayout, "Enter Valid City Name", Snackbar.LENGTH_LONG)
                            .show();

                    citysh.requestFocus();
                }

            }
        }
    }

    private void toServer() {
        loading.show();
        if (createJsonObject()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_SellHouses, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toLowerCase().contains("error")) {
                        loading.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(SellHouses.this).create();
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
                        AlertDialog alertDialog = new AlertDialog.Builder(SellHouses.this).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(SellHouses.this).create();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SellHouses.this);
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
            MySingleton.getInstance(SellHouses.this).addToRequestQueue(stringRequest);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(SellHouses.this);

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
                Intent intent = new Intent(SellHouses.this, UserMainActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(SellHouses.this, R.anim.fade_in, R.anim.fade_out);
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
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.sendSuccessMails, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toLowerCase().contains("error")) {
                        Log.i("mailerror", response);
                    } else {
                        if (response.toLowerCase().contains("success")) {
                            Log.i("mailerror", "onResponse: " + response);
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
                        AlertDialog alertDialog = new AlertDialog.Builder(SellHouses.this).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(SellHouses.this).create();
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
                    params.put("email",email );
                    params.put("subject", "NG: Your AD has been posted.");
                    params.put("body","Dear "+name+",<br><p>Thanks for choosing NEUUGEN. Your AD has been posted.</p><br>Our Customer Executive/Agent will reach out to you for verification of the posted ad. The Service Requested will be provided soon.");
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SellHouses.this);
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
            MySingleton.getInstance(SellHouses.this).addToRequestQueue(stringRequest);
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
                    SellHouses.ImageProcessClass imageProcessClass = new SellHouses.ImageProcessClass();
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_SellHouses, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toLowerCase().contains("error")) {
                    loading.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SellHouses.this);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(SellHouses.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SellHouses.this).create();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(SellHouses.this);
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
        MySingleton.getInstance(SellHouses.this).addToRequestQueue(stringRequest);
    }

    private boolean createJsonObject() {
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            try {
                jsonObject.put("mobileno",sp.getString("mobileno",null));
                jsonObject.put("houseno",housenoshtext);
                jsonObject.put("area",areashtext);
                jsonObject.put("city",cityshtext);
                jsonObject.put("landmark",landmarkshtext);
                jsonObject.put("pincode",pincodeshtext);
                jsonObject.put("propertytype",propertytype);
                jsonObject.put("bedrooms",numofbedshtext);
                jsonObject.put("bathrooms",numofbathshtext);
                jsonObject.put("furnishtype",shfurnishtyypetext);
                jsonObject.put("builtuparea",builtareashtext);
                jsonObject.put("price",costshtext);
                jsonObject.put("constnstatus",constatustext);
                jsonObject.put("ageofproperty",ageofpropertyshtext);
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
