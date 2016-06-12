package fr.irit.smac.amasrenderer.utils.attributes;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

/**
 * This class defines the context menu to use when the user is interacting with
 * the attributes
 */
public class AttributesContextMenu {

    private final ContextMenu contextMenu;

    private final MenuItem add;

    private final MenuItem delete;

    private final MenuItem rename;

    private final static String ADD_ITEM_ID = "addAttributeItem";

    private final static String DELETE_ITEM_ID = "removeAttributeItem";

    private final static String RENAME_ITEM_ID = "renameAttributeItem";

    private final static String ADD_ITEM = "Ajouter";

    private final static String DELETE_ITEM = "Supprimer";

    private final static String RENAME_ITEM = "Renommer";

    public AttributesContextMenu() {
        this.add = new MenuItem(ADD_ITEM);
        this.add.setId(ADD_ITEM_ID);
        this.delete = new MenuItem(DELETE_ITEM);
        this.delete.setId(DELETE_ITEM_ID);
        this.rename = new MenuItem(RENAME_ITEM);
        this.rename.setId(RENAME_ITEM_ID);
        this.contextMenu = new ContextMenu(add, delete, rename);
    }

    public ContextMenu getContextMenu() {
        return contextMenu;
    }

    public MenuItem getAdd() {
        return add;
    }

    public MenuItem getDelete() {
        return delete;
    }

    public MenuItem getRename() {
        return rename;
    }

    /**
     * This method prevents memory leak by setting all actionListeners to null
     */
    public void freeActionListeners() {
        this.add.setOnAction(null);
        this.delete.setOnAction(null);
        this.rename.setOnAction(null);
    }

}
