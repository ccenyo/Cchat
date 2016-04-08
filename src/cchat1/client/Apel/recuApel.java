/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Apel;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import cchat1.Client.FenetresPrincipaux.connexion;
import cchat1.Client.reception;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.LineUnavailableException;

/**
 *
 * @author Cenyo
 */

public class recuApel implements Runnable{
     private   ServerSocket MyService;
   private         MaFenetre parent;
   private         reception parent2;
  private    String msg;
  private    String demandeur="";
  private    String receveur="";
  private    ServerSocket recep;
  private    Socket reception;
  public Sender s;
  int port;
public recuApel(String m,MaFenetre maf,reception rec)
{
    port=50;
    parent=maf;
          parent2=rec;
            try {
                msg=m;
                 demandeur=msg.substring(3,msg.indexOf(":"));
                     receveur=msg.substring(msg.indexOf(":")+1);
                      while(!openServeur())
            {
                port++;
                System.out.println("Envoi profil "+port);
              //  service = new ServerSocket(port);
                
                
            }
                    InetAddress adrLocale = InetAddress.getLocalHost(); 
                parent.outp.println("RAI"+adrLocale.getHostAddress()+"~"+demandeur+":"+receveur+"?"+port);
                    System.out.println("dans recu:"+adrLocale.getHostAddress()+" "+demandeur+" "+receveur);
                     parent.outp.flush();
                      System.out.println("initiation de la recption et création du serveur socket");
              // recep=new ServerSocket(5052);
              System.out.println("En attente de connexion");
              
            } catch (IOException ex) {
                Logger.getLogger(connexion.class.getName()).log(Level.SEVERE, null, ex);
            } 
}
    @Override
    public void run() {
        try {
           s=new Sender(demandeur,parent,MyService);
        } catch (LineUnavailableException ex) {
            Logger.getLogger(recuApel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
          public boolean openServeur()
    {
	boolean b = true;
	try{
	 MyService = new ServerSocket(port);
	}catch(Exception e){
	    b = false;
	    e.printStackTrace();
	    
	}
	return b;
    }
    
}
