/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.DAO;

import java.util.ArrayList;
import java.util.List;
import model.MyBatisUtils;
import model.pojos.EE;
import org.apache.ibatis.session.SqlSession;

/**
 * Objeto de Acceso de Datos de EEs para MySQL
 * @author Manolo
 * @since 04/09/2018
 * @version 1.0
 */
public class EEDAO {
    /**
     * Buscador de todos los EEs almacenados
     * @return Lista de los EEs encontrados
     */
    public static List<EE> getAllEEs() {
        List<EE> lista = new ArrayList<EE>();
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            lista = conn.selectList("EE.obtener");
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
     * Buscador de EE con un id en específico
     * @return Lista de los EEs encontrados
     */
    public static List<EE> obtenerEE(int idEE) {
        List<EE> lista = new ArrayList<EE>();
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            lista = conn.selectList("EE.obtenerEE", idEE);
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
     * Se almacena un nuevo EE en la Base de Datos
     * @param EE Objeto EE para registrarlo
     * @return Confirmación si se pudo registrar el EE con éxito
     */
    public static boolean registrar(EE ee) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            conn.insert("EE.registrar", ee);
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
     * Se actualiza un EE ya registrado en la Base de Datos
     * @param EE Objeto EE con un idEE definido
     * @return Confirmación si se pudo actualizar el EE con éxito
     */
    public static boolean actualizar(EE ee) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            conn.update("EE.actualizar", ee);
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
     * Se elimina un EE de la Base de Datos a partir de su idEE
     * @param idEE Identificador de un EE ya registrado en la Base de Datos
     * @return Confirmación si se pudo eliminar el EE con éxito
     */
    public static boolean eliminar(int idEE) {
        SqlSession conn = null;
        try {
            conn = MyBatisUtils.getSession();
            conn.delete("EE.eliminar", idEE);
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
