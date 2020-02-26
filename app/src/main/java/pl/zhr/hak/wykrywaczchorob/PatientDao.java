package pl.zhr.hak.wykrywaczchorob;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM Patient")
    LiveData<List<Patient>> getAll();

    @Query("SELECT * FROM Patient WHERE patientID = :id")
    LiveData<Patient> getItemById(int id);

    @Query("SELECT * FROM Patient WHERE surname LIKE :phrase")
    LiveData<List<Patient>> getBySurname(String phrase);

    @Insert
    void insert(Patient patient);

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Patient> badges);

    @Delete
    void delete(Patient patient);

    @Update
    void update(Patient patient);

    @Query("DELETE FROM Patient")
    void deleteAll();
}
