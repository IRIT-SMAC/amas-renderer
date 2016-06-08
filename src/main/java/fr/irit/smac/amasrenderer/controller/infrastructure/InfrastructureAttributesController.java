package fr.irit.smac.amasrenderer.controller.infrastructure;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.controller.attributes.AttributesContextMenu;
import fr.irit.smac.amasrenderer.controller.attributes.AttributesTreeCell;
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

public class InfrastructureAttributesController implements Initializable {

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
        
        AttributesService.getInstance().updateAttributesMap(tree.getRoot().getValue(), tree.getRoot(),
            infra.getAttributesMap(), infra);
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

    public void init() {

        this.infra = InfrastructureService.getInstance().getInfrastructure();
        TreeItem<String> myItem = new TreeItem<>(infra.getName());
        tree.setRoot(myItem);
        HashMap<String, Object> infrastructure = (HashMap<String, Object>) InfrastructureService.getInstance()
            .getInfrastructure().getAttributesMap();
        AttributesService.getInstance().fillAttributes(infrastructure, myItem, infra);
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
}
