package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.app.MicvApp;
import dad.micv.dialogs.TituloDialog;
import dad.micv.model.CV;
import dad.micv.model.Titulo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class FormacionController implements Initializable {

	CV model = new CV();
	@FXML
    private Button addBoton;

    @FXML
    private Button deleteBoton;

    @FXML
    private TableColumn<Titulo, String> denominacionCol;

    @FXML
    private TableColumn<Titulo, LocalDate> desdeCol;

    @FXML
    private TableView<Titulo> formacionTable;

    @FXML
    private TableColumn<Titulo, LocalDate> hastaCol;

    @FXML
    private TableColumn<Titulo, String> organizadorCol;

    @FXML
    private BorderPane view;
	
	public FormacionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		formacionTable.itemsProperty().bind(model.formacionProperty());
		denominacionCol.setCellValueFactory(v -> v.getValue().denominacionProperty());
		organizadorCol.setCellValueFactory(v -> v.getValue().organizadorProperty());
		desdeCol.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaCol.setCellValueFactory(v -> v.getValue().hastaProperty());

		deleteBoton.disableProperty().bind(formacionTable.getSelectionModel().selectedItemProperty().isNull());
	}
	
	public BorderPane getView() {
		return view;
	}
	
    @FXML
    void onAddAction(ActionEvent event) {
    	TituloDialog dialog = new TituloDialog();
    	dialog.initOwner(MicvApp.primaryStage);
    	Titulo titulo = dialog.showAndWait().orElse(null);
    	if(titulo != null) {
    		model.formacionProperty().add(titulo);
    	}
    }

    @FXML
    void onDeleteAction(ActionEvent event) {
    	if(!formacionTable.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.initOwner(MicvApp.primaryStage);
        	alert.setTitle("Confirmacion de eliminar");
        	alert.setHeaderText("Confirmacion de eliminar");
        	alert.setContentText("Estás seguro que desea eliminar?");

        	Optional<ButtonType> result = alert.showAndWait();
        	if (result.get() == ButtonType.OK){
        		model.getFormacion().remove(formacionTable.getSelectionModel().selectedIndexProperty().get());
        	} 
    	}
    }

}
