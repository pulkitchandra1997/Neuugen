package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class LearningActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton actingbtn,dancingbtn,makeupbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        idLink();
        listenerLink();

    }

    private void idLink() {
        actingbtn=findViewById(R.id.actingbtn);
        dancingbtn=findViewById(R.id.dancingbtn);
        makeupbtn=findViewById(R.id.makeupbtn);
    }

    private void listenerLink() {
        actingbtn.setOnClickListener(this);
        dancingbtn.setOnClickListener(this);
        makeupbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.dancingbtn)
        {

        }
        if (v.getId()==R.id.makeupbtn)
        {

        }
        if (v.getId()==R.id.actingbtn)
        {

        }
    }
}
