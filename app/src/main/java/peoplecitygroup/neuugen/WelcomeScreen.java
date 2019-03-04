package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
        if(checkLoggedInProfile()){

        }
        else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(WelcomeScreen.this, MobileNumberInput.class);
                    ActivityOptions options = ActivityOptions.makeCustomAnimation(WelcomeScreen.this, R.anim.fade_in, R.anim.fade_out);
                    startActivity(intent, options.toBundle());
                    finish();
                }
            }, 2000);
        }
    }

    private boolean checkLoggedInProfile() {
        return false;
    }
}
