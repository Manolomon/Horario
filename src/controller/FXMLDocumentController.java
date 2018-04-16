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
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.Duration;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
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
        agenda.setAllowDragging(false);
        agenda.setAllowResize(false);
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
        loadAgregarScene();
    }

    public void mostrarDatosClase(Appointment appo) {
        Clase clase = buscarClase(appo); 
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text(appo.getSummary()));
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/FXMLDatosClase.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        DatosClaseController display = loader.getController();
        display.montarDatos(clase);
        VBox p = loader.getRoot();
        content.setBody(p);
        JFXDialog dialog = new JFXDialog(rootPane, content, JFXDialog.DialogTransition.CENTER);
        JFXButton aceptar = new JFXButton("ACEPTAR");
        display.getButtonEliminar().setOnAction((ActionEvent e) -> {
            eliminarClase();
            dialog.close();
        });
        
        display.getButtonEditar().setOnAction((ActionEvent e) -> {
            modificarClase(clase);
        });
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
        transition.play();
    }

    public void loadAgregarScene() {
        try {
            StackPane agregarView;
            agregarView = FXMLLoader.load(getClass().getResource("/view/AgregarDatos.fxml"));
            Scene newScene = new Scene(agregarView);
            Stage curStage = (Stage) rootPane.getScene().getWindow();
            curStage.setScene(newScene);
            curStage.show();
        } catch (IOException e) {
            System.out.println("No se enecontró: " + e);
        }
    }

    public Clase buscarClase(Appointment clase) {
        for (Clase c : clases) {
            if (c.getDescription() == clase.getDescription()
                    && c.getStartLocalDateTime() == clase.getStartLocalDateTime()
                    && c.getLocation() == clase.getLocation() 
                    && c.getSummary() == clase.getSummary()
                    && c.getEndLocalDateTime() == clase.getEndLocalDateTime()) {
                return c;
            }
        }
        return null;
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
        aceptar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                dialog.close();
            }
        });
        content.setActions(aceptar);
        dialog.show();
    }
    
    public void eliminarClase() {
        Clase porEliminar = buscarClase(agenda.selectedAppointments().get(0));
        int idClase = porEliminar.getIdClase();
        if (ClaseDAO.eliminar(idClase)) {
            showDialog("Eliminado", "Contacto eliminado con éxito");
            cargarClases();
        } else {
            showDialog("Error", "No se pudo eliminar el contacto");
        }
    }

    public void modificarClase(Clase clase){
        fadeOutTransition();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/view/AgregarDatos.fxml"));
        try {
            loader.load();
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        AgregarDatosController display = loader.getController();
        display.modificarClase(clase);
        StackPane agregarView = loader.getRoot();
        Scene newScene = new Scene(agregarView);
        Stage curStage = (Stage) rootPane.getScene().getWindow();
        curStage.setScene(newScene);
        curStage.show();
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
