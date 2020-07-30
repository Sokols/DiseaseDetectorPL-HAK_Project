package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Symptom {

    private int symptomID;
    private String symptomName;
    private Boolean isChecked;

    private Symptom(int symptomID, String symptomName, Boolean isChecked) {
        this.symptomID = symptomID;
        this.symptomName = symptomName;
        this.isChecked = isChecked;
    }

    public int getSymptomID() {
        return this.symptomID;
    }

    String getSymptomName() {
        return this.symptomName;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public static List<Symptom> getSymptoms(Context context) {
        List<Symptom> symptomList = new ArrayList<>();
        symptomList.add(new Symptom(1, context.getString(R.string.cough), false));
        symptomList.add(new Symptom(2, context.getString(R.string.vomiting), false));
        symptomList.add(new Symptom(3, context.getString(R.string.headache), false));
        symptomList.add(new Symptom(4, context.getString(R.string.fever), false));
        symptomList.add(new Symptom(5, context.getString(R.string.dyspnoea), false));
        symptomList.add(new Symptom(6, context.getString(R.string.chills), false));
        symptomList.add(new Symptom(7, context.getString(R.string.sore_throat), false));
        symptomList.add(new Symptom(8, context.getString(R.string.diarrhea), false));
        symptomList.add(new Symptom(9, context.getString(R.string.stomach_ache), false));
        symptomList.add(new Symptom(10, context.getString(R.string.runny_nose), false));
        return symptomList;
    }
}
