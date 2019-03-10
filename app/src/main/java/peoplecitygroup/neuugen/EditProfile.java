package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;

public class EditProfile extends AppCompatActivity implements View.OnClickListener {

    androidx.appcompat.widget.AppCompatTextView backtoprofile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        idLink();
        listenerLink();

        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        backtoprofile.setTypeface(font);
    }

    public void idLink()
    {
        backtoprofile=findViewById(R.id.backtoprofile);
    }
    public void listenerLink()
    {
        backtoprofile.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.backtoprofile)
        {
            onBackPressed();
        }
    }
}
