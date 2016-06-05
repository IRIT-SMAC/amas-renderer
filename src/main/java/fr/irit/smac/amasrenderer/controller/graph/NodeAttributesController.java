package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.graphstream.graph.Node;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.controller.menu.MenuAttributesTreeCellController;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

/**
 * The Class TreeModifyController. Manage the modal window opening to modify
 * attributes
 */
public class NodeAttributesController implements Initializable {

    @FXML
    private Button           confButton;
    @FXML
    private Button           cancButton;
    @FXML
    private TreeView<String> tree;
    private Stage            dialogStage;
    private String           baseAgentName;
    private Node             node;
    private String           newAgentName = null;
    private String           id;

    /**
     * Confirm button. Sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {

        AttributesService.getInstance().updateAttributesMap(tree.getRoot().getValue(), tree.getRoot(),
            (Map<String, Object>) GraphService.getInstance().getAgentMap().get(id));

        newAgentName = tree.getRoot().getValue();
        if (newAgentName != baseAgentName) {
            node.setAttribute("ui.label", newAgentName);
        }
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

    /**
     * Sets the node to be modified
     * 
     * @param node
     *            the node to modify
     */
    public void setNode(Node node) {
        this.node = node;
    }

    public void init(String id) {

        @SuppressWarnings("unchecked")
        HashMap<String, Object> agent = (HashMap<String, Object>) GraphService.getInstance().getAgentMap()
            .get(id);
        this.id = id;
        TreeItem<String> myItem = new TreeItem<>(id);
        tree.setRoot(myItem);
        AttributesService.getInstance().fillAttributes(agent, myItem);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        tree.setCellFactory(p -> new MenuAttributesTreeCellController(tree));
    }

}