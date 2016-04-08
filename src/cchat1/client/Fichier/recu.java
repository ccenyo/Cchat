/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Fichier;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import cchat1.Client.FenetresPrincipaux.connexion;
import cchat1.Client.FenetresPrincipaux.priveeMessage;
import cchat1.Client.reception;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author Cenyo
 */
public final class recu implements Runnable{
    
       private     MaFenetre parent;
     private       reception parent2;
   private   String msg;
  private    String demandeur="";
  private    String receveur="";
 private     ServerSocket recep;
   private   Socket reception;
   Transfert t;
   private int port;
    public  recu(String m,MaFenetre maf,reception rec)
      {
        try {
            port=5051;
            parent=maf;
            parent2=rec;
              
                  msg=m;
                   
                   demandeur=msg.substring(3,msg.indexOf(":"));
                       receveur=msg.substring(msg.indexOf(":")+1);
                       t=new Transfert(10240,port);
                        while(!t.openServeur())
                        {
                       port++;
                       t=new Transfert(10240,port);
                        }
                        t.initPanel(initPane(msg.substring(3,msg.indexOf(":"))));
                        
                           InetAddress adrLocale = InetAddress.getLocalHost(); 
                  parent.outp.println("pro"+adrLocale.getHostAddress()+"~"+demandeur+":"+receveur+"|"+port);
                      System.out.println("dans recu:"+adrLocale.getHostAddress()+" "+demandeur+" "+receveur);
                       parent.outp.flush();
                        System.out.println("initiation de la recption et création du serveur socket");
                        
                System.out.println("En attente de connexion");
        } catch (UnknownHostException ex) {
            Logger.getLogger(recu.class.getName()).log(Level.SEVERE, null, ex);
        }
               
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
        t.waitConnect();
               System.out.println("connecté");
               t.openFlux();
                      String nomFichier=t.waitNom();
 System.out.println(nomFichier);
 long taille=t.waitTaille();
  JOptionPane jop = new JOptionPane();			
int option = JOptionPane.showConfirmDialog(null, "Voulez-vous recevoir le fichier:\n"+nomFichier+"\n taille:"+taille,demandeur+" vous envoi un fichier" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
if(option != JOptionPane.OK_OPTION)
{
    // outp.println("pri"+" je ne veut pas ce fichier"+"~"+demandeur+":"+receveur);
     parent.outp.println("pri"+parent.pseud+":"+"je ne veut pas de votre fichier"+"~"+demandeur);
     parent.outp.flush();
     t.stopTransfert();
}
else
{
    
    JFileChooser chooser = new JFileChooser();
             chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
             chooser.setFileHidingEnabled(false);
                      chooser.showOpenDialog(null);
                      
                File f=chooser.getSelectedFile();
                if(f!=null)
                {
               String lien=f.getAbsolutePath();
               parent2.receptionMessagePrivee("<strong>nom du fichier:</strong> "+nomFichier+"<br><strong> taille:</strong> "+taille+" Ko",receveur,demandeur);
               t.reception(lien, nomFichier,taille);
                }
                else
                    t.closeFlux();
} 
            
    }
}
