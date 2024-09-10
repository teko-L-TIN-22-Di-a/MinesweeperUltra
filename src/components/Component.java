package src.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;

import src.assets.Loader;
import src.core.StaticValues.Corners;

/**
 * Baseclass for a Component
 */
public class Component {

    private BufferedImage image;
    /** Rectangle to track size and location */
    private Rectangle rect;
    private Clip sound;

    /**
     * Creates a Component with size, location and Rectangle
     * @param width     width of the Component
     * @param height    height of the Component
     * @param x         x coordinate of the Component
     * @param y         y coordinate of the Component
     */
    public Component(int width, int height, int x, int y) {
        this.rect = new Rectangle(width, height, x, y);
        this.sound = null;
    }

    /**
     * Creates a Component with image, size, location and Rectangle
     * Loads an image from app/src/resources/assets.
     * @param imageName image name like "image.png"
     * @param x         x coordinate of the Component
     * @param y         y coordinate of the Component
     */
    public Component(String imageName, int x, int y) {
        BufferedImage image = Loader.loadImage(imageName);
        setImage(image);
        int width = image.getWidth();
        int height = image.getHeight();
        this.rect = new Rectangle(width, height, x, y);
        this.sound = null;
    }

    /**
     * Takes a point and calls the collidepoint method of the Rectangle Object.
     * @param point Point to be checked for collision with this Objects Rectangle.
     * @return True, if the Point collides with this Objects Rectangle.
     */
    public boolean collidePoint(Point point) {
        return rect.collidePoint(point);
    }

    /**
     * Takes a filename, loads the corresponding sound file and sets it to the sound variable.
     * @param filename filename of the sound file to be loaded
     */
    public void setSound(Clip sound) {
        this.sound = sound;
    }

    public void playSound() {
        if (sound != null) {
            sound.stop();
            sound.setFramePosition(0);
            sound.start();
        }
    }

    /**
     * Base for update methods of extending subclasses.
     */
    public void update() {
        // to overide per component
    }

    /**
     * Returns the image of the Component
     * @return image of the Component
     */
    public BufferedImage getImage() {
        return image;
    }

    /**
     * Takes an image and stores it.
     * @param image  new image to store
     */
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    /**
     * returns the top left corner of the Rectangle as draw position
     * @return top left corner
     */
    public Point getDrawPosition() {
        return this.rect.getCorner(Corners.TOP_LEFT);
    }

    /**
     * Takes a Graphic and draws this Objects Rectangle onto the Graphic.
     * @param graphics Graphic to draw the Rectangle onto.
     * @param color Color that the Rectangle will be drawn in.
     */
    public void drawRect(Graphics2D graphics, Color color) {
        rect.draw(graphics, color);
    }

    /**
     * Returns the location of the Component
     * @return location of the Component
     */
    public Point getLocation() {
        return this.rect.getLocation();
    }

    /**
     * Takes coordinates for a new location and stores them 
     * @param x  new x coordinate for the Component
     * @param y  new y coordinate for the Component
     */
    public void setLocation(int x, int y) {
        this.rect.setLocation(x, y);
    }

    /**
     * Returns the width of the Component
     * @return width of the Component
     */
    public int getWidth() {
        Point size = rect.getSize();
        return size.x;
    }

    /**
     * Returns the height of the Component
     * @return height of the Component
     */
    public int getHeight() {
        Point size = rect.getSize();
        return size.y;
    }

    /**
     * Takes width and height to store in size.
     * Recalculates the Rectangle.
     * @param width  new width of the Component
     * @param height new height of the Component
     */
    public void setSize(int width, int height) {
        Point location = rect.getLocation();
        this.rect.setSize(width, height);
        setLocation(location.x, location.y);
    }
}
