package pl.zhr.hak.wykrywaczchorob.activities;

import android.content.Intent;
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

public class DiagnoseActivity extends AppCompatActivity {

    @BindViews({R.id.textViewPatientDisease1, R.id.textViewPatientDisease2, R.id.textViewPatientDisease3})
    List<TextView> textViews;

    // The disease ID to pass to PatientActivity
    private int diseaseID = 0;

    private List<Disease> highProbability;
    private List<Disease> mediumProbability;
    private List<Disease> lowProbability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnose);
        ButterKnife.bind(this);

        highProbability = new ArrayList<>();
        mediumProbability = new ArrayList<>();
        lowProbability = new ArrayList<>();

        // there are 3 levels of probability of getting sick - high, medium, low
        // a maximum of 3 levels may appear, minimum 1
        // turn level 2 and 3 off at the start
        textViews.get(1).setVisibility(View.GONE);
        textViews.get(2).setVisibility(View.GONE);

        startInference();
    }

    @OnClick(R.id.buttonBackToMenu)
    public void buttonBackToMenu() {
        finish();
    }

    @OnClick(R.id.buttonAddPatient)
    public void buttonAddPatient() {
        // if no single disease is found with a high probability of being infected, the patient cannot be added to the database
        if (diseaseID == 0) {
            Toast.makeText(DiagnoseActivity.this, getString(R.string.cannot_add), Toast.LENGTH_SHORT).show();
        } else {
            Intent addPatientActivity = new Intent(DiagnoseActivity.this, AddPatientActivity.class);
            // provide the name of the diagnosed disease to the new activity
            addPatientActivity.putExtra("diseaseID", diseaseID);
            startActivity(addPatientActivity);
            finish();
        }
    }

    // the algorithm responsible for inference
    public void startInference() {

        // collect the selected symptoms from the adapter and the list of diseases
        List<Symptom> symptomList = getChecked();
        List<Disease> diseaseList = getDiseases(this);
        // SET THE APPEARANCE OF SYMPTOMS IN INDIVIDUAL DISEASES
        // check the list of marked symptoms
        for (Symptom symptom : symptomList) {
            // only go forward if the symptom was marked
            if (symptom.getChecked()) {
                // check the list of diseases
                for (Disease disease : diseaseList) {
                    // check every symptom of the disease
                    for (int symptomIndex : disease.getSymptoms()) {
                        // check whether the marked symptom corresponds to any of the symptoms of the disease
                        if (symptom.getSymptomID() == symptomIndex) {
                            // if so, we set in sickness
                            disease.setSymptomConfirm(disease.getSymptomConfirm() + 1);
                        }
                    }
                }
            }
        }

        // PUT DISEASES INTO THREE LISTS - HIGH, MEDIUM AND LOW PROBABILITY OF OCCURRENCE
        for (Disease disease : diseaseList) {
            // add the disease to the appropriate list based on the counted symptoms
            switch (disease.getSymptomConfirm()) {
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

        // DISPLAYING OF DISEASES IN TEXTVIEW BASED ON THE CREATED LISTS OF DISEASE PROBABILITY
        if (!highProbability.isEmpty()) {
            String name = null;
            // flag signaling the occurrence of more than one disease in the list
            boolean flag = true;
            for (Disease disease : highProbability) {
                //move on if this is the first disease
                if (flag) {
                    name = disease.getDiseaseName();
                    flag = false;
                    diseaseID = disease.getDiseaseID();
                }
                // move on if this is another disease
                else {
                    // add another disease to the names
                    name = name + ", " + disease.getDiseaseName();
                    // reset disease ID - not suitable for database
                    diseaseID = 0;
                }
            }
            // set a diagnosis for a high probability of disease
            textViews.get(0).setText(getString(R.string.patient_diagnose, getString(R.string.high), name));
        }

        //similarly
        if (!mediumProbability.isEmpty()) {
            String name = null;
            boolean flag = true;
            for (Disease disease : mediumProbability) {
                if (flag) {
                    name = disease.getDiseaseName();
                    flag = false;
                } else {
                    name = name + ", " + disease.getDiseaseName();
                }
            }
            // set the diagnosis for the average probability of disease occurrence
            // if high probability did not exist set diagnosis in TextView1
            if (highProbability.isEmpty()) {
                textViews.get(0).setText(getString(R.string.patient_diagnose, getString(R.string.medium), name));
            }
            // if it existed, expose TextView2 and add a diagnosis to it
            else {
                textViews.get(1).setVisibility(View.VISIBLE);
                textViews.get(1).setText(getString(R.string.patient_diagnose, getString(R.string.medium), name));
            }
        }

        // similarly
        if (!lowProbability.isEmpty()) {
            String name = null;
            boolean flag = true;
            for (Disease disease : lowProbability) {
                if (flag) {
                    name = disease.getDiseaseName();
                    flag = false;
                } else {
                    name = name + ", " + disease.getDiseaseName();
                }
            }

            if (highProbability.isEmpty() && mediumProbability.isEmpty()) {
                textViews.get(0).setText(getString(R.string.patient_diagnose, getString(R.string.low), name));
            } else if (!highProbability.isEmpty() && mediumProbability.isEmpty()) {
                textViews.get(1).setVisibility(View.VISIBLE);
                textViews.get(1).setText(getString(R.string.patient_diagnose, getString(R.string.low), name));
            } else {
                textViews.get(2).setVisibility(View.VISIBLE);
                textViews.get(2).setText(getString(R.string.patient_diagnose, getString(R.string.low), name));
            }
        }
    }
}