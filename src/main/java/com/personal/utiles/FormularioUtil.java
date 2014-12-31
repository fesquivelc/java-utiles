/*
 UTILES DE FRANCIS XOxOXo
 */
package com.personal.utiles;

import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JTextFieldDateEditor;
import java.awt.Component;
import java.awt.Image;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.text.DateFormatter;
import javax.swing.text.JTextComponent;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import sun.awt.image.ByteArrayImageSource;
import sun.awt.image.ToolkitImage;

/**
 *
 * @author RyuujiMD
 */
public class FormularioUtil {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";

    public final static int NUEVO = 1;
    public final static int MODIFICAR = 2;
    public final static int ELIMINAR = 3;

    public static boolean dialogoConfirmar(Component parent, int opcion) {
        String mensaje = "";
        String titulo = "Mensaje del sistema";
        int tipoMensaje = 0;

        switch (opcion) {
            case NUEVO:
                mensaje = "¿Desea guardar los datos?";
                tipoMensaje = JOptionPane.QUESTION_MESSAGE;
                break;
            case MODIFICAR:
                mensaje = "¿Desea guardar los cambios realizados?";
                tipoMensaje = JOptionPane.QUESTION_MESSAGE;
                break;
            case ELIMINAR:
                mensaje = "¿Desea eliminar el elemento seleccionado?";
                tipoMensaje = JOptionPane.WARNING_MESSAGE;
                break;
        }

        return JOptionPane.showConfirmDialog(parent, mensaje, titulo, JOptionPane.YES_NO_OPTION, tipoMensaje) == JOptionPane.YES_OPTION;
    }

    public static void mensajeExito(Component parent, int opcion) {
        String mensaje = "";
        String titulo = "Mensaje del sistema";
        int tipoMensaje = JOptionPane.INFORMATION_MESSAGE;

        switch (opcion) {
            case NUEVO:
                mensaje = "Los datos se guardaron correctamente";
                break;
            case MODIFICAR:
                mensaje = "Los datos fueron actualizados correctamente";
                break;
            case ELIMINAR:
                mensaje = "Se eliminó el elemento seleccionado";
                break;
        }

        JOptionPane.showMessageDialog(parent, mensaje, titulo, tipoMensaje);
    }

