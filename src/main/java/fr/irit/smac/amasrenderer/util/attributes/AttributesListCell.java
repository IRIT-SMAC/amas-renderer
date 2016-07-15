package fr.irit.smac.amasrenderer.util.attributes;

import com.lowagie.text.ListItem;

import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;

public class AttributesListCell<PortModel> extends TextFieldListCell<PortModel> {

    private final AttributesContextMenu contextMenu;
    private ObservableList<PortModel> list;
    private ListView<fr.irit.smac.amasrenderer.model.agent.feature.social.PortModel> liste;

    public AttributesListCell(AttributesContextMenu contextMenu, StringConverter<PortModel> converter, ObservableList<PortModel> ports, ListView<fr.irit.smac.amasrenderer.model.agent.feature.social.PortModel> listPort) {
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

    private void prepareContextMenu(ListView<PortModel> item) {

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
    public void updateItem(PortModel item, boolean empty) {
        super.updateItem(item, empty);
        if (!empty) {
            setContextMenu("nocontext".equals(item) ? null : contextMenu.getContextMenu());
            setEditable(!"noedit".equals(item));
        }
    }
}
