package fr.irit.smac.amasrenderer.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

public abstract class LoadWindowModalController {

    protected Stage stageWindowModal;

    protected FXMLLoader loaderWindowModal;

    private static final Logger LOGGER = Logger.getLogger(LoadWindowModalController.class.getName());

    private IParentStyle parentStyle;

    public void loadFxml(Window window, String resourcePath, boolean isResizable) {

        loaderWindowModal = new FXMLLoader();
        loaderWindowModal.setLocation(Main.class.getResource(resourcePath));

        Pane root = null;

        try {
            root = loaderWindowModal.load();
            stageWindowModal = new Stage();
            stageWindowModal.initModality(Modality.WINDOW_MODAL);
            stageWindowModal.initOwner(window);
            Scene scene = new Scene(root);
            stageWindowModal.setScene(scene);
            stageWindowModal.setResizable(isResizable);
            scene.setFill(Color.BLACK);
            initDialogModalController();

            if (parentStyle != null) {
                parentStyle.setBackground();

                stageWindowModal.setOnCloseRequest(
                    e -> parentStyle.setForeground());
            }

            stageWindowModal.showAndWait();

            if (parentStyle != null) {
                parentStyle.setForeground();
            }

        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The loading of the fxml has failed", e);
        }
    }

    public void loadFxmlIndependent(Window window, String resourcePath, boolean isResizable) {

        FXMLLoader loaderWindowModal = new FXMLLoader();
        loaderWindowModal.setLocation(Main.class.getResource(resourcePath));

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

    public void setParentStyle(IParentStyle parentStyle) {
        this.parentStyle = parentStyle;
    }

    public abstract void initDialogModalController() throws IOException;

    public interface IParentStyle {

        public void setBackground();

        public void setForeground();
    }

}
