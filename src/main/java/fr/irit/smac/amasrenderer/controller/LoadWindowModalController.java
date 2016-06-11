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

    public void loadFxml(Window window, String resourcePath, boolean isResizable) {

        loaderWindowModal = new FXMLLoader();
        loaderWindowModal.setLocation(Main.class.getResource(resourcePath));

        Pane root = null;

        try {
            root = loaderWindowModal.load();
            if (!Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass()
                .contains(Const.SECONDARY_WINDOW)) {
                Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().add(Const.SECONDARY_WINDOW);
            }
            stageWindowModal = new Stage();
            stageWindowModal.initModality(Modality.WINDOW_MODAL);
            stageWindowModal.initOwner(window);
            Scene scene = new Scene(root);
            stageWindowModal.setScene(scene);
            stageWindowModal.setResizable(isResizable);
            scene.setFill(Color.BLACK);
            initDialogModalController();
            stageWindowModal.showAndWait();

        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The loading of the fxml has failed", e);
        }
    }

    public abstract void initDialogModalController() throws IOException;
}
