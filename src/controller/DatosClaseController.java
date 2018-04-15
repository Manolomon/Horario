/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import jfxtras.scene.control.agenda.Agenda.Appointment;

/**
 *
 * @author Manolo
 * @since 14/04/2018
 */
public class DatosClaseController implements Initializable {

    @FXML
    private Label label_DayOfWeek;

    @FXML
    private Label label_FromTo;

    @FXML
    private JFXButton button_Eliminar;

    @FXML
    private JFXButton button_Editar;

    @FXML
    private Label label_Lugar;
    
    public void hola(){
        System.out.println("Holuuuu");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
}
