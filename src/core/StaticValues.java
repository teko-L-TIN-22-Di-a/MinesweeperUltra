package src.core;

import java.awt.Color;

/**
 * Dataclass to store fixed values
 */
public class StaticValues {

    /** Empty constructor. */
    private StaticValues() {
        // empty
    }
    
    // Gamesettings
    /** name of the gamewindow */
    public static String GAMENAME = "Minesweeper Ultra";
    /** width of the gamewindow */
    public static int CANVAS_WIDTH = 1324;
    /** height of the gamewindow */
    public static int CANVAS_HEIGHT = 1024;
    /** Framerate */
    public static int UPDATE_PERIOD = 60;
    /** Lenght of the side of the field square */
    public static int FIELDSIZE = 30;
    /**
     * Colors used for the Fields. The sequence is relevant and
     * the index corresponds to the Field value.
     */
    public static Color[] COLORS = {
        Color.LIGHT_GRAY,
        Color.BLUE,
        Color.GREEN,
        Color.ORANGE,
        Color.YELLOW,
        Color.MAGENTA,
        Color.PINK,
        Color.CYAN,
        Color.BLACK,
        Color.RED
    };
    /** Possible states a Field can have */
    public static enum FieldState {
        UNKNOWN,
        FLAGGED,
        REVEILED
    }

    // Enumerations for Selections
    /** Available Rectangle corners */
    public static enum Corners {
        /** Top Right corner */
        TOP_RIGHT,
        /** Top Left corner */
        TOP_LEFT,
        /** Bottom Left corner */
        BOTTOM_RIGHT,
        /** Bottom Right corner */
        BOTTOM_LEFT
    }

    /** Available Tags for a Scene */
    public static enum SceneTag {
        /** Tag for active Scene */
        ACTIVE,
        /** Tag for previous Scene */
        PREVIOUS,
        /** Tag for new Scenes */
        NEW
    }
}