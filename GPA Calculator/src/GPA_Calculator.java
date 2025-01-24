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
            command = AllCommands.printCommands(userInput);
            userInput.nextLine();

            switch (command) {
                case 1:
                    AllCommands.addCourse(userInput);
                    FileEditor.writeToFile(); // WRITES TO FILE
                    break;
                case 2:
                    AllCommands.alterCourse(userInput);
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
                        Course.allCourses.put(i, new ArrayList<>());
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
