package fr.irit.smac.amasrenderer.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.irit.smac.amasrenderer.AmasRenderer;
import fr.irit.smac.amasrenderer.model.IModel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class LoadSecondaryWindowController {

    protected Stage stageSecondaryWindow;

    protected FXMLLoader loaderSecondaryWindow;

    private static final Logger LOGGER = Logger.getLogger(LoadSecondaryWindowController.class.getName());

    protected Window window;

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

    public void loadFxmlIndependent(Window window, String resourcePath, boolean isResizable) {

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
