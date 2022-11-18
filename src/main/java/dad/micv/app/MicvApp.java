package dad.micv.app;

import dad.micv.controller.MainController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MicvApp extends Application {

	public static Stage primaryStage;
	private MainController mainController;

	@Override
	public void start(Stage primaryStage) throws Exception {
		MicvApp.primaryStage = primaryStage;
		mainController = new MainController();
		
		primaryStage.setTitle("Micv");
		primaryStage.setScene(new Scene(mainController.getView()));
		primaryStage.getIcons().add(new Image("/images/cv64x64.png"));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
