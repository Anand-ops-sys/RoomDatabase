package play.gator.roomassignment.Interface;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import play.gator.roomassignment.Model.CourseModal;

@androidx.room.Dao
public interface Dao {

    @Insert
    void insert(CourseModal model);


    @Query("DELETE FROM course_table")
    void deleteAllCourses();

    @Query("SELECT * FROM course_table ORDER BY Name ASC")
    LiveData<List<CourseModal>> getAllCourses();




}
