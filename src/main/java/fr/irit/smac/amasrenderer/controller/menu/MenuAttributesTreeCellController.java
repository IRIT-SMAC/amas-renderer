package fr.irit.smac.amasrenderer.controller.menu;

import fr.irit.smac.amasrenderer.Const;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.util.converter.DefaultStringConverter;

public class MenuAttributesTreeCellController extends TextFieldTreeCell<String> {

    private ContextMenu      menu = new ContextMenu();
    private TreeView<String> tree;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public MenuAttributesTreeCellController(TreeView<String> tree) {
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
        removeItem.setOnAction(e -> {
            if (!isProtectedField(getItem())) {
                getTreeItem().getParent().getChildren().remove(getTreeItem());
            }
        });
    }

    /**
     * test if the string new value is valid
     * 
     * @param newValue
     * @return
     */
    private boolean isValidExpression(TreeItem<String> item, String newValue) {
        boolean isSemiColonCorrect = false;
        boolean isValueKeyCorrect = false;
        // checks if the new value has the good key-value séparator ( and
        // only one )
        if (item.getChildren().size() == 0) {
            isSemiColonCorrect = newValue.split(Const.KEY_VALUE_SEPARATOR).length == 2;
            if (isSemiColonCorrect)
                isValueKeyCorrect = !newValue.split(Const.KEY_VALUE_SEPARATOR)[0].trim().isEmpty()
                    && !newValue.split(Const.KEY_VALUE_SEPARATOR)[1].trim().isEmpty();
        }
        else {
            isSemiColonCorrect = !(newValue.contains(":") || newValue.contains(" "));
            isValueKeyCorrect = !newValue.trim().isEmpty();
        }
        // checks if the new value contains an unauthorized string
        boolean containsNewUnauthorizedString = false;
        for (String str : Const.UNAUTHORISED_STRING) {
            containsNewUnauthorizedString = newValue.contains(str) ? true : containsNewUnauthorizedString;
        }

        return isSemiColonCorrect && isValueKeyCorrect && !containsNewUnauthorizedString;
    }

    private boolean isProtectedField(String oldValue) {
        // checks if the oldValue was protected
        boolean oldContainsProtectedString = false;
        for (String str : Const.PROTECTED_STRING) {
            oldContainsProtectedString = oldValue.contains(str) ? true : oldContainsProtectedString;
        }
        return oldContainsProtectedString;
    }

    @Override
    public void updateItem(String item, boolean empty) {
        super.updateItem(item, empty);

        if (!empty && !isEditing()) {
            setContextMenu(menu);
        }
    }

    @Override
    public void startEdit() {
        if (isProtectedField(this.getItem())) {
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
        else if (this.getTreeItem() != tree.getRoot() && !isValidExpression(getTreeItem(), newValue)) {
            return;
        }

        super.commitEdit(newValue);
    }
}
