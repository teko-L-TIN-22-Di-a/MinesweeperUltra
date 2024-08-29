package src.scenes;

import java.awt.Color;
import java.awt.Point;

import src.assets.ImageMapping;
import src.assets.SoundMapping;
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
    private Field[] fields;

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

        registerButton(menu);
        registerButton(exit);

        //setBG(ImageMapping.MAP1);
        //setBGM(SoundMapping.LEVEL1BGM);
        
        grid = new Grid(width, height, mineCount);
        fields = new Field[width*height];
        int fieldSide = 30;
        int index = 0;

        for (int i = 0; i<height; i++) {
            for (int j = 0; j<width; j++) {
                Field newField = new Field(fieldSide, fieldSide, i*fieldSide+300, j*fieldSide+25);
                fields[index] = newField;
                index += 1;
                registerComponent(newField);
            }
        }
    }
}
