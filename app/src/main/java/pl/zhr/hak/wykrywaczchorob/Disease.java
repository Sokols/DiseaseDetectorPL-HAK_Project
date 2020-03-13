package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Disease {
    private int diseaseID;
    private String diseaseName;
    private int [] symptoms;

    public Disease(int diseaseID, String diseaseName, int [] symptoms) {
        this.diseaseID = diseaseID;
        this.diseaseName = diseaseName;
        this.symptoms = symptoms;
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

    public int[] getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(int[] symptoms) {
        this.symptoms = symptoms;
    }

    public static List<Disease> getDiseases(Context context) {
        List<Disease> diseaseList = new ArrayList<>();
        diseaseList.add(new Disease(1, context.getString(R.string.coronavirus), new int[] {1, 4, 5}));
        diseaseList.add(new Disease(2, context.getString(R.string.food_poisoning), new int[] {2, 8, 9}));
        diseaseList.add(new Disease(3, context.getString(R.string.flu), new int[] {3, 6, 4}));
        diseaseList.add(new Disease(4, context.getString(R.string.angina), new int[] {7, 6, 4}));
        diseaseList.add(new Disease(5, context.getString(R.string.cold), new int[] {7, 1, 10}));
        return diseaseList;
    }


}
