/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Fichier;

import cchat1.Client.FenetresPrincipaux.priveeMessage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.OutputStream;

/**
 *
 * @author Cenyo
 */
public class EnvoiFichier implements Runnable{
   private String ipRec;
   private File f;
   private priveeMessage parent;
   private OutputStream fluxsortie ;
    Transfert t;
    int port;
    public EnvoiFichier(String ipRec,File f,priveeMessage p,String pp)
    {
        this.ipRec=ipRec;
        this.f=f;
        this.parent=p;
        System.out.println(pp);
      port=Integer.valueOf(pp).intValue();
        
    t=new Transfert(10240,port); 
    t.initPanel(parent); 
        t.cancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
             
                    t.closeFlux();
                
            }
        } );
        
    }



    @Override
    public void run() {
             
           t.connect(ipRec); 
           
             t.openFlux();
             System.out.println("dans envoifichier nom :"+f.getName());
       t.envoiNom(f); 
       t.envoiTaille(f);
        t.envoi(f); 
    }
    
}
