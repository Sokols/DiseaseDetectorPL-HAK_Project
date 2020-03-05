package pl.zhr.hak.wykrywaczchorob;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Patient implements Parcelable {

    @PrimaryKey
    private int patientID;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String surname;

    @ColumnInfo
    private int diseaseID;

    @ColumnInfo
    private String PESEL;

    @ColumnInfo
    private String addedBy;

    @Ignore
    public Patient(String name, String surname, int diseaseID, String PESEL, String addedBy) {
        this.name = name;
        this.surname = surname;
        this.diseaseID = diseaseID;
        this.PESEL = PESEL;
        this.addedBy = addedBy;
    }

    public Patient(int patientID, String name, String surname, int diseaseID, String PESEL, String addedBy) {
        this.patientID = patientID;
        this.name = name;
        this.surname = surname;
        this.diseaseID = diseaseID;
        this.PESEL = PESEL;
        this.addedBy = addedBy;
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

    public String getPESEL() {
        return PESEL;
    }

    public void setPESEL(String PESEL) {
        this.PESEL = PESEL;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(patientID);
        out.writeString(name);
        out.writeString(surname);
        out.writeInt(diseaseID);
        out.writeString(PESEL);
        out.writeString(addedBy);
    }

    public static final Parcelable.Creator<Patient> CREATOR = new Parcelable.Creator<Patient>() {
        public Patient createFromParcel(Parcel in) {
            return new Patient(in);
        }

        public Patient[] newArray(int size) {
            return new Patient[size];
        }
    };

    private Patient(Parcel in) {
        patientID = in.readInt();
        name = in.readString();
        surname = in.readString();
        diseaseID = in.readInt();
        PESEL = in.readString();
        addedBy = in.readString();
    }


}
