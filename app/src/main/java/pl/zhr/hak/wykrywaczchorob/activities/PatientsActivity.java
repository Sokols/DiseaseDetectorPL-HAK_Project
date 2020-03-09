package pl.zhr.hak.wykrywaczchorob.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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

import pl.zhr.hak.wykrywaczchorob.Patient;
import pl.zhr.hak.wykrywaczchorob.PatientAdapter;
import pl.zhr.hak.wykrywaczchorob.PatientViewModel;
import pl.zhr.hak.wykrywaczchorob.R;

import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;

public class PatientsActivity extends AppCompatActivity {

    List<Patient> patientList = new ArrayList<>();
    PatientAdapter patientAdapter;
    PatientViewModel patientViewModel;
    RecyclerView recyclerView;
    Button buttonBackDataBase;
    Button buttonDeleteAll;
    Button buttonDeleteSelected;
    Button buttonSetAllPatients;
    Button buttonSetMyPatients;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        buttonDeleteAll = findViewById(R.id.buttonDeleteAll);
        buttonBackDataBase = findViewById(R.id.buttonBackDataBase);
        buttonDeleteSelected = findViewById(R.id.buttonDeleteSelected);
        buttonSetAllPatients = findViewById(R.id.buttonAllPatients);
        buttonSetMyPatients = findViewById(R.id.buttonMyPatients);

        recyclerView = findViewById(R.id.recyclerView1);
        patientAdapter = new PatientAdapter(patientList, PatientsActivity.this);
        recyclerView.setAdapter(patientAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        // dodaj pacjenta jeśli flaga jest ustawiona na true
        Bundle data = getIntent().getExtras();
        if (data.getBoolean("flag", false)) {
            int patientID = data.getInt("id");
            String name = data.getString("name");
            String surname = data.getString("surname");
            int age = data.getInt("age");
            int diseaseID = data.getInt("diseaseID");
            String date = data.getString("date");
            String addedBy = sharedPreferences.getString("name", "");
            patientViewModel.insert(new Patient(patientID, name, surname, diseaseID, age, addedBy, date));
        }

        // ustaw domyślny widok listy pacjentów
        setPatients(patientViewModel.getPatientList());

        // wróć do menu
        buttonBackDataBase.setOnClickListener(v -> finish());

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

        buttonSetMyPatients.setOnClickListener(v -> setPatients(patientViewModel.getItemsByAddedBy(sharedPreferences.getString("name", ""))));

        buttonSetAllPatients.setOnClickListener(v -> setPatients(patientViewModel.getPatientList()));
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

    private void setPatients(LiveData<List<Patient>> data) {
        data.observe(this, patients -> {
            patientList = patients;
            patientAdapter.setPatients(patientList);
        });
    }
}