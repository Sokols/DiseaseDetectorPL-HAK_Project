package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import static pl.zhr.hak.wykrywaczchorob.Disease.getDiseases;

public class PatientPresentationActivity extends AppCompatActivity {

    Patient patient;
    ListView listViewInfo;
    Button buttonIntroduceBack;
    List<String> patientList = new ArrayList<>();
    List<Disease> diseaseList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_presentation);

        buttonIntroduceBack = findViewById(R.id.buttonIntroduceBack);
        buttonIntroduceBack.setOnClickListener(v -> finish());

        Bundle extras = getIntent().getExtras();
        diseaseList = getDiseases(PatientPresentationActivity.this);
        patient = extras.getParcelable("patient");
        listViewInfo = findViewById(R.id.listViewInfo);
        patientList.add(getString(R.string.patientID_introduce, patient.getPatientID()));
        patientList.add(getString(R.string.name_introduce, patient.getName()));
        patientList.add(getString(R.string.surname_introduce, patient.getSurname()));
        patientList.add(getString(R.string.PESEL_introduce, patient.getPESEL()));
        patientList.add(getString(R.string.disease_introduce, diseaseList.get(patient.getDiseaseID() - 1).getDiseaseName()));
        patientList.add(getString(R.string.added_by_introduce, patient.getAddedBy()));

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patientList);
        listViewInfo.setAdapter(arrayAdapter);
    }
}
