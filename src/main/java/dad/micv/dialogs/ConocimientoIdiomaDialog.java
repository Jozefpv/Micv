package dad.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import dad.micv.model.Conocimiento;
import dad.micv.model.Nivel;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class ConocimientoIdiomaDialog extends Dialog<Conocimiento> implements Initializable {

	//model
	private StringProperty denominacion = new SimpleStringProperty();
	private ObjectProperty<Nivel> nivel = new SimpleObjectProperty<>();
	private StringProperty certificacion = new SimpleStringProperty();
 

	
    @FXML
    private Button deleteButton;

    @FXML
    private TextField denominacionText;
    
    @FXML
    private TextField certificacionText;

    @FXML
    private ComboBox<Nivel> nivelCombo;

    @FXML
    private GridPane view;
    
    public ConocimientoIdiomaDialog() {
    	super();
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/customAlerts/NuevoConocimientoIdiomaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
    
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		ButtonType crearButtonType = new ButtonType("Crear", ButtonData.OK_DONE);

		setTitle("Nuevo conocimiento");
		
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);
		
		setResultConverter(buttonType -> onCovertResult(buttonType));
		
		Button crearButton = (Button) getDialogPane().lookupButton(crearButtonType);
		crearButton.disableProperty().bind(denominacion.isEmpty().or(nivel.isNull().or(certificacion.isEmpty())));
		//bindings
		denominacion.bind(denominacionText.textProperty());
		certificacion.bind(certificacionText.textProperty());
		nivel.bind(nivelCombo.getSelectionModel().selectedItemProperty());
		deleteButton.disableProperty().bind(nivel.isNull());
		
		//load combo
		nivelCombo.getItems().setAll(Nivel.values());
		
		denominacionText.requestFocus();
		
	}
	
	private Conocimiento onCovertResult(ButtonType buttonType) {
		if(buttonType.getButtonData() == ButtonData.OK_DONE) {
			Conocimiento conocimiento = new Conocimiento();
			conocimiento.setDenominacion(denominacion.get());
			conocimiento.setNivel(nivel.get());
			return conocimiento;
		}
		return null;
	 }
	
    @FXML
    void onDeleteAction(ActionEvent event) {
    	nivelCombo.getSelectionModel().select(null);
    }


}
