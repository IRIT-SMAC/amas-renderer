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

        String portName = textfieldTool.getText();

        if (portName.trim().isEmpty()) {
            portName = null;
        }
        
        if (sprite.getAttribute("type").equals("source")) {
            targetModel.getAttributesMap().put("portSource", portName);
            sprite.setAttribute(Const.NODE_LABEL, portName);
        }
        else if (sprite.getAttribute("type").equals("target")) {
            targetModel.getAttributesMap().put("portTarget", portName);
            sprite.setAttribute(Const.NODE_LABEL, portName);
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
        sprite = (Sprite) args[0];
        Edge e = (Edge) sprite.getAttachment();
        Node node = e.getSourceNode();

        String id = ((String) sprite.getAttribute("id")).substring(node.getId().length(),
            ((String) sprite.getAttribute("id")).length());
        
        targetModel = graphService.getTargets().get(node.getId()).get(id);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

}
