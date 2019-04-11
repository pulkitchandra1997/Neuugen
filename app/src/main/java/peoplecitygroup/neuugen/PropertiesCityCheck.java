package peoplecitygroup.neuugen;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class PropertiesCityCheck extends AppCompatActivity implements View.OnClickListener {

    Intent intent;
    String adtypetext=null,citytext,propertiesID;
    TextInputEditText citycheck;
    MaterialButton next;
    ProgressDialog loading = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties_city_check);

        idLink();
        listenerLink();
        hideSoftKeyboard();

        intent=getIntent();
        adtypetext=intent.getStringExtra("adtype");
        propertiesID=intent.getStringExtra("propertiesid");

        loading = new ProgressDialog(PropertiesCityCheck.this,R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

    }
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    private void idLink() {
        citycheck=findViewById(R.id.citycheck);
        next=findViewById(R.id.next);
    }

    private void listenerLink() {
        next.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.next)
        {
            citytext=citycheck.getText().toString().trim();
            if (TextUtils.isEmpty(citytext))
            {
                  citycheck.setError("Enter City");
                  citycheck.requestFocus();
            }
        }

    }
}
