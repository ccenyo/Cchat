/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1;

import cchat1.Serveur.acceuil;
import javax.swing.JOptionPane;

/**
 *
 * @author Cenyo
 */
public class Cchat1 {

    /**
     * @param args the command line arguments
     */
   
    public static void main(String[] args) {
         acceuil ac = new acceuil();
         
    }
     public static boolean isOneInstance (String[] args)
    {
        JOptionPane.showMessageDialog(null, "Une fenetre de Meet Me est déja ouverte", "Erreur", JOptionPane.ERROR_MESSAGE);
        return true;
    }
}
