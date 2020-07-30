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

    public void addUser(Context context, UserViewModel userViewModel, int requestCode, int resultCode, Intent data) {
        // check if the ID of the result is correct
        if (requestCode == REQUEST_CODE) {
            // check the correctness of execution
            if (resultCode == RESULT_OK) {
                // add a user to the database remotely by adding an ID with sharedpreferences
                Toast.makeText(context, context.getString(R.string.registration_confirm), Toast.LENGTH_SHORT).show();
                String login = data.getStringExtra("login");
                String password = data.getStringExtra("password");
                int userID = sharedPreferences.getInt("userID", 1);
                userViewModel.insert(new User(userID, login, password));
                sharedPreferences.edit().putInt("userID", userID + 1).apply();
            }
        }
    }

    // check if it is possible to log in
    public void checkLogin(Context context, UserViewModel userViewModel, String login, String password, boolean remember) {
        if (login.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, context.getString(R.string.alldata), Toast.LENGTH_SHORT).show();
        } else {
            User user = userViewModel.getItemByName(login);
            if (userViewModel.checkItemByName(login) == 1) {
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

    // a dialog box confirming exiting the application
    public Dialog dialogConfirmExit(Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        dialogBuilder.setTitle(context.getString(R.string.confirm));
        dialogBuilder.setMessage(context.getString(R.string.please_confirm));
        dialogBuilder.setPositiveButton(context.getString(R.string.yes),
                (dialog, whichButton) ->
                        ((Activity) context).finish());
        dialogBuilder.setNegativeButton(context.getString(R.string.no),
                (dialog, whichButton) ->
                { /* do nothing */ });
        return dialogBuilder.create();
    }

    // launching a new activity
    public void homeReady(Context context) {
        context.startActivity(new Intent(context, HomeActivity.class));
        ((Activity) context).finish();
    }

    // set the language of the application
    public void setLanguage(Context context) {
        LANGUAGE_CODE = sharedPreferences.getString("language", "values");
        Resources res = context.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale(LANGUAGE_CODE));
        res.updateConfiguration(conf, dm);
    }
}
