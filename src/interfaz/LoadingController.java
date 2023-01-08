package interfaz;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoadingController implements Initializable {
    public AnchorPane Intro;
    public ProgressBar pgb_inicio;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        new LoadingVentana().start();
    }

    class LoadingVentana extends Thread{
        @Override
        public void run(){
            try {
                Thread.sleep(4000);
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        Stage primaryStage= new Stage();
                        Task task = taskCreator(3);

                        new Thread(task).start();
                        pgb_inicio.progressProperty().unbind();
                        pgb_inicio.progressProperty().bind(task.progressProperty());
                        pgb_inicio.progressProperty().addListener(new ChangeListener<Number>() {
                            @Override
                            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {

                                if(t1.doubleValue()==1){
                                    try {
                                        Parent root = null;
                                        root = FXMLLoader.load(getClass().getResource("principal.fxml"));
                                        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("elecciones2019.png")));
                                        primaryStage.setTitle("Elecciones PASO 2019");
                                        primaryStage.setScene(new Scene(root,650,436));
                                        primaryStage.resizableProperty().setValue(false);
                                        primaryStage.show();
                                        Intro.getScene().getWindow().hide();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }

                        });
                    }
                });} catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    //Crear Task
    private Task taskCreator(int seconds){
        return new Task() {
            @Override
            protected Object call() throws Exception {
                for(int i=0; i<seconds;i++){
                    Thread.sleep(1000);
                    updateProgress(i+1, seconds);

                }
                return true;
            }
        };
    }
}}
