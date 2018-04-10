package model.pojos;

/**
 * Clase principal para modelar las Experiencias Educativas con al Base de Datos
 * @author Manolo
 * @since 04/09/2018
 * @version 1.0
 */
public class EE {
    private Integer idEE;
    private String nombre;
    private String profesor;

    public EE() {
    }

    public EE(Integer idEE, String nombre, String profesor) {
        this.idEE = idEE;
        this.nombre = nombre;
        this.profesor = profesor;
    }

    public Integer getIdEE() {
        return idEE;
    }

    public void setIdEE(Integer idEE) {
        this.idEE = idEE;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getProfesor() {
        return profesor;
    }

    public void setProfesor(String profesor) {
        this.profesor = profesor;
    }
    
}