package peoplecitygroup.neuugen;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class UserMainActivity extends AppCompatActivity {

    Fragment fragment=null;
    FragmentTransaction ft;
    int flag;
    BottomNavigationView navigation;

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if(flag==0){
            if (doubleBackToExitPressedOnce) {
                Intent a = new Intent(Intent.ACTION_MAIN);
                a.addCategory(Intent.CATEGORY_HOME);
                a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(a);
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
        else{
            fragment=new HomeFrag();
            flag=0;
            navigation.setSelectedItemId(R.id.navigation_home);
            switchFragment();
            return;
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment=new HomeFrag();
                    flag=0;
                    switchFragment();
                    return true;
                case R.id.navigation_settings:
                    fragment=new SettingsFrag();
                    flag=2;
                    switchFragment();
                    return true;
                case R.id.navigation_bookings:
                    fragment=new BookingsFrag();
                    flag=1;
                    switchFragment();
                    return true;
                case R.id.navigation_account:
                    fragment=new ProfileFrag();
                    flag=3;
                    switchFragment();
                    return true;
            }
            return false;
        }
    };
    public void switchFragment()
    {
        ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content, fragment);
        ft.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main);

        fragment=new HomeFrag();
        flag=0;
        switchFragment();

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
