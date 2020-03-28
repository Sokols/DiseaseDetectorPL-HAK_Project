package pl.zhr.hak.wykrywaczchorob.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.zhr.hak.wykrywaczchorob.Patient;
import pl.zhr.hak.wykrywaczchorob.PatientAdapter;
import pl.zhr.hak.wykrywaczchorob.PatientViewModel;
import pl.zhr.hak.wykrywaczchorob.R;

import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;

public class PatientsActivity extends AppCompatActivity {

    @BindViews({R.id.buttonBackDataBase, R.id.buttonCheckAll, R.id.buttonDeleteSelected, R.id.buttonSetAllPatients, R.id.buttonSetMyPatients})
        List<Button> buttons;
    @BindView(R.id.recyclerView1) RecyclerView recyclerView1;

    SharedPreferences sharedPreferences;
    List<Patient> patientList = new ArrayList<>();
    PatientAdapter patientAdapter;
    PatientViewModel patientViewModel;

    @SuppressLint("StringFormatMatches")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        patientAdapter = new PatientAdapter(patientList, PatientsActivity.this);
        recyclerView1.setAdapter(patientAdapter);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));
        patientViewModel = new ViewModelProvider(this).get(PatientViewModel.class);

        // dodaj pacjenta jeśli flaga jest ustawiona na true
        Bundle data = getIntent().getExtras();
        if (Objects.requireNonNull(data).getBoolean("flag", false)) {
            addPatient(data);
        }

        // ustaw domyślny widok listy pacjentów
        setPatients(patientViewModel.getPatientList());
    }

    // wróć do menu
    @OnClick(R.id.buttonBackDataBase)
    public void setButtonBackDataBase() {
        finish();
    }

    // zaznacz / odznacz wszystkie checkboxy
    @OnClick(R.id.buttonCheckAll)
    public void setButtonCheckAll() {
        patientAdapter.checkAll(buttons.get(1).getText().toString());
        // zmień tekst przycisku na przeciwny
        if (buttons.get(1).getText().toString().equals(getString(R.string.check_all))) {
            buttons.get(1).setText(R.string.uncheck_all);
        }
        else {
            buttons.get(1).setText(R.string.check_all);
        }
    }

    // usuń zaznaczonych pacjentów
    @OnClick(R.id.buttonDeleteSelected)
    public void setButtonDeleteSelected() {
        if (patientList.isEmpty()) {
            Toast.makeText(this, getString(R.string.empty), Toast.LENGTH_SHORT).show();
        }
        else {
            dialogConfirmSelected().show();
        }
    }

    // wyświetl wszystkich pacjentów
    @OnClick(R.id.buttonSetAllPatients)
    public void setButtonSetAllPatients() {
        setPatients(patientViewModel.getPatientList());
        buttons.get(1).setText(R.string.check_all);
    }

    // wyświetl moich pacjentów
    @OnClick(R.id.buttonSetMyPatients)
    public void setButtonSetMyPatients() {
        setPatients(patientViewModel.getItemsByAddedBy(sharedPreferences.getString("name", "")));
        buttons.get(1).setText(R.string.check_all);
    }

    // dodawanie pacjenta - w arguemencie przesłane dane z AddPatientActivity
    public void addPatient(Bundle data) {
        int patientID = data.getInt("id");
        String name = data.getString("name");
        String surname = data.getString("surname");
        String sex = data.getString("sex");
        int age = data.getInt("age");
        int diseaseID = data.getInt("diseaseID");
        String date = data.getString("date");
        String addedBy = sharedPreferences.getString("name", "");
        patientViewModel.insert(new Patient(patientID, name, surname, sex, diseaseID, age, addedBy, date, false));
    }

    // okno dialogowe potwierdzające usuwanie pacjentów
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

    // ustawianie widoku listy pacjentów
    private void setPatients(LiveData<List<Patient>> data) {
        data.observe(this, patients -> {
            patientList = patients;
            patientAdapter.setPatients(patientList);
        });
    }
}