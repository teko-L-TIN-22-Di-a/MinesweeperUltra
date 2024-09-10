package src.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import javax.sound.sampled.Clip;

import src.core.StaticValues;
import src.core.StaticValues.FieldState;

/**
 * The Field will be placed in the Grid and contains a value from 0-9, where 9
 * indicates a Minefield and the other numbers indicate the number of Mines around the Field.  
 * A Field has 1 of 3 possible states: "Unknown", "Reveiled" and "Flagged".
 * A "Reveiled" Field can no longer be interacted with and a "Flagged" Field can't be reveiled.  
 * The Color of the field is dependent from it's state. A Reveiled Field uses it's value as
 * Index to get it's Color from the StaticValues.COLORS.  
 * Each Field stores its Location in the Grid as matrixLoction and can store a reference
 * to all adjacent Fields. This reference can later be used for area effects.
 * @see Grid
 * @see Component
 */
public class Field extends Component{

    private FieldState state;
    private int value;
    private Textfield text;
    private Color color;
    private Point matrixLocation;
    private List<Field> adjacentFields;
    private Clip flag;
    
    /**
     * Takes width, height, x and y coordinates to create a Field Object.
     * Field Objects are used in a Grid to create a Minefield.
     * @param width width of the Field
     * @param height height of the Field
     * @param x x coordinate of the Field on the canvas
     * @param y y coordinate of the Field on the canvas
     */
    public Field(int width, int height, int x, int y) {
        super(width, height, x, y);
        this.state = FieldState.UNKNOWN;
        this.text = new Textfield(x, y, " ");
        this.text.setColor(Color.WHITE);
        this.color = Color.GRAY;
        this.setText(" ");
    }

    /**
     * Takes a string to change the displayed text on the Field.  
     * Creates a new Image to be filled with this Objects Color.
     * Then the Image of the Text is taken and drawn in the middle
     * of the colored Field.
     * @param text
     */
    private void setText(String text) {
        this.text.setText(text);
        int fieldWidth = this.getWidth();
        int fieldHeight = this.getHeight();
        BufferedImage image = new BufferedImage(fieldWidth, fieldHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        g.setColor(this.color);
        g.fillRect(0, 0, fieldWidth, fieldHeight);
        BufferedImage textImage = this.text.getImage();
        int x = (fieldWidth-textImage.getWidth())/2;
        int y = (fieldHeight-textImage.getHeight())/2;
        g.drawImage(textImage, x, y, null);
        this.setImage(image);
        g.dispose();
    }

    public FieldState getState() {
        return state;
    }

    /**
     * Takes an x and y coordinate to be stored in this Objects matrixLocation variable.
     * @param x x coordinate to be stored in this Objects matrixLocation variable
     * @param y y coordinate to be stored in this Objects matrixLocation variable
     */
    public void setMatrixLocation(int x, int y) {
        matrixLocation = new Point(x, y);
    }

    /**
     * Returns a Point Object that contains an x and y value representing
     * the location inside the Grid matrix.
     * @return x and y location inside the Grid matrix
     */
    public Point getMatrixLocation() {
        return matrixLocation;
    }

    /**
     * Takes an int value (between 0-9) to store in this Objects value variable.  
     * Where 0-8 represent the amount of Mines around the Field, 9 indicates, that
     * the Field itsself is a Mine.
     * @param value value to store in this Objects value variable
     */
    public void setValue(int value) {
        this.value = value;
    }

    public void setSound(Clip sound, Clip flagSound) {
        setSound(sound);
        this.flag = flagSound;
    }

    /**
     * Returns the value of this Field.
     * @return value of this Field
     */
    public int getValue() {
        return value;
    }

    /**
     * Uses the value of the Field to get a corresponding Value
     * from StaticValues.Colors.
     */
    private void setColor() {
        this.color = StaticValues.COLORS[value];
    }

    /**
     * Takes a List of Fields to store in the adjacentFields List.  
     * These Fields can be used for actions that concern the other Fields
     * around this Field Object.
     * @param af
     */
    public void setAdjacentFields(List<Field> af) {
        adjacentFields = af;
    }

    /**
     * Retunrns adjacentFields to use for actions that concern the other Fields
     * around this Field Object.
     * @return all adjacent Fields
     */
    public List<Field> getAdjacentFields() {
        return adjacentFields;
    }

    public void action(Point mouseLocation) {
        if (collidePoint(mouseLocation)) {
            reveilAction();
        }
    }

    /**
     * Changes the State of the Field to "Reveiled" and sets the corresponding Color.
     */
    public void reveil() {
        this.state = FieldState.REVEILED;
        show();
    }

    public void show() {
        this.setColor();
        this.setText("" + value);
        if (value == 9) {
            this.setText("O");
        }
    }

    public void conceal() {
        this.state = FieldState.UNKNOWN;
        this.color = Color.GRAY;
        this.setText(" ");
    }

    /**
     * Action to be triggred, when a Field is reveiled. If a mine is reveiled, the Game ends.
     * If a field with value 0 is reveiled, all adjacent Fields are reveiled as well. All
     * 0 Fields reveiled additionally will also reveil their adjacent Fields.
     */
    public void reveilAction() {
        if (this.state == FieldState.UNKNOWN) {
            reveil();
            playSound();
            if (value == 0) {
                for (Field f: adjacentFields) {
                    f.reveilAction();
                }
            }
        }
    }

    /**
     * Action to be triggered, when a Field is flagged.  
     * The Fieldstate of a Unkwnown Field will be changed to Flagged and
     * vice versa.
     * @param mouseLocation
     */
    public void flagAction(Point mouseLocation) {
        if (collidePoint(mouseLocation)) {
            
            this.flag.stop();
            this.flag.setFramePosition(0);
            this.flag.start();

            this.color = StaticValues.COLORS[0];
            if (this.state == FieldState.UNKNOWN) {
                this.state = FieldState.FLAGGED;
                this.color = Color.DARK_GRAY;
                this.setText("F");
                System.err.println("flagged field");
            }
            else if (this.state == FieldState.FLAGGED) {
                this.state = FieldState.UNKNOWN;
                this.color = Color.GRAY;
                this.setText(" ");
                System.err.println("unflagged field");
            }
        }
    }
}
