package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.app.MicvApp;
import dad.micv.dialogs.ConocimientoDialog;
import dad.micv.dialogs.ConocimientoIdiomaDialog;
import dad.micv.model.Conocimiento;
import dad.micv.model.Nivel;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;

public class ConocimientosController implements Initializable {

	// model
	ListProperty<Conocimiento> habilidades = new SimpleListProperty<>(FXCollections.observableArrayList());

	@FXML
	private Button addConocimientoBoton;

	@FXML
	private Button addIdiomaBoton;

	@FXML
	private TableView<Conocimiento> conocimientosTable;

	@FXML
	private Button deleteBoton;

	@FXML
	private TableColumn<Conocimiento, String> denominacionCol;

	@FXML
	private TableColumn<Conocimiento, Nivel> nivelCol;

	@FXML
	private BorderPane view;

	public ConocimientosController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ConocimientosView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		conocimientosTable.itemsProperty().bind(habilidades);

		denominacionCol.setCellValueFactory(v -> v.getValue().denominacionProperty());
		nivelCol.setCellValueFactory(v -> v.getValue().nivelProperty());

		// Para poder editar sobre la propia tabla
		denominacionCol.setCellFactory(TextFieldTableCell.forTableColumn());
		nivelCol.setCellFactory(ComboBoxTableCell.forTableColumn(Nivel.values())); // al ser una enumeracion con unos
																					// determinados valores tenemos que
																					// usar el combo

		deleteBoton.disableProperty().bind(conocimientosTable.getSelectionModel().selectedItemProperty().isNull());
	}

	@FXML
	void onAddConocimientoAction(ActionEvent event) {
		ConocimientoDialog dialog = new ConocimientoDialog();
		dialog.initOwner(MicvApp.primaryStage);
		Conocimiento conocimiento = dialog.showAndWait().orElse(null);
		if (conocimiento != null) {
			habilidadesProperty().add(conocimiento);
		}
	}

	@FXML
	void onAddIdiomaAction(ActionEvent event) {
		ConocimientoIdiomaDialog dialog = new ConocimientoIdiomaDialog();
		dialog.initOwner(MicvApp.primaryStage);
		Conocimiento conocimiento = dialog.showAndWait().orElse(null);
		if (conocimiento != null) {
			habilidadesProperty().add(conocimiento);
		}
	}

	@FXML
	void onDeleteAction(ActionEvent event) {
		if (!conocimientosTable.getSelectionModel().isEmpty()) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(MicvApp.primaryStage);
			alert.setTitle("Confirmacion de eliminar");
			alert.setHeaderText("Confirmacion de eliminar");
			alert.setContentText("Estás seguro que desea eliminar?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				getHabilidades().remove(conocimientosTable.getSelectionModel().selectedIndexProperty().get());
			}
		}
	}

	public BorderPane getView() {
		return view;
	}

	public final ListProperty<Conocimiento> habilidadesProperty() {
		return this.habilidades;
	}

	public final ObservableList<Conocimiento> getHabilidades() {
		return this.habilidadesProperty().get();
	}

	public final void setHabilidades(final ObservableList<Conocimiento> habilidades) {
		this.habilidadesProperty().set(habilidades);
	}

}
