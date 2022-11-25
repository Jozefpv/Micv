package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.app.MicvApp;
import dad.micv.dialogs.ExperienciaDialog;
import dad.micv.model.Experiencia;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.util.converter.LocalDateStringConverter;

public class ExperienciaController implements Initializable {

	//model
//	ObjectProperty<CV> cv = new SimpleObjectProperty<>();
	// Solo necestio la lista como modelo por lo tanto al no ser un objeto complejo no necesito listener
	//
	ListProperty<Experiencia> experiencia = new SimpleListProperty<>(FXCollections.observableArrayList());
	
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

//		cv.addListener((o, ov, nv) -> onExperienciaChanged(o, ov, nv));
//		cv.set(new CV());
		//Preguntar porque funciona???
		
		experienciaTable.itemsProperty().bind(experiencia);
		denominacionCol.setCellValueFactory(v -> v.getValue().denominacionProperty());
		empleadorCol.setCellValueFactory(v -> v.getValue().empleadorProperty());
		desdeCol.setCellValueFactory(v -> v.getValue().desdeProperty());
		hastaCol.setCellValueFactory(v -> v.getValue().hastaProperty());
		
		//Para poder editar sobre la propia tabla
		denominacionCol.setCellFactory(TextFieldTableCell.forTableColumn());
		empleadorCol.setCellFactory(TextFieldTableCell.forTableColumn());
		desdeCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		hastaCol.setCellFactory(TextFieldTableCell.forTableColumn(new LocalDateStringConverter()));
		
		deleteBoton.disableProperty().bind(experienciaTable.getSelectionModel().selectedItemProperty().isNull());
		

	}
	
//    private void onExperienciaChanged(ObservableValue<? extends CV> o, CV ov, CV nv) {
//    	
//    	if(ov != null) {
//        	experienciaTable.itemsProperty().unbind();
//
//    	}
//    	
//    	if(nv != null) {
//        	experienciaTable.itemsProperty().bind(nv.experienciasProperty());
//        	//PREGUNTAR SI ESTO ESTÁ BIEN
//    	}
//    	
//	}
	@FXML
    void onAddAction(ActionEvent event) {
    	ExperienciaDialog dialog = new ExperienciaDialog();
    	dialog.initOwner(MicvApp.primaryStage);
    	Experiencia experiencia = dialog.showAndWait().orElse(null);
    	if(experiencia != null) {
    		getExperiencia().add(experiencia);
    		
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
        		getExperiencia().remove(experienciaTable.getSelectionModel().selectedIndexProperty().get());
        	} 
    	}
    }
	
	public BorderPane getView() {
		return view;
	}
//	public final ObjectProperty<CV> cvProperty() {
//		return this.cv;
//	}
//	
//	public final CV getCv() {
//		return this.cvProperty().get();
//	}
//	
//	public final void setCv(final CV cv) {
//		this.cvProperty().set(cv);
//	}
	public final ListProperty<Experiencia> experienciaProperty() {
		return this.experiencia;
	}
	
	public final ObservableList<Experiencia> getExperiencia() {
		return this.experienciaProperty().get();
	}
	
	public final void setExperiencia(final ObservableList<Experiencia> experiencia) {
		this.experienciaProperty().set(experiencia);
	}
	
	
	
	
	

}
