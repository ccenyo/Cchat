/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author cenyo
 */
public class Salon extends JFrame{
    JPanel pane=new JPanel(new BorderLayout());
    JLabel label1=new JLabel("Nom du Groupe"); 
    JLabel label2=new JLabel("Type de Groupe");
    JRadioButton radio1=new JRadioButton("Public");
    JRadioButton radio2=new JRadioButton("Privée");
    ButtonGroup groupe=new ButtonGroup();
    JButton bouton=new JButton("Créer");
    JTextField text=new JTextField();
    JPanel nord=new JPanel(new GridLayout(1,2));
    JPanel centre=new JPanel(new GridLayout(3,1));
    MaFenetre parent;
    public JPanel sud=new JPanel(new GridLayout(3,2)); 
    public JLabel label3=new JLabel("Mot de passe");
    public JLabel label4=new JLabel("Confirmer mot de passe");
    public JTextField pw1=new JTextField(); 
    public JTextField pw2=new JTextField(); 
    public String motDePasse="";
   public  Salon(MaFenetre f)
    {
        parent=f;
        this.setTitle("Creer un Groupe");
         //Définit une taille pour celle-ci ; ici, 400 px de large et 500 px de haut
         Image icone = Toolkit.getDefaultToolkit().getImage("image/meet_me.png");
    this.setIconImage(icone);
         this.setSize(300, 200);
        groupe.add(radio1);
        groupe.add(radio2);
        this.setResizable(false); 
        nord.add(label1);
        nord.add(text);
        pane.add(nord,BorderLayout.NORTH);
        centre.add(label2);
        centre.add(radio1);
        centre.add(radio2);
        radio1.setSelected(true); 
        pane.add(centre,BorderLayout.CENTER);
        sud.add(label3);
        sud.add(pw1);
        sud.add(label4);
        sud.add(pw2);
        this.setAlwaysOnTop(true);
        pane.add(bouton,BorderLayout.SOUTH);
         //Nous allons maintenant dire à notre objet de se positionner au centre
         this.setLocationRelativeTo(null);
         //Ferme-toi lorsqu'on clique sur "Fermer" !
       //  this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         radio2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               pane.remove(bouton); 
               sud.add(bouton);
               pane.add(sud,BorderLayout.SOUTH);
                setSize(300, 250);
               sud.repaint();
               pane.repaint();
            }
        }); 
          radio1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sud.remove(bouton);
               pane.remove(sud); 
               
               pane.add(bouton,BorderLayout.SOUTH);
               setSize(300, 200);
               pane.repaint();
            }
        });
         bouton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               if(!text.getText().equals(""))
               {
                   if(radio1.isSelected())
                   {
                       parent.outp.println("<~>"+text.getText()+"|"+parent.pseud);
                       parent.outp.flush();
                       setVisible(false);
                   }
                   if(radio2.isSelected())
                   {
                       if(pw1.getText()!="" && pw2.getText()!="" && pw1.getText().equals(pw2.getText()))
                       {
                           motDePasse=pw1.getText();
                           parent.outp.println("<*>"+text.getText()+"|"+parent.pseud+"~"+motDePasse);
                           parent.outp.flush();
                           
                            setVisible(false);
                       }
                   }
               }
            }
        }); 
          this.setContentPane(pane);    
         this.setVisible(true);
    }
    
}
