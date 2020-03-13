package pl.zhr.hak.wykrywaczchorob.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import pl.zhr.hak.wykrywaczchorob.R;
import pl.zhr.hak.wykrywaczchorob.Symptom;

import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;
import static pl.zhr.hak.wykrywaczchorob.SymptomAdapter.getChecked;

public class DiagnoseActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView textViewPatientDisease1;
    TextView textViewPatientDisease2;
    TextView textViewPatientDisease3;
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
        setContentView(R.layout.activity_diagnose);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        textViewPatientDisease1 = findViewById(R.id.textViewPatientDisease1);
        textViewPatientDisease2 = findViewById(R.id.textViewPatientDisease2);
        textViewPatientDisease3 = findViewById(R.id.textViewPatientDisease3);
        buttonBackToMenu = findViewById(R.id.buttonBackToMenu);
        buttonAddPatient = findViewById(R.id.buttonAddPatient);

        // zebranie zaznaczonych symptomów z adaptera
        sym = getChecked();

        // są 3 stopnie prawdopodobieństwa zachorowania - wysokie, średnie, niskie
        // pojawić się może maksymalnie 3 poziomy, minimalnie 1
        // poziom 2 i 3 na starcie wyłączamy
        textViewPatientDisease2.setVisibility(View.INVISIBLE);
        textViewPatientDisease3.setVisibility(View.INVISIBLE);

        // wnioskowanie chorób po zatwierdzeniu symptomów
        // jeśli zostanie wywnioskowana więcej niż jedna choroba, zostaną one zapisane razem po przecinku

        // KORONAWIRUS - GORĄCZKA[4 - 1], KASZEL[1 - 1], DUSZNOŚCI[5 - 1]
        // ZATRUCIE POKARMOWE - WYMIOTY[2 - 1], BÓL BRZUCHA[9 - 1], BIEGUNKA[8 - 1]
        // GRYPA - BÓL GŁOWY[3 - 1], DRESZCZE[6 - 1], GORĄCZKA[4 - 1]
        // ANGINA - BÓL GARDŁA[7 - 1], DRESZCZE[6 - 1], GORĄCZKA[4 - 1]
        // PRZEZIĘBIENIE - BÓL GARDŁA[7 - 1], KASZEL[1 - 1], KATAR[10 - 1]



        buttonBackToMenu.setOnClickListener(v -> finish());

        buttonAddPatient.setOnClickListener(v -> {
//            // jeśli nie znaleziono choroby, nie można dodać pacjenta do bazy
//            if (textViewDiagnosedDisease.getText().toString().equals(getString(R.string.nullable))) {
//                Toast.makeText(DiagnoseActivity.this, getString(R.string.cannot_add), Toast.LENGTH_SHORT).show();
//            }
//            // jeśli znaleziono więcej niż jedną chorobę, nie można dodać pacjenta do bazy
//            else if(!oneDiseaseFlag) {
//                Toast.makeText(DiagnoseActivity.this, getString(R.string.more_disease), Toast.LENGTH_SHORT).show();
//            }
//            else {
//                Intent addPatientActivity = new Intent(DiagnoseActivity.this, AddPatientActivity.class);
//                // podanie do nowej aktywności nazwy zdiagnozowanej choroby
//                addPatientActivity.putExtra("diseaseID", diseaseID);
//                startActivity(addPatientActivity);
//                finish();
//            }
        });
    }
}
