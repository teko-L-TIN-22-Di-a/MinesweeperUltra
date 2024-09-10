package src.scenes;

import java.awt.Color;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.sound.sampled.Clip;

import src.assets.ImageMapping;
import src.assets.Loader;
import src.assets.SoundMapping;
import src.components.Button;
import src.components.Field;
import src.components.Grid;
import src.components.Textfield;
import src.core.StaticValues;
import src.core.StaticValues.FieldState;
import src.core.StaticValues.Mode;


/**
 * Creates the level1 screen.
 * Extends the Scene class.
 * @see Bow
 * @see Scene
 */
public class MineField extends Scene {
    
    private Grid grid;
    private int mineCount;
    private int fieldCount, reveiledFields;
    private int sleepCounter;
    private int endCount, shield;
    private boolean end;
    private boolean removeEndscreenImage;
    private Mode mode, previousMode;
    private Button removeEndscreen;
    private List<Button> otherButtons;
    private List<Field> truesightFields;
    private Field lastField;
    private Textfield shields;

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
        this.reveiledFields = 0;
        this.sleepCounter = 0;
        this.endCount = 0;
        this.shield = 0;
        this.truesightFields = null;
        this.lastField = null;
        this.otherButtons = new ArrayList<>();
        this.mode = Mode.NEUTRAL;
        this.shields = new Textfield(25, 200, "" + this.shield);
        this.shields.setColor(Color.WHITE);
        setBGM(SoundMapping.MINEFIELD);

        registerComponent(this.shields);

        Button menu = new Button(100, 50, 25, 25, "MENU", Color.GRAY);
        menu.setAction(() -> {
            Menu m = new Menu();
            setNewScene(m);
        });
        Button exit = new Button(100, 50, windowSize.x-125, 25 , "EXIT", Color.GRAY);
        exit.setAction(() -> {
            System.exit(0);
        });        
        Button action1 = new Button(100, 50, 25, windowSize.y/2 - 75, "FIND SAFEZONE", Color.GRAY);
        action1.setAction(() -> {
            setMode(Mode.SAFEZONE);
        });
        Button action2 = new Button(100, 50, 25, windowSize.y/2 , "SHIELD", Color.GRAY);
        action2.setAction(() -> {
            this.shield = 4;
            this.shields.setText("" + (this.shield-1));
        });
        Button action3 = new Button(100, 50, 25, windowSize.y/2 + 75 , "TRUESIGHT", Color.GRAY);
        action3.setAction(() -> {
            setMode(Mode.TRUESIGHT);
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
    private void setMode(Mode mode) {
        if (this.mode != Mode.NEUTRAL) {
            this.mode = Mode.NEUTRAL;
        }
        else {
            this.mode = mode;
        }
    }

    private void setModeNeutral() {
        this.mode = Mode.NEUTRAL;
    }

    private void setModeSleep(int count) {
        unregisterOtherButtons();
        this.previousMode = this.mode;
        this.mode = Mode.SLEEP;
        this.sleepCounter = count;
    }

    /**
     * Takes a boolean that indicates, if the game was won or lost.  
     * Sets the end variable to true, to indicate, that the Game has ended.  
     * Reveils all Fields and changes the image from lose to win, if the Game was won.
     * @param win boolean to indicate win or loss
     */
    private void end(boolean win) {
        this.end = true;
        this.endCount = getCounter();
        this.grid.reveilAll();

        if (win) {
            BufferedImage image = Loader.loadImage(ImageMapping.WIN);
            this.removeEndscreen.setImage(image);
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
        lastField = updateReveiledFields();
        switch (mode) {
            case Mode.NEUTRAL:
                if (removeEndscreenImage) {
                    removeEndScreen();
                }
                if (endCount!=0) {
                    updateEndCount();
                }
                if (!end) {
                    checkEndConditions(lastField);
                }
                break;
            case Mode.SAFEZONE:
                safeZoneFinder();
                break;
            case Mode.TRUESIGHT:
                truesightFields = truesight();
                break;
            case Mode.SLEEP:
                if (sleepCounter == 0) {
                    registerOtherButtons();
                    this.mode = this.previousMode;
                    this.previousMode = Mode.SLEEP;
                    if (this.lastField != null) {
                        this.lastField.conceal();
                        this.lastField = null;
                    }
                    if (truesightFields != null) {
                        for (Field f: truesightFields) {
                            f.conceal();
                        }
                        truesightFields = null;
                    }
                    setModeNeutral();
                }
                else {
                    sleepCounter -= 1;
                }
                break;
        }
    }

    public Field updateReveiledFields() {
        int previousCount = reveiledFields;
        reveiledFields = 0;
        Field reveiledMine = null;
        for (Field f: getFields()) {
            if (f.getState() == FieldState.REVEILED) {
                reveiledFields +=1;
                if (f.getValue()==9) {
                    reveiledMine = f;
                }
            }
        }
        if (reveiledFields>previousCount && shield > 0) {
            shield -= 1;
            shields.setText("" + (shield-1));
        }
        return reveiledMine;
    }

    public void safeZoneFinder() {
        Random duck = new Random();
        Point gridSize = grid.getSize();
        boolean validField = false;
        Field f = null;
        while (!validField) {
            int x = duck.nextInt(gridSize.x);
            int y = duck.nextInt(gridSize.y);
            f = grid.getField(x, y);
            if (f.getValue() == 0) {
                validField = true;
            }
        }
        f.reveilAction();
        setModeNeutral();
    }

    public List<Field> truesight() {
        List<Field> truesightFields = new ArrayList<>();
        for (Field f: grid.getAllFields()) {
            if (f.getState()==FieldState.UNKNOWN) {
                f.show();
                truesightFields.add(f);
            }
        }
        setModeSleep(StaticValues.TRUESIGHTCOUNTER);
        return truesightFields;
    }

    public void updateEndCount() {
        if (getCounter() == endCount + 40 ) {
            registerButton(removeEndscreen);
            unregisterOtherButtons();
            endCount = 0;
        }
    }

    public void removeEndScreen() {
        unregisterButton(removeEndscreen);
        registerOtherButtons();
        removeEndscreenImage = false;
    }

    public void checkEndConditions(Field lastField) {
        if (lastField!=null && shield==0) {
            end(false);
        }
        else if (lastField!=null && shield>0) {
            setModeSleep(20);
        }
        else if (reveiledFields+mineCount==fieldCount) {
            end(true);
        }
    }
}
