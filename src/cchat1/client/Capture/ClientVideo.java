/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Capture;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author cenyo
 */
public class ClientVideo extends JPanel  {
     Robot robot;
BufferedImage image;
 
  private JLabel etat=new JLabel("partage en cours");
  private PanneauImage panneau ;
 // private JButton choix=new JButton("débuter");
 private JPanel general=new JPanel(new BorderLayout());
private FileDialog fl;
private Thread t;
private Socket connexion;
private File  fichier = new File("image.jpg");
private String ip;
  public ClientVideo(String f,String titre) {
        try {
            Font font = new Font("Comics Sans MS", Font.BOLD, 14);
      robot = new Robot();
      ip=f;
         panneau = new PanneauImage(this);
         panneau.setLayout(new BorderLayout()); 
         etat.setFont(font); 
         etat.setForeground(Color.YELLOW);
                panneau.add(etat,BorderLayout.NORTH);
          panneau.change(récupérer());
      
       //  this.setResizable(false);
          setSize(300, 200);
         // setTitle("partage d'écran avec "+titre);
   //       this.add(choix, BorderLayout.NORTH);
          JPanel sud=new JPanel(new GridLayout(1,2));
     //     sud.add(choix);
         // sud.add(envoyer);
          this.add(sud, BorderLayout.SOUTH);
          this.add(panneau);
         // setContentPane(general);
          //setDefaultCloseOperation(EXIT_ON_CLOSE);
          t=new Thread(new init());
        t.start();
   /*      addWindowListener(new WindowAdapter() {
            @Override
                        public void windowClosing(WindowEvent e) {
                if(connexion!=null)
                {
                try {
                    connexion.close();
                    ip="0";
                    t.interrupt();
                    t.stop();
                    t.suspend();
                     connexion.close();
                } catch (IOException ex) {
                    Logger.getLogger(ClientPhoto.class.getName()).log(Level.SEVERE, null, ex);
                }
                }
                        }
                });*/
       //   setVisible(true);
        } catch (AWTException ex) {
           
        }
  }
     
  private BufferedImage récupérer() {
    try {
      BufferedImage photo = ImageIO.read(new File(fl.getDirectory() + "/" + fl.getFile()));
     // panneau.change(récupérer());
      return photo;
    }
    catch (Exception ex) {
 //     setTitle("Problème de localisation des photos");
      return null;
    }      
  }
 
    
  private void envoyer(byte[] octets) {
    try {
      connexion = new Socket(ip, 7778);
      ObjectOutputStream fluxRéseau = new ObjectOutputStream(connexion.getOutputStream());
      fluxRéseau.writeObject(octets);
      connexion.close();
       //etat.setText("Terminer");
     //  panneau.remove(etat); 
    } 
    catch (IOException ex) {
   //   setTitle("Problème avec le serveur");
    }
  }
 
  class init implements Runnable
{

    @Override
    public void run() {
       while(true)
        {
               
                    
                    image = robot.createScreenCapture(new Rectangle(java.awt.Toolkit.getDefaultToolkit().getScreenSize()));  
                    
               //  panneau.change(image);
                 
                         écrireFichier(image);
              /*  try {
                    Thread.sleep(1000); 
                } catch (InterruptedException ex) {
                    Logger.getLogger(ClientVideo.class.getName()).log(Level.SEVERE, null, ex);
                }*/
    }
    }
    
}
  
   void écrireFichier(BufferedImage image) {
        
            try {
              ImageWriter writer = ImageIO.getImageWritersByFormatName("JPEG").next();
              ImageWriteParam iwp = writer.getDefaultWriteParam();
              iwp.setCompressionMode(iwp.MODE_EXPLICIT);
              float[] valeurs = iwp.getCompressionQualityValues();
              System.out.println(valeurs[valeurs.length-1]);
              iwp.setCompressionQuality(valeurs[valeurs.length-1]);
              FileImageOutputStream fichiers = new FileImageOutputStream(new File("image.jpg"));
              writer.setOutput(fichiers);
              IIOImage imageFlux = new IIOImage(image, null, null);
              writer.write(null, imageFlux, iwp);              
            byte[] octets = new byte[(int)fichiers.length()];
                             FileInputStream photo = new FileInputStream(fichier);
                             photo.read(octets);
                             envoyer(octets);
        } catch (IOException ex) {
           
        }
               
      }
}
