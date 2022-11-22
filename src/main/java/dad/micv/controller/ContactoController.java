package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;

import dad.micv.app.MicvApp;
import dad.micv.dialogs.TelefonoDialog;
import dad.micv.model.Contacto;
import dad.micv.model.Email;
import dad.micv.model.Telefono;
import dad.micv.model.TipoTelefono;
import dad.micv.model.Web;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

public class ContactoController implements Initializable {
	
	Contacto model = new Contacto();
	
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
		
		//bindigs
		
		telefonosTable.itemsProperty().bind(model.telefonosProperty());
		numeroCol.setCellValueFactory(v -> v.getValue().numeroProperty());
		tipoCol.setCellValueFactory(v -> v.getValue().tipoProperty());
		
		emailTable.itemsProperty().bind(model.emailsProperty());
		emailCol.setCellValueFactory(v -> v.getValue().direccionProperty());
		
		webTable.itemsProperty().bind(model.websProperty());
		urlCol.setCellValueFactory(v -> v.getValue().urlProperty());
		
		deleteTelefonoBoton.disableProperty().bind(telefonosTable.getSelectionModel().selectedItemProperty().isNull());
		//deleteTelefonoBoton.disableProperty().bind(Bindings.isEmpty(model.telefonosProperty()));  (esto solo seria si no hay elementos en la lista)
		
		deleteEmailBoton.disableProperty().bind(emailTable.getSelectionModel().selectedItemProperty().isNull());
		
		deleteUrlBoton.disableProperty().bind(webTable.getSelectionModel().selectedItemProperty().isNull());
	}

	public SplitPane getView() {
		return view;
	}
	
	@FXML
	void OnAddTelefonoAction(ActionEvent event) {
		TelefonoDialog dialog = new TelefonoDialog();
		dialog.initOwner(MicvApp.primaryStage);
		Telefono telefono = dialog.showAndWait().orElse(null);
		if(telefono != null) {			
			model.telefonosProperty().add(telefono);
		}
	}
	
	@FXML
	void OnDeleteTelefonoAction(ActionEvent event) {
		if(!telefonosTable.getSelectionModel().isEmpty()) {
			model.telefonosProperty().remove(telefonosTable.getSelectionModel().selectedIndexProperty().get());
		}
	}
	
    @FXML
    void onAddEmailBoton(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(MicvApp.primaryStage);
		dialog.setTitle("Nuevo e-mail");
		dialog.setHeaderText("Crear una nueva dirección de correo.");
		dialog.setContentText("E-mail: ");
		dialog.showAndWait().ifPresent(t ->  {  //otra forma de hacerlo usando ifPresent
			Email email = new Email();
			email.setDireccion(t);
			model.emailsProperty().add(email);
		});
		
	
    }
    
    @FXML
    void onDeleteEmailBoton(ActionEvent event) {
    	if(!emailTable.getSelectionModel().isEmpty()) {
    		model.emailsProperty().remove(emailTable.getSelectionModel().selectedIndexProperty().get());
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
		if(urlText.isPresent()) {
			Web web = new Web();
			web.setUrl(urlText.get());
			model.websProperty().add(web);

		}
		
    }
    
    @FXML
    void onDeleteUrlBoton(ActionEvent event) {
    	if(!webTable.getSelectionModel().isEmpty()) {
    		model.websProperty().remove(webTable.getSelectionModel().selectedIndexProperty().get());
    	}
    }
    

}
