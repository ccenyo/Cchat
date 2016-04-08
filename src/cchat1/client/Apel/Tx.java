/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Apel;


import cchat1.Client.FenetresPrincipaux.MaFenetre;
import cchat1.Client.FenetresPrincipaux.priveeMessage;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

public class Tx {
   private String msg;
  private  BufferedReader in2;
   private PrintWriter out2;
  private  String ipA;
   private String envoyeur;
   private fenetreApel fen;
  private  int n=0;
  private  boolean stopCapture = false;
   
private	ByteArrayOutputStream byteArrayOutputStream;

private	AudioFormat audioFormat;
private Mixer.Info[] mixerInfo;
private Mixer mixer;
	private  TargetDataLine targetDataLine;

	private AudioInputStream audioInputStream;

	private BufferedOutputStream out = null;

	private BufferedInputStream in = null;

	private Socket sock = null;
      private MaFenetre fenetre;
	private SourceDataLine sourceDataLine;
        private int port;
        Tx(String ip,String envoi,MaFenetre m,int po)
        {
            
            ipA=ip;
            envoyeur=envoi;
            fenetre=m;
            port=po;
           
        }
        public void captureAudio() {
		try {

			sock = new Socket(ipA, port);

			out = new BufferedOutputStream(sock.getOutputStream());
			in = new BufferedInputStream(sock.getInputStream());
                         in2=new BufferedReader(new InputStreamReader(sock.getInputStream()));
                        out2=new PrintWriter(sock.getOutputStream());
                               fenetre.fen.arreter.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                     signalEndapel(fenetre.pseud,envoyeur);
                                endApel();
                            
                    }
                }); 
                   fenetre.fen.commence=true;
   //  fenetre.fen.c.compt=0;
                        
//fen=new fenetreApel("Discussion avec "+envoyeur);
/*fen.jj.setText("Appel en cours");
fen.arreter.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            fen.jj.setText("Appel Terminer");
                            
                            targetDataLine.stop();
                                sourceDataLine.stop();
                                 targetDataLine.close();
                                sourceDataLine.close();
                                n=0;
                           sock.close();
                            fen.setVisible(false);
                        } catch (IOException ex) {
                            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
    
});
fen.partage.addActionListener(new ActionListener(){

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                       fenetre.outp.println("par"+envoyeur+"~"+fenetre.pseud);
                       fenetre.outp.flush();
                       fen.partage.setEnabled(false);  
                       
                        
                       
                    }

});
  /* fen.addWindowListener(new WindowAdapter() {
            @Override
                        public void windowClosing(WindowEvent e) {
                try {
                     fen.jj.setText("Appel Terminer");
                            
                            targetDataLine.stop();
                                sourceDataLine.stop();
                                 targetDataLine.close();
                                sourceDataLine.close();
                           sock.close();
                           n=0;
                            fen.setVisible(false);
                } catch (IOException ex) {
                    Logger.getLogger(ClientPhoto.class.getName()).log(Level.SEVERE, null, ex);
                }
              
                        }
                });*/
		   fenetre.fen.partage.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                           
                           fenetre.outp.println("par"+envoyeur+"~"+fenetre.pseud);
                           fenetre.outp.flush();
                            fenetre.fen.partage.setEnabled(false);  
                           
                        }

    });
			 mixerInfo = AudioSystem.getMixerInfo();
                        
			System.out.println("Available mixers:");
			for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
				System.out.println(mixerInfo[cnt].getName());
                              //  fen.list.addItem(mixerInfo[cnt].getName()); 
			}
               

			audioFormat = getAudioFormat();

			DataLine.Info dataLineInfo = new DataLine.Info(
					TargetDataLine.class, audioFormat);

			mixer = AudioSystem.getMixer(mixerInfo[3]);
                        System.out.println(mixer.isOpen()); 
                        
                               /*            fen.list.addActionListener(new ActionListener(){

                @Override
                public void actionPerformed(ActionEvent e) {
                   mixer = AudioSystem.getMixer(mixerInfo[fen.list.getSelectedIndex()]);
                   System.out.println(fen.list.getSelectedIndex()); 
                }
                            
                        });*/
                        System.out.println(mixer.isLineSupported(dataLineInfo));
			targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);
                          
			targetDataLine.open(audioFormat);
			targetDataLine.start();
//System.out.println(targetDataLine.isOpen());
			Thread captureThread = new CaptureThread();
			captureThread.start();
                       
			DataLine.Info dataLineInfo1 = new DataLine.Info(
					SourceDataLine.class, audioFormat);
			sourceDataLine = (SourceDataLine) AudioSystem
					.getLine(dataLineInfo1);
			sourceDataLine.open(audioFormat);
			sourceDataLine.start();

			Thread playThread = new PlayThread();
			playThread.start(); 
                       
		} catch (Exception e) {            
                 
                System.out.println("erreur----4 "+e);   
		}
	}

	class CaptureThread extends Thread {

		byte tempBuffer[] = new byte[10000];
                                      
                      


        @Override
		public void run() {
			byteArrayOutputStream = new ByteArrayOutputStream();
                      //   fen.start(fen); 
			stopCapture = false;
			try {
				while (!stopCapture) {

					int cnt = targetDataLine.read(tempBuffer, 0,
							tempBuffer.length);

					out.write(tempBuffer);
 mixer = AudioSystem.getMixer(mixerInfo[3]);
					if (cnt > 0) {

						byteArrayOutputStream.write(tempBuffer, 0, cnt);

					}
				}
                             
			} catch (Exception e) {
               endApel();
				//fen.setVisible(false);
                    System.out.println("erreur----3"+e);
                   
                
			}
		}
	}

	private AudioFormat getAudioFormat() {
		float sampleRate = 8000.0F;

		int sampleSizeInBits = 16;

		int channels = 1;

		boolean signed = true;

		boolean bigEndian = false;

		return new AudioFormat(sampleRate, sampleSizeInBits, channels, signed,
				bigEndian);
	}

	class PlayThread extends Thread {
		byte tempBuffer[] = new byte[10000];

        @Override
		public void run() {
			try {
				while (in.read(tempBuffer) != -1) {
					sourceDataLine.write(tempBuffer, 0, 10000);
				}
                               endApel();
                         
			} catch (IOException e) {
             
			}
		}
	}
  public void endApel()
 {
     System.out.println("dans TX 1"+fenetre.compteur);
        fenetre.fen.commence=false;
        if(fenetre.fen.c!=null)
     fenetre.fen.c.compt=0;
      try {
                   
                if(sourceDataLine!=null)    
                sourceDataLine.stop();
                if(targetDataLine!=null) 
                    targetDataLine.close();
                    if(sourceDataLine!=null) 
                    sourceDataLine.close();
                    if(sock!=null) 
                    sock.close();
                //    fen.setVisible(false);
                } catch (IOException ex) {
                    Logger.getLogger(Tx.class.getName()).log(Level.SEVERE, null, ex);
                }
         
                for(int i=0;i<fenetre.fenetrePrivee.size();i++)
                   {
                       priveeMessage Temp=(priveeMessage)fenetre.fenetrePrivee.get(i);
                       if(Temp.pseudo.equals(envoyeur))
                       {
           
                           Temp.centre.remove(Temp.est);
                           Temp.centre.repaint();
                           
                           break;
                       }
                   }
              fenetre.compteur=1;
       System.out.println("dans TX 2"+fenetre.compteur);
 }
  public void signalEndapel(String monpseudo,String destinataire)
 {
     fenetre.outp.println("fin"+monpseudo+"~"+destinataire);
                     fenetre.outp.flush();
 }
}
