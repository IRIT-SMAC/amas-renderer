package fr.irit.smac.amasrenderer.controller.tool;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.GraphService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.stage.Stage;
import javafx.util.converter.DefaultStringConverter;

/**
 * The Class TreeModifyController. Manage the modal window opening to modify
 * attributes
 */
public class ToolModifyController implements Initializable {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private Button delButton;

    @FXML
    private TreeView<String> tree;

    private TreeItem<String> value;

    private Stage dialogStage;

    private String key;

    private ListView<String> list;

    private String baseRootName;

    private String newServiceName = null;

    /**
     * Sets the stage.
     *
     * @param stage
     *            the new stage
     */
    public void setStage(Stage stage) {
        dialogStage = stage;
    }

    /**
     * sets the node to be modified
     * 
     * @param node
     *            the node to modify
     */
    public void setKey(String label) {
        this.key = label;
    }

    /**
     * Initialize the controller
     * 
     * @param attributeMap
     *            the map of attributes
     * @param list
     *            the list of tools
     */
    public void init(ListView<String> list, String name) {
        setKey(list.getSelectionModel().getSelectedItem());
        this.list = list;
        
		TreeItem<String> myItem = new TreeItem<>(name);
		tree.setRoot(myItem);
		System.out.println(ToolService.getInstance().getServicesMap());

		System.out.println(ToolService.getInstance().getServicesMap().get(name));
		HashMap<String, Object> service = (HashMap<String, Object>) ToolService.getInstance().getServicesMap().get(name);
		System.out.println(service);
		fillAgentAttributes(service, myItem);
//        tree.setRoot(deepcopy(attributeMap.get(key)));
        baseRootName = key;
    }

	private void fillAgentAttributes(HashMap<String, Object> service, TreeItem<String> parent) {

		Iterator<Map.Entry<String, Object>> attributeIterator = service.entrySet().iterator();
		while (attributeIterator.hasNext()) {
			Map.Entry<String, Object> attribute = attributeIterator.next();
			String name = attribute.getKey();
			Object value = attribute.getValue();

			if (value instanceof HashMap<?, ?>) {
				TreeItem<String> item = new TreeItem<>();
				item.setValue(name);
				fillAgentAttributes((HashMap<String, Object>) value, item);
				parent.getChildren().add(item);
			} else {
				TreeItem<String> item = new TreeItem<>();
				item.setValue(name + " : " + value);
				parent.getChildren().add(item);
			}
		}
	}
	
    /**
     * deletes the service ( no confirmation )
     */
    @FXML
    public void deleteButton() {
        list.getItems().remove(key);
//        ToolService.getInstance().getAttributes().remove(key);
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    /**
     * Confirm button. sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {
        value = tree.getRoot();
        
//        ToolService.getInstance().getServicesMap().put(key, value);
        
		ToolService.getInstance().updateServiceMap(tree.getRoot().getValue(), tree.getRoot());

//        newServiceName = tree.getRoot().getValue();
//        if (newServiceName != baseRootName) {
//            list.getItems().remove(key);
//            key = newServiceName;
//            list.getItems().add(key);
//            ToolService.getInstance().getAttributes().put(key,
//                ToolService.getInstance().getAttributes().remove(baseRootName));
//        }
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    /**
     * Cancel button. just exit this window
     */
    @FXML
    public void cancelButton() {
        Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
        dialogStage.close();
    }

    /**
     * Deepcopy. recursively copies the tree, to be able to modify it without
     * changing the original
     * 
     * @param item
     *            the the tree to copy
     * @return a copy of the tree item the tree item
     */
    private TreeItem<String> deepcopy(TreeItem<String> item) {
        TreeItem<String> copy = new TreeItem<>(item.getValue());
        for (TreeItem<String> child : item.getChildren()) {
            copy.getChildren().add(deepcopy(child));
        }
        return copy;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        tree.setCellFactory(p -> new ToolAttributesTreeCell());
    }

    private static class ToolAttributesTreeCell extends TextFieldTreeCell<String> {
        private ContextMenu menu = new ContextMenu();

        public ToolAttributesTreeCell() {
            super(new DefaultStringConverter());

            menu.setId("treeAttributeItem");
            MenuItem renameItem = new MenuItem("Renommer");
            renameItem.setId("renameAttributeItem");
            menu.getItems().add(renameItem);

            renameItem.setOnAction(e -> startEdit());

            MenuItem addItem = new MenuItem("Ajouter");
            menu.getItems().add(addItem);
            addItem.setId("addAttributeItem");
            addItem.setOnAction(e -> getTreeItem().getChildren().add(new TreeItem<String>("Nouvel attribut")));

            MenuItem removeItem = new MenuItem("Supprimer");
            menu.getItems().add(removeItem);
            removeItem.setId("removeAttributeItem");
            removeItem.setOnAction(e -> getTreeItem().getParent().getChildren().remove(getTreeItem()));
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
            if (!isEditing()) {
                setContextMenu(menu);
            }
        }

        @Override
        public void commitEdit(String newValue) {

            if (newValue.trim().isEmpty()) {
                return;
            }
            super.commitEdit(newValue);
        }

    }

}