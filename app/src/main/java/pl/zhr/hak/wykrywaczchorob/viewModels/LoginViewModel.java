package pl.zhr.hak.wykrywaczchorob.viewModels;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import java.util.Locale;

import pl.zhr.hak.wykrywaczchorob.R;
import pl.zhr.hak.wykrywaczchorob.User;
import pl.zhr.hak.wykrywaczchorob.UserViewModel;
import pl.zhr.hak.wykrywaczchorob.activities.HomeActivity;

import static android.app.Activity.RESULT_OK;
import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.LANGUAGE_CODE;
import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.REQUEST_CODE;

public class LoginViewModel extends ViewModel {

    private SharedPreferences sharedPreferences;

    public void setSharedPreferences(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    // dodawanie użytkownika do bazy
    public void addUser(Context context, UserViewModel userViewModel, int requestCode, int resultCode, Intent data) {
        // sprawdź poprawność ID rezultatu
        if (requestCode == REQUEST_CODE) {
            // sprawdź poprawność wykonania
            if (resultCode == RESULT_OK) {
                // dodaj użytkownika do bazy danych zdalnie dodając ID z sharedpref
                Toast.makeText(context, context.getString(R.string.registration_confirm), Toast.LENGTH_SHORT).show();
                String login = data.getStringExtra("login");
                String password = data.getStringExtra("password");
                int userID = sharedPreferences.getInt("userID", 1);
                userViewModel.insert(new User(userID, login, password));
                sharedPreferences.edit().putInt("userID", userID + 1).apply();
            }
        }
    }

    // sprawdz czy mozliwe jest zalogowanie sie
    public void checkLogin(Context context, UserViewModel userViewModel, String login, String password, boolean remember) {
        // sprawdź czy wszsystkie pola zostały wypełnione
        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.alldata), Toast.LENGTH_SHORT).show();
        } else {
            User user = userViewModel.getItemByName(login);
            // sprawdź czy podany login istnieje w bazie danych
            if (userViewModel.checkItemByName(login) == 1) {
                // sprawdź czy dla podanego loginu wpisano odpowiednie hasło
                if (user.getPassword().equals(password)) {
                    sharedPreferences.edit().putBoolean("remember", remember).apply();
                    sharedPreferences.edit().putString("name", login).apply();
                    homeReady(context);
                } else {
                    Toast.makeText(context, context.getString(R.string.wrong_password), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, context.getString(R.string.wrong_login), Toast.LENGTH_SHORT).show();
            }
        }
    }

    // okno dialogowe potwierdzające wyjście z aplikacji
    public Dialog dialogConfirmExit(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.confirm));
        dialogBuilder.setMessage(context.getString(R.string.please_confirm));
        dialogBuilder.setPositiveButton(context.getString(R.string.yes),
                (dialog, whichButton) ->
                        ((Activity) context).finish());
        dialogBuilder.setNegativeButton(context.getString(R.string.no),
                (dialog, whichButton) ->
                { /* nic nie rób */ });
        return dialogBuilder.create();
    }

    // uruchomienie nowej aktywności
    public void homeReady(Context context) {
        Intent homeActivity = new Intent(context, HomeActivity.class);
        context.startActivity(homeActivity);
        ((Activity) context).finish();
    }

    // ustaw język aplikacji
    public void setLanguage(Context context) {
        LANGUAGE_CODE = sharedPreferences.getString("language", "values");
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(LANGUAGE_CODE));
        res.updateConfiguration(conf, dm);
    }
}
