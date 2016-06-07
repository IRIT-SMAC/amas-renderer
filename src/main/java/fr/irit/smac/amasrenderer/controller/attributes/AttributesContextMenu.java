package fr.irit.smac.amasrenderer.controller.attributes;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;

public class AttributesContextMenu {
    private final ContextMenu contextMenu;
    private final MenuItem    add;
    private final MenuItem    delete;
    private final MenuItem    rename;

    public AttributesContextMenu() {
        this.add = new MenuItem("Ajouter");
        this.add.setId("addAttributeItem");
        this.delete = new MenuItem("Supprimer");
        this.delete.setId("removeAttributeItem");
        this.rename = new MenuItem("Renommer");
        this.rename.setId("renameAttributeItem");
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
     * This method prevents memory leak by setting all actionListeners to null.
     */
    public void freeActionListeners() {
        this.add.setOnAction(null);
        this.delete.setOnAction(null);
        this.rename.setOnAction(null);
    }

}
