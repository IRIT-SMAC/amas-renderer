package fr.irit.smac.amasrenderer.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.stage.FileChooser;
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

	@FXML
	private Button generateButton;

	private Stage stage;
	
	private static final Logger LOGGER = Logger.getLogger(ServicesController.class.getName());

	public ServicesController() {

	}

	@FXML
	public void addService() {

		stage = new Stage();
		stage.setTitle("Ajouter un service");
		stage.setResizable(false);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("view/ServiceDialog.fxml"));
		DialogPane root = null;
		try {
			root = (DialogPane) loader.load();
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
		} catch (IOException e) {
			LOGGER.log(Level.SEVERE, "Couldn't load the dialog frame" , e);
		}
	}
	
	@FXML
	public void generateJsonFile(){
		File file = new FileChooser().showSaveDialog(generateButton.getScene().getWindow());
		
		List<String> lines = new ArrayList<String>();
		
		createLines(lines);
		
		try {
			Files.write(file.toPath(), lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			LOGGER.log(Level.INFO, "Couldn't create the file" , e);
		}
		
	}

	private void createLines(List<String> lines) {
//		lines.add("{");
//		lines.add("\"classname\":\""+InfrastructureService.getInstance().getInfrastructure().get(0)+"\"}");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ArrayList<String> list = new ArrayList<>();
		for (Label label: listServices.getItems()) {
			list.add(label.getText());
		}
		ServiceService.getInstance().setListServices(FXCollections.observableArrayList(list));

		ServiceService.getInstance().getListServices().addListener(new ListChangeListener<String>(){
			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				String newItem = ServiceService.getInstance().getListServices().get(ServiceService.getInstance().getListServices().size() - 1);
				Label nouveauService = new Label(newItem);
				nouveauService.setFont(new Font("OpenSymbol", Const.FONT_SIZE));
				listServices.getItems().add(nouveauService);
			}
		});

		InfrastructureService.getInstance().setInfrastructure(FXCollections.observableArrayList(new ArrayList<>()));

		InfrastructureService.getInstance().getInfrastructure().addListener(new ListChangeListener<String>(){

			@Override
			public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
				String nouvelleInfrastructure =  InfrastructureService.getInstance().getInfrastructure().get(0);
				infrastructureLabel.setText(nouvelleInfrastructure);
			}

		});





	}

}
