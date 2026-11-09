package interpreter.view;

import interpreter.controller.Controller;
import interpreter.exceptions.MyException;
import interpreter.model.PrgState;
import interpreter.model.adt.MyDictionary;
import interpreter.model.adt.MyList;
import interpreter.model.adt.MyStack;
import interpreter.model.stmt.IStmt;
import interpreter.repository.IRepository;
import interpreter.repository.Repository;

import java.util.List;
import java.util.Scanner;

public class View {
    private final List<IStmt> programs;
    private Controller controller;

    public View(List<IStmt> programs) {
        this.programs = programs;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMENU ");
            System.out.println("1.Select a program");
            System.out.println("2.Run the selected program");
            System.out.println("0.Exit");
            System.out.print("Choose an option: ");

            String option = scanner.nextLine().trim();

            switch (option) {
                case "1" -> selectProgram(scanner);
                case "2" -> runProgram();
                case "0" -> {
                    System.out.println("Exiting application...");
                    return;
                }
                default -> System.out.println("Invalid option! Please enter 0, 1, or 2.");
            }
        }
    }

    private void selectProgram(Scanner scanner) {
        System.out.println("\nAvailable programs:");
        for (int i = 0; i < programs.size(); i++) {
            System.out.println((i + 1) + ". " + programs.get(i));
        }
        System.out.print("Enter program number: ");

        try {
            int choice = Integer.parseInt(scanner.nextLine().trim());
            if (choice < 1 || choice > programs.size()) {
                System.out.println("Invalid program number!");
                return;
            }

            IStmt selectedProgram = programs.get(choice - 1);

            PrgState prgState = PrgState.of(selectedProgram);
            IRepository repo = new Repository("log1.txt");
            this.controller = new Controller(repo);

            System.out.println("Program " + choice + " selected successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid integer number!");
        }
    }

    private void runProgram() {
        if (controller == null) {
            System.out.println("No program selected! Please select a program first (option 1).");
            return;
        }

        try {
            System.out.println("Executing program...");
            controller.allStep();
            System.out.println("Program executed successfully!");
        } catch (MyException e) {
            System.out.println("Execution Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected Error: " + e.getMessage());
        } finally {
            this.controller = null;
        }
    }
}