package dad.micv.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.util.ResourceBundle;

import org.hildan.fxgson.FxGson;

import com.google.gson.Gson;

import dad.micv.adapter.LocalDateAdapter;
import dad.micv.app.MicvApp;
import dad.micv.model.CV;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

public class MainController implements Initializable {

	private static Gson gson = FxGson.fullBuilder().setPrettyPrinting()
			.registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

	//model
	private ObjectProperty<CV> cv = new SimpleObjectProperty<>();
	
	// controllers
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

	public MainController() {

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

		abrir.setOnAction(e -> openFile());
		guardarComo.setOnAction(e -> saveAs());
		
		cv.set(new CV());
	}

	private void saveAs() {
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Guardar como");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("All Files", "*.*"));
		File cvFile = fileChooser.showSaveDialog(MicvApp.primaryStage);

		if(cvFile != null) {
			String json = gson.toJson(cv.get(), CV.class);
			try {
				Files.writeString(cvFile.toPath(), json, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void openFile() {
		FileChooser fileChooser = new FileChooser();
		File selectedFile = fileChooser.showOpenDialog(MicvApp.primaryStage);

	}

	public BorderPane getView() {
		return mainView;
	}

}
