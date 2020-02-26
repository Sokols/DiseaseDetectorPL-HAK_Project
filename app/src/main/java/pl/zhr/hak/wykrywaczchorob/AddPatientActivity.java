package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddPatientActivity extends AppCompatActivity {

    EditText editTextAddName;
    EditText editTextAddSurname;
    TextView textViewAddDisease;
    Button buttonAddCancel;
    Button buttonAddConfirm;

    PatientViewModel patientViewModel;
    List<Patient> patientList = new ArrayList<>();
    PatientAdapter patientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);

        editTextAddName = findViewById(R.id.editTextAddName);
        editTextAddSurname = findViewById(R.id.editTextAddSurname);
        textViewAddDisease = findViewById(R.id.textViewAddDisease);
        buttonAddCancel = findViewById(R.id.buttonAddCancel);
        buttonAddConfirm = findViewById(R.id.buttonAddConfirm);

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

        // patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        buttonAddConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                String name = editTextAddName.getText().toString();
                String surname = editTextAddSurname.getText().toString();
                if (name.isEmpty() || surname.isEmpty()) {
                    Toast.makeText(AddPatientActivity.this, R.string.alldata, Toast.LENGTH_SHORT).show();
                }
                else {
                    //patientAdapter = new PatientAdapter(patientList, AddPatientActivity.this);
                    // patientViewModel.insert(new Patient(name, surname, disease));
                   /* patientViewModel.getPatientList().observe(this, new Observer<List<Patient>>() {
                        @Override
                        public void onChanged(List<Patient> patients) {
                            patientList = patients;
                            patientAdapter.setPatients(patientList);
                        }
                    });*/
                    Intent patientsActivity = new Intent(AddPatientActivity.this,
                            PatientsActivity.class);
                    startActivity(patientsActivity);
                    finish();
                }
            }
        });
    }
}
