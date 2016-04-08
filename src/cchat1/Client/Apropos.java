/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client;

import java.awt.BorderLayout;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author cenyo
 */
public class Apropos extends JFrame{
    JPanel panel=new JPanel(new BorderLayout());
    JEditorPane area=new JEditorPane();
    JScrollPane pane=new JScrollPane(area);
   public Apropos()
    {
         //Définit un titre pour votre fenêtre
        this.setAlwaysOnTop(true);
         this.setTitle("A propos"); 
         Image icone = Toolkit.getDefaultToolkit().getImage("image/meet_me.png");
    this.setIconImage(icone);
         //Définit une taille pour celle-ci ; ici, 400 px de large et 500 px de haut
         this.setSize(300, 200);
                 this.setResizable(false); 
                 area.setEditable(false); 
         //Nous allons maintenant dire à notre objet de se positionner au centre
         this.setLocationRelativeTo(null);
         panel.add(pane);
         area.setEditorKit(new HTMLEditorKit() ); 
         area.setText("Presentation de meet me<br> Ce logiciel vous permet de communiquer au sein de la société<br> pour tout renseignement, contacter <br>- <Strong>Cenyo MEDEWOU</Strong> <br> Numéro:96 72 55 90<br>- <Strong>Dayane GBADAMASSI</Strong> <br> Numéro:96 12 00 49");
         this.setContentPane(panel);
         
             
         this.setVisible(true);
    }
}
