package fr.irit.smac.amasrenderer.controller.menu;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.AgentModel;
import fr.irit.smac.amasrenderer.model.IConstraintFields;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.converter.DefaultStringConverter;

public class MenuAttributesTreeCellController extends TextFieldTreeCell<String> {

    private ContextMenu menu = new ContextMenu();

    private TreeView<String> tree;

    private boolean isRequired = false;

    private boolean isProtected;

    private IConstraintFields node;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MenuAttributesTreeCellController(TreeView<String> tree, IConstraintFields node) {
        super(new DefaultStringConverter());
        this.tree = tree;
        this.node = node;

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
        removeItem.setOnAction(e -> {
            getTreeItem().getParent().getChildren().remove(getTreeItem());
        });
    }

    /**
     * test if the string new value is valid
     * 
     * @param newValue
     * @return
     */
    private boolean isValidExpression(TreeItem<String> item, String newValue) {

        isRequired = false;
        isProtected = false;
        for (String requiredField : node.getRequiredKey()) {
            if (requiredField.equals(newValue)) {
                isRequired = true;
            }
        }

        for (String protectedField : node.getProtectedValue()) {
            if (protectedField.equals(newValue)) {
                isProtected = true;
            }
        }

        return !isProtected && !isRequired;
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty && !isEditing()) {
            isRequired = false;
            isProtected = false;

            if (node != null) {
                for (String requiredField : node.getRequiredKey()) {

                    if (item.equals(requiredField)) {
                        isRequired = true;
                    }
                }

                for (String protectedField : node.getProtectedValue()) {

                    if (this.getTreeItem().getParent() != null
                        && this.getTreeItem().getParent().getValue().equals(protectedField)) {
                        isProtected = true;
                    }
                }
            }
            else {
                isRequired = true;
                isProtected = true;
            }

            if (!isRequired && !isProtected) {
                ImageView imgView = new ImageView(new Image(Main.class.getResource("image/edit.png").toExternalForm()));
                this.getTreeItem().setGraphic(imgView);
                setContextMenu(menu);
            }
        }

    }

    @Override
    public void startEdit() {
        if (isRequired || isProtected) {
            this.cancelEdit();
        }
        else {
            super.startEdit();
        }
    };

    @Override
    public void commitEdit(String newValue) {
        if (newValue.trim().isEmpty()) {
            return;
        }
        else if (!isValidExpression(getTreeItem(), newValue)) {
            return;
        }

        super.commitEdit(newValue);
    }
}
