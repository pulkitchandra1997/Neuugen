package peoplecitygroup.neuugen;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class ProfileFrag extends Fragment implements View.OnClickListener {


    ImageView editprofile;
    androidx.appcompat.widget.AppCompatTextView logout,username,emailid,mobilenum;
    LinearLayout postad,help,manageads,customersupport,rateus,aboutus,termsofuse;
    BootstrapCircleThumbnail profilepic;

    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v  = inflater.inflate(R.layout.accountfrag, container, false);

       idLink();
       listenerLink();


        return v;
    }

    public void idLink()
    {
        editprofile=v.findViewById(R.id.editprofile);
        logout=v.findViewById(R.id.logout);
        profilepic=v.findViewById(R.id.profilepic);
        username=v.findViewById(R.id.username);
        emailid=v.findViewById(R.id.emailid);
        mobilenum=v.findViewById(R.id.mobilenum);
        postad=v.findViewById(R.id.postad);
        manageads=v.findViewById(R.id.manageads);
        help=v.findViewById(R.id.help);
        rateus=v.findViewById(R.id.rateus);
        aboutus=v.findViewById(R.id.aboutus);
        termsofuse=v.findViewById(R.id.termsofuse);
        customersupport=v.findViewById(R.id.customersupport);

    }
    public void listenerLink()
    {
        logout.setOnClickListener(this);
        editprofile.setOnClickListener(this);
        profilepic.setOnClickListener(this);
        postad.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.editprofile)
        {
            Intent intent = new Intent(getActivity(), EditProfile.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }

        }
        if (v.getId()==R.id.logout)
        {

        }
        if (v.getId()==R.id.profilepic)
        {
            Intent intent = new Intent(getActivity(), ViewProfile.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.postad)
        {
            Intent intent = new Intent(getActivity(), PostAd.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }
}
