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
 * Clase controlador de la escena "FXMLExperiencias.fxml" 
 * para mostrar los datos cporrespondientes a las EE almacenadas 
 * en la Base de Datos
 * @author Manolo Perez
 * @since 16/04/2018
 * @version 1.0
 */
public class FXMLExperienciasController implements Initializable {

  // ==================================================================================================================
  // Elementos de la Interfaz FXML
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

  // ==================================================================================================================
  // Elementos de la Interfaz generados por código
  private JFXPopup popup;

  private JFXButton btn_eliminar;

  // ==================================================================================================================
  // Recursos de la Base de Datos
  private List<EE> experiencias;
  private List<Clase> clasesEE;

  // ==================================================================================================================
  // Carga de GUI

  /**
   * Inicialización de la escena, carga de datos y animación
   * @param location URL de inicialización
   * @param resources ResourceBundle de inicialización
   */
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

  /**
   * Inicialización del JFXPopup y llenado con la opción de "Eliminar",
   * la cual se muestra dentro de la lista. Se realiza la asignación del 
   * evento generado por la acción del botón Eliminar
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

  // ==================================================================================================================
  // Gestión de Datos

  /**
   * Se obtienen las EE almacenadas en la Base de Datos y se insertan
   * sus datos en la JFXList
   */
  public void cargarLista() {
    list_EE.getItems().clear();
    experiencias = EEDAO.getAllEEs();
    if (experiencias != null) {
      for (EE c : experiencias) {
        try {
          Label lbl = new Label(c.getNombre());
          lbl.setGraphic(
              new ImageView(new Image(getClass().getResourceAsStream("/resources/ic_school_grey600_18dp.png"))));
          list_EE.getItems().add(lbl);
        } catch (Exception ex) {
          System.err.println("Error: " + ex);
        }
      }
    }
  }

  /**
   * Se elimina una EE y las Clases con las que está
   * compuesta
   */
  public void eliminarEE() {
    int id = experiencias.get(list_EE.getSelectionModel().getSelectedIndex()).getIdEE();
    if (ClaseDAO.eliminarClases(id) && EEDAO.eliminar(id)) {
      showDialog("Eliminada", "Experiencia Educativas y Clases asociadas eliminadas con éxito");
      cargarLista();
    } else {
      showDialog("Error", "No se pudo eliminar la Clase");
    }
    limpiarCampos();
  }

  // ==================================================================================================================
  // Animaciones

  /**
   * Animación de entrada para la escena
   * @param element Elemento para animarlo
   */
  public void fadeInTransition(Parent element) {
    FadeTransition transition = new FadeTransition();
    transition.setDuration(Duration.millis(300));
    transition.setNode(element);
    transition.setFromValue(0);
    transition.setToValue(1);
    transition.play();
  }

  /**
   * Animación de salida para algún componente
   * @param element Parent o heredado que se desee animar
   */
  public void fadeOutTransition(Parent element) {
    FadeTransition transition = new FadeTransition();
    transition.setDuration(Duration.millis(300));
    transition.setNode(element);
    transition.setFromValue(1);
    transition.setToValue(0);
    transition.play();
  }

  // ==================================================================================================================
  // Control de campos

  /**
   * Método para vaciar los datos en los JFXTextFields
   * y ocultar el panel de Días a la semana
   */
  public void limpiarCampos() {
    label_nombreEE.setText("Experiencia Educativa");
    txt_nombre.setText("");
    txt_profesor.setText("");
    fadeOutTransition(panel_diasSemana);
    panel_diasSemana.setVisible(false);
    unselectDiasSemana();
  }

  /**
   * Validación si algún JFXTextField se encuentra vacío
   * @return Confirmación si algún campo está incompleto
   */
  public boolean camposIncompletos() {
    return txt_nombre.getText().isEmpty() || txt_profesor.getText().isEmpty();
  }

