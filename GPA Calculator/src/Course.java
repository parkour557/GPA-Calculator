import java.util.ArrayList;
import java.util.HashMap;

public class Course {
    public static HashMap<Integer, ArrayList<Course>> allCourses = new HashMap<>();
    private String name;
    private int weight;
    private Semester first;
    private Semester second;

    public Course(String n, int w, int f, int s) {
        name = n;
        weight = w;
        first = new Semester(n, f, w);
        second = new Semester(n, s, w);
    }

    public Course(String n, int w, int f) {
        name = n;
        weight = w;
        first = new Semester(n, f, w);
        second = null;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public Semester[] getSemesters() {
        return new Semester[]{first, second};
    }

    public void setName(String n) {
        name = n;
        first.setName(n);
        if (second != null)
            second.setName(n);
    }

    public void setWeight(int w) {
        weight = w;
        first.setWeight(w);
        if (second != null)
            second.setWeight(w);
    }

    public void setSemesters(Semester f) {
        first = f;
        second = null;
    }

    public void setSemesters(Semester f, Semester s) {
        first = f;
        second = s;
    }

    public void printCourse(int maxLength) {
        System.out.print(name + " ".repeat(maxLength - name.length()));
        System.out.printf("%3d", first.getGrade());
        System.out.println((second != null) ? " " + String.format("%3d", second.getGrade()) : "");
    }

    public String toString() {
        if (second == null)
            return name.replaceAll(" ", "_") + " " + weight + " " + first.getGrade();
        return name.replaceAll(" ", "_") + " " + weight + " " + first.getGrade() + " " + second.getGrade();
    }
}
