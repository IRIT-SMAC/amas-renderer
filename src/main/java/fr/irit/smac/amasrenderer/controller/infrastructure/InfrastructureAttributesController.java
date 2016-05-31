package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
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

public class InfrastructureAttributesController implements Initializable{

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private TreeView<String> tree;
 
    private Stage dialogStage;

    private InfrastructureModel infra;

    /**
     * Confirm button. Sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {
        
        InfrastructureService.getInstance().updateInfrastructureMap(tree.getRoot().getValue(), tree.getRoot(), infra);
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
    }
    
    /**
     * Sets the stage.
     *
     * @param stage
     *            the new stage
     */
    public void setStage(Stage stage) {
        dialogStage = stage;
    }
    
    public void init(InfrastructureModel infra) {
        
        this.infra = infra;
        TreeItem<String> myItem = new TreeItem<>(infra.getName());
        tree.setRoot(myItem);
        HashMap<String, Object> infrastructure = (HashMap<String, Object>) InfrastructureService.getInstance().getInfrastructure().get(0).getAttributesMap();
        fillInfrastructureAttributes(infrastructure, myItem);
    }
    
    @SuppressWarnings("unchecked")
    private void fillInfrastructureAttributes(HashMap<String, Object> infrastructure, TreeItem<String> parent) {

        Iterator<Map.Entry<String, Object>> attributeIterator = infrastructure.entrySet().iterator();
        while (attributeIterator.hasNext()) {
            Map.Entry<String, Object> attribute = attributeIterator.next();
            String name = attribute.getKey();
            Object value = attribute.getValue();

            if (value instanceof HashMap<?, ?>) {
                TreeItem<String> item = new TreeItem<>();
                item.setValue(name);
                fillInfrastructureAttributes((HashMap<String, Object>) value, item);
                parent.getChildren().add(item);
            } else {
                TreeItem<String> item = new TreeItem<>();
                item.setValue(name + " : " + value);
                parent.getChildren().add(item);
            }
        }
    }
    
    
    private static class MenuAttributesTreeCell extends TextFieldTreeCell<String> {
        private ContextMenu      menu = new ContextMenu();
        private TreeView<String> tree;

        @SuppressWarnings({ "rawtypes", "unchecked" })
        public MenuAttributesTreeCell(TreeView<String> tree) {
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
        private boolean isValidExpression(TreeItem<String> item, String newValue){
            boolean isSemiColonCorrect = false;
            boolean isValueKeyCorrect = false;
            //checks if the new value has the good key-value séparator ( and only one )
            if(item.getChildren().size() == 0){
                isSemiColonCorrect = newValue.split(Const.KEY_VALUE_SEPARATOR).length == 2; 
                if(isSemiColonCorrect) isValueKeyCorrect = !newValue.split(Const.KEY_VALUE_SEPARATOR)[0].trim().isEmpty() && !newValue.split(Const.KEY_VALUE_SEPARATOR)[1].trim().isEmpty();
            } else { // if it has childs then its a complex attribute
                isSemiColonCorrect = !(newValue.contains(":") || newValue.contains(" ")); 
                isValueKeyCorrect = !newValue.trim().isEmpty();
            }
            //checks if the new value contains an unauthorized string
            boolean containsNewUnauthorizedString = false;
            for(String str : Const.UNAUTHORISED_STRING){
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
            else if (this.getTreeItem() != tree.getRoot() && !isValidExpression(getTreeItem(),newValue)) {
                return;
            }

            super.commitEdit(newValue);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);

        tree.setCellFactory(p -> new MenuAttributesTreeCell(tree));

    }
}
