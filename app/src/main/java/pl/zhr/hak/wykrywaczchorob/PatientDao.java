package pl.zhr.hak.wykrywaczchorob;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM Patient")
    LiveData<List<Patient>> getAll();

    @Insert
    void insert(Patient patient);

    @Delete
    void delete(Patient patient);

    @Query("SELECT * FROM Patient WHERE addedBy = :addedBy")
    LiveData<List<Patient>> getItemsByAddedBy(String addedBy);
}
