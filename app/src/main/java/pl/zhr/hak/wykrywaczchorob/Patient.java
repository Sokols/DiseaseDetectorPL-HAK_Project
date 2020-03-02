package pl.zhr.hak.wykrywaczchorob;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Patient {

    @PrimaryKey
    private int patientID;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String surname;

    @ColumnInfo
    private int diseaseID;

    @Ignore
    public Patient(String name, String surname, int diseaseID) {
        this.name = name;
        this.surname = surname;
        this.diseaseID = diseaseID;
    }

    public Patient(int patientID, String name, String surname, int diseaseID) {
        this.patientID = patientID;
        this.name = name;
        this.surname = surname;
        this.diseaseID = diseaseID;
    }

    public int getPatientID() {
        return this.patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getDiseaseID() {
        return diseaseID;
    }

    public void setDiseaseID(int diseaseID) {
        this.diseaseID = diseaseID;
    }
}
