package fr.irit.smac.amasrenderer.controller.attribute;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.graphstream.graph.Node;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.StockModel;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
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
public class TreeModifyController implements Initializable {

	@FXML
	private Button confButton;

	@FXML
	private Button cancButton;

	@FXML
	private TreeView<String> tree;

	private TreeItem<String> oldTree;

	private StockModel stock;

	private Stage dialogStage;

	private String baseAgentName;

	private Node node;

	private String newAgentName = null;

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
	 * Sets the node to be modified
	 * 
	 * @param node
	 *            the node to modify
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * Sets the stock.
	 *
	 * @param s
	 *            the new stock
	 */
	public void setStock(StockModel s) {
		baseAgentName = s.getRoot().getValue();
		stock = s;
		tree.setRoot(deepcopy(s.getRoot()));
		this.oldTree = new TreeItem<>();
		this.oldTree = deepcopy(tree.getRoot());
		tree.getRoot().setExpanded(true);
	}

	public void init(
			 String id) {

		HashMap<String, Object> agent = (HashMap<String, Object>) GraphService.getInstance().getModel().getAgentMap()
				.get(id);
	
		TreeItem<String> myItem = new TreeItem<>(id);
		tree.setRoot(myItem);

		fillAgentAttributes(agent, myItem);

		this.tree.setEditable(true);

		tree.setCellFactory(p -> new MenuAttributesTreeCell());
	}

	private void fillAgentAttributes(HashMap<String, Object> agent, TreeItem<String> parent) {

		Iterator<Map.Entry<String, Object>> attributeIterator = agent.entrySet().iterator();
		while (attributeIterator.hasNext()) {
			Map.Entry<String, Object> attribute = attributeIterator.next();
			String name = attribute.getKey();
			Object value = attribute.getValue();

			if (value instanceof HashMap<?, ?>) {
				TreeItem<String> item = new TreeItem<>();
				item.setValue(name);
				fillAgentAttributes((HashMap<String, Object>) value, item);
			} else {
				TreeItem<String> item = new TreeItem<>();
				item.setValue(name + " : " + value);
				parent.getChildren().add(item);
			}
		}
	}

	/**
	 * Confirm button. Sets the new tree as the node tree, and exit this window
	 */
	public void confirmButton() {
		stock.setRoot(tree.getRoot());
		newAgentName = tree.getRoot().getValue();
		if (newAgentName != baseAgentName) {
			node.setAttribute("ui.label", newAgentName);
		}
		Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
		dialogStage.close();
	}

	/**
	 * Cancel button. Just exit this window
	 */
	@FXML
	public void cancelButton() {
		Main.getMainStage().getScene().lookup("#rootLayout").getStyleClass().remove("secondaryWindow");
		dialogStage.close();
		this.tree.setRoot(deepcopy(oldTree));
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


	}

	private static class MenuAttributesTreeCell extends TextFieldTreeCell<String> {
		private ContextMenu menu = new ContextMenu();

		@SuppressWarnings({ "rawtypes", "unchecked" })
		public MenuAttributesTreeCell() {
			super(new DefaultStringConverter());

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
			removeItem.setOnAction(e -> getTreeItem().getParent().getChildren().remove(getTreeItem()));
		}

		@Override
		public void updateItem(String item, boolean empty) {
			super.updateItem(item, empty);

			if (!empty && !isEditing()) {
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