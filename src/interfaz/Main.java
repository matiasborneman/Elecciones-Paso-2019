package interfaz;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // ventana de incio
        Parent root = FXMLLoader.load(getClass().getResource("Loading.fxml"));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("elecciones2019.png")));
        primaryStage.setScene(new Scene(root));
        // stilo inicial sin botones
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
