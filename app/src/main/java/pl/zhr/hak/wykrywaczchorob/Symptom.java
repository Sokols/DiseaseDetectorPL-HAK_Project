package pl.zhr.hak.wykrywaczchorob;

public class Symptom {

    private int symptomID;
    private String symptomName;

    public Symptom(int symptomID, String symptomName) {
        this.symptomID = symptomID;
        this.symptomName = symptomName;
    }

    public void setSymptomID(int symptomID) {
        this.symptomID = symptomID;
    }

    public void setSymptomName(String symptomName) {
        this.symptomName = symptomName;
    }

    public int getSymptomID() {
        return this.symptomID;
    }

    public  String getSymptomName() {
        return this.symptomName;
    }
}
