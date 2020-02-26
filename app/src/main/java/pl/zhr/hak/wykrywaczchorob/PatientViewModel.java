package pl.zhr.hak.wykrywaczchorob;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PatientViewModel extends AndroidViewModel {

    private ExecutorService executorService;
    private PatientDao patientDao;

    public PatientViewModel(@NonNull Application application) {
        super(application);
        patientDao = AppDatabase.getInstance(application).patientDao();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<Patient>> getPatientList() {
        return patientDao.getAll();
    }

    public void insert(Patient patient) {
        executorService.execute(() -> patientDao.insert(patient));
    }

    public void update(Patient patient) {
        executorService.execute(() -> patientDao.update(patient));
    }

    public void delete(final Patient patient) {
        executorService.execute(() -> patientDao.delete(patient));
    }
}