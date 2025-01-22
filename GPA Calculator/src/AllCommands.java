import java.util.Collections;
import java.util.Scanner;

public class AllCommands {
    public static int printCommands(Scanner userInput) {
        System.out.println("""
                    PICK A COMMAND:
                    1 - ADD A COURSE
                    2 - ALTER A COURSE
                    3 - REMOVE A COURSE
                    4 - VIEW ALL COURSES
                    5 - RESET ALL COURSES
                    6 - CALCULATE GPA
                    7 - EXIT THE PROGRAM""");
        return userInput.nextInt();
    }

    public static void addCourse(Scanner userInput) {
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
            Semester.allSemesters.get(type).add(new Semester(name, grade, weight));
        }

        if (grades.length == 1) {
            Course.allCourses.get(type).add(new Course(name, weight, grades[0]));
        } else {
            Course.allCourses.get(type).add(new Course(name, weight, grades[0], grades[1]));
        }

        System.out.println("COURSE CREATION SUCCESSFUL!");
    }

    public static void removeCourse(Scanner userInput) {
        System.out.println("WHAT IS THE NAME OF THE COURSE YOU WOULD LIKE TO REMOVE?");
        String name = userInput.nextLine();

        for (int i = 0; i < 5; i++) {
            Semester.allSemesters.get(i).removeIf(semester -> semester.getName().equals(name));
            Course.allCourses.get(i).removeIf(course -> course.getName().equals(name));
        }

        System.out.println("COURSE REMOVAL SUCCESSFUL!");
    }

    public static void printCourseList() {
        int maxLength = 0;

        for (int i = 0; i < 5; i++) {
            for (Course course : Course.allCourses.get(i)) {
                maxLength = Math.max(maxLength, course.getName().length());
            }
        }

        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0 -> System.out.println("\nMATH COURSES:\n" + "-".repeat(13));
                case 1 -> System.out.println("\nENGLISH COURSES:\n" + "-".repeat(16));
                case 2 -> System.out.println("\nSCIENCE COURSES:\n" + "-".repeat(16));
                case 3 -> System.out.println("\nSOCIAL STUDIES COURSES:\n" + "-".repeat(23));
                case 4 -> System.out.println("\nFOREIGN LANGUAGE COURSES:\n" + "-".repeat(25));
            }

            if (Course.allCourses.get(i).isEmpty()) {
                System.out.println("NONE");
            }
            for (Course course : Course.allCourses.get(i)) {
                course.printCourse(maxLength + 5);
            }
        }
    }

    public static void calculateGPA() {
        double total = 0;
        int semesterCount = 0;

        for (int i = 0; i < 5; i++) {
            Collections.sort(Semester.allSemesters.get(i));
            Collections.reverse(Semester.allSemesters.get(i));
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Semester.allSemesters.get(i).size() && j < 8; j++) {
                total += Semester.allSemesters.get(i).get(j).convertToGPA();
                semesterCount++;
            }
        }

        for (int j = 0; j < Semester.allSemesters.get(4).size() && j < 4; j++) {
            total += Semester.allSemesters.get(4).get(j).convertToGPA();
            semesterCount++;
        }

        if (semesterCount == 0) {
            System.out.println("YOUR GPA IS: 0.000");
        } else {
            System.out.printf("YOUR GPA IS: %.3f\n", (total / semesterCount));
        }
    }
}
