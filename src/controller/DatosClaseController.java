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
import model.pojos.Clase;

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
    
    public JFXButton getButtonEliminar() {
        return this.button_Eliminar;
    }
    
    public JFXButton getButtonEditar() {
        return this.button_Editar;
    }
    
    public void montarDatos(Clase clase) {
        label_DayOfWeek.setText(clase.getDia());
        label_FromTo.setText("Desde " + clase.getHoraInicio().toString() + " hasta " + clase.getHoraFin().toString());
        label_Lugar.setText(clase.getSalon());
    }
}
