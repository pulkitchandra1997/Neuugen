package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.squareup.picasso.Picasso;

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

import peoplecitygroup.neuugen.service.UrlNeuugen;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class ViewProfile extends AppCompatActivity implements View.OnClickListener {

    AppCompatTextView emailverify,addverify,phoneverify,editprofileicon,backtoaccount,emailtext,phonetext,citytext,addresstext,statetext,pincodetext,emailicon,phoneicon,addressicon,cityicon,nametext,gendertext,gendericon,bdaytext,bdayicon;
    boolean chk = true;
    BootstrapCircleThumbnail profileimg,profileimgbtn;
    String ImagePath = "image_path" ;
    SharedPreferences sp;
    ProgressDialog loading = null;
    SharedPreferences.Editor se;
    Bitmap bitmap;
    static int quality=100;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        idLink();
        listenerLink();

        sp=getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        se=sp.edit();
        loading = new ProgressDialog(ViewProfile.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        fill();
        if(sp.getString("pic",null)!=null) {
            Picasso.with(this).load(sp.getString("pic",null))
                    .placeholder(R.drawable.defaultpic)
                    .into(profileimg);
        }
        else
            profileimg.setImageResource(R.drawable.defaultpic);
        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        editprofileicon.setTypeface(font);
        backtoaccount.setTypeface(font);
        emailicon.setTypeface(font);
        phoneicon.setTypeface(font);
        cityicon.setTypeface(font);
        addressicon.setTypeface(font);
        gendericon.setTypeface(font);
        bdayicon.setTypeface(font);

    }

    public void fill()
    {
        nametext.setText(sp.getString("name","name"));
        emailtext.setText(sp.getString("email","email"));
        phonetext.setText(sp.getString("mobileno","mobileno"));
        citytext.setText(sp.getString("city","city"));
        if (sp.getString("address","").equalsIgnoreCase("null"))
        {
            addresstext.setText("Address not yet filled");
        }else
        {
            addresstext.setText(sp.getString("address","Address not yet filled"));
        }
        if (sp.getString("state","state").equalsIgnoreCase("null"))
        {
            statetext.setText("");
        }
        else
        {
            statetext.setText(sp.getString("state", " "));
        }
        if (sp.getString("gender","gender").equalsIgnoreCase("M"))
        {
            gendertext.setText("Male");
        }
        else if (sp.getString("gender","gender").equalsIgnoreCase("F"))
        {
            gendertext.setText("Female");
        }
        else
        {
            gendertext.setText("Gender not yet filled");
        }
        if (sp.getString("pincode","pincode").equalsIgnoreCase("null"))
        {
            pincodetext.setText(" ");
        }
        else
        {
            pincodetext.setText(sp.getString("pincode"," "));
        }
        if (sp.getString("dob","dob").equalsIgnoreCase("null"))
        {
            bdaytext.setText("DOB not yet filled");
        }
        else
        {
            bdaytext.setText(sp.getString("dob","DOB not yet filled"));
        }
        if (sp.getString("emailverified","emailverified").equalsIgnoreCase("1"))
        {
            emailverify.setText("Verified");
        }
        else
        {
            emailverify.setText("Not Verified");
        }

        if (sp.getString("addressverified","addressverified").equalsIgnoreCase("1"))
        {
            addverify.setText("Verified");
        }
        else
        {
            addverify.setText("Not Verified");
        }


    }

    public void idLink()
    {
        editprofileicon=findViewById(R.id.editprofileicon);
        backtoaccount=findViewById(R.id.backtoaccount);
        emailicon=findViewById(R.id.emailicon);
        phoneicon=findViewById(R.id.phoneicon);
        cityicon=findViewById(R.id.cityicon);
        addressicon=findViewById(R.id.addressicon);
        emailtext=findViewById(R.id.emailtext);
        phonetext=findViewById(R.id.phonetext);
        addresstext=findViewById(R.id.addresstext);
        citytext=findViewById(R.id.citytext);
        statetext=findViewById(R.id.statetext);
        pincodetext=findViewById(R.id.pincodetext);
        nametext=findViewById(R.id.nametext);
        bdayicon=findViewById(R.id.bdayicon);
        bdaytext=findViewById(R.id.bdaytext);
        gendericon=findViewById(R.id.gendericon);
        gendertext=findViewById(R.id.gendertext);
        profileimg=findViewById(R.id.profileimg);
        profileimgbtn=findViewById(R.id.profileimgbtn);
        emailverify=findViewById(R.id.emailverify);
        phoneverify=findViewById(R.id.phoneverify);
        addverify=findViewById(R.id.addverify);


    }
    public void listenerLink()
    {
        editprofileicon.setOnClickListener(this);
        backtoaccount.setOnClickListener(this);
        profileimgbtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.profileimgbtn){
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Image From Gallery"), 1);
        }
        if(v.getId()==R.id.backtoaccount)
        {
            onBackPressed();
        }
        if(v.getId()==R.id.editprofileicon)
        {
            Intent intent = new Intent(ViewProfile.this, EditProfile.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(ViewProfile.this, R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onActivityResult(int RC, int RQC, Intent I) {
        super.onActivityResult(RC, RQC, I);
        if (RC == 1 && RQC == RESULT_OK && I != null && I.getData() != null) {
            Uri uri = I.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                profileimg.setImageBitmap(bitmap);
                if(bitmap!=null)
                ImageUploadToServerFunction();
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfile.this);

                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Error in image. Select another")
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private static Bitmap resize(Bitmap image, int maxWidth, int maxHeight) {
        if (maxHeight > 0 && maxWidth > 0) {
            int width = image.getWidth();
            int height = image.getHeight();
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            image = Bitmap.createScaledBitmap(image, finalWidth, finalHeight, true);
            return compress(image);
        } else {
            return compress(image);
        }
    }
    private static Bitmap compress(Bitmap bitmap){
        if(bitmap.getByteCount()>1024*1024*10)
            quality=50;
        else
            if(bitmap.getByteCount()>1024*1024*5)
                quality=25;
            else
                quality=20;
       return bitmap;
    }
    public void ImageUploadToServerFunction(){
        compress(bitmap);
        ByteArrayOutputStream byteArrayOutputStreamObject ;
        byteArrayOutputStreamObject = new ByteArrayOutputStream();
        resize(bitmap,profileimg.getWidth(),profileimg.getHeight()).compress(Bitmap.CompressFormat.PNG, quality, byteArrayOutputStreamObject);
        byte[] byteArrayVar = byteArrayOutputStreamObject.toByteArray();
        final String ConvertImage = Base64.encodeToString(byteArrayVar, Base64.DEFAULT);
        class AsyncTaskUploadClass extends AsyncTask<Void,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading.setMessage("Image is loading. Please Wait!");
                loading.show();
            }
            @Override
            protected void onPostExecute(String string) {
                super.onPostExecute(string);
                // Dismiss the progress dialog after done uploading.
                loading.dismiss();
                // Printing uploading success message coming from server on android app.
                if(string.equalsIgnoreCase("success")) {
                    se.putString("pic", ConvertImage);
                    se.commit();
                    Toast.makeText(ViewProfile.this, "Profile Pic Changed", Toast.LENGTH_SHORT).show();
                }
                else{
                    if(string.toLowerCase().contains("error")){
                        profileimg.setImageResource(R.drawable.defaultpic);
                        bitmap=null;
                        AlertDialog.Builder builder = new AlertDialog.Builder(ViewProfile.this);

                        builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        builder.setMessage("Error in uploading. Try again!")
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
                // Setting image as transparent after done uploading.
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        finish();
                    }
                }, 3000);


            }

/*            private String saveToInternalStorage(Bitmap bitmapImage){
                ContextWrapper cw = new ContextWrapper(getApplicationContext());
                // path to /data/data/yourapp/app_data/imageDir
                File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
                // Create imageDir
                File mypath=new File(directory,"profilepic.jpeg");
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(mypath);
                    // Use the compress method on the BitMap object to write image to the OutputStream
                    bitmapImage.compress(Bitmap.CompressFormat.PNG, 20, fos);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return directory.getAbsolutePath();
            }*/

            @Override
            protected String doInBackground(Void... params) {
                ImageProcessClass imageProcessClass = new ImageProcessClass();
                HashMap<String,String> HashMapParams = new HashMap<String,String>();
                HashMapParams.put("mobileno", sp.getString("mobileno",null));
                HashMapParams.put(ImagePath, ConvertImage);
                String FinalData = imageProcessClass.ImageHttpRequest(UrlNeuugen.profilenewpic, HashMapParams);
                return FinalData;
            }
        }
        AsyncTaskUploadClass AsyncTaskUploadClassOBJ = new AsyncTaskUploadClass();
        AsyncTaskUploadClassOBJ.execute();
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
                    while ((RC2 = bufferedReaderObject.readLine()) != null){
                        stringBuilder.append(RC2);
                    }
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