  /**
   * Asignación de colores de los Label de panel_DiasSemana, donde se colorean
   * los días donde la EE entregada tiene al menos una Clase
   * @param experiencia EE cuyos días a la semana se desean confirmar
   */
  public void diasSemana(EE experiencia) {
    clasesEE = ClaseDAO.obtenerClasesAsociadas(experiencia.getIdEE());
    for (Clase clase : clasesEE) {
      switch (clase.getDia()) {
      case "Lunes":
        label_Lunes.setTextFill(Color.web("#FFFFFF"));
        label_Lunes
            .setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
        break;
      case "Martes":
        label_Martes.setTextFill(Color.web("#FFFFFF"));
        label_Martes
            .setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
        break;
      case "Miércoles":
        label_Miercoles.setTextFill(Color.web("#FFFFFF"));
        label_Miercoles
            .setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));

        break;
      case "Jueves":
        label_Jueves.setTextFill(Color.web("#FFFFFF"));
        label_Jueves
            .setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
        break;
      case "Viernes":
        label_Viernes.setTextFill(Color.web("#FFFFFF"));
        label_Viernes
            .setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
        break;
      case "Sábado":
        label_Sabado.setTextFill(Color.web("#FFFFFF"));
        label_Sabado
            .setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
        break;
      case "Domingo":
        label_Domingo.setTextFill(Color.web("#FFFFFF"));
        label_Domingo
            .setBackground(new Background(new BackgroundFill(Color.web("#FF4081"), new CornerRadii(50), Insets.EMPTY)));
        break;
      }
    }
  }

  /**
   * Se deseleccionan todos los Lables como parte de limpiar 
   * la pantalla
   */
  public void unselectDiasSemana() {
    label_Lunes.setTextFill(Color.web("#000000"));
    label_Lunes
        .setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
    label_Martes.setTextFill(Color.web("#000000"));
    label_Martes
        .setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
    label_Miercoles.setTextFill(Color.web("#000000"));
    label_Miercoles
        .setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
    label_Jueves.setTextFill(Color.web("#000000"));
    label_Jueves
        .setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
    label_Viernes.setTextFill(Color.web("#000000"));
    label_Viernes
        .setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
    label_Sabado.setTextFill(Color.web("#000000"));
    label_Sabado
        .setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
    label_Domingo.setTextFill(Color.web("#000000"));
    label_Domingo
        .setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"), new CornerRadii(50), Insets.EMPTY)));
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
    aceptar.setOnAction((ActionEvent e) -> {
      dialog.close();
    });
    content.setActions(aceptar);
    dialog.show();
  }

  // ==================================================================================================================
  // Llamadas a GUI externas
  
  /**
   * Se hace la carga de la Escena principal de la vista del Horario
   */
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

  // ==================================================================================================================
  // Eventos FXML

  /**
   * Evento de salida del mouse del campo del Drawer para cerrarlo
   */
  @FXML
  void cerrarDrawer(MouseEvent event) {
    drawer.close();
    drawer.setMouseTransparent(true);
  }

  /**
   * Evento de click en el botón Agregar, que limpia los campos
   * @param event Evento de click en el botón Agregar
   */
  @FXML
  void clickAgregarEE(ActionEvent event) {
    limpiarCampos();
  }

  /**
   * Evento de click en el botón Cancelar, que limpia los campos y cancela el 
   * guardado de una nueva Clase o su modificación
   * @param event Evento de click en el botón Cancelar
   */
  @FXML
  void clickCancelar(ActionEvent event) {
    limpiarCampos();
  }

  /**
   * Evento de click en el botón de Guardar. Se realiza la selección en caso
   * de que se trate de una alta de EE o un campo
   * @param event Evento de click en el botón Guardar
   */
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
        nuevoRegistro = new EE(experiencias.get(list_EE.getSelectionModel().getSelectedIndex()).getIdEE(),
            txt_nombre.getText(), txt_profesor.getText());
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

  /**
   * Evento de click en el botón de despliege del Drawer y asignación de 
   * sus botones
   * @param event Evento de click en el botón Hamburguesa
   */
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

  /**
   * Respuesta al evento de Mouse generado en la JFXListView, en 
   * el que un click derecho muestra el JFXPopup para eliminar, y un click
   * simple muestra los datos de la Experiencia Educativa seleccionado en la lista.
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
}