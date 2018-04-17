/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.DAO.ClaseDAO;
import model.DAO.EEDAO;
import model.pojos.Clase;
import model.pojos.EE;

/**
 * 
 * @author Manolo Perez
 */
public class FXMLExperienciasController implements Initializable {
    @FXML
    private StackPane rootPane;

    @FXML
    private JFXButton hamburger;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXListView<Label> list_EE;

    @FXML
    private JFXButton btn_agregar;
    
    @FXML
    private Label label_nombreEE;

    @FXML
    private JFXTextField txt_nombre;

    @FXML
    private JFXTextField txt_profesor;

    @FXML
    private AnchorPane panel_diasSemana;

    @FXML
    private Label label_Lunes;

    @FXML
    private Label label_Martes;

    @FXML
    private Label label_Miercoles;

    @FXML
    private Label label_Jueves;

    @FXML
    private Label label_Viernes;

    @FXML
    private Label label_Sabado;

    @FXML
    private Label label_Domingo;

    @FXML
    private JFXButton btn_guardar;

    @FXML
    private JFXButton btn_cancelar;

    private JFXPopup popup;
    private JFXButton btn_eliminar;

    // ==================================================================================================================
    // Lista de datos de la Base de Datos
    private List<EE> experiencias;
    private List<Clase> clasesEE;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        list_EE.setExpanded(true);
        list_EE.depthProperty().set(1);
        rootPane.setOpacity(0);
        fadeInTransition(rootPane);
        drawer.setResizeContent(true);
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        initPopup();
        panel_diasSemana.setVisible(false);
        cargarLista();
    }

    public void cargarLista() {
        list_EE.getItems().clear();
        experiencias = EEDAO.getAllEEs();
        if (experiencias != null) {
            for (EE c : experiencias) {
                try {
                    Label lbl = new Label(c.getNombre());
                    lbl.setGraphic(new ImageView(
                            new Image(getClass().getResourceAsStream("/resources/ic_school_grey600_18dp.png"))));
                    list_EE.getItems().add(lbl);
                } catch (Exception ex) {
                    System.err.println("Error: " + ex);
                }
            }
        }
    }

    @FXML
    void cerrarDrawer(MouseEvent event) {
        drawer.close();
        drawer.setMouseTransparent(true);
    }

    @FXML
    void clickAgregarEE(ActionEvent event) {
        limpiarCampos();
    }

    public void limpiarCampos() {
        label_nombreEE.setText("Experiencia Educativa");
        txt_nombre.setText("");
        txt_profesor.setText("");
        fadeOutTransition(panel_diasSemana);
        panel_diasSemana.setVisible(false);
        unselectDiasSemana();
    }

    public boolean camposIncompletos() {
        return txt_nombre.getText().isEmpty() || txt_profesor.getText().isEmpty();
    }

    @FXML
    void clickCancelar(ActionEvent event) {
        limpiarCampos();
    }

    /**
     * Inicialización y muestra de un JFXDialog al centro de la pantalla, 
     * mandando una advertencia a alguna operación
     * @param head Título del dialog
     * @param body Texto principal del dialog
     */
    public void showDialog(String head, String body) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(head));
        content.setBody(new Text(body));
        JFXDialog dialog = new JFXDialog(rootPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton aceptar = new JFXButton("ACEPTAR");
        aceptar.setOnAction((ActionEvent e) -> {
            dialog.close();
        });
        content.setActions(aceptar);
        dialog.show();
    }

    @FXML
    void clickGuardar(ActionEvent event) {
        EE nuevoRegistro;
        if (!panel_diasSemana.isVisible()) {
            if (!camposIncompletos()) {
                nuevoRegistro = new EE(txt_nombre.getText(), txt_profesor.getText());
                if (EEDAO.registrar(nuevoRegistro)) {
                    showDialog("Guardada", "Experiencia Educativa almacenada con éxito");
                } else {
                    showDialog("Error", "No se pudo almacenar en la Base de Datos");
                }
            } else {
                showDialog("Campos Incompletos", "Por favor llene todos los campos necesarios");
            }
        } else {
            if (!camposIncompletos()) {
                nuevoRegistro = new EE(experiencias.get(list_EE.getSelectionModel().getSelectedIndex()).getIdEE(), txt_nombre.getText(), txt_profesor.getText());
                if (EEDAO.actualizar(nuevoRegistro)) {
                    showDialog("Actualizada", "Experiencia Educativa actualizada con éxito");
                } else {
                    showDialog("Error", "No se pudo almacenar en la Base de Datos");
                }
            } else {
                showDialog("Campos Incompletos", "Por favor llene todos los campos necesarios");
            }
        }
        limpiarCampos();
        cargarLista();
    }

    @FXML
    void clickHamburger(ActionEvent event) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/view/FXMLDrawer.fxml"));
            for (Node node : box.getChildren()) {
                node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                    if (node.getAccessibleText() != null) {
                        switch (node.getAccessibleText()) {
                        case "Horario":
                            fadeOutTransition(rootPane);
                            cargarHorario();
                            break;
                        case "Experiencias":
                            drawer.close();
                            drawer.setMouseTransparent(true);
                            break;
                        }
                    }
                });
            }
            drawer.setSidePane(box);
            drawer.setEffect(new DropShadow());
            drawer.open();
            drawer.setMouseTransparent(false);
        } catch (IOException e) {

        }
    }

    public void fadeInTransition(Parent element) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(300));
        transition.setNode(element);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    public void fadeOutTransition(Parent element) {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(300));
        transition.setNode(element);
        transition.setFromValue(1);
        transition.setToValue(0);
        transition.play();
    }

    public void cargarHorario() {
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

    /**
     * Inicialización del JFXPopup y llenado con la opción de "Eliminar",
     * los cuales se muestran dentro de la lista. Se inicialización del 
     * evento por la activación del botón Eliminar
     */
    public void initPopup() {
        btn_eliminar = new JFXButton("Eliminar");
        btn_eliminar.setPadding(new Insets(10));
        VBox vbox = new VBox(btn_eliminar);
        popup = new JFXPopup(list_EE);
        popup.setPopupContent(vbox);
        btn_eliminar.setOnAction((ActionEvent e) -> {
            popup.hide();
            eliminarEE();
        });
    }

    /**
     * Respuesta al evento de Mouse generado en la JFXListView contactList, en 
     * el que un click derecho muestra el JFXPopup para eliminar, y un click
     * derecho muestra los datos del contacto seleccionado en la lista.
     * @param event Evento de mouse, correspondiente a un click derecho o izquierdo
     */
    @FXML
    public void showPopup(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            popup.show(list_EE, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT, event.getX(), event.getY());
        } else {
            limpiarCampos();
            EE selected = experiencias.get(list_EE.getSelectionModel().getSelectedIndex());
            label_nombreEE.setText(selected.getNombre());
            txt_nombre.setText(selected.getNombre());
            txt_profesor.setText(selected.getProfesor());
            panel_diasSemana.setVisible(true);
            fadeInTransition(panel_diasSemana);
            diasSemana(selected);
        }
    }
    
    public void diasSemana(EE exp) {
        clasesEE = ClaseDAO.obtenerClasesAsociadas(exp.getIdEE());
        for(Clase clase: clasesEE){
            switch (clase.getDia()) {
            case "Lunes":
                label_Lunes.setTextFill(Color.web("#FFFFFF"));
                label_Lunes.setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
                break;
            case "Martes":
                label_Martes.setTextFill(Color.web("#FFFFFF"));
                label_Martes.setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
                break;
            case "Miércoles":
                label_Miercoles.setTextFill(Color.web("#FFFFFF"));
                label_Miercoles.setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
                
                break;
            case "Jueves":
                label_Jueves.setTextFill(Color.web("#FFFFFF"));
                label_Jueves.setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
                break;
            case "Viernes":
                label_Viernes.setTextFill(Color.web("#FFFFFF"));
                label_Viernes.setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
                break;
            case "Sábado":
                label_Sabado.setTextFill(Color.web("#FFFFFF"));
                label_Sabado.setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
                break;
            case "Domingo":
                label_Domingo.setTextFill(Color.web("#FFFFFF"));
                label_Domingo.setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
                break;
            }
        }
    }
    
    public void unselectDiasSemana() {
        label_Lunes.setTextFill(Color.web("#000000"));
        label_Lunes.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
        label_Martes.setTextFill(Color.web("#000000"));
        label_Martes.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
        label_Miercoles.setTextFill(Color.web("#000000"));
        label_Miercoles.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
        label_Jueves.setTextFill(Color.web("#000000"));
        label_Jueves.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
        label_Viernes.setTextFill(Color.web("#000000"));
        label_Viernes.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
        label_Sabado.setTextFill(Color.web("#000000"));
        label_Sabado.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
        label_Domingo.setTextFill(Color.web("#000000"));
        label_Domingo.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
    }
    
    public void eliminarEE(){
        int id = experiencias.get(list_EE.getSelectionModel().getSelectedIndex()).getIdEE();
        if (ClaseDAO.eliminarClases(id) && EEDAO.eliminar(id)) {
            showDialog("Eliminada", "Experiencia Educativas y Clases asociadas eliminadas con éxito");
            cargarLista();
        } else {
            showDialog("Error", "No se pudo eliminar la Clase");
        }
        limpiarCampos();
    }
}