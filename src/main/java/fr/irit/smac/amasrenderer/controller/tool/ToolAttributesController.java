package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.IParentWindowModal;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.util.attributes.AttributesTreeCell;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * This controller is related to the attributes of a tool
 */
public class ToolAttributesController extends LoadSecondaryWindowController
    implements Initializable, IParentWindowModal, ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private ImageView delButton;

    @FXML
    private TreeView<String> tree;

    private Stage stage;

    private ToolModel tool;

    private AttributesService attributesService = AttributesService.getInstance();

    /**
     * When the delete button is clicked, the selected tool is deleted
     */
    @FXML
    public void deleteButton() {

        this.loadFxml(this.window, "view/tool/ToolDeletion.fxml", false, this, this.tool);
    }

    /**
     * When the confirm button is clecked, the attributes are updated depending
     * on the items of the tree
     */
    @FXML
    public void confirmButton() {

        attributesService.updateAttributesMap(tree.getRoot().getValue(), tree.getRoot(),
            tool.getAttributesMap(), tool);
        stage.close();
    }

    /**
     * When the cancel button is clicked, the attributes are not updated and the
     * modal window is closed
     */
    @FXML
    public void cancelButton() {

        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

            private final AttributesContextMenu contextMenu = new AttributesContextMenu();
            private final StringConverter converter = new DefaultStringConverter();

            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new AttributesTreeCell(contextMenu, converter, tool);
            }

        });
    }

    @Override
    public void closeWindow() {
        this.stage.close();
    }

    @Override
    public void init(Stage stage, Object... args) {

        ToolModel currentTool = (ToolModel) args[0];
        this.tool = currentTool;
        this.stage = stage;
        TreeItem<String> root = new TreeItem<>(currentTool.getName());
        this.tree.setRoot(root);
        HashMap<String, Object> toolAttributes = (HashMap<String, Object>) currentTool.getAttributesMap();
        this.attributesService.fillAttributes(toolAttributes, root, currentTool);
    }

}