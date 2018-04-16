/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.util.StringConverter;
import model.DAO.ClaseDAO;
import model.DAO.EEDAO;
import model.pojos.Clase;
import model.pojos.EE;

/**
 *
 * @author Manolo
 */
public class AgregarDatosController implements Initializable {

    @FXML
    private JFXButton btn_back;

    @FXML
    private JFXComboBox<EE> cmb_ee;

    @FXML
    private JFXComboBox<String> cmb_dia;
    
    @FXML
    private Label pageTitle;

    @FXML
    private StackPane rootPane;

    @FXML
    private JFXTimePicker time_inicio;

    @FXML
    private JFXTimePicker time_fin;

    @FXML
    private JFXTextField txt_salon;

    @FXML
    private JFXTextField txt_nota;

    @FXML
    private JFXButton btn_agregar;

    private List<EE> experiencias;
    private List<Clase> clases;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        experiencias = EEDAO.getAllEEs();
        rootPane.setOpacity(0);
        fadeInTransition();
        initComboBox();
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
    }

    @FXML
    void agregarClick(ActionEvent event) {
        if (!camposIncompletos()) {
            if (!time_inicio.getValue().isAfter(time_fin.getValue())) {
                int idEE = cmb_ee.getSelectionModel().getSelectedItem().getIdEE();
                String dia = cmb_dia.getValue();
                Time horaInicio = java.sql.Time.valueOf(time_inicio.getValue());
                Time horaFin = java.sql.Time.valueOf(time_fin.getValue());
                String salon = txt_salon.getText();
                String nota = txt_nota.getText();
                Clase nuevaClase = new Clase(idEE, dia, horaInicio, horaFin, salon, nota);
                if (ClaseDAO.registrar(nuevaClase)) {
                    showDialog("Guardado", "Clase almacenada con éxito", true);
                } else {
                    showDialog("Error", "No se pudo almacenar en la Base de Datos", true);
                }
            } else {
                showDialog("Formato de Hora", "Las horas de inicio y fin no son consistentes", false);
            }
        } else {
            showDialog("Campos Incompletos", "Por favor llene todos los campos necesarios", false);
        }
    }

    /**
     * Verificación de que todos los campos necesarios esten llenos
     * @return Confirmación si los campos están vacíos
     */
    public boolean camposIncompletos() {
        return cmb_ee.getValue() == null || cmb_dia.getValue() == null || time_inicio.getValue() == null
                || time_fin.getValue() == null || txt_salon.getText().isEmpty() || txt_nota.getText().isEmpty();
    }

    /**
     * Inicialización y muestra de un JFXDialog al centro de la pantalla, 
     * mandando una advertencia a alguna operación
     * @param head Título del dialog
     * @param body Texto principal del dialog
     */
    public void showDialog(String head, String body, boolean salir) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(head));
        content.setBody(new Text(body));
        JFXDialog dialog = new JFXDialog(rootPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton aceptar = new JFXButton("ACEPTAR");
        aceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dialog.close();
                if(salir){
                    fadeOutTransition();
                }
            }
        });
        content.setActions(aceptar);
        dialog.show();
    }
    
    public void modificarClase(Clase clase) {
        for(EE e: experiencias){
            if(e.getIdEE() == clase.getIdEE()){
               cmb_ee.getSelectionModel().select(e);
            }
        }
        pageTitle.setText("Modificar Contacto");
        cmb_dia.getSelectionModel().select(clase.getDia());
        time_inicio.setValue(clase.getHoraInicio().toLocalTime());
        time_fin.setValue(clase.getHoraFin().toLocalTime());
        txt_salon.setText(clase.getSalon());
        txt_nota.setText(clase.getNota());
        btn_agregar.setText("Actualizar");
        
        btn_agregar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if (!camposIncompletos()) {
                    if (!time_inicio.getValue().isAfter(time_fin.getValue())) {
                        int idEE = cmb_ee.getSelectionModel().getSelectedItem().getIdEE();
                        String dia = cmb_dia.getValue();
                        Time horaInicio = java.sql.Time.valueOf(time_inicio.getValue());
                        Time horaFin = java.sql.Time.valueOf(time_fin.getValue());
                        String salon = txt_salon.getText();
                        String nota = txt_nota.getText();
                        Clase nuevaClase = new Clase(clase.getIdClase(), idEE, dia, horaInicio, horaFin, salon, nota);
                        if (ClaseDAO.actualizar(nuevaClase)) {
                            showDialog("Actualizado", "Clase actualizada con éxito", true);
                        } else {
                            showDialog("Error", "No se pudo actualizar en la Base de Datos", true);
                        }
                    } else {
                        showDialog("Formato de Hora", "Las horas de inicio y fin no son consistentes", false);
                    }
                } else {
                    showDialog("Campos Incompletos", "Por favor llene todos los campos necesarios", false);
                }
            }
        });
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

    public void loadHorarioScene() {
        try {
            StackPane horarioView;
            horarioView = FXMLLoader.load(getClass().getResource("/view/FXMLHorario.fxml"));
            Scene newScene = new Scene(horarioView);
            newScene.getStylesheets().add(getClass().getResource("/view/AgendaStyle.css").toExternalForm());
            Stage curStage = (Stage) rootPane.getScene().getWindow();
            curStage.setScene(newScene);
            curStage.show();
        } catch (IOException e) {
            System.out.println("No se enecontró: " + e);
        }
    }

    public void initComboBox() {
        cmb_ee.getItems().addAll(experiencias);
        cmb_ee.setConverter(new StringConverter<EE>() {
            @Override
            public String toString(EE experiencia) {
                if (experiencia == null) {
                    return "";
                } else {
                    return experiencia.getNombre();
                }
            }

            @Override
            public EE fromString(String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
        cmb_dia.getItems().addAll("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo");
    }

}
