import java.util.ArrayList;
import java.util.HashMap;

public class Semester implements Comparable<Semester> {
    public static HashMap<Integer, ArrayList<Semester>> allSemesters = new HashMap<>();
    private final String name;
    private final int grade;
    private final int weight;
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
