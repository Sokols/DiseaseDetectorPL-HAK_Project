package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Disease {
    private int diseaseID;
    private String diseaseName;

    public Disease(int diseaseID, String diseaseName) {
        this.diseaseID = diseaseID;
        this.diseaseName = diseaseName;
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

    public static List<Disease> getDiseases(Context context) {
        List<Disease> diseaseList = new ArrayList<>();
        diseaseList.add(new Disease(1, context.getString(R.string.coronavirus)));
        diseaseList.add(new Disease(2, context.getString(R.string.food_poisoning)));
        diseaseList.add(new Disease(3, context.getString(R.string.flu)));
        diseaseList.add(new Disease(4, context.getString(R.string.angina)));
        diseaseList.add(new Disease(5, context.getString(R.string.hypochondria)));
        return diseaseList;
    }
}
