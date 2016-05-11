package fr.irit.smac.amasrenderer.controller.tool;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ToolService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class ServicesController.
 * This controller handles the service part of the main UI 
 */
public class ToolController implements Initializable {

    @FXML
    private Button buttonAddService;

    @FXML
    private ListView<Label> listTool;
    
    @FXML
    private Label infrastructureLabel;

    private Stage stage;
    private static BorderPane root3;
    private HashMap<Label,TreeItem<String>> attributeMap = new HashMap<Label, TreeItem<String>>();

    public ToolController() {
    	
   	}
    
    @FXML
    public void handleMouseClick(MouseEvent e){
        Label selectedLabel =  listTool.getSelectionModel().getSelectedItem();
        if(selectedLabel != null){
            Platform.runLater(new Runnable() {
                @Override public void run() {
                   
                    
                    FXMLLoader loaderServices = new FXMLLoader();
                    loaderServices.setLocation(Main.class.getResource("view/ServiceAttributes.fxml"));
                    root3 = null;
                    try {
                        root3 = loaderServices.load();
                    }
                    catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    
                    ToolModifyController serviceModifyController = loaderServices.getController();
                    
                    Stage dialogStage = new Stage();
                    dialogStage.setTitle("Modification d'attribut");
                    //fenetre modale, obligation de quitter pour revenir a la fenetre principale
                    dialogStage.initModality(Modality.WINDOW_MODAL);
                    dialogStage.initOwner(buttonAddService.getScene().getWindow());
                    Scene miniScene = new Scene(root3);
                    dialogStage.setScene(miniScene);
                    dialogStage.initStyle(StageStyle.UNDECORATED);
                    dialogStage.setMinHeight(380);
                    dialogStage.setMinWidth(440);
                    
                    serviceModifyController.setStage(dialogStage);
                    System.out.println(attributeMap);
                    System.out.println(listTool);
                    serviceModifyController.init(attributeMap, listTool);
                    
                    
                    dialogStage.showAndWait();
                    listTool.getSelectionModel().clearSelection();
                }
                
            });
        }   
    }
    
    @FXML
    public void addTool() throws IOException {

        stage = new Stage();
        stage.setTitle("Ajouter un service");
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ToolDialog.fxml"));
        DialogPane root = (DialogPane) loader.load();
        ToolDialogController toolDialogController = loader.getController();
//        toolDialogController.setList(listTool);
        toolDialogController.setAttributeMap(attributeMap);
//        toolDialogController.i
        stage.initModality(Modality.WINDOW_MODAL);
        Window window = buttonAddService.getScene().getWindow();
        stage.initOwner(buttonAddService.getScene().getWindow());
        stage.initStyle(StageStyle.UNDECORATED);

        Scene myScene = new Scene(root);

        double x = window.getX() + (window.getWidth() - root.getPrefWidth()) / 2;
        double y = window.getY() + (window.getHeight() - root.getPrefHeight()) / 2;
        stage.setX(x);
        stage.setY(y);

        stage.setScene(myScene);
        
        stage.showAndWait();
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
    	
		ArrayList<String> list = new ArrayList<>();
    	for (Label label: listTool.getItems()) {
    		list.add(label.getText());
    	}
    	
    	ToolService.getInstance().setTools(FXCollections.observableArrayList(list));	

    	InfrastructureService.getInstance().setLabelInfrastructures(infrastructureLabel);
    	
		ToolService.getInstance().getTools().addListener(new ListChangeListener<String>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				String newTool = ToolService.getInstance().getTools().get(ToolService.getInstance().getTools().size() - 1);
		        Label newToolLabel = new Label(newTool);
		        newToolLabel.setFont(new Font("OpenSymbol", Const.FONT_SIZE));
                attributeMap.put(newToolLabel, new TreeItem<String>(newToolLabel.getText()));
		        listTool.getItems().add(newToolLabel);
			}
		});
    	
    	
	}

}