/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import model.DAO.EEDAO;
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
    private Text txt_HoraInicio;

    @FXML
    private Text txt_HoraFin;

    @FXML
    private JFXButton button_Eliminar;

    @FXML
    private JFXButton button_Editar;

    @FXML
    private Label label_Lugar;

    @FXML
    private Label label_Profesor;

    @FXML
    private Label label_Nota;

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
        new SimpleDateFormat("HH:mm").format(clase.getHoraInicio());
        txt_HoraInicio.setText(new SimpleDateFormat("HH:mm").format(clase.getHoraInicio()));
        txt_HoraFin.setText(new SimpleDateFormat("HH:mm").format(clase.getHoraFin()));
        label_Lugar.setText(clase.getSalon());
        label_Profesor.setText(EEDAO.obtenerEE(clase.getIdEE()).get(0).getProfesor());
        label_Nota.setText(clase.getNota());
    }
}
