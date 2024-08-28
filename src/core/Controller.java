package src.core;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.List;

import javax.swing.JPanel;

import src.components.Button;

/**
 * Handles Player input
 * @see MouseHandler
 * @see MouseTracker
 */
public class Controller extends JPanel{
    
    /** handles mouse input */
    private final MouseHandler handler;
    /** tracks mouse position */
    private final MouseTracker tracker;
    /** list of Buttons to check */
    private List<Button> buttonlist;

    /**
     * Creates a Controller object.
     * Sets up a mouse handler and tracker.
     */
    public Controller() {
        setSize(StaticValues.CANVAS_WIDTH, StaticValues.CANVAS_HEIGHT);
        this.handler = new MouseHandler();
        this.tracker = new MouseTracker();
    }

    /**
     * Returns the mouse position.
     * @return mouse location
     */
    public Point getMousePos() {
        return this.tracker.getLocation();
    }

    /**
     * Indicates, if the Mouse Button 1 is currently beeing pressed or not.
     * @return true, if the Button M1 is beeing pressed
     */
    public boolean getM1down() {
        return this.handler.getM1down();
    }

    /**
     * Takes a list of Buttons and stores it in the buttonList.
     * @param buttonList    list to store
     */
    public void setButtonList(List<Button> buttonList) {
        this.buttonlist = buttonList;
    }

    /**
     * Adds the required listeners to a JPanel
     * @param panel panel to add listeners
     */
    public void setupListeners(JPanel panel) {
        panel.addMouseListener(this.handler);
        panel.addMouseMotionListener(this.tracker);
    }

    /**
     * Extends the MouseAdapter to add the actioncheck for the buttons.
     * @see MouseAdapter
     * @see Button
     */
    private class MouseHandler extends MouseAdapter {

        boolean m1down;

        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() == 1) {
                this.m1down = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() == 1) {
                for (Button b: buttonlist) {
                    Point mousePosition = tracker.getLocation();
                    b.actionCheck(mousePosition);
                }
                this.m1down = false;
            }
        }

        /**
         * Indicates, if the Mouse Button 1 is currently beeing pressed or not.
         * @return true, if the Button M1 is beeing pressed
         */
        public boolean getM1down() {
            return this.m1down;
        }
    }

    /**
     * Extends the MouseMotionAdapter class to extract the current mouse location
     * @see MouseMotionAdapter
     */
    private class MouseTracker extends MouseMotionAdapter {

        private Point location = new Point(0, 0);

        @Override
        public void mouseMoved(final MouseEvent e) {
            this.location = e.getPoint();
            if (this.location == null) {
                this.location = new Point(0, 0);
            }
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            this.location = e.getPoint();
        }

        public Point getLocation() {
            return this.location;
        }
    }
}