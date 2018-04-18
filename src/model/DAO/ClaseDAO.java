/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.util.ArrayList;
import java.util.List;
import model.MyBatisUtils;
import model.pojos.Clase;
import org.apache.ibatis.session.SqlSession;

/**
 * Objeto de Acceso de Datos de Clase para MySQL
 * @author Manolo
 * @since 09/04/2018
 * @version 1.0
 */
public class ClaseDAO {
    /**
     * Buscador de todos los Clases almacenados
     * @return Lista de los Clases encontrados
     */
    public static List<Clase> getAllClases() {
        List<Clase> lista = new ArrayList<Clase>();
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            lista = conn.selectList("Clase.getAllClases");
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return lista;
    }

    /**
     * Buscador de todas los Clases relacionadas a una EE específica
     * @param idEE El identificador de la EE asociada
     * @return Lista de los Clases encontrados
     */
    public static List<Clase> obtenerClasesAsociadas(int idEE) {
        List<Clase> lista = new ArrayList<Clase>();
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            lista = conn.selectList("Clase.obtenerClasesAsociadas", idEE);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return lista;
    }

    /**
     * Se almacena un nuevo Clase en la Base de Datos
     * @param clase Objeto Clase para registrarlo
     * @return Confirmación si se pudo registrar el Clase con éxito
     */
    public static boolean registrar(Clase clase) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            conn.insert("Clase.registrar", clase);
            conn.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    /**
     * Se actualiza un Clase ya registrado en la Base de Datos
     * @param clase Objeto Clase con un idClase definido
     * @return Confirmación si se pudo actualizar el Clase con éxito
     */
    public static boolean actualizar(Clase clase) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            conn.update("Clase.actualizar", clase);
            conn.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    /**
     * Se elimina un Clase de la Base de Datos a partir de su idClase
     * @param idClase Identificador de un Clase ya registrado en la Base de Datos
     * @return Confirmación si se pudo eliminar la Clase con éxito
     */
    public static boolean eliminar(int idClase) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            conn.delete("Clase.eliminar", idClase);
            conn.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    /**
     * Se eliminan las Clases asociadas a una EE específica
     * @param idEE Identificador de un Clase ya registrado en la Base de Datos
     * @return Confirmación si se pudieron eliminar las Clases con éxito
     */
    public static boolean eliminarClases(int idEE) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            conn.delete("Clase.eliminarClases", idEE);
            conn.commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }
}
