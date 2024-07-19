package src;

import javax.swing.SwingUtilities;

/**
 * Main class. Serves as the entry Point to the app.
 */
public class Main {
    
    /**
     * Not yet defined.
     */
    public Main() {
        //empty
    }

    /**
     * Executabel for main class.
     * @param args  None
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(Main::new);
    }
}