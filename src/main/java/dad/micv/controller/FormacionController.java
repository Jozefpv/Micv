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

public class FormacionController implements Initializable {

	@FXML
    private Button addBoton;

    @FXML
    private Button deleteBoton;

    @FXML
    private TableColumn<?, ?> denominacionCol;

    @FXML
    private TableColumn<?, ?> desdeCol;

    @FXML
    private TableView<?> formacionTable;

    @FXML
    private TableColumn<?, ?> hastaCol;

    @FXML
    private TableColumn<?, ?> organizadorCol;

    @FXML
    private BorderPane view;
	
	public FormacionController() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FormacionView.fxml"));
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
