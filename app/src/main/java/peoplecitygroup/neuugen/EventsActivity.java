package peoplecitygroup.neuugen;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.google.android.material.button.MaterialButton;

import static android.os.Build.VERSION_CODES.JELLY_BEAN;

public class EventsActivity extends AppCompatActivity implements View.OnClickListener {

    MaterialButton photoproceedbtn,eventproceedbtn;
    RadioGroup shootoptions,eventarrangements;
    AppCompatRadioButton prewedshoot,wedshoot,eventshoot,dancers,singers,anchors,bands;
    AppCompatTextView cleanicon,trainericon,timericon,eventmsg,photomsg;
    LinearLayout eventlayout;

    AppCompatImageView esimg1,esimg2,esimg3;
    String servicetypetext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        idLink();
        listenerLink();
        Typeface font = Typeface.createFromAsset(getAssets(), "Font Awesome 5 Free-Solid-900.otf" );
        trainericon.setTypeface(font);
        timericon.setTypeface(font);
        cleanicon.setTypeface(font);


    }

    public void idLink()
    {
        eventlayout=findViewById(R.id.eventlayout);
        timericon=findViewById(R.id.timericon);
        trainericon=findViewById(R.id.trainericon);
        cleanicon=findViewById(R.id.cleanicon);
        eventarrangements=findViewById(R.id.eventarrangements);
        shootoptions=findViewById(R.id.shootoptions);
        prewedshoot=findViewById(R.id.prewedshoot);
        wedshoot=findViewById(R.id.wedshoot);
        eventshoot=findViewById(R.id.eventshoot);
        dancers=findViewById(R.id.dancers);
        singers=findViewById(R.id.singers);
        bands=findViewById(R.id.bands);
        anchors=findViewById(R.id.anchors);
        eventproceedbtn=findViewById(R.id.eventproceedbtn);
        photoproceedbtn=findViewById(R.id.photoproceedbtn);
        esimg1=findViewById(R.id.esimg1);
        esimg2=findViewById(R.id.esimg2);
        esimg3=findViewById(R.id.esimg3);
        eventmsg=findViewById(R.id.eventmsg);
        photomsg=findViewById(R.id.photomsg);


    }

    public void listenerLink()
    {
        eventproceedbtn.setOnClickListener(this);
        photoproceedbtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId()==R.id.photoproceedbtn)
        {
            if (!prewedshoot.isChecked()&&!wedshoot.isChecked()&&!eventshoot.isChecked())
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage("Select atleast one option");
                alertDialog.setIcon(R.mipmap.ic_launcher_round);
                alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                alertDialog.show();

                shootoptions.requestFocus();
            }else
            {
                if (prewedshoot.isChecked())
                {
                    servicetypetext="Pre-Wedding Shoot";
                    Intent intent = new Intent(EventsActivity.this, PreWedShootForm.class);
                    intent.putExtra("servicetype",servicetypetext);
                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(EventsActivity.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }else
                    if (wedshoot.isChecked())
                {
                    servicetypetext="Wedding Shoot";
                    Intent intent = new Intent(EventsActivity.this, WeddingPackages.class);
                    intent.putExtra("servicetype",servicetypetext);
                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(EventsActivity.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }else
                    if (eventshoot.isChecked())
                {
                    servicetypetext="Event Photography";
                    Intent intent = new Intent(EventsActivity.this, EventPhotographyForm.class);
                    intent.putExtra("servicetype",servicetypetext);
                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(EventsActivity.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }

            }
        }
        if (v.getId()==R.id.eventproceedbtn)
        {
            if (!dancers.isChecked()&&!anchors.isChecked()&&!singers.isChecked()&&!bands.isChecked())
            {
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setMessage("Select atleast one option");
                alertDialog.setIcon(R.mipmap.ic_launcher_round);
                alertDialog.setTitle(Html.fromHtml("<font color='#FF0000'>NeuuGen</font>"));
                alertDialog.show();

                eventarrangements.requestFocus();
            }else
            {
                if (dancers.isChecked())
                {
                    servicetypetext="Dance Performers";
                    Intent intent = new Intent(EventsActivity.this, EventArrangementForm.class);
                    intent.putExtra("servicetype",servicetypetext);
                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(EventsActivity.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }else
                if (anchors.isChecked())
                {
                    servicetypetext="Anchors OR Hosts";
                    Intent intent = new Intent(EventsActivity.this, EventArrangementForm.class);
                    intent.putExtra("servicetype",servicetypetext);
                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(EventsActivity.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }else
                if (singers.isChecked())
                {
                    servicetypetext="Singers";
                    Intent intent = new Intent(EventsActivity.this, EventArrangementForm.class);
                    intent.putExtra("servicetype",servicetypetext);
                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(EventsActivity.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }
                if (bands.isChecked())
                {
                    servicetypetext="Bands OR Musicians";
                    Intent intent = new Intent(EventsActivity.this, EventArrangementForm.class);
                    intent.putExtra("servicetype",servicetypetext);
                    if (android.os.Build.VERSION.SDK_INT >= JELLY_BEAN) {
                        ActivityOptions options = ActivityOptions.makeCustomAnimation(EventsActivity.this, R.anim.fade_in, R.anim.fade_out);
                        startActivity(intent, options.toBundle());
                    } else {
                        startActivity(intent);
                    }
                }

            }

        }

    }
}
