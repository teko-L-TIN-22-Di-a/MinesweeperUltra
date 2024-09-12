package src.scenes;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Clip;

import src.assets.Loader;
import src.components.Button;
import src.components.Component;
import src.components.Field;

/**
 * Baseclass for any Scene.
 * @see Component
 * @see Button
 */
public class Scene {
    private List<Component> components;
    private List<Button> buttons;
    private List<Field> fields;
    private boolean menu, active;
    private String TAG;
    private Scene newScene;
    private Point mousepoint;
    private int counter;
    private Clip bgm;

    /**
     * Initialises the lists Entities, Components and Buttons.
     * Properties from these lists will be drawn in the Renderer.
     * @param isMenu determines, if the Scene is considered a Menu
     * @param TAG scene identifier
     */
    public Scene(boolean isMenu, String TAG) {
        System.out.println("> start scene " + TAG);
        setTAG(TAG);
        this.bgm = null;
        this.counter = 0;
        this.components = new ArrayList<>();
        this.buttons = new ArrayList<>();
        this.fields = new ArrayList<>();
        this.menu = isMenu;

        // Fills the newScene Variable with itself to indicate, that the scene does not have to be changed
        setNewScene(this);
    }

    /**
     * Takes the name of a audio file, loads the resource and sets it as bgm.
     * @param filename name of the audio file to be loaded.
     */
    public void setBGM(String filename) {
        Clip audio = Loader.loadSound(filename);
        this.bgm = audio;
    }

    /**
     * Changes the Backgroundmusic and starts the new one from beginning
     */
    public void changeBGM(String filename) {
        this.bgm.stop();
        this.bgm = Loader.loadSound(filename);
        this.bgm.setFramePosition(0);
        this.bgm.loop(-1);
    }

    /**
     * Collects all operations to be done, when the Scene is started.
     */
    public void start() {
        this.active = true;
        if (this.bgm != null) {
            this.bgm.loop(-1);
        }
    }

    /**
     * Collects all operations to be done, when the Scene is stoped.
     */
    public void stop() {
        this.active = false;
        if (this.bgm != null) {
            this.bgm.stop();
            this.bgm.setFramePosition(0);
        }
    }

    /**
     * Calls the update method of all Components.
     */
    public void update() {
        this.counter += 1;

        for (Component component: this.components) {
            component.update();
        }
    }

    /**
     * Returns the counter of the Scene.
     * The counter is used for time based events.
     * @return Scene counter.
     */
    public int getCounter() {
        return this.counter;
    }

    /**
     * Returns a boolean indicating if the Scene is considered a Menu.
     * @return true, if the Scene is considered a Menu
     */
    public boolean getMenu() {
        return this.menu;
    }

    /**
     * Set the Background image for your Scene object.
     * If not set the Background will be black.
     * Creates a new Component object to hold the image.
     * Registers the image to the components list of your scene.
     * @param bgName the name like "image.png" of your image in src/resources/assets/
     * @see Component
     */
    public void setBG(String bgName) {
        Component bg = new Component(bgName, 0, 0);
        bg.setLocation(bg.getWidth()/2, bg.getHeight());
        registerComponent(bg);
    }

    /**
     * Returns true, if the Scene is currently active.
     * @return true, if the Scene is currently active.
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * Takes x and y coordinates and stores them in the mousepoint variable
     * @param x x coordinate a new location
     * @param y y coordinate a new location
     */
    public void updateMouseLocation(int x, int y) {
        this.mousepoint = new Point(x, y);
    }

    /**
     * returns the private mousepoint variable
     * @return  the private Point object mousepoint
     * @see     Point
     */
    public Point getMousePoint() {
        return this.mousepoint;
    }

    /**
     * set the private newScene variable to a new scene
     * @param scene     a Scene object to set a new scene
     * @see             Scene
     */
    public void setNewScene(Scene scene) {
        this.newScene = scene;
    }

    /**
     * returns the private newScene variable
     * @return  the private Scene object newScene
     * @see     Scene
     */
    public Scene getNewScene() {
        return this.newScene;
    }

    /**
     * set the private TAG variable to a string
     * @param tag a String object to set a new location
     * @see String
     */
    public void setTAG(String tag) {
        this.TAG = tag;
    }

    /**
     * returns the private TAG variable
     * @return  the private String object TAG
     * @see String
     */
    public String getTAG() {
        return this.TAG;
    }

    /**
     * adds an Component object to the private list components
     * @param component a Component object to add to the List components
     * @see Component
     */
    public void registerComponent(Component component) {
        this.components.add(component);
    }

    /**
     * removes an Component Object from the private list components
     * @param component a Component object to remove from the List components
     * @see Component
     */
    public void unregisterComponent(Component component) {
        this.components.remove(component);
    }

    /**
     * returns the private list of Components
     * @return the private List object components
     * @see Component
     */
    public List<Component> getComponents() {
        return this.components;
    }

    /**
     * adds an Button Object to the private list buttons
     * @param button a Button object to add to the List buttons
     * @see Button
     */
    public void registerButton(Button button) {
        this.buttons.add(button);
    }

    /**
     * removes an Button Object from the private list buttons
     * @param button a Button object to remove from the List buttons
     * @see Button
     */
    public void unregisterButton(Button button) {
        this.buttons.remove(button);
    }

    /**
     * returns the private List of Buttons
     * @return the private List object buttons
     * @see Button
     */
    public List<Button> getButtons() {
        return this.buttons;
    }

    /**
     * Takes a Field to add it to the Fields List.
     * @param field Field to add to the Fields List
     */
    public void addField(Field field) {
        this.fields.add(field);
    }

    /**
     * Returns the list of Fields associated with the Scene.
     * @return list of Fields
     */
    public List<Field> getFields() {
        return this.fields;
    }
}