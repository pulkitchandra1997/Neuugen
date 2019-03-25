package peoplecitygroup.neuugen;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.beardedhen.androidbootstrap.BootstrapCircleThumbnail;
import com.beardedhen.androidbootstrap.BootstrapThumbnail;
import com.squareup.picasso.Picasso;

import java.io.File;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;
import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class ProfileFrag extends Fragment implements View.OnClickListener {


    ImageView editprofile;
    androidx.appcompat.widget.AppCompatTextView logout,username,emailid,mobilenum;
    LinearLayout postad,help,manageads,customersupport,rateus,aboutus,termsofuse;
    BootstrapCircleThumbnail profilepic;

    SharedPreferences sp;

    SharedPreferences.Editor se;
    View v;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       v  = inflater.inflate(R.layout.accountfrag, container, false);

       idLink();
       listenerLink();

        sp=getActivity().getSharedPreferences("NeuuGen_data",MODE_PRIVATE);
        se=sp.edit();

        fill();

        return v;
    }

    public void fill() {
        username.setText(sp.getString("name", "name"));
        emailid.setText(sp.getString("email", "email"));
        mobilenum.setText(sp.getString("mobileno", "mobileno"));

        if(sp.getString("pic",null)!=null) {
            Picasso.with(getContext()).load(sp.getString("pic",null))
                    .placeholder(R.drawable.defaultpic)
                    .into(profilepic);

        }
        else
            profilepic.setImageResource(R.drawable.defaultpic);
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
        customersupport.setOnClickListener(this);
        help.setOnClickListener(this);
        termsofuse.setOnClickListener(this);
        aboutus.setOnClickListener(this);
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
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setIcon(R.mipmap.ic_launcher_round);
            builder.setTitle(Html.fromHtml("<font color='#FF0000'>neuugen</font>"));
            builder.setMessage("Are you sure you want to logout?");
            builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    se.remove("email");
                    se.remove("name");
                    se.remove("mobileno");
                    se.remove("dob");
                    se.remove("address");
                    se.remove("emailverified");
                    se.remove("city");
                    se.remove("addressverified");
                    se.remove("pincode");
                    se.remove("gender");
                    se.remove("profileflag");
                    se.remove("state");
                    se.remove("pic");
                    se.commit();
                    Intent intent = new Intent(getActivity(), MobileNumberInput.class);
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
            Button negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            negativeButton.setTextColor(Color.parseColor("#FF12B2FA"));
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
        if (v.getId()==R.id.customersupport)
        {
            Intent intent = new Intent(getActivity(), Customercare.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.aboutus)
        {
            Intent intent = new Intent(getActivity(), AboutUs.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.termsofuse)
        {
            Intent intent = new Intent(getActivity(), Termsofuse.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.help)
        {
            Intent intent = new Intent(getActivity(), HelpSupportActivity.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
        if (v.getId()==R.id.manageads)
        {
            Intent intent = new Intent(getActivity(), ManageYourAds.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fill();
    }

}
