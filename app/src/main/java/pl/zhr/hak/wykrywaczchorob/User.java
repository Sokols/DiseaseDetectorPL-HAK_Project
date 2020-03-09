package pl.zhr.hak.wykrywaczchorob;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    private int userID;

    @ColumnInfo
    private String login;

    @ColumnInfo
    private String password;

    @Ignore
    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public User(int userID, String login, String password) {
        this.userID = userID;
        this.login = login;
        this.password = password;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
