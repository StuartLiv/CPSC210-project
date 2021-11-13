package ui;

public class Main {
    public static void main(String[] args) {
        boolean useGUI = true;
        if (useGUI) {
            new GraphicalUI();
        } else {
            new ConsoleUI();
        }
    }
}
