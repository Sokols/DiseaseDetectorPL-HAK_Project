package pl.zhr.hak.wykrywaczchorob.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import pl.zhr.hak.wykrywaczchorob.Disease;
import pl.zhr.hak.wykrywaczchorob.R;

import static pl.zhr.hak.wykrywaczchorob.Disease.getDiseases;
import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;

public class AddPatientActivity extends AppCompatActivity {

    EditText editTextAddName;
    EditText editTextAddSurname;
    EditText editTextAge;
    TextView textViewAddDisease;
    Button buttonAddCancel;
    Button buttonAddConfirm;
    Spinner spinnerSex;
    SharedPreferences sharedPreferences;
    int patientID;
    int diseaseID;
    List<Disease> diseaseList;
    String sex = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        editTextAddName = findViewById(R.id.editTextAddName);
        editTextAddSurname = findViewById(R.id.editTextAddSurname);
        editTextAge = findViewById(R.id.editTextAge);
        textViewAddDisease = findViewById(R.id.textViewAddDisease);
        buttonAddCancel = findViewById(R.id.buttonAddCancel);
        buttonAddConfirm = findViewById(R.id.buttonAddConfirm);
        spinnerSex = findViewById(R.id.spinnerSex);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        Bundle extras = getIntent().getExtras();
        diseaseID = extras.getInt("diseaseID", 0);
        diseaseList = getDiseases(this);

        textViewAddDisease.setText(getString(R.string.disease_introduce, diseaseList.get(diseaseID - 1).getDiseaseName()));
        String[] spinnerSexes = {"-", getString(R.string.female), getString(R.string.male)};
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, spinnerSexes);
        spinnerSex.setAdapter(spinnerAdapter);
        spinnerSex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sex = "";
                        break;
                    case 1:
                        sex = "female";
                        break;
                    case 2:
                        sex = "male";
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        buttonAddCancel.setOnClickListener(v -> finish());

        buttonAddConfirm.setOnClickListener(v -> {
            String name = editTextAddName.getText().toString();
            String surname = editTextAddSurname.getText().toString();
            // jeśli nie uzupełniono wszystkich danych pacjenta nie pozwól przejść dalej
            if (name.isEmpty() || surname.isEmpty() || editTextAge.getText().toString().isEmpty() || sex.equals("")) {
                Toast.makeText(AddPatientActivity.this, R.string.alldata, Toast.LENGTH_SHORT).show();
            }
            else {
                // sprawdzenie czy w pole wieku wpisano liczbę
                boolean digitsOnly = TextUtils.isDigitsOnly(editTextAge.getText());
                if (digitsOnly) {
                    int age = Integer.parseInt(editTextAge.getText().toString());
                    @SuppressLint("SimpleDateFormat") DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm");
                    String date = df.format(Calendar.getInstance().getTime());
                    Intent patientsActivity = new Intent(AddPatientActivity.this, PatientsActivity.class);
                    // ręczne nadawanie ID pacjentom
                    patientID = sharedPreferences.getInt("id", 1);
                    patientsActivity.putExtra("id", patientID);
                    sharedPreferences.edit().putInt("id", patientID + 1).apply();
                    // flaga służąca do sygnalizowania potrzeby dodania pacjenta - tutaj istnieje potrzeba
                    patientsActivity.putExtra("flag", true);
                    patientsActivity.putExtra("name", name);
                    patientsActivity.putExtra("surname", surname);
                    patientsActivity.putExtra("sex", sex);
                    patientsActivity.putExtra("age", age);
                    patientsActivity.putExtra("diseaseID", diseaseID);
                    patientsActivity.putExtra("date", date);
                    startActivity(patientsActivity);
                    finish();
                }
                else {
                    Toast.makeText(this, getString(R.string.wrong_age), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
