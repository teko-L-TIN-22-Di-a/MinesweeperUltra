package src.scenes;

import java.awt.Color;
import java.awt.Point;

import javax.sound.sampled.Clip;

import src.core.StaticValues;
import src.assets.Loader;
import src.assets.SoundMapping;
import src.components.Button;
import src.components.Textfield;

/**
 * Creates the menu screen.
 * Extends the Scene class.
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

        Button start = new Button(100, 50, width/2-50, height/2+300, "START", Color.GRAY);
        start.setSound(buttonSound);
        start.setAction(() -> {
            MineField mf = new MineField(gridSize.x, gridSize.y, mines);
            setNewScene(mf);
        });
        registerButton(start);

        Button exit = new Button(100, 50, width-125, 25, "EXIT", Color.GRAY);
        exit.setSound(buttonSound);
        exit.setAction(() -> {
            System.exit(0);
        });
        registerButton(exit);

        Button small = new Button(100, 50, width/2-150, height/2-150, "SMALL", Color.GRAY);
        small.setSound(buttonSound);
        small.setAction(() -> {
            gridSize = StaticValues.SMALL;
            sizeText = "SMALL";
            setMines();
        });
        registerButton(small);

        Button medium = new Button(100, 50, width/2-150, height/2-25, "MEDIUM", Color.GRAY);
        medium.setSound(buttonSound);
        medium.setAction(() -> {
            gridSize = StaticValues.MEDIUM;
            sizeText = "MEDIUM";
            setMines();
        });
        registerButton(medium);

        Button large = new Button(100, 50, width/2-150, height/2+100, "LARGE", Color.GRAY);
        large.setSound(buttonSound);
        large.setAction(() -> {
            gridSize = StaticValues.LARGE;
            sizeText = "LARGE";
            setMines();
        });
        registerButton(large);

        Button easy = new Button(100, 50, width/2+50, height/2-150, "EASY", Color.GRAY);
        easy.setSound(buttonSound);
        easy.setAction(() -> {
            difficulty = 0;
            difficultyText = "EASY";
            setMines();
        });
        registerButton(easy);

        Button intermediate = new Button(100, 50, width/2+50, height/2-25, "MEDIUM", Color.GRAY);
        intermediate.setSound(buttonSound);
        intermediate.setAction(() -> {
            difficulty = 1;
            difficultyText = "MEDIUM";
            setMines();
        });
        registerButton(intermediate);

        Button hard = new Button(100, 50, width/2+50, height/2+100, "HARD", Color.GRAY);
        hard.setSound(buttonSound);
        hard.setAction(() -> {
            difficulty = 2;
            difficultyText = "HARD";
            setMines();
        });
        registerButton(hard);
    }

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

    private void updateText() {
        sizeTextfield.setText(("SIZE: " + sizeText));
        difficultyTextfield.setText("DIFFICULTIY: " + difficultyText);
    }
}
