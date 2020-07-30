package pl.zhr.hak.wykrywaczchorob.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private SymptomAdapter symptomAdapter;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination);
        ButterKnife.bind(this);
        sharedPreferences = getSharedPreferences(sharedPreferencesName, 0);

        setSymptoms();
    }

    @OnClick(R.id.buttonCancelSymptoms)
    public void buttonCancelSymptoms() {
        finish();
    }

    @OnClick(R.id.buttonConfirmSymptoms)
    public void buttonConfirmSymptoms() {
        // if symptoms are not marked, do not move on
        if (sharedPreferences.getInt("symptomCounter", 0) == 0) {
            Toast.makeText(ExaminationActivity.this, getString(R.string.not_enough), Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(ExaminationActivity.this, DiagnoseActivity.class));
            finish();
        }
    }

    @OnClick(R.id.buttonUncheckSymptoms)
    public void buttonUncheckSymptoms() {
        symptomAdapter.uncheckAll();
        setSymptoms();
    }

    private void setSymptoms() {
        List<Symptom> symptomList = getSymptoms(this);
        symptomAdapter = new SymptomAdapter(symptomList, ExaminationActivity.this);
        recyclerView.setAdapter(symptomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
