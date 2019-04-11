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

public class RentOffice extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView backtopost2;

    TextInputEditText arearo,cityro,landmarkro,builtarearo,monthlyrentro,shopno,pincodero;

    MaterialButton submitroform,addimgro1,addimgro2,addimgro3;

    AppCompatSpinner propertytypero;

    ChipGroup constatusro;

    Chip readytomovero,underconro;

    LinearLayout romainlayout;

    AppCompatImageView imgro1,imgro2,imgro3;
    ProgressDialog loading = null;
    String uniqueid=null;
    String propertytype,arearotext,cityrotext,landmarkrotext,pincoderotext,builtarearotext,monthlyrentrotext,shopnorotext,propertytyperotext,constatusrotext;
    JSONObject jsonObject= new JSONObject();
    int imageflag=0;
    Bitmap img1,img2,img3;
    boolean chk = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rent_office);

        idLink();
        listenerLink();
        hideSoftKeyboard();
        loading = new ProgressDialog(RentOffice.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backtopost2.setTypeface(font);
    }

    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }
    public void listenerLink()
    {
        backtopost2.setOnClickListener(this);
        submitroform.setOnClickListener(this);
        addimgro1.setOnClickListener(this);
        addimgro2.setOnClickListener(this);
        addimgro3.setOnClickListener(this);
    }

    public void idLink()
    {
        backtopost2=findViewById(R.id.backtopost2);
        arearo=findViewById(R.id.arearo);
        cityro=findViewById(R.id.cityro);
        landmarkro=findViewById(R.id.landmarkro);
        builtarearo=findViewById(R.id.builtarearo);
        monthlyrentro=findViewById(R.id.monthlyrentro);
        propertytypero=findViewById(R.id.propertytypero);
        submitroform=findViewById(R.id.submitroform);
        constatusro=findViewById(R.id.constatusro);
        readytomovero=findViewById(R.id.readytomovero);
        underconro=findViewById(R.id.underconro);
        shopno=findViewById(R.id.shopno);
        romainlayout=findViewById(R.id.romainlayout);
        imgro1=findViewById(R.id.imgro1);
        imgro2=findViewById(R.id.imgro2);
        imgro3=findViewById(R.id.imgro3);
        addimgro1=findViewById(R.id.addimgro1);
        addimgro2=findViewById(R.id.addimgro2);
        addimgro3=findViewById(R.id.addimgro3);
        pincodero=findViewById(R.id.pincodero);

    }
    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                switch (imageflag){
                    case 1:img1 = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgro1.setImageBitmap(img1);
                        break;
                    case 2:img2=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgro2.setImageBitmap(img2);
                        break;
                    case 3:img3=MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        imgro3.setImageBitmap(img3);
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
        if(v.getId()==R.id.backtopost2)
        {
            onBackPressed();
        }
        if (v.getId()==R.id.submitroform)
        {
            arearotext=arearo.getText().toString().trim();
            shopnorotext=shopno.getText().toString().trim();
            cityrotext=cityro.getText().toString().trim();
            landmarkrotext=landmarkro.getText().toString().trim();
            builtarearotext=builtarearo.getText().toString().trim();
            monthlyrentrotext=monthlyrentro.getText().toString().trim();
            propertytyperotext=propertytypero.getSelectedItem().toString();
            pincoderotext=pincodero.getText().toString().trim();

            if (constatusro.getCheckedChipId()==R.id.readytomove)
            {
                constatusrotext="0";
            }else
            if (constatusro.getCheckedChipId()==R.id.undercon)
            {
                constatusrotext="1";
            }

            if (propertytyperotext.equalsIgnoreCase("Office Area"))
            {
                propertytype="4";
            }else
            if (propertytyperotext.equalsIgnoreCase("Shop Area"))
            {
                propertytype="5";
            }
            if (TextUtils.isEmpty(pincoderotext) ||TextUtils.isEmpty(arearotext) || TextUtils.isEmpty(shopnorotext)||TextUtils.isEmpty(cityrotext)||TextUtils.isEmpty(builtarearotext)||TextUtils.isEmpty(monthlyrentrotext)||propertytyperotext.equalsIgnoreCase("Select Property Type")||!(constatusro.getCheckedChipId()==R.id.undercon)&&!(constatusro.getCheckedChipId()==R.id.readytomove)||(img1==null)||(img2==null)||(img3==null))
            {
                if (TextUtils.isEmpty(shopnorotext) )
                {
                    shopno.setError("Enter Shop Number");
                    shopno.requestFocus();
                }else
                if (TextUtils.isEmpty(arearotext) )
                {
                    arearo.setError("Enter Area");
                    arearo.requestFocus();
                }else

                if (TextUtils.isEmpty(cityrotext) )
                {
                    cityro.setError("Enter City");
                    cityro.requestFocus();
                }else
                if (TextUtils.isEmpty(pincoderotext) )
                {
                    pincodero.setError("Enter Pincode");
                    pincodero.requestFocus();
                }else
                if (TextUtils.isEmpty(builtarearotext) )
                {
                    builtarearo.setError("Enter Built Area");
                    builtarearo.requestFocus();
                }else
                if (TextUtils.isEmpty(monthlyrentrotext) )
                {
                    monthlyrentro.setError("Enter Monthly Rent");
                    monthlyrentro.requestFocus();
                }else

                if (propertytyperotext.equalsIgnoreCase("Select Property Type"))
                {
                    Snackbar.make(romainlayout, "Select Property Type", Snackbar.LENGTH_LONG)
                            .show();

                    propertytypero.requestFocus();
                }else

                if (!(constatusro.getCheckedChipId()==R.id.undercon)&&!(constatusro.getCheckedChipId()==R.id.readytomove)) {
                    Snackbar.make(romainlayout, "Select Construction Status", Snackbar.LENGTH_LONG)
                            .show();

                    constatusro.requestFocus();
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
                if(Validation.isValidCity(cityrotext)){
                    if(pincoderotext.charAt(0)!='0'&&pincoderotext.length()==6)
                        toServer();
                    else{
                        Snackbar.make(romainlayout, "Enter Valid Pincode", Snackbar.LENGTH_LONG)
                                .show();

                        pincodero.requestFocus();
                    }
                }
                else {
                    Snackbar.make(romainlayout, "Enter Valid City Name", Snackbar.LENGTH_LONG)
                            .show();

                    cityro.requestFocus();
                }
            }
        }
    }

    private void toServer() {
        loading.show();
        if (createJsonObject()) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_RentOffice, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.toLowerCase().contains("error")) {
                        loading.dismiss();

                        AlertDialog alertDialog = new AlertDialog.Builder(RentOffice.this).create();
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
                        AlertDialog alertDialog = new AlertDialog.Builder(RentOffice.this).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(RentOffice.this).create();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(RentOffice.this);
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
            MySingleton.getInstance(RentOffice.this).addToRequestQueue(stringRequest);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(RentOffice.this);

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

    private boolean createJsonObject() {
        SharedPreferences sp;
        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        if(sp!=null) {
            try {
                jsonObject.put("mobileno",sp.getString("mobileno",null));
                jsonObject.put("shopno",shopnorotext);
                jsonObject.put("area",arearotext);
                jsonObject.put("city",cityrotext);
                jsonObject.put("landmark",landmarkrotext);
                jsonObject.put("pincode",pincoderotext);
                jsonObject.put("propertytype",propertytype);
                jsonObject.put("constructionstatus",constatusrotext);
                jsonObject.put("builtuparea",builtarearotext);
                jsonObject.put("price",monthlyrentrotext);
                return true;
            } catch (Exception e) {
                Log.i("errorrent",e.getMessage());
                return false;
            }
        }
        return false;
    }

    private void uploadPic(Bitmap img, final String message, final int flag ) {
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
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.fill_RentOffice, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toLowerCase().contains("error")) {
                    loading.dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(RentOffice.this);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(RentOffice.this).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(RentOffice.this).create();
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
                AlertDialog.Builder builder = new AlertDialog.Builder(RentOffice.this);
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
        MySingleton.getInstance(RentOffice.this).addToRequestQueue(stringRequest);
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
                Intent intent = new Intent(RentOffice.this, UserMainActivity.class);
                if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(RentOffice.this, R.anim.fade_in, R.anim.fade_out);
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
                        AlertDialog alertDialog = new AlertDialog.Builder(RentOffice.this).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(RentOffice.this).create();
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(RentOffice.this);
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
            MySingleton.getInstance(RentOffice.this).addToRequestQueue(stringRequest);
        }
    }
}
