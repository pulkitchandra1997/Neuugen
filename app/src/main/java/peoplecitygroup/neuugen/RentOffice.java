package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
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

import java.util.HashMap;
import java.util.Map;

import static android.content.DialogInterface.BUTTON_POSITIVE;

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
    public void onClick(View v) {
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
            if (TextUtils.isEmpty(pincoderotext) ||TextUtils.isEmpty(arearotext) || TextUtils.isEmpty(shopnorotext)||TextUtils.isEmpty(cityrotext)||TextUtils.isEmpty(builtarearotext)||TextUtils.isEmpty(monthlyrentrotext)||propertytyperotext.equalsIgnoreCase("Select Property Type")||!(constatusro.getCheckedChipId()==R.id.undercon)&&!(constatusro.getCheckedChipId()==R.id.readytomove))
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

    private void picuploadfuntion(int i) {
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

}
