package src.core;

import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import src.core.StaticValues.SceneTag;
import src.scenes.Menu;
import src.scenes.Scene;
import src.scenes.SceneHandler;

/**
 * Handles Events regarding the gameloop.
 * @see Renderer
 * @see Controller
 * @see SceneHandler
 */
public class Gameloop {
    private Renderer renderer;
    private Controller controller;
    private Menu menu;
    private SceneHandler sceneHandler;

    /**
     * Sets up a Gameloop Object, initializes Renderer, Controller and Scene.
     */
    public Gameloop() {
        this.renderer = new Renderer(false);
        this.controller = new Controller();
        this.menu = new Menu();
        this.sceneHandler = new SceneHandler(menu);
    }

    /**
     * Starts the gameloop. Sets up the Renderer, SceneHandler and Controller
     * and reacts to Game Events
     */
    public void start() {
        this.renderer.setScene(this.menu);
        this.sceneHandler.setScene(this.menu, SceneTag.NEW);
        this.controller.setButtonList(this.menu.getButtons());
        this.controller.setFieldList(this.menu.getFields());
        this.controller.setupListeners(this.renderer.canvas);
        this.menu.start();

        ActionListener updateTask = updateEvent -> {
            Scene activeScene = sceneHandler.getActive();
            updateScene(activeScene);
            this.renderer.repaint();
        };

        new Timer(60, updateTask).start();
    }

    /**
     * Takes the active Scene and checks, if the Scene needs to be changed or not.  
     * If the Scene is changed, the Components of the new Scene will be set for the
     * Controller, the new Scene will be set as active Scene and the new Scene will
     * be set for the Renderer.  
     * At the end, the Mouse location will be updated for the current active Scene
     * @param activeScene
     */
    private void updateScene(Scene activeScene) {
        Scene newScene = activeScene.getNewScene();
        if (this.sceneHandler.sceneCheck(newScene)) {
            this.sceneHandler.setScene(newScene, SceneTag.NEW);
            this.sceneHandler.startNew();
            this.renderer.setScene(this.sceneHandler.getActive());
            this.controller.setButtonList(newScene.getButtons());
            this.controller.setFieldList(newScene.getFields());
        }
        Point mouseLocation = this.controller.getMousePos();
        activeScene.updateMouseLocation(mouseLocation.x, mouseLocation.y);
        activeScene.update();
    }
}