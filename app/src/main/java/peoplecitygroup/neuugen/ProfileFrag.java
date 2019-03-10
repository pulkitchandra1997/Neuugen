package peoplecitygroup.neuugen;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class ProfileFrag extends Fragment implements View.OnClickListener {


    ImageView editprofile;
    androidx.appcompat.widget.AppCompatTextView logout;

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
    }
    public void listenerLink()
    {
        logout.setOnClickListener(this);
        editprofile.setOnClickListener(this);
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
    }
}
