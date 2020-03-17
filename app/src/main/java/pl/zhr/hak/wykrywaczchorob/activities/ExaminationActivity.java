package pl.zhr.hak.wykrywaczchorob.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pl.zhr.hak.wykrywaczchorob.R;
import pl.zhr.hak.wykrywaczchorob.Symptom;
import pl.zhr.hak.wykrywaczchorob.SymptomAdapter;

import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;
import static pl.zhr.hak.wykrywaczchorob.Symptom.getSymptoms;

public class ExaminationActivity extends AppCompatActivity {

    List<Symptom> symptomList = new ArrayList<>();
    SymptomAdapter symptomAdapter;
    Button buttonCancelSymptoms;
    Button buttonConfirmSymptoms;
    Button buttonUncheckSymptoms;
    SharedPreferences sharedPreferences;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);

        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        buttonCancelSymptoms = findViewById(R.id.buttonCancelSymptoms);
        buttonConfirmSymptoms = findViewById(R.id.buttonConfirmSymptoms);
        buttonUncheckSymptoms = findViewById(R.id.buttonUncheckSymptoms);
        recyclerView = findViewById(R.id.recyclerView);

        setSymptoms();

        buttonCancelSymptoms.setOnClickListener(v -> finish());

        buttonConfirmSymptoms.setOnClickListener(v -> {
            // jeśli zaznaczono więcej niż 4 symptomy, nie pozwól przejść dalej
            if (sharedPreferences.getInt("symptomCounter", 0) > 4) {
                Toast.makeText(ExaminationActivity.this, getString(R.string.please_only4), Toast.LENGTH_SHORT).show();
            }
            // jeśli zaznaczono mniej niż 2 symptomy, nie pozwól przejść dalej
//            else if (sharedPreferences.getInt("symptomCounter", 0) < 2) {
//                Toast.makeText(this, getString(R.string.not_enough), Toast.LENGTH_SHORT).show();
//            }
            else {
                Intent diagnoseActivity = new Intent(ExaminationActivity.this, DiagnoseActivity.class);
                startActivity(diagnoseActivity);
                finish();
            }
        });

        buttonUncheckSymptoms.setOnClickListener(v -> {
            symptomAdapter.uncheckAll();
            setSymptoms();
        });
    }

    private void setSymptoms() {
        symptomList = getSymptoms(this);
        symptomAdapter = new SymptomAdapter(symptomList, ExaminationActivity.this);
        recyclerView.setAdapter(symptomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
