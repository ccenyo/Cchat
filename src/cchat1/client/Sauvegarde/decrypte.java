/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Sauvegarde;

import java.awt.BorderLayout;
import java.awt.FileDialog;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;

/**
 *
 * @author cenyo
 */
public class decrypte extends JFrame
{
    JButton choisir=new JButton("choisir fichier txt");
    FileDialog f;
    JPanel central=new JPanel(new BorderLayout());
    JEditorPane pane=new JEditorPane();
    JScrollPane p=new JScrollPane(pane);
    ObjectInputStream o;
    decrypte d;
   public decrypte()
    {
        this.setLocationRelativeTo(null);
         this.setAlwaysOnTop(true);
         //setResizable(false);
         Image icone = Toolkit.getDefaultToolkit().getImage("image/meet_me.png");
    this.setIconImage(icone);
         d=this;
        setTitle("fn");
        setSize(500,350);
        pane.setEditable(false); 
        pane.setEditorKit(new HTMLEditorKit() ); 
        central.add(choisir,BorderLayout.SOUTH);
        central.add(p,BorderLayout.CENTER);
        choisir.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    f=new FileDialog(d,"choix");
                    f.show();
                    File fichier=new File(f.getDirectory() + "/" + f.getFile());
                    o = new ObjectInputStream(
                                         new BufferedInputStream(
                                                         new FileInputStream(
                                                                         fichier)));
                    pane.setText(o.readObject().toString());
                    o.close();
                } catch (IOException ex) {
                    Logger.getLogger(decrypte.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(decrypte.class.getName()).log(Level.SEVERE, null, ex);
                } 

               
            }
        
        });
        this.setContentPane(central); 
        setVisible(true);
        
    }
}
