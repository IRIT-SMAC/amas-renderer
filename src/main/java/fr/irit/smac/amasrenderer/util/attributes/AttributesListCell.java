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

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

public class AttributesListCell<T> extends TextFieldListCell<T> {

    private final AttributesContextMenuList contextMenu;
    private ObservableList<T>               list;
    private ListView<T>                     listView;

    public AttributesListCell(AttributesContextMenuList contextMenu, StringConverter<T> converter,
        ObservableList<T> list,
        ListView<T> listView) {
        super(converter);
        if (contextMenu == null) {
            throw new NullPointerException();
        }
        this.list = list;
        this.listView = listView;
        this.contextMenu = contextMenu;
        this.setOnContextMenuRequested(evt -> {
            prepareContextMenu(getListView());
            evt.consume();
        });
    }

    /**
     * Prepares the design of the context menu depending on the node
     * 
     * @param item
     *            the clicked node
     */
    private void prepareContextMenu(ListView<T> item) {

        if (item != null) {

            AttributesContextMenuList menu = contextMenu;
            MenuItem delete = menu.getDelete();
            MenuItem rename = menu.getRename();

            rename.setOnAction(evt -> startEdit());
            delete.setOnAction(evt -> {
                list.remove(listView.getSelectionModel().getSelectedItem());
                menu.freeActionListeners();
            });
        }
    }

    @Override
    public void updateItem(T item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContextMenu("nocontext".equals(item) ? null : contextMenu.getContextMenu());
            setEditable(!"noedit".equals(item));
        }
    }
}
