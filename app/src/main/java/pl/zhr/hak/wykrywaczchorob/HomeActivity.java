package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static pl.zhr.hak.wykrywaczchorob.MainActivity.sharedPreferencesName;

public class HomeActivity extends AppCompatActivity {

    TextView textViewHello;
    Button buttonLogout;
    SharedPreferences sharedPreferences;
    ImageButton imageButtonExit;
    ImageButton imageButtonAdd;
    ImageButton imageButtonDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("name");
        textViewHello = findViewById(R.id.textViewHello);
        imageButtonExit = findViewById(R.id.imageButtonExit);
        imageButtonAdd = findViewById(R.id.imageButtonAdd);
        imageButtonDB = findViewById(R.id.imageButtonDB);
        buttonLogout = findViewById(R.id.buttonLogout);
        textViewHello.setText(getString(R.string.hello, name));

        imageButtonExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent examination1Activity = new Intent(HomeActivity.this,
                        Examination1Activity.class);
                startActivity(examination1Activity);
            }
        });

        imageButtonDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent patientActivity = new Intent(HomeActivity.this,
                        PatientsActivity.class);
                patientActivity.putExtra("flag", false);
                startActivity(patientActivity);
            }
        });

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
