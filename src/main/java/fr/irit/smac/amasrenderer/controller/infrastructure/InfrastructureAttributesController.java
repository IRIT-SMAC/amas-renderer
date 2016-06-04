package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.controller.menu.MenuAttributesTreeCellController;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

public class InfrastructureAttributesController implements Initializable{

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private TreeView<String> tree;
 
    private Stage dialogStage;

    private InfrastructureModel infra;

    /**
     * Confirm button. Sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {
        
        AttributesService.getInstance().updateAttributesMap(tree.getRoot().getValue(), tree.getRoot(), infra.getAttributesMap());
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    /**
     * Cancel button. Just exit this window
     */
    @FXML
    public void cancelButton() {
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }
    
    /**
     * Sets the stage.
     *
     * @param stage
     *            the new stage
     */
    public void setStage(Stage stage) {
        dialogStage = stage;
    }
    
    public void init(InfrastructureModel infra) {
        
        this.infra = infra;
        TreeItem<String> myItem = new TreeItem<>(infra.getName());
        tree.setRoot(myItem);
        HashMap<String, Object> infrastructure = (HashMap<String, Object>) InfrastructureService.getInstance().getInfrastructure().get(0).getAttributesMap();
        AttributesService.getInstance().fillAttributes(infrastructure, myItem);
    }  
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);

        tree.setCellFactory(p -> new MenuAttributesTreeCellController(tree));

    }
}
