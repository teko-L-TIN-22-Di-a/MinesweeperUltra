package src.components;

import src.core.StaticValues.FieldState;

public class Field extends Component{

    private FieldState state;
    private boolean isMine;
    private int surroundingMines;
    private Textfield text;
    
    public Field(int width, int height, int x, int y) {
        super(width, height, x, y);
        this.state = FieldState.UNKNOWN;
        this.isMine = false;
        this.text = new Textfield(x, y, null);
    }

    public void reveilAction() {
        if (this.state == FieldState.UNKNOWN) {
            this.state = FieldState.REVEILED;
            if (this.isMine) {
                this.text.setText("O");
            }
            else {
                this.text.setText("" + surroundingMines);
            }
        }
    }

    public void flagAction() {
        if (this.state == FieldState.UNKNOWN) {
            this.state = FieldState.FLAGGED;
            this.text.setText("F");
        }
        else if (this.state == FieldState.FLAGGED) {
            this.state = FieldState.UNKNOWN;
            this.text.setText("");
        }
    }
}
