package fr.irit.smac.amasrenderer.controller;

import java.net.URL;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.model.Stock;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;

/**
 * The Class TreeModifyController. Manage the modal window opening to modify
 * attributes
 */
public class TreeModifyController implements Initializable {

    /** The confirm button. */
    @FXML
    private Button confButton;

    /** The cancel button. */
    @FXML
    private Button cancButton;

    /** The attribute tree. */
    @FXML
    private TreeView<String> tree;


    /** The stock var of the clicked node. */
    private Stock stock;

    /** The dialog stage. */
    private Stage dialogStage;

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
     * Sets the stock.
     *
     * @param s
     *            the new stock
     */
    public void setStock(Stock s) {
        stock = s;
        tree.setRoot(deepcopy(s.getRoot()));
    }

    /**
     * Confirm button. sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {
        stock.setRoot(tree.getRoot());
        dialogStage.close();
    }

    /**
     * Cancel button. just exit this window
     */
    @FXML
    public void cancelButton() {
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
        TreeItem<String> copy = new TreeItem<String>(item.getValue());
        for (TreeItem<String> child : item.getChildren()) {
            copy.getChildren().add(deepcopy(child));
        }
        return copy;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
            @Override
            public TreeCell<String> call(TreeView<String> p) {
                return new RenameMenuTreeCell();
            }
        });
    }

    private static class RenameMenuTreeCell extends TextFieldTreeCell<String> {
        private ContextMenu menu = new ContextMenu();

        public RenameMenuTreeCell() {
            super(new DefaultStringConverter());

            MenuItem renameItem = new MenuItem("Renommer");
            menu.getItems().add(renameItem);
            renameItem.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    startEdit();
                }
            });

            MenuItem addItem = new MenuItem("Ajouter");
            menu.getItems().add(addItem);
            addItem.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    TreeItem newItem = new TreeItem<String>("Nouvel attribut");
                    getTreeItem().getChildren().add(newItem);
                }
            });

            MenuItem removeItem = new MenuItem("Supprimer");
            menu.getItems().add(removeItem);
            removeItem.setOnAction(new EventHandler() {
                public void handle(Event t) {
                    getTreeItem().getParent().getChildren().remove(getTreeItem());
                }
            });
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (!isEditing()) {
                setContextMenu(menu);
            }
        }
    }

}