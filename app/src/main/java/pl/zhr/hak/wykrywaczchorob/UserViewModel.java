package pl.zhr.hak.wykrywaczchorob;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UserViewModel extends AndroidViewModel {

    private ExecutorService executorService;
    private UserDao userDao;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userDao = AppDatabase.getInstance(application).userDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<User>> getUserList() {
        return userDao.getAll();
    }

    public void insert(User user) {
        executorService.execute(() -> userDao.insert(user));
    }

    public void update(User user) {
        executorService.execute(() -> userDao.update(user));
    }

    public void delete(User user) {
        executorService.execute(() -> userDao.delete(user));
    }

    public void deleteAll() {
        executorService.execute(() -> userDao.deleteAll());
    }

    public User getItemByName(String login) { return userDao.getItemByName(login); }

    public int checkItemByName(String login) {
        return userDao.checkItemByName(login);
    }
}
