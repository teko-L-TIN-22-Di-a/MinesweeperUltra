package src.scenes;

import src.assets.ImageMapping;
import src.assets.SoundMapping;
import src.components.Grid;


/**
 * Creates the level1 screen.
 * Extends the Scene class.
 * @see Bow
 * @see Scene
 */
public class MineField extends Scene {
    
    private Grid grid;

    /**
     * Sets up the Level1 scene.
     * @see Monster
     * @see Bow
     */
    public MineField(int width, int height, int mineCount) {
        super(false, "minefield");
        grid = new Grid(width, height, mineCount);

        //setBG(ImageMapping.MAP1);
        //setBGM(SoundMapping.LEVEL1BGM);
    }
}
