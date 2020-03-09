package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static pl.zhr.hak.wykrywaczchorob.LoginActivity.sharedPreferencesName;

public class AddPatientActivity extends AppCompatActivity {

    EditText editTextAddName;
    EditText editTextAddSurname;
    EditText editTextPESEL;
    Button buttonAddCancel;
    Button buttonAddConfirm;
    SharedPreferences sharedPreferences;
    int patientID;
    int diseaseID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        editTextAddName = findViewById(R.id.editTextAddName);
        editTextAddSurname = findViewById(R.id.editTextAddSurname);
        editTextPESEL = findViewById(R.id.editTextPESEL);
        buttonAddCancel = findViewById(R.id.buttonAddCancel);
        buttonAddConfirm = findViewById(R.id.buttonAddConfirm);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        Bundle extras = getIntent().getExtras();
        diseaseID = extras.getInt("diseaseID", 0);

        buttonAddCancel.setOnClickListener(v -> finish());

        buttonAddConfirm.setOnClickListener(v -> {
            String name = editTextAddName.getText().toString();
            String surname = editTextAddSurname.getText().toString();
            String PESEL = editTextPESEL.getText().toString();
            // jeśli nie uzupełniono wszystkich danych pacjenta nie pozwól przejść dalej
            if (name.isEmpty() || surname.isEmpty() || PESEL.isEmpty()) {
                Toast.makeText(AddPatientActivity.this, R.string.alldata, Toast.LENGTH_SHORT).show();
            }
            else {
                Intent patientsActivity = new Intent(AddPatientActivity.this,
                        PatientsActivity.class);
                // ręczne nadawanie ID pacjentom
                patientID = sharedPreferences.getInt("id", 1);
                patientsActivity.putExtra("id", patientID);
                sharedPreferences.edit().putInt("id", patientID + 1).apply();
                // flaga służąca do sygnalizowania potrzeby dodania pacjenta - tutaj istnieje potrzeba
                patientsActivity.putExtra("flag", true);
                patientsActivity.putExtra("name", name);
                patientsActivity.putExtra("surname", surname);
                patientsActivity.putExtra("PESEL", PESEL);
                patientsActivity.putExtra("diseaseID", diseaseID);
                startActivity(patientsActivity);
                finish();
            }
        });
    }
}
