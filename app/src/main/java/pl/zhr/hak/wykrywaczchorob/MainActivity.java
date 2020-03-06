package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String sharedPreferencesName = "data";
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);
        new Handler().postDelayed(() -> {
            // uruchom HomeActivity jeśli pierwotnie zaznaczono checkbox zapamiętania
            boolean rememberFlag = sharedPreferences.getBoolean("remember", false);
            if (rememberFlag) {
                activityReady(HomeActivity.class);
            }
            // w przeciwnym wypadku uruchom LoginActivity
            else {
                activityReady(LoginActivity.class);
            }
        }, SPLASH_TIME_OUT);
    }

    public void activityReady(Class activityToOpen){
        String ownerName = sharedPreferences.getString("name", "");
        Intent activity = new Intent(MainActivity.this,
                activityToOpen);
        activity.putExtra("name", ownerName);
        startActivity(activity);
        finish();
    }
}
