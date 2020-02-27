package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static pl.zhr.hak.wykrywaczchorob.MainActivity.sharedPreferencesName;

public class Examination2Activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView textViewDiagnosis;
    TextView textViewPatientDisease;
    TextView textViewDiagnosedDisease;
    Button buttonBackToMenu;
    Button buttonAddPatient;
    // tablica odpowiedzialna za przechowywanie potwierdzonych objawów
    Boolean [] symptoms = new Boolean[12];
    // flaga sygnalizująca czy znaleziono tylko jedną chorobę
    Boolean oneDiseaseFlag = true;

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

        // zebranie zaznaczonych symptomów do tablicy
        getSymptoms();

        // wnioskowanie chorób po zatwierdzeniu symptomów
        // jeśli zostanie wywnioskowana więcej niż jedna choroba, zostaną one zapisane razem po przecinku

        // KORONAWIRUS - GORĄCZKA[4], KASZEL[1], DUSZNOŚCI[5]
        if (symptoms[4] && symptoms[1] && symptoms[5]) {
            textViewDiagnosedDisease.setText(getString(R.string.coronavirus));
        }
        // ZATRUCIE POKARMOWE - WYMIOTY[2], BÓL BRZUCHA[9], BIEGUNKA[8]
        if (symptoms[2] && symptoms[9] && symptoms[8]) {
            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
                textViewDiagnosedDisease.setText(getString(R.string.food_poisoning));
            }
            else {
                textViewDiagnosedDisease.setText(textViewDiagnosedDisease.getText().toString()
                        + ", " + getString(R.string.food_poisoning));
                oneDiseaseFlag = false;
            }
        }
        // GRYPA - BÓL GŁOWY[3], DRESZCZE[6], GORĄCZKA[4]
        if (symptoms[3] && symptoms[6] && symptoms[4]) {
            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
                textViewDiagnosedDisease.setText(getString(R.string.flu));
            }
            else {
                textViewDiagnosedDisease.setText(textViewDiagnosedDisease.getText().toString()
                        + ", " + getString(R.string.flu));
                oneDiseaseFlag = false;
            }
        }
        // ANGINA - BÓL GARDŁA[7], DRESZCZE[6], GORĄCZKA[4]
        if (symptoms[7] && symptoms[6] && symptoms[4]) {
            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
                textViewDiagnosedDisease.setText(getString(R.string.angina));
            }
            else {
                textViewDiagnosedDisease.setText(textViewDiagnosedDisease.getText().toString()
                        + ", " + getString(R.string.angina));
                oneDiseaseFlag = false;
            }
        }
        // HIPOCHONDRIA - NIEUZASADNIONY STRACH[10], NAPADY PANIKI[11]
        if (symptoms[10] && symptoms[11]) {
            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
                textViewDiagnosedDisease.setText(getString(R.string.hypochondria));
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
                addPatientActivity.putExtra("diseaseName",
                        textViewDiagnosedDisease.getText().toString());
                startActivity(addPatientActivity);
                finish();
            }
        });
    }

    private void getSymptoms() {
        symptoms[0] = false; // blank
        symptoms[1] = sharedPreferences.getBoolean(getString(R.string.cough), false);
        symptoms[2] = sharedPreferences.getBoolean(getString(R.string.vomiting), false);
        symptoms[3] = sharedPreferences.getBoolean(getString(R.string.headache), false);
        symptoms[4] = sharedPreferences.getBoolean(getString(R.string.fever), false);
        symptoms[5] = sharedPreferences.getBoolean(getString(R.string.dyspnoea), false);
        symptoms[6] = sharedPreferences.getBoolean(getString(R.string.chills), false);
        symptoms[7] = sharedPreferences.getBoolean(getString(R.string.sore_throat), false);
        symptoms[8] = sharedPreferences.getBoolean(getString(R.string.diarrhea), false);
        symptoms[9] = sharedPreferences.getBoolean(getString(R.string.stomach_ache), false);
        symptoms[10] = sharedPreferences.getBoolean(getString(R.string.fear), false);
        symptoms[11] = sharedPreferences.getBoolean(getString(R.string.panic), false);
    }
}
