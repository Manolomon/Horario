/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Manolo
 */
public class AgregarDatosController implements Initializable {
    
    @FXML
    private JFXButton btn_back;

    @FXML
    private JFXComboBox<String> cmb_ee;

    @FXML
    private JFXComboBox<String> cmb_dia;
    
    @FXML
    private StackPane rootPane;

    @FXML
    private JFXTimePicker time_inicio;

    @FXML
    private JFXTimePicker time_fin;

    @FXML
    private JFXTextField txt_salon;

    @FXML
    private JFXTextField txt_profesor;

    @FXML
    private JFXTextField txt_nota;

    @FXML
    private JFXButton btn_agregar;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rootPane.setOpacity(0);
        fadeInTransition();
    }
    
    public void fadeInTransition() {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(300));
        transition.setNode(rootPane);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    @FXML
    void clickBack(ActionEvent event) {
        fadeOutTransition();
        loadHorarioScene();
    }
    
    public void fadeOutTransition() {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(300));
        transition.setNode(rootPane);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.setOnFinished((ActionEvent event) -> {
            loadHorarioScene();
        });
        transition.play();
    }
 //hgfsdhjgsdjhg
    public void loadHorarioScene(){
        try {
            Parent horarioView;
            horarioView = FXMLLoader.load(getClass().getResource("/view/FXMLHorario.fxml"));
            Scene newScene = new Scene(horarioView);
            Stage curStage = (Stage) rootPane.getScene().getWindow();
            curStage.setScene(newScene);
        } catch (IOException e){
            System.out.println("No se enecontró: " + e);
        }
    }
    
}
