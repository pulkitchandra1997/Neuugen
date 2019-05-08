package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;

import peoplecitygroup.neuugen.R;

public class ManageYourAds extends AppCompatActivity {
    ProgressDialog loading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_your_ads);
        loading = new ProgressDialog(ManageYourAds.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }
}
