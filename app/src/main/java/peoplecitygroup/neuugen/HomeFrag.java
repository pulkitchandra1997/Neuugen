package peoplecitygroup.neuugen;

import android.app.ActivityOptions;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import peoplecitygroup.neuugen.common_req_files.PROFILE;
import peoplecitygroup.neuugen.properties.PostAd;
import peoplecitygroup.neuugen.properties.PropertiesForm;

import static android.content.Context.MODE_PRIVATE;
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

    ShowcaseView showcaseView1,showcaseView2,showcaseView3;

    ViewTarget[] viewTargets;
    String[] titles=new String[]{"Post your Ads","Change your location","Choose Service"};
    String[] contents=new String[]{"Post your Properties advertisements by clicking here","Choose your city where you want services","Choose one of the services according to your need."};
    SharedPreferences sp;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.homefragment, container, false);

        idLink();
        listenerLink();
        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        locationicon.setTypeface(font);
        sp=this.getActivity().getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        viewTargets=new ViewTarget[]{new ViewTarget(postformfab),new ViewTarget(locationtext),new ViewTarget(serviceheadtext)};
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkFirstTime();
            }
        },2000);
        return v;
    }

    private void checkFirstTime() {
        if(sp!=null) {
            int firsttime=sp.getInt("firsttime",1);
            if(firsttime!=0) {
                showCase(0);
            }
        }
    }

    private void showCase(int i) {
        Log.d("showcase",String.valueOf(i));
        switch (i){
            case 0:showcaseView1(titles[i],contents[i],viewTargets[i],i);break;
            case 1:showcaseView2(titles[i],contents[i],viewTargets[i],i);break;
            case 2:showcaseView3(titles[i],contents[i],viewTargets[i],i);break;
            default:SharedPreferences.Editor se=sp.edit();
                    se.putInt("firsttime",0);
                    se.commit();
        }
    }

    private void showcaseView3(final String title, final String content, ViewTarget viewTarget, final int i) {
        showcaseView1=new ShowcaseView.Builder(getActivity())
                .withHoloShowcase()
                .setStyle(R.style.CustomShowcaseTheme2)
                .setTarget(viewTarget)
                .setContentTitle(title)
                .setContentText(content)
                .useDecorViewAsParent()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseView1.hide();
                        showCase(i+1);
                    }
                })
                .build();
    }

    private void showcaseView2(final String title, final String content, ViewTarget viewTarget,final int i) {
        showcaseView2=new ShowcaseView.Builder(getActivity())
                .setStyle(R.style.CustomShowcaseTheme)
                .withHoloShowcase()
                .setTarget(viewTarget)
                .setContentTitle(title)
                .useDecorViewAsParent()
                .setContentText(content)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseView2.hide();
                        showCase(i+1);
                    }
                })
                .build();
    }

    private void showcaseView1(final String title, final String content, ViewTarget viewTarget,final int i) {
        showcaseView3=new ShowcaseView.Builder(getActivity())
                .withHoloShowcase()
                .setStyle(R.style.CustomShowcaseTheme)
                .setTarget(viewTarget)
                .setContentTitle(title)
                .setContentText(content)
                .useDecorViewAsParent()
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showcaseView3.hide();
                        showCase(i+1);
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
