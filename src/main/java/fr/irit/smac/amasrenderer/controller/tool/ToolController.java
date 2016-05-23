package fr.irit.smac.amasrenderer.controller.tool;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
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
public class ToolController implements Initializable {

    @FXML
    private Button buttonAddService;

    @FXML
    private ListView<String> listTool;
    
    @FXML
    private TextField infrastructureTextField;

    @FXML
    private Button generateButton;
    
    private Stage stage;
    private static BorderPane root3;
    private HashMap<String,TreeItem<String>> attributeMap = new HashMap<String, TreeItem<String>>();
    private static final Logger LOGGER = Logger.getLogger(ToolController.class.getName());

    
    public ToolController() {
        
    }
    
    @FXML
    public void handleMouseClick(MouseEvent e){
        String selectedLabel =  listTool.getSelectionModel().getSelectedItem();
        if(selectedLabel != null && selectedLabel != ""){
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
                    System.out.println(listTool.getItems()+" danzodihazdgah");
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

    @FXML
    public void generateJsonFile(){
        File file = new FileChooser().showSaveDialog(generateButton.getScene().getWindow());
        
        List<String> lines = new ArrayList<String>();
        
        generateInfrastructure(lines);
        generateServices(lines);
        
//      try {
//          Files.write(file.toPath(), lines, Charset.forName("UTF-8"));
//      } catch (IOException e) {
//          LOGGER.log(Level.INFO, "Couldn't create the file" , e);
//      }
        
    }

    private void generateInfrastructure(List<String> lines) {
        lines.add("\t\"classname\":\""+InfrastructureService.getInstance().getInfrastructure().get(0)+"\"},");
    }
    
    private void generateServices(List<String> lines) {
        List<String> services = ToolService.getInstance().getTools();
        for(String service : services) {
            TreeItem<String> serviceAttribute = attributeMap.get(service);
            System.out.println(serviceAttribute);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        ArrayList<String> list = new ArrayList<>();
        for (String tool: listTool.getItems()) {
            list.add(tool);
        }
        
        ToolService.getInstance().setTools(FXCollections.observableArrayList(list));    

        ToolService.getInstance().getTools().addListener(new ListChangeListener<String>(){
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends String> c) {
                String newTool = ToolService.getInstance().getTools().get(ToolService.getInstance().getTools().size() - 1);
                attributeMap.put(newTool, new TreeItem<String>(newTool));
                listTool.getItems().add(newTool);
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
