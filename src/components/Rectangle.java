package src.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import src.core.StaticValues.Corners;

/**
 * Provides functionalities to create and calculate with rectangles.
 */
public class Rectangle {
    private Point size, location, topLeft, topRight, bottomLeft, bottomRight;

    /**
     * Creates a Rectangle object.
     * @param width     width of the rectangle
     * @param height    height of the rectangle
     * @param x         x value of the centerpoint
     * @param y         y value of the centerpoint
     */
    public Rectangle(int width, int height, int x, int y) {
        this.location = new Point(x, y);
        this.size = new Point(width, height);
        setCorners();
    }

    /**
     * Draws the Rectangle object on surface.
     * @param surface   surface to draw on
     * @param color     color of the drawing
     * @return          the surface with the drawn rectangle
     * @see             Graphics2D
     */
    public Graphics2D draw(Graphics2D surface, Color color) {
        int x1 = getCorner(Corners.TOP_LEFT).x;
        int x2 = getCorner(Corners.BOTTOM_RIGHT).x;
        int y1 = getCorner(Corners.TOP_LEFT).y;
        int y2 = getCorner(Corners.BOTTOM_RIGHT).y;
        surface.setColor(color);
        surface.drawLine(x1, y1, x1, y2);
        surface.drawLine(x1, y1, x2, y1);
        surface.drawLine(x2, y2, x2, y1);
        surface.drawLine(x2, y2, x1, y2);
        return surface;
    }

    /**
     * Takes a Point object and determines, if it's inside the rectangle or not.
     * @param point point to be checked
     * @return      true, if the point is inside the Rectangle object
     */
    public boolean collidePoint(Point point) {
        if (
            point.x >= topLeft.x &&
            point.x <= bottomRight.x &&
            point.y >= topLeft.y &&
            point.y <= bottomRight.y
        ) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Takes a Rectangle object and determines if it collides with the rectangle or not.
     * @param rectangle rectangle to be checked
     * @return          true, if the rectangles intersect
     */
    public boolean collideRect(Rectangle rectangle) {
        List<Point> points = rectangle.getCorners();
        for (Point point : points) {
            if (collidePoint(point)) {
                return true;
            }
            else {return false;}
        }
        return false;
    }

    /**
     * Returns a corner determined by the input
     * @param corner    corner to be returned
     * @return          dtermined corner
     * @see             Corners
     */
    public Point getCorner(Corners corner) {
        switch (corner) {
            case BOTTOM_LEFT:
                return this.bottomLeft;
            case BOTTOM_RIGHT:
                return this.bottomRight;
            case TOP_RIGHT:
                return this.topRight;
            default:
                return this.topLeft;
        }
    }

    /**
     * Sets the corner coordinates based on the location and the size of the Rectangle object.
     */
    private void setCorners() {
        this.topLeft = new Point (this.location.x, this.location.y);
        this.topRight = new Point (this.location.x + size.x, this.location.y);
        this.bottomLeft = new Point (this.location.x, this.location.y + size.y);
        this.bottomRight = new Point (this.location.x + size.x, this.location.y + size.y);
    }

    /**
     * Returns a list with all corner points
     * @return  list of cornerpoints
     */
    public List<Point> getCorners() {
        List<Point> points = new ArrayList<>();
        points.add(this.topLeft);
        points.add(this.topRight);
        points.add(this.bottomLeft);
        points.add(this.bottomRight);
        return points;
    }

    /**
     * Sets the size of the rectangle and recalculates the corner coordinates.
     * @param width     new width for the rectangle
     * @param height    new height for the rectangle
     * @see             setCorners
     */
    public void setSize(int width, int height) {
        this.size = new Point(width, height);
        setCorners();
    }

    /**
     * Sets a new location for the rectangle and recalculates the corner coordinates.
     * @param newX new x location for the rectangle.
     * @param newY new y location for the rectangle.
     */
    public void setLocation(int newX, int newY) {
        this.location = new Point(newX, newY);
        setCorners();
    }

    /**
     * Returns the size of the Rectangle object.
     * @return size of the rectangle
     */
    public Point getSize() {
        return this.size;
    }

    /**
     * Returns the width of the Rectangle object.
     * @return width of the rectangle
     */
    public int getWidth() {
        return this.size.x;
    }

    /**
     * Returns the height of the Rectangle object.
     * @return height of the rectangle
     */
    public int getHeight() {
        return this.size.y;
    }

    /**
     * Returns the location of the Rectangle object.
     * @return location of the rectangle
     */
    public Point getLocation() {
        return this.location;
    }

    /**
     * Returns the x value of the Rectangle objects location.
     * @return x value of the location
     */
    public int getX() {
        return this.location.x;
    }

    /**
     * Updates the x value of the Rectangle objects location.
     * @param value new value for location.x
     */
    public void setX(int value) {
        this.location.x = value;
    }

    /**
     * Returns the y value of the Rectangle objects location.
     * @return y value of the location
     */
    public int getY() {
        return this.location.y;
    }

    /**
     * Updates the y value of the Rectangle objects location.
     * @param value new value for location.y
     */
    public void setY(int value) {
        this.location.y = value;
    }
}
