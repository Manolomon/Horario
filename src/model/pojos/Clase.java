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

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Time horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(Time horaFin) {
        this.horaFin = horaFin;
    }

    public String getSalon() {
        return salon;
    }

    public void setSalon(String salon) {
        this.salon = salon;
    }

    public String getNota() {
        return nota;
    }

    public void setNota(String nota) {
        this.nota = nota;
    }
    
    public Integer getIdClase() {
        return idClase;
    }

    public void setIdClase(Integer idClase) {
        this.idClase = idClase;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public Integer getIdEE() {
        return idEE;
    }

    public void setIdEE(Integer idEE) {
        this.idEE = idEE;
    }

//    public Clase() {
//        super();
//    }

    public Clase(Integer idEE, String dia, Time horaInicio, Time horaFin, String salon, String nota){
        this.idEE = idEE;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.salon = salon;
        this.nota = nota;
    }
    
    //Actualizar
    public Clase(Integer idClase, Integer idEE, String dia, Time horaInicio, Time horaFin, String salon, String nota){
        this.idClase = idClase;
        this.idEE = idEE;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.salon = salon;
        this.nota = nota;
    }

    /**
     * Constructor para los datos obtenidos de la Base de Datos
     * @param idClase
     * @param idEE
     * @param nombre
     * @param dia
     * @param horaInicio
     * @param horaFin
     * @param salon
     * @param nota     */
    public Clase(Integer idClase, Integer idEE, String nombre, String dia, Time horaInicio, Time horaFin, String salon, String nota) {
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
}
//withStartLocalDateTime(LocalDateTime.of(LocalDate.of(2018, Month.APRIL, 9), LocalTime.of(10, 00)))
//                .withEndLocalDateTime(LocalDateTime.of(LocalDate.of(2018, Month.APRIL, 9), LocalTime.of(12, 00)))
//                .withSummary("Principios de Construcción de Software")
//                .withDescription("Santa Claus")
//                .withAppointmentGroup(lAppointmentGroupMap.get("group04"));;