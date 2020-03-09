package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    public static final String sharedPreferencesName = "data";
    private static final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // opóźnij uruchomienie kolejnej aktywności
        new Handler().postDelayed(this::activityReady, SPLASH_TIME_OUT);
    }

    public void activityReady(){
        Intent activity = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(activity);
        finish();
    }
}
