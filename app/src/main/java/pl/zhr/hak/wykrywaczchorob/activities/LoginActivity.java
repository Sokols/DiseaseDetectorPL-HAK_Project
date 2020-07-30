package pl.zhr.hak.wykrywaczchorob.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.zhr.hak.wykrywaczchorob.R;
import pl.zhr.hak.wykrywaczchorob.UserViewModel;
import pl.zhr.hak.wykrywaczchorob.viewModels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.checkBoxRemember)
    CheckBox checkBoxRemember;
    @BindView(R.id.textViewRegistration)
    TextView textViewRegistration;

    public static final int REQUEST_CODE = 123;
    public static final String sharedPreferencesName = "data";
    public static String LANGUAGE_CODE;

    private LoginViewModel loginViewModel;
    private UserViewModel userViewModel;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginViewModel.addUser(this, userViewModel, requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);
        loginViewModel.setSharedPreferences(sharedPreferences);
        loginViewModel.setLanguage(this);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // underline button / text to go to registration
        textViewRegistration.setPaintFlags(textViewRegistration.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // run HomeActivity if the save checkbox was originally selected
        boolean rememberFlag = sharedPreferences.getBoolean("remember", false);
        if (rememberFlag) {
            loginViewModel.homeReady(this);
        }
    }

    @OnClick(R.id.imageButtonExit1)
    public void imageButtonExit1() {
        loginViewModel.dialogConfirmExit(this).show();
    }

    @OnClick(R.id.buttonLogin)
    public void setButtonLogin() {
        String login = editTextName.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean remember = checkBoxRemember.isChecked();
        loginViewModel.checkLogin(this, userViewModel, login, password, remember);
    }

    @OnClick(R.id.textViewRegistration)
    public void setTextViewRegistration() {
        startActivityForResult(new Intent(LoginActivity.this, RegisterActivity.class), REQUEST_CODE);
    }

    // change the language of the application and refresh
    @OnClick(R.id.imageButtonLanguage)
    public void setImageButtonLanguage() {
        if (LANGUAGE_CODE.equals("values")) {
            LANGUAGE_CODE = "en";
        } else {
            LANGUAGE_CODE = "values";
        }
        finish();
        sharedPreferences.edit().putString("language", LANGUAGE_CODE).apply();
        startActivity(getIntent());
    }
}