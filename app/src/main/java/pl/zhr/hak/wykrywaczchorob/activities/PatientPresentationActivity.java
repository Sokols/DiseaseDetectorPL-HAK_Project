package pl.zhr.hak.wykrywaczchorob.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.zhr.hak.wykrywaczchorob.Disease;
import pl.zhr.hak.wykrywaczchorob.Patient;
import pl.zhr.hak.wykrywaczchorob.R;

import static pl.zhr.hak.wykrywaczchorob.Disease.getDiseases;

public class PatientPresentationActivity extends AppCompatActivity {

    @BindView(R.id.listViewInfo) ListView listViewInfo;

    Patient patient;
    List<String> patientList = new ArrayList<>();
    List<Disease> diseaseList;
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_presentation);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        diseaseList = getDiseases(PatientPresentationActivity.this);
        patient = Objects.requireNonNull(extras).getParcelable("patient");

        // przygotowanie listy zawierającej dane pacjenta
        setPatientInfo(patientList);

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, patientList);
        listViewInfo.setAdapter(arrayAdapter);
    }

    // wróć do bazy pacjentów
    @OnClick(R.id.buttonIntroduceBack)
    public void setButtonIntroduceBack() {
        finish();
    }

    // dodaj informacje o pacjencie do listy
    public void setPatientInfo(List<String> patientList) {
        patientList.add(getString(R.string.patientID_introduce, patient.getPatientID()));
        patientList.add(getString(R.string.name_introduce, patient.getName()));
        patientList.add(getString(R.string.surname_introduce, patient.getSurname()));
        patientList.add(getString(R.string.sex) + " " + patient.getRealSex(this, patient.getSex()));
        patientList.add(getString(R.string.age_introduce, patient.getAge()));
        patientList.add(getString(R.string.disease_introduce, diseaseList.get(patient.getDiseaseID() - 1).getDiseaseName()));
        patientList.add(getString(R.string.added_by_introduce, patient.getAddedBy()));
        patientList.add(getString(R.string.date_added, patient.getDate()));
    }
}


