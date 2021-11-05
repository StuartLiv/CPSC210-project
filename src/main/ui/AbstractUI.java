package ui;

public abstract class AbstractUI {

    //EFFECTS: runs UI
    public AbstractUI() {
        runUI();
    }

    protected abstract void runUI();
}
