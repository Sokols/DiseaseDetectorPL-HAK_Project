package pl.zhr.hak.wykrywaczchorob;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM User WHERE login = :login")
    User getItemByName(String login);

    @Query("SELECT EXISTS (SELECT * FROM User WHERE login = :login)")
    int checkItemByName(String login);
}
