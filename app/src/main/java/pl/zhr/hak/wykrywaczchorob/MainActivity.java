package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    EditText editTextName;
    EditText editTextPassword;
    CheckBox checkBoxRemember;
    Button buttonLogin;
    ImageButton imageButtonLanguage;

    SharedPreferences sharedPreferences;
    public static final String sharedPreferencesName = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);
        if (sharedPreferences.getBoolean("language", false))
            setAppLocale("en");
        else
            setAppLocale("values");
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);
        buttonLogin = findViewById(R.id.buttonLogin);
        imageButtonLanguage = findViewById(R.id.imageButtonLanguage);

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

        imageButtonLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferences.edit().putBoolean("language",
                        !sharedPreferences.getBoolean("language", true)).apply();
                finish();
                startActivity(getIntent());
            }
        });
    }

    private void setAppLocale(String localeCode) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            conf.setLocale(new Locale(localeCode.toLowerCase()));
        else
            conf.locale = new Locale(localeCode.toLowerCase());
        res.updateConfiguration(conf, dm);
    }

    public void homeReady(String name){
        Intent homeActivity = new Intent(MainActivity.this,
                HomeActivity.class);
        homeActivity.putExtra("name", name);
        startActivity(homeActivity);
        finish();
    }
}
