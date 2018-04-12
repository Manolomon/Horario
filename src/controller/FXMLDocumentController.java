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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
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
    private AnchorPane AgendaPane;

    @FXML
    private Agenda agenda;

    @FXML
    private JFXButton btn_agregar;

    @FXML
    private JFXButton hamburger;

    @FXML
    private JFXDrawer drawer;

    private List<EE> experiencias;
    private List<Clase> clases;
    private final Map<String, Agenda.AppointmentGroup> lAppointmentGroupMap = new TreeMap<String, Agenda.AppointmentGroup>();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
            //drawer.hidden();
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
                mostrarDatosClase();
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
                System.out.println(i);
                c.setAppointmentGroup(lAppointmentGroupMap.get("group" + (i < 10 ? "0" : "") + i));
            }
            agenda.appointments().addAll(clases);
        }
    }

    @FXML
    public void agregarClase(ActionEvent event) {
        //datos();

    }

    public void mostrarDatosClase() {
        JFXDialogLayout content = new JFXDialogLayout();
        try {
            VBox box = FXMLLoader.load(getClass().getResource("/view/FXMLDatosClase.fxml"));
            content.setHeading(new Text("Nombre de Clase"));
            content.setBody(box);
        } catch (IOException e) {
            System.out.println("No se encontró la GUI");
        }
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

    public void datos() {

    }
}

/*
* final Label response = new Label();
    final ImageView imageView = new ImageView(
      new Image("http://icons.iconarchive.com/icons/eponas-deeway/colobrush/128/heart-2-icon.png")
    );
    final Button button = new Button("I love you", imageView);
/*
Para fecha:

private LocalDate calcNextFriday(LocalDate d) {
    if (d.getDayOfWeek() >= DateTimeConstants.FRIDAY) {
        d = d.plusWeeks(1);
    }
    return d.withDayOfWeek(DateTimeConstants.FRIDAY);
}
*/
