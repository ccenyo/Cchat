/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client;

import cchat1.Client.Apel.Apelrecu;
import cchat1.Client.Apel.recuApel;
import cchat1.Client.Capture.ClientVideo;
import cchat1.Client.Capture.EnvoiProfil;
import cchat1.Client.Capture.ServeurPhotos;
import cchat1.Client.Capture.receptionProfil;
import cchat1.Client.FenetresPrincipaux.MaFenetre;
import cchat1.Client.FenetresPrincipaux.connexion;
import cchat1.Client.FenetresPrincipaux.priveeMessage;
import cchat1.Client.Fichier.recu;
import java.awt.Color;
import java.awt.TrayIcon;
import java.io.*;
import java.net.Socket;
import java.sql.Time;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Cenyo
 */
public class reception implements Runnable{
  private   MaFenetre parent;
 private    reception parent2;
 String url;
 private       Socket socke;
 private       String pseudo;
 private       gestionDuSon SonPublic;
 private       gestionDuSon SonPrivee;
 private       ClientVideo v;
private BufferedReader in;
private ObjectInputStream in2;
private ArrayList connecte=new ArrayList();
private connexion paren;
public recuApel recu;
ServeurPhotos servP;
gestionDuSon SonPrivee2;

public reception(Socket s,MaFenetre f,connexion con,String p)
{
    
    parent2=this;
    paren=con;
    parent=f;
    socke=s;
    pseudo=p;
    SonPublic =new gestionDuSon("music/cc.au",parent);
     SonPrivee=new gestionDuSon("music/Speech Sleep.au",parent);
     SonPrivee2=new gestionDuSon("music/fichier.au",parent);
    Thread t;
    
}
        @Override
        public void run() {
            try {
                System.out.println("run reception");
                 in = new BufferedReader (new InputStreamReader (socke.getInputStream()));
               in2= new ObjectInputStream(socke.getInputStream());

                 
                                   String msg="";
                 while((msg=in.readLine())!=null)
                {
                            System.out.println("le message"+msg);
                        String inst=msg.substring(0, 3);
                        
                        if(inst.equals("???"))
                        {
                            JOptionPane jop = new JOptionPane();
               String nom = jop.showInputDialog(null, "Ce Groupe est privée \n Veuillez entrer le mot de passe", "Authentification", JOptionPane.QUESTION_MESSAGE);
               System.out.println(nom);
               if(nom!=null)
               {
                   System.out.println("envoie du nom");
                   parent.outp.println(msg+"*"+nom);
                   parent.outp.flush();
               }
                        }
                        if(inst.equalsIgnoreCase("IPA"))
                        {
                           Thread tht=new Thread(new receptionProfil(msg.substring(msg.indexOf("~")+1,msg.indexOf("?")),parent,msg.substring(msg.indexOf("?")+1),msg.substring(3,msg.indexOf("~"))));
                           tht.start();
                        }
                        if(inst.equalsIgnoreCase("pin"))
                        {
                             parent.outp.println("yes"+parent.pseud);
                              parent.outp.flush();
                        }
                        if(inst.equalsIgnoreCase("IP3"))
                        {
                            Thread tht=new Thread(new EnvoiProfil(parent,msg.substring(msg.indexOf("~")+1)));
                           tht.start();
                        }
                        if(inst.equalsIgnoreCase("pub"))
                        {
                            System.out.println("-><Strong>"+msg.substring(3,msg.indexOf(":"))+"</Strong>"+msg.substring(msg.indexOf(":")));
                             if(!msg.substring(3,msg.indexOf(":")).equalsIgnoreCase(pseudo))
                        {
                            Time tt=new Time(System.currentTimeMillis());
            
           SonPublic.play();
                            receptionMessageGeneral(tt.toString()+"-><Strong>"+msg.substring(3,msg.indexOf(":"))+"</Strong>"+msg.substring(msg.indexOf(":")));
            
                            }
                             else
                             {
                                 Time tt=new Time(System.currentTimeMillis());
                                // parent.messageGeneral= parent.messageGeneral+tt.toString()+"-><Strong style=color:blue>"+msg.substring(3,msg.indexOf(":"))+"</Strong>"+msg.substring(msg.indexOf(":"))+"<br>";
                           parent.receptionMessageGeneral(tt.toString()+"-><Strong style=color:blue>"+msg.substring(3,msg.indexOf(":"))+"</Strong>"+msg.substring(msg.indexOf(":")));
                             }
                        }
                        if(inst.equalsIgnoreCase("pri"))
                        {
                            receptionMessagePrivee(msg.substring(msg.indexOf(":")+1,msg.lastIndexOf("~")),msg.substring(msg.lastIndexOf("~")+1),msg.substring(3,msg.indexOf(":")));
                        System.out.println(msg.substring(msg.indexOf(":")+1,msg.lastIndexOf("~"))); 
                        }
                        if(msg.substring(0,1).equalsIgnoreCase("["))
                        {
                           System.out.println(msg); 
                           
                           msg=msg.trim();
                           System.out.println(msg); 
      String [] s=msg.split(", ");
parent.tab2=s;
parent.tab2[0]=s[0].substring(1);
System.out.println("cest tab[0] "+parent.tab2[0]);

parent.tab2[parent.tab2.length-1]=s[s.length-1].substring(0,s[s.length-1].indexOf("]"));
             for(int i=0;i<parent.tab2.length;i++)
                 System.out.println(parent.tab2[i]); 
             parent.labelconnecte.setText(parent.tab2.length+" membres connectés :");
             parent.listc.setListData(parent.tab2); 
                        }
                        if(msg.substring(0,1).equalsIgnoreCase("]"))
                        {
                           System.out.println("trouvézzzzzzzzzzzz"); 
                           
                           msg=msg.trim();
                           System.out.println(msg); 
      String [] s=msg.split("/");
      parent.tabl=s;
parent.tabl[0]=s[0].substring(1);
System.out.println("cest pas tab[0] "+parent.tabl[0]);

//parent.tabl[parent.tabl.length-1]=s[s.length-1].substring(0,s[s.length-1].indexOf("]"));
             for(int i=0;i<parent.tabl.length;i++)
                 System.out.println(parent.tabl[i]); 
             //parent.labelconnecte.setText(parent.tabl.length+" membres connectés :");
             parent.sal.setListData(parent.tabl); 

                        }
                        if(inst.equalsIgnoreCase("ipe"))
                        {
                         //   ClientPhoto c=new ClientPhoto(msg.substring(3,msg.indexOf("~")),msg.substring(msg.indexOf("~")+1));
                        }
                         if(inst.equalsIgnoreCase("OIP"))
                        { servP=new ServeurPhotos(msg.substring(3),7777,parent);
                           Thread d=new Thread(servP);  
                           d.start();
                        }
                        
                        if(inst.equalsIgnoreCase("dec"))
                        {
                            receptionMessageGeneral("<Strong style=color:red;>"+msg.substring(3)+"</Strong><Strong> s'est déconnecté</Strong>");
                            parent.listc.setListData(parent.tab2);
                             for(int i=0;i<parent.fenetrePrivee.size();i++)
            {
                priveeMessage Temp=(priveeMessage)parent.fenetrePrivee.get(i);
                if(Temp.pseudo.equals(msg.substring(3)))
                {
                    parent.fenetrePrivee.remove(Temp);
              parent.onglet.remove(Temp); 
                }
                    
                
            }
                        }
                        
                        if(inst.equals("rec"))
                        {
                            
          Thread t=new Thread(new recu(msg,parent,parent2));
          t.start();
          System.out.println("recu demarer "+msg);
                        }

                        if(inst.equalsIgnoreCase("rea"))
                        {
                            if(parent.compteur==1)
                            {
                             recu=new recuApel(msg,parent,parent2);
                             Thread t=new Thread(recu);
                             System.out.println();
                             t.start();
                             System.out.println("dans rea "+parent.compteur);
                             parent.compteur++;
                            }
                            else
                            {
                             System.out.println(msg.substring(3,msg.indexOf(":"))+" je discute deja "+parent.pseud);                             
                             
                             parent.outp.println("fi2"+parent.pseud+"~"+msg.substring(3,msg.indexOf(":")));
                             parent.outp.flush();
                            }
                        }
                        if(inst.equalsIgnoreCase("AEC"))
                        {
                            String ipRec=msg.substring(3,msg.indexOf("~"));
                            String envoyeur=msg.substring(msg.indexOf("~")+1,msg.indexOf("?"));
                            String port=msg.substring(msg.indexOf("?")+1);
                               boolean flag=false;
                               System.out.println(ipRec);
                               System.out.println(envoyeur);
                             
                               Thread t2=new Thread(new Apelrecu(ipRec,envoyeur,parent,port));
                               t2.start();
                               
                              
                        }
                         if(inst.equalsIgnoreCase("OI2"))
                        {
                             Thread d=new Thread(new ServeurPhotos(msg.substring(3),7778,parent));  
                             
                           d.start();
                        }
                         if(inst.equalsIgnoreCase("sto"))
                        {
                            
                        }
                          if(inst.equalsIgnoreCase("ip2"))
                        {
                           v=new ClientVideo(msg.substring(3,msg.indexOf("~")),msg.substring(msg.indexOf("~")+1));
                       
                        }
                        if(inst.equalsIgnoreCase("Env"))
                        {
                            String ipRec=msg.substring(3,msg.indexOf("~"));
                            String envoyeur=msg.substring(msg.indexOf("~")+1,msg.indexOf("|")); 
                            String port=msg.substring(msg.indexOf("|")+1);
                               boolean flag=false;
                               System.out.println(ipRec);
                               System.out.println(envoyeur);
                               System.out.println(port);
        System.out.println("Env enclanché");
            for(int i=0;i<parent.fenetrePrivee.size();i++)
            {
                priveeMessage Temp=(priveeMessage)parent.fenetrePrivee.get(i);
                if(Temp.pseudo.equals(envoyeur))
                {
                    SonPrivee2.play();
                    System.out.println("envoyeur trouvé");
                 //   parent.fenetrePrivee[i].ta.append("Envoi du fichier\ndemande d'autorisation en cours\n");
                     Temp.envoiFichier(ipRec,port);
                   //  parent.fenetrePrivee[i].ta.append("Envoi Terminer\n");             
                    flag=true;
                    break;
                    
                }
            }
                           if(flag==false)
           {
            try {
                 SonPrivee2.play();
                parent.fenetrePrivee.add(new priveeMessage(envoyeur," ",parent.socket,parent.pseud,parent,parent.fen));
               priveeMessage Temp2=(priveeMessage)parent.fenetrePrivee.get(parent.fenetrePrivee.size()-1);
                Temp2.envoiFichier(ipRec,port);
            parent.onglet.add(envoyeur,Temp2);
            } catch (IOException ex) {}
           }
                
            
  
                        }
                        if(inst.equalsIgnoreCase("adm"))
                        {
                            
                            receptionMessageGeneral("<strong style=color:green;>"+msg.substring(3)+"</strong>");
                            
                        }
                         if(inst.equalsIgnoreCase("jvm"))
                        {
                            
                            receptionMessageGeneral("<strong style=color:green;>"+msg.substring(3)+"</strong>");
                            parent.outp.close();
                            in.close();
                            this.socke.close();
                        }
                        if(inst.equalsIgnoreCase("fin"))
                        {
                            System.out.println("dans fin 1 "+parent.compteur);
                            if(recu!=null)
                            {
                                if(recu.s!=null)
                            recu.s.endApel();
                            }
                            if(servP!=null)
                                servP.end();
                            parent.haut.remove(parent.ApelLabel); 
                            if(recu!=null)
                            {
                                if(recu.s!=null)
                                recu.s.Apel.stop();
                            }
                           // parent.compteur=1;
                            System.out.println("dans fin 2 "+parent.compteur);
                        }
                         if(inst.equalsIgnoreCase("fi2"))
                        {
                             boolean flag=false;
      
           System.out.println("fi2 demarrer recherche de "+msg.substring(3));
            for(int i=0;i<parent.fenetrePrivee.size();i++)
            {
                  System.out.println("Temp demarrer "+msg.substring(3));
                 priveeMessage Temp=(priveeMessage)parent.fenetrePrivee.get(i);
              
                 if(Temp.pseudo.equals(msg.substring(3)))
                 {
                    
                  Temp.centre.remove(Temp.est);
                           Temp.centre.repaint();
                           parent.compteur=1;
                 }
                     
                    
                
                
            }
           
                        }
                        if(inst.equalsIgnoreCase("con"))
                        {
                            receptionMessageGeneral("<strong style=color:white;background-color:black;>"+msg.substring(3)+"</strong>");
                        }
                        if(inst.equalsIgnoreCase("ex~"))
                        {
                            System.out.println("ce pseudo existe deja sur Cchat");
                            parent.outp.println("qui"+pseudo);
              parent.outp.flush();
              JOptionPane jop = new JOptionPane();
               String nom = jop.showInputDialog(null, "Ce pseudo existe deja changer le !", "Duplication de pseudo !", JOptionPane.QUESTION_MESSAGE);
                 parent.setVisible(false);
               //parent.pseud=nom;
                 if(!nom.equals(""))
                 paren.validation(nom, paren.text1.getText(),5050);
                 else
                     System.exit(0);
                        }
                        }
                   

                
            } catch (IOException ex) {
               JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "Le serveur a été fermé", "Erreur", JOptionPane.ERROR_MESSAGE);
                                parent.setVisible(false);
                                connexion c=new connexion();
            }
        }
          void  receptionMessageGeneral(String message)
    {
        
        try {
            url = (new File(".")).getCanonicalPath();
       System.out.println(url);
          String result=parent.emo.remplacer(message);
         
             
            parent.messageGeneral= parent.messageGeneral+result+"<br>";
             System.out.println(result);
          parent.ChatBox.setText(parent.messageGeneral);
           parent.ChatBox.setCaretPosition((int)(1*parent.ChatBox.getDocument().getLength()));
         

        } catch (IOException ex) {
            Logger.getLogger(reception.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  public void receptionMessagePrivee(String message,String moi,String envoyeur)
          
  {
      if(message.equals("|"))
      {
            for(int i=0;i< parent.fenetrePrivee.size();i++)
            {
                 priveeMessage Temp=(priveeMessage)parent.fenetrePrivee.get(i);
                if( Temp.pseudo.equals(envoyeur))
                {
                    
                  new Thread(new ecrir(Temp)).start();
                       
                    
                }
                
            }
      }
      else
      {
      boolean flag=false;
        SonPrivee.play();
           // priv.append(message+"\n");
           
            for(int i=0;i< parent.fenetrePrivee.size();i++)
            {
                 priveeMessage Temp=(priveeMessage)parent.fenetrePrivee.get(i);
                if( Temp.pseudo.equals(envoyeur))
                {
                    parent.tray.displayMessage("Message privée", envoyeur+" vous a envoyer un message", TrayIcon.MessageType.INFO);
                     Temp.MessageGeneral=Temp.MessageGeneral+"<strong style=color:green;>"+envoyeur+"</strong> :"+message+"<br>";
                     Temp.ta.setText(parent.emo.remplacer(Temp.MessageGeneral));
                     Temp.ta.setCaretPosition((int)(1*Temp.ta.getDocument().getLength()));
                    if(parent.onglet.getSelectedComponent()!=parent.onglet.getComponentAt(parent.onglet.indexOfComponent(Temp)))
                    {Temp.compteurMessage++;
                    
                     parent.onglet.setTitleAt(parent.onglet.indexOfComponent(Temp), envoyeur+"("+Temp.compteurMessage+")");
                     parent.onglet.setBackgroundAt(parent.onglet.indexOfComponent(Temp), Color.darkGray);
                    }
                     flag=true;
                    break;
                    
                }
                
            }
           if(flag==false)
           {
               parent.tray.displayMessage("Message privée", envoyeur+" vous a envoyer un message", TrayIcon.MessageType.INFO);
            try {
                 parent.fenetrePrivee.add(new priveeMessage(envoyeur,message, parent.socket,moi,parent,parent.fen));
                 priveeMessage Temp2=(priveeMessage)parent.fenetrePrivee.get(parent.fenetrePrivee.size()-1);
                 Temp2.MessageGeneral=Temp2.MessageGeneral+"<strong style=color:green;>"+envoyeur+"</strong> :"+message+"<br>";
                     Temp2.ta.setText(parent.emo.remplacer(Temp2.MessageGeneral));
                     Temp2.ta.setCaretPosition((int)(1*Temp2.ta.getDocument().getLength()));
                    Temp2.compteurMessage++;
             parent.onglet.add(envoyeur+"("+Temp2.compteurMessage+")",Temp2);
             parent.onglet.setBackgroundAt(parent.onglet.indexOfComponent(Temp2), Color.darkGray);
           
            } catch (IOException ex) {JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "Client introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);			}
           }
                                
      
      }
  }    
 
 class ecrir implements Runnable
 {
     priveeMessage Temp;
 ecrir(priveeMessage p)
 {
     Temp=p;
 }
        @Override
        public void run() {
            try {
                Temp.controle.setText("En train d'écrire.......");
             Thread.sleep(2000); 
         Temp.controle.setText(""); 
            Temp.nord.repaint();
            } catch (InterruptedException ex) {
                Logger.getLogger(reception.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
     
 }
         
}
