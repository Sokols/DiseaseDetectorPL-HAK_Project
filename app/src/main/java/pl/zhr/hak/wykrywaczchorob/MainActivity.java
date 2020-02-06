package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextPassword;
    CheckBox checkBoxRemember;
    Button buttonLogin;

    SharedPreferences sharedPreferences;
    public static final String sharedPreferencesName = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);
        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);
        buttonLogin = findViewById(R.id.buttonLogin);

        boolean rememberFlag = sharedPreferences.getBoolean("remember", false);
        String ownerName = sharedPreferences.getString("name", "");
        if (rememberFlag && !ownerName.isEmpty()) {
            homeReady(ownerName);
        }

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String password = editTextPassword.getText().toString();
                boolean remember = checkBoxRemember.isChecked();
                if(name.isEmpty() || password.isEmpty())
                    Toast.makeText(MainActivity.this,
                            getString(R.string.alldata),
                            Toast.LENGTH_SHORT).show();
                else {
                    sharedPreferences.edit().putBoolean("remember",
                            remember).apply();
                    sharedPreferences.edit().putString("name",
                            name).apply();
                    homeReady(name);
                }
            }
        });
    }
    public void homeReady(String name){
        Intent homeActivity = new Intent(MainActivity.this,
                HomeActivity.class);
        homeActivity.putExtra("name", name);
        startActivity(homeActivity);
        finish();
    }
}
