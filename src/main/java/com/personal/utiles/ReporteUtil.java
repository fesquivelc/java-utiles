/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.utiles;

import java.awt.Component;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.JDialog;
import javax.swing.JPanel;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author RyuujiMD
 */
public class ReporteUtil {

    private Connection conn;
    private static final Logger LOG = Logger.getLogger(ReporteUtil.class.getName());
    private static final File insignia = new File("reportes/logo.png");
    private static final String rutaRelativa = new File(".").getAbsolutePath().substring(0, new File(".").getAbsolutePath().length() - 2);

    public Connection getConn() {
        return conn;
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public void verRuta() {
        LOG.info(insignia.getAbsolutePath());
        LOG.info(rutaRelativa);
    }

    public void generarReporte(File reporte, Map parametros, Frame ventana){
        try {
            JDialog visor = new JDialog(ventana, "Reporte", true);
            
            JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
//            System.out.println("INSIGNIA: " + insignia.getAbsolutePath());
            parametros.put("ruta", rutaRelativa);
//            LOG.log(Level.INFO, "La ruta relativa es {0}", rutaRelativa);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            visor.setSize(jasperViewer.getSize());
//        visor.setSize(ventana.getSize().width, ventana.getSize().height);
            visor.getContentPane().add(jasperViewer.getContentPane());
            visor.setLocationRelativeTo(ventana);
            
//            visor.setUndecorated(true);
            visor.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
            visor.setVisible(true);
            visor.setAlwaysOnTop(true);
            
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("ESTO ES MALO2: " + ex.getMessage());
                }
            }
        } catch (JRException ex) {
            Logger.getLogger(ReporteUtil.class.getName()).log(Level.ERROR, null, ex);
        }

    }
    public void generarReporte(List listaObjetos, File reporte, Map parametros, Frame ventana){
        try {            
            JDialog visor = new JDialog(ventana, "Titulo", true);
            JRBeanCollectionDataSource origen = new JRBeanCollectionDataSource(listaObjetos);
            JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
//            System.out.println("INSIGNIA: " + insignia.getAbsolutePath());
            parametros.put("ruta", rutaRelativa);
//            LOG.log(Level.INFO, "La ruta relativa es {0}", rutaRelativa);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, origen);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            visor.setSize(jasperViewer.getSize());
//        visor.setSize(ventana.getSize().width, ventana.getSize().height);
            visor.getContentPane().add(jasperViewer.getContentPane());
            visor.setLocationRelativeTo(ventana);
            
//            visor.setUndecorated(true);
            visor.setBounds(GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds());
            visor.setVisible(true);
            visor.setAlwaysOnTop(true);
        } catch (JRException ex) {
            Logger.getLogger(ReporteUtil.class.getName()).log(Level.ERROR, null, ex);
        }

    }
    
    public Component obtenerReporte(File reporte, Map parametros){
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
            System.out.println("RUTA: " + reporte.getAbsolutePath());
            parametros.put("ruta", rutaRelativa);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("ESTO ES MALO2: " + ex.getMessage());
                }
            }
            
            return jasperViewer.getContentPane();
        } catch (JRException ex) {
            Logger.getLogger(ReporteUtil.class.getName()).log(Level.ERROR, null, ex);
            return null;
        }

    }
    public Component obtenerReporte(List listaObjetos,File reporte, Map parametros){
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
            System.out.println("RUTA: " + reporte.getAbsolutePath());
            parametros.put("ruta", rutaRelativa);
            JRBeanCollectionDataSource origen = new JRBeanCollectionDataSource(listaObjetos);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, origen);
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false);
            
            return jasperViewer.getContentPane();
        } catch (JRException ex) {
            Logger.getLogger(ReporteUtil.class.getName()).log(Level.ERROR, null, ex);
            return null;
        }

    }
}
