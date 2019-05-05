package peoplecitygroup.neuugen.HomeServices.LearningServices;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.button.MaterialButton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import peoplecitygroup.neuugen.R;
import peoplecitygroup.neuugen.common_req_files.MySingleton;
import peoplecitygroup.neuugen.common_req_files.SendMail;
import peoplecitygroup.neuugen.common_req_files.SendMsg;
import peoplecitygroup.neuugen.common_req_files.ServiceCheck;
import peoplecitygroup.neuugen.common_req_files.UrlNeuugen;
import peoplecitygroup.neuugen.common_req_files.VolleyCallback;

public class LearningActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton actingbtn, dancingbtn, makeupbtn;

    AppCompatImageView lcimg1, lcimg2, lcimg3;

    AppCompatTextView dancingmsg, actingmsg, makeupmsg;

    String id = null;

    CardView actingcard, dancingcard, makeupcard;
    ProgressDialog loading = null;
    SharedPreferences sp;
    String[] ownId = new String[]{UrlNeuugen.learningServiceId, UrlNeuugen.dancingClassId, UrlNeuugen.groomingClassId, UrlNeuugen.actingClassId};
    String[] ownparentId = new String[]{"0", UrlNeuugen.learningServiceId, UrlNeuugen.learningServiceId, UrlNeuugen.learningServiceId};
    ArrayList<JSONObject> childclick = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_learning);

        idLink();
        listenerLink();
        loading = new ProgressDialog(LearningActivity.this, R.style.AppCompatAlertDialogStyle);
        loading.setCancelable(false);
        loading.setMessage("Sending Request...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        sp = getSharedPreferences("NeuuGen_data", MODE_PRIVATE);
        checkActive();

    }

    private void checkActive() {
        String city = sp.getString("city", null);
        loading.show();
        ServiceCheck serviceCheck = new ServiceCheck();
        serviceCheck.check(UrlNeuugen.learningServiceId, city, this, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                loading.dismiss();
                serviceDecode(result);
                Log.d("receivedmsg", result);
            }

            @Override
            public void onError(String response) {
                loading.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);
                if (response.equalsIgnoreCase("error")) {
                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Error in server. Try Again ")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish
                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                }
            }

            @Override
            public void onVolleyError() {
                loading.dismiss();
            }
        });
    }

    private void serviceDecode(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray serviceId = jsonObject.getJSONArray("serviceid");
            JSONArray parentserviceid = jsonObject.getJSONArray("parentserviceid");
            JSONArray servicename = jsonObject.getJSONArray("servicename");
            JSONArray status = jsonObject.getJSONArray("status");
            JSONArray cost = jsonObject.getJSONArray("cost");
            JSONArray pic1 = jsonObject.getJSONArray("pic1");
            JSONArray pic2 = jsonObject.getJSONArray("pic2");
            JSONArray pic3 = jsonObject.getJSONArray("pic3");
            JSONArray cityactive = jsonObject.getJSONArray("cityactive");
            int lengths[] = new int[]{serviceId.length(), parentserviceid.length(), servicename.length(), status.length(), cost.length(), pic1.length(), pic2.length(), pic3.length(), cityactive.length()};
            boolean flag = true;
            int L = serviceId.length();
            for (int l : lengths)
                if (L != l) {
                    flag = false;
                    return;
                }
            if (flag) {
                if (checkParentActive(serviceId.getString(0), parentserviceid.getString(0), servicename.getString(0), status.getString(0), cost.getString(0), pic1.getString(0), pic2.getString(0), pic3.getString(0), cityactive.getString(0)))
                    checkSubActive(serviceId, parentserviceid, servicename, status, cost, pic1, pic2, pic3, cityactive);
            } else {
                //ALERT
                AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);
                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Error in server. Try Again")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
            }
        } catch (JSONException e) {
            //ALERT DIALOG
            AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error in server. Try Again")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
            e.printStackTrace();
            Log.d("receivedmsg", e.toString());
        }

    }

    private void checkSubActive(JSONArray serviceId, JSONArray parentserviceid, JSONArray servicename, JSONArray status, JSONArray cost, JSONArray pic1, JSONArray pic2, JSONArray pic3, JSONArray cityactive) {
        try {
            for (int i = 1; i < ownId.length; i++) {
                int index = findIndex(serviceId, ownId[i]);
                if (index > 0) {
                    if (parentserviceid.getString(index).equalsIgnoreCase(ownparentId[i])) {
                        if (cityactive.getString(index).equalsIgnoreCase("1") && status.getString(index).equalsIgnoreCase("1")) {
                            changeService(ownId[i], true, serviceId.getString(index), parentserviceid.getString(index), servicename.getString(index), status.getString(index), cost.getString(index), pic1.getString(index), pic2.getString(index), pic3.getString(index), cityactive.getString(index));
                            JSONArray childserviceId = new JSONArray();
                            JSONArray childparentserviceid = new JSONArray();
                            JSONArray childservicename = new JSONArray();
                            JSONArray childstatus = new JSONArray();
                            JSONArray childcost = new JSONArray();
                            JSONArray childpic1 = new JSONArray();
                            JSONArray childpic2 = new JSONArray();
                            JSONArray childpic3 = new JSONArray();
                            JSONArray childcityactive = new JSONArray();
                            childserviceId.put(serviceId.getString(i));
                            childparentserviceid.put(parentserviceid.getString(i));
                            childservicename.put(servicename.getString(i));
                            childstatus.put(status.getString(i));
                            childcost.put(cost.getString(i));
                            childpic1.put(pic1.getString(i));
                            childpic2.put(pic2.getString(i));
                            childpic3.put(pic3.getString(i));
                            childcityactive.put(cityactive.getString(i));
                            for (int j = 1; j < parentserviceid.length(); j++) {
                                if (parentserviceid.getString(j).equalsIgnoreCase(ownId[i])) {
                                    childserviceId.put(serviceId.getString(j));
                                    childparentserviceid.put(parentserviceid.getString(j));
                                    childservicename.put(servicename.getString(j));
                                    childstatus.put(status.getString(j));
                                    childcost.put(cost.getString(j));
                                    childpic1.put(pic1.getString(j));
                                    childpic2.put(pic2.getString(j));
                                    childpic3.put(pic3.getString(j));
                                    childcityactive.put(cityactive.getString(j));
                                }
                            }
                            JSONObject temp = new JSONObject();
                            temp.put("serviceid", childserviceId);
                            temp.put("parentserviceid", childparentserviceid);
                            temp.put("servicename", childservicename);
                            temp.put("status", childstatus);
                            temp.put("cost", childcost);
                            temp.put("pic1", childpic1);
                            temp.put("pic2", childpic2);
                            temp.put("pic3", childpic3);
                            temp.put("cityactive", childcityactive);
                            childclick.add(temp);
                        } else
                            changeService(ownId[i], false, serviceId.getString(index), parentserviceid.getString(index), servicename.getString(index), status.getString(index), cost.getString(index), pic1.getString(index), pic2.getString(index), pic3.getString(index), cityactive.getString(index));
                    } else {
                        //SERVICE NOT FOUND
                        changeService(ownId[i], false, null, null, null, null, null, null, null, null, null);
                    }
                } else {
                    //SERVICE NOT FOUND
                    changeService(ownId[i], false, null, null, null, null, null, null, null, null, null);
                }
            }
            if (serviceId.length() > ownId.length) {
            }
        } catch (Exception e) {
            //ALERT
            AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error in server. Try Again")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
    }

    public void changeService(String ownid, boolean flag, String serviceid, String parentserviceid, String servicename, String status, String cost, String pic1, String pic2, String pic3, String cityactive) {
        String c = ownid.trim();
        if (c == UrlNeuugen.actingClassId.trim()) {
            if (flag) {
                if (cost != null && cost.trim() != "" && !cost.equalsIgnoreCase("null")) {
                    actingmsg.setVisibility(View.VISIBLE);
                    actingmsg.setText(cost.trim());
                }
            } else {
                if (cityactive.equalsIgnoreCase("0"))
                    actingmsg.setText("Service not available in this City. Will Come Soon!");
                else
                    actingmsg.setText("Service is currently unavailable");
                actingmsg.setVisibility(View.VISIBLE);
                actingcard.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                actingcard.setClickable(false);
            }
        }
        if (c == UrlNeuugen.groomingClassId.trim()) {
            if (flag) {
                if (cost != null && cost.trim() != "" && !cost.equalsIgnoreCase("null")) {
                    makeupmsg.setVisibility(View.VISIBLE);
                    makeupmsg.setText(cost.trim());
                }
            } else {
                if (cityactive.equalsIgnoreCase("0"))
                    makeupmsg.setText("Service not available in this City. Will Come Soon!");
                else
                    makeupmsg.setText("Service is currently unavailable");
                makeupmsg.setVisibility(View.VISIBLE);
                makeupcard.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                makeupcard.setClickable(false);
            }
        }
        if (c == UrlNeuugen.dancingClassId.trim()) {
            if (flag) {
                if (cost != null && cost.trim() != "" && !cost.equalsIgnoreCase("null")) {
                    dancingmsg.setVisibility(View.VISIBLE);
                    dancingmsg.setText(cost.trim());
                }
            } else {
                if (cityactive.equalsIgnoreCase("0"))
                    dancingmsg.setText("Service not available in this City. Will Come Soon!");
                else
                    dancingmsg.setText("Service is currently unavailable");
                dancingmsg.setVisibility(View.VISIBLE);
                dancingcard.setCardBackgroundColor(Color.parseColor("#FFE0E0E0"));
                dancingcard.setClickable(false);
            }
        }
    }

    private int findIndex(JSONArray serviceId, String s) throws JSONException {
        int index = -1;
        for (int i = 1; i < serviceId.length(); i++)
            if (serviceId.getString(i).trim().equalsIgnoreCase(s))
                index = i;
        return index;
    }


    private boolean checkParentActive(String serviceid, String parentserviceid, String servicename, String status, String cost, String pic1, String pic2, String pic3, String cityactive) {
        if (serviceid.equalsIgnoreCase(ownId[0])) {
            pic1 = pic1.trim();
            pic2 = pic2.trim();
            pic3 = pic3.trim();
            if (pic1 != null && pic1 != "") {
                Picasso.with(this).load(pic1).fit().centerCrop()
                        .placeholder(R.drawable.imgplaceholder)
                        .error(R.drawable.imgplaceholder)
                        .into(lcimg1);
            }
            if (pic2 != null && pic2 != "") {
                Picasso.with(this).load(pic2).fit().centerCrop()
                        .placeholder(R.drawable.imgplaceholder)
                        .error(R.drawable.imgplaceholder)
                        .into(lcimg2);
            }
            if (pic3 != null && pic3 != "") {
                Picasso.with(this).load(pic3).fit().centerCrop()
                        .placeholder(R.drawable.imgplaceholder)
                        .error(R.drawable.imgplaceholder)
                        .into(lcimg3);
            }
            rotateImg();
            if (status.trim().equalsIgnoreCase("1")) {
                if (cityactive.trim().equalsIgnoreCase("1")) {
                    //other task with name and cost
                    return true;
                } else {
                    //ALERT
                    AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);

                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Service not available in this city. Will come Soon!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //finish
                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                }
            } else {
                //ALERT
                AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);

                builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                builder.setMessage("Service is not available")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //finish
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher_round);
                AlertDialog dialog = builder.create();
                dialog.show();
                Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
            }
        } else {
            //ALERT
            AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Error in server. Try Again")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
        return false;
    }

    private void rotateImg() {
    }


    private void idLink() {
        actingbtn = findViewById(R.id.actingbtn);
        dancingbtn = findViewById(R.id.dancingbtn);
        makeupbtn = findViewById(R.id.makeupbtn);
        lcimg1 = findViewById(R.id.lcimg1);
        lcimg2 = findViewById(R.id.lcimg2);
        lcimg3 = findViewById(R.id.lcimg3);
        actingmsg = findViewById(R.id.actingmsg);
        makeupmsg = findViewById(R.id.makeupmsg);
        dancingmsg = findViewById(R.id.dancingmsg);
        actingcard = findViewById(R.id.actingcard);
        dancingcard = findViewById(R.id.dancingcard);
        makeupcard = findViewById(R.id.makeupcard);
    }

    private void listenerLink() {
        actingbtn.setOnClickListener(this);
        dancingbtn.setOnClickListener(this);
        makeupbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.dancingbtn || v.getId() == R.id.makeupbtn || v.getId() == R.id.actingbtn) {

            if (v.getId() == R.id.dancingbtn)
                id = UrlNeuugen.dancingClassId;
            if (v.getId() == R.id.makeupbtn)
                id = UrlNeuugen.groomingClassId;
            if (v.getId() == R.id.actingbtn)
                id = UrlNeuugen.actingClassId;

            AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);
            builder.setIcon(R.mipmap.ic_launcher_round);
            builder.setTitle(Html.fromHtml("<font color='#FF0000'>neuugen</font>"));
            builder.setMessage("Are you sure you want to request for this service?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    sendData(id);
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
    }

    private void sendData(final String id) {
        loading.show();
        SharedPreferences sp;
        sp = getSharedPreferences("NeuuGen_data", MODE_PRIVATE);
        if (sp != null) {
            final String email = sp.getString("email", null);
            final String name = sp.getString("name", null).toUpperCase();
            final String mobileno = sp.getString("mobileno", null).toUpperCase();
            final String city = sp.getString("city", null);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlNeuugen.requestservice_Learning, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    loading.dismiss();
                    if (response.toLowerCase().contains("error")) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);
                        builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        builder.setMessage("Error in server. Try Again")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })
                                .setIcon(R.mipmap.ic_launcher_round);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                    } else {
                        if (response.toLowerCase().equalsIgnoreCase("success")) {
                            //SUCCEESS
                            sendMail(email, name);
                            sendSMS(mobileno, name);
                            AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);
                            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                            builder.setMessage("Service Requested. Our Agent will contact you soon regarding same.")
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            finish();
                                        }
                                    })
                                    .setIcon(R.mipmap.ic_launcher_round);
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
                        }
                        else{
                            AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);
                            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                            builder.setMessage(response)
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
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    loading.dismiss();
                    boolean haveConnectedWifi = false;
                    boolean haveConnectedMobile = false;
                    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
                        AlertDialog alertDialog = new AlertDialog.Builder(LearningActivity.this).create();
                        alertDialog.setMessage("No Internet Connection");
                        alertDialog.setIcon(R.mipmap.ic_launcher_round);
                        alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                        alertDialog.show();
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(LearningActivity.this).create();
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
                    params.put("mobileno", mobileno);
                    params.put("serviceid", id);
                    params.put("city", city);
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);

                    builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
                    builder.setMessage("Connection")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setIcon(R.mipmap.ic_launcher_round);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));

                }
            });
            MySingleton.getInstance(LearningActivity.this).addToRequestQueue(stringRequest);
        }
        else {
            loading.dismiss();
            AlertDialog.Builder builder = new AlertDialog.Builder(LearningActivity.this);

            builder.setTitle(Html.fromHtml("<font color='#FF0000'>Neuugen</font>"));
            builder.setMessage("Internal Error")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setIcon(R.mipmap.ic_launcher_round);
            AlertDialog dialog = builder.create();
            dialog.show();
            Button positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            positiveButton.setTextColor(Color.parseColor("#FF12B2FA"));
        }
    }
    private void sendSMS(String mobileno,String name) {
        SendMsg sendMsg=new SendMsg();
        String msg="Dear "+name.trim()+", your learning service has been requested. Our Customer Executive will contact you soon.";
        sendMsg.SendCustomMsg(mobileno, msg, this, new VolleyCallback() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onError(String response) {

            }

            @Override
            public void onVolleyError() {

            }
        });
    }

    private void sendMail(String email,String name) {
        SendMail sendMail=new SendMail();
        String subject="NG: Learning Service Request";
        String body="Dear "+name+",<br><p>Thanks for choosing NEUUGEN. Your Service has been requested.</p><br>Our Customer Executive/Agent will reach out to you for the same. The Service Requested will be provided soon.";
        sendMail.sendMailmethod(email, subject, body, this, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {

            }

            @Override
            public void onError(String response) {

            }

            @Override
            public void onVolleyError() {
                loading.dismiss();
            }
        });
    }
}
