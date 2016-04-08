/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Fichier;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import cchat1.Client.FenetresPrincipaux.priveeMessage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Ccenyo
 */
public class EnvoiAndroid implements Runnable{
 Transfert t;
 int port=200;
 String pseudo;
 MaFenetre parent;
 File fichier;
   public EnvoiAndroid(String msg,MaFenetre m,File f) throws UnknownHostException
    {
        fichier=f;
        parent=m;
        pseudo=msg;
        t=new Transfert(10240,port);
            while(!t.openServeur())
                        {
                       port++;
                       t=new Transfert(10240,port);
                        }
            t.initPanel(initPane(msg));
                      
                           
                        System.out.println("initiation de la recption et création du serveur socket");
                        
                System.out.println(port);
    }
     public priveeMessage initPane(String nom)
    {
        System.out.println("init pane demarrer");
        priveeMessage trouve=null;  
        try {
                                 
                    boolean flag=false;
                     
                                 for(int i=0;i<parent.fenetrePrivee.size();i++)
            {
                priveeMessage Temp=(priveeMessage)parent.fenetrePrivee.get(i);
                if(Temp.pseudo.equals(nom))
                {
    System.out.println("init pane existe");
                    parent.onglet.setSelectedComponent(parent.onglet.getComponentAt(parent.onglet.indexOfComponent(Temp)));
                    trouve=Temp;
                     Temp.est.repaint();
                    flag=true;
                    break;
                }
            }
           if(!flag)
           {
            
                System.out.println("init pane existe pas");
                parent.fenetrePrivee.add(new priveeMessage(nom," ",parent.socket,parent.pseud,parent.myself,parent.fen));
            priveeMessage Temp2=(priveeMessage)parent.fenetrePrivee.get(parent.fenetrePrivee.size()-1);
                parent.onglet.add(nom,Temp2);            
                    trouve=Temp2;
                 
           
           }
                                   
                                   
                } catch (IOException ex) {
                  
                                JOptionPane.showMessageDialog(null, "Client introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);			
                }
        return trouve;
    }
    @Override
    public void run() {
        try {
         //   t.openServeur();
            InetAddress adrLocale = InetAddress.getLocalHost(); 
                        parent.outp.println("pra"+adrLocale.getHostAddress()+"~"+pseudo+":"+parent.pseud+"|"+port);
                            System.out.println("dans recu:"+adrLocale.getHostAddress()+" "+pseudo+" "+parent.pseud);
                             parent.outp.flush();
            t.waitConnect();
              t.openFlux();
            t.envoiNom(fichier);
            t.envoiTaille(fichier);
            
            t.envoi(fichier);
            t.closeFlux();
        } catch (UnknownHostException ex) {
            Logger.getLogger(EnvoiAndroid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
