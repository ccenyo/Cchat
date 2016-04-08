/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Capture;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import cchat1.Client.FenetresPrincipaux.priveeMessage;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author cenyo
 */
public class receptionProfil implements Runnable{
Socket client=new Socket();
 private static byte[] octets;
 private static BufferedImage photo;
    String ip;
    String pseudo;
    MaFenetre parent;
    Image ph;
    Image ph2;
    Image ph3;
    int port;
   public receptionProfil(String i,MaFenetre p,String ps,String por)
    {
        parent=p;
        pseudo=ps;
        ip=i;
        port=Integer.valueOf(por).intValue();
    }
    @Override
    public void run() {
        try {
            client = new Socket(ip, port);
             ObjectInputStream fluxRéseau = new ObjectInputStream(client.getInputStream());
           octets = (byte[]) fluxRéseau.readObject();
          ByteArrayInputStream fluxImage = new ByteArrayInputStream(octets);
           photo = ImageIO.read(fluxImage);
           ph=photo;
           ph=scale(photo,100,80);
           ph2=scale(photo,250,200);
           ph3=scale(photo,20,20);
          // BufferedImage im=photo;
          initV();
           
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(receptionProfil.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            try {
                client=new Socket(ip, 5053);
                ObjectInputStream fluxRéseau = new ObjectInputStream(client.getInputStream());
           octets = (byte[]) fluxRéseau.readObject();
          ByteArrayInputStream fluxImage = new ByteArrayInputStream(octets);
           photo = ImageIO.read(fluxImage);
           ph=photo;
           ph=scale(photo,100,100);
           ph2=scale(photo,250,200);
          // BufferedImage im=photo;
          initV();
            } catch (ClassNotFoundException ex1) {
                Logger.getLogger(receptionProfil.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (UnknownHostException ex1) {
                Logger.getLogger(receptionProfil.class.getName()).log(Level.SEVERE, null, ex1);
            } catch (IOException ex1) {
                Logger.getLogger(receptionProfil.class.getName()).log(Level.SEVERE, null, ex1);
            }
        } catch (IOException ex) {
            Logger.getLogger(receptionProfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        public static Image scale(Image source, int width, int height) {
    /* On crée une nouvelle image aux bonnes dimensions. */
    BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

    /* On dessine sur le Graphics de l'image bufferisée. */
    Graphics2D g = buf.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.drawImage(source, 0, 0, width, height, null);
    g.dispose();

    /* On retourne l'image bufferisée, qui est une image. */
    return buf;
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
                    Temp.imageprofil.setIcon(new ImageIcon(ph));   
                    Temp.call.photo.setIcon(new ImageIcon(ph2));
                    Temp.icc=ph3;
                     Temp.repaint();
                    flag=true;
                    break;
                }
            }
           if(!flag)
           {
            
               
                parent.fenetrePrivee.add(new priveeMessage(pseudo," ",parent.socket,parent.pseud,parent.myself,parent.fen));
            priveeMessage Temp2=(priveeMessage)parent.fenetrePrivee.get(parent.fenetrePrivee.size()-1);
                parent.onglet.add(pseudo,Temp2);            
                     Temp2.imageprofil.setIcon(new ImageIcon(ph)); 
                     Temp2.call.photo.setIcon(new ImageIcon(ph2));
                     Temp2.icc=ph3;
                     
                     Temp2.repaint();
                 
           
           }
                                   
                                   
                } catch (IOException ex) {
                  
                                JOptionPane.showMessageDialog(null, "Client introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);			
                }
  }
}
