package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText editTextLogin;
    EditText editTextPassword;
    EditText editTextPassword2;
    Button buttonRegister;
    UserViewModel userViewModel;
    User user;
    TextView textViewLoginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextLogin = findViewById(R.id.editTextRegisterName);
        editTextPassword = findViewById(R.id.editTextRegisterPassword);
        editTextPassword2 = findViewById(R.id.editTextRegisterPassword2);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLoginNow = findViewById(R.id.textViewLoginNow);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        buttonRegister.setOnClickListener(v -> {
            String login = editTextLogin.getText().toString();
            String password = editTextPassword.getText().toString();
            String password2 = editTextPassword2.getText().toString();
            user = userViewModel.getItemByName(login);
            // sprawdź czy uzupełniono wszystkie pola
            if (login.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                Toast.makeText(this, getString(R.string.alldata), Toast.LENGTH_SHORT).show();
            }
            else {
                // sprawdź czy podany login istnieje w bazie
                if (user.getLogin().equals(login)){
                    Toast.makeText(this, getString(R.string.double_login), Toast.LENGTH_SHORT).show();
                }
                else {
                    // sprawdź poprawność hasła
                    if (password != password2) {
                        Toast.makeText(this, getString(R.string.wrong_password_repeat), Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Intent mIntent = new Intent();
                        mIntent.putExtra("login", login);
                        mIntent.putExtra("password", password);
                        setResult(RESULT_OK, mIntent);
                        finish();
                    }
                }
            }
        });

        textViewLoginNow.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });
    }
}
