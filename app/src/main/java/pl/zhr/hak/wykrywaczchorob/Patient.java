package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;
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
    private String sex;

    @ColumnInfo
    private int diseaseID;

    @ColumnInfo
    private int age;

    @ColumnInfo
    private String addedBy;

    @ColumnInfo
    private String date;

    @Ignore
    public Patient(String name, String surname, String sex, int diseaseID, int age, String addedBy, String date) {
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.diseaseID = diseaseID;
        this.age = age;
        this.addedBy = addedBy;
        this.date = date;
    }

    public Patient(int patientID, String name, String surname, String sex, int diseaseID, int age, String addedBy, String date) {
        this.patientID = patientID;
        this.name = name;
        this.surname = surname;
        this.sex = sex;
        this.diseaseID = diseaseID;
        this.age = age;
        this.addedBy = addedBy;
        this.date = date;
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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getDiseaseID() {
        return diseaseID;
    }

    public void setDiseaseID(int diseaseID) {
        this.diseaseID = diseaseID;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddedBy() {
        return addedBy;
    }

    public void setAddedBy(String addedBy) {
        this.addedBy = addedBy;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRealSex(Context context, String sex) {
        if (sex.equals("female")) {
            return context.getString(R.string.female);
        }
        else {
            return context.getString(R.string.male);
        }
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
        out.writeString(sex);
        out.writeInt(diseaseID);
        out.writeInt(age);
        out.writeString(addedBy);
        out.writeString(date);
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
        sex = in.readString();
        diseaseID = in.readInt();
        age = in.readInt();
        addedBy = in.readString();
        date = in.readString();
    }
}
