import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GPA_Calculator {
    public static File saveData;
    public static void main(String[] args) throws IOException {
        saveData = new File("saveData.dat");
        saveData.createNewFile();

        Scanner userInput = new Scanner(System.in);

        int totalCourses = FileEditor.readFromFile(); // READS FROM FILE

        // MAIN PROGRAM

        int command = 0;
        System.out.println("WELCOME" + ((totalCourses != 0) ? " BACK" : "") + " TO THE GPA CALCULATOR!\n");

        while (command != 7) {
            System.out.println("""
                    PICK A COMMAND:
                    1 - ADD A COURSE
                    2 - ALTER A COURSE
                    3 - REMOVE A COURSE
                    4 - VIEW ALL COURSES
                    5 - RESET ALL COURSES
                    6 - CALCULATE GPA
                    7 - EXIT THE PROGRAM""");
            command = userInput.nextInt(); userInput.nextLine();

            switch (command) {
                case 1:
                    AllCommands.addCourse(userInput);
                    FileEditor.writeToFile(); // WRITES TO FILE
                    break;
                case 2:
                    // TODO: ADD THIS FEATURE
                    FileEditor.writeToFile(); // WRITES TO FILE
                    break;
                case 3:
                    AllCommands.removeCourse(userInput);
                    FileEditor.writeToFile(); // WRITES TO FILE
                    break;
                case 4:
                    AllCommands.printCourseList();
                    break;
                case 5:
                    for (int i = 0; i < 5; i++) {
                        Semester.allSemesters.put(i, new ArrayList<>());
                    }
                    FileEditor.writeToFile(); // WRITES TO FILE
                    System.out.println("SUCCESSFULLY REMOVED ALL COURSES!");
                    break;
                case 6:
                    AllCommands.calculateGPA();
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
    }
}
