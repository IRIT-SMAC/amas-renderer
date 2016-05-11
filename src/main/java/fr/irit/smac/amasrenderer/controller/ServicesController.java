package fr.irit.smac.amasrenderer.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fr.irit.smac.amasrenderer.Const;
import fr.irit.smac.amasrenderer.Main;
import fr.irit.smac.amasrenderer.service.InfrastructureService;
import fr.irit.smac.amasrenderer.service.ServiceService;
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
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * The Class ServicesController.
 * This controller handles the service part of the main UI 
 */
public class ServicesController implements Initializable {

    @FXML
    private Button buttonAddService;

    @FXML
    private ListView<Label> listServices;
    
    @FXML
    private Label infrastructureLabel;

    private Stage stage;
    
    public ServicesController() {
    	
   	}
    
    @FXML
    public void addService() throws IOException {

        stage = new Stage();
        stage.setTitle("Ajouter un service");
        stage.setResizable(false);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(Main.class.getResource("view/ServiceDialog.fxml"));
        DialogPane root = (DialogPane) loader.load();
        
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
    	for (Label label: listServices.getItems()) {
    		list.add(label.getText());
    	}
    	
    	ServiceService.getInstance().setListServices(FXCollections.observableArrayList(list));	

    	InfrastructureService.getInstance().setLabelInfrastructures(infrastructureLabel);
    	
		ServiceService.getInstance().getListServices().addListener(new ListChangeListener<String>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				String newItem = ServiceService.getInstance().getListServices().get(ServiceService.getInstance().getListServices().size() - 1);
		        Label nouveauService = new Label(newItem);
		        nouveauService.setFont(new Font("OpenSymbol", Const.FONT_SIZE));
		        listServices.getItems().add(nouveauService);
			}
		});
    	
    	
	}

}
