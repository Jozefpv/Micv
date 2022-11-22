package dad.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;


import dad.micv.model.Titulo;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;

public class TituloDialog extends Dialog<Titulo>implements Initializable {
	
	//model
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty organizador = new SimpleStringProperty();
	private ObjectProperty<LocalDate> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> hasta = new SimpleObjectProperty<>();

	
    @FXML
    private TextField denominacionText;

    @FXML
    private DatePicker desdeDate;

    @FXML
    private DatePicker hastaDate;

    @FXML
    private TextField organizadorText;
    
    @FXML
    private GridPane view;

    public TituloDialog() {
    	super();
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/customAlerts/NuevoTituloView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ButtonType crearButtonType = new ButtonType("Crear", ButtonData.OK_DONE);

		setTitle("Nuevo título");
		
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);
		
		setResultConverter(buttonType -> onCovertResult(buttonType));
		
		Button crearButton = (Button) getDialogPane().lookupButton(crearButtonType);
		crearButton.disableProperty().bind(denominacion.isEmpty().or(organizador.isEmpty().or(desde.isNull().or(hasta.isNull()))));
		
		//bindings
		denominacion.bind(denominacionText.textProperty());
		organizador.bind(organizadorText.textProperty());
		desde.bind(desdeDate.valueProperty());
		hasta.bind(hastaDate.valueProperty());
		
		denominacionText.requestFocus();

	}
	
	private Titulo onCovertResult(ButtonType buttonType) {
		if(buttonType.getButtonData() == ButtonData.OK_DONE) {
			Titulo titulo = new Titulo();
			titulo.setDenominacion(denominacion.get());
			titulo.setOrganizador(organizador.get());
			titulo.setDesde(desdeDate.getValue());
			titulo.setHasta(hastaDate.getValue());
			return titulo;
		}
		return null;
	}
	
	public GridPane getView() {
		return view;
	}
}
