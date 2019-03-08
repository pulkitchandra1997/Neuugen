package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;

public class UserPermission extends AppCompatActivity implements View.OnClickListener {

    MaterialButton allowbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_permission);

        allowbtn=findViewById(R.id.allowbtn);

        allowbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

    }
}
