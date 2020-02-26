package pl.zhr.hak.wykrywaczchorob;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    private String disease;

    public Patient(int patientID, String name, String surname, String disease) {
        this.patientID = patientID;
        this.name = name;
        this.surname = surname;
        this.disease = disease;
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

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }
}
