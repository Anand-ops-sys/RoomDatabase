package play.gator;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import play.gator.roomassignment.CourseRepository;
import play.gator.roomassignment.Model.CourseModal;

public class ViewModal extends AndroidViewModel {

    private CourseRepository repository;

    private LiveData<List<CourseModal>> allCourses;


    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new CourseRepository(application);
        allCourses = repository.getAllCourses();
    }

    public void insert(CourseModal model) {
        repository.insert(model);
    }

    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    public LiveData<List<CourseModal>> getAllCourses() {
        return allCourses;
    }
}
