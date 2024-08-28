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
    private Renderer bobRoss;
    private Controller controller;
    private Menu menu;
    private SceneHandler sceneHandler;

    /**
     * Sets up a Gameloop Object, initializes Renderer, Controller and Scene.
     */
    public Gameloop() {
        bobRoss = new Renderer(true);
        controller = new Controller();
        menu = new Menu();
        sceneHandler = new SceneHandler(menu);
    }

    /**
     * Starts the gameloop. Sets up the Renderer, SceneHandler and Controller
     * and reacts to Game Events
     */
    public void start() {
        bobRoss.setScene(menu);
        sceneHandler.setScene(menu, SceneTag.NEW);
        controller.setButtonList(menu.getButtons());
        controller.setupListeners(bobRoss.canvas);
        menu.start();

        ActionListener updateTask = updateEvent -> {
            Scene activeScene = sceneHandler.getActive();
            updateScene(activeScene);
            bobRoss.repaint();
        };

        new Timer(60, updateTask).start();
    }

    private void updateScene(Scene activeScene) {
        Scene newScene = activeScene.getNewScene();
        if (sceneHandler.sceneCheck(newScene)) {
            sceneHandler.setScene(newScene, SceneTag.NEW);
            sceneHandler.startNew();
            bobRoss.setScene(sceneHandler.getActive());
            controller.setButtonList(newScene.getButtons());
        }
        Point mouseLocation = controller.getMousePos();
        activeScene.updateMouseLocation(mouseLocation.x, mouseLocation.y);
        activeScene.update();
        activeScene.setM1down(controller.getM1down());
    }
}