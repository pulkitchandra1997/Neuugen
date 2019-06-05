package peoplecitygroup.neuugen.properties;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import java.util.List;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.AD;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class Ad_Adapter extends RecyclerView.Adapter<Ad_Adapter.MyViewHolder> {
     List<AD> adList;
     Activity activity;


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= activity.getLayoutInflater().inflate(R.layout.customcardlist,null);
        Log.d("checkview","yes");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        final AD ad = adList.get(position);
        String temp="";
        switch (Integer.parseInt(ad.getPropertytype().trim())){
            case 0:temp="Apartment";break;
            case 1:temp="Independent House";break;
            case 2:temp="Villa";break;
            case 3:temp="Hostel";break;
            case 4:temp="Office Area";break;
            case 5:temp="Shop Area";break;
            case 6:temp="Plot";break;
        }
        holder.propertytype.setText(temp);
        holder.cost.setText(ad.getPrice());
        holder.area.setText(ad.getArea().trim()+", "+ad.getCity().trim());
        Log.d("verifiedcheck",ad.getPrice()+ad.getVerified());
        if(ad.getVerified().trim().equalsIgnoreCase("1")) {
            holder.verified.setVisibility(View.VISIBLE);
            holder.notverified.setVisibility(View.GONE);
        }
        else{ holder.notverified.setVisibility(View.VISIBLE);holder.verified.setVisibility(View.GONE);
        }
        if(ad.getAvailable().trim().equalsIgnoreCase("1")) {
            holder.available.setVisibility(View.VISIBLE);holder.notavailable.setVisibility(View.GONE);
        }
        else {holder.notavailable.setVisibility(View.VISIBLE);holder.available.setVisibility(View.GONE);}
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });
    }

    @Override
    public int getItemCount() {
        return adList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView propertytype,cost,area,rupeeicon;
        public LinearLayout verified,notverified,available,notavailable;
        public ImageView image1;
        public CardView cardView;
        public MyViewHolder(View view) {
            super(view);
            cardView=view.findViewById(R.id.customcard);
            propertytype = (TextView) view.findViewById(R.id.properttype);
            cost = (TextView) view.findViewById(R.id.cost);
            area = (TextView) view.findViewById(R.id.area);
            verified = (LinearLayout) view.findViewById(R.id.verified);
            notverified = (LinearLayout) view.findViewById(R.id.notverified);
            available = (LinearLayout) view.findViewById(R.id.available);
            notavailable = (LinearLayout) view.findViewById(R.id.notavailable);
            image1 = (ImageView) view.findViewById(R.id.image1);
            //image2 = (ImageView) view.findViewById(R.id.image2);
            //image3 = (ImageView) view.findViewById(R.id.image3);
            rupeeicon=(TextView)view.findViewById(R.id.rupeeicon);
            Typeface font = Typeface.createFromAsset(activity.getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
            rupeeicon.setTypeface(font);

        }
    }
    public Ad_Adapter(Activity activity, List<AD> adList){
        //this.mContext=mContext;
        this.adList=adList;
        this.activity=activity;
    }

}
