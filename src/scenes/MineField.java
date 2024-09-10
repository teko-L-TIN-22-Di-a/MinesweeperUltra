package src.scenes;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;

import src.assets.ImageMapping;
import src.assets.Loader;
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
    private int mineCount;
    private int fieldCount;
    private int endCount;
    private boolean end;
    private boolean removeEndscreenImage;
    private Button removeEndscreen;
    private List<Button> otherButtons;

    /**
     * Takes a width, height and mine count to create a Minefield Scene.  
     * Width and height define the size of the Grid. Minecount defines,
     * how many Mines will be in the Minefield.  
     * The contstructor sets up all neccessary variables and Buttons.
     * @param width
     * @param height
     * @param mineCount
     */
    public MineField(int width, int height, int mineCount) {
        super(false, "minefield");
        Point windowSize = new Point(StaticValues.CANVAS_WIDTH, StaticValues.CANVAS_HEIGHT);
        this.mineCount = mineCount;
        this.fieldCount = width*height;
        this.removeEndscreenImage = false;
        this.endCount = 0;
        this.otherButtons = new ArrayList<>();
        setBGM(SoundMapping.MINEFIELD);

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

        BufferedImage image = Loader.loadImage(ImageMapping.LOSE);
        int x = (StaticValues.CANVAS_WIDTH - image.getWidth())/2;
        int y = (StaticValues.CANVAS_HEIGHT - image.getHeight())/2;
        
        removeEndscreen = new Button(x, y, image);
        removeEndscreen.setAction(() -> {
            removeEndscreenImage = true;
        });

        otherButtons.add(menu);
        otherButtons.add(exit);
        otherButtons.add(action1);
        otherButtons.add(action2);
        otherButtons.add(action3);
        
        Clip buttonSound = Loader.loadSound(SoundMapping.BUTTON);
        for (Button b: otherButtons) {
            b.setSound(buttonSound);
        }
        registerOtherButtons();
        
        grid = new Grid(width, height, mineCount);

        for (Field f: grid.getAllFields()) {
            addField(f);
        }
    }

    /**
     * Action for the first special Ability.
     */
    private void action1() {
        System.out.println("Execute Action 1!");
    }

    /**
     * Action for the second special Ability.
     */
    private void action2() {
        System.out.println("Execute Action 2!");
    }

    /**
     * Action for the third special Ability.
     */
    private void action3() {
        System.out.println("Execute Action 3!");
    }

    /**
     * Takes a boolean that indicates, if the game was won or lost.  
     * Sets the end variable to true, to indicate, that the Game has ended.  
     * Reveils all Fields and changes the image from lose to win, if the Game was won.
     * @param win boolean to indicate win or loss
     */
    private void end(boolean win) {
        end = true;
        endCount = getCounter();
        grid.reveilAll();

        if (win) {
            BufferedImage image = Loader.loadImage(ImageMapping.WIN);
            removeEndscreen.setImage(image);
        }
    }

    /**
     * Registers all Buttons added to the otherButtons List.
     */
    private void registerOtherButtons() {
        for (Button b: otherButtons) {
            registerButton(b);
        }
    }

    /**
     * Unregisters all Buttons added to the otherButtons List.
     */
    private void unregisterOtherButtons() {
        for (Button b: otherButtons) {
            unregisterButton(b);
        }
    }

    /**
     * Calls the basic update Method and adds funcionalities specific to the Minefield.  
     * Registers the endscreen Button and unregisters all other Buttons, when the Game ended.  
     * Unregisters the endscreen Button and registers all other Buttons, after the endscreen
     * Button has been used.  
     * Checks, if either a Mine was reveiled or if the game has been won.
     */
    @Override
    public void update() {
        super.update();
        if (removeEndscreenImage) {
            unregisterButton(removeEndscreen);
            registerOtherButtons();
            removeEndscreenImage = false;
        }
        if (endCount!=0) {
            if (getCounter() == endCount + 40 ) {
                registerButton(removeEndscreen);
                unregisterOtherButtons();
                endCount = 0;
            }
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
