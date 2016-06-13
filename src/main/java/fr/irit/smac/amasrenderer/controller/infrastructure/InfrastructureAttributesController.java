package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.InfrastructureModel;
import fr.irit.smac.amasrenderer.service.AttributesService;
import fr.irit.smac.amasrenderer.util.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.util.attributes.AttributesTreeCell;
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

/**
 * This controller is related to the attributes of the infrastructure
 */
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
     * When the confirm button is clicked, the attributes of the infrastructure
     * are updated depending on the tree
     */
    @FXML
    public void confirmButton() {

        this.attributesService.updateAttributesMap(this.tree.getRoot().getValue(), this.tree.getRoot(),
            infra.getAttributesMap(), this.infra);
        this.stage.close();
    }

    /**
     * When the cancel button is clicked, the attributes are not updated and the
     * window is closed
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
            @SuppressWarnings("rawtypes")
            private final StringConverter converter = new DefaultStringConverter();

            @SuppressWarnings("unchecked")
            @Override
            public TreeCell<String> call(TreeView<String> param) {
                return new AttributesTreeCell(contextMenu, converter, infra);
            }

        });
    }

    @Override
    public void init(Stage stage, Object... args) {

        InfrastructureModel infrastructure = (InfrastructureModel) args[0];
        this.infra = infrastructure;
        this.stage = stage;
        TreeItem<String> root = new TreeItem<>(infrastructure.getName());
        this.tree.setRoot(root);
        HashMap<String, Object> infrastructureAttributes = (HashMap<String, Object>) infrastructure.getAttributesMap();
        this.attributesService.fillAttributes(infrastructureAttributes, root, infrastructure);
    }
}
