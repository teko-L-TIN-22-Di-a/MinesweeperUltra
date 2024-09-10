package src.scenes;

import java.awt.Color;

import javax.sound.sampled.Clip;

import src.core.StaticValues;
import src.assets.Loader;
import src.assets.SoundMapping;
import src.components.Button;

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
        int width = StaticValues.CANVAS_WIDTH;
        int height = StaticValues.CANVAS_HEIGHT;
        setBGM(SoundMapping.MENU);

        Clip buttonSound = Loader.loadSound(SoundMapping.BUTTON); 
        Button start = new Button(100, 50, width/2-50, height/2 - 100, "START", Color.GRAY);
        start.setSound(buttonSound);
        start.setAction(() -> {
            MineField mf = new MineField(30, 30, 200);
            setNewScene(mf);
        });
        Button exit = new Button(100, 50, width/2-50, height/2 + 100, "EXIT", Color.GRAY);
        exit.setSound(buttonSound);
        exit.setAction(() -> {
            System.exit(0);
        });
        registerButton(start);
        registerButton(exit);
    }
}
