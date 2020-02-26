package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {
                Patient.class
        },
        version = 1
)

public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "WykrywaczChorob"
            ).allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() { INSTANCE = null; }

    public abstract PatientDao patientDao();
}
