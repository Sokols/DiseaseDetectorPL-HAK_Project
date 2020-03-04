package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import static pl.zhr.hak.wykrywaczchorob.MainActivity.sharedPreferencesName;
import static pl.zhr.hak.wykrywaczchorob.SymptomAdapter.getChecked;

public class Examination2Activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView textViewDiagnosis;
    TextView textViewPatientDisease;
    TextView textViewDiagnosedDisease;
    Button buttonBackToMenu;
    Button buttonAddPatient;
    // flaga sygnalizująca czy znaleziono tylko jedną chorobę
    Boolean oneDiseaseFlag = true;
    // ID choroby, które zostanie przekazane do PatientActivity
    int diseaseID = 0;
    // lista zaznaczonych symptomów
    List<Symptom> sym;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination2);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        textViewDiagnosis = findViewById(R.id.textViewDiagnosis);
        textViewPatientDisease = findViewById(R.id.textViewPatientDisease);
        textViewDiagnosedDisease = findViewById(R.id.textViewDiagnosedDisease);
        buttonBackToMenu = findViewById(R.id.buttonBackToMenu);
        buttonAddPatient = findViewById(R.id.buttonAddPatient);

        // zebranie zaznaczonych symptomów z adaptera
        sym = getChecked();

        // wnioskowanie chorób po zatwierdzeniu symptomów
        // jeśli zostanie wywnioskowana więcej niż jedna choroba, zostaną one zapisane razem po przecinku

        // KORONAWIRUS - GORĄCZKA[4 - 1], KASZEL[1 - 1], DUSZNOŚCI[5 - 1]
        if (sym.get(3).getChecked() && sym.get(0).getChecked() && sym.get(4).getChecked()) {
            textViewDiagnosedDisease.setText(getString(R.string.coronavirus));
            diseaseID = 1;
        }
        // ZATRUCIE POKARMOWE - WYMIOTY[2 - 1], BÓL BRZUCHA[9 - 1], BIEGUNKA[8 - 1]
        if (sym.get(1).getChecked() && sym.get(8).getChecked() && sym.get(7).getChecked()) {
            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
                textViewDiagnosedDisease.setText(getString(R.string.food_poisoning));
                diseaseID = 2;
            }
            else {
                textViewDiagnosedDisease.setText(textViewDiagnosedDisease.getText().toString()
                        + ", " + getString(R.string.food_poisoning));
                oneDiseaseFlag = false;
            }
        }
        // GRYPA - BÓL GŁOWY[3 - 1], DRESZCZE[6 - 1], GORĄCZKA[4 - 1]
        if ((sym.get(2).getChecked() && sym.get(5).getChecked() && sym.get(3).getChecked())) {
            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
                textViewDiagnosedDisease.setText(getString(R.string.flu));
                diseaseID = 3;
            }
            else {
                textViewDiagnosedDisease.setText(textViewDiagnosedDisease.getText().toString()
                        + ", " + getString(R.string.flu));
                oneDiseaseFlag = false;
            }
        }
        // ANGINA - BÓL GARDŁA[7 - 1], DRESZCZE[6 - 1], GORĄCZKA[4 - 1]
        if ((sym.get(6).getChecked() && sym.get(5).getChecked() && sym.get(3).getChecked())) {
            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
                textViewDiagnosedDisease.setText(getString(R.string.angina));
                diseaseID = 4;
            }
            else {
                textViewDiagnosedDisease.setText(textViewDiagnosedDisease.getText().toString()
                        + ", " + getString(R.string.angina));
                oneDiseaseFlag = false;
            }
        }
        // HIPOCHONDRIA - NIEUZASADNIONY STRACH[10 - 1], NAPADY PANIKI[11 - 1]
        if (sym.get(9).getChecked() && sym.get(10).getChecked()) {
            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
                textViewDiagnosedDisease.setText(getString(R.string.hypochondria));
                diseaseID = 5;
            }
            else {
                textViewDiagnosedDisease.setText(textViewDiagnosedDisease.getText().toString()
                        + ", " + getString(R.string.hypochondria));
                oneDiseaseFlag = false;
            }
        }

        buttonBackToMenu.setOnClickListener(v -> finish());

        // zmien nazwe diagnozy jeśli nie znaleziono choroby
        if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
            textViewPatientDisease.setText(getString(R.string.no_disease));
        }

        buttonAddPatient.setOnClickListener(v -> {
            // jeśli nie znaleziono choroby, nie można dodać pacjenta do bazy
            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
                Toast.makeText(Examination2Activity.this, getString(R.string.cannot_add), Toast.LENGTH_SHORT).show();
            }
            // jeśli znaleziono więcej niż jedną chorobę, nie można dodać pacjenta do bazy
            else if(!oneDiseaseFlag) {
                Toast.makeText(Examination2Activity.this, getString(R.string.more_disease), Toast.LENGTH_SHORT).show();
            }
            else {
                Intent addPatientActivity = new Intent(Examination2Activity.this,
                        AddPatientActivity.class);
                // podanie do nowej aktywności nazwy zdiagnozowanej choroby
                addPatientActivity.putExtra("diseaseID", diseaseID);
                startActivity(addPatientActivity);
                finish();
            }
        });
    }
}
