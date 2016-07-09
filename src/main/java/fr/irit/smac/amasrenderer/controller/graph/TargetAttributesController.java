package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.Sprite;

import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.TargetModel;
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

public class TargetAttributesController implements Initializable, ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private TreeView<String> tree;

    private Stage stage;

    private GraphService graphService = GraphService.getInstance();
    
    private TargetModel targetModel;

    private AttributesService attributesService = AttributesService.getInstance();

    @FXML
    public void confirmButton() {

        attributesService.updateAttributesMap(tree.getRoot().getValue(), tree.getRoot(),
            targetModel.getAttributesMap(), targetModel);
        this.stage.close();
    }
    
    @FXML
    public void cancelButton() {
        
        this.stage.close();
    }
    
    @Override
    public void init(Stage stage, Object... args) {

        Sprite sprite = (Sprite) args[0];
        Edge e = (Edge) sprite.getAttachment();
        Node node = e.getSourceNode();
        this.stage = stage;
        Map<String, Object> agent = (Map<String, Object>) graphService.getAgentMap().get(node.getId());
        String id = ((String)sprite.getAttribute("id")).substring(node.getId().length(), ((String)sprite.getAttribute("id")).length());
        Map<String,Object> target = (Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) agent
            .get("knowledge")).get("targets")).get(id);

        targetModel = new TargetModel();
        targetModel.setAttributesMap(target);
        
        TreeItem<String> root = new TreeItem<>(e.getTargetNode().getId());
        this.tree.setRoot(root);
        this.attributesService.fillAttributes(targetModel.getAttributesMap(), root, targetModel);

        this.tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

            private final AttributesContextMenu contextMenu = new AttributesContextMenu();
            @SuppressWarnings("rawtypes")
            private final StringConverter converter = new DefaultStringConverter();

            @SuppressWarnings("unchecked")
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new AttributesTreeCell(this.contextMenu, this.converter, targetModel);
            }

        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
    }

}
