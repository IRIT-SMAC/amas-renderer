package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.AgentModel;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.service.GraphService;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.util.attributes.AttributesTreeCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * This controller is related to the attributes of an agent
 */
public class NodeAttributesController implements Initializable, ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private TreeView<String> tree;

    private Stage stage;

    private AgentModel node;

    private String newAgentName = null;

    private String id;

    private AttributesService attributesService = AttributesService.getInstance();

    /**
     * When the confirm button is clicked, the attributes are updated depending
     * on the tree
     */
    @SuppressWarnings("unchecked")
    @FXML
    public void confirmButton() {

        this.attributesService.updateAttributesMap(tree.getRoot().getValue(), tree.getRoot(),
            (Map<String, Object>) GraphService.getInstance().getAgentMap().get(id), node);

        this.newAgentName = tree.getRoot().getValue();
        this.node.setAttribute(Const.GS_UI_LABEL, newAgentName);
        this.stage.close();
    }

    /**
     * When the cancel button is clicked, the attributes are not updated and the
     * window is closed
     */
    @FXML
    public void cancelButton() {
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
    }

    @Override
    public void init(Stage stage, Object... args) {

        AgentModel agent = (AgentModel) args[0];
        this.stage = stage;
        this.node = agent;
        this.id = agent.getAttribute(Const.GS_UI_LABEL);
        @SuppressWarnings("unchecked")
        HashMap<String, Object> agentMap = (HashMap<String, Object>) GraphService.getInstance().getAgentMap()
            .get(this.id);
        TreeItem<String> root = new TreeItem<>(this.id);
        this.tree.setRoot(root);
        this.attributesService.fillAttributes(agentMap, root, (IModel) agent);

        this.tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

            private final AttributesContextMenu contextMenu = new AttributesContextMenu();
            @SuppressWarnings("rawtypes")
            private final StringConverter converter = new DefaultStringConverter();

            @SuppressWarnings("unchecked")
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new AttributesTreeCell(this.contextMenu, this.converter, agent);
            }

        });
      
    }

}