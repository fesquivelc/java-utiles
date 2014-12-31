/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.utiles;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author fesquivelc
 */
public class PropertiesUtil {
    private static final Logger LOG = Logger.getLogger(PropertiesUtil.class.getName());
    
    public static Properties cargarProperties(String url){
        File fichero = new File(url);
        InputStream is = null;
        Properties properties = new Properties();
        try {
           is = new FileInputStream(fichero);
        } catch (FileNotFoundException ex) {
            java.util.logging.Logger.getLogger(PropertiesUtil.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if(is != null){
            try {
                properties.load(is);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(PropertiesUtil.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return properties;
        
    }
}
