package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

public class ConocimientosController implements Initializable {

	
    @FXML
    private Button addConocimientoBoton;

    @FXML
    private Button addIdiomaBoton;

    @FXML
    private TableView<?> conocimientosTable;

    @FXML
    private Button deleteBoton;

    @FXML
    private TableColumn<?, ?> denominacionTab;

    @FXML
    private TableColumn<?, ?> nivelTab;

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
		// TODO Auto-generated method stub

	}
	
	public BorderPane getView() {
		return view;
	}

}
