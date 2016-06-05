package fr.irit.smac.amasrenderer.controller.menu;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import fr.irit.smac.amasrenderer.service.GraphService;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;

/**
 * This controller manages the menu bar events
 */
public class MenuBarController {

    private GraphService graphService = GraphService.getInstance();

    private ToolService toolService = ToolService.getInstance();

    private InfrastructureService infrastructureService = InfrastructureService.getInstance();

    private static final Logger LOGGER = Logger.getLogger(MenuBarController.class.getName());

    /**
     * On click on the menu item "Charger" in the menu "Fichier". Open a file
     * chooser to load a configuration file. Generate the graph from the
     * configuration file.
     */
    @FXML
    public void clickMenuCharger() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir un fichier de configuration");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(Main.getMainStage());
        ObjectMapper mapper = new ObjectMapper();
        try {
            InfrastructureModel infrastructure = mapper.readValue(file, InfrastructureModel.class);
            InfrastructureService.getInstance().setInfrastructure(infrastructure);
            HashMap<String, Object> agentHandlerService = (HashMap<String, Object>) InfrastructureService.getInstance()
                .getInfrastructure().getAttributesMap()
                .get("agentHandlerService");
            HashMap<String, Object> agentMap = (HashMap<String, Object>) agentHandlerService.get("agentMap");
            graphService.createAgentGraphFromMap(agentMap);
            toolService.createServicesFromMap(
                InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
            infrastructureService.createInfrastructureFromMap(
                InfrastructureService.getInstance().getInfrastructure().getAttributesMap());
            GraphService.getInstance().setQualityGraph();
        }
        catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Impossible de lire le fichier spécifié.", e);
        }
    }

    /**
     * On click on the menu item "Fermer" in the menu "Fichier" Close the
     * program
     */
    @FXML
    public void clickMenuFermer() {

        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void clickMenuSave() {

        File file = new FileChooser().showSaveDialog(Main.getMainStage().getScene().getWindow());

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
     * On click on the menu item "A propos" in the menu "Aide" Open a help
     * window
     */
    @FXML
    public void clickMenuAPropos() {
        // TODO Popup à propos
        LOGGER.log(Level.INFO, "Popup à propos");
    }
}
