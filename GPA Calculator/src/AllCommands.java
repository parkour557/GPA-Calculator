import java.util.ArrayList;
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

    public static void alterCourse(Scanner userInput) {
        System.out.println("WHAT IS THE NAME OF THE COURSE YOU WOULD LIKE TO ALTER?");
        String name = userInput.nextLine();

        ArrayList<Course> foundCourses = new ArrayList<>();

        while (true) {
            boolean foundSomething = false;

            for (int i = 0; i < 5; i++) {
                for (Course course : Course.allCourses.get(i)) {
                    if (name.equals(course.getName())) {
                        foundCourses.add(course);
                        foundSomething = true;
                    }
                }
            }

            if (foundSomething)
                break;

            System.out.println("COURSE NOT FOUND. TRY AGAIN.");
            name = userInput.nextLine();
        }

        if (foundCourses.size() > 1) {
            System.out.println("""
                    THIS NAME CORRESPONDS TO MULTIPLE COURSES.
                    WHICH COURSE WOULD YOU LIKE TO PICK? (SELECT THE CORRESPONDING NUMBER)""");

            int maxLength = 0;

            for (Course course : foundCourses) {
                maxLength = Math.max(maxLength, course.getName().length());
            }
            for (int i = 0; i < foundCourses.size(); i++) {
                System.out.println(i + "\t");
                foundCourses.get(i).printCourse(maxLength);
            }

            int choice = userInput.nextInt();

            foundCourses.set(0, foundCourses.get(choice));
            while (foundCourses.size() > 1) {
                foundCourses.removeLast();
            }
        }

        Course foundCourse = foundCourses.getFirst();
        Semester firstFoundSemester = foundCourse.getSemesters()[0];
        Semester secondFoundSemester = foundCourse.getSemesters()[1];

        System.out.println("""
                COURSE ALTERATION MENU:
                0 - CHANGE NAME
                1 - CHANGE TYPE (NORMAL, ADV, AP)
                2 - CHANGE NUMBER OF SEMESTERS
                3 - CHANGE FIRST SEMESTER GRADE
                4 - CHANGE SECOND SEMESTER GRADE
                5 - EXIT MENU""");
        int choice = userInput.nextInt();

        while (choice != 5) {
            switch (choice) {
                case 0:
                    System.out.println("WHAT WOULD YOU LIKE THE NEW NAME TO BE?");
                    userInput.nextLine();
                    name = userInput.nextLine();

                    foundCourse.setName(name);
                    firstFoundSemester.setName(name);
                    if (secondFoundSemester != null) {
                        secondFoundSemester.setName(name);
                    }
                    System.out.println("NAME CHANGED SUCCESSFULLY!");
                    break;
                case 1:
                    System.out.println("""
                            WHAT WOULD YOU LIKE THE NEW COURSE TYPE TO BE?
                            0 - NORMAL
                            1 - ADVANCED
                            2 - AP
                            """);
                    int type = userInput.nextInt();

                    while (type > 2 || type < 0) {
                        System.out.println("""
                            INVALID TYPE, TRY AGAIN.
                            WHAT WOULD YOU LIKE THE NEW COURSE TYPE TO BE?
                            0 - NORMAL
                            1 - ADVANCED
                            2 - AP
                            """);
                        type = userInput.nextInt();
                    }

                    foundCourse.setWeight(type);
                    firstFoundSemester.setWeight(type);
                    if (secondFoundSemester != null) {
                        secondFoundSemester.setWeight(type);
                    }
                    System.out.println("TYPE CHANGED SUCCESSFULLY!");
                    break;
                case 2:
                    System.out.println("HOW MANY SEMESTERS DO YOU WANT THIS CLASS TO BE?");
                    int length = userInput.nextInt();

                    while (length > 2 || length < 1) {
                        System.out.println("INVALID LENGTH, TRY AGAIN.");
                        System.out.println("HOW MANY SEMESTERS DO YOU WANT THIS CLASS TO BE?");
                        length = userInput.nextInt();
                    }

                    while ((length == 2 && secondFoundSemester != null) || (length == 1 && secondFoundSemester == null)) {
                        System.out.println("THAT IS THE CURRENT NUMBER OF SEMESTERS. TRY AGAIN.");
                        System.out.println("HOW MANY SEMESTERS DO YOU WANT THIS CLASS TO BE?");
                        length = userInput.nextInt();
                    }

                    if (length == 2) {
                        System.out.println("WHAT IS YOUR FIRST SEMESTER GRADE?");
                        firstFoundSemester.setGrade(userInput.nextInt());

                        System.out.println("WHAT IS YOUR SECOND SEMESTER GRADE?");
                        secondFoundSemester = new Semester(foundCourse.getName(), userInput.nextInt(), foundCourse.getWeight());

                        foundCourse.setSemesters(firstFoundSemester, secondFoundSemester);
                        for (int i = 0; i < 5; i++) {
                            for (Semester semester : Semester.allSemesters.get(i)) {
                                if (firstFoundSemester.equals(semester)) {
                                    Semester.allSemesters.get(i).add(secondFoundSemester);
                                }
                            }
                        }
                    } else {
                        System.out.println("WHAT IS THE SEMESTER GRADE?");
                        firstFoundSemester.setGrade(userInput.nextInt());

                        foundCourse.setSemesters(firstFoundSemester);
                        for (int i = 0; i < 5; i++) {
                            Semester.allSemesters.get(i).remove(secondFoundSemester);
                        }
                        secondFoundSemester = null;
                    }
                    break;
                case 3:
                    System.out.println("WHAT IS THE NEW FIRST SEMESTER GRADE?");
                    firstFoundSemester.setGrade(userInput.nextInt());
                    break;
                case 4:
                    if (secondFoundSemester == null) {
                        System.out.println("SECOND SEMESTER DOES NOT EXIST. TRY AGAIN.");
                    } else {
                        System.out.println("WHAT IS THE SECOND SEMESTER GRADE?");
                        secondFoundSemester.setGrade(userInput.nextInt());
                    }
                    break;
                default:
                    System.out.println("NOT A VALID COMMAND. TRY AGAIN.");
                    break;
            }

            System.out.println("""
                COURSE ALTERATION MENU:
                0 - CHANGE NAME
                1 - CHANGE TYPE (NORMAL, ADV, AP)
                2 - CHANGE NUMBER OF SEMESTERS
                3 - CHANGE FIRST SEMESTER GRADE
                4 - CHANGE SECOND SEMESTER GRADE
                5 - EXIT MENU""");
            choice = userInput.nextInt();
        }
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
