/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import javax.swing.*;

/**
 *
 * @author Dayane
 */
public class Heberger extends JFrame {

    public JPanel pane=new JPanel();
    public JLabel nom=new JLabel("Nom du Serveur :");
    public JLabel com=new JLabel("Commentaire");
    public JTextField nomServ=new JTextField();
    public JTextField commentaire=new JTextField();
    public JButton valide=new JButton("Heberger");
    public fn fenetre;
    public String info="";
    public String sanCom="Aucun Commentaire";
    
    public Heberger(){
        fenetre=new fn(this);
        this.setTitle("HEBERGER");
        this.setSize(400, 250);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         Image icone = Toolkit.getDefaultToolkit().getImage("image/meet_me.png");
    this.setIconImage(icone);
       
        pane.setLayout(null);
        nom.setBounds(5, 5, 100, 15);
        com.setBounds(5, 40, 100, 15);
        nomServ.setBounds(110, 5, 200, 25);
        commentaire.setBounds(110, 40, 270, 120);
        valide.setBounds(280, 180, 100, 30);
        pane.add(nom);
        pane.add(com);
        pane.add(nomServ);
        pane.add(commentaire);
        pane.add(valide);
        
        this.setContentPane(pane);
    
        valide.addActionListener(new ActionListener()
            {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(!nomServ.getText().equals(""))
                {
                    if(!(nomServ.getText().contains(" ")||nomServ.getText().contains("~")||nomServ.getText().contains("^")||nomServ.getText().contains("¨")))
                    {
                    if(commentaire.getText().equals(""))
                    {
                        info=" "+nomServ.getText()+"|"+sanCom+"*"+fenetre.d.getNnbrClient()+"/"+fenetre.d.getNbrSalon();
                    }
                    else
                        info=" "+nomServ.getText()+"|"+commentaire.getText()+"*"+fenetre.d.getNnbrClient()+"/"+fenetre.d.getNbrSalon();
                
                try {
                     final Thread tr=new Thread(new cont(fenetre));
                    tr.start();
                    InetAddress adrLocale = InetAddress.getLocalHost();
                   fenetre.label.setText("Serveur demarrer a partir de "+adrLocale.getHostName()+" a l'adresse "+adrLocale.getHostAddress()+" et au port 5050");
                } catch (UnknownHostException ex) {
                    JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "impossible de trouver l'addresse", "Erreur", JOptionPane.ERROR_MESSAGE);			
                }
                fenetre.setVisible(true);
                setVisible(false);
                    }
                    else
                        JOptionPane.showMessageDialog(null, "Presence de caractere(s) indesirable(s)\nVeuillez le nom de votre serveur", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
                
            });
        
        this.setVisible(true);
    }
    
    class cont implements Runnable
  {
      fn ss;
      cont(fn f)
      {
            
                ss=f;
               
           
      }
      @Override
        public void run() {
            try {
                   fenetre.socketserver = new ServerSocket(5050); 
                  // commence.setEnabled(false);
                         fenetre.stop.setEnabled(true);
                   while(true)
                   {
                      
                      
                        fenetre.socketduserveur=fenetre.socketserver.accept();
                        fenetre.nombre++;
                        fenetre.out2= new ObjectOutputStream(fenetre.socketduserveur.getOutputStream());
            
                        fenetre.in=new BufferedReader(new InputStreamReader(fenetre.socketduserveur.getInputStream()));
            String identifiant=fenetre.in.readLine();
            System.out.println(identifiant);
            if(identifiant.equals("~~~"))
            {
                PrintWriter out=new PrintWriter(fenetre.socketduserveur.getOutputStream());
                out.println(info);
                out.flush();
            }
            else
            {
            String pseudo=identifiant.substring(0,identifiant.indexOf("~"));
            String ipPseudo=identifiant.substring(identifiant.indexOf("~")+1,identifiant.indexOf(":"));
            String nomPc=identifiant.substring(identifiant.indexOf(":")+1);
            System.out.println("pseudo:"+pseudo);         
		new discussion(fenetre.socketduserveur,fenetre.d,pseudo,ipPseudo,nomPc,"General");
                 fenetre.scrols[0].setListData(fenetre.salo[0].getList());
                 if(commentaire.getText().equals(""))
                    {
                        info=" "+nomServ.getText()+"|"+sanCom+"*"+fenetre.d.getNnbrClient()+"/"+fenetre.d.getNbrSalon();
                    }
                    else
                        info=" "+nomServ.getText()+"|"+commentaire.getText()+"*"+fenetre.d.getNnbrClient()+"/"+fenetre.d.getNbrSalon();
            }
            
                 
                   }
                } catch (IOException ex) {JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "demarrage serveur impossible\nvérifier si le port est libre", "Erreur", JOptionPane.ERROR_MESSAGE);			}
                
        }
      
  }
    
}
