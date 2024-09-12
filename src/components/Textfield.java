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
    private Color color;

    /**
     * Takes x and y coordinate and a String to create a Component with text.
     * @param x             location x
     * @param y             location y
     * @param buttonText    displayed text
     */
    public Textfield(int x, int y, String text) {
        super(1, 1, x, y);
        this.text = text;
        BufferedImage image = createImage();
        setImage(image);
        setSize(image.getWidth(), image.getHeight());
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setText(String text) {
        this.text = text;
        setImage(createImage());
    }

    /**
     * Creates an image with the display text
     * @return  image with display text
     */
    private BufferedImage createImage() {
        // Split the text into lines
        String[] lines = text.split("\n");

        // Create a temporary image to get the font metrics
        BufferedImage tempImage = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics tempGraphics = tempImage.getGraphics();
        FontMetrics fm = tempGraphics.getFontMetrics();
        int lineHeight = fm.getHeight();

        // Calculate the width and height of the final image
        int imageWidth = 0;
        for (String line : lines) {
            int lineWidth = fm.stringWidth(line);
            if (lineWidth > imageWidth) {
                imageWidth = lineWidth;
            }
        }
        int imageHeight = lineHeight * lines.length;

        // Create the final image
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(color);

        // Draw each line of text
        int y = fm.getAscent();
        for (String line : lines) {
            g.drawString(line, 0, y);
            y += lineHeight;
        }

        g.dispose();
        return image;
    }
}
