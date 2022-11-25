package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.app.MicvApp;
import dad.micv.dialogs.TelefonoDialog;
import dad.micv.model.Contacto;
import dad.micv.model.Email;
import dad.micv.model.Telefono;
import dad.micv.model.TipoTelefono;
import dad.micv.model.Web;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;

public class ContactoController implements Initializable {

	private ObjectProperty<Contacto> contacto = new SimpleObjectProperty<>();

	@FXML
	private Button addEmailBoton;

	@FXML
	private Button addTelefonoBoton;

	@FXML
	private Button addUrlBoton;

	@FXML
	private Button deleteEmailBoton;

	@FXML
	private Button deleteTelefonoBoton;

	@FXML
	private Button deleteUrlBoton;

	@FXML
	private TableColumn<Email, String> emailCol;

	@FXML
	private TableView<Email> emailTable;

	@FXML
	private TableColumn<Telefono, String> numeroCol;

	@FXML
	private TableView<Telefono> telefonosTable;

	@FXML
	private TableColumn<Telefono, TipoTelefono> tipoCol;

	@FXML
	private TableColumn<Web, String> urlCol;

	@FXML
	private TableView<Web> webTable;

	@FXML
	private SplitPane view;

	public ContactoController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ContactoView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		contacto.addListener((o, ov, nv) -> onContactoChanged(o, ov, nv));
		// bindigs

		numeroCol.setCellValueFactory(v -> v.getValue().numeroProperty());
		tipoCol.setCellValueFactory(v -> v.getValue().tipoProperty());
		numeroCol.setCellFactory(TextFieldTableCell.forTableColumn());
		tipoCol.setCellFactory(ComboBoxTableCell.forTableColumn(TipoTelefono.values()));

		emailCol.setCellValueFactory(v -> v.getValue().direccionProperty());
		emailCol.setCellFactory(TextFieldTableCell.forTableColumn());

		urlCol.setCellValueFactory(v -> v.getValue().urlProperty());
		urlCol.setCellFactory(TextFieldTableCell.forTableColumn());

		deleteTelefonoBoton.disableProperty().bind(telefonosTable.getSelectionModel().selectedItemProperty().isNull());
		// deleteTelefonoBoton.disableProperty().bind(Bindings.isEmpty(model.telefonosProperty()));
		// (esto solo seria si no hay elementos en la lista)

		deleteEmailBoton.disableProperty().bind(emailTable.getSelectionModel().selectedItemProperty().isNull());

		deleteUrlBoton.disableProperty().bind(webTable.getSelectionModel().selectedItemProperty().isNull());
	}

	private void onContactoChanged(ObservableValue<? extends Contacto> o, Contacto ov, Contacto nv) {
		if (ov != null) {
			telefonosTable.itemsProperty().unbindBidirectional(ov.telefonosProperty());
			emailTable.itemsProperty().unbindBidirectional(ov.emailsProperty());
			webTable.itemsProperty().unbindBidirectional(ov.websProperty());
		}
		
		if (nv != null) {
			telefonosTable.itemsProperty().bindBidirectional(nv.telefonosProperty());
			emailTable.itemsProperty().bindBidirectional(nv.emailsProperty());
			webTable.itemsProperty().bindBidirectional(nv.websProperty());
		}
	}

	public SplitPane getView() {
		return view;
	}

	@FXML
	void OnAddTelefonoAction(ActionEvent event) {
		TelefonoDialog dialog = new TelefonoDialog();
		dialog.initOwner(MicvApp.primaryStage);
		Telefono telefono = dialog.showAndWait().orElse(null);
		if (telefono != null) {
			getContacto().telefonosProperty().add(telefono);
		}
	}

	@FXML
	void OnDeleteTelefonoAction(ActionEvent event) {
		if (!telefonosTable.getSelectionModel().isEmpty()) {
			getContacto().telefonosProperty().remove(telefonosTable.getSelectionModel().selectedIndexProperty().get());
		}
	}

	@FXML
	void onAddEmailBoton(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(MicvApp.primaryStage);
		dialog.setTitle("Nuevo e-mail");
		dialog.setHeaderText("Crear una nueva dirección de correo.");
		dialog.setContentText("E-mail: ");
		dialog.showAndWait().ifPresent(t -> { // otra forma de hacerlo usando ifPresent
			Email email = new Email();
			email.setDireccion(t);
			getContacto().emailsProperty().add(email);
		});

	}

	@FXML
	void onDeleteEmailBoton(ActionEvent event) {
		if (!emailTable.getSelectionModel().isEmpty()) {
			getContacto().emailsProperty().remove(emailTable.getSelectionModel().selectedIndexProperty().get());
		}
	}

	@FXML
	void onAddUrlBoton(ActionEvent event) {
		TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(MicvApp.primaryStage);
		dialog.setTitle("Nueva web");
		dialog.setHeaderText("Crear una nueva dirección web.");
		dialog.setContentText("URL: ");
		Optional<String> urlText = dialog.showAndWait();
		if (urlText.isPresent()) {
			Web web = new Web();
			web.setUrl(urlText.get());
			getContacto().websProperty().add(web);

		}

	}

	@FXML
	void onDeleteUrlBoton(ActionEvent event) {
		if (!webTable.getSelectionModel().isEmpty()) {
			getContacto().websProperty().remove(webTable.getSelectionModel().selectedIndexProperty().get());
		}
	}

	public final ObjectProperty<Contacto> contactoProperty() {
		return this.contacto;
	}

	public final Contacto getContacto() {
		return this.contactoProperty().get();
	}

	public final void setContacto(final Contacto contacto) {
		this.contactoProperty().set(contacto);
	}

}
