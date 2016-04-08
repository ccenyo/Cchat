/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Apel;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Cenyo
 */
public class compteurApel {
   public int compt=0;
   private fenetreApel fn;
   private Timer  tt = new Timer();
   public boolean commence=false;
    compteurApel(fenetreApel f)
    {
        fn=f;
        tt.schedule(new initiation(fn,this), 0,1*1000); 
    }

}
 class initiation extends TimerTask
{
compteurApel f;
fenetreApel fn;
initiation(fenetreApel ff,compteurApel cc)
{
    fn=ff;
    f=cc;
    
}
    @Override
    public void run() {
        if(fn.commence=true)
        {
         int seconde=f.compt%60;
          int minute=(f.compt/60)%60;
          int heure=(f.compt/60)/60;
          fn.jj.setText("Appel en Cours... "+heure+":"+minute+":"+seconde);
          f.compt++;
        }
    }
    
}
