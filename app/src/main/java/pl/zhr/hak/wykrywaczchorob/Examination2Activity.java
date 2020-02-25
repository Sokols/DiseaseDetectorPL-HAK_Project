package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class Examination2Activity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String sharedPreferencesName = "data";
    TextView textViewDiagnosis;
    TextView textViewPatientDisease;
    TextView textViewDiagnosedDisease;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination2);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        textViewDiagnosis = findViewById(R.id.textViewDiagnosis);
        textViewPatientDisease = findViewById(R.id.textViewPatientDisease);
        textViewDiagnosedDisease = findViewById(R.id.textViewDiagnosedDisease);

        // wnioskowanie chorób po zatwierdzeniu symptomów
        // KORONAWIRUS - GORĄCZKA, KASZEL, DUSZNOŚCI
        if (sharedPreferences.getBoolean(getString(R.string.fever), false)
            && sharedPreferences.getBoolean(getString(R.string.cough), false)
            && sharedPreferences.getBoolean(getString(R.string.dyspnoea), false)) {
            textViewDiagnosedDisease.setText(getString(R.string.coronavirus));
        }
    }
}
