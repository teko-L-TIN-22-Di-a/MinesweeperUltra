package src.scenes;

import java.awt.Color;
import java.awt.Point;

import javax.sound.sampled.Clip;

import src.core.StaticValues;
import src.assets.Loader;
import src.assets.sounds.SoundMapping;
import src.components.Button;
import src.components.Textfield;

/**
 * Extends the Scene class to create a Menu.  
 * The Menu contains Buttons to set Size and Difficulty
 * and to start or exit the Game.
 * @see Button
 * @see Scene
 */
public class Menu extends Scene {

    private Point gridSize;
    private int mines, difficulty;
    private String sizeText, difficultyText;
    private Textfield sizeTextfield, difficultyTextfield;
    /**
     * Sets up the Menu scene.
     */
    public Menu() {
        super(true, "menu");
        int width = StaticValues.CANVAS_WIDTH;
        int height = StaticValues.CANVAS_HEIGHT;
        gridSize = StaticValues.SMALL;
        difficulty = 0;
        mines = StaticValues.DIFFICULTY[0][difficulty];
        setBGM(SoundMapping.MENU);
        sizeText = "SMALL";
        difficultyText = "EASY";
        
        sizeTextfield = new Textfield(width/2-150, height/2-200, "SIZE: " + sizeText);
        sizeTextfield.setColor(Color.WHITE);
        registerComponent(sizeTextfield);

        difficultyTextfield = new Textfield(width/2+50, height/2-200, "DIFFICULTIY: " + difficultyText);
        difficultyTextfield.setColor(Color.WHITE);
        registerComponent(difficultyTextfield);

        Clip buttonSound = Loader.loadSound(SoundMapping.BUTTON);

        Point buttonsize = StaticValues.BUTTONSIZE;

        Button start = new Button(buttonsize.x, buttonsize.y, width/2-150, height/2+300, "START", Color.GRAY);
        start.setSound(buttonSound);
        start.setAction(() -> {
            MineField mf = new MineField(gridSize.x, gridSize.y, mines, sizeText, difficultyText);
            setNewScene(mf);
        });
        registerButton(start);

        Button exit = new Button(buttonsize.x, buttonsize.y, width/2+50, height/2+300, "EXIT", Color.GRAY);
        exit.setSound(buttonSound);
        exit.setAction(() -> {
            System.exit(0);
        });
        registerButton(exit);

        Button small = new Button(buttonsize.x, buttonsize.y, width/2-150, height/2-150, "SMALL", Color.GRAY);
        small.setSound(buttonSound);
        small.setAction(() -> {
            gridSize = StaticValues.SMALL;
            sizeText = "SMALL";
            setMines();
        });
        registerButton(small);

        Button medium = new Button(buttonsize.x, buttonsize.y, width/2-150, height/2-25, "MEDIUM", Color.GRAY);
        medium.setSound(buttonSound);
        medium.setAction(() -> {
            gridSize = StaticValues.MEDIUM;
            sizeText = "MEDIUM";
            setMines();
        });
        registerButton(medium);

        Button large = new Button(buttonsize.x, buttonsize.y, width/2-150, height/2+100, "LARGE", Color.GRAY);
        large.setSound(buttonSound);
        large.setAction(() -> {
            gridSize = StaticValues.LARGE;
            sizeText = "LARGE";
            setMines();
        });
        registerButton(large);

        Button easy = new Button(buttonsize.x, buttonsize.y, width/2+50, height/2-150, "EASY", Color.GRAY);
        easy.setSound(buttonSound);
        easy.setAction(() -> {
            difficulty = 0;
            difficultyText = "EASY";
            setMines();
        });
        registerButton(easy);

        Button intermediate = new Button(buttonsize.x, buttonsize.y, width/2+50, height/2-25, "MEDIUM", Color.GRAY);
        intermediate.setSound(buttonSound);
        intermediate.setAction(() -> {
            difficulty = 1;
            difficultyText = "MEDIUM";
            setMines();
        });
        registerButton(intermediate);

        Button hard = new Button(buttonsize.x, buttonsize.y, width/2+50, height/2+100, "HARD", Color.GRAY);
        hard.setSound(buttonSound);
        hard.setAction(() -> {
            difficulty = 2;
            difficultyText = "HARD";
            setMines();
        });
        registerButton(hard);
    }

    /**
     * Uses the gridSize to determine the size of the grid
     * and the difficulty variable to load the corresponding
     * amount of mines from the difficulty settings in the StaticValues.
     * At the end the Textfields are updated with the new values.
     */
    private void setMines() {
        int size = 0;
        if (gridSize==StaticValues.MEDIUM) {
            size = 1;
        }
        else if (gridSize==StaticValues.LARGE) {
            size = 2;
        }
        mines = StaticValues.DIFFICULTY[size][difficulty];
        updateText();
    }

    /**
     * Uses the sizeText and difficultyText variables to update the
     * corresponding Textfields.
     */
    private void updateText() {
        sizeTextfield.setText(("SIZE: " + sizeText));
        difficultyTextfield.setText("DIFFICULTIY: " + difficultyText);
    }
}
