package src.scenes;

import java.awt.Color;
import java.awt.Point;

import src.components.Button;
import src.components.Field;
import src.components.Grid;
import src.core.StaticValues;


/**
 * Creates the level1 screen.
 * Extends the Scene class.
 * @see Bow
 * @see Scene
 */
public class MineField extends Scene {
    
    private Grid grid;

    /**
     * Sets up the Level1 scene.
     * @see Monster
     * @see Bow
     */
    public MineField(int width, int height, int mineCount) {
        super(false, "minefield");
        Point windowSize = new Point(StaticValues.CANVAS_WIDTH, StaticValues.CANVAS_HEIGHT);

        Button menu = new Button(100, 50, 25, 25, "MENU", Color.GRAY);
        menu.setAction(() -> {
            Menu m = new Menu();
            setNewScene(m);
        });
        Button exit = new Button(100, 50, windowSize.x-125, 25 , "EXIT", Color.GRAY);
        exit.setAction(() -> {
            System.exit(0);
        });        
        Button action1 = new Button(100, 50, 25, windowSize.y/2 - 75, "ACTION1", Color.GRAY);
        action1.setAction(() -> {
            action1();
        });
        Button action2 = new Button(100, 50, 25, windowSize.y/2 , "ACTION2", Color.GRAY);
        action2.setAction(() -> {
            action2();
        });
        Button action3 = new Button(100, 50, 25, windowSize.y/2 + 75 , "ACTION3", Color.GRAY);
        action3.setAction(() -> {
            action3();
        });

        registerButton(menu);
        registerButton(exit);
        registerButton(action1);
        registerButton(action2);
        registerButton(action3);
        
        grid = new Grid(width, height, mineCount);

        for (Field f: grid.getAllFields()) {
            addField(f);
        }
    }

    private void action1() {
        System.out.println("Execute Action 1!");
    }

    private void action2() {
        System.out.println("Execute Action 2!");
    }

    private void action3() {
        System.out.println("Execute Action 3!");
    }
}
