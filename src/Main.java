import javax.swing.SwingUtilities;

import core.Gameloop;

/**
 * Main class. Serves as the entry Point to the app.
 */
public class Main {
    
    /**
     * Not yet defined.
     */
    public Main() {
        Gameloop loop = new Gameloop();
        loop.start();
    }

    /**
     * Executabel for main class.
     * @param args  None
     */
    public static void main(String args[]) {
        SwingUtilities.invokeLater(Main::new);
    }
}