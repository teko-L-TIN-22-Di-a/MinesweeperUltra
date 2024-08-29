package src.scenes;

import java.awt.Color;

import src.assets.ImageMapping;
import src.assets.SoundMapping;
import src.core.StaticValues;
import src.components.Button;
import src.components.Grid;

/**
 * Creates the menu screen.
 * Extends the Scene class.
 * @see Button
 * @see Scene
 */
public class Menu extends Scene {
    /**
     * Sets up the Menu scene.
     */
    public Menu() {
        super(true, "menu");
        setBG(ImageMapping.BG);
        //setBGM(SoundMapping.MENUBGM);
        int width = StaticValues.CANVAS_WIDTH;
        int height = StaticValues.CANVAS_HEIGHT;
        Button start = new Button(100, 50, width/2, height/2 - 100, "START", Color.GRAY);
        start.setAction(() -> {
            MineField l = new MineField(10, 10, 15);
            setNewScene(l);
        });
        Button exit = new Button(100, 50, width/2, height/2 + 100, "EXIT", Color.GRAY);
        exit.setAction(() -> {
            System.exit(0);
        });
        registerButton(start);
        registerButton(exit);
    }
}
