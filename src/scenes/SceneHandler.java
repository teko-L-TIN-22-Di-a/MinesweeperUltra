package src.scenes;

import src.core.StaticValues.SceneTag;

/**
 * Provides functionalities to switch between scenes.
 * @see Scene
 */
public class SceneHandler {
    private Scene activeScene, previousScene, newScene;

    /**
     * Takes a scene to set as active.
     * @param activeScene first scene to start
     */
    public SceneHandler(Scene activeScene) {
        setScene(activeScene, SceneTag.ACTIVE);
    }
    
    /**
     * Compares the TAGs from active and new scene.
     * @param newScene Scene object to be compared
     * @return boolean: true, if the new scene is different
     */
    public boolean sceneCheck(Scene newScene) {
        if (activeScene != newScene) {
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * Sets the activeScene  as previousScene 
     * and the newScene  as activeScene
     * @see setScene
     */
    public void startNew() {
        this.activeScene.stop();
        setScene(activeScene, SceneTag.PREVIOUS);
        this.newScene.start();
        setScene(newScene, SceneTag.ACTIVE);
    }

    /**
     * Sets the previousScene  as newScene
     * and the activeScene  as previousScene,
     * then calls startNew() method.
     * @see setScene
     * @see startNew
     */
    public void startPrevious() {
        setScene(this.previousScene, SceneTag.NEW);
        setScene(this.activeScene, SceneTag.PREVIOUS);
        startNew();        
    }

    /**
     * Sets the provided Scene object to one of the Scene variables,
     * depending on the given SceneTag
     * @param scene Scene object which should be set
     * @param tag   SceneTag object, determines, which varaible is set.
     * @see Scene
     * @see SceneTag
     */
    public void setScene(Scene scene, SceneTag tag) {
        switch (tag) {
            case ACTIVE:
                this.activeScene = scene;
                break;
            case PREVIOUS:
                this.previousScene = scene;
                break;
            case NEW:
                this.newScene = scene;
                break;
        }
    }

    /**
     * Returns the Scene object activeScene
     * @return Scene object activeScene
     */
    public Scene getActive() {
        return this.activeScene;
    }

    /**
     * Returns the Scene object previousScene
     * @return Scene object previousScene
     */
    public Scene getPrevious() {
        return this.previousScene;
    }

    /**
     * Returns the Scene object newScene
     * @return Scene object newScene
     */
    public Scene getNew() {
        return this.newScene;
    }
}
