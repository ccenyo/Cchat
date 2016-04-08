/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import java.sql.Time;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cenyo
 */
public class Temps implements Runnable{
   private MaFenetre ff;
    
    public Temps(MaFenetre f)
    {
        ff=f;
    }

    @Override
    public void run() {
        while(true)
        {
            Date dt=new Date();
            Time ti=new Time(System.currentTimeMillis());
        ff.AfficheTemps.setText(ti.toString()); 
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Temps.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
}
