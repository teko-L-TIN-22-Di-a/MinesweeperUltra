package src.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import src.components.Button;
import src.components.Component;
import src.components.Field;
import src.scenes.Scene;

/**
 * Renders the game view to the screen
 * @see Scene
 * @see Canvas
 * @see Component
 * @see Button
 */
public class Renderer extends JFrame{
    /** Scene to render */
    private Scene scene;
    /** Size of the canvas/Screen */
    private Point canvasSize;
    /** Canvas to draw the Scene on */
    public Canvas canvas = new Canvas();
    /** Used to set debug mode */
    private boolean debug = false;

    /**
     * Creates a Renderer Object. The debug option can be used to display Hit-
     * and image Boxes, for easy controlling if the calculated positions match
     * the positions on the screen.
     * @param debug if true, Hitboxes and ImageBoxes will be drawn
     */
    public Renderer(boolean debug) {
        this.canvasSize = new Point(StaticValues.CANVAS_WIDTH, StaticValues.CANVAS_HEIGHT);
        this.canvas.setPreferredSize(new Dimension(this.canvasSize.x, this.canvasSize.y));
        this.setContentPane(canvas);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.setTitle(StaticValues.GAMENAME);
        this.setVisible(true);
        this.debug = debug;
    }

    /**
     * Takes a Scene for rendering.
     * @param scene Scene to render
     */
    public void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Extends the JPanel class to create a canvas to draw onto.
     * @see JPanel
     */
    public class Canvas extends JPanel {
        /** width of the canvas */
        int width = StaticValues.CANVAS_WIDTH;
        /** height of the canvas */
        int height = StaticValues.CANVAS_HEIGHT;
        /** image that is drawn on the screen */
        BufferedImage onScreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /** pre rendered image */
        BufferedImage offScreenImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        /** graphics for the screen */
        Graphics2D onScreen = onScreenImage.createGraphics();
        /** graphics for pre rendering */
        Graphics2D offScreen = offScreenImage.createGraphics();

        /**
         * Constructor. Creates a Canvas object to draw onto.
         */
        public Canvas() {
            this.onScreen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            this.offScreen.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        
        /**
         * Takes Graphics and to draw.
         * Draws the defined components on the screen.
         */
        @Override
        public void paintComponent(Graphics g) {
            this.offScreen.setColor(Color.black);
            this.offScreen.fillRect(0, 0, width, height);
            if (debug) {
                this.offScreen.setColor(Color.blue);
                this.offScreen.drawLine(0, StaticValues.CANVAS_HEIGHT/2, StaticValues.CANVAS_WIDTH, StaticValues.CANVAS_HEIGHT/2);
                this.offScreen.drawLine(StaticValues.CANVAS_WIDTH/2, 0, StaticValues.CANVAS_WIDTH/2, StaticValues.CANVAS_HEIGHT);
            }
            List<Component> components = scene.getComponents();
            for (Component component: components) {
                Point componentLocation = component.getDrawPosition();
                offScreen.drawImage(component.getImage(), componentLocation.x, componentLocation.y, null);
                if (debug) {
                    component.drawRect(offScreen, Color.red); // DEBUG VIEW
                }
            }
            List<Field> fields = scene.getFields();
            for (Field field: fields) {
                Point componentLocation = field.getDrawPosition();
                offScreen.drawImage(field.getImage(), componentLocation.x, componentLocation.y, null);
                if (debug) {
                    field.drawRect(offScreen, Color.red); // DEBUG VIEW
                }
            }
            List<Button> buttons = scene.getButtons();
            for (Button button: buttons) {
                Point buttonLocation = button.getDrawPosition();
                this.offScreen.drawImage(button.getImage(), buttonLocation.x, buttonLocation.y, null);
                if (debug) {
                    button.drawRect(offScreen, Color.red); // DEBUG VIEW
                }
            }
            this.onScreen.drawImage(offScreenImage, 0, 0, null);
            g.drawImage(onScreenImage, 0, 0, null);
        }
    }
}
