package src.components;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

import src.core.StaticValues;
import src.core.StaticValues.FieldState;

public class Field extends Component{

    private FieldState state;
    private boolean isMine;
    private int value;
    private Textfield text;
    private Color color;
    
    public Field(int width, int height, int x, int y) {
        super(width, height, x, y);
        this.state = FieldState.UNKNOWN;
        this.isMine = false;
        this.text = new Textfield(x, y, " ");
        this.text.setColor(Color.WHITE);
        this.color = Color.LIGHT_GRAY;
        this.setText(" ");
    }

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

    public void setMine() {
        this.isMine = true;
    }

    public void setValue(int value) {
        this.value = value;
    }

    private void setColor() {
        this.color = StaticValues.COLORS[value];
    }

    public void reveilAction(Point mouseLocation) {
        if (collidePoint(mouseLocation)) {
            if (this.state == FieldState.UNKNOWN) {
                this.state = FieldState.REVEILED;
                this.setColor();
                if (this.isMine) {
                    this.setText("O");
                    System.out.println("BOOM!");
                }
                else {
                    this.setText("" + value);
                    System.out.println(value);
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
