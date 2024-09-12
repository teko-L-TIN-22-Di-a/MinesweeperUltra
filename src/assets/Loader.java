package src.assets;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/** Loads assets from the resources folder. */
public class Loader {

    private static String imageDir = ImageMapping.DIR;
    private static String soundDir = SoundMapping.DIR;
    
    /** 
     * Loader does not need to be initialized, therefore
     * the constructor is set to private.
     */
    private Loader() {
        // Initialization code can go here if needed
    }
    
    /**
     * Takes the name of an image file and loads it as Buffered image.
     * @param name name like "image.png"
     * @return the loaded image as BufferedImage
     * @see BufferedImage
     */
    public static BufferedImage loadImage(String name) {
    BufferedImage image = null;
        try {
            String imagePath = imageDir + name;
            InputStream inputStream = Loader.class.getResourceAsStream(imagePath);
            image = ImageIO.read(inputStream);
            System.out.println("image " + name + " loaded");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("can't load image");
        }
        return image;
    }

    /**
     * Takes the name of a audio file and loads it as a clip.
     * @param name name like "sound.wav"
     * @return the loaded audio as Clip
     * @see Clip
     */
    public static Clip loadSound(String name) {
        BufferedInputStream audio = null;
        Clip clip = null;
        try {
            String soundPath = soundDir + name;
            clip = AudioSystem.getClip();

            InputStream inputStream = Loader.class.getResourceAsStream(soundPath);
            audio = new BufferedInputStream(inputStream);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audio);
            clip.open(audioStream);
            System.out.println("audio " + name + " loaded");
            
        } catch (UnsupportedAudioFileException e) {
            System.out.println("The specified audio file is not supported.");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Error playing the audio file.");
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            System.out.println("Audio line for playing back is unavailable.");
            e.printStackTrace();
        }
        return clip;
    }
}
