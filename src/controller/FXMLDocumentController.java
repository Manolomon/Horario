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
import java.net.URL;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Callback;
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
    private StackPane stackDialogPane;
    
    @FXML
    private Agenda agenda;

    @FXML
    private JFXButton btn_agregar;

    private List<EE> experiencias;
    private List<Clase> clases;
    private final Map<String, Agenda.AppointmentGroup> lAppointmentGroupMap = new TreeMap<String, Agenda.AppointmentGroup>();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXCheckBox ch = new JFXCheckBox();
        ch.setSelected(false);
        
        //lAppointmentGroupMap.put("group" + (i < 10 ? "0" : "") + i, new Agenda.AppointmentGroupImpl().withStyleClass("group" + i))
        for (Agenda.AppointmentGroup lAppointmentGroup : agenda.appointmentGroups()) {
            lAppointmentGroupMap.put(lAppointmentGroup.getDescription(), lAppointmentGroup);
        }
        cargar();
        //TODO: Agregar vista del evento
        agenda.setActionCallback(new Callback<Appointment, Void>() {
            @Override
            public Void call(Appointment clase) {
                System.out.println(clase.getLocation());
                System.out.println(clase.getAppointmentGroup());
                return null;
            }
        });
        agenda.allowDraggingProperty().bind(ch.selectedProperty());
        agenda.allowResizeProperty().bind(ch.selectedProperty());
        
        //Restringir Click Derecho
        agenda.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) {
                ev.consume();
            }
        });
    }

    public void cargar() {
        experiencias = EEDAO.getAllEEs();
        clases = ClaseDAO.getAllClases();
        int i;
        for (Clase c : clases) {
            i = c.getIdEE();
            System.out.println(i);
            c.setAppointmentGroup(lAppointmentGroupMap.get("group" + (i < 10 ? "0" : "") + i));
        }
        agenda.appointments().addAll(clases);
    }
    @FXML
    void agregarClase(ActionEvent event) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Agregar Contacto"));
        content.setBody(new Text("Holu"));
//        GridPane body = FXMLLoader.load(getClass().getResource("/view/FXMLAgregar.fxml"));
//        content.setBody(body);
        JFXDialog dialog = new JFXDialog(stackDialogPane, content, JFXDialog.DialogTransition.CENTER);
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
