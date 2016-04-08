/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.FenetresPrincipaux;

import cchat1.Client.Apel.fenetreApel;
import cchat1.Client.Emoticone;
import cchat1.Client.Fichier.EnvoiAndroid;
import cchat1.Client.Fichier.EnvoiFichier;
import cchat1.Client.Sauvegarde.sauvegardeMessage;
import cchat1.Client.gestionDuSon;
import cchat1.Client.reception;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
public class priveeMessage extends JPanel{
    public Image icc;
  public  String MessageGeneral="";
 public   File ff;
 Emoticone emo=new Emoticone();
 public JLabel controle=new JLabel();
 String url;
 JButton sm=new JButton();
 
  public  priveeMessage prive;
  
 public   JPanel panO =new JPanel(), cont =new JPanel();
 private   JTextField text =new JTextField();
 public   JEditorPane ta=new JEditorPane();
 public   JScrollPane pa=new JScrollPane(ta);
 private   JLabel mes= new JLabel("Message"), ps= new JLabel("Pseudo :"),psdo=new JLabel(); 
 
 private   JButton bt=new JButton("ENVOYER");
 private   JMenuBar menu = new JMenuBar();
  private  JMenu Soumenu = new JMenu("Fichier");
 private   JButton sm1=new JButton("Sauvegarder");
private    JButton sm2 = new JButton("Envoyer fichier");
 private   JButton sm4 = new JButton("Effacer ");
//private    JButton sm5 = new JButton("partager photos");
private    JButton sm3 = new JButton("Quitter");
public   JPanel cent=new JPanel(new BorderLayout());
public   JPanel cent2=new JPanel(new BorderLayout());
public   JSplitPane split=new JSplitPane();
 private JPanel boutons =new JPanel(new GridLayout(3,2)); 


