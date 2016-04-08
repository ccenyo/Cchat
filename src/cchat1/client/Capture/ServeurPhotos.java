/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Capture;

/**
 *
 * @author Cenyo
 */
import cchat1.Client.FenetresPrincipaux.MaFenetre;
import cchat1.Client.FenetresPrincipaux.priveeMessage;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ServeurPhotos extends JPanel implements Runnable{
  private static PanneauImage panneau ;
 private  static JLabel etat=new JLabel();
 private static byte[] octets;
 private static JButton save=new JButton("Capturer");
 private ObjectOutputStream oos = null;
private static BufferedImage photo;
private static int port;
private static Socket client;
private static ServerSocket service;
MaFenetre parent;
String pseudo;
  public ServeurPhotos(String titre,int por,MaFenetre fe) {
      panneau = new PanneauImage(this);
      pseudo=titre;
      parent=fe;
      port=por;
      Font font = new Font("Comics Sans MS", Font.BOLD, 14);
    //   panneau.setLayout(new BorderLayout()); 
   etat.setFont(font); 
   etat.setForeground(Color.YELLOW);
      //    panneau.add(etat,BorderLayout.NORTH);
        //  panneau.add(save,BorderLayout.SOUTH);
    //setSize(500, 400);
    //this.setLocationRelativeTo(null);    
    ///setTitle("Photo envoyé par "+titre);
    //add(panneau);
   initV();
    save.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser f=new JFileChooser();
                f.approveSelection();
                f.showSaveDialog(null);
                 File choi= f.getSelectedFile();
                System.out.println(choi.getAbsolutePath());
                System.out.println(choi.getName());
                
                if(photo!=null)
                {
                 File fichier = new File(choi.getAbsolutePath()+".jpg");
                etat.setText("Envoie de la photo en cours"); 
                ImageIO.write(photo, "JPEG", fichier);
                choi=fichier;
                oos = new ObjectOutputStream(
                               new BufferedOutputStream(
                                               new FileOutputStream(
                                                               choi)));
                oos.writeObject(fichier);
                oos.close();
                }
                } catch (IOException ex) {
                    Logger.getLogger(ServeurPhotos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }); 
   /* addWindowListener(new WindowAdapter() {
            @Override
                        public void windowClosing(WindowEvent e) {
                if(client!=null)
                {
                try {
                    client.close();
                    service.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientPhoto.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                        }
                });
    setVisible(true);*/
  }
  public void end()
  {
      try {
                      client.close();  
                      service.close();
                    boolean flag=false;
                     
                                 for(int i=0;i<parent.fenetrePrivee.size();i++)
            {
                priveeMessage Temp=(priveeMessage)parent.fenetrePrivee.get(i);
                if(Temp.pseudo.equals(pseudo))
                {
    
                    parent.onglet.setSelectedComponent(parent.onglet.getComponentAt(parent.onglet.indexOfComponent(Temp)));
                 
                    Temp.centre.add(Temp.pa); 
                      Temp.centre.remove(Temp.split);
                     Temp.centre.repaint();
                    flag=true;
                    break;
                }
            }
           if(!flag)
           {
            
               
                parent.fenetrePrivee.add(new priveeMessage(pseudo," ",parent.socket,parent.pseud,parent.myself,parent.fen));
            priveeMessage Temp2=(priveeMessage)parent.fenetrePrivee.get(parent.fenetrePrivee.size()-1);
                parent.onglet.add(pseudo,Temp2);            
                    parent.onglet.setSelectedComponent(parent.onglet.getComponentAt(parent.onglet.indexOfComponent(Temp2))); 
                    
                  
                   
                    Temp2.centre.add(Temp2.pa); 
                      Temp2.centre.remove(Temp2.split);
                     Temp2.centre.repaint();
           
           }
                                   
                                   
                } catch (IOException ex) {
                  
                                JOptionPane.showMessageDialog(null, "Client introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);			
                }
  }
  public void initV()
  {
        try {
                                   
                    boolean flag=false;
                     
                                 for(int i=0;i<parent.fenetrePrivee.size();i++)
            {
                priveeMessage Temp=(priveeMessage)parent.fenetrePrivee.get(i);
                if(Temp.pseudo.equals(pseudo))
                {
    
                    parent.onglet.setSelectedComponent(parent.onglet.getComponentAt(parent.onglet.indexOfComponent(Temp)));
                    Temp.split.setLeftComponent(Temp.pa);
                    Temp.split.setRightComponent(panneau);
                    Temp.centre.remove(Temp.pa); 
                      Temp.centre.add(Temp.split);
                     Temp.centre.repaint();
                    flag=true;
                    break;
                }
            }
           if(!flag)
           {
            
               
                parent.fenetrePrivee.add(new priveeMessage(pseudo," ",parent.socket,parent.pseud,parent.myself,parent.fen));
            priveeMessage Temp2=(priveeMessage)parent.fenetrePrivee.get(parent.fenetrePrivee.size()-1);
                parent.onglet.add(pseudo,Temp2);            
                    parent.onglet.setSelectedComponent(parent.onglet.getComponentAt(parent.onglet.indexOfComponent(Temp2))); 
                    
                  
                   
                    Temp2.split.setLeftComponent(Temp2.pa);
                    Temp2.split.setRightComponent(panneau);
                    Temp2.centre.remove(Temp2.pa); 
                      Temp2.centre.add(Temp2.split);
                 
           
           }
                                   
                                   
                } catch (IOException ex) {
                  
                                JOptionPane.showMessageDialog(null, "Client introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);			
                }
  }

 

  public static void activerService() throws Exception {
    service = new ServerSocket(port);
    while (true) {
       client = service.accept();
      etat.setText("reception d'une image"); 
      ObjectInputStream fluxRéseau = new ObjectInputStream(client.getInputStream());
       octets = (byte[]) fluxRéseau.readObject();
      ByteArrayInputStream fluxImage = new ByteArrayInputStream(octets);
       photo = ImageIO.read(fluxImage);
      etat.setText("Terminer"); 
      panneau.change(photo); 
    //  panneau.remove(etat); 
    }
  }

    @Override
    public void run() {
        try {
                
       activerService();
        } catch (Exception ex) {
   //         setVisible(false);
        }
    }
}  