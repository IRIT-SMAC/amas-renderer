package fr.irit.smac.amasrenderer.controller.menu;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.AgentGraphModel;
import fr.irit.smac.amasrenderer.service.GraphService;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
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
            GraphService.getInstance().getModel().addAttribute("ui.quality");
            GraphService.getInstance().getModel().addAttribute("layout.quality", 4);
            GraphService.getInstance().getModel().addAttribute("ui.antialias");
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

    @FXML
    public void clickMenuSave() {

        File file = new FileChooser().showSaveDialog(Main.getMainStage().getScene().getWindow());

        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        updateGraphMap();

        try {
            mapper.writeValue(file, GraphService.getInstance().getModel().getGraphMap());
        }
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void updateGraphMap() {
        HashMap<String, Object> newMap = new HashMap<String, Object>();
        String infra = InfrastructureService.getInstance().getInfrastructure().get(0);
        newMap.put("className", infra);
        for (String service : ToolService.getInstance().getTools()) {
            newMap.put(service, createToolEntry(service));
        }
    }

    private HashMap<String, Object> createToolEntry(String service) {
        TreeItem<String> attributes = ToolService.getInstance().getAttributes().get(service);
        return exploreTree(attributes);
    }

    private HashMap<String, Object> exploreTree(TreeItem<String> attributes) {

        HashMap<String, Object> entry = new HashMap<String, Object>();

        for (TreeItem<String> child : attributes.getChildren()) {
            if (!child.isLeaf()) {
                entry.put(attributes.getValue(), exploreTree(child));
            }
            else {
                entry.put(attributes.getValue(), child.getValue());
            }
        }

        return entry;
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
