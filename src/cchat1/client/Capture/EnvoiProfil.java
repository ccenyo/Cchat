/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Capture;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;

/**
 *
 * @author cenyo
 */
public final class EnvoiProfil implements Runnable{

    private static ServerSocket service;   
        private static byte[] octets;
        private static BufferedImage photo;
      private static Socket client;
      MaFenetre parent;
      int port;
      String nom;
      // si on met une echelle >0 ca foire
private double echelle=0.5;
    public EnvoiProfil(MaFenetre m,String no)
    {
        try {
            nom=no;
            port=5052;
            parent=m;
            System.out.println("Envoi profil "+port);
            service = new ServerSocket(port);
            while(!openServeur())
            {
                port++;
                System.out.println("Envoi profil "+port);
              //  service = new ServerSocket(port);
                
                
            }
            parent.outp.println("IP4"+nom+":"+port+"?"+parent.pseud);
             parent.outp.flush();
        } catch (IOException ex) {
            Logger.getLogger(EnvoiProfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public boolean openServeur()
    {
	boolean b = true;
	try{
	    service = new ServerSocket(port);
	}catch(Exception e){
	    b = false;
	    e.printStackTrace();
	    
	}
	return b;
    }
    @Override
    public void run() {
        try {
          
          
       client = service.accept();
      écrireFichier( récupérer());
    
        }catch (IOException ex) {
            Logger.getLogger(EnvoiProfil.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
      private void envoyer(byte[] octets) {
    try {
  
      ObjectOutputStream fluxRéseau = new ObjectOutputStream(client.getOutputStream());
      fluxRéseau.writeObject(octets);
      client.close();
      
      
    } 
    catch (IOException ex) {
   //   setTitle("Problème avec le serveur");
    }
  }
         void écrireFichier(BufferedImage image) {
        
            try {
BufferedImage biNew =new BufferedImage((int) (image.getWidth() * echelle),(int) (image.getHeight() * echelle),image.getType());
AffineTransform tx =new AffineTransform();
tx.scale(0.5,0.5);
AffineTransformOp op =new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
image=op.filter(image, biNew);
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
                             FileInputStream photo = new FileInputStream(new File("image.jpg"));
                             photo.read(octets);
                             envoyer(octets);
                             fichiers.close();
                             photo.close();
        } catch (IOException ex) {
           
        }
               
      }
         private BufferedImage récupérer() {
              BufferedImage photo=null;
        try {
          photo=ImageIO.read(new File(parent.o.reglages.getProperty("image")));  
              
             
         
       
        } catch (IOException ex) {
            Logger.getLogger(EnvoiProfil.class.getName()).log(Level.SEVERE, null, ex);
        }
          return photo;
    }
}
