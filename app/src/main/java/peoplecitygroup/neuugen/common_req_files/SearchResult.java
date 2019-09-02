package peoplecitygroup.neuugen.common_req_files;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.Html;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.R;

public class SearchResult {
    public void SearchAd(final String adtype, final String propertytype[],final String city[], final String bedrooms[],final String bathrooms[], final String furnishtype[],final String price[], final String constructionstatus[], final String possessionstatus[], final int resultshown,final int verified,final int available,final String number, final Context context, final VolleyCallback volleyCallback) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.searchAd, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.toLowerCase().contains("error")) {
                    volleyCallback.onError(response);
                } else {
                    volleyCallback.onSuccess(response);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                boolean haveConnectedWifi = false;
                boolean haveConnectedMobile = false;
                ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setMessage("No Internet Connection");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setMessage("Connection Error!");
                    alertDialog.setIcon(R.mipmap.ic_launcher_round);
                    alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    alertDialog.show();
                }
                volleyCallback.onVolleyError();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("adtype", adtype);
                params.put("propertytype",  new JSONArray(Arrays.asList(propertytype)).toString());
                params.put("city",  new JSONArray(Arrays.asList(city)).toString());
                params.put("bedrooms",  new JSONArray(Arrays.asList(bedrooms)).toString());
                params.put("bathrooms",  new JSONArray(Arrays.asList(bathrooms)).toString());
                params.put("furnishtype",  new JSONArray(Arrays.asList(furnishtype)).toString());
                params.put("price",  new JSONArray(Arrays.asList(price)).toString());
                params.put("constructionstatus",  new JSONArray(Arrays.asList(constructionstatus)).toString());
                params.put("possessionstatus", new JSONArray(Arrays.asList(possessionstatus)).toString());
                params.put("resultshown", String.valueOf(resultshown));
                params.put("verified",String.valueOf(verified));
                params.put("available",String.valueOf(available));
                params.put("number",number);
                Log.d("checksenddata","?adtype="+adtype+"&propertytype="+new JSONArray(Arrays.asList(propertytype)).toString()+"&city="+new JSONArray(Arrays.asList(city)).toString()+"&bedrooms="+new JSONArray(Arrays.asList(bedrooms)).toString()+"&bathrooms="+new JSONArray(Arrays.asList(bathrooms)).toString()+"&furnishtype="+new JSONArray(Arrays.asList(furnishtype)).toString()+"&price="+new JSONArray(Arrays.asList(price)).toString()+"&constructionstatus="+new JSONArray(Arrays.asList(constructionstatus)).toString()+"&possessionstatus="+new JSONArray(Arrays.asList(possessionstatus)).toString()+"&resultshown="+String.valueOf(resultshown)+"&verified=1&available=1");
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
                AlertDialog.Builder builder = new AlertDialog.Builder(context);

                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Connection")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish();
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

            }
        });
        MySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(stringRequest);
    }
}
