package pl.zhr.hak.wykrywaczchorob.activities;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import pl.zhr.hak.wykrywaczchorob.R;
import pl.zhr.hak.wykrywaczchorob.User;
import pl.zhr.hak.wykrywaczchorob.UserViewModel;

public class RegisterActivity extends AppCompatActivity {

    @BindViews({R.id.editTextRegisterName, R.id.editTextRegisterPassword, R.id.editTextRegisterPassword2})
        List<EditText> editTexts;
    @BindView(R.id.buttonRegister) Button buttonRegister;
    @BindView(R.id.textViewLoginNow) TextView textViewLoginNow;

    UserViewModel userViewModel;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        textViewLoginNow.setPaintFlags(textViewLoginNow.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        buttonRegister.setOnClickListener(v -> {
            String login = editTexts.get(0).getText().toString();
            String password = editTexts.get(1).getText().toString();
            String password2 = editTexts.get(2).getText().toString();
            user = userViewModel.getItemByName(login);
            // sprawdź czy uzupełniono wszystkie pola
            if (login.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                Toast.makeText(this, getString(R.string.alldata), Toast.LENGTH_SHORT).show();
            }
            else {
                // sprawdź czy podany login istnieje w bazie
                if (userViewModel.checkItemByName(login) == 1) {
                    Toast.makeText(this, getString(R.string.double_login), Toast.LENGTH_SHORT).show();
                }
                else {
                    // sprawdź poprawność hasła
                    if (!password.equals(password2)) {
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
