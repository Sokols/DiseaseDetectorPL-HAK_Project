package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static pl.zhr.hak.wykrywaczchorob.MainActivity.sharedPreferencesName;

public class Examination1Activity extends AppCompatActivity {

    List<Symptom> symptomList = new ArrayList<>();
    SymptomAdapter symptomAdapter;
    Button buttonCancelSymptoms;
    Button buttonConfirmSymptoms;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination1);

        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);
        addSymptoms();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        symptomAdapter = new SymptomAdapter(symptomList, Examination1Activity.this);
        recyclerView.setAdapter(symptomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonCancelSymptoms = findViewById(R.id.buttonCancelSymptoms);
        buttonConfirmSymptoms = findViewById(R.id.buttonConfirmSymptoms);

        buttonCancelSymptoms.setOnClickListener(v -> finish());

        buttonConfirmSymptoms.setOnClickListener(v -> {
            // jeśli zaznaczono więcej niż 4 symptomy, nie pozwól przejść dalej
            if (sharedPreferences.getInt("symptomCounter", 0) > 4) {
                Toast.makeText(Examination1Activity.this, getString(R.string.please_only4), Toast.LENGTH_SHORT).show();
            }
            else {
                Intent examination2activity = new Intent(Examination1Activity.this,
                        Examination2Activity.class);
                startActivity(examination2activity);
                finish();
            }
        });
    }

    public void addSymptoms() {
        symptomList.add(new Symptom(1, getString(R.string.cough)));
        symptomList.add(new Symptom(2, getString(R.string.vomiting)));
        symptomList.add(new Symptom(3, getString(R.string.headache)));
        symptomList.add(new Symptom(4, getString(R.string.fever)));
        symptomList.add(new Symptom(5, getString(R.string.dyspnoea)));
        symptomList.add(new Symptom(6, getString(R.string.chills)));
        symptomList.add(new Symptom(7, getString(R.string.sore_throat)));
        symptomList.add(new Symptom(8, getString(R.string.diarrhea)));
        symptomList.add(new Symptom(9, getString(R.string.stomach_ache)));
    }
}
