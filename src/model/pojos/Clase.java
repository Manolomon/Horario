/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.pojos;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import jfxtras.scene.control.agenda.Agenda.AppointmentImplLocal;

/**
 * Clase principal para modelar las Clases con al Base de Datos
 * @author Manolo
 * @since 04/09/2018
 * @version 1.0
 */
public class Clase extends AppointmentImplLocal {
    private Integer idClase;
    private String nombre;
    private Integer idEE;
    private String dia;
    private Time horaInicio;
    private Time horaFin;
    private String salon;
    private String nota;
    
    /**
     * Constructor para las nuevas Clases generadas
     * @param idEE Identificador de la EE asociada a la Clase
     * @param dia dia de la Clase
     * @param horaInicio Hora de Inicio de la Clase
     * @param horaFin Hora de Fin de la Clase
     * @param salon Salón de la Clase
     * @param nota Nota de la Clase
     */
    public Clase(Integer idEE, String dia, Time horaInicio, Time horaFin, String salon, String nota) {
        this.idEE = idEE;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.salon = salon;
        this.nota = nota;
    }
    
    /**
     * Constructor para una Clase generada para Actualizar la Base de Datos
     * @param idClase Identificador de la Clase
     * @param idEE Identificador de la EE asociada a la Clase
     * @param dia dia de la Clase
     * @param horaInicio Hora de Inicio de la Clase
     * @param horaFin Hora de Fin de la Clase
     * @param salon Salón de la Clase
     * @param nota Nota de la Clase
     */
    public Clase(Integer idClase, Integer idEE, String dia, Time horaInicio, Time horaFin, String salon, String nota) {
        this.idClase = idClase;
        this.idEE = idEE;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.salon = salon;
        this.nota = nota;
    }

    /**
     * Constructor para las Clases obtenidas de la Base de Datos, que 
     * es directamente asociado como AppointmentImplLocal por su herencia
     * @param idClase Identificador de la Clase
     * @param idEE Identificador de la EE asociada a la Clase
     * @param nombre Nombre de la Clase
     * @param dia dia de la Clase
     * @param horaInicio Hora de Inicio de la Clase
     * @param horaFin Hora de Fin de la Clase
     * @param salon Salón de la Clase
     * @param nota Nota de la Clase
     */
    public Clase(Integer idClase, Integer idEE, String nombre, String dia, Time horaInicio, Time horaFin, String salon,
            String nota) {
        super();
        int cant = 0;
        switch (dia) {
        case "Lunes":
            cant = 0;
            break;
        case "Martes":
            cant = 1;
            break;
        case "Miércoles":
            cant = 2;
            break;
        case "Jueves":
            cant = 3;
            break;
        case "Viernes":
            cant = 4;
            break;
        case "Sábado":
            cant = 5;
            break;
        case "Domingo":
            cant = 6;
            break;
        }
        LocalDate thisWeekLunes = LocalDate.now().with(DayOfWeek.MONDAY).plusDays(cant);
        this.idEE = idEE;
        this.setStartLocalDateTime(LocalDateTime.of(thisWeekLunes, horaInicio.toLocalTime()));
        this.setEndLocalDateTime(LocalDateTime.of(thisWeekLunes, horaFin.toLocalTime()));
        this.setLocation(salon);
        this.setSummary(nombre);
        this.setDescription(nota);
    }

    /**
     * Getter del idClase
     * @return Identificador de la Clase 
     */
    public Integer getIdClase() {
        return idClase;
    }

    /**
     * Setter del idClase
     * @param idClase Identifiacdor de la Clase 
     */
    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    /**
     * Getter del idClase
     * @return Nombre de la Clase 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Setter del idClase
     * @param nombre Nombre de la Clase 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Getter del idEE
     * @return Identificador de la EE asociada a la Clase 
     */
    public Integer getIdEE() {
        return idEE;
    }

    /**
     * Setter del idEE
     * @param idEE Identificador de la EE asociada a la Clase 
     */
    public void setIdEE(Integer idEE) {
        this.idEE = idEE;
    }

    /**
     * Getter del dia
     * @return Dia de la Clase 
     */
    public String getDia() {
        return dia;
    }

    /**
     * Setter del dia
     * @param dia Dia de la Clase 
     */
    public void setDia(String dia) {
        this.dia = dia;
    }

    /**
     * Getter de la horaInicio
     * @return Hora de Inicio de la Clase 
     */
    public Time getHoraInicio() {
        return horaInicio;
    }

    /**
     * Setter de la horaInicio
     * @param horaInicio Hora de Inicio de la Clase 
     */
    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * Getter de la horaFin
     * @return Hora de Fin de la Clase 
     */
    public Time getHoraFin() {
        return horaFin;
    }

    /**
     * Setter de la horaFin
     * @param horaFin Hora de Fin de la Clase 
     */
    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    /**
     * Getter del salón
     * @return Salón de la Clase 
     */
    public String getSalon() {
        return salon;
    }

    /**
     * Setter del salón
     * @param salon Salón de la Clase 
     */
    public void setSalon(String salon) {
        this.salon = salon;
    }

    /**
     * Getter de la nota
     * @return Nota de la Clase 
     */
    public String getNota() {
        return nota;
    }

    /**
     * Setter de la nota
     * @param nota Nota de la Clase 
     */
    public void setNota(String nota) {
        this.nota = nota;
    }
    
}