package pl.zhr.hak.wykrywaczchorob.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.zhr.hak.wykrywaczchorob.R;

import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.textViewUser)
    TextView textViewUser;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        String name = sharedPreferences.getString("name", "");
        textViewUser.setText(getString(R.string.user, name));
    }

    @OnClick(R.id.imageButtonExit)
    public void imageButtonExit() {
        dialogConfirmExit().show();
    }

    @OnClick(R.id.imageButtonAdd)
    public void imageButtonAdd() {
        startActivity(new Intent(HomeActivity.this, ExaminationActivity.class));
    }

    @OnClick(R.id.imageButtonDB)
    public void imageButtonDB() {
        Intent patientActivity = new Intent(HomeActivity.this, PatientsActivity.class);
        // flag for signaling the need to add a patient - no need here
        patientActivity.putExtra("flag", false);
        startActivity(patientActivity);
    }

    @OnClick(R.id.buttonLogout)
    public void buttonLogout() {
        // uncheck the remember user flag
        sharedPreferences.edit().putBoolean("remember", false).apply();
        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
        finish();
    }

    // a dialog box confirming exiting the application
    private Dialog dialogConfirmExit() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getString(R.string.confirm));
        dialogBuilder.setMessage(getString(R.string.please_confirm));
        dialogBuilder.setPositiveButton(getString(R.string.yes),
                (dialog, whichButton) ->
                        finish());
        dialogBuilder.setNegativeButton(getString(R.string.no),
                (dialog, whichButton) ->
                { /* do nothing */ });
        return dialogBuilder.create();
    }
}