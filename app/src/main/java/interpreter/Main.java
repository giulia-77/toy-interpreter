package interpreter;

import interpreter.view.Examples;
import interpreter.view.View;

public class Main {
    public static void main(String[] args) {
        View view = new View(Examples.getAll());
        view.start();
    }
}