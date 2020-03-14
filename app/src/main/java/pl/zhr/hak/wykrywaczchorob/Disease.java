package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class Disease {
    private int diseaseID;
    private String diseaseName;
    private int[] symptoms;
    private Boolean[] symptomConfirm;

    public Disease(int diseaseID, String diseaseName, int[] symptoms, Boolean[] symptomConfirm) {
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

    public int[] getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(int[] symptoms) {
        this.symptoms = symptoms;
    }

    public Boolean[] getSymptomConfirm() {
        return symptomConfirm;
    }

    public void setSymptomConfirm(Boolean[] symptomConfirm) {
        this.symptomConfirm = symptomConfirm;
    }

    public void setSymptomConfirmByPosition(int position, Boolean set) { this.symptomConfirm[position] = set; }

    public static List<Disease> getDiseases(Context context) {
        List<Disease> diseaseList = new ArrayList<>();
        Boolean[] negative = new Boolean[] {false, false, false};
        diseaseList.add(new Disease(1, context.getString(R.string.coronavirus), new int[] {1, 4, 5}, negative));
        diseaseList.add(new Disease(2, context.getString(R.string.food_poisoning), new int[] {2, 8, 9}, negative));
        diseaseList.add(new Disease(3, context.getString(R.string.flu), new int[] {3, 6, 4}, negative));
        diseaseList.add(new Disease(4, context.getString(R.string.angina), new int[] {7, 6, 4}, negative));
        diseaseList.add(new Disease(5, context.getString(R.string.cold), new int[] {7, 1, 10}, negative));
        return diseaseList;
    }



}
