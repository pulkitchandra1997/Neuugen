package peoplecitygroup.neuugen;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;


import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class HomeFrag extends Fragment implements View.OnClickListener {

    androidx.appcompat.widget.AppCompatTextView locationicon;

    LinearLayout location;

    /*FloatingActionButton postformfab;*/

    View v;

    LinearLayout properties,salon,events,appliancerepair,homerenovation,holidayplanner;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.homefragment, container, false);

        idLink();
        listenerLink();

        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        locationicon.setTypeface(font);


        return v;
    }

    public void idLink()
    {
        locationicon=v.findViewById(R.id.locationicon);
        location=v.findViewById(R.id.location);
/*
        postformfab=v.findViewById(R.id.postformfab);
*/
        properties=v.findViewById(R.id.properties);
        events=v.findViewById(R.id.events);
        homerenovation=v.findViewById(R.id.homerenovation);
        appliancerepair=v.findViewById(R.id.appliancerepair);
        holidayplanner=v.findViewById(R.id.holidayplanner);
        salon=v.findViewById(R.id.salon);
    }

    public void listenerLink()
    {
        /*postformfab.setOnClickListener(this);*/
        properties.setOnClickListener(this);
        events.setOnClickListener(this);
        homerenovation.setOnClickListener(this);
        holidayplanner.setOnClickListener(this);
        salon.setOnClickListener(this);
        appliancerepair.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

      /*  if (v.getId()==R.id.postformfab)
        {
            Intent intent = new Intent(getActivity(), PostAd.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }*/
        if (v.getId()==R.id.appliancerepair)
        {
            Intent intent = new Intent(getActivity(), Appliancerepair.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.properties)
        {
            Intent intent = new Intent(getActivity(), PropertiesForm.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.salon)
        {
            Intent intent = new Intent(getActivity(), HomeSalon.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.homerenovation)
        {
            Intent intent = new Intent(getActivity(), HomeRenovation.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.events)
        {
            Intent intent = new Intent(getActivity(), EventsActivity.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

    }
}
