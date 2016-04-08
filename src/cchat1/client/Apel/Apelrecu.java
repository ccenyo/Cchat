/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Apel;

import cchat1.Client.FenetresPrincipaux.MaFenetre;

/**
 *
 * @author Cenyo
 */
public class Apelrecu implements Runnable{

   private String ipA;
  private  String envoyeur;
  private  MaFenetre fenetre;
  private int port;
   public Apelrecu(String ip,String envoi,MaFenetre m,String po)
    {
        System.out.println(po);
        port=Integer.valueOf(po).intValue();
        ipA=ip;
        envoyeur=envoi;
        fenetre=m;
    }
    @Override
    public void run() {
        Tx tx = new Tx(ipA,envoyeur,fenetre,port);
		tx.captureAudio();
    }
    
}
