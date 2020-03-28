package pl.zhr.hak.wykrywaczchorob.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.zhr.hak.wykrywaczchorob.R;
import pl.zhr.hak.wykrywaczchorob.Symptom;
import pl.zhr.hak.wykrywaczchorob.SymptomAdapter;

import static pl.zhr.hak.wykrywaczchorob.Symptom.getSymptoms;
import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;

public class ExaminationActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;

    SymptomAdapter symptomAdapter;
    SharedPreferences sharedPreferences;
    List<Symptom> symptomList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        // załaduj symptomy do adaptera
        setSymptoms();
    }

    // anuluj i wróć do menu
    @OnClick(R.id.buttonCancelSymptoms)
    public void buttonCancelSymptoms() {
        finish();
    }

    // zatwierdzenie symptomów
    @OnClick(R.id.buttonConfirmSymptoms)
    public void buttonConfirmSymptoms() {
        // jeśli nie zaznaczono symptomów, nie pozwól przejść dalej
        if (sharedPreferences.getInt("symptomCounter", 0) == 0) {
            Toast.makeText(ExaminationActivity.this, getString(R.string.not_enough), Toast.LENGTH_SHORT).show();
        }
        else {
            Intent diagnoseActivity = new Intent(ExaminationActivity.this, DiagnoseActivity.class);
            startActivity(diagnoseActivity);
            finish();
        }
    }

    @OnClick(R.id.buttonUncheckSymptoms)
    public void buttonUncheckSymptoms() {
        symptomAdapter.uncheckAll();
        setSymptoms();
    }

    private void setSymptoms() {
        symptomList = getSymptoms(this);
        symptomAdapter = new SymptomAdapter(symptomList, ExaminationActivity.this);
        recyclerView.setAdapter(symptomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
