package fr.irit.smac.amasrenderer.controller.attribute;

import java.net.URL;
import java.util.ResourceBundle;

import org.graphstream.graph.Node;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.StockModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
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
public class TreeModifyController implements Initializable {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private TreeView<String> tree;

    private TreeItem<String> oldTree;

    private StockModel stock;

    private Stage dialogStage;

    private String baseAgentName;

    private Node node;

    private String newAgentName = null;

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

    /**
     * Sets the stock.
     *
     * @param s
     *            the new stock
     */
    public void setStock(StockModel s) {
        baseAgentName = s.getRoot().getValue();
        stock = s;
        tree.setRoot(deepcopy(s.getRoot()));
        this.oldTree = new TreeItem<>();
        this.oldTree = deepcopy(tree.getRoot());
        tree.getRoot().setExpanded(true);
    }

    /**
     * Confirm button. Sets the new tree as the node tree, and exit this window
     */
    public void confirmButton() {
        stock.setRoot(tree.getRoot());
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
        this.tree.setRoot(deepcopy(oldTree));
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

        tree.setCellFactory(p -> new MenuAttributesTreeCell(tree));

    }

    private static class MenuAttributesTreeCell extends TextFieldTreeCell<String> {
        private ContextMenu menu = new ContextMenu();
        private TreeView<String> tree;

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public MenuAttributesTreeCell(TreeView<String> tree) {
            super(new DefaultStringConverter());
            this.tree = tree;
            menu.setId("treeAttributeItem");
            MenuItem renameItem = new MenuItem("Renommer");
            renameItem.setId("renameAttributeItem");
            menu.getItems().add(renameItem);
            renameItem.setOnAction(e -> startEdit());

            MenuItem addItem = new MenuItem("Ajouter");
            menu.getItems().add(addItem);
            addItem.setId("addAttributeItem");
            addItem.setOnAction(e -> {
                TreeItem newItem = new TreeItem<String>("Nouvel attribut");
                getTreeItem().getChildren().add(newItem);
            });

            MenuItem removeItem = new MenuItem("Supprimer");
            menu.getItems().add(removeItem);
            removeItem.setId("removeAttributeItem");
            removeItem.setOnAction(e -> getTreeItem().getParent().getChildren().remove(getTreeItem()));
        }

        private boolean isValidExpression(String newValue){
            return newValue.split(" : ").length != 2 ^ (!newValue.split(" : ")[0].trim().isEmpty() && !newValue.split(" : ")[1].trim().isEmpty() );
        }
        
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (!empty && !isEditing()) {
                setContextMenu(menu);
            }
        }

        @Override
        public void commitEdit(String newValue) {

            if (newValue.trim().isEmpty()) {
                return;
            }
            else if(this.getTreeItem() != tree.getRoot() && !isValidExpression(newValue)){
                return;
            }
            super.commitEdit(newValue);
        }
    }

}