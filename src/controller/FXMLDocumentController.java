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
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import jfxtras.scene.control.agenda.Agenda;
import jfxtras.scene.control.agenda.Agenda.Appointment;
import model.DAO.ClaseDAO;
import model.DAO.EEDAO;
import model.pojos.Clase;
import model.pojos.EE;

/**
 * Clase controlador de la escena "FXMLHorario.fxml"
 * @author Manolo
 * @since 07/04/2018
 * @version 1.0
 */
public class FXMLDocumentController implements Initializable {

  // ==================================================================================================================
  // Elementos de la Interfaz FXML

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
  // Recursos de la Base de Datos

  private List<EE> experiencias;
  private List<Clase> clases;

  // ==================================================================================================================
  // Recurso Interfaz de Agenda

  private final Map<String, Agenda.AppointmentGroup> mapaDeGrupos = new TreeMap<String, Agenda.AppointmentGroup>();

  // ==================================================================================================================
  // Carga de GUI

  /**
   * Inicialización de la escena, carga de datos y animación
   * @param url URL de inicialización
   * @param rb ResourceBundle de inicialización
   */
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

  /**
   * Configuración inicial de la Agenda
   */
  public void prepararAgenda() {
        for (Agenda.AppointmentGroup grupo : agenda.appointmentGroups()) {
            mapaDeGrupos.put(grupo.getDescription(), grupo);
        }
        agenda.setActionCallback((Appointment clase) -> {
            mostrarDatosClase(clase);
            return null;
        });
        agenda.setEditAppointmentCallback((Appointment clase) -> null);
        agenda.setAllowDragging(false);
        agenda.setAllowResize(false);
    }

  // ==================================================================================================================
  // Gestión de Datos

  /**
   * Se obtienen las Clases y EE almacenadas en la Base de Datos
   * y se asignan a un AppointmentGroup para cuestiones de la Interfaz
   */
  public void cargarClases() {
        agenda.appointments().clear();
        experiencias = EEDAO.getAllEEs();
        clases = ClaseDAO.getAllClases();
        if (!clases.isEmpty()) {
            int i, cont = clases.get(0).getIdEE(), sum = 1;
            for (Clase c : clases) {
                i = c. getIdEE();
                if (cont != i) {
                    cont = c.getIdEE();
                    sum++;
                }
                c.setAppointmentGroup(mapaDeGrupos.get("group" + (sum < 10 ? "0" : "") + sum));
            }
            agenda.appointments().addAll(clases);
        }
    }

  /**
   * Se busca la clase a la que hace referencia un Appointment
   * @param appointment Appointement con datos relacionados a una clase
   * @return Clase encontrada
   */
  public Clase buscarClase(Appointment appointment) {
    for (Clase clase : clases) {
      if (clase.getDescription() == appointment.getDescription() && clase.getStartLocalDateTime() == appointment.getStartLocalDateTime()
          && clase.getLocation() == appointment.getLocation() && clase.getSummary() == appointment.getSummary()
          && clase.getEndLocalDateTime() == appointment.getEndLocalDateTime()) {
        return clase;
      }
    }
    return null;
  }

  /**
   * Se elimina una clase seleccionada
   */
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

  /**
   * Se hace un envío a la escena de AgregarDatos con una 
   * Clase seleccionada
   * @param clase Clase que seleccionada para modificar
   */
  public void modificarClase(Clase clase) {
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

  // ==================================================================================================================
  // Llamadas a GUI externas

  /**
   * Se genera un JFXDialog con la interfaz y los datos de una Clase 
   * vinculada a un Appointment seleccionado en la Agenda
   * @param appointment Appointment seleccionado como respuesta a un click en la Agenda
   */
  public void mostrarDatosClase(Appointment appointment) {
    Clase clase = buscarClase(appointment);
    JFXDialogLayout content = new JFXDialogLayout();
    content.setHeading(new Text(appointment.getSummary()));
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

  /**
   * Se hace la carga de la escena de Agregar Datos
   */
  public void loadAgregarScene() {
    try {
      StackPane agregarView;
      agregarView = FXMLLoader.load(getClass().getResource("/view/AgregarDatos.fxml"));
      Scene newScene = new Scene(agregarView);
      newScene.getStylesheets().add(getClass().getResource("/view/AgendaStyle.css").toExternalForm());
      Stage curStage = (Stage) rootPane.getScene().getWindow();
      curStage.setScene(newScene);
      curStage.show();
    } catch (IOException e) {
      System.out.println("No se enecontró: " + e);
    }
  }

  /**
   * Se hace la carga de la escena de Experiencias Educativas
   */
  public void cargarExperiencias() {
    try {
      StackPane agregarView;
      agregarView = FXMLLoader.load(getClass().getResource("/view/FXMLExperiencias.fxml"));
      Scene newScene = new Scene(agregarView);
      newScene.getStylesheets().add(getClass().getResource("/view/AgendaStyle.css").toExternalForm());
      Stage curStage = (Stage) rootPane.getScene().getWindow();
      curStage.setScene(newScene);
      curStage.show();
    } catch (IOException e) {
      System.out.println("No se enecontró: " + e);
    }
  }

  /**
   * Construcción de un JFXDialog que emite mensajes de texto simples
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

  // ==================================================================================================================
  // Eventos FXML

  /**
   * Evento de click en el botón de despliege del Drawer y asignación de 
   * sus botones
   * @param event Evento de click en el botón Hamburguesa
   */
  @FXML
  public void clickHamburger(ActionEvent event) {
    try {
      VBox box = FXMLLoader.load(getClass().getResource("/view/FXMLDrawer.fxml"));
      for (Node node : box.getChildren()) {
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
          if (node.getAccessibleText() != null) {
            switch (node.getAccessibleText()) {
            case "Horario":
              drawer.close();
              drawer.setMouseTransparent(true);
              break;
            case "Experiencias":
              cargarExperiencias();
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

  /**
   * Evento de salida del mouse del campo del Drawer para cerrarlo
   * @param event Evento de salida del Mouse
   */
  @FXML
    void cerrarDrawer(MouseEvent event) {
        drawer.close();
        drawer.setMouseTransparent(true);
    }

  /**
   * Evento de click en el botón de Agregar Clase que carga la escena
   * @param event Evento de click en el botón Agregar
   */
  @FXML
  public void agregarClase(ActionEvent event) {
    fadeOutTransition();
    loadAgregarScene();
  }

  // ==================================================================================================================
  // Animaciones
  
  /**
   * Animación de entrada para la escena
   */
  public void fadeInTransition() {
    FadeTransition transition = new FadeTransition();
    transition.setDuration(Duration.millis(300));
    transition.setNode(rootPane);
    transition.setFromValue(0);
    transition.setToValue(1);
    transition.play();
  }

  /**
   * Animación de salida para la escena
   */
  public void fadeOutTransition() {
    FadeTransition transition = new FadeTransition();
    transition.setDuration(Duration.millis(300));
    transition.setNode(rootPane);
    transition.setFromValue(1);
    transition.setToValue(0);
    transition.play();
  }

}