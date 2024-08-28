package src.core;

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
    public static int CANVAS_WIDTH = 500;
    /** height of the gamewindow */
    public static int CANVAS_HEIGHT = 1080;
    /** Framerate */
    public static int UPDATE_PERIOD = 60;

    public static enum FieldState {
        UNKNOWN,
        FLAGGED,
        REVEILED,
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
        BOTTOM_LEFT,
    }

    /** Available Tags for a Scene */
    public static enum SceneTag {
        /** Tag for active Scene */
        ACTIVE,
        /** Tag for previous Scene */
        PREVIOUS,
        /** Tag for new Scenes */
        NEW,
    }
}