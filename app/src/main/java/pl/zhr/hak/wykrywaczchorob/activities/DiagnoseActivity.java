package pl.zhr.hak.wykrywaczchorob.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.zhr.hak.wykrywaczchorob.Disease;
import pl.zhr.hak.wykrywaczchorob.R;
import pl.zhr.hak.wykrywaczchorob.Symptom;

import static pl.zhr.hak.wykrywaczchorob.Disease.getDiseases;
import static pl.zhr.hak.wykrywaczchorob.SymptomAdapter.getChecked;
import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;

public class DiagnoseActivity extends AppCompatActivity {

    @BindViews({R.id.textViewPatientDisease1, R.id.textViewPatientDisease2, R.id.textViewPatientDisease3})
        List<TextView> textViews;

    SharedPreferences sharedPreferences;
    // flaga sygnalizująca czy znaleziono tylko jedną chorobę o wysokim prawdopodobieństwie
    Boolean isDiseaseFlag = false;
    // ID choroby, które zostanie przekazane do PatientActivity
    int diseaseID = 0;

    List<Symptom> symptomList;
    List<Disease> diseaseList;
    List<Disease> highProbability = new ArrayList<>();
    List<Disease> mediumProbability = new ArrayList<>();
    List<Disease> lowProbability = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        // są 3 stopnie prawdopodobieństwa zachorowania - wysokie, średnie, niskie
        // pojawić się może maksymalnie 3 poziomy, minimalnie 1
        // poziom 2 i 3 na starcie wyłączamy
        textViews.get(1).setVisibility(View.GONE);
        textViews.get(2).setVisibility(View.GONE);

        startInference();
    }

    // wróć do menu
    @OnClick(R.id.buttonBackToMenu)
    public void buttonBackToMenu() {
        finish();
    }

    // dodaj pacjenta
    @OnClick(R.id.buttonAddPatient)
    public void buttonAddPatient() {
        // jeśli nie znaleziono jednej choroby o wysokim prawdopodobieństwie zarażenia, nie można dodać pacjenta do bazy
        if (diseaseID == 0) {
            Toast.makeText(DiagnoseActivity.this, getString(R.string.cannot_add), Toast.LENGTH_SHORT).show();
        }
        else {
            Intent addPatientActivity = new Intent(DiagnoseActivity.this, AddPatientActivity.class);
            // podanie do nowej aktywności nazwy zdiagnozowanej choroby
            addPatientActivity.putExtra("diseaseID", diseaseID);
            startActivity(addPatientActivity);
            finish();
        }
    }

    // algorytm odpowiadający za wnioskowanie
    public void startInference() {
        // zebranie zaznaczonych symptomów z adaptera oraz listy chorób
        symptomList = getChecked();
        diseaseList = getDiseases(this);
        // USTAWIENIE WYSTĄPIEŃ SYMPTOMÓW W POSZCZEGÓLNYCH CHOROBACH
        // sprawdzamy listę zaznaczonych symptomówq
        for (Symptom symptom : symptomList) {
            // przechodzimy dalej tylko jeśli symptom był zaznaczony
            if (symptom.getChecked()) {
                // sprawdzamy listę chorób
                for (Disease disease : diseaseList) {
                    // sprawdzamy każdy symptom choroby
                    for (int symptomIndex : disease.getSymptoms()) {
                        // sprawdzamy czy zaznaczony symptom odpowiada któremuś z symptomów choroby
                        if (symptom.getSymptomID() == symptomIndex) {
                            // jeśli tak, ustawiamy w chorobie
                            disease.setSymptomConfirm(disease.getSymptomConfirm() + 1);
                        }
                    }
                }
            }
        }

        // PODZIELENIE CHORÓB NA TRZY LISTY - WYSOKIE, ŚREDNIE I NISKIE PRAWDOPODOBIEŃSTWO WYSTĄPIENIA
        for (Disease disease : diseaseList) {
            // dodaj chorobę do odpowiedniej listy na podstawie zliczonych objawów
            switch(disease.getSymptomConfirm()) {
                case 1:
                    lowProbability.add(disease);
                    break;
                case 2:
                    mediumProbability.add(disease);
                    break;
                case 3:
                    highProbability.add(disease);
                    break;
                default:
                    break;
            }
        }

        // WYPISWANIE CHORÓB W TEXTVIEW NA PODSTAWIE UTWORZONYCH LIST PRAWDOPODOBIEŃSTW CHORÓB
        if (!highProbability.isEmpty()) {
            String name = null;
            // flaga sygnalizująca wystąpienienie więcej chorób w liście niż jedna
            boolean flag = true;
            for (Disease disease : highProbability) {
                // przejdź dalej jeśli jest to pierwsza choroba
                if (flag) {
                    // ustaw nazwę choroby
                    name = disease.getDiseaseName();
                    // zasygnalizuj, że istnieje już jedna choroba
                    flag = false;
                    // zasygnalizuj, że na ten moment diagnoza nadaje się do dodania do bazy
                    isDiseaseFlag = true;
                    // podaj ID zdiagnozowanej choroby
                    diseaseID = disease.getDiseaseID();
                }
                // przejdź dalej jeśli jest to któraś choroba z kolei
                else {
                    // dodaj kolejną chorobę do nazw
                    name = name + ", " + disease.getDiseaseName();
                    // zasygnalizuj, że diagnoza nie nadaje się do dodania do bazy
                    isDiseaseFlag = false;
                    // wyzeruj ID choroby - nie nadaje się do bazy
                    diseaseID = 0;
                }
            }
            // ustaw diagnozę dla wysokiego prawdopodobieństwa wystąpienia choroby
            textViews.get(0).setText(getString(R.string.patient_diagnose, getString(R.string.high), name));
        }

        if (!mediumProbability.isEmpty()) {
            // analogicznie
            String name = null;
            boolean flag = true;
            for (Disease disease : mediumProbability) {
                if (flag) {
                    name = disease.getDiseaseName();
                    flag = false;
                }
                else {
                    name = name + ", " + disease.getDiseaseName();
                }
            }
            // ustaw diagnozę dla średniego prawdopodobieństwa wystąpienia choroby
            // jeśli wysokie prawdopodobieństwo nie istniało ustaw diagnozę w TextView1
            if (highProbability.isEmpty()) {
                textViews.get(0).setText(getString(R.string.patient_diagnose, getString(R.string.medium), name));
            }
            // jeśli istniało, uwidocznij TextView2 i dodaj do niego diagnozę
            else {
                textViews.get(1).setVisibility(View.VISIBLE);
                textViews.get(1).setText(getString(R.string.patient_diagnose, getString(R.string.medium), name));
            }
        }

        if (!lowProbability.isEmpty()) {
            // analogicznie
            String name = null;
            boolean flag = true;
            for (Disease disease : lowProbability) {
                if (flag) {
                    name = disease.getDiseaseName();
                    flag = false;
                }
                else {
                    name = name + ", " + disease.getDiseaseName();
                }
            }
            // ustaw diagnozę dla niskiego prawdopodobieństwa wystąpienia choroby
            // jeśli wysokie i średnie prawdopodobieństwo nie istniało ustaw diagnozę w TextView1
            if (highProbability.isEmpty() && mediumProbability.isEmpty()) {
                textViews.get(0).setText(getString(R.string.patient_diagnose, getString(R.string.low), name));
            }
            // jeśli wysokie prawdopodobieństwo istniało, a średnie nie, uwidocznij TextView2 i dodaj do niego diagnozę
            else if (!highProbability.isEmpty() && mediumProbability.isEmpty()){
                textViews.get(1).setVisibility(View.VISIBLE);
                textViews.get(1).setText(getString(R.string.patient_diagnose, getString(R.string.low), name));
            }
            // jeśli nie istniało ani wysokie ani średnie uwidocznij TextView3 i dodaj do niego diagnozę
            else {
                textViews.get(2).setVisibility(View.VISIBLE);
                textViews.get(2).setText(getString(R.string.patient_diagnose, getString(R.string.low), name));
            }
        }
    }
}