/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;

/**
 *
 * @author Ccenyo
 */
public class micro implements Runnable{
    public MaFenetre parent;
    private	AudioFormat audioFormat;
private Mixer.Info[] mixerInfo;
private Mixer mixer;
  public  micro(MaFenetre f)
    {
        parent=f;
         
    }
     private AudioFormat getAudioFormat(){
		    float sampleRate = 8000.0F;		  
		    int sampleSizeInBits = 16;		   
		    int channels = 1;		    
		    boolean signed = true;		    
		    boolean bigEndian = false;		 
		    return new AudioFormat(sampleRate,sampleSizeInBits,channels,signed,bigEndian);
		  }

    @Override
    public void run() {
        while(true)
        {
        mixerInfo = AudioSystem.getMixerInfo();
                        audioFormat = getAudioFormat();

			DataLine.Info dataLineInfo = new DataLine.Info(
					TargetDataLine.class, audioFormat);
			System.out.println("Available mixers:");
			for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
				System.out.println(mixerInfo[cnt].getName());
                                mixer = AudioSystem.getMixer(mixerInfo[cnt]);
                                if(mixer.isLineSupported(dataLineInfo))
                                    System.out.println("numero ouvert "+cnt);
                              //  fen.list.addItem(mixerInfo[cnt].getName()); 
			}
               

			

			
                        System.out.println(mixer.isLineSupported(dataLineInfo));
            try {
                Thread.sleep(10000);
            } catch (InterruptedException ex) {
                Logger.getLogger(micro.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
