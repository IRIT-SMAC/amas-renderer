package fr.irit.smac.amasrenderer.controller.attributes;

import java.util.Arrays;

import fr.irit.smac.amasrenderer.model.IConstraintFields;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.StringConverter;

public class AttributesTreeCell extends TextFieldTreeCell<String> {

    private final AttributesContextMenu contextMenu;

    private boolean isRequiredKeyComplex;
    private boolean isRequiredKeySingle;
    private boolean isProtected;
    private boolean isParentSingleNode;

    private IConstraintFields node;

    public AttributesTreeCell(AttributesContextMenu contextMenu, StringConverter<String> converter,
        IConstraintFields node) {
        super(converter);
        this.node = node;
        if (contextMenu == null) {
            throw new NullPointerException();
        }
        this.contextMenu = contextMenu;
        this.setOnContextMenuRequested(evt -> {
            prepareContextMenu(getTreeItem());
            evt.consume();
        });
    }

    private void prepareContextMenu(TreeItem<String> item) {

        if (item != null) {
            MenuItem delete = contextMenu.getDelete();
            MenuItem rename = contextMenu.getRename();
            MenuItem add = contextMenu.getAdd();
            boolean root = item.getParent() == null;

            checkNode(item);

            if (!root) {
                delete.setOnAction(evt -> {
                    item.getParent().getChildren().remove(item);
                    contextMenu.freeActionListeners();
                });
            }

            add.setOnAction(evt -> {
                item.getChildren().add(new TreeItem<>("..."));
                contextMenu.freeActionListeners();
            });

            rename.setOnAction(evt -> startEdit());

            if (!isRequiredKeySingle && !isProtected) {

                if (isRequiredKeyComplex) {
                    rename.setDisable(true);
                    delete.setDisable(true);
                    add.setDisable(false);
                }
                else if (isParentSingleNode) {
                    delete.setDisable(true);
                    add.setDisable(true);
                    rename.setDisable(false);
                }
                else if (item.getParent() == null) {
                    delete.setDisable(true);
                    add.setDisable(false);
                    rename.setDisable(false);
                }
                else {
                    delete.setDisable(false);
                    add.setDisable(false);
                    rename.setDisable(false);
                }
            }
            else {
                rename.setDisable(true);
                delete.setDisable(true);
                add.setDisable(true);
            }
        }
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContextMenu("nocontext".equals(item) ? null : contextMenu.getContextMenu());
            setEditable(!"noedit".equals(item));
        }
    }

    private void checkNode(TreeItem<String> item) {

        isRequiredKeySingle = false;
        isProtected = false;
        isRequiredKeyComplex = false;
        isParentSingleNode = false;

        if (node != null) {
            isRequiredKeySingle = Arrays.asList(node.getRequiredKeySingle()).contains(item.getValue());
            TreeItem<String> parent = item.getParent();
            if (parent != null) {
                isParentSingleNode = Arrays.asList(node.getRequiredKeySingle())
                    .contains(parent.getValue());
                isProtected = Arrays.asList(node.getProtectedValue())
                    .contains(parent.getValue());
            }
            isRequiredKeyComplex = Arrays.asList(node.getRequiredKeyComplex()).contains(item);
        }
    }

    @Override
    public void startEdit() {
        if (isRequiredKeySingle || isProtected || isRequiredKeyComplex) {
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
            || Arrays.asList(node.getRequiredKeySingle()).contains(newValue)
            || Arrays.asList(node.getProtectedValue()).contains(newValue)) {
            return;
        }

        super.commitEdit(newValue);
    }

}
