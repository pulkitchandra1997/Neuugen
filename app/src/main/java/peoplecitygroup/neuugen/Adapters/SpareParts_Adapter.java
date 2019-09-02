package peoplecitygroup.neuugen.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import peoplecitygroup.neuugen.HomeServices.SparePartsActivity;
import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.SparePart;

public class SpareParts_Adapter extends RecyclerView.Adapter<SpareParts_Adapter.MyViewHolder> {
    ArrayList<SparePart> spareParts;
    Activity activity;

    SparePartsActivity sparePartsActivity;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= activity.getLayoutInflater().inflate(R.layout.customsparecardist,null);

        Log.d("checkview","yes");
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final SparePart sparePart = spareParts.get(position);
        if(sparePart.getPic()!=null&&sparePart.getPic().trim()!="") {

            Picasso.with(activity).load(sparePart.getPic())
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.placeholder)
                    .into(holder.placeimage);
            holder.placeimage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sparePartsActivity.zoomImageFromThumb(holder.placeimage,sparePart.getPic(),true);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return spareParts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView placeimage;
        public CardView cardView;
        public FrameLayout container;
        ImageView enlargeImage;
        public MyViewHolder(View view) {
            super(view);

            placeimage = (ImageView) view.findViewById(R.id.placeimage);
            cardView=(CardView)view.findViewById(R.id.customcardspare);
            //enlargeImage=(ImageView)view.findViewById(R.id.expanded_image);
            container=(FrameLayout)view.findViewById(R.id.container);
        }
    }
    public SpareParts_Adapter(Activity activity, ArrayList<SparePart> spareParts, SparePartsActivity sparePartsActivity){
        this.spareParts=spareParts;
        this.activity=activity;
        this.sparePartsActivity=sparePartsActivity;
    }

}