package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class PatientsActivity extends AppCompatActivity {

    List<Patient> patientList = new ArrayList<>();
    PatientAdapter patientAdapter;
    PatientViewModel patientViewModel;
    Button buttonBackDataBase;
    Button buttonDeleteAll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        buttonDeleteAll = findViewById(R.id.buttonDeleteAll);
        buttonBackDataBase = findViewById(R.id.buttonBackDataBase);
        buttonBackDataBase.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        patientAdapter = new PatientAdapter(patientList, PatientsActivity.this);
        recyclerView.setAdapter(patientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        buttonDeleteAll.setOnClickListener(v -> {
            patientViewModel.deleteAll();
            // finish();
            // startActivity(getIntent());
        });

        Bundle data = getIntent().getExtras();
        if (data.getBoolean("flag", false)) {
            int patientID = data.getInt("id");
            String name = data.getString("name");
            String surname = data.getString("surname");
            String disease = data.getString("disease");
            patientViewModel.insert(new Patient(patientID, name, surname, disease));
        }

        patientViewModel.getPatientList().observe(this, patients -> {
            patientList = patients;
            patientAdapter.setPatients(patientList);
        });
    }
}