import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileEditor {
    public static void writeToFile() throws IOException {
        FileWriter writer = new FileWriter(GPA_Calculator.saveData, false);

        for (int i = 0; i < 4; i++) {
            writer.append(String.valueOf(Course.allCourses.get(i).size())).append(" ");
        }
        writer.append(String.valueOf(Course.allCourses.get(4).size())).append("\n");

        for (int i = 0; i < 5; i++) {
            for (Course course : Course.allCourses.get(i)) {
                writer.append(String.valueOf(course)).append("\n");
            }
        }

        writer.close();
    }

    public static int readFromFile() throws IOException {
        Scanner fileReader = new Scanner(GPA_Calculator.saveData);

        if (!fileReader.hasNext()) {
            FileWriter temp = new FileWriter(GPA_Calculator.saveData);
            temp.write("0 0 0 0 0\n");
            temp.close();
        }

        for (int i = 0; i < 5; i++) {
            Semester.allSemesters.put(i, new ArrayList<>());
            Course.allCourses.put(i, new ArrayList<>());
        }

        int[] numEach = new int[5];
        int totalCourses = 0;

        for (int i = 0; i < 5; i++) {
            numEach[i] = fileReader.nextInt();
            totalCourses += numEach[i];
        }
        fileReader.nextLine();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < numEach[i]; j++) {
                String[] line = fileReader.nextLine().split(" ");
                Course course;
                if (line.length == 3) {
                    course = new Course(line[0].replaceAll("_", " "),
                            Integer.parseInt(line[1]), Integer.parseInt(line[2]));
                    Semester.allSemesters.get(i).add(course.getSemesters()[0]);
                } else {
                    course = new Course(line[0].replaceAll("_", " "),
                            Integer.parseInt(line[1]), Integer.parseInt(line[2]), Integer.parseInt(line[3]));
                    Semester.allSemesters.get(i).add(course.getSemesters()[0]);
                    Semester.allSemesters.get(i).add(course.getSemesters()[1]);
                }
                Course.allCourses.get(i).add(course);
            }
        }

        fileReader.close();
        return totalCourses;
    }
}