 private   PrintWriter out;
private    BufferedReader in;
  public  String pseudo="";
  private  String monpseudo="";
  //  boolean ff;
 private   String message="";
private    priveeMessage pri;
private JPanel sud=new JPanel(new BorderLayout());
public JPanel centre=new JPanel(new BorderLayout());
public JPanel nord=new JPanel(new BorderLayout());
public JPanel est=new JPanel(new BorderLayout());
public MaFenetre moi;
public fenetreApel call;
public int compteurMessage=0;
public JLabel imageprofil;
boolean smil=true;
gestionDuSon Son;
public Vector vec=new Vector();
public JTabbedPane Tab=new JTabbedPane();
JPanel smiliesPan=new JPanel(new BorderLayout());
JPanel smiliesPane=new JPanel(new GridLayout(10,10));
JScrollPane spane=new JScrollPane(smiliesPane);
       public priveeMessage(String pseud,String mess,Socket s,String pseudoE,MaFenetre fn,fenetreApel fenApp) throws IOException{  
       // this.setTitle("Discussion avec "+pseud);
           spane.setPreferredSize(new Dimension(500,100));
            smiliesPane.setPreferredSize(new Dimension(300,600));
           text.setFocusable(true);
            Son=new gestionDuSon("music/envoimsg.au",new JFrame());
           moi=fn;
           call=fenApp;
           this.setLayout(new BorderLayout());
        pri=this;
        sm1.setIcon(new ImageIcon("image/save.png")); 
        sm2.setIcon(new ImageIcon("image/send.png")); 
        sm3.setIcon(new ImageIcon("image/close.png")); 
       // sm4.setIcon(new ImageIcon("image/addGroup.png")); 
        bt.setIcon(new ImageIcon("image/msg.png")); 
        sm.setIcon(new ImageIcon("image/Smiley.png")); 
        sm4.setIcon(new ImageIcon("image/effacer.png"));
        this.setSize(330,515);
ta.setEditorKit(new HTMLEditorKit()); 
nord.setBackground(Color.GRAY);
sud.setBackground(Color.GRAY); 
       // this.setLocationRelativeTo(null);
       // this.setResizable(false);
        // this.setAlwaysOnTop(true);
emo.setPanel(smiliesPane);
emo.setListener(text);
 BufferedImage im=null;
        try {
            im = ImageIO.read(new File("image/defaultIm.png"));
        } catch (IOException ex) {
            Logger.getLogger(MaFenetre.class.getName()).log(Level.SEVERE, null, ex);
        }
        Thread tt=new Thread(new charger());
        tt.start();
        Image i=im;
        i=moi.scale(i, 250, 250);
        call.start(call);
est.add(call.photo,BorderLayout.CENTER);
est.add(call.sud,BorderLayout.SOUTH);
est.add(call.jj,BorderLayout.NORTH);
call.photo.setIcon(new ImageIcon(i)); 
        prive=this;
        pseudo=pseud;
        psdo.setText(pseud);
        monpseudo=pseudoE;
        out=new PrintWriter(s.getOutputStream());
        in=new BufferedReader(new InputStreamReader(s.getInputStream()));
       // mes.setBounds(5,415,60, 15);
        mes.setFont(new Font("Lucida Sans",Font.BOLD,12));
       // text.setBounds(5,430,230,25);
   
        text.setBorder(BorderFactory.createLineBorder(Color.blue));
        bt.setPreferredSize(new Dimension(90, 31)); 
        bt.setBorder(BorderFactory.createLineBorder(Color.blue));
        bt.setFont(new Font("Tahoma",Font.BOLD,8));
        //pa.setBounds(10,60,300,330);
        pa.setBorder(BorderFactory.createLineBorder(Color.blue));
       // ps.setBounds(80,7,70,15);
        ps.setFont(new Font("Lucida Sans",Font.BOLD,10));
         imageprofil = new JLabel(new ImageIcon("image/defaultIm.png"));
        
        //imageprofil.setBounds(10,3,55,50);
      //  psdo.setBounds(80,25,110,20);
        psdo.setBorder(BorderFactory.createLineBorder(Color.magenta));
        sud.add(mes,BorderLayout.WEST);
        sud.add(text,BorderLayout.CENTER);
        text.setPreferredSize(new Dimension(660, 31)); 
        JPanel sudEst=new JPanel(new GridLayout(1,2));
        sudEst.add(sm);
        sudEst.add(bt);
        sudEst.setBackground(Color.GRAY);
        sud.add(sudEst,BorderLayout.EAST);
        
        this.add(sud,BorderLayout.SOUTH);
        smiliesPan.add(pa);
        centre.add(smiliesPan);
        this.add(centre,BorderLayout.CENTER);
       
        boutons.add(sm1);
        boutons.add(sm2);
        boutons.add(sm3);
        boutons.add(sm4);
//        boutons.add(sm5);
        cont.setBackground(Color.GRAY);
        Tab.setBackground(Color.GRAY);
        Tab.setVisible(false);
        imageprofil.setBackground(Color.GRAY);
        controle.setBackground(Color.GRAY);
        boutons.setBackground(Color.GRAY);
        
        nord.add(boutons,BorderLayout.EAST);
        nord.add(imageprofil,BorderLayout.WEST);  
        nord.add(Tab,BorderLayout.CENTER);
        nord.add(controle,BorderLayout.SOUTH);
        this.add(nord,BorderLayout.NORTH);
        sm.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(smil==true)
                {
                    smiliesPan.add(spane,BorderLayout.SOUTH);
                    smil=false;
                    smiliesPan.repaint();
                }
                else
                {
                    smiliesPan.remove(spane);
                    smil=true;
                     smiliesPan.repaint();
                }
            }
        }); 
        ta.setEditable(false);
        
        //this.setContentPane(cont);
                        text.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }
            @Override
            public void keyPressed(KeyEvent e) {
            }

            @Override
            public void keyReleased(KeyEvent e) {
               if( e.getKeyCode()==10)
               {
                   if(!text.getText().equals("")&&!text.getText().equals(" ")){
                   message=text.getText();
                        
               EnvoiMessage();
               text.setText("");

                }}
               
          out.println("pri"+monpseudo+":|"+"~"+pseudo);
              out.flush();
               
               }
            
        });
        /*addWindowListener(new WindowAdapter() {
            @Override
			public void windowClosing(WindowEvent e) {				
				setVisible(false);
			}
		});*/
      //  this.Soumenu.add(sm1);
       
        bt.addActionListener(new ActionListener()
        {

            @Override
            public void actionPerformed(ActionEvent e) {
              
                message=text.getText();
                if(!message.equals(""))
                {
               EnvoiMessage();
               text.setText("");
                }
            }
            
        });
        sm1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                sauvegardeMessage s=new sauvegardeMessage(MessageGeneral);
            }
        });
        
        sm2.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                
                
                FileDialog fl = new FileDialog(moi, "sélectionner le fichier");
                     
                      fl.show();
                       ff= new File(fl.getDirectory() + "/" + fl.getFile());
                   System.out.println("fichier creer "+fl.getDirectory() + "/" + fl.getFile());    
                      if(ff.exists())
                      {
                     int option = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment envoyer le fichier:\n"+ff.getName()+"\n taille:"+ff.length(),"Confirmation d'envoie" , JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
if(option == JOptionPane.OK_OPTION)
{
    if(pseudo.contains(" "))
    {
    if(pseudo.substring(pseudo.lastIndexOf(" ")+1).equals("(Android)"))
    {
                                try {
                                    new Thread(new EnvoiAndroid(pseudo,moi,ff)).start();
                                } catch (UnknownHostException ex) {
                                    Logger.getLogger(priveeMessage.class.getName()).log(Level.SEVERE, null, ex);
                                }
    }
     }
    else
    {
         out.println("tra"+monpseudo+"~"+pseudo);
                     out.flush();
                      System.out.println("tra envoyer "+monpseudo+"~"+pseudo); 
    }
                       
}
                      }
                
            }
        });
        
    
        sm3.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(moi.onglet.getSelectedIndex()!=0)
                {
               
                   
                moi.fenetrePrivee.remove((priveeMessage)moi.onglet.getSelectedComponent());
               moi.onglet.remove(moi.onglet.getSelectedComponent()); 
               
             
              
              
            }
            }
        });
         sm4.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                JOptionPane confirmation=new JOptionPane();
               int index=confirmation.showConfirmDialog(null, "etes-vous sur de vouloire effacer?","Confirmation",JOptionPane.YES_NO_OPTION);
               if(index==confirmation.OK_OPTION)
               {
               MessageGeneral="";
               ta.setText(MessageGeneral); 
               }
            }
        });
