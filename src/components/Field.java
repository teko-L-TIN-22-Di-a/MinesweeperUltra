package src.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.List;

import src.core.StaticValues;
import src.core.StaticValues.FieldState;

public class Field extends Component{

    private FieldState state;
    private boolean isMine;
    private int value;
    private Textfield text;
    private Color color;
    private Point matrixLocation;
    private List<Field> adjacentFields;
    
    public Field(int width, int height, int x, int y) {
        super(width, height, x, y);
        this.state = FieldState.UNKNOWN;
        this.isMine = false;
        this.text = new Textfield(x, y, " ");
        this.text.setColor(Color.WHITE);
        this.color = Color.LIGHT_GRAY;
        this.setText(" ");
    }

    /**
     * 
     * @param text
     */
    private void setText(String text) {
        this.text.setText(text);
        BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        int fieldWidth = this.getWidth();
        int fieldHeight = this.getHeight();
        g.setColor(this.color);
        g.fillRect(0, 0, fieldWidth, fieldHeight);
        BufferedImage textImage = this.text.getImage();
        int x = (fieldWidth-textImage.getWidth())/2;
        int y = (fieldHeight-textImage.getHeight())/2;
        g.drawImage(textImage, x, y, null);
        this.setImage(image);
        g.dispose();
    }

    public void setMatrixLocation(int x, int y) {
        matrixLocation = new Point(x, y);
    }

    public Point getMatrixLocation() {
        return matrixLocation;
    }

    public void setMine() {
        this.isMine = true;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    private void setColor() {
        this.color = StaticValues.COLORS[value];
    }

    public void setAdjacentFields(List<Field> af) {
        adjacentFields = af;
    }

    public List<Field> getAdjacentFields() {
        return adjacentFields;
    }

    private void reveil() {
        this.state = FieldState.REVEILED;
        this.setColor();
    }

    public void action(Point mouseLocation) {
        if (collidePoint(mouseLocation)) {
            reveilAction();
        }
    }

    public void reveilAction() {
        if (this.state == FieldState.UNKNOWN) {
            reveil();
            if (this.isMine) {
                this.setText("O");
                System.out.println("BOOM!");
            }
            else {
                this.setText("" + value);
                if (value == 0) {
                    for (Field f: adjacentFields) {
                        f.reveilAction();
                    }
                }
            }
        }
    }

    public void flagAction(Point mouseLocation) {
        if (collidePoint(mouseLocation)) {
            this.color = StaticValues.COLORS[0];
            if (this.state == FieldState.UNKNOWN) {
                this.state = FieldState.FLAGGED;
                this.setText("F");
                System.err.println("flagged field");
            }
            else if (this.state == FieldState.FLAGGED) {
                this.state = FieldState.UNKNOWN;
                this.setText(" ");
                System.err.println("unflagged field");
            }
        }
    }
}
