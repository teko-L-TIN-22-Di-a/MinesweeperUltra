package src.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Extends the Compnent class by adding text, that can be drawn on a surface.
 */
public class Textfield extends Component{

    private String text;

    /**
     * Takes x and y coordinate and a String to create a Component with text.
     * @param x             location x
     * @param y             location y
     * @param buttonText    displayed text
     */
    public Textfield(int x, int y, String buttonText) {
        super(x, y, 1, 1);
        text = buttonText;
        BufferedImage image = createImage();
        setImage(image);
        setSize(image.getWidth(), image.getHeight());
    }

    /**
     * Creates an image with the display text
     * @return  image with display text
     */
    private BufferedImage createImage() {
        BufferedImage ImageTMP = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics gTMP = ImageTMP.getGraphics();
        gTMP.drawString(text, 0, 0);
        FontMetrics fm = gTMP.getFontMetrics();
        gTMP.dispose();
        BufferedImage image = new BufferedImage(fm.stringWidth(text), fm.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.drawString(text, 0, g.getFontMetrics().getAscent());
        g.dispose();
        return image;
    }
}
