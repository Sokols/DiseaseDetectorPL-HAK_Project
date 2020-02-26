package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class PatientsActivity extends AppCompatActivity {

    List<Patient> patientList = new ArrayList<>();
    PatientAdapter patientAdapter;
    PatientViewModel patientViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);

        patientList.add(new Patient("Igor", "Sokol", "Łak"));
        patientList.add(new Patient("Artur", "Sokol", "Ebola"));
        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        patientAdapter = new PatientAdapter(patientList, PatientsActivity.this);
        recyclerView.setAdapter(patientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);
        patientViewModel.insert(new Patient("Bolo", "Sokol", "Ebe ebe ebe"));
        patientViewModel.getPatientList().observe(this, new Observer<List<Patient>>(){
            @Override
            public void onChanged(List<Patient> patients) {
                patientList = patients;
                patientAdapter.setPatients(patientList);
            }
        });
    }
}
