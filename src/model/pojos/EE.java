package model.pojos;

/**
 * Clase principal para modelar las Experiencias Educativas con al Base de Datos
 * @author Manolo
 * @since 09/04/2018
 * @version 1.0
 */
public class EE {
  private Integer idEE;
  private String nombre;
  private String profesor;
/**
 * Constructor por defecto
 */
  public EE() {
  }

  /**
   * Constructor para las nuevas EE generadas
   * @param nombre Nombre de la EE
   * @param profesor Nombre del profesor de la EE
   */
  public EE(String nombre, String profesor) {
    this.nombre = nombre;
    this.profesor = profesor;
  }

  /**
   * Constructor para las EE obtenidos de la Base de Datos
   * @param idEE Identificador de la EE
   * @param nombre Nombre de la EE
   * @param profesor Nombre del profesor de la EE
   */
  public EE(Integer idEE, String nombre, String profesor) {
    this.idEE = idEE;
    this.nombre = nombre;
    this.profesor = profesor;
  }

  /**
   * Getter del idEE
   * @return Identificador de la EE
   */
  public Integer getIdEE() {
    return idEE;
  }

  /**
   * Setter del idEE
   * @param idEE Identificador de la EE
   */
  public void setIdEE(Integer idEE) {
    this.idEE = idEE;
  }

  /**
   * Getter del nombre de la EE
   * @return Nombre de la EE 
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * Setter del nombre de la EE
   * @param nombre Nombre de la EE 
   */
  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * Getter del nombre del profesor
   * @return Nombre del profesor de la EE
   */
  public String getProfesor() {
    return profesor;
  }

  /**
   * Setter del nombre del profesor
   * @param profesor Nombre del profesor de la EE
   */
  public void setProfesor(String profesor) {
    this.profesor = profesor;
  }

}