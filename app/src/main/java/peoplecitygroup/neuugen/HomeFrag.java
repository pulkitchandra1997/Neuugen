package peoplecitygroup.neuugen;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;


import peoplecitygroup.neuugen.HomeServices.ApplianceRepairServices.Appliancerepair;
import peoplecitygroup.neuugen.HomeServices.EventServices.EventsActivity;
import peoplecitygroup.neuugen.HomeServices.HomeRenovationServices.HomeRenovation;
import peoplecitygroup.neuugen.HomeServices.LearningServices.LearningActivity;
import peoplecitygroup.neuugen.HomeServices.SalonServices.HomeSalon;
import peoplecitygroup.neuugen.properties.PostAd;
import peoplecitygroup.neuugen.properties.PropertiesForm;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class HomeFrag extends Fragment implements View.OnClickListener {

    androidx.appcompat.widget.AppCompatTextView locationicon;

    LinearLayout location;
    AppCompatImageView homeimg1,homeimg2,homeimg3,homeimg4,homeimg5;

    FloatingActionButton postformfab;

    TableLayout serviceicons;

    AppCompatTextView locationtext,serviceheadtext;
    View v;

    LinearLayout properties,salon,events,appliancerepair,homerenovation,learning,protectionprgrm;

    String titlearr[]={"Change Location","Post Your ads"};
    String contentarr[]={"Select your city where you want service","Post your Properties adverisements by clicking here"};

    View viewarr[]={location,postformfab};
    ShowcaseView showcaseView,showcaseView2,showcaseView3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.homefragment, container, false);

        idLink();
        listenerLink();
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        locationicon.setTypeface(font);

        showcaseView=new ShowcaseView.Builder(getActivity())
                .withMaterialShowcase()
                .setTarget(new ViewTarget(postformfab))
                .setContentTitle("Post your Ads")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentText("Post your Properties advertisements by clicking here")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextShowcase();
                    }
                })
                .build();

        return v;
    }

    private void nextShowcase() {
        showcaseView.hide();
        showcaseView2=new ShowcaseView.Builder(getActivity())
                .withMaterialShowcase()
                .setTarget(new ViewTarget(locationtext))
                .setContentTitle("Change your location")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentText("Choose your city where you want your services")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        lastShowcase();
                    }
                })
                .build();
    }

    private void lastShowcase() {
        showcaseView2.hide();
        showcaseView3=new ShowcaseView.Builder(getActivity())
                .withMaterialShowcase()
                .setTarget(new ViewTarget(serviceheadtext))
                .setContentTitle("Choose Service")
                .setStyle(R.style.CustomShowcaseTheme2)
                .setContentText("Choose one of the services according to your need.")
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseView3.hide();
                    }
                })
                .build();
    }


    public void idLink()
    {
        locationicon=v.findViewById(R.id.locationicon);
        location=v.findViewById(R.id.location);
        postformfab=v.findViewById(R.id.postformfab);
        properties=v.findViewById(R.id.properties);
        events=v.findViewById(R.id.events);
        homerenovation=v.findViewById(R.id.homerenovation);
        appliancerepair=v.findViewById(R.id.appliancerepair);
        learning=v.findViewById(R.id.learning);
        salon=v.findViewById(R.id.salon);
        homeimg1=v.findViewById(R.id.homeimg1);
        homeimg2=v.findViewById(R.id.homeimg2);
        homeimg3=v.findViewById(R.id.homeimg3);
        homeimg4=v.findViewById(R.id.homeimg4);
        homeimg5=v.findViewById(R.id.homeimg5);
        protectionprgrm=v.findViewById(R.id.protectionprgrm);
        serviceicons=v.findViewById(R.id.serviceicons);
        locationtext=v.findViewById(R.id.locationtext);
        serviceheadtext=v.findViewById(R.id.serviceheadtext);
    }

    public void listenerLink()
    {
        postformfab.setOnClickListener(this);
        properties.setOnClickListener(this);
        events.setOnClickListener(this);
        homerenovation.setOnClickListener(this);
        learning.setOnClickListener(this);
        salon.setOnClickListener(this);
        appliancerepair.setOnClickListener(this);
        protectionprgrm.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.protectionprgrm)
        {
            Intent intent = new Intent(getActivity(), ProtectionProgram.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

        if (v.getId()==R.id.postformfab)
        {
            Intent intent = new Intent(getActivity(), PostAd.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
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
        if (v.getId()==R.id.learning)
        {
            Intent intent = new Intent(getActivity(), LearningActivity.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }

    }
}
