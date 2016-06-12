package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.controller.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.controller.attributes.AttributesTreeCell;
import fr.irit.smac.amasrenderer.model.IModel;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;

public class InfrastructureAttributesController implements Initializable, ISecondaryWindowController {

    @FXML
    private Button confButton;

    @FXML
    private Button cancButton;

    @FXML
    private TreeView<String> tree;

    private Stage stage;

    private InfrastructureModel infra;

    private AttributesService attributesService = AttributesService.getInstance();

    /**
     * Confirm button. Sets the new tree as the node tree, and exit this window
     */
    @FXML
    public void confirmButton() {

        this.attributesService.updateAttributesMap(this.tree.getRoot().getValue(), this.tree.getRoot(),
            infra.getAttributesMap(), this.infra);
        this.stage.close();
    }

    /**
     * Cancel button. Just exit this window
     */
    @FXML
    public void cancelButton() {

        this.stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.tree.setEditable(true);
        tree.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {

            private final AttributesContextMenu contextMenu = new AttributesContextMenu();
            private final StringConverter converter = new DefaultStringConverter();

            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new AttributesTreeCell(contextMenu, converter, infra);
            }

        });
    }

    @Override
    public void init(Stage stage, Object... args) {

        InfrastructureModel infra = (InfrastructureModel) args[0];
        this.infra = infra;
        this.stage = stage;
        TreeItem<String> root = new TreeItem<>(infra.getName());
        this.tree.setRoot(root);
        HashMap<String, Object> infrastructure = (HashMap<String, Object>) infra.getAttributesMap();
        this.attributesService.fillAttributes(infrastructure, root, infra);
    }
}
