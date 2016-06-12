package fr.irit.smac.amasrenderer.controller.tool;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController;
import fr.irit.smac.amasrenderer.controller.LoadSecondaryWindowController.IParentStyle;
import fr.irit.smac.amasrenderer.controller.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.controller.attributes.AttributesTreeCell;
import fr.irit.smac.amasrenderer.controller.tool.ToolDeletionController.IParentWindowModal;
import fr.irit.smac.amasrenderer.model.ToolModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

/**
 * The Class TreeModifyController. Manage the modal window opening to modify
 * attributes
 */
public class ToolAttributesController extends LoadSecondaryWindowController implements Initializable, IParentWindowModal {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private ImageView delButton;

    @FXML
    private TreeView<String> tree;

    private Stage stage;

    private ListView<ToolModel> list;

    private ToolModel tool;

    /**
     * Initialize the controller
     * 
     * @param attributeMap
     *            the map of attributes
     * @param list
     *            the list of tools
     */
    public void init(Stage stage, ListView<ToolModel> list, String name, ToolModel tool) {

        this.list = list;
        this.tool = tool;
        this.stage = stage;
        TreeItem<String> myItem = new TreeItem<>(name);
        tree.setRoot(myItem);
        HashMap<String, Object> service = (HashMap<String, Object>) tool.getAttributesMap();
        AttributesService.getInstance().fillAttributes(service, myItem, tool);
    }

    /**
     * deletes the service ( no confirmation )
     */
    @FXML
    public void deleteButton() {

        this.loadFxml(this.window, "view/tool/ToolDeletion.fxml", false);
    }

    /**
     * Confirm button. sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {

        AttributesService.getInstance().updateAttributesMap(tree.getRoot().getValue(), tree.getRoot(),
            tool.getAttributesMap(), tool);
        stage.close();
    }

    /**
     * Cancel button. just exit this window
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
    public void initDialogModalController() throws IOException {

        ToolDeletionController controller;
        controller = (ToolDeletionController) loaderWindowModal.getController();
        controller.init(this.stageWindowModal, list.getSelectionModel().getSelectedIndex(),
            list.getSelectionModel().getSelectedItem().getName());
        controller.setParentWindowModal(this);
    }

    @Override
    public void closeWindow() {
        this.stage.close();
    }

}