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

import com.synnapps.carouselview.CarouselView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.synnapps.carouselview.ImageListener;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class HomeFrag extends Fragment implements View.OnClickListener {

    androidx.appcompat.widget.AppCompatTextView locationicon;

    LinearLayout location;
    View v;
    CarouselView carouselView;
    int[] sampleImages = {R.drawable.defaultpic, R.drawable.camera, R.drawable.defaultpic, R.drawable.camera};


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.homefrag, container, false);

        idLink();
        listenerLink();




        carouselView = v.findViewById(R.id.carouselView);
        carouselView.setPageCount(sampleImages.length);

        ImageListener imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(sampleImages[position]);
            }
        };
        carouselView.setImageListener(imageListener);



        Typeface font = Typeface.createFromAsset(getActivity().getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        locationicon.setTypeface(font);


        return v;
    }

    public void idLink()
    {
        locationicon=v.findViewById(R.id.locationicon);
        location=v.findViewById(R.id.location);
    }

    public void listenerLink()
    {

    }

    @Override
    public void onClick(View v) {

    }
}
