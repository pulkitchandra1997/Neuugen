package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
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

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.UserMainActivity;
import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.SendMail;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.common_req_files.Validation;
import peoplecitygroup.neuugen.common_req_files.VolleyCallback;

import static android.content.DialogInterface.BUTTON_POSITIVE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class SellPlots extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView backtopost4;

    boolean chk = true;

    TextInputEditText pincodesp,areasp,citysp,landmarksp,plotarea,plotprice,plotno,lengthofplot,widthofplot,widthofroad,propertytypesp;

    MaterialButton submitspform,addimgsp1,addimgsp2;

    ChipGroup posesnstatus;

    Chip immediate,infuture;

    LinearLayout spmainlayout;

    AppCompatImageView imgsp1,imgsp2;

    int imageflag=0;
    Bitmap img1,img2;

    String propertytype,areasptext,citysptext,landmarksptext,plotareatext,plotpricetext,plotnotext,lengthofplottext,widthofplottext,widthofroadtext,propertytypesptext,posesnstatustext,pincodesptext;

    ProgressDialog loading = null;
    JSONObject jsonObject= new JSONObject();
    String uniqueid=null;
Intent intent=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_plots);

        idLink();
        listenerLink();
        hideSoftKeyboard();

        loading = new ProgressDialog(SellPlots.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        
        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backtopost4.setTypeface(font);

        intent=getIntent();
        citysptext=intent.getStringExtra("city").toUpperCase();
        citysp.setText(citysptext.toUpperCase());
        citysp.setEnabled(false);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    public void listenerLink()
    {
        backtopost4.setOnClickListener(this);
        submitspform.setOnClickListener(this);
        addimgsp1.setOnClickListener(this);
        addimgsp2.setOnClickListener(this);
    }

    public void idLink()
    {
        backtopost4=findViewById(R.id.backtopost4);
        areasp=findViewById(R.id.areasp);
        citysp=findViewById(R.id.citysp);
        landmarksp=findViewById(R.id.landmarksp);
        plotarea=findViewById(R.id.plotarea);
        plotno=findViewById(R.id.plotno);
        plotprice=findViewById(R.id.plotprice);
        submitspform=findViewById(R.id.submitspform);
        lengthofplot=findViewById(R.id.lengthplot);
        widthofplot=findViewById(R.id.widthplot);
        widthofroad=findViewById(R.id.widthofroad);
        propertytypesp=findViewById(R.id.propertytypesp);
        posesnstatus=findViewById(R.id.posesnstatus);
        immediate=findViewById(R.id.immediate);
        infuture=findViewById(R.id.infuture);
        spmainlayout=findViewById(R.id.spmainlayout);
        imgsp1=findViewById(R.id.imgsp1);
        imgsp2=findViewById(R.id.imgsp2);
        addimgsp1=findViewById(R.id.addimgsp1);
        addimgsp2=findViewById(R.id.addimgsp2);
        pincodesp=findViewById(R.id.pincodesp);

    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                switch (imageflag){
                    case 1:img1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgsp1.setImageBitmap(img1);
                        break;
                    case 2:img2=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgsp2.setImageBitmap(img2);
                   
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
        if (v.getId()==R.id.addimgsp1){
            imageflag=1;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
        }
        if (v.getId()==R.id.addimgsp2){
            imageflag=2;
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_PICK);
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
        }
        if(v.getId()==R.id.backtopost4)
        {
            onBackPressed();
        }
        if (v.getId()==R.id.submitspform)
        {
            areasptext=areasp.getText().toString().trim();
            plotnotext=plotno.getText().toString().trim();
            citysptext=citysp.getText().toString().trim();
            landmarksptext=landmarksp.getText().toString().trim();
            plotareatext=plotarea.getText().toString().trim();
            plotpricetext=plotprice.getText().toString().trim();
            propertytypesptext=propertytypesp.getText().toString().trim();
            lengthofplottext=lengthofplot.getText().toString().trim();
            widthofplottext=widthofplot.getText().toString().trim();
            widthofroadtext=widthofroad.getText().toString().trim();
            pincodesptext=pincodesp.getText().toString().trim();

            propertytype="6";
            if (posesnstatus.getCheckedChipId()==R.id.immediate)
            {
                posesnstatustext="0";
            }else
            if (posesnstatus.getCheckedChipId()==R.id.infuture)
            {
                posesnstatustext="1";
            }

            if (TextUtils.isEmpty(pincodesptext) ||TextUtils.isEmpty(areasptext) || TextUtils.isEmpty(plotnotext)||TextUtils.isEmpty(citysptext)||TextUtils.isEmpty(plotareatext)||TextUtils.isEmpty(plotpricetext)||propertytypesptext.equalsIgnoreCase("Select Property Type")||!(posesnstatus.getCheckedChipId()==R.id.immediate)&&!(posesnstatus.getCheckedChipId()==R.id.infuture)||(img1==null)||(img2==null))
            {
                if (TextUtils.isEmpty(plotnotext) )
                {
                    plotno.setError("Enter Plot Number");
                    plotno.requestFocus();
                }else
                if (TextUtils.isEmpty(areasptext) )
                {
                    areasp.setError("Enter Area");
                    areasp.requestFocus();
                }else

                if (TextUtils.isEmpty(citysptext) )
                {
                    citysp.setError("Enter City");
                    citysp.requestFocus();
                }else
                if (TextUtils.isEmpty(pincodesptext) )
                {
                    pincodesp.setError("Enter Pincode");
                    pincodesp.requestFocus();
                }else
                if (TextUtils.isEmpty(plotareatext) )
                {
                    plotarea.setError("Enter Plot Area");
                    plotarea.requestFocus();
                }else
                if (TextUtils.isEmpty(plotpricetext) )
                {
                    plotprice.setError("Enter Plot Price");
                    plotprice.requestFocus();
                }else
                if (propertytypesptext.equalsIgnoreCase("Select Property Type"))
                {
                    Snackbar.make(spmainlayout, "Select Property Type", Snackbar.LENGTH_LONG)
                            .show();

                    propertytypesp.requestFocus();
                }else

                if (!(posesnstatus.getCheckedChipId()==R.id.immediate)&&!(posesnstatus.getCheckedChipId()==R.id.infuture)) {
                    Snackbar.make(spmainlayout, "Select Possession Status", Snackbar.LENGTH_LONG)
                            .show();

                    posesnstatus.requestFocus();
                }
                if ((img1==null)||(img2==null))
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
                if(Validation.isValidCity(citysptext)){
                    if(pincodesptext.charAt(0)!='0'&&pincodesptext.length()==6)
                        toServer();
                    else{
                        Snackbar.make(spmainlayout, "Enter Valid Pincode", Snackbar.LENGTH_LONG)
                                .show();

                        pincodesp.requestFocus();
                    }
                }
                else {
                    Snackbar.make(spmainlayout, "Enter Valid City Name", Snackbar.LENGTH_LONG)
                            .show();

                    citysp.requestFocus();
                }
            }
        }
    }

    private void toServer() {
        loading.show();
        if (createJsonObject()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_SellPlots, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toLowerCase().contains("error")) {
                        loading.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(SellPlots.this).create();
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
                        AlertDialog alertDialog = new AlertDialog.Builder(SellPlots.this).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(SellPlots.this).create();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(SellPlots.this);
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
            MySingleton.getInstance(SellPlots.this).addToRequestQueue(stringRequest);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(SellPlots.this);

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
            case 3: success();
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
                Intent intent = new Intent(SellPlots.this, UserMainActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(SellPlots.this, R.anim.fade_in, R.anim.fade_out);
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
                    SellPlots.ImageProcessClass imageProcessClass = new SellPlots.ImageProcessClass();
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_SellPlots, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toLowerCase().contains("error")) {
                    loading.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(SellPlots.this);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(SellPlots.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(SellPlots.this).create();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(SellPlots.this);
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
        MySingleton.getInstance(SellPlots.this).addToRequestQueue(stringRequest);
    }

    private boolean createJsonObject() {
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            try {
                jsonObject.put("mobileno",sp.getString("mobileno",null));
                jsonObject.put("houseno",plotnotext);
                jsonObject.put("area",areasptext);
                jsonObject.put("city",citysptext);
                jsonObject.put("landmark",landmarksptext);
                jsonObject.put("pincode",pincodesptext);
                jsonObject.put("propertytype",propertytype);
                jsonObject.put("possessionstatus",posesnstatustext);
                jsonObject.put("plotarea",plotareatext);
                jsonObject.put("plotlength",lengthofplottext);
                jsonObject.put("plotwidth",widthofplottext);
                jsonObject.put("roadwidth",widthofroadtext);
                jsonObject.put("plotprice",plotpricetext);
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
