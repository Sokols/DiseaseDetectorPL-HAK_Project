package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static pl.zhr.hak.wykrywaczchorob.MainActivity.sharedPreferencesName;

public class HomeActivity extends AppCompatActivity {

    TextView textViewHello;
    Button buttonLogout;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        textViewHello = findViewById(R.id.textViewHello);
        textViewHello.setText(getString(R.string.hello, name));

        buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent mainActivity = new Intent(HomeActivity.this,
                        MainActivity.class);
                sharedPreferences.edit().putBoolean("remember",
                        false).apply();
                startActivity(mainActivity);
                finish();
            }
        });
    }

}
