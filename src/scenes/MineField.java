package src.scenes;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;

import src.assets.Loader;
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
    private int mineCount;
    private int fieldCount;
    private boolean end;
    private boolean removeEndImage;
    private Button removeEnd;

    /**
     * Sets up the Level1 scene.
     * @see Monster
     * @see Bow
     */
    public MineField(int width, int height, int mineCount) {
        super(false, "minefield");
        Point windowSize = new Point(StaticValues.CANVAS_WIDTH, StaticValues.CANVAS_HEIGHT);
        this.mineCount = mineCount;
        this.fieldCount = width*height;
        this.removeEndImage = false;

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

        String imageName = "lose.png";
        BufferedImage image = Loader.loadImage(imageName);
        int x = (StaticValues.CANVAS_WIDTH - image.getWidth())/2;
        int y = (StaticValues.CANVAS_HEIGHT - image.getHeight())/2;
        
        removeEnd = new Button(x, y, image);
        removeEnd.setAction(() -> {
            removeEndImage = true;
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

    private void end(boolean win) {
        end = true;
        grid.reveilAll();

        if (win) {
            String imageName = "win.png";
            BufferedImage image = Loader.loadImage(imageName);
            removeEnd.setImage(image);
        }
        registerButton(removeEnd);
    }

    @Override
    public void update() {
        super.update();
        if (removeEndImage) {
            unregisterButton(removeEnd);
        }
        int reveiledFields = mineCount;
        if (!end) {
            for (Field f: getFields()) {
                if (StaticValues.FieldState.REVEILED == f.getState()) {
                    reveiledFields += 1;
                    if (9 == f.getValue()) {
                        end(false);
                    }
                }
            }
            if (reveiledFields == fieldCount) {
                end(true);
            }
        }
    }
}
