package fr.irit.smac.amasrenderer.controller.menu;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.AgentGraphModel;
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

    private GraphService          graphService          = GraphService.getInstance();
    private ToolService           toolService           = ToolService.getInstance();
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
            AgentGraphModel tmp = mapper.readValue(file, AgentGraphModel.class);
            GraphService.getInstance().getModel().setGraphMap(tmp.getGraphMap());
            Map<String, Object> graphMap = GraphService.getInstance().getModel().getGraphMap();
            graphService.createAgentGraphFromMap(graphMap);
            toolService.createServicesFromMap(graphMap);
            infrastructureService.createInfrastructuresFromMap(graphMap);
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
