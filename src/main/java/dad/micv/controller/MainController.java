package dad.micv.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

public class MainController implements Initializable {

	//controllers
	private PersonalController personalController = new PersonalController();
	private ContactoController contactoController = new ContactoController();
	private FormacionController formacionController = new FormacionController();
	private ExperienciaController experienciaController = new ExperienciaController();
	private ConocimientosController conocimientosController = new ConocimientosController();

    @FXML
    private MenuItem abrir;

    @FXML
    private Tab conocimientosTab;

    @FXML
    private Tab contactoTab;

    @FXML
    private Tab experienciaTab;

    @FXML
    private Tab formacionTab;

    @FXML
    private MenuItem guardar;

    @FXML
    private MenuItem guardarComo;

    @FXML
    private BorderPane mainView;

    @FXML
    private MenuItem nuevo;

    @FXML
    private Tab personalTab;

    @FXML
    private MenuItem salir;
	
	public MainController(){
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainView.fxml"));
			loader.setController(this);
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		personalTab.setContent(personalController.getView());
		contactoTab.setContent(contactoController.getView());
		formacionTab.setContent(formacionController.getView());
		experienciaTab.setContent(experienciaController.getView());
		conocimientosTab.setContent(conocimientosController.getView());
	}
	

	public BorderPane getView() {
		return mainView;
	}

}
