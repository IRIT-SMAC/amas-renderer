package fr.irit.smac.amasrenderer.controller.graph;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.spriteManager.Sprite;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.controller.ISecondaryWindowController;
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

    private Node node;

    private Stage stage;

    private GraphService graphService = GraphService.getInstance();

    @FXML
    public void clickConfirm() {

        String portName = textfieldTool.getText();

        if (sprite.getAttribute("type").equals("source")) {
            Map<String, Object> o = (Map<String, Object>) graphService.getAgentMap().get(node.getId());
            ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) o.get("knowledge")).get("targets"))
                .get(((Edge) sprite.getAttachment()).getTargetNode().getId())).put("portSource", portName);
            sprite.setAttribute(Const.NODE_LABEL, portName);
        } else if (sprite.getAttribute("type").equals("target")) {
            Map<String, Object> o = (Map<String, Object>) graphService.getAgentMap().get(node.getId());
            ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) o.get("knowledge")).get("targets"))
                .get(((Edge) sprite.getAttachment()).getTargetNode().getId())).put("portTarget", portName);
            sprite.setAttribute(Const.NODE_LABEL, portName);
        }

        this.stage.close();

    }

    @FXML
    public void clickCancel() {

    }

    @Override
    public void init(Stage stage, Object... args) {
        // TODO Auto-generated method stub
        this.stage = stage;
        sprite = (Sprite) args[0];
        Edge e = (Edge) sprite.getAttachment();
        node = e.getSourceNode();
        Map<String, Object> o = (Map<String, Object>) graphService.getAgentMap().get(node.getId());
        String portTarget = (String) ((Map<String, Object>) ((Map<String, Object>) ((Map<String, Object>) o
            .get("knowledge")).get("targets")).get(e.getTargetNode().getId())).get("portSource");
        System.out.println(portTarget);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub

    }

}
