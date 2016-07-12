package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.ResourceBundle;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.Sprite;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
import fr.irit.smac.amasrenderer.model.TargetModel;
import fr.irit.smac.amasrenderer.service.GraphService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PortController implements Initializable, ISecondaryWindowController {

    @FXML
    private Button buttonConfirm;

    @FXML
    private Button buttonCancel;

    @FXML
    private TextField textfieldTool;

    private Sprite sprite;

    private TargetModel targetModel;

    private Stage stage;

    private GraphService graphService = GraphService.getInstance();

    @FXML
    public void clickConfirm() {

        String portName = this.textfieldTool.getText();

        if (portName.trim().isEmpty() || portName.trim().equals(Const.NULL_STRING)) {
            portName = null;
        }

        if (this.sprite.getAttribute(Const.SUBTYPE_SPRITE).equals(Const.SOURCE_PORT_SPRITE)) {
            this.targetModel.getAttributesMap().put(Const.PORT_SOURCE, portName);
            this.sprite.setAttribute(Const.GS_UI_LABEL, portName);
        }
        else if (sprite.getAttribute(Const.SUBTYPE_SPRITE).equals(Const.TARGET_PORT_SPRITE)) {
            this.targetModel.getAttributesMap().put(Const.PORT_TARGET, portName);
            this.sprite.setAttribute(Const.GS_UI_LABEL, portName);
        }

        this.stage.close();

    }

    @FXML
    public void clickCancel() {

        this.stage.close();
    }

    @Override
    public void init(Stage stage, Object... args) {

        this.stage = stage;
        this.sprite = (Sprite) args[0];
        Edge e = (Edge) this.sprite.getAttachment();
        Node node = e.getSourceNode();
        
        System.out.println(e.getAttribute(Const.GS_UI_LABEL).toString());

        this.targetModel = this.graphService.getTargetModel(node.getId(), e.getAttribute(Const.GS_UI_LABEL).toString());
        System.out.println(targetModel);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // Nothing to do
    }

}
