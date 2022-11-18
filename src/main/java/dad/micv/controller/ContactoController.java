package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import dad.micv.app.MicvApp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;

public class ContactoController implements Initializable {

	@FXML
	private TableColumn<?, ?> emailTab;

	@FXML
	private TableView<?> emailTable;

	@FXML
	private TableColumn<?, ?> numeroCol;

	@FXML
	private TableView<?> telefonosTable;

	@FXML
	private TableColumn<?, ?> tipoCol;

	@FXML
	private TableColumn<?, ?> urlCol;

	@FXML
	private TableView<?> webTable;

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

	}

	public SplitPane getView() {
		return view;
	}
	
    @FXML
    void onAddEmailBoton(ActionEvent event) {
    	TextInputDialog dialog = new TextInputDialog();
		dialog.initOwner(MicvApp.primaryStage);
		dialog.setTitle("Nuevo e-mail");
		dialog.setHeaderText("Crear una nueva dirección de correo.");
		dialog.setContentText("E-mail: ");
		Optional<String> email = dialog.showAndWait();
    }

	@FXML
	void OnDeleteTelefonoAction(ActionEvent event) {
		
		
	}

}
