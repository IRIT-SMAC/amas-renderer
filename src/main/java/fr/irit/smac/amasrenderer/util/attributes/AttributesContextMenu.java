/*
 * #%L
 * amas-renderer
 * %%
 * Copyright (C) 2016 IRIT - SMAC Team
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Lesser Public License for more details.
 * 
 * You should have received a copy of the GNU General Lesser Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/lgpl-3.0.html>.
 * #L%
 */
package fr.irit.smac.amasrenderer.util.attributes;

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

    private final MenuItem addSingle;

    private static final String ADD_ITEM_ID = "addAttributeItem";

    private static final String DELETE_ITEM_ID = "removeAttributeItem";

    private static final String RENAME_ITEM_ID = "renameAttributeItem";

    private static final String ADD_ITEM = "Ajouter un attribut parent";

    private static final String ADD_ITEM_SINGLE = "Ajouter un attribut simple";

    private static final String DELETE_ITEM = "Supprimer";

    private static final String RENAME_ITEM = "Renommer";

    public AttributesContextMenu(boolean forAList) {
        add = new MenuItem(ADD_ITEM);
        add.setId(ADD_ITEM_ID);
        delete = new MenuItem(DELETE_ITEM);
        delete.setId(DELETE_ITEM_ID);
        rename = new MenuItem(RENAME_ITEM);
        rename.setId(RENAME_ITEM_ID);
        addSingle = new MenuItem(ADD_ITEM_SINGLE);

        if (forAList) {
            contextMenu = new ContextMenu(delete, rename);
        }
        else {
            contextMenu = new ContextMenu(add, addSingle, delete, rename);
        }
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
        add.setOnAction(null);
        delete.setOnAction(null);
        rename.setOnAction(null);
    }

    public MenuItem getAddSingle() {
        return addSingle;
    }

}
