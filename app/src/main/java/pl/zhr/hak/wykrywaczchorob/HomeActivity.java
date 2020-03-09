package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import static pl.zhr.hak.wykrywaczchorob.LoginActivity.sharedPreferencesName;

public class HomeActivity extends AppCompatActivity {

    TextView textViewHello;
    TextView textViewUser;
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
        String name = extras.getString("name", "");

        textViewHello = findViewById(R.id.textViewHello);
        textViewUser = findViewById(R.id.textViewUser);
        imageButtonExit = findViewById(R.id.imageButtonExit);
        imageButtonAdd = findViewById(R.id.imageButtonAdd);
        imageButtonDB = findViewById(R.id.imageButtonDB);
        buttonLogout = findViewById(R.id.buttonLogout);

        textViewHello.setText(getString(R.string.hello));
        textViewUser.setText(getString(R.string.user, name));

        imageButtonExit.setOnClickListener(v -> finish());

        imageButtonAdd.setOnClickListener(v -> {
            Intent examination1Activity = new Intent(HomeActivity.this,
                    ExaminationActivity.class);
            startActivity(examination1Activity);
        });

        imageButtonDB.setOnClickListener(v -> {
            Intent patientActivity = new Intent(HomeActivity.this,
                    PatientsActivity.class);
            // flaga służąca do sygnalizowania potrzeby dodania pacjenta - tutaj brak potrzeby
            patientActivity.putExtra("flag", false);
            startActivity(patientActivity);
        });

        buttonLogout.setOnClickListener(v -> {
            Intent loginActivity = new Intent(HomeActivity.this,
                    LoginActivity.class);
            sharedPreferences.edit().putBoolean("remember",
                    false).apply();
            startActivity(loginActivity);
            finish();
        });
    }

}
