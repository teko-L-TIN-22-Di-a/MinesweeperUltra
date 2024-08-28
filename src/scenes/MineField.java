package src.scenes;

import src.assets.ImageMapping;
import src.assets.SoundMapping;


/**
 * Creates the level1 screen.
 * Extends the Scene class.
 * @see Bow
 * @see Scene
 */
public class MineField extends Scene {
    
    /**
     * Sets up the Level1 scene.
     * @see Monster
     * @see Bow
     */
    public MineField() {
        super(false);
        setTAG("minefield");

        setBG(ImageMapping.MAP1);
        //setBGM(SoundMapping.LEVEL1BGM);
    }
}
