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
 * Clase controlador de la escena "DatosClase.fxml" 
 * llenada con datos de una Clase en específico
 * @author Manolo
 * @since 14/04/2018
 * @version 1.0
 */
public class DatosClaseController implements Initializable {

  // ==================================================================================================================
  // Elementos de la Interfaz FXML

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

  // ==================================================================================================================
  // Carga de GUI

  /**
   * Inicialización de la interfaz
   */
  @Override
  public void initialize(URL location, ResourceBundle resources) {

  }

  // ==================================================================================================================
  // Entrega de componentes

  /**
   * Método para retornar la referencia al Botón de Eliminar.
   * @return button_Eliminar
   */
  public JFXButton getButtonEliminar() {
    return this.button_Eliminar;
  }

  /**
   * Método para retornar la referencia al Botón de Editar.
   * @return button_Editar
   */
  public JFXButton getButtonEditar() {
    return this.button_Editar;
  }

  // ==================================================================================================================
  // Display de datos
  
  /**
   * Método para mostrar en la interfaz los datos de una Clase
   * @param clase Clase cuyos datos serán mostrados
   */
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
