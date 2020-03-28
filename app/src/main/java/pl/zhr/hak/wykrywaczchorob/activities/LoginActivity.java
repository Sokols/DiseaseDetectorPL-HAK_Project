package pl.zhr.hak.wykrywaczchorob.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.zhr.hak.wykrywaczchorob.R;
import pl.zhr.hak.wykrywaczchorob.User;
import pl.zhr.hak.wykrywaczchorob.UserViewModel;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editTextName) EditText editTextName;
    @BindView(R.id.editTextPassword) EditText editTextPassword;
    @BindView(R.id.checkBoxRemember) CheckBox checkBoxRemember;
    @BindView(R.id.textViewRegistration) TextView textViewRegistration;

    int REQUEST_CODE = 123;
    int userID;
    String LANGUAGE_CODE;
    UserViewModel userViewModel;
    User user;
    SharedPreferences sharedPreferences;
    public static final String sharedPreferencesName = "data";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ButterKnife.bind(this);
        // dodaj nowego użytkownika do bazy jeśli wszystkie warunki zostały spełnione
        addUser(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);
        setLanguage();
        ButterKnife.bind(this);

        // podkreśl przycisk/tekst przejścia do rejestracji
        textViewRegistration.setPaintFlags(textViewRegistration.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // uruchom HomeActivity jeśli pierwotnie zaznaczono checkbox zapamiętania
        boolean rememberFlag = sharedPreferences.getBoolean("remember", false);
        if (rememberFlag) {
            homeReady();
        }
    }

    // wyjdź z aplikacji
    @OnClick(R.id.imageButtonExit1)
    public void imageButtonExit1() {
        dialogConfirmExit().show();
    }

    // zaloguj się
    @OnClick(R.id.buttonLogin)
    public void setButtonLogin() {
        String login = editTextName.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean remember = checkBoxRemember.isChecked();

        // sprawdź czy wszsystkie pola zostały wypełnione
        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.alldata), Toast.LENGTH_SHORT).show();
        }
        else {
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
            user = userViewModel.getItemByName(login);
            // sprawdź czy podany login istnieje w bazie danych
            if (userViewModel.checkItemByName(login) == 1) {
                // sprawdź czy dla podanego loginu wpisano odpowiednie hasło
                if (user.getPassword().equals(password)) {
                    sharedPreferences.edit().putBoolean("remember", remember).apply();
                    sharedPreferences.edit().putString("name", login).apply();
                    homeReady();
                }
                else {
                    Toast.makeText(LoginActivity.this, getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(LoginActivity.this, getString(R.string.wrong_login), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // przejdź do rejestracji
    @OnClick(R.id.textViewRegistration)
    public void setTextViewRegistration() {
        Intent registerActivity = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivityForResult(registerActivity, REQUEST_CODE);
    }

    // zmień język aplikacji i odśwież
    @OnClick(R.id.imageButtonLanguage)
    public void setImageButtonLanguage() {
        if (LANGUAGE_CODE.equals("values")) {
            LANGUAGE_CODE = "en";
        }
        else {
            LANGUAGE_CODE = "values";
        }
        finish();
        sharedPreferences.edit().putString("language", LANGUAGE_CODE).apply();
        startActivity(getIntent());
    }

    // ustaw język aplikacji
    public void setLanguage() {
        LANGUAGE_CODE = sharedPreferences.getString("language", "values");
        setAppLocale(LANGUAGE_CODE);
        setContentView(R.layout.activity_login);
    }

    // zmiana języka
    private void setAppLocale(String localeCode) {
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(localeCode.toLowerCase()));
        res.updateConfiguration(conf, dm);
    }

    // uruchomienie nowej aktywności
    public void homeReady(){
        Intent homeActivity = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(homeActivity);
        finish();
    }

    // okno dialogowe potwierdzające wyjście z aplikacji
    private Dialog dialogConfirmExit() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getString(R.string.confirm));
        dialogBuilder.setMessage(getString(R.string.please_confirm));
        dialogBuilder.setPositiveButton(getString(R.string.yes),
                (dialog, whichButton) ->
                        finish());
        dialogBuilder.setNegativeButton(getString(R.string.no),
                (dialog, whichButton) ->
                { /* nic nie rób */ });
        return dialogBuilder.create();
    }

    // dodawanie użytkownika do bazy
    public void addUser(int requestCode, int resultCode, Intent data) {
        // sprawdź poprawność ID rezultatu
        if (requestCode == REQUEST_CODE) {
            // sprawdź poprawność wykonania
            if (resultCode == RESULT_OK) {
                // dodaj użytkownika do bazy danych zdalnie dodając ID z sharedpref
                Toast.makeText(this, getString(R.string.registration_confirm), Toast.LENGTH_SHORT).show();
                String login = data.getStringExtra("login");
                String password = data.getStringExtra("password");
                userID = sharedPreferences.getInt("userID", 1);
                userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
                userViewModel.insert(new User(userID, login, password));
                sharedPreferences.edit().putInt("userID", userID + 1).apply();
            }
        }
    }
}