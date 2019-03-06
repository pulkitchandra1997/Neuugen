package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

public class UserDetails extends AppCompatActivity implements View.OnClickListener {

    com.google.android.material.textfield.TextInputEditText name,city,email;
    androidx.appcompat.widget.AppCompatTextView autodetect,autodetecticon;
    com.google.android.material.button.MaterialButton submit;
    String nametext,emailtext,citytext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        idLink();
        listenerLink();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        autodetecticon.setTypeface(font);
    }
    public void idLink() {

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        city=findViewById(R.id.city);
        submit=findViewById(R.id.submit);
        autodetect=findViewById(R.id.autodetect);
        autodetecticon=findViewById(R.id.autodetecticon);
    }

    public void listenerLink() {
        autodetect.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.autodetect)
        {

        }
        if (v.getId()==R.id.submit)
        {

        }

    }
}
