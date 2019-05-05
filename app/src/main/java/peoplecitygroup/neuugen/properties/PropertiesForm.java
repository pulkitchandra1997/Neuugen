package peoplecitygroup.neuugen.properties;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatSpinner;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;

import peoplecitygroup.neuugen.R;

public class PropertiesForm extends AppCompatActivity implements View.OnClickListener {

    RadioGroup propertytags;
    AppCompatRadioButton buybtn,rentbtn;
    AppCompatSpinner buypropertytype,rentpropertytype;
    MaterialButton searchbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_properties_form);

        idLink();
        listenerLink();

    }

    private void idLink() {

        propertytags=findViewById(R.id.propertytags);
        buybtn=findViewById(R.id.buybtn);
        rentbtn=findViewById(R.id.rentbtn);
        rentpropertytype=findViewById(R.id.rentpropertytype);
        buypropertytype=findViewById(R.id.buypropertytype);
        searchbutton=findViewById(R.id.searchbutton);
    }

    private void listenerLink() {
        searchbutton.setOnClickListener(this);
        rentbtn.setOnClickListener(this);
        buybtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v.getId()==R.id.rentbtn)
        {
            rentpropertytype.setVisibility(View.VISIBLE);
            buypropertytype.setVisibility(View.GONE);
        }
        if (v.getId()==R.id.buybtn)
        {
            rentpropertytype.setVisibility(View.GONE);
            buypropertytype.setVisibility(View.VISIBLE);
        }
    }
}
