package fr.irit.smac.amasrenderer.util.attributes;

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

    /**
     * Prepares the design of the context menu depending on the node
     * 
     * @param item
     *            the clicked node
     */
    private void prepareContextMenu(TreeItem<String> item) {

        if (item != null) {

            AttributesContextMenu menu = this.contextMenu;
            MenuItem delete = menu.getDelete();
            MenuItem rename = menu.getRename();
            MenuItem add = menu.getAdd();
            boolean root = item.getParent() == null;

            checkConstraintsNode(item);

            if (!root) {
                delete.setOnAction(evt -> {
                    item.getParent().getChildren().remove(item);
                    menu.freeActionListeners();
                });
            }

            add.setOnAction(evt -> {
                item.getChildren().add(new TreeItem<>("item"));
                menu.freeActionListeners();
            });

            rename.setOnAction(evt -> startEdit());

            if (!this.isRequiredKeySingle && !this.isProtected) {

                if (this.isRequiredKeyComplex) {
                    rename.setDisable(true);
                    delete.setDisable(true);
                    add.setDisable(false);
                }
                else if (this.isParentSingleNode) {
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

    /**
     * Check the constraints of a node
     * 
     * @param item
     *            the node
     */
    private void checkConstraintsNode(TreeItem<String> item) {

        IModel currentModel = this.model;
        this.isRequiredKeySingle = false;
        this.isProtected = false;
        this.isRequiredKeyComplex = false;
        this.isParentSingleNode = false;

        if (currentModel != null) {
            this.isRequiredKeySingle = Arrays.asList(currentModel.getRequiredKeySingle()).contains(item.getValue());
            TreeItem<String> parent = item.getParent();
            if (parent != null) {
                this.isParentSingleNode = Arrays.asList(currentModel.getRequiredKeySingle())
                    .contains(parent.getValue());
                this.isProtected = Arrays.asList(currentModel.getProtectedValue())
                    .contains(parent.getValue());
            }
            this.isRequiredKeyComplex = Arrays.asList(currentModel.getRequiredKeyComplex()).contains(item.getValue());
        }
    }

    @Override
    public void startEdit() {
        if (this.isRequiredKeySingle || this.isProtected || this.isRequiredKeyComplex) {
            this.cancelEdit();
        }
        else {
            super.startEdit();
        }
    };

    @Override
    public void commitEdit(String newValue) {

        IModel currentModel = this.model;
        boolean notEmpty = !newValue.trim().isEmpty();
        boolean notRequiredComplex = !Arrays.asList(currentModel.getRequiredKeyComplex()).contains(newValue);
        boolean notRequiredKeySingle = !Arrays.asList(currentModel.getRequiredKeySingle()).contains(newValue);
        boolean notProtectedValue = !Arrays.asList(currentModel.getProtectedValue()).contains(newValue);
        boolean notExpanded = !Arrays.stream(currentModel.getNotExpanded()).allMatch(s -> newValue.contains(s))
            || currentModel.getNotExpanded().length == 0;

        if (notEmpty && notRequiredComplex && notRequiredKeySingle && notProtectedValue && notExpanded) {

            if (getTreeItem().getParent() == null) {
                super.commitEdit(currentModel.getNewName(newValue));
            }
            else {
                super.commitEdit(newValue);
            }
        }
    }

}
