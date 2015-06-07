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
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import javax.swing.JDialog;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvMetadataExporter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.export.AbstractXlsExporterConfiguration;
import net.sf.jasperreports.export.Exporter;
import net.sf.jasperreports.export.SimpleCsvMetadataReportConfiguration;
import net.sf.jasperreports.export.SimpleCsvReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsExporterConfiguration;
import net.sf.jasperreports.export.SimpleXlsReportConfiguration;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import net.sf.jasperreports.export.XlsExporterConfiguration;
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

    public static enum Formato {

        XLS, DBF, CSV, PDF, XLSX
    };

    public void exportarReporte(File reporteFichero, Map parametros, Formato formato, String rutaDestino) {
        try {
            if(formato != Formato.PDF){
                parametros.put(JRParameter.IS_IGNORE_PAGINATION, true);
            }
            JasperPrint reporte = this.getReporte(reporteFichero, parametros);
            Exporter exporter = null;
            String extension;
            switch (formato) {
                case XLSX:
                    exporter = new JRXlsxExporter();
                    SimpleXlsxReportConfiguration xlsxConfig = new SimpleXlsxReportConfiguration();
                    xlsxConfig.setDetectCellType(true);
                    xlsxConfig.setOnePagePerSheet(false);
                    xlsxConfig.setCollapseRowSpan(true);
                    exporter.setConfiguration(xlsxConfig);
                    extension = ".xlsx";
                    break;
                case XLS:
                    exporter = new JRXlsExporter();
                    SimpleXlsReportConfiguration xlsConfig = new SimpleXlsReportConfiguration();
                    xlsConfig.setDetectCellType(true);
                    xlsConfig.setOnePagePerSheet(false);
                    xlsConfig.setCollapseRowSpan(true);
                    exporter.setConfiguration(xlsConfig);
                    extension = ".xls";
                    break;
                case CSV:
                    exporter = new JRCsvExporter();
                    extension = ".csv";
                    break;
                case PDF: default:
                    exporter = new JRPdfExporter();
                    extension = ".pdf";
//                    SimplePdfReportConfiguration pdfConfig = new SimplePdfReportConfiguration();
                    break;
                    
            }
//            OutputStream os = new FileOutputStream(rutaDestino);
            System.out.println("RUTA DESTINO ANTES DE REEMPLAZAR: "+rutaDestino);
            String extensionInicial = FormularioUtil.getExtension(rutaDestino);
            if(extensionInicial != null){
                rutaDestino = rutaDestino.replaceAll("."+FormularioUtil.getExtension(rutaDestino),"");
            }            
            rutaDestino = rutaDestino + extension;
            System.out.println("RUTA DESTINO: "+rutaDestino);
            exporter.setExporterInput(new SimpleExporterInput(reporte));
            exporter.setExporterOutput(formato != Formato.CSV ? new SimpleOutputStreamExporterOutput(rutaDestino) : new SimpleWriterExporterOutput(rutaDestino));            
            exporter.exportReport();            
        } catch (JRException ex) {
            java.util.logging.Logger.getLogger(ReporteUtil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        
    }

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

    public JasperPrint getReporte(File reporte, Map parametros) throws JRException{
        try {
            JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
//            System.out.println("INSIGNIA: " + insignia.getAbsolutePath());
            parametros.put("ruta", rutaRelativa);
//            LOG.log(Level.INFO, "La ruta relativa es {0}", rutaRelativa);
            JasperPrint jasperPrint = JasperFillManager.fillReport(report, parametros, conn);
            
            return jasperPrint;
        } catch (JRException ex) {
            java.util.logging.Logger.getLogger(ReporteUtil.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            throw ex;
        }
    }

    public void generarReporte(File reporte, Map parametros, Frame ventana) {
        try {
            JDialog visor = new JDialog(ventana, "Reporte", true);

//            JasperReport report = (JasperReport) JRLoader.loadObject(reporte);
//            System.out.println("INSIGNIA: " + insignia.getAbsolutePath());
//            parametros.put("ruta", rutaRelativa);
//            LOG.log(Level.INFO, "La ruta relativa es {0}", rutaRelativa);
            JasperPrint jasperPrint = this.getReporte(reporte, parametros);
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

    public Component obtenerReporte(File reporte, Map parametros) {
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
}
