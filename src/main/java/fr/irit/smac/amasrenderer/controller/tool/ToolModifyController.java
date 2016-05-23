package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

/**
 * The Class TreeModifyController. Manage the modal window opening to modify
 * attributes
 */
public class ToolModifyController implements Initializable {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private Button delButton;

    @FXML
    private TreeView<String> tree;

    private TreeItem<String> value;

    private Stage dialogStage;

    private String key;

    private ListView<String> list;

    private String baseRootName;

    private String newServiceName = null;

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
     * sets the node to be modified
     * 
     * @param node
     *            the node to modify
     */
    public void setKey(String label) {
        this.key = label;
    }

    /**
     * Sets the map of the attributes
     * 
     * @param attributeMap
     *            the attributeMap
     */
    public void setAttributeMap(Map<String, TreeItem<String>> attributeMap) {
        ToolService.getInstance().setAttributesMap(attributeMap);
    }

    /**
     * Initialize the controller
     * 
     * @param attributeMap
     *            the map of attributes
     * @param list
     *            the list of tools
     */
    public void init(Map<String, TreeItem<String>> attributeMap, ListView<String> list) {
        setAttributeMap(attributeMap);
        setKey(list.getSelectionModel().getSelectedItem());
        this.list = list;
        tree.setRoot(deepcopy(attributeMap.get(key)));
        baseRootName = key;
    }

    /**
     * deletes the service ( no confirmation )
     */
    @FXML
    public void deleteButton() {
        list.getItems().remove(key);
        ToolService.getInstance().getAttributes().remove(key);
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    /**
     * Confirm button. sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {
        value = tree.getRoot();
        ToolService.getInstance().getAttributes().put(key, value);
        newServiceName = tree.getRoot().getValue();
        if (newServiceName != baseRootName) {
            list.getItems().remove(key);
            key = newServiceName;
            list.getItems().add(key);
            System.out.println(key);
            ToolService.getInstance().getAttributes().put(key,
                ToolService.getInstance().getAttributes().remove(baseRootName));
        }
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    /**
     * Cancel button. just exit this window
     */
    @FXML
    public void cancelButton() {
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    /**
     * Deepcopy. recursively copies the tree, to be able to modify it without
     * changing the original
     * 
     * @param item
     *            the the tree to copy
     * @return a copy of the tree item the tree item
     */
    private TreeItem<String> deepcopy(TreeItem<String> item) {
        TreeItem<String> copy = new TreeItem<>(item.getValue());
        for (TreeItem<String> child : item.getChildren()) {
            copy.getChildren().add(deepcopy(child));
        }
        return copy;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        tree.setCellFactory(p -> new ToolAttributesTreeCell());
    }

    private static class ToolAttributesTreeCell extends TextFieldTreeCell<String> {
        private ContextMenu menu = new ContextMenu();

        public ToolAttributesTreeCell() {
            super(new DefaultStringConverter());

            menu.setId("treeAttributeItem");
            MenuItem renameItem = new MenuItem("Renommer");
            renameItem.setId("renameAttributeItem");
            menu.getItems().add(renameItem);

            renameItem.setOnAction(e -> startEdit());

            MenuItem addItem = new MenuItem("Ajouter");
            menu.getItems().add(addItem);
            addItem.setId("addAttributeItem");
            addItem.setOnAction(e -> getTreeItem().getChildren().add(new TreeItem<String>("Nouvel attribut")));

            MenuItem removeItem = new MenuItem("Supprimer");
            menu.getItems().add(removeItem);
            removeItem.setId("removeAttributeItem");
            removeItem.setOnAction(e -> getTreeItem().getParent().getChildren().remove(getTreeItem()));
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEditing()) {
                setContextMenu(menu);
            }
        }

        @Override
        public void commitEdit(String newValue) {

            if (newValue.trim().isEmpty()) {
                return;
            }
            super.commitEdit(newValue);
        }

    }

}