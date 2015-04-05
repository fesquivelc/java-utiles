/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author fesquivelc
 */
public class PropertiesUtil {

    private static final Logger LOG = Logger.getLogger(PropertiesUtil.class.getName());

    public static Properties cargarProperties(String url) {
        File fichero = new File(url);
        Properties properties = new Properties();
        try(InputStream is = new FileInputStream(fichero)) {
            properties.load(is);
        } catch (FileNotFoundException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
        return properties;

    }
    public static Properties cargarProperties(InputStream is) {
        Properties properties = new Properties();
        try(InputStream fichero = is) {
            properties.load(fichero);
        } catch (FileNotFoundException ex) {
            LOG.error(ex);
        } catch (IOException ex) {
            LOG.error(ex);
        }
        return properties;
    }

    public static void guardarProperties(Properties fichero, String url) {
        try(OutputStream out = new FileOutputStream(url)){
            fichero.store(out,"FICHERO DE CONFIGURACIÓN");
        } catch (Exception e) {
            LOG.error(e);
        }
    }
}
