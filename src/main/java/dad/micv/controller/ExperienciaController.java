package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.app.MicvApp;
import dad.micv.dialogs.ExperienciaDialog;
import dad.micv.model.CV;
import dad.micv.model.Experiencia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;

public class ExperienciaController implements Initializable {

	CV model = new CV();
	
    @FXML
    private Button addBoton;

    @FXML
    private Button deleteBoton;
    
    @FXML
    private TableView<Experiencia> experienciaTable;

    @FXML
    private TableColumn<Experiencia, String> denominacionCol;

    @FXML
    private TableColumn<Experiencia, String> empleadorCol;

    @FXML
    private TableColumn<Experiencia, LocalDate> desdeCol;

    @FXML
    private TableColumn<Experiencia, LocalDate> hastaCol;

    @FXML
    private BorderPane view;

    
	public ExperienciaController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ExperienciaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		experienciaTable.itemsProperty().bind(model.experienciasProperty());
		denominacionCol.setCellValueFactory(v -> v.getValue().denominacionProperty());
		empleadorCol.setCellValueFactory(v -> v.getValue().empleadorProperty());
		desdeCol.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaCol.setCellValueFactory(v -> v.getValue().hastaProperty());
		
		deleteBoton.disableProperty().bind(experienciaTable.getSelectionModel().selectedItemProperty().isNull());
		

	}
	
    @FXML
    void onAddAction(ActionEvent event) {
    	ExperienciaDialog dialog = new ExperienciaDialog();
    	dialog.initOwner(MicvApp.primaryStage);
    	Experiencia experiencia = dialog.showAndWait().orElse(null);
    	if(experiencia != null) {
    		model.experienciasProperty().add(experiencia);
    	}
    	
    }

    @FXML
    void onDeleteAction(ActionEvent event) {
    	if(!experienciaTable.getSelectionModel().isEmpty()) {
    		Alert alert = new Alert(AlertType.CONFIRMATION);
        	alert.initOwner(MicvApp.primaryStage);
        	alert.setTitle("Confirmacion de eliminar");
        	alert.setHeaderText("Confirmacion de eliminar");
        	alert.setContentText("Estás seguro que desea eliminar?");

        	Optional<ButtonType> result = alert.showAndWait();
        	if (result.get() == ButtonType.OK){
        		model.getExperiencias().remove(experienciaTable.getSelectionModel().selectedIndexProperty().get());
        	} 
    	}
    }
	
	public BorderPane getView() {
		return view;
	}
	
	
	

}
