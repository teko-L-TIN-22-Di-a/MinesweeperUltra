package src.core;

import java.awt.Color;
import java.awt.Point;

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
    /** Amount of tickts for truesight activity */
    public static int TRUESIGHTCOUNTER = 2;
    /** Size if a small Minefield */
    public static Point SMALL = new Point(10, 10);
    /** Size if a medium Minefield */
    public static Point MEDIUM = new Point(20, 20);
    /** Size if a large Minefield */
    public static Point LARGE = new Point(30, 30);
    /** Amount of Mines for the Minefield sizes for easy, medium and hard difficulties */
    public static int[][] DIFFICULTY = {{10,20,25},{40,80,100},{90,180,225}};
    /**
     * Colors used for the Fields. The sequence is relevant and
     * the index corresponds to the Field value.
     */
    public static Color[] COLORS = {
        Color.LIGHT_GRAY,
        Color.BLUE,
        Color.GREEN,
        Color.ORANGE,
        Color.PINK,
        Color.MAGENTA,
        Color.CYAN,
        Color.YELLOW,
        Color.BLACK,
        Color.RED
    };
    /** Possible states a Field can have */
    public static enum FieldState {
        /** Determines the State of a Field as UNKNOWN */
        UNKNOWN,
        /** Determines the State of a Field as FLAGGED */
        FLAGGED,
        /** Determines the State of a Field as REVEILED */
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

    /** Avaiable Modes for the Minefield scene */
    public static enum Mode {
        /** Standard Mode */
        NEUTRAL,
        /** Mode for activating Truesight */
        TRUESIGHT,
        /** Mode to pass time and reset options for Neutral Mode */
        SLEEP
    }
}