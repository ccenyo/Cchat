/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Sauvegarde;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author Cenyo
 */
public class sauvegardeMessage {
 private   String messages="";
   public sauvegardeMessage(String text)
    {
        ObjectOutputStream oos = null;
        try {
            JFileChooser f=new JFileChooser();
            f.setFileFilter(new filtreTXT("txt","Fichier texte"));
            f.showSaveDialog(null);
            File choi= f.getSelectedFile();
            System.out.println(choi.getAbsolutePath());
            System.out.println(choi.getName());
            oos = new ObjectOutputStream(
                           new BufferedOutputStream(
                                           new FileOutputStream(
                                                           new File(choi.getAbsolutePath()+".txt"))));
            
            oos.writeObject(text);
            oos.close();
        } catch (IOException ex) {
            Logger.getLogger(sauvegardeMessage.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                oos.close();
            } catch (IOException ex) {
                Logger.getLogger(sauvegardeMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    
    }
    
}

