package dad.micv.dialogs;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import dad.micv.model.Experiencia;
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

public class ExperienciaDialog extends Dialog<Experiencia>implements Initializable {

	//model
	private StringProperty denominacion = new SimpleStringProperty();
	private StringProperty empleador = new SimpleStringProperty();
	private ObjectProperty<LocalDate> desde = new SimpleObjectProperty<>();
	private ObjectProperty<LocalDate> hasta = new SimpleObjectProperty<>();

	
    @FXML
    private TextField denominacionText;

    @FXML
    private DatePicker desdeDate;

    @FXML
    private TextField empleadorText;

    @FXML
    private DatePicker hastaDate;

    @FXML
    private GridPane view;
    
    public ExperienciaDialog() {
    	super();
    	try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/customAlerts/NuevaExperienciaView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		ButtonType crearButtonType = new ButtonType("Crear", ButtonData.OK_DONE);

		setTitle("Nuevo experiencia");
		
		getDialogPane().setContent(view);
		getDialogPane().getButtonTypes().addAll(crearButtonType, ButtonType.CANCEL);
		
		setResultConverter(buttonType -> onCovertResult(buttonType));
		
		Button crearButton = (Button) getDialogPane().lookupButton(crearButtonType);
		crearButton.disableProperty().bind(denominacion.isEmpty().or(empleador.isEmpty().or(desde.isNull().or(hasta.isNull()))));
		
		//bindings
		denominacion.bind(denominacionText.textProperty());
		empleador.bind(empleadorText.textProperty());
		desde.bind(desdeDate.valueProperty());
		hasta.bind(hastaDate.valueProperty());
		
		denominacionText.requestFocus();
	}
	
	private Experiencia onCovertResult(ButtonType buttonType) {
		if(buttonType.getButtonData() == ButtonData.OK_DONE) {
			Experiencia experiencia = new Experiencia();
			experiencia.setDenominacion(denominacion.get());
			experiencia.setEmpleador(empleador.get());
			experiencia.setDesde(desdeDate.getValue());
			experiencia.setHasta(hastaDate.getValue());
			return experiencia;
		}
		return null;
	}
	
	public GridPane getView() {
		return view;
	}
}
