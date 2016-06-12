package fr.irit.smac.amasrenderer.utils.attributes;

import java.util.Arrays;

import fr.irit.smac.amasrenderer.model.IModel;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.StringConverter;

/**
 * This class defines the tree to use for the visualisation or the update of the
 * attributes
 */
public class AttributesTreeCell extends TextFieldTreeCell<String> {

    private final AttributesContextMenu contextMenu;

    private boolean isRequiredKeyComplex;
    private boolean isRequiredKeySingle;
    private boolean isProtected;
    private boolean isParentSingleNode;

    private IModel model;

    public AttributesTreeCell(AttributesContextMenu contextMenu, StringConverter<String> converter,
        IModel node) {
        super(converter);
        this.model = node;
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
                item.getChildren().add(new TreeItem<>("item"));
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

        if (model != null) {
            isRequiredKeySingle = Arrays.asList(model.getRequiredKeySingle()).contains(item.getValue());
            TreeItem<String> parent = item.getParent();
            if (parent != null) {
                isParentSingleNode = Arrays.asList(model.getRequiredKeySingle())
                    .contains(parent.getValue());
                isProtected = Arrays.asList(model.getProtectedValue())
                    .contains(parent.getValue());
            }
            isRequiredKeyComplex = Arrays.asList(model.getRequiredKeyComplex()).contains(item.getValue());
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

        boolean notEmpty = !newValue.trim().isEmpty();
        boolean notRequiredComplex = !Arrays.asList(model.getRequiredKeyComplex()).contains(newValue);
        boolean notRequiredKeySingle = !Arrays.asList(model.getRequiredKeySingle()).contains(newValue);
        boolean notProtectedValue = !Arrays.asList(model.getProtectedValue()).contains(newValue);
        boolean notExpanded = !Arrays.stream(model.getNotExpanded()).allMatch(s -> newValue.contains(s))
            || model.getNotExpanded().length == 0;

        if (notEmpty && notRequiredComplex && notRequiredKeySingle && notProtectedValue && notExpanded) {

            if (getTreeItem().getParent() == null) {
                super.commitEdit(this.model.getNewName(newValue));
            }
            else {
                super.commitEdit(newValue);
            }
        }
    }

}
