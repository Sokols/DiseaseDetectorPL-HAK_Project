package pl.zhr.hak.wykrywaczchorob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class Examination1Activity extends AppCompatActivity {

    List<Symptom> symptomList = new ArrayList<>();
    SymptomAdapter symptomAdapter;
    Button buttonCancelSymptoms;
    Button buttonConfirmSymptoms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examination1);

        addSymptoms();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        symptomAdapter = new SymptomAdapter(symptomList, Examination1Activity.this);
        recyclerView.setAdapter(symptomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonCancelSymptoms = findViewById(R.id.buttonCancelSymptoms);
        buttonConfirmSymptoms = findViewById(R.id.buttonConfirmSymptoms);

        buttonCancelSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        buttonConfirmSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent examination2acitivty = new Intent(Examination1Activity.this,
                        Examination2Activity.class);
                startActivity(examination2acitivty);
                finish();
            }
        });
    }

    public void addSymptoms() {
        symptomList.add(new Symptom(1, "Wymioty"));
        symptomList.add(new Symptom(2, "Ból głowy"));
        symptomList.add(new Symptom(3, "Kaszel"));

    }
}
