package pl.zhr.hak.wykrywaczchorob.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import pl.zhr.hak.wykrywaczchorob.R;
import pl.zhr.hak.wykrywaczchorob.Symptom;
import pl.zhr.hak.wykrywaczchorob.SymptomAdapter;

import static pl.zhr.hak.wykrywaczchorob.Symptom.getSymptoms;
import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;

public class ExaminationActivity extends AppCompatActivity {

    @BindViews({R.id.buttonCancelSymptoms, R.id.buttonConfirmSymptoms, R.id.buttonUncheckSymptoms})
        List<Button> buttons;
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

        buttons.get(0).setOnClickListener(v -> finish());

        buttons.get(1).setOnClickListener(v -> {
            // jeśli nie zaznaczono symptomów, nie pozwól przejść dalej
            if (sharedPreferences.getInt("symptomCounter", 0) == 0) {
                Toast.makeText(ExaminationActivity.this, getString(R.string.not_enough), Toast.LENGTH_SHORT).show();
            }
            else {
                Intent diagnoseActivity = new Intent(ExaminationActivity.this, DiagnoseActivity.class);
                startActivity(diagnoseActivity);
                finish();
            }
        });

        buttons.get(2).setOnClickListener(v -> {
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
