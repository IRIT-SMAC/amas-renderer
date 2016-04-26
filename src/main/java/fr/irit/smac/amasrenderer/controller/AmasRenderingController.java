package fr.irit.smac.amasrenderer.controller;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;

import fr.irit.smac.amasrenderer.model.AgentGraph;
import fr.irit.smac.amasrenderer.model.Const;
import fr.irit.smac.amasrenderer.model.Stock;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class AmasRenderingController {
	
	private AgentGraph model;
	private ViewPanel graphView;
	@FXML
	private TextArea attributes_synthesis;
	
	@FXML
	private SwingNode graphNode;
	
	public AmasRenderingController() {
		this.model = new AgentGraph("AMAS Rendering");
		model.addAttribute("ui.stylesheet","url(" + getClass().getResource("../view/styleSheet1.css") + ")");
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		Viewer viewer = new Viewer(this.model, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
		viewer.enableAutoLayout();
		this.graphView = viewer.addDefaultView(false);
		
		initGraph();
        initAdapters();
        
        //attributes_synthesis.setText("No nodes are actually selected, click on a node");
		
		
	}
	
	public void setAttributes_synthesis(String text) {
        attributes_synthesis.setText(text);
    }
	public String getAttributes_synthesis() {
        return attributes_synthesis.getText();
    }

    public void drawGraph(){
		this.graphNode.setContent(this.graphView);
	}
	
	@FXML
	public void ajouterService(){
		
	}
	
	@FXML
	public void ajouterAttribut(){
		
	}
	
	@FXML
	public void ajouterAgent(){
		model.addNode(""+model.getNodeCount()+1);
	}
	
	@FXML
	public void graphMouseClicked(){
		//model.ajouterAgent();
	}
	
	public ViewPanel getGraphView() {
        return graphView;
    }
	
	public AgentGraph getModel() {
        return model;
    }
	

	private void initGraph(){
	    //construit le graphe de test(arbre), avec Const.NODE_INIT nodes
	    model.addNode("0");
        for (Integer i = 1; i < Const.NODE_INIT; i++) {
            int firstNode = i;
            model.addNode(""+firstNode);
            if(i >= Const.EDGE_INIT){
                for (int j = 0; j < Const.EDGE_INIT; j++) {
                    int secondNode = (int)Math.floor((Math.random() * i));
                    if(model.getEdge(i+" ::: "+firstNode+""+secondNode) == null)
                        model.addEdge(i+" ::: "+firstNode+""+secondNode,""+firstNode, ""+secondNode, true);
                    else
                        j--;
                }
            }
            
            
        }
        //modifie le layout
        for (Edge edge : model.getEachEdge()) {
            edge.setAttribute("layout.weight", 20);
        }
        for (Node node : model) {
            node.setAttribute("layout.weight", 300);
            //set la forme de stoquage des noeuds
            node.setAttribute("ui.stocked-info", new Stock());
        }
	}
	
	private void initAdapters() {
        MouseMotionListener[] mml = graphView.getMouseMotionListeners();
        for (MouseMotionListener mouseMotionListener : mml) {
            System.out.println(mouseMotionListener.toString());
            graphView.removeMouseMotionListener(mouseMotionListener);
        }
        
        MouseListener[] ml = graphView.getMouseListeners();
        for (MouseListener mouseListener : ml) {
            graphView.removeMouseListener(mouseListener);
        }
        //view.addMouseListener(new GraphMouseAdapter(ag));
        new GraphMouseWheelController().init(this);
        new AddEdgeMouseController().init(this);
        new NodeEditController().init(this);   
        new FocusNodeController().init(this);
        new DefaultMouseController().init(this);
        
    }
}