/*          sm5.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                 out.println("pho"+monpseudo+"~"+pseudo);
                     out.flush();
            }
        });*/
        
 

        
         }
    
    
    public void EnvoiMessage()
     {
         Son.play();
         MessageGeneral=MessageGeneral+"<strong>Vous :</strong>"+message+"<br>";
         ta.setText(emo.remplacer(MessageGeneral)); 
         //pa.setWheelScrollingEnabled(true);
         
          out.println("pri"+monpseudo+":"+message+"~"+pseudo);
              out.flush();
              message="";
              
            
     }
     public void envoiFichier(String ipRec,String port)
     {
         System.out.println("Thread envoiFichier "+pseudo);    
        
        Thread t=new Thread( new EnvoiFichier(ipRec,ff,pri,port));
       t.start();
       System.out.println("Thread envoiFichier "+ff.getName());    
     }
 class charger implements Runnable
 {

        @Override
        public void run() {
            while(true)
            {
                if(icc!=null)
                {
                      for(int i=0;i< moi.fenetrePrivee.size();i++)
            {
                 priveeMessage Temp=(priveeMessage)moi.fenetrePrivee.get(i);
                if( Temp.pseudo.equals(pseudo))
                {
                    moi.onglet.setIconAt(moi.onglet.indexOfComponent(Temp), new ImageIcon(icc));
                    break;
                    
                }
              
                }
            }
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    Logger.getLogger(priveeMessage.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
     
 }
}
}
