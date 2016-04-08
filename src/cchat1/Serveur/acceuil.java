/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

/**
 *
 * @author SODJINOU
 */
import cchat1.Serveur.Serveur;
import cchat1.Client.FenetresPrincipaux.connexion;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Cenyo
 */
public class acceuil extends JFrame{
    private JLabel icon=new JLabel(new ImageIcon("image/meetme.png"));
     private JButton bouton1 = new JButton("Se connecter");
     private JButton bouton2 = new JButton("Héberger");
    private JPanel container = new JPanel();
    private JLabel label = new JLabel("Bienvenue sur l'interface de communication");
    private JLabel label1 = new JLabel("Réalisé par Dayane GBADAMASSI & Cenyo MEDEWOU pour la C.E.B ");

    public acceuil() {
        this.setTitle("ACCUEIL");
                   Image icone = Toolkit.getDefaultToolkit().getImage("image/meet_me.png");
    this.setIconImage(icone);
        this.setSize(500, 200);
         this.setResizable(false); 
        this.setLocationRelativeTo(null);
         
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);            
         
          container.setBackground(Color.getHSBColor(163, 150, 158));
          
            container.setLayout(new BorderLayout());
            
             bouton1.addActionListener(new BoutonListener());             
            bouton2.addActionListener(new Bouton2Listener());
                    
            JPanel south = new JPanel();
            south.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            south.setLayout(null);          
           
             bouton1.setBounds(0,20,150,40);
            bouton2.setBounds(168,70,150,40);
            //  bouton1.setEnabled(false);
           south.add(bouton1);
           south.add(bouton2);
           container.add(south,BorderLayout.CENTER);
                   
            //Définition d'une police d'écriture
            Font police = new Font("Castellar", Font.BOLD, 15 );
            Font police1 = new Font("calibri", Font.BOLD, 16 );
            //On applique celle-ci aux JLabel
            label.setFont(police);
            label1.setFont(police1);
            //On change la couleur de police
            label.setForeground(Color.BLUE);
            south.setBackground(Color.getHSBColor(150, 255, 173));
            label1.setForeground(Color.BLUE);
            //Et on change l'alignement du texte grâce aux attributs static de la classe JLabel
            label.setHorizontalAlignment(JLabel.CENTER);
            label1.setHorizontalAlignment(JLabel.CENTER);
            label.setBounds(20,5,400,90);
            label1.setBounds(20,90,400,90);
            container.add(label,BorderLayout.NORTH);
            container.add(label1,BorderLayout.SOUTH);
            container.add(icon,BorderLayout.EAST);
          //  Panneau pan= new Panneau();
            //container.add(pan);
            this.setContentPane(container);
              
         this.setVisible(true);
        
    }
     public class BoutonListener implements ActionListener{

		public void actionPerformed(ActionEvent arg0) {
           connexion essai = new connexion();
           setVisible(false);
                    
                        }
                }
    
    
    class Bouton2Listener  implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			Heberger h = new Heberger();
                        setVisible(false);
	//System.out.println("Hébergement réussie");	
                    
		}
    }
}

