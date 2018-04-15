/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXDrawer;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.ColumnConstraints;
import static javafx.scene.layout.Region.USE_PREF_SIZE;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import jfxtras.scene.layout.GridPane;
import jfxtras.scene.layout.HBox;
import model.DAO.ClaseDAO;
import model.DAO.EEDAO;
import model.pojos.Clase;
import model.pojos.EE;

/**
 *
 * @author Manolo
 */
public class FXMLDocumentController implements Initializable {

    @FXML
    private StackPane rootPane;

    @FXML
    private AnchorPane AgendaPane;

    @FXML
    private Agenda agenda;

    @FXML
    private JFXButton btn_agregar;

    @FXML
    private JFXButton hamburger;

    @FXML
    private JFXDrawer drawer;
    
    // ==================================================================================================================
        // Componentes del JFXDialog, obtenidos de /view/FXMLDatosClase.fxml
    
//    @FXML
//    private Label label_DayOfWeek;
//
//    @FXML
//    private Label label_FromTo;
//
//    @FXML
//    private JFXButton button_Eliminar;
//
//    @FXML
//    private JFXButton button_Editar;
//
//    @FXML
//    private Label label_Lugar;
    
    // ==================================================================================================================
        // Lista de datos de la Base de Datos
    private List<EE> experiencias;
    private List<Clase> clases;
    private final Map<String, Agenda.AppointmentGroup> lAppointmentGroupMap = new TreeMap<String, Agenda.AppointmentGroup>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rootPane.setOpacity(0);
        fadeInTransition();
        drawer.setResizeContent(true);
        drawer.setOverLayVisible(false);
        drawer.setResizableOnDrag(true);
        prepararAgenda();
        cargarClases();
    }

    @FXML
    public void clickHamburger(ActionEvent event) {
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/view/FXMLDrawer.fxml"));
            drawer.setSidePane(box);
            drawer.setEffect(new DropShadow());
            drawer.open();
            drawer.setMouseTransparent(false);
        } catch (IOException e) {

        }
    }

    @FXML
    void cerrarDrawer(MouseEvent event) {
        drawer.close();
        drawer.setMouseTransparent(true);
    }

    public void prepararAgenda() {
        JFXCheckBox ch = new JFXCheckBox();
        ch.setSelected(false);
        //lAppointmentGroupMap.put("group" + (i < 10 ? "0" : "") + i, new Agenda.AppointmentGroupImpl().withStyleClass("group" + i))
        for (Agenda.AppointmentGroup lAppointmentGroup : agenda.appointmentGroups()) {
            lAppointmentGroupMap.put(lAppointmentGroup.getDescription(), lAppointmentGroup);
        }
        agenda.setActionCallback(new Callback<Appointment, Void>() {
            @Override
            public Void call(Appointment clase) {
                mostrarDatosClase(clase);
                return null;
            }
        });
        agenda.setEditAppointmentCallback(new Callback<Appointment, Void>() {
            @Override
            public Void call(Appointment clase) {

                return null;
            }
        });
        agenda.allowDraggingProperty().bind(ch.selectedProperty());
        agenda.allowResizeProperty().bind(ch.selectedProperty());
    }

    public void cargarClases() {
        agenda.appointments().clear();
        experiencias = EEDAO.getAllEEs();
        clases = ClaseDAO.getAllClases();
        if (!clases.isEmpty()) {
            int i;
            for (Clase c : clases) {
                i = c.getIdEE();
                c.setAppointmentGroup(lAppointmentGroupMap.get("group" + (i < 10 ? "0" : "") + i));
            }
            agenda.appointments().addAll(clases);
        }
    }

    @FXML
    public void agregarClase(ActionEvent event) {
        fadeOutTransition();
    }

    //TODO: Eliminar y Editar clases
    public void mostrarDatosClase(Appointment clase) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(clase.getSummary()));
        content.setBody();
        System.out.println(clase.getLocation());
        JFXDialog dialog = new JFXDialog(rootPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton aceptar = new JFXButton("ACEPTAR");
        aceptar.setOnAction((ActionEvent e) -> {
            dialog.close();
        });
        content.setActions(aceptar);
        dialog.show();
    }

    public void fadeInTransition() {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(300));
        transition.setNode(rootPane);
        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();
    }

    public void fadeOutTransition() {
        FadeTransition transition = new FadeTransition();
        transition.setDuration(Duration.millis(300));
        transition.setNode(rootPane);
        transition.setFromValue(1);
        transition.setToValue(0);

        transition.setOnFinished((ActionEvent event) -> {
            loadAgregarScene();
        });
        transition.play();
    }

    public void loadAgregarScene(){
        try {
            StackPane agregarView;
            agregarView = FXMLLoader.load(getClass().getResource("/view/AgregarDatos.fxml"));
            Scene newScene = new Scene(agregarView);
            Stage curStage = (Stage) rootPane.getScene().getWindow();
            curStage.setScene(newScene);
            curStage.show();
        } catch (IOException e){
            System.out.println("No se enecontró: " + e);
        }
    }
}

/*
Para fecha:

private LocalDate calcNextFriday(LocalDate d) {
    if (d.getDayOfWeek() >= DateTimeConstants.FRIDAY) {
        d = d.plusWeeks(1);
    }
    return d.withDayOfWeek(DateTimeConstants.FRIDAY);
}
*/
