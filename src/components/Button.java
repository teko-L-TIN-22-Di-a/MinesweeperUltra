package src.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferedImage;

/**
 * Extends the Component class with a Textfield and a Runnable action
 * @see Textfield
 * @see Component
 */
public class Button extends Component{

    private Textfield label;
    private String text;
    private Runnable action;

    /**
     * Creates a Button with size, location, text and color.
     * @param x         x coordinate for the location
     * @param y         y coordinate for the location
     * @param width     width of the Button
     * @param height    height of the Button
     * @param text      text displayed on the Button
     * @param color     color of the Button
     */
    public Button(int width, int height, int x, int y, String text, Color color) {
        super(width, height, x, y);
        setUpStandardButton(width, height);
        fill(color);
        updateLabel(text);
    }

    /**
     * Creates a Button with location and image.
     * Loads the image from app/src/resources/assets.
     * Size is taken from the image.
     * @param x         x coordinate for the location
     * @param y         y coordinate for the location
     * @param imageName     image of the Button
     */
    public Button(int x, int y, BufferedImage image) {
        super(1, 1, x, y);
        setImage(image);
        int width = image.getWidth();
        int height = image.getHeight();
        setSize(width, height);
    }

    /**
     * Takes a Runnable and stores it as action variable
     * @param action    new action to perform on button click
     */
    public void setAction(Runnable action) {
        this.action = action;
    }

    /**
     * Runs the defined action
     */
    public void action() {
        playSound();
        if (this.action != null) {
            this.action.run();
        }
    }

    /**
     * Takes a color and fills the Button with it.
     * @param color new color for the Button
     */
    public void fill(Color color) {
        Graphics g = getImage().getGraphics();
        g.setColor(color);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();
    }

    /**
     * Takes a String and puts in on the Button
     * @param text text to be displayed on the Button
     */
    public void updateLabel(String text) {
        if (this.text != "") {
            this.text = text;
        }
        this.label = new Textfield(getLocation().x, getLocation().y,  text);
        int labelWidth =  label.getWidth();
        int labelHeight =  label.getHeight();
        Graphics g = getImage().getGraphics();
        int drawPosX = (getWidth() - labelWidth)/2;
        int drawPosY = (getHeight() - labelHeight)/2;
        g.drawImage(label.getImage(), drawPosX, drawPosY, null);
        g.dispose();
    }

    /**
     * Takes the mouse location and compares it to the buttons corners.
     * If the mouse location is inside the Button, the Buttons action will be perormed.
     * @param mousePosition position to compare to the Button
     */
    public void actionCheck(Point mousePosition) {
        if (collidePoint(mousePosition)) {
            action();
        }
    }

    /**
     * Basic set up for the different Button constructors.
     * @param width     width of the Button
     * @param height    height of the Button
     */
    private void setUpStandardButton(int width, int height) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        setImage(image);
    }
}
