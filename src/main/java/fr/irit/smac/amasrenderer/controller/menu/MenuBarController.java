package fr.irit.smac.amasrenderer.controller.menu;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;

/**
 * This controller is related to the menu bar
 */
public class MenuBarController extends LoadSecondaryWindowController {

    private static final Logger LOGGER = Logger.getLogger(MenuBarController.class.getName());

    private static final String FILE_CHOOSER = "Choisir un fichier de configuration";

    private static final String EXTENSION_FILTER_DESCRIPTION = "JSON files (*.json)";

    private static final String EXTENSION_FILTER_EXTENSION = "*.json";

    @FXML
    MenuBar menuBar;

    /**
     * When the load menuItem is clicked, the data of the chosen JSON file
     * allows to generate the infrastructure, the tools and the graph of agents
     */
    @FXML
    public void clickMenuLoad() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(FILE_CHOOSER);
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(EXTENSION_FILTER_DESCRIPTION,
            EXTENSION_FILTER_EXTENSION);
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(this.window);

        if (file != null) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                InfrastructureModel infrastructure = mapper.readValue(file, InfrastructureModel.class);
                InfrastructureService.getInstance().updateInfrastructureFromModel(infrastructure);
            }
            catch (IOException e) {
                LOGGER.log(Level.SEVERE, "Impossible to read this file.", e);
            }
        }
    }

    /**
     * When the exit menuItem is clicked, the program is closed
     */
    @FXML
    public void clickMenuExit() {

        Platform.exit();
        System.exit(0);
    }

    /**
     * When the save menuItem is clicked, the informations about the
     * infrastructure (and so about the tools and the agents) are saved in a
     * JSON file
     */
    @FXML
    public void clickMenuSave() {

        File file = new FileChooser().showSaveDialog(this.window);

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try {
            if (file != null)
                mapper.writeValue(file,
                    InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * When the documentation menuItem is clicked, a window showing the
     * documentation is opened
     */
    @FXML
    public void clickDocumentation() {

        this.loadFxmlIndependent("view/help/Documentation.fxml", true);
    }
}
