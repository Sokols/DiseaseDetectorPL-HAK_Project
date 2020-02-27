package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static pl.zhr.hak.wykrywaczchorob.MainActivity.sharedPreferencesName;

public class AddPatientActivity extends AppCompatActivity {

    EditText editTextAddName;
    EditText editTextAddSurname;
    TextView textViewAddDisease;
    Button buttonAddCancel;
    Button buttonAddConfirm;
    SharedPreferences sharedPreferences;
    int patientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        editTextAddName = findViewById(R.id.editTextAddName);
        editTextAddSurname = findViewById(R.id.editTextAddSurname);
        textViewAddDisease = findViewById(R.id.textViewAddDisease);
        buttonAddCancel = findViewById(R.id.buttonAddCancel);
        buttonAddConfirm = findViewById(R.id.buttonAddConfirm);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        Bundle extras = getIntent().getExtras();
        String disease = extras.getString("diseaseName");
        textViewAddDisease.setText(getString(R.string.disease) + ": "
                + disease);

        buttonAddCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                finish();
            }
        });

        buttonAddConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String name = editTextAddName.getText().toString();
                String surname = editTextAddSurname.getText().toString();
                if (name.isEmpty() || surname.isEmpty()) {
                    Toast.makeText(AddPatientActivity.this, R.string.alldata, Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent patientsActivity = new Intent(AddPatientActivity.this,
                            PatientsActivity.class);
                    patientID = sharedPreferences.getInt("id", 1);
                    patientsActivity.putExtra("id", patientID);
                    sharedPreferences.edit().putInt("id", patientID + 1).apply();
                    patientsActivity.putExtra("flag", true);
                    patientsActivity.putExtra("name", name);
                    patientsActivity.putExtra("surname", surname);
                    patientsActivity.putExtra("disease", disease);
                    startActivity(patientsActivity);
                    finish();
                }
            }
        });
    }
}
