package src.components;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 * Extends the Compnent class by adding text, that can be drawn on a surface.
 * Can be used as Textfield in the Scene or to add Labels to other Components.
 */
public class Textfield extends Component{

    private String text;
    private Color color;
    private boolean orientRight;

    /**
     * Takes x and y coordinate and a String to create a Component with text.
     * @param x location x
     * @param y location y
     * @param text displayed text
     */
    public Textfield(int x, int y, String text) {
        super(1, 1, x, y);
        this.text = text;
        this.orientRight = false;
        BufferedImage image = createImage();
        setImage(image);
        setSize(image.getWidth(), image.getHeight());
    }

    /**
     * Takes a Color and sets it as the Textfields writing Color.
     * @param color writing Color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Takes a String to set as the text of the Textfield.
     * @param text displayed text
     */
    public void setText(String text) {
        this.text = text;
        setImage(createImage());
    }

    /**
     * Takes a boolean and if true, will set the orientation of the
     * Textfield to the right side, if false to the left side.
     * @param right
     */
    public void setOrientation(boolean right) {
        this.orientRight = right;
    }

    /**
     * Creates an image with the display text.
     * @return image with display text
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
        int[] widthList = new int[lines.length];
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            int lineWidth = fm.stringWidth(line);
            if (lineWidth > imageWidth) {
                imageWidth = lineWidth;
            }
            widthList[i] = lineWidth;
        }
        int imageHeight = lineHeight * lines.length;

        // Create the final image
        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.getGraphics();
        g.setColor(color);

        // Draw each line of text
        int y = fm.getAscent();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i]; {
            int x = 0;
            if (orientRight) {
                x = imageWidth - widthList[i];
            }
            g.drawString(line, x, y);
            y += lineHeight;
            }
        }

        g.dispose();
        return image;
    }
}
