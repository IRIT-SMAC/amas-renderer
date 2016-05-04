package fr.irit.smac.amasrenderer.controller;

import fr.irit.smac.amasrenderer.Const;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * The Class ServiceDialogController This controller manages the popup form
 */
public class ServiceDialogController {

    /** The confirm button */
    @FXML
    private Button buttonConfirm;

    /** The cancel button */
    @FXML
    private Button buttonCancel;

    /** The new service textfield */
    @FXML
    private TextField textfieldService;

    /** The instance of the list in which we add the new service */
    private ListView<Label> serviceList;

    /**
     * @param list
     *            The instance of the list in which we add the new service
     */
    public ServiceDialogController(ListView<Label> list) {
        this.serviceList = list;
    }

    /**
     * Click on the confirm button handler
     */
    @FXML
    public void clickConfirm() {
        if (textfieldService.getText() != null && !textfieldService.getText().trim().isEmpty()
                && !textfieldService.getText().trim().contains(" ")) {

            Label nouveauService = new Label(textfieldService.getText().trim());
            nouveauService.setFont(new Font("OpenSymbol", Const.FONT_SIZE));

            boolean found = false;

            for (Label item : serviceList.getItems()) {
                if (item.getText().equals(nouveauService.getText())){
                    found = true;
                }
            }

            if(!found){
                this.serviceList.getItems().add(nouveauService);
            }

            ((Stage) buttonConfirm.getScene().getWindow()).close();
        } else {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("Champs vide ou nom invalide");
            alert.showAndWait();
        }
    }

    /**
     * Click on the cancel button handler
     */
    @FXML
    public void clickCancel() {
        ((Stage) buttonCancel.getScene().getWindow()).close();
    }
}
