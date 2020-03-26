package peoplecitygroup.neuugen;

import android.app.Activity;
import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.RectangularBounds;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import peoplecitygroup.neuugen.HomeServices.ApplianceRepairServices.Appliancerepair;
import peoplecitygroup.neuugen.HomeServices.EventServices.EventsActivity;
import peoplecitygroup.neuugen.HomeServices.HomeRenovationServices.HomeRenovation;
import peoplecitygroup.neuugen.HomeServices.LearningServices.LearningActivity;
import peoplecitygroup.neuugen.HomeServices.SalonServices.HomeSalon;
import peoplecitygroup.neuugen.common_req_files.ServiceCheck;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.common_req_files.VolleyCallback;
import peoplecitygroup.neuugen.properties.PostAd;
import peoplecitygroup.neuugen.properties.PropertiesForm;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class HomeFrag extends Fragment implements View.OnClickListener {

    private androidx.appcompat.widget.AppCompatTextView locationicon;
    private LinearLayout location;
    private AppCompatImageView homeimg1,homeimg2,homeimg3,homeimg4,homeimg5;
    private int AUTOCOMPLETE_REQUEST_CODE=1011;
    private FloatingActionButton postformfab;
    private boolean firsttime=false;
    private TableLayout serviceicons;
    ProgressDialog loading = null;
    AppCompatTextView locationtext,serviceheadtext;
    View v;
    Activity activity;

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
        activity=getActivity();

        idLink();
        listenerLink();

        loadhomeimgs();

        loading = new ProgressDialog(getContext(),R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Loading");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
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

    private void loadhomeimgs() {
        Picasso.with(getContext()).load("http://neuugen.com/app/neuugen/images/services/homeimage1.jpg")
                .placeholder(R.drawable.imgplaceholder)
                .into(homeimg1);
        Picasso.with(getContext()).load("http://neuugen.com/app/neuugen/images/services/homeimage2.jpg")
                .placeholder(R.drawable.imgplaceholder)
                .into(homeimg2);
        Picasso.with(getContext()).load("http://neuugen.com/app/neuugen/images/services/homeimage3.jpg")
                .placeholder(R.drawable.imgplaceholder)
                .into(homeimg3);
        Picasso.with(getContext()).load("http://neuugen.com/app/neuugen/images/services/homeimage4.jpg")
                .placeholder(R.drawable.imgplaceholder)
                .into(homeimg4);
        Picasso.with(getContext()).load("http://neuugen.com/app/neuugen/images/services/homeimage5.jpg")
                .placeholder(R.drawable.imgplaceholder)
                .into(homeimg5);

    }

    private void showcity() {
        String city=sp.getString("set_city",null);
        if(city==null) {
            firsttime=true;
            changeCity(firsttime);
        }else
            locationtext.setText(city);
    }

    private void checkFirstTime() {
        if(sp!=null) {
            int firsttime=sp.getInt("firsttime",1);
            if(firsttime!=0) {
                showCase(0);
            }
            else
                showcity();
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
                    showcity();
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
        location.setOnClickListener(this);
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
        if (v.getId()==R.id.location){
            changeCity(firsttime);
        }
    }
    private void changeCity(final Boolean firsttime) {
        String message="Enter your new city to experience features of the app.";
        if(firsttime)
            message="Enter city to provide best services.";
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openActivityForCity();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(firsttime)
                            getActivity().finish();
                    }
                })
                .setIcon(R.mipmap.ic_launcher_round);
        AlertDialog dialog = builder.create();
        dialog.show();
        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        negativeButton.setTextColor(Color.parseColor("#FF12B2FA"));
    }

    private void openActivityForCity() {

// Set the fields to specify which types of place data to
// return after the user has made a selection.

        RectangularBounds bounds = RectangularBounds.newInstance(
                new LatLng(23.63936, 68.14712),
                new LatLng(28.20453, 97.34466));

        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), UrlNeuugen.returnKey());
        }
        List<Place.Field> fields = Arrays.asList(Place.Field.NAME);
        Intent intent = new Autocomplete.IntentBuilder(
                AutocompleteActivityMode.OVERLAY, fields)
                .setCountry("IN")
                .setLocationBias(bounds)
                //.setLocationRestriction(bounds)
                .setTypeFilter(TypeFilter.CITIES)
                .build(getActivity());
        startActivityForResult(intent,AUTOCOMPLETE_REQUEST_CODE );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("placesapi",requestCode+"----"+resultCode+"____"+data);

        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                checkActiveService(place);
                Log.i("placesapi", "Place: " + place.getName());
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("placesapi", status.getStatusMessage()+status);
                if(firsttime)
                    getActivity().finish();
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
                if(firsttime){
                    getActivity().finish();
                }
            }
        }
    }

    private void checkActiveService(Place place) {
        loading.show();
        ServiceCheck serviceCheck=new ServiceCheck();
        serviceCheck.check(UrlNeuugen.appRepairInstallId,place.getName(), getContext(), new VolleyCallback() {

            @Override
            public void onSuccess(String result) {
                loading.dismiss();
                setPlace(place);
                Log.d("receivedmsg",result);
            }

            @Override
            public void onError(String response) {
                Log.i("receivedmsg",response);
                loading.dismiss();
                serviceNotAvailable(response);
            }

            @Override
            public void onVolleyError() {
                loading.dismiss();
                serviceNotAvailable("Error, Try Again.");
            }
        });
    }

    private void serviceNotAvailable(String response) {
        Log.i("receivedmsg2",response);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        Log.i("receivedmsg3",response+builder);
            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage(response)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            openActivityForCity();
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

    }

    private void setPlace(Place place) {
        SharedPreferences sp;
        SharedPreferences.Editor se;
        sp=getActivity().getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        se=sp.edit();
        se.putString("set_city", place.getName());
        se.commit();
        locationtext.setText(place.getName());
    }

}
