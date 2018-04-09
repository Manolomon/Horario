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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        JFXCheckBox ch = new JFXCheckBox();
        ch.setSelected(false);
        final Map<String, Agenda.AppointmentGroup> lAppointmentGroupMap = new TreeMap<String, Agenda.AppointmentGroup>();
        for (Agenda.AppointmentGroup lAppointmentGroup : agenda.appointmentGroups()) {
            lAppointmentGroupMap.put(lAppointmentGroup.getDescription(), lAppointmentGroup);
        }
        LocalDate lTodayLocalDate = LocalDate.now();
        Appointment mate = new Agenda.AppointmentImplLocal()
                .withStartLocalDateTime(LocalDateTime.of(LocalDate.of(2018, Month.APRIL, 3), LocalTime.of(8, 00)))
                .withEndLocalDateTime(LocalDateTime.of(LocalDate.of(2018, Month.APRIL, 3), LocalTime.of(11, 30)))
                .withSummary("Matemáticas")
                .withDescription("Santa Claus")
                .withAppointmentGroup(lAppointmentGroupMap.get("group02"));
        mate.setLocation("Mi cora");
        agenda.appointments().addAll(mate);
        
        //TODO: Agregar vista del evento
        agenda.setActionCallback( (appointment) -> {
            System.out.println(appointment.getLocation());
            System.out.println(appointment.getAppointmentGroup());
            appointment.getAppointmentGroup().setStyleClass("/view/AgendaStyle.css");
            return null;
        });
        agenda.allowDraggingProperty().bind(ch.selectedProperty());
        agenda.allowResizeProperty().bind(ch.selectedProperty());
        agenda.addEventFilter(MouseEvent.MOUSE_PRESSED, ev -> {
            if (ev.getButton() == MouseButton.SECONDARY) {
                ev.consume();
            }
        });
    }

    @FXML
    void agregarClase(ActionEvent event) {
        JFXDialogLayout content = new JFXDialogLayout();
        content.setHeading(new Text("Agregar Contacto"));
        //content.setBody(new Text("Holu"));
        GridPane body = FXMLLoader.load(getClass().getResource("/view/FXMLAgregar.fxml"));
        content.setBody(body);
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
