package pl.zhr.hak.wykrywaczchorob;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    LiveData<List<User>> getAll();

    @Insert
    void insert (User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);

    @Query("Delete FROM User")
    void deleteAll();

    @Query("SELECT * FROM User WHERE login = :login")
    User getItemByName(String login);

    @Query("SELECT EXISTS (SELECT * FROM User WHERE login = :login)")
    int checkItemByName(String login);
}
