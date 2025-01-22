import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class GPA_Calculator {
    public static File saveData;
    public static void main(String[] args) throws IOException {
        saveData = new File("saveData.dat");
        saveData.createNewFile();

        Scanner fileReader = new Scanner(saveData);
        Scanner userInput = new Scanner(System.in);
        HashMap<Integer, ArrayList<Semester>> allSemesters = new HashMap<>();
        HashMap<Integer, ArrayList<Course>> allCourses = new HashMap<>();

        int totalCourses = FileEditor.readFromFile(); // READS FROM FILE

        // MAIN PROGRAM

        int command = 0;
        System.out.println("WELCOME" + ((totalCourses != 0) ? " BACK" : "") + " TO THE GPA CALCULATOR!\n");

        while (command != 7) {
            System.out.println("""
                    PICK A COMMAND:
                    1 - ADD A COURSE
                    2 - ALTER A COURSE
                    2 - REMOVE A COURSE
                    3 - VIEW ALL COURSES
                    4 - RESET ALL COURSES
                    5 - CALCULATE GPA
                    6 - EXIT THE PROGRAM""");
            command = userInput.nextInt(); userInput.nextLine();

            switch (command) {
                case 1:
                    addCourse(userInput, allSemesters, allCourses);
                    break;
                case 2:
                    // TODO: ADD THIS FEATURE
                    break;
                case 3:
                    removeCourse(userInput, allSemesters, allCourses);
                    break;
                case 4:
                    int maxLength = 0;

                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < allCourses.get(i).size(); j++) {
                            maxLength = Math.max(maxLength, allCourses.get(i).get(j).getName().length());
                        }
                    }

                    for (int key : allCourses.keySet()) {
                        switch (key) {
                            case 0 -> System.out.println("\nMATH COURSES:\n" + "-".repeat(13));
                            case 1 -> System.out.println("\nENGLISH COURSES:\n" + "-".repeat(16));
                            case 2 -> System.out.println("\nSCIENCE COURSES:\n" + "-".repeat(16));
                            case 3 -> System.out.println("\nSOCIAL STUDIES COURSES:\n" + "-".repeat(23));
                            case 4 -> System.out.println("\nFOREIGN LANGUAGE COURSES:\n" + "-".repeat(25));
                        }

                        if (allCourses.get(key).isEmpty()) {
                            System.out.println("NONE");
                        }

                        for (Course course : allCourses.get(key)) {
                            course.printCourse(maxLength + 5);
                        }
                    }

                    break;
                case 5:
                    for (int i = 0; i < 5; i++) {
                        allSemesters.put(i, new ArrayList<>());
                    }

                    System.out.println("SUCCESSFULLY REMOVED ALL COURSES!");
                    break;
                case 6:
                    calculateGPA(allSemesters);
                    break;
                case 7:
                    System.out.println("HAVE A GREAT DAY!");
                    break;
                default:
                    System.out.println("INVALID COMMAND, TRY AGAIN.");
                    break;
            }

            System.out.println();
        }

        FileEditor.writeToFile(); // WRITES TO FILE
    }

    public static void addCourse(Scanner userInput, HashMap<Integer, ArrayList<Semester>> allSemesters,
                                 HashMap<Integer, ArrayList<Course>> allCourses) {
        System.out.println("WHAT IS THE NAME OF THIS COURSE?");
        String name = userInput.nextLine();

        System.out.println("""
                            WHAT SUBJECT IS THIS COURSE? TYPE THE CORRESPONDING NUMBER.
                            0 - MATH
                            1 - ENGLISH
                            2 - SCIENCE
                            3 - SOCIAL STUDIES
                            4 - FOREIGN LANGUAGE""");
        int type = userInput.nextInt();

        System.out.println("HOW MANY SEMESTERS IS THIS COURSE?");
        int length = userInput.nextInt();

        while (length > 2 || length < 1) {
            System.out.println("INVALID NUMBER OF SEMESTERS, TRY AGAIN.");
            System.out.println("HOW MANY SEMESTERS IS THIS COURSE?");
            length = userInput.nextInt();
        }

        System.out.println("IS THIS AN REGULAR (0), ADVANCED (1), OR AP COURSE (2)?" +
                "\nANSWER WITH THE GIVEN NUMBER");
        int weight = userInput.nextInt();

        while (weight > 2 || weight < 0) {
            System.out.println("INVALID COURSE TYPE, TRY AGAIN.");
            System.out.println("IS THIS AN REGULAR (0), ADVANCED (1), OR AP COURSE (2)?" +
                    "\nANSWER WITH THE GIVEN NUMBER.");
            weight = userInput.nextInt(); userInput.nextLine();
        }

        int[] grades = new int[length];

        for (int i = 0; i < length; i++) {
            System.out.println("PROVIDE THE SEMESTER " + (i + 1) + " GRADE (AS A WHOLE NUMBER).");
            int grade = userInput.nextInt();
            grades[i] = grade;
        }

        for (int grade : grades) {
            allSemesters.get(type).add(new Semester(name, grade, weight));
        }

        if (grades.length == 1) {
            allCourses.get(type).add(new Course(name, weight, grades[0]));
        } else {
            allCourses.get(type).add(new Course(name, weight, grades[0], grades[1]));
        }

        System.out.println("COURSE CREATION SUCCESSFUL!");
    }

    public static void removeCourse(Scanner userInput, HashMap<Integer, ArrayList<Semester>> allSemesters,
                               HashMap<Integer, ArrayList<Course>> allCourses) {
        System.out.println("WHAT IS THE NAME OF THE COURSE YOU WOULD LIKE TO REMOVE?");
        String name = userInput.nextLine();

        for (int i = 0; i < 5; i++) {
            allSemesters.get(i).removeIf(semester -> semester.getName().equals(name));
            allCourses.get(i).removeIf(course -> course.getName().equals(name));
        }

        System.out.println("COURSE REMOVAL SUCCESSFUL!");
    }

    public static void calculateGPA(HashMap<Integer, ArrayList<Semester>> allSemesters) {
        double total = 0;
        int semesterCount = 0;

        for (int i = 0; i < 5; i++) {
            Collections.sort(allSemesters.get(i));
            Collections.reverse(allSemesters.get(i));
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < allSemesters.get(i).size() && j < 8; j++) {
                total += allSemesters.get(i).get(j).convertToGPA();
                semesterCount++;
            }
        }

        for (int j = 0; j < allSemesters.get(4).size() && j < 4; j++) {
            total += allSemesters.get(4).get(j).convertToGPA();
            semesterCount++;
        }

        if (semesterCount == 0) {
            System.out.println("YOUR GPA IS: 0.000");
        } else {
            System.out.printf("YOUR GPA IS: %.3f\n", (total / semesterCount));
        }
    }
}
