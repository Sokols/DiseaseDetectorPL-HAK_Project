package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static pl.zhr.hak.wykrywaczchorob.MainActivity.sharedPreferencesName;

public class PatientsActivity extends AppCompatActivity {

    List<Patient> patientList = new ArrayList<>();
    PatientAdapter patientAdapter;
    PatientViewModel patientViewModel;
    Button buttonBackDataBase;
    Button buttonDeleteAll;
    Button buttonDeleteSelected;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        buttonDeleteAll = findViewById(R.id.buttonDeleteAll);
        buttonBackDataBase = findViewById(R.id.buttonBackDataBase);
        buttonDeleteSelected = findViewById(R.id.buttonDeleteSelected);

        buttonBackDataBase.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerView1);
        patientAdapter = new PatientAdapter(patientList, PatientsActivity.this);
        recyclerView.setAdapter(patientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        buttonDeleteAll.setOnClickListener(v -> {
            if (patientList.isEmpty()) {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show();
            }
            else {
                dialogConfirmAll().show();
            }
        });

        buttonDeleteSelected.setOnClickListener(v -> {
            if (patientList.isEmpty()) {
                Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show();
            }
            else {
                dialogConfirmSelected().show();
            }
        });

        Bundle data = getIntent().getExtras();
        if (data.getBoolean("flag", false)) {
            int patientID = data.getInt("id");
            String name = data.getString("name");
            String surname = data.getString("surname");
            String PESEL = data.getString("PESEL");
            int diseaseID = data.getInt("diseaseID");
            String addedBy = sharedPreferences.getString("name", "");
            patientViewModel.insert(new Patient(patientID, name, surname, diseaseID, PESEL, addedBy));
        }

        patientViewModel.getPatientList().observe(this, patients -> {
            patientList = patients;
            patientAdapter.setPatients(patientList);
        });
    }

    private Dialog dialogConfirmAll() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getString(R.string.confirm));
        dialogBuilder.setMessage(getString(R.string.please_confirm));
        dialogBuilder.setPositiveButton(getString(R.string.yes),
                (dialog, whichButton) -> patientViewModel.deleteAll());
        dialogBuilder.setNegativeButton(getString(R.string.no),
                (dialog, whichButton) -> { /* nic nie rób */ });
        return dialogBuilder.create();
    }

    private Dialog dialogConfirmSelected() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(getString(R.string.confirm));
        dialogBuilder.setMessage(getString(R.string.please_confirm));
        dialogBuilder.setPositiveButton(getString(R.string.yes),
                (dialog, whichButton) ->
                        patientAdapter.deleteSelected(patientViewModel));
        dialogBuilder.setNegativeButton(getString(R.string.no),
                (dialog, whichButton) ->
                        { /* nic nie rób */ });
        return dialogBuilder.create();
    }
}