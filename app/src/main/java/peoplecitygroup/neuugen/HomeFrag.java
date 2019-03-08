package peoplecitygroup.neuugen;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.fragment.app.Fragment;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class HomeFrag extends Fragment implements View.OnClickListener {

    androidx.appcompat.widget.AppCompatTextView locationicon,searchbox;

    LinearLayout location;
    View v;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.homefrag, container, false);

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
        searchbox=v.findViewById(R.id.searchbox);
    }

    public void listenerLink()
    {
        searchbox.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.searchbox)
        {
            Intent intent = new Intent(getActivity(), SearchActivity.class);
            if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                ActivityOptions options = ActivityOptions.makeCustomAnimation(getActivity(), R.anim.fade_in, R.anim.fade_out);
                startActivity(intent, options.toBundle());
            } else {
                startActivity(intent);
            }

        }
    }
}
