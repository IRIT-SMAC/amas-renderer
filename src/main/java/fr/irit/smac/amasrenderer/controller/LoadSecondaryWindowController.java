package fr.irit.smac.amasrenderer.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.irit.smac.amasrenderer.AmasRenderer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

/**
 * This abstract class contains method to load a new window (either a modal
 * window or an independent window)
 */
public abstract class LoadSecondaryWindowController {

    protected Stage stageSecondaryWindow;

    protected FXMLLoader loaderSecondaryWindow;

    private static final Logger LOGGER = Logger.getLogger(LoadSecondaryWindowController.class.getName());

    protected Window window;

    /**
     * Load a new modal window
     * 
     * @param window
     *            the owner window
     * @param resourcePath
     *            the path of the fxml
     * @param isResizable
     *            true is the window can be resized, false otherwise
     * @param argsInit
     *            the arguments required to init the controller
     */
    public void loadFxml(Window window, String resourcePath, boolean isResizable, Object... argsInit) {

        try {
            this.loaderSecondaryWindow = new FXMLLoader();
            this.loaderSecondaryWindow.setLocation(AmasRenderer.class.getResource(resourcePath));
            Pane root = this.loaderSecondaryWindow.load();
            this.stageSecondaryWindow = new Stage();
            this.stageSecondaryWindow.initModality(Modality.WINDOW_MODAL);
            this.stageSecondaryWindow.initOwner(window);
            Scene scene = new Scene(root);
            this.stageSecondaryWindow.setScene(scene);
            this.stageSecondaryWindow.setResizable(isResizable);
            scene.setFill(Color.BLACK);
            ISecondaryWindowController controller = this.loaderSecondaryWindow.getController();
            controller.init(this.stageSecondaryWindow, argsInit);
            this.stageSecondaryWindow.showAndWait();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The loading of the fxml has failed", e);
        }
    }

    /**
     * Load an independent window, such as the documentation window
     * 
     * @param resourcePath
     *            the path of the fxml
     * @param isResizable
     *            true if the window can be resized, false otherwise
     */
    public void loadFxmlIndependent(String resourcePath, Object... argsInit) {

        FXMLLoader loaderWindowModal = new FXMLLoader();
        loaderWindowModal.setLocation(LoadSecondaryWindowController.class.getResource("../" + resourcePath));

        Pane root = null;

        try {
            
            root = loaderWindowModal.load();
            Stage stageWindowModal = new Stage();
            stageWindowModal.initModality(Modality.WINDOW_MODAL);
            Scene scene = new Scene(root);
            stageWindowModal.setScene(scene);
            scene.setFill(Color.BLACK);
            
            ISecondaryWindowController controller = loaderWindowModal.getController();
            if (controller != null) {
                controller.init(stageWindowModal, argsInit);
            }
            stageWindowModal.show();

        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The loading of the fxml has failed", e);
        }
    }

    public void setWindow(Window window) {
        this.window = window;
    }
}
