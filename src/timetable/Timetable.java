/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Clase principal encargada de la carga de la primera escena del Horario
 * @author Manolo
 * @since 07/04/2018
 * @version 1.0
 */
public class Timetable extends Application {
    
    /**
     * Carga del primer Stage de la aplicación
     * @param stage Stage para el inicio de la aplicación
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/FXMLHorario.fxml"));
        stage.setTitle("Horario");
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/view/AgendaStyle.css").toExternalForm());
        stage.getIcons()
                .add(new Image(getClass().getResourceAsStream("/resources/ic_launcher.png")));
        stage.setResizable(false);
        stage.sizeToScene();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
