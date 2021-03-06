package gui;

import javafx.scene.paint.Color;

public abstract class GuiSimple {
    public void setup() {}
    abstract public void draw();

    // Exclude _ prefix because I think that could
    // confuse the freshmen
    // These will be given values in Master.start
    public Screen screen;
    public Mouse mouse;
    public Keyboard keyboard;

    protected static void begin(GuiSimple prgm) {
        Master.begin(prgm);
    }
}
