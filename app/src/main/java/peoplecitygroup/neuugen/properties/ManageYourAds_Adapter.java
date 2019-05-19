package peoplecitygroup.neuugen.properties;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.util.List;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.AD;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class ManageYourAds_Adapter extends RecyclerView.Adapter<ManageYourAds_Adapter.MyViewHolder> {
     List<AD> adList;
     Activity activity;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= activity.getLayoutInflater().inflate(R.layout.manage_ad_cardview,null);
        Log.d("checkview","yes");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final AD ad = adList.get(position);
        holder.propertytype.setText(ad.getPropertytype());
        holder.cost.setText(ad.getPrice());
        holder.area.setText(ad.getArea());
        holder.city.setText(ad.getCity());
        if(ad.getVerified().trim().equalsIgnoreCase("1"))
            holder.verified.setVisibility(View.VISIBLE);
        else holder.notverified.setVisibility(View.VISIBLE);
        if(ad.getAvailable().trim().equalsIgnoreCase("1"))
            holder.available.setVisibility(View.VISIBLE);
        else holder.notavailable.setVisibility(View.VISIBLE);
        if(ad.getPic1()!=null||ad.getPic1().trim()!="")
            Picasso.with(activity).load(ad.getPic1())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.image1);
        /*if(ad.getPic2()!=null||ad.getPic2().trim()!="")
            Picasso.with(activity).load(ad.getPic2())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.image2);
        if(ad.getPic3()!=null||ad.getPic3().trim()!="")
            Picasso.with(activity).load(ad.getPic3())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.image3);*/
        holder.viewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //VIEWBTN
                viewAd(holder.viewbtn,ad);
            }
        });
        holder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DELETEBTN
                deleteAd(holder.deletebtn,ad);
            }
        });
    }

    private void deleteAd(MaterialButton deletebtn, AD ad) {

    }

    private void viewAd(MaterialButton viewbtn, AD ad) {
        Intent intent = new Intent(activity, PropertyDetails.class);
        intent.putExtra("flag","object");
        intent.putExtra("object", ad);
        if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
            ActivityOptions options = ActivityOptions.makeCustomAnimation(activity, R.anim.fade_in, R.anim.fade_out);
            activity.startActivity(intent, options.toBundle());
        } else {
            activity.startActivity(intent);
        }
    }

    @Override
    public int getItemCount() {
        return adList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView propertytype,cost,area,city,rupeeicon;
        public LinearLayout verified,notverified,available,notavailable;
        public ImageView image1,image2,image3;
        public MaterialButton viewbtn,deletebtn;
        public MyViewHolder(View view) {
            super(view);
            propertytype = (TextView) view.findViewById(R.id.properttype);
            cost = (TextView) view.findViewById(R.id.cost);
            area = (TextView) view.findViewById(R.id.area);
            city = (TextView) view.findViewById(R.id.city);
            verified = (LinearLayout) view.findViewById(R.id.verified);
            notverified = (LinearLayout) view.findViewById(R.id.notverified);
            available = (LinearLayout) view.findViewById(R.id.available);
            notavailable = (LinearLayout) view.findViewById(R.id.notavailable);
            image1 = (ImageView) view.findViewById(R.id.image1);
            //image2 = (ImageView) view.findViewById(R.id.image2);
            //image3 = (ImageView) view.findViewById(R.id.image3);
            viewbtn=(MaterialButton) view.findViewById(R.id.viewbtn);
            deletebtn=(MaterialButton)view.findViewById(R.id.deletebtn);
            rupeeicon=(TextView)view.findViewById(R.id.rupeeicon);
            Typeface font = Typeface.createFromAsset(activity.getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
            rupeeicon.setTypeface(font);

        }
    }
    public ManageYourAds_Adapter(Activity activity, List<AD> adList){
        //this.mContext=mContext;
        this.adList=adList;
        this.activity=activity;
    }

}
