package interpreter;

import interpreter.controller.Controller;
import interpreter.model.PrgState;
import interpreter.model.stmt.IStmt;
import interpreter.repository.Repository;
import interpreter.view.Examples;
import interpreter.view.ExitCommand;
import interpreter.view.RunExampleCommand;
import interpreter.view.TextMenu;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<IStmt> programs = Examples.getAll();

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));

        for (int i = 0; i < programs.size(); i++) {
            IStmt program = programs.get(i);
            var repo = new Repository("log" + (i + 1) + ".txt");
            PrgState prgState = PrgState.of(program);
            repo.addPrg(prgState);

            var controller = new Controller(repo);
            menu.addCommand(new RunExampleCommand(
                    String.valueOf(i + 1),
                    program.toString(),
                    controller
            ));
        }

        menu.show();
    }
}