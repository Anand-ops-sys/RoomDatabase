package play.gator.roomassignment.Database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import play.gator.roomassignment.Interface.Dao;
import play.gator.roomassignment.Model.CourseModal;

// adding annotation for our database entities and db version.
@Database(entities = {CourseModal.class}, version = 4)
public abstract class CourseDatabase extends RoomDatabase {

    private static CourseDatabase instance;

    public abstract Dao Dao();

    public static synchronized CourseDatabase getInstance(Context context) {

        if (instance == null) {

            instance =

                    Room.databaseBuilder(context.getApplicationContext(),
                            CourseDatabase.class, "course_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(roomCallback)
                            .build();
        }
        // after creating an instance
        // we are returning our instance
        return instance;
    }

    // below line is to create a callback for our room database.
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        PopulateDbAsyncTask(CourseDatabase instance) {
            Dao dao = instance.Dao();
        }
        @Override
        protected Void doInBackground(Void... voids) {
            return null;
        }
    }
}
