package pl.zhr.hak.wykrywaczchorob.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import pl.zhr.hak.wykrywaczchorob.R;

public class MainActivity extends AppCompatActivity {

    private final int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // delay the start of the next activity
        new Handler().postDelayed(this::activityReady, SPLASH_TIME_OUT);
    }

    public void activityReady(){
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
