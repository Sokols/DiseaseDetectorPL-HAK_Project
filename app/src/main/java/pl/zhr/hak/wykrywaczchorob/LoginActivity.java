package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    int REQUEST_CODE = 123;
    EditText editTextName;
    EditText editTextPassword;
    CheckBox checkBoxRemember;
    Button buttonLogin;
    ImageButton imageButtonLanguage;
    ImageButton imageButtonExit1;
    TextView textViewRegister;

    SharedPreferences sharedPreferences;
    public static final String sharedPreferencesName = "data";

    UserViewModel userViewModel;
    User user;

    int userID;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // sprawdź poprawność ID rezultatu
        if (requestCode == REQUEST_CODE){
            // sprawdź poprawność wykonania
            if (resultCode == RESULT_OK) {
                // dodaj użytkownika do bazy danych zdalnie dodając ID z sharedpref
                String login = data.getStringExtra("login");
                String password = data.getStringExtra("password");
                userID = sharedPreferences.getInt("userID", 1);
                userViewModel.insert(new User(userID, login, password));
                sharedPreferences.edit().putInt("userID", userID + 1).apply();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        // zmiana języka na przeciwny
        if (sharedPreferences.getBoolean("language", false)) {
            setAppLocale("en");
        }
        else {
            setAppLocale("values");
        }

        setContentView(R.layout.activity_login);

        editTextName = findViewById(R.id.editTextName);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxRemember = findViewById(R.id.checkBoxRemember);
        buttonLogin = findViewById(R.id.buttonLogin);
        imageButtonLanguage = findViewById(R.id.imageButtonLanguage);
        imageButtonExit1 = findViewById(R.id.imageButtonExit1);
        textViewRegister = findViewById(R.id.textViewRegistration);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        // uruchom HomeActivity jeśli pierwotnie zaznaczono checkbox zapamiętania
        boolean rememberFlag = sharedPreferences.getBoolean("remember", false);
        String ownerName = sharedPreferences.getString("name", "");
        if (rememberFlag && !ownerName.isEmpty()) {
            homeReady(ownerName);
        }

        imageButtonExit1.setOnClickListener(v -> finish());

        buttonLogin.setOnClickListener(v -> {
            String name = editTextName.getText().toString();
            String password = editTextPassword.getText().toString();
            boolean remember = checkBoxRemember.isChecked();
            // sprawdź czy wszsystkie pola zostały wypełnione
            if(name.isEmpty() || password.isEmpty()) {
                Toast.makeText(LoginActivity.this,
                        getString(R.string.alldata),
                        Toast.LENGTH_SHORT).show();
            }
            else {
                user = userViewModel.getItemByName(name);
                // sprawdź czy podany login istnieje w bazie danych
                if (user.getLogin().equals(name)) {
                    // sprawdź czy dla podanego loginu wpisano odpowiednie hasło
                    if (user.getPassword().equals(password)) {
                        sharedPreferences.edit().putBoolean("remember", remember).apply();
                        sharedPreferences.edit().putString("name", name).apply();
                        homeReady(name);
                    }
                    else {
                        Toast.makeText(LoginActivity.this,
                                getString(R.string.wrong_password),
                                Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginActivity.this,
                            getString(R.string.wrong_login),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        textViewRegister.setOnClickListener(v -> {
            Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivityForResult(registerActivity, REQUEST_CODE);
        });

        imageButtonLanguage.setOnClickListener(v -> {
            sharedPreferences.edit().putBoolean("language",
                    !sharedPreferences.getBoolean("language", true)).apply();
            finish();
            startActivity(getIntent());
        });
    }

    // zmiana języka
    private void setAppLocale(String localeCode) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(localeCode.toLowerCase()));
        res.updateConfiguration(conf, dm);
    }

    public void homeReady(String name){
        Intent homeActivity = new Intent(LoginActivity.this,
                HomeActivity.class);
        homeActivity.putExtra("name", name);
        startActivity(homeActivity);
        finish();
    }
}
