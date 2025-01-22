package student;

import java.util.Scanner;

public class StudentService {
    private static Scanner scanner;

    static {
        scanner = new Scanner(System.in);
    }

    private static int printMenu() {
        StudentDao oprs = new StudentDao();
        System.out.println("1: Insert 2: Delete 3: Update 4: Search 5: List All 6: Exit \t Your Choice: ");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1:
                oprs.insertStuDetails(); // Insert student details
                break;
            case 2:
                oprs.deleteStuDetails(); // Delete student details
                break;
            case 3:
                oprs.updateStuDetails(); // Update student details
                break;
            case 4:
                oprs.searchStuDetails(); // Search student details
                break;
            case 5:
                oprs.listAllStudents(); // List all students
                break;
            case 6:
                System.out.println("End of Program");
                choice = -1; // Exit the loop
                break;
            default:
                System.out.println("Invalid choice entered");
        }
        return choice;
    }

    private static void startApp() {
        int choice = 0;
        do {
            choice = printMenu();
        } while (choice != -1);
    }

    public static void main(String[] args) {
        startApp();
    }
}
