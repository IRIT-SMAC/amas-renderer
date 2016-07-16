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

    private final AttributesContextMenu contextMenu;
    private ObservableList<T> list;
    private ListView<T> liste;

    public AttributesListCell(AttributesContextMenu contextMenu, StringConverter<T> converter, ObservableList<T> ports, ListView<T> listPort) {
        super(converter);
        if (contextMenu == null) {
            throw new NullPointerException();
        }
        this.list = ports;
        this.liste = listPort;
        this.contextMenu = contextMenu;
        this.setOnContextMenuRequested(evt -> {
            prepareContextMenu(getListView());
            evt.consume();
        });
    }

    private void prepareContextMenu(ListView<T> item) {

        if (item != null) {

            AttributesContextMenu menu = this.contextMenu;
            MenuItem delete = menu.getDelete();
            MenuItem rename = menu.getRename();

            rename.setOnAction(evt -> startEdit());
            delete.setOnAction(evt -> {
                list.remove(liste.getSelectionModel().getSelectedItem());
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
