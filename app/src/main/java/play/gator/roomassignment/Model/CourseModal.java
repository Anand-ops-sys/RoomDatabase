package play.gator.roomassignment.Model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "course_table")
public class CourseModal {

    @PrimaryKey(autoGenerate = true)



    private int id;
    private String Name;
    private String Capital;
    private String Flag;
    private String Region,Subregion,population,border,language;

    public CourseModal() {
    }

    public CourseModal(String name, String capital, String flag, String region, String subregion, String population, String border, String language) {

        this.Name = name;
        this.Capital = capital;
        this.Flag = flag;
        this.Region = region;
        this.Subregion = subregion;
        this.population = population;
        this.border = border;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCapital() {
        return Capital;
    }

    public void setCapital(String capital) {
        Capital = capital;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getSubregion() {
        return Subregion;
    }

    public void setSubregion(String subregion) {
        Subregion = subregion;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}


