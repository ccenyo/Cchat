/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Apel;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import cchat1.Client.FenetresPrincipaux.priveeMessage;
import cchat1.Client.gestionDuSon;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Cenyo
 */
public class Sender {
   private String msg;
   private int n=0;
  private  BufferedReader in2;
  private  PrintWriter out2;
  private  MaFenetre fenetr;
//  private  fenetreApel fen;
 private   ServerSocket MyService;
private	Socket clientSocket = null;
private	BufferedInputStream input;
private	TargetDataLine targetDataLine;

	private BufferedOutputStream out;
	 private ByteArrayOutputStream byteArrayOutputStream;
	 private AudioFormat audioFormat;	
	
private	  SourceDataLine sourceDataLine;	  
private	 byte tempBuffer[] = new byte[10000];
	public gestionDuSon Apel;
        String envoyeur;
	 Sender(final String appelant,MaFenetre fe,ServerSocket s) throws LineUnavailableException{   
        try {
            Apel=new gestionDuSon("music/son.au",fe);
            fenetr=fe;
             envoyeur=appelant;
              MyService=s;
 fenetr.compt++;
                    audioFormat = getAudioFormat();
                    DataLine.Info dataLineInfo =  new DataLine.Info( SourceDataLine.class,audioFormat);
   sourceDataLine = (SourceDataLine)AudioSystem.getLine(dataLineInfo);
   sourceDataLine.open(audioFormat);
                sourceDataLine.start();
                     // MyService = new ServerSocket(500);
                        clientSocket = MyService.accept();
                        in2=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                        out2=new PrintWriter(clientSocket.getOutputStream());
                        Apel.loop();
                                 input = new BufferedInputStream(clientSocket.getInputStream());	
                            out=new BufferedOutputStream(clientSocket.getOutputStream());
                  
                       fenetr.fen.arreter.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                  
                        signalEndapel(fenetr.pseud,appelant);
                        
                    
                               endApel();
                    
                            
                    }
                }); 
                   
                                
                         JOptionPane jop = new JOptionPane();	
 
             fenetr.infoApel.setText("Appel de "+appelant);
             fenetr.haut.add(fenetr.ApelLabel,BorderLayout.SOUTH);
 fenetr.haut.repaint();
 fenetr.accepter.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    initApel();
               Thread t=new Thread(new accept());
               t.start();
               
                }
            }); 
 
 fenetr.rejeter.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                       
            Apel.stop();
                                
           clientSocket.close();
           MyService.close();
           fenetr.haut.remove(fenetr.ApelLabel); 
              fenetr.haut.repaint();
              fenetr.compteur=1;
                    } catch (IOException ex) {
                         sourceDataLine.close();
                        targetDataLine.close();
                      //  endApel();
                    }
                }
            });
        } catch (IOException ex) {
           
        }

         
	}
	 private AudioFormat getAudioFormat(){
		    float sampleRate = 8000.0F;		  
		    int sampleSizeInBits = 16;		   
		    int channels = 1;		    
		    boolean signed = true;		    
		    boolean bigEndian = false;		 
		    return new AudioFormat(sampleRate,sampleSizeInBits,channels,signed,bigEndian);
		  }
         private void captureAudio() {
		try {
			
			Mixer.Info[] mixerInfo = AudioSystem.getMixerInfo();
			System.out.println("Available mixers:");
			for (int cnt = 0; cnt < mixerInfo.length; cnt++) {
				System.out.println(mixerInfo[cnt].getName());
			}
			audioFormat = getAudioFormat();

			DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);

			Mixer mixer = AudioSystem.getMixer(mixerInfo[3]);
System.out.println(mixer.isOpen());
System.out.println(mixer.isLineSupported(dataLineInfo));
			targetDataLine = (TargetDataLine) mixer.getLine(dataLineInfo);

			targetDataLine.open(audioFormat);
			targetDataLine.start();
//System.out.println(targetDataLine.isOpen());
			Thread captureThread = new CaptureThread();
			captureThread.start();		
		} catch (Exception e) {
            
		}
                        
	}
	
	class CaptureThread extends Thread {

		byte tempBuffer[] = new byte[10000];

        @Override
		public void run() {			
			try {
				while (true) {
					int cnt = targetDataLine.read(tempBuffer, 0,
							tempBuffer.length);
					out.write(tempBuffer);				
				}
                                
				
			} catch (Exception e) {
                
				
			}
		}
	}
