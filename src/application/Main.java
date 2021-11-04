package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * The "Model" Component of the MVC Design pattern. Purpose is to generate the stage for the other two components.
 * @author Jason Li, John Leng
 */


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("SampleView.fxml"));
			Scene scene = new Scene(root,660,600);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setTitle("Tuition Manager");
			primaryStage.setScene(scene);
			primaryStage.show();
			Roster roster = new Roster();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
