import java.util.ArrayList;
import java.util.HashMap;

public class Semester implements Comparable<Semester> {
    public static HashMap<Integer, ArrayList<Semester>> allSemesters = new HashMap<>();
    private String name;
    private int grade;
    private int weight;

    public Semester(String n, int g, int w) {
        name = n;
        grade = g;
        weight = w;
    }

    public String getName() {
        return name;
    }

    public int getGrade() {
        return grade;
    }

    public int getAdjustedGrade() {
        return grade + (getWeight() * 5);
    }

    public int getWeight() {
        return weight;
    }

    public void setName(String n) {
        name = n;
    }

    public void setWeight(int w) {
        weight = w;
    }

    public void setGrade(int g) {
        grade = g;
    }

    public double convertToGPA() {
        if (grade < 70)
            return 0.0;
        return (getAdjustedGrade() / 10.0) - 5;
    }

    public int compareTo(Semester other) {
        return getAdjustedGrade() - other.getAdjustedGrade();
    }

    public String toString() {
        return name + " " + grade;
    }
}