class accept implements Runnable
 {

                @Override
                public void run() {
                         try {
                        Apel.stop();
                        fenetr.haut.remove(fenetr.ApelLabel); 
              fenetr.haut.repaint();
      //  fen=new fenetreApel("Discussion avec "+appelant);
    //fen.jj.setText("Appel en cours");
    //fen.start(fen); 
   /* fen.arreter.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                fen.jj.setText("Appel Terminer");
                               sourceDataLine.close();
                            targetDataLine.close();
                                clientSocket.close();
                                MyService.close();
                                fen.setVisible(false);
                            } catch (IOException ex) {
                                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
        
    });*/
    fenetr.fen.partage.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            
                           
                           fenetr.outp.println("par"+envoyeur+"~"+fenetr.pseud);
                           fenetr.outp.flush();
                            fenetr.fen.partage.setEnabled(false);  
                           
                        }

    });
    /*fen.addWindowListener(new WindowAdapter() {
                @Override
                            public void windowClosing(WindowEvent e) {
                    try {
                         fen.jj.setText("Appel Terminer");
                               sourceDataLine.close();
                            targetDataLine.close();
                                clientSocket.close();
                                MyService.close();
                                fen.setVisible(false);
                    } catch (IOException ex) {
                        Logger.getLogger(ClientPhoto.class.getName()).log(Level.SEVERE, null, ex);
                    }
                  
                            }
                    });*/
                            captureAudio();
                           
                            
                            while(input.read(tempBuffer)!=-1){			
                                    sourceDataLine.write(tempBuffer,0,10000);
                            }
                           
                    } catch (IOException ex) {
                        sourceDataLine.stop();
                        targetDataLine.stop();
                       
                    }
                }
     
 }
 public  void initApel()
      {
          fenetr.fen.commence=true;
   //  fenetr.fen.c.compt=0;
          try {
                                   
                    boolean flag=false;
                     
                                 for(int i=0;i<fenetr.fenetrePrivee.size();i++)
            {
                priveeMessage Temp=(priveeMessage)fenetr.fenetrePrivee.get(i);
                if(Temp.pseudo.equals(envoyeur))
                {
    
                    fenetr.onglet.setSelectedComponent(fenetr.onglet.getComponentAt(fenetr.onglet.indexOfComponent(Temp)));
                    Temp.centre.add(Temp.est,BorderLayout.EAST);
                     Temp.est.repaint();
                    flag=true;
                    break;
                }
            }
           if(!flag)
           {
            
               
                fenetr.fenetrePrivee.add(new priveeMessage(envoyeur," ",fenetr.socket,fenetr.pseud,fenetr.myself,fenetr.fen));
            priveeMessage Temp2=(priveeMessage)fenetr.fenetrePrivee.get(fenetr.fenetrePrivee.size()-1);
                fenetr.onglet.add(envoyeur,Temp2);            
                    fenetr.onglet.setSelectedComponent(fenetr.onglet.getComponentAt(fenetr.onglet.indexOfComponent(Temp2))); 
                    Temp2.centre.add(Temp2.est,BorderLayout.EAST);
                 
           
           }
                                   
                                   
                } catch (IOException ex) {
                  
                                JOptionPane.showMessageDialog(null, "Client introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);			
                }
      }
 public void endApel()
 {
     System.out.println("dans sender 1"+fenetr.compteur);
      fenetr.compteur=1;
     if(fenetr.fen!=null)
     {
     fenetr.fen.commence=false;
     if(fenetr.fen.c!=null)
     fenetr.fen.c.compt=0;
     }
        try {
            if(sourceDataLine!=null)
            sourceDataLine.close();
            if(targetDataLine!=null)
                targetDataLine.close();
                clientSocket.close();
                    MyService.close();
                 clientSocket.close();
               
                     for(int i=0;i<fenetr.fenetrePrivee.size();i++)
                        {
                            priveeMessage Temp=(priveeMessage)fenetr.fenetrePrivee.get(i);
                            if(Temp.pseudo.equals(envoyeur))
                            {
                
                                Temp.centre.remove(Temp.est);
                                Temp.centre.repaint();
                                
                                break;
                            }
                        }
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
        
 }
 public void signalEndapel(String monpseudo,String destinataire)
 {
     fenetr.outp.println("fin"+monpseudo+"~"+destinataire);
                     fenetr.outp.flush();
 }
}
