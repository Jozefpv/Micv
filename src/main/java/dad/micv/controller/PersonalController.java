package dad.micv.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import dad.micv.app.MicvApp;
import dad.micv.model.Nacionalidad;
import dad.micv.model.Personal;
import javafx.beans.property.ListProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class PersonalController implements Initializable {

	ListProperty<String> paisesList = new SimpleListProperty<>(FXCollections.observableArrayList());
	ListProperty<Nacionalidad> nacionalidadesList = new SimpleListProperty<>(FXCollections.observableArrayList());

	private ObjectProperty<Personal> personal = new SimpleObjectProperty<>();

	@FXML
	private Button addBoton;

	@FXML
	private TextField apellidosText;

	@FXML
	private TextField codigoPostalText;

	@FXML
	private Button deleteBoton;

	@FXML
	private TextArea direccionText;

	@FXML
	private TextField dniText;

	@FXML
	private TextField localidadText;

	@FXML
	private DatePicker nacimientoDate;

	@FXML
	private TextField nombreText;

	@FXML
	private ComboBox<String> paisCombo;

	@FXML
	private ListView<Nacionalidad> nacionalidadList;

	@FXML
	private GridPane view;

	public PersonalController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonalView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// listeners

		personal.addListener(this::onPersonalChanged);

		// load data
		try {
			Path paisesFile = Paths.get(getClass().getResource("/csv/paises.csv").toURI());
			Path nacionalidadesFile = Paths.get(getClass().getResource("/csv/nacionalidades.csv").toURI());

			List<String> paises = Files.readAllLines(paisesFile, StandardCharsets.UTF_8);
			// paisCombo.getItems().setAll(paises); Y borro
			List<String> nacionalidades = Files.readAllLines(nacionalidadesFile, StandardCharsets.UTF_8);

			// PREGUNTAR SI ES NECESARIO CREAR UNAS LISTAS EN EL CONTROLADOR PARA CARGAR LOS
			// COMBOS
			paisesList.setAll(paises);
			nacionalidadesList.setAll(nacionalidades.stream().map(s -> {
				Nacionalidad n = new Nacionalidad();
				n.setDenominacion(s);
				return n;
			}).collect(Collectors.toList()));

		} catch (URISyntaxException | IOException e) {
			e.printStackTrace();
		}

		paisCombo.setItems(paisesList);

	}

	private void onPersonalChanged(ObservableValue<? extends Personal> o, Personal ov, Personal nv) {

		if (ov != null) {

			// unbindings
			
			dniText.textProperty().unbindBidirectional(ov.identificacionProperty());
			nombreText.textProperty().unbindBidirectional(ov.nombreProperty());
			apellidosText.textProperty().unbindBidirectional(ov.apellidosProperty());
			nacimientoDate.valueProperty().unbindBidirectional(ov.fechaNacimientoProperty());
			direccionText.textProperty().unbindBidirectional(ov.direccionProperty());
			codigoPostalText.textProperty().unbindBidirectional(ov.codigoPostalProperty());
			localidadText.textProperty().unbindBidirectional(ov.localidadProperty());
			ov.paisProperty().unbind();
			nacionalidadList.itemsProperty().unbind();

		}

		if (nv != null) {

			// bindings

			dniText.textProperty().bindBidirectional(nv.identificacionProperty());
			nombreText.textProperty().bindBidirectional(nv.nombreProperty());
			apellidosText.textProperty().bindBidirectional(nv.apellidosProperty());
			nacimientoDate.valueProperty().bindBidirectional(nv.fechaNacimientoProperty());
			direccionText.textProperty().bindBidirectional(nv.direccionProperty());
			codigoPostalText.textProperty().bindBidirectional(nv.codigoPostalProperty());
			localidadText.textProperty().bindBidirectional(nv.localidadProperty());
			nacionalidadList.itemsProperty().bind(nv.nacionalidadesProperty());

			paisCombo.getSelectionModel().select(nv.getPais());
			nv.paisProperty().bind(paisCombo.getSelectionModel().selectedItemProperty());

		}

	}

	@FXML
	void onBorrarNacionalidadAction(ActionEvent event) {
		if (!nacionalidadList.getSelectionModel().isEmpty()) {
			getPersonal().nacionalidadesProperty()
					.remove(nacionalidadList.getSelectionModel().selectedIndexProperty().get());
		}
	}

	@FXML
	void onNuevaNacionalidadAction(ActionEvent event) {

		ChoiceDialog<Nacionalidad> dialog = new ChoiceDialog<>();
		dialog.initOwner(MicvApp.primaryStage);
		dialog.setTitle("Nueva nacionalidad");
		dialog.setHeaderText("Añadir nacionalidad");
		dialog.setContentText("Seleccione una nacionalidad");
		dialog.getItems().setAll(nacionalidadesList);
		dialog.getItems().removeAll(getPersonal().getNacionalidades());
		dialog.setSelectedItem(dialog.getItems().get(0));
		Nacionalidad nacionalidad = dialog.showAndWait().orElse(null);

		if (nacionalidad != null) {
			getPersonal().getNacionalidades().add(nacionalidad);
		}

	}

	public GridPane getView() {
		return view;
	}

	public final ObjectProperty<Personal> personalProperty() {
		return this.personal;
	}

	public final Personal getPersonal() {
		return this.personalProperty().get();
	}

	public final void setPersonal(final Personal personal) {
		this.personalProperty().set(personal);
	}

}
