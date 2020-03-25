package peoplecitygroup.neuugen.Adapters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.BookingsFrag;
import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.Bookings;
import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;
import static com.google.android.gms.common.internal.safeparcel.SafeParcelable.NULL;

public class Bookings_Adapter extends RecyclerView.Adapter<Bookings_Adapter.MyViewHolder> {
     ArrayList<Bookings> bookingsList,otherlist;
     Activity activity;
    ProgressDialog loading = null;
    BookingsFrag fragment;
    String area=null;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView= activity.getLayoutInflater().inflate(R.layout.bookings_cardview,null);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        final Bookings bookings = bookingsList.get(position);
        if ((bookings.getArea().trim()=="null")){
            area="";
        }
        else
        {
            area=bookings.getArea().trim()+", ";
        }
        holder.servicename.setText(UrlNeuugen.serviceName[Integer.parseInt(bookings.getServiceid())]);
        holder.area.setText(area+bookings.getCity().trim());
        if(bookings.getDateofservice().equalsIgnoreCase("null")||bookings.getDateofservice()==null)
            holder.dateofservice.setText("Not Applicable");
        else
        holder.dateofservice.setText(new SimpleDateFormat("MM/dd/yyyy").format(new Date(Long.parseLong(bookings.getDateofservice()))));
        String temp="-";
        switch (bookings.getStatus().trim()){
            case "0":temp="Pending";break;
            case "1":temp="Confirmed";break;
            case "2":temp="Cancelled";break;
            case "3":temp="Completed";break;
        }
        holder.status.setText(temp);
        if(bookings.getStatus().trim().equalsIgnoreCase("3")||bookings.getStatus().trim().equalsIgnoreCase("2"))
            holder.cancelrequest.setVisibility(View.GONE);
        holder.cancelrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DELETEBTN
                AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                alertDialog.setMessage("Are you sure you want to Cancel Request?");
                alertDialog.setButton(BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        cancelRequest(holder.cancelrequest,bookings,position,holder);
                    }
                });
                alertDialog.setButton(BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.setIcon(R.mipmap.ic_launcher_round);
                alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                alertDialog.show();
                Button positiveButton = alertDialog.getButton(BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                Button negativeButton = alertDialog.getButton(BUTTON_NEGATIVE);
                negativeButton.setTextColor(Color.parseColor("#FF12B2FA"));
            }
        });
    }

    public void cancelRequest(final MaterialButton cancelrequest, final Bookings bookings, final int position, final MyViewHolder holder) {
        //CANCEL BOOKING
        loading.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.cancelBooking, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.dismiss();
                if (response.toLowerCase().contains("error")) {


                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setMessage(response);
                    alertDialog.setButton(BUTTON_POSITIVE,"Ok", (DialogInterface.OnClickListener) null);
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                    Button positiveButton = alertDialog.getButton(BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

                } else {

                    if(response.toLowerCase().trim().contains("success")){
                        bookingsList.remove(position);
                        bookings.setStatus("2");
                        otherlist.add(bookings);
                        fragment.showListView(bookingsList,otherlist);
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                boolean haveConnectedWifi = false;
                boolean haveConnectedMobile = false;
                ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo[] netInfo = cm.getAllNetworkInfo();
                for (NetworkInfo ni : netInfo) {
                    if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                        if (ni.isConnected())
                            haveConnectedWifi = true;
                    if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                        if (ni.isConnected())
                            haveConnectedMobile = true;
                }
                if (!haveConnectedWifi && !haveConnectedMobile) {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
                    alertDialog.setMessage("Connection Error!");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("requestid", bookings.getRequestid());
                return params;
            }
        };
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 50000;
            }
            @Override
            public int getCurrentRetryCount() {
                return 50000;
            }
            @Override
            public void retry(VolleyError error) throws VolleyError {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Connection")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                activity.finish();
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = dialog.getButton(BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
            }
        });
        MySingleton.getInstance(activity).addToRequestQueue(stringRequest);
    }

    @Override
    public int getItemCount() {
        return bookingsList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {


        public TextView servicename,area,dateofservice,status;
        public MaterialButton cancelrequest;
        public MyViewHolder(View view) {
            super(view);
            loading = new ProgressDialog(activity,R.style.AppCompatAlertDialogStyle);
            loading.setCancelable(false);
            loading.setMessage("Loading...");
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            servicename = (TextView) view.findViewById(R.id.servicename);
            dateofservice = (TextView) view.findViewById(R.id.dateofservice);
            area = (TextView) view.findViewById(R.id.areabooking);
            status = (TextView) view.findViewById(R.id.status);
            cancelrequest=(MaterialButton) view.findViewById(R.id.cancelrequest);
        }
    }
    public Bookings_Adapter(Activity activity, ArrayList<Bookings> bookingsList,ArrayList<Bookings> otherlist,BookingsFrag fragment){
        this.fragment=fragment;
        this.bookingsList=bookingsList;
        this.otherlist=otherlist;
        this.activity=activity;
    }

}
