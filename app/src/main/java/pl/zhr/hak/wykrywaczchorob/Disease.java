package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Disease {
    private int diseaseID;
    private String diseaseName;
    private List<Integer> symptoms;
    private int symptomConfirm;

    public Disease(int diseaseID, String diseaseName, List<Integer> symptoms, int symptomConfirm) {
        this.diseaseID = diseaseID;
        this.diseaseName = diseaseName;
        this.symptoms = symptoms;
        this.symptomConfirm = symptomConfirm;
    }

    public int getDiseaseID() {
        return diseaseID;
    }

    public void setDiseaseID(int diseaseID) {
        this.diseaseID = diseaseID;
    }

    public String getDiseaseName() {
        return diseaseName;
    }

    public void setDiseaseName(String diseaseName) {
        this.diseaseName = diseaseName;
    }

    public List<Integer> getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(List<Integer> symptoms) {
        this.symptoms = symptoms;
    }

    public int getSymptomConfirm() {
        return this.symptomConfirm;
    }

    public void setSymptomConfirm (int symptomConfirm) { this.symptomConfirm = symptomConfirm; }

       public static List<Disease> getDiseases(Context context) {
        List<Disease> diseaseList = new ArrayList<>();
        diseaseList.add(new Disease(1, context.getString(R.string.coronavirus), fillSymptomList(1, 4, 5),0));
        diseaseList.add(new Disease(2, context.getString(R.string.food_poisoning), fillSymptomList(2, 8, 9), 0));
        diseaseList.add(new Disease(3, context.getString(R.string.flu), fillSymptomList(3, 6, 4), 0));
        diseaseList.add(new Disease(4, context.getString(R.string.angina), fillSymptomList(7, 6, 4), 0));
        diseaseList.add(new Disease(5, context.getString(R.string.cold), fillSymptomList(7, 1, 10), 0));
        return diseaseList;
    }

    public static List<Integer> fillSymptomList(int a, int b, int c) {
        List<Integer> symptomList = new ArrayList<>();
        symptomList.add(a);
        symptomList.add(b);
        symptomList.add(c);
        return symptomList;
    }



}