    public static void mensajeError(Component parent, int opcion) {
        String mensaje = "";
        String titulo = "Mensaje del sistema";
        int tipoMensaje = JOptionPane.ERROR_MESSAGE;

        switch (opcion) {
            case NUEVO:
                mensaje = "Hubo un error al guardar los datos";
                break;
            case MODIFICAR:
                mensaje = "Hubo un error al modificar los datos";
                break;
            case ELIMINAR:
                mensaje = "Hubo un error al eliminar los datos";
                break;
        }

        JOptionPane.showMessageDialog(parent, mensaje, titulo, tipoMensaje);
    }

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }
    private static JFileChooser chooser;

    public static String chooserImagen(Component component) {
        if (chooser == null) {
            chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
        }

        chooser.setDialogTitle("Seleccione una imagen");
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        //    
        if (chooser.showOpenDialog(JOptionPane.getFrameForComponent(component)) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
            chooser.setCurrentDirectory(chooser.getSelectedFile());
            return chooser.getSelectedFile().getAbsolutePath();
        } else {
            System.out.println("No Selection ");
            return "";
        }

    }
    private static final Logger LOG = Logger.getLogger(FormularioUtil.class.getName());

    public static String guardarImagen(String origen, String directorioImg) {
        File dirImagenes = new File(directorioImg);
        if (dirImagenes.exists()) {
            LOG.info("--- EXISTE LA CARPETA DE LA IMAGEN---");
        } else {
            LOG.warn("--- NO EXISTE LA CARPETA DE LA IMAGEN---");
            dirImagenes.mkdir();
        }
        String imagen = "/img-" + dirImagenes.listFiles().length;
        Path pOrigen = Paths.get(origen);
        Path pDestino = Paths.get(dirImagenes.getAbsolutePath() + imagen);

        CopyOption[] opciones = new CopyOption[]{StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.COPY_ATTRIBUTES};

        try {
            Files.copy(pOrigen, pDestino, opciones);
        } catch (IOException ex) {
            Logger.getLogger(FormularioUtil.class.getName()).log(Level.ERROR, null, ex);
        }

        String extension = getExtension(pOrigen.toFile());
        return directorioImg + imagen + extension;
    }

    public static void activarComponente(Component component, boolean editable) {
        if (component instanceof JDateChooser) {
            JDateChooser dc = (JDateChooser) component;
            activarComponente(dc.getCalendarButton(), editable);
            ((JTextFieldDateEditor) dc.getDateEditor()).setEditable(false);
        } else if (component instanceof JTextComponent) {
            ((JTextComponent) component).setEditable(editable);
        } else if (component instanceof AbstractButton) {
            component.setEnabled(editable);
        } else if (component instanceof JTable) {
            ((JTable) component).setEnabled(editable);
        }else if(component instanceof JComboBox){
            ((JComboBox)component).setEnabled(editable);
        } else if (component instanceof JComponent) {
            for (Component c : ((JComponent) component).getComponents()) {
                activarComponente(c, editable);
            }
        } else {
            component.setEnabled(editable);
        }
    }

    public static void limpiarComponente(Component component) {
        if (component instanceof JTextArea) {
            JTextArea texto = (JTextArea) component;
            texto.setText("");
        } else if (component instanceof JComboBox) {
            ((JComboBox) component).setSelectedIndex(0);
        } else if (component instanceof JTextComponent) {
            ((JTextComponent) component).setText("");
        } else if (component instanceof JDateChooser) {
            ((JDateChooser) component).setDate(null);
        } else if (component instanceof JComponent) {
            for (Component c : ((JComponent) component).getComponents()) {
                limpiarComponente(c);
            }
        }

    }

    public static void convertirMayusculas(Component component) {
        if (component instanceof JTextArea) {
            JTextArea texto = (JTextArea) component;
            texto.setText(texto.getText().toUpperCase());
        } else if (component instanceof JTextComponent) {
            JTextComponent textoComponent = (JTextComponent) component;
            textoComponent.setText(textoComponent.getText().toUpperCase().trim());
        } else if (component instanceof JComponent) {
            for (Component c : ((JComponent) component).getComponents()) {
                convertirMayusculas(c);
            }
        }
    }

    public static void imagenALabel(byte[] imgByte, JLabel label) {
        Image img = new ToolkitImage(new ByteArrayImageSource(imgByte));
        Icon icono = new ImageIcon(img.getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
        label.setIcon(icono);
    }

    public static void imagenALabel(String imgRuta, JLabel label) {
        File fichero = new File(imgRuta);
        ImageIcon img = new ImageIcon(fichero.getAbsolutePath());
        Icon icono = new ImageIcon(img.getImage().getScaledInstance(label.getWidth(), label.getHeight(), Image.SCALE_DEFAULT));
        label.setIcon(icono);
    }

    public static byte[] imagenABytes(String ruta) {
        InputStream is = null;
        try {
            File file = new File(ruta);
            is = new FileInputStream(file);
            byte[] buffer = new byte[(int) file.length()];
            int readers = is.read(buffer);
            return buffer;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FormularioUtil.class.getName()).log(Level.ERROR, null, ex);
            return null;
        } catch (IOException ex) {
            Logger.getLogger(FormularioUtil.class.getName()).log(Level.ERROR, null, ex);
            return null;
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FormularioUtil.class.getName()).log(Level.ERROR, null, ex);
            }
        }

    }

    public static void modeloSpinnerFechaHora(JSpinner spinner, String patron) {
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinner, patron);
        DateFormatter formatter = (DateFormatter) editor.getTextField().getFormatter();
        formatter.setAllowsInvalid(false); // this makes what you want
        formatter.setOverwriteMode(true);
        spinner.setEditor(editor);
    }
}
