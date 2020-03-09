package pl.zhr.hak.wykrywaczchorob;

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

import static pl.zhr.hak.wykrywaczchorob.LoginActivity.sharedPreferencesName;
import static pl.zhr.hak.wykrywaczchorob.Symptom.getSymptoms;

public class ExaminationActivity extends AppCompatActivity {

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
        symptomList = getSymptoms(ExaminationActivity.this);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        symptomAdapter = new SymptomAdapter(symptomList, ExaminationActivity.this);
        recyclerView.setAdapter(symptomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonCancelSymptoms = findViewById(R.id.buttonCancelSymptoms);
        buttonConfirmSymptoms = findViewById(R.id.buttonConfirmSymptoms);

        buttonCancelSymptoms.setOnClickListener(v -> finish());

        buttonConfirmSymptoms.setOnClickListener(v -> {
            // jeśli zaznaczono więcej niż 4 symptomy, nie pozwól przejść dalej
            if (sharedPreferences.getInt("symptomCounter", 0) > 4) {
                Toast.makeText(ExaminationActivity.this, getString(R.string.please_only4), Toast.LENGTH_SHORT).show();
            }
            // jeśli zaznaczono mniej niż 2 symptomy, nie pozwól przejść dalej
            else if (sharedPreferences.getInt("symptomCounter", 0) < 2) {
                Toast.makeText(this, getString(R.string.not_enough), Toast.LENGTH_SHORT).show();
            }
            else {
                Intent diagnoseActivity = new Intent(ExaminationActivity.this,
                        DiagnoseActivity.class);
                startActivity(diagnoseActivity);
                finish();
            }
        });
    }
}
