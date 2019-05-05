package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;

public class Customercare extends AppCompatActivity implements View.OnClickListener {

    MaterialButton callbtn,mailbtn,msgbtn;
    TextInputEditText name,email,message,subject;
    String nametext,emailtext,msgtext,subtext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customercare);

        idLink();
        listenerLink();
        hideSoftKeyboard();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        callbtn.setTypeface(font);
        mailbtn.setTypeface(font);
    }
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }


    private void idLink() {
        callbtn=findViewById(R.id.callbtn);
        mailbtn=findViewById(R.id.mailbtn);
        msgbtn=findViewById(R.id.msgbtn);
        name=findViewById(R.id.namecs);
        email=findViewById(R.id.emailcs);
        message=findViewById(R.id.messagecs);
        subject=findViewById(R.id.subjectcs);
    }

    private void listenerLink() {
        callbtn.setOnClickListener(this);
        mailbtn.setOnClickListener(this);
        msgbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.callbtn)
        {

                Intent i = new Intent(Intent.ACTION_DIAL);
                Uri u = Uri.parse("tel:"+ UrlNeuugen.csphone);
                i.setData(u);
                /*if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }*/
                startActivity(i);

        }
        if (v.getId()==R.id.mailbtn)
        {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String[] recipients={UrlNeuugen.csemail};
            intent.putExtra(Intent.EXTRA_EMAIL, recipients);
            intent.putExtra(Intent.EXTRA_SUBJECT,subtext);
        /*intent.putExtra(Intent.EXTRA_TEXT,"Body of the content here...");
        intent.putExtra(Intent.EXTRA_CC,"mailcc@gmail.com");*/
            intent.setType("text/html");
            intent.setPackage("com.google.android.gm");
            startActivity(Intent.createChooser(intent, "Send mail"));
        }
        if (v.getId()==R.id.msgbtn)
        {
            msgtext=message.getText().toString().trim();
            nametext=name.getText().toString().trim();
            emailtext=email.getText().toString().trim();
            subtext=subject.getText().toString().trim();
            if (TextUtils.isEmpty(nametext) || TextUtils.isEmpty(emailtext) || TextUtils.isEmpty(subtext)||TextUtils.isEmpty(msgtext)) {
                if (TextUtils.isEmpty(nametext)) {
                    name.setError("Enter Name");
                    name.requestFocus();
                } else if (TextUtils.isEmpty(emailtext)) {
                    email.setError("Enter Email");
                    email.requestFocus();
                } else if (TextUtils.isEmpty(subtext)) {
                    subject.setError("Enter Subject");
                    subject.requestFocus();
                } else if (TextUtils.isEmpty(msgtext)) {
                    message.setError("Select DOB");
                    message.requestFocus();
                }
            }else {

            }

        }

    }
}
