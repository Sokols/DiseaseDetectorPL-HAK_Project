package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static pl.zhr.hak.wykrywaczchorob.MainActivity.sharedPreferencesName;

public class Examination2Activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    TextView textViewDiagnosis;
    TextView textViewPatientDisease;
    TextView textViewDiagnosedDisease;
    Button buttonBackToMenu;
    Button buttonAddPatient;

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

        // wnioskowanie chorób po zatwierdzeniu symptomów
        // KORONAWIRUS - GORĄCZKA, KASZEL, DUSZNOŚCI
        if (sharedPreferences.getBoolean(getString(R.string.fever), false)
            && sharedPreferences.getBoolean(getString(R.string.cough), false)
            && sharedPreferences.getBoolean(getString(R.string.dyspnoea), false)) {
            textViewDiagnosedDisease.setText(getString(R.string.coronavirus));
        }
        // ZATRUCIE POKARMOWE - WYMIOTY, BÓL BRZUCHA, BIEGUNKA
        if (sharedPreferences.getBoolean(getString(R.string.vomiting), false)
                && sharedPreferences.getBoolean(getString(R.string.stomach_ache), false)
                && sharedPreferences.getBoolean(getString(R.string.diarrhea), false)) {
            textViewDiagnosedDisease.setText(getString(R.string.food_poisoning));
        }
        // GRYPA - BÓL GŁOWY, KASZEL, GORĄCZKA
        if (sharedPreferences.getBoolean(getString(R.string.headache), false)
                && sharedPreferences.getBoolean(getString(R.string.cough), false)
                && sharedPreferences.getBoolean(getString(R.string.fever), false)) {
            textViewDiagnosedDisease.setText(getString(R.string.flu));
        }
        // ANGINA - BÓL GARDŁA, DRESZCZE, GORĄCZKA
        if (sharedPreferences.getBoolean(getString(R.string.sore_throat), false)
                && sharedPreferences.getBoolean(getString(R.string.chills), false)
                && sharedPreferences.getBoolean(getString(R.string.fever), false)) {
            textViewDiagnosedDisease.setText(getString(R.string.angina));
        }

        buttonBackToMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonAddPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
}
