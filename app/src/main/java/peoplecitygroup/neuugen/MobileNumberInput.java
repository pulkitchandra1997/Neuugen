package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class MobileNumberInput extends AppCompatActivity implements View.OnClickListener {

    com.google.android.material.textfield.TextInputEditText phonenum;
    com.google.android.material.button.MaterialButton nextbtn;
    String phonetext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_number_input);

        idLink();
        listenerLink();

    }

    public void idLink() {
        phonenum = findViewById(R.id.phonenum);
        nextbtn = findViewById(R.id.nextbtn);
    }

    public void listenerLink() {
        nextbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.nextbtn) {
            phonetext = phonenum.getText().toString().trim();
            if (TextUtils.isEmpty(phonetext)) {
                phonenum.setError("Enter phone number");
                phonenum.requestFocus();
            } else {
                if (!Validation.isValidPhone(phonetext)) {
                    phonenum.setError("Enter Valid Phone no");
                    phonenum.requestFocus();
                } else {
                    Intent intent = new Intent(MobileNumberInput.this, OtpInputActivity.class);
                    intent.putExtra("phone",phonetext);
                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(MobileNumberInput.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }

                }
            }

        }
    }
}

