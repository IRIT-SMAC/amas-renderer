package fr.irit.smac.amasrenderer.controller.menu;

import java.util.Arrays;

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

    private boolean isRequired = false;

    private boolean isProtected;

    private IConstraintFields node;

    private boolean isRequiredKeyComplex;

    private MenuItem renameItem;

    private MenuItem addItem;

    private MenuItem removeItem;

    private boolean isParentSingleNode;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MenuAttributesTreeCellController(TreeView<String> tree, IConstraintFields node) {

        super(new DefaultStringConverter());
        this.node = node;

        menu.setId("treeAttributeItem");
        renameItem = new MenuItem("Renommer");
        renameItem.setId("renameAttributeItem");

        renameItem.setOnAction(e -> startEdit());

        addItem = new MenuItem("Ajouter");
        addItem.setId("addAttributeItem");
        addItem.setOnAction(e -> {
            TreeItem newItem = new TreeItem<String>("Nouvel attribut");
            getTreeItem().getChildren().add(newItem);
        });

        removeItem = new MenuItem("Supprimer");
        removeItem.setId("removeAttributeItem");
        removeItem.setOnAction(e -> {
            getTreeItem().getParent().getChildren().remove(getTreeItem());
        });

    }

    private void checkNode(String item) {

        isRequired = false;
        isProtected = false;
        isRequiredKeyComplex = false;
        isParentSingleNode = false;

        if (node != null) {
            isRequired = Arrays.asList(node.getRequiredKey()).contains(item);
            if (this.getTreeItem().getParent() != null) {
                isParentSingleNode = Arrays.asList(node.getRequiredKey())
                    .contains(this.getTreeItem().getParent().getValue());
                isProtected = Arrays.asList(node.getProtectedValue())
                    .contains(this.getTreeItem().getParent().getValue());
            }

            isRequiredKeyComplex = Arrays.asList(node.getRequiredKeyComplex()).contains(item);
        }
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty && !isEditing()) {
            checkNode(item);

            if (!isRequired && !isProtected) {
                ImageView imgView = new ImageView(new Image(Main.class.getResource("image/edit.png").toExternalForm()));
                this.getTreeItem().setGraphic(imgView);

                if (isRequiredKeyComplex) {
                    menu.getItems().add(addItem);
                }
                else if (isParentSingleNode) {
                    menu.getItems().add(renameItem);
                }
                else {
                    menu.getItems().add(renameItem);
                    menu.getItems().add(removeItem);
                    menu.getItems().add(addItem);
                }

                setContextMenu(menu);
            }
        }

    }

    @Override
    public void startEdit() {
        if (isRequired || isProtected || isRequiredKeyComplex) {
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
        else if (Arrays.asList(node.getRequiredKeyComplex()).contains(newValue)
            || Arrays.asList(node.getRequiredKey()).contains(newValue)
            || Arrays.asList(node.getProtectedValue()).contains(newValue)) {
            return;
        }

        super.commitEdit(newValue);
    }
}
