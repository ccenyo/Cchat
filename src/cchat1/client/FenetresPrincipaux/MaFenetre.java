package cchat1.Client.FenetresPrincipaux;

import cchat1.Client.*;
import cchat1.Client.Apel.fenetreApel;
import cchat1.Client.Capture.EnvoiProfil;
import cchat1.Client.Sauvegarde.decrypte;
import cchat1.Client.Sauvegarde.sauvegardeMessage;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.html.HTMLEditorKit;
public final  class MaFenetre extends JFrame{
    public  Toolkit kit = Toolkit.getDefaultToolkit();
    public int compteur=1;
    public Image icone = kit.getImage(getClass().getResource("meet_me.gif"));
   public TrayIcon tray;
   public Emoticone emo=new Emoticone();
    public int compt=0;
    public JButton smilies=new JButton(new ImageIcon("image/Smiley.png"));
    private String nomSalon="";
    private JPanel panneauOnglet=new JPanel(new BorderLayout()); 
    //déclarations pour la sauvegarde
   public fenetreApel fen=new fenetreApel();
  public JButton creer=new JButton("Créer Groupe");
  public JPanel sudList=new JPanel(new GridLayout(1,2));
    public JButton accepter=new JButton("Accepter");
  public JButton rejeter=new JButton("Rejeter");
  public JPanel  sud2=new JPanel(new GridLayout(1,2));
  
  boolean smil=true;
   private connexion paren;
  private  Properties parDefaut = new Properties();
  private  Properties réglages = new Properties(parDefaut);
    
    private String salonActuel="General";
    
  public Option o;
   private File fd=new File("image/defaultIm.jpg");
    
 public JPanel smiliesPane=new JPanel(new GridLayout(10,10));
 public JScrollPane spane=new JScrollPane(smiliesPane);
   private   Image i;
      
   private   JSplitPane split;
      
      
   public   int compteurFenetre;
   public JList listc=new JList();
    
    //Ensemble des socket
   public   Socket socket;
      
      
   private   Thread t,t2; 
      
      
     // serveur Serv;
private FileOutputStream fichiers;
public PrintWriter outp;


private JPanel pageConnecte =new JPanel();


   //tous les string
     public String messager=" ", msg="",pseud="";     
      private String lien=new String();
        public String messageGeneral="";
    public String [] tab2;
    public String [] tabl;
    private String dfd=réglages.getProperty("image");
    //tous les JLabel
 
    private JLabel me=new JLabel("message: ");
   private  JLabel mess=new JLabel("messages");
   public  JLabel pseudo = new JLabel();
     public JLabel labelconnecte;
   public   JLabel icon2=new JLabel(new ImageIcon(fd.getAbsolutePath()));
    // ImageIO.read(new File(réglages.getProperty("image")))
   private  JLabel icon=new JLabel(new ImageIcon("image/meetme.png"));
   public  JLabel AfficheTemps=new JLabel();
   private  JLabel salonActu=new JLabel("salon actuel : General");
     
  public JList sal=new JList();
  public JTabbedPane onglet=new JTabbedPane();  
  
  
     /** creation des instances pour la fenetre */
    public JEditorPane ChatBox=new JEditorPane();//le JEditorPane permet de créé un champ pour les balises html et tous types de texte y compris images
    
    public JScrollPane myChatHistory=new JScrollPane(ChatBox);//Le JScrollPane est un conteneur qui prend en sont sein le JTextArea et cré des bar de defilements
   private JScrollPane listeconnecte=new JScrollPane(listc);
   private  JScrollPane listesal=new JScrollPane(sal);
    
    private JButton Send = new JButton("Envoyer");//le JButton cré des bouton
   private JButton priv=new JButton("MP");
   private     JButton Apel=new JButton("Appeler");
        
        
    private JTextField messages=new JTextField();//pour écrir les messages
    
    
    
    private JMenuBar menuBar = new JMenuBar();//pour la bar de menu
    private JMenu fichier = new JMenu("Fichier");
	private JMenu edition = new JMenu("Edition");
        private JMenu option = new JMenu("Option");
        private JMenu aide = new JMenu("aide");
        private JMenuItem quit = new JMenuItem("Quitter");
	private JMenuItem sauvegarde = new JMenuItem("Sauvegarder la conversation");
	private JMenuItem effacer = new JMenuItem("effacer la conversation");
	private JMenuItem options = new JMenuItem("Options Avancée");
        private JMenuItem aides = new JMenuItem("A propos"); 
        private JMenuItem decrypte = new JMenuItem("ouvrir une conversation");
        public JButton csal=new JButton("changer de salon");
  public     MaFenetre myself;
        
      public JLabel infoApel=new JLabel();  
  public      ArrayList fenetrePrivee=new ArrayList();
  public  JPanel haut=new JPanel(new BorderLayout());     
        public JPanel ApelLabel=new JPanel(new BorderLayout());

private Font police = new Font("Arial", Font.ROMAN_BASELINE, 12);
JPanel pn=new JPanel(new BorderLayout()); 

  
    //la methode pour la fenetre le myFrame
    public MaFenetre(String ps,Socket s,connexion con){
        try {
            myself=this;
            BufferedImage photo = ImageIO.read(new File("image/meet_me.png"));
            tray = new TrayIcon(photo, "Meet me");
            tray.setImageAutoSize(true);
            
            try {
                SystemTray.getSystemTray().add(tray);
            } catch (AWTException ex) {
                Logger.getLogger(MaFenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
            o=new Option(myself);
             emo.setPanel(smiliesPane); 
            try {
               option.setIcon(new ImageIcon("image/configuration.png"));
               options.setIcon(new ImageIcon("image/configuration.png"));
               
               aide.setIcon(new ImageIcon("image/help.png")); 
               aides.setIcon(new ImageIcon("image/help.png"));
                spane.setPreferredSize(new Dimension(500,100));
                smiliesPane.setPreferredSize(new Dimension(300,600));
                messages.setFocusable(true);
                pseud=ps;
                socket=s; 
                paren=con;
                pn.add(myChatHistory);           
                panneauOnglet.add(pn,BorderLayout.CENTER);
                onglet.setBackground(Color.red); 
                accepter.setPreferredSize(new Dimension(100,20));
                rejeter.setPreferredSize(new Dimension(100,20));
                sud2.add(accepter);
             sud2.add(rejeter);
            
            emo.setListener(messages); 
            
           myChatHistory.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        myChatHistory.setViewportBorder(null);
       myChatHistory.setAutoscrolls(true);
       myChatHistory.setMinimumSize(new Dimension(10, 10));
       myChatHistory.setOpaque(false);
       myChatHistory.setPreferredSize(new Dimension(50, 50));
       myChatHistory.setVerifyInputWhenFocusTarget(true);
       myChatHistory.setWheelScrollingEnabled(true); 
      //myChatHistory.setColumnHeaderView(ChatBox);
       
         
            
          
               
            compteurFenetre=0;
     gestionDuSon acceuil=new gestionDuSon("music/tada.au",this);
     acceuil.play();
            ChatBox.setFont(police);
            ChatBox.setEditorKit(new HTMLEditorKit() ); 
            listc.setSelectionBackground(Color.lightGray) ;
            listc.setSelectionForeground(Color.BLACK); 
             JPanel cp=new JPanel();//Déclaration du conteneur
            
             Thread ttt=new Thread(new Temps(this));
             ttt.start();
             
             Image icone = Toolkit.getDefaultToolkit().getImage("image/meet_me.png");
    this.setIconImage(icone);
    //this.setModalExclusionType(Dialog.ModalExclusionType.NO_EXCLUDE); 
             cp.setLayout(new BorderLayout());
            try {
                outp = new PrintWriter(socket.getOutputStream());
            } catch (IOException ex) {
                Logger.getLogger(MaFenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
              parDefaut.put("image", "image/defaultIm.jpg");
              parDefaut.put("taille", "800*600");
              parDefaut.put("IP", "127.0.0.1");
              parDefaut.put("PORT", "8080");
    Apel.setIcon(new ImageIcon("image/Apel.png")); 
    priv.setIcon(new ImageIcon("image/msg.png")); 
    Send.setIcon(new ImageIcon("image/msg.png")); 
    creer.setIcon(new ImageIcon("image/addGroup.png")); 
    fichier.setIcon(new ImageIcon("image/fichier.png")); 
    sauvegarde.setIcon(new ImageIcon("image/save.png")); 
    edition.setIcon(new ImageIcon("image/edition.png")); 
    quit.setIcon(new ImageIcon("image/close.png"));
    decrypte.setIcon(new ImageIcon("image/ouvrir.png"));
    effacer.setIcon(new ImageIcon("image/effacer.png"));
                    this.fichier.add(sauvegarde);
                    this.fichier.add(decrypte);
                    this.fichier.add(quit);
                    
                    this.edition.add(effacer);
                    this.option.add(options);
                    this.aide.add(aides);
      BufferedImage im=null;
            try {
                im = ImageIO.read(new File("image/defaultIm.png"));
            } catch (IOException ex) {
                Logger.getLogger(MaFenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
                      //  i=im;
                      //  i=scale(i,120,110);
                     //   icon2.setIcon(new ImageIcon(i));
                 
                    lesListener();
           
                

                   
               //     icon2.setToolTipText("cliquer pour changer l'image");
                
                    
                addWindowListener(new WindowAdapter() {
                @Override
                            public void windowClosing(WindowEvent e) {
                   
              
                                    outp.println("qui"+pseud);
                  outp.flush();
                   System.exit(0);
               
                            }
                    });
                labelconnecte=new JLabel("Liste des connectés");
                
                JPanel milieu=new JPanel(new BorderLayout());
                JPanel bas=new JPanel(new BorderLayout());
             //   JLabel ho=new JLabel(new ImageIcon("image/cchat.png"));
                ApelLabel.add(infoApel,BorderLayout.CENTER);
                ApelLabel.add(sud2,BorderLayout.EAST);
             //   haut.add(ApelLabel,BorderLayout.SOUTH);
               // haut.add(ho,BorderLayout.CENTER);
                haut.add(icon,BorderLayout.EAST);
                JPanel nel=new JPanel(new BorderLayout());
                nel.add(icon2,BorderLayout.CENTER);
                pseudo.setFont(new Font("calibri",Font.BOLD,20)); 
                AfficheTemps.setFont(new Font("calibri",Font.BOLD,15));
                JPanel pse=new JPanel(new GridLayout(2,1));
                pse.add(pseudo);
                pse.add(AfficheTemps);
                JPanel pse2=new JPanel(new BorderLayout());
                pse2.add(pse,BorderLayout.NORTH);
                pse2.setBackground(Color.gray);
                nel.add(pse2,BorderLayout.EAST);
                 haut.add(nel,BorderLayout.WEST);
            //    haut.add(AfficheTemps,BorderLayout.SOUTH);  
                bas.add(me,BorderLayout.WEST);
                messages.setPreferredSize(new Dimension(660, 31)); 
                bas.add(messages,BorderLayout.CENTER);
                JPanel basEst=new JPanel(new GridLayout(1,2));
                basEst.add(smilies);
                basEst.add(Send);
                Send.setPreferredSize(new Dimension(90, 31)); 
                bas.add(basEst,BorderLayout.EAST);
                
                
                
                listeconnecte.setPreferredSize(new Dimension(200,330)); 
                JPanel listePan=new JPanel(new BorderLayout());
                JPanel listeSalon=new JPanel(new BorderLayout());
                JSplitPane pp=new JSplitPane();
                pp.setOrientation(JSplitPane.VERTICAL_SPLIT); 
                pp.setDividerLocation(170); 
                pp.setOneTouchExpandable(true);
                listeSalon.add(listesal,BorderLayout.CENTER);
                sudList.add(csal);
                sudList.add(creer);
                listeSalon.add(sudList,BorderLayout.SOUTH);
                listeSalon.add(salonActu,BorderLayout.NORTH);
                listePan.add(listeconnecte,BorderLayout.CENTER);
                listePan.add(labelconnecte,BorderLayout.NORTH);
                JPanel boutonPan=new JPanel(new FlowLayout());
                priv.setPreferredSize(new Dimension(100, 31));
                        Apel.setPreferredSize(new Dimension(100, 31));
                        boutonPan.add(priv);
                        boutonPan.add(Apel);
                listePan.add(boutonPan,BorderLayout.SOUTH);
                
               pp.setTopComponent(listePan);
                pp.setBottomComponent(listeSalon);
                milieu.add(pp,BorderLayout.EAST);
                cp.add(haut,BorderLayout.NORTH);
              //  cp.add(bas,BorderLayout.SOUTH);
                
                panneauOnglet.add(bas,BorderLayout.SOUTH);
             onglet.add("General",panneauOnglet);
              milieu.add(onglet,BorderLayout.CENTER);
              cp.add(milieu,BorderLayout.CENTER);
              
                haut.setBackground(Color.GRAY); 
                milieu.setBackground(Color.GRAY); 
                bas.setBackground(Color.GRAY); 
                pse.setBackground(Color.GRAY); 
                nel.setBackground(Color.GRAY); 
                listePan.setBackground(Color.GRAY); 
                listeSalon.setBackground(Color.GRAY); 
                boutonPan.setBackground(Color.GRAY);
                Apel.setEnabled(false); 
                        priv.setEnabled(false); 
                csal.setEnabled(false);
    pseudo.setText(pseud);
 //   pseudo.setBounds(140,10, 200,15);

             Send.setBackground(Color.white);
           //  Send.setBorder(BorderFactory.createLoweredBevelBorder());
           // Send.setBorder(BorderFactory.createLineBorder(Color.BLUE));
            messages.setBorder(BorderFactory.createLineBorder(Color.BLUE));
     setSize(800,600);
           
            ChatBox.setEditable(false);  

            setTitle("Client "+pseud);
            
            cp.setBackground(Color.getHSBColor(150, 255, 173));
     
            Send.addActionListener(new envoyer());

                            this.menuBar.add(fichier);
                            this.menuBar.add(edition);
                            this.menuBar.add(option);
                            this.menuBar.add(aide);

            this.setJMenuBar(menuBar);
            listc.setSelectionInterval(5, 10);
            //listc.setBackground(Color.GREEN);
            listc.setSelectionBackground(Color.WHITE);

            this.setLocationRelativeTo(null);      
            this.setContentPane(cp);
             o.recharger();
            setVisible(true);
            t=new Thread(new reception(socket,this,paren,pseud));
                        t.start();
                       Thread t2=new Thread(new micro(this));
                   //     t2.start();
            } catch (Exception ex) {
                Logger.getLogger(MaFenetre.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(MaFenetre.class.getName()).log(Level.SEVERE, null, ex);
        }
       
            
}
     class envoyer  implements ActionListener{

                /**
                 */
        @Override
                public void actionPerformed(ActionEvent e) {
          //  String ima=i.toString();
        //    System.out.println(ima);
            
             String messag=messages.getText();
              outp.println("pub"+pseud+":"+messag);
              outp.flush();
              messages.setText("");
   
        }
        }
     public static Image scale(Image source, int width, int height) {
    /* On crée une nouvelle image aux bonnes dimensions. */
    BufferedImage buf = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
    /* On dessine sur le Graphics de l'image bufferisée. */
    Graphics2D g = buf.createGraphics();
    g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g.drawImage(source, 0, 0, width, height, null);
    g.dispose();

    /* On retourne l'image bufferisée, qui est une image. */
    return buf;
}
 
void lesListener()
{
    creer.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Salon s=new Salon(myself);
            }
        }); 
    smilies.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(smil==true)
                {
                   
                    pn.add(spane,BorderLayout.SOUTH);
                    smil=false;
                    pn.repaint();
                }
                else
                {
                    
                   
                    pn.remove(spane);
                    smil=true;
                     pn.repaint();
                }
                
                
            }
        }); 
    sauvegarde.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sauvegardeMessage s=new sauvegardeMessage(messageGeneral);
                        
            }
        
    });
    decrypte.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                decrypte d=new decrypte();
            }
    });
    sal.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {               }
            @Override
            public void mousePressed(MouseEvent e) {                }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(sal.isSelectionEmpty())
                {
                    csal.setEnabled(false); 
                    
                }
                else
                {
                    csal.setEnabled(true); 
                    
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
             
            }

            @Override
            public void mouseExited(MouseEvent e) {
            
            }
    
    });
    listc.addMouseListener(new MouseListener(){

            @Override
            public void mouseClicked(MouseEvent e) {
                
            }

            @Override
            public void mousePressed(MouseEvent e) {
                
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if(listc.isSelectionEmpty())
                {
                    Apel.setEnabled(false); 
                    priv.setEnabled(false); 
                }
                else
                {
                    Apel.setEnabled(true); 
                    priv.setEnabled(true); 
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
             
            }

            @Override
            public void mouseExited(MouseEvent e) {
            
            }
    
    });
////    o
    onglet.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent e) {
                onglet.setBackgroundAt(onglet.getSelectedIndex(), Color.red); 
                if(onglet.getSelectedIndex()!=0)
                {
              priveeMessage temp=(priveeMessage)onglet.getSelectedComponent();
              onglet.setTitleAt(onglet.getSelectedIndex(), temp.pseudo);
              temp.compteurMessage=0;
               if(pseud.contains(" "))
    {
    
    }
     else
               {
                    outp.println("tpf~"+pseud+":"+temp.pseudo);
                      outp.flush();
               }
                }
            }
        });
     quit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                
               outp.println("qui"+pseud);
              outp.flush();
               System.exit(0);
            }});
                effacer.addActionListener(new ActionListener() {@Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane confirmation=new JOptionPane();
               int index=confirmation.showConfirmDialog(null, "etes-vous sur de vouloire effacer?","Confirmation",JOptionPane.YES_NO_OPTION);
               if(index==confirmation.OK_OPTION)
               {
                    messageGeneral="";
                ChatBox.setText(messageGeneral);                 
               }}});
                messages.addKeyListener(new KeyListener() {
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
                   if(!messages.getText().equals("")&&!messages.getText().equals(" ")){
                   String messag=messages.getText();
              outp.println("pub"+pseud+":"+messag);
              outp.flush();
              messages.setText("");

                }}
               }
            
        });
               aides.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                Apropos a=new Apropos();
            }
        }); 

                options.addActionListener(new ActionListener()
                {

            @Override
            public void actionPerformed(ActionEvent e) {
                 o.setVisible(true); 
            }
            
                });
                 Apel.addActionListener(new ActionListener()
   {

            @Override
            public void actionPerformed(ActionEvent e) {
                if(!((String)listc.getSelectedValue()).equals(pseud))
                                   {
////                                       System.out.println("dans apel "+compteur);
                                       if(compteur==1)
                                       {
                                       JOptionPane confirmation=new JOptionPane();
                   int index=confirmation.showConfirmDialog(null, "N'appelez pas si vous ne disposez pas d'un micro\n Cela peut être synonyme de bug ","Confirmation",JOptionPane.YES_NO_OPTION);
               if(index==confirmation.OK_OPTION)
               {                     
                appeler(pseud,(String)listc.getSelectedValue());
                compteur++;
               }
                                       }
                                       else
                                           JOptionPane.showMessageDialog(null, "Vous etes déja en communication", "Erreur", JOptionPane.ERROR_MESSAGE);
                                   }
                 else
                                   {
                                JOptionPane.showMessageDialog(null, "Vous ne pouvez pas vous Appeler", "Erreur de selection", JOptionPane.ERROR_MESSAGE);			
                                   }
            }
       
   });      
     csal.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
       if(!salonActuel.equalsIgnoreCase((String)sal.getSelectedValue()))
       {
           nomSalon=(String)sal.getSelectedValue();
            outp.println("CSA"+pseud+"^"+nomSalon);
              outp.flush();
               messageGeneral="";
              salonActu.setText("salon actuel :" +nomSalon);
              salonActuel=nomSalon;
              onglet.setTitleAt(0, "Groupe"); 
              if(nomSalon.equals("General"))
                  onglet.setTitleAt(0, "General"); 
            }
            }
     });            
priv.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
               
                               try {
                                   if(!((String)listc.getSelectedValue()).equals(pseud))
                                   {
                    boolean flag=false;
                
                            if(pseud.contains(" "))
    {
    
    
    }
     else
               {
                    outp.println("tpf~"+pseud+":"+(String)listc.getSelectedValue());
                      outp.flush();
               }
                                 for(int i=0;i<fenetrePrivee.size();i++)
            {
                priveeMessage Temp=(priveeMessage)fenetrePrivee.get(i);
                if(Temp.pseudo.equals((String)listc.getSelectedValue()))
                {
    
                    onglet.setSelectedComponent(onglet.getComponentAt(onglet.indexOfComponent(Temp)));
                    flag=true;
                    break;
                }
            }
           if(!flag)
           {
            
                fenetrePrivee.add(new priveeMessage((String)listc.getSelectedValue()," ",socket,pseud,myself,fen));
           onglet.add((String)listc.getSelectedValue(),(priveeMessage)fenetrePrivee.get(fenetrePrivee.size()-1));            
                    onglet.setSelectedComponent(onglet.getComponentAt(onglet.indexOfComponent((priveeMessage)fenetrePrivee.get(fenetrePrivee.size()-1)))); 
           }
                                   }
                                   else
                                   {
                                   
                                JOptionPane.showMessageDialog(null, "Vous ne pouvez pas vous ecrire un message privé", "Erreur de selection", JOptionPane.ERROR_MESSAGE);			
                                   }
                } catch (IOException ex) {
                  
                                JOptionPane.showMessageDialog(null, "Client introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);			
                }
            }
            
        });
                 
}

public void appeler(String monpseudo,String destinataire)
{
    
                                    try {
                                   if(!((String)listc.getSelectedValue()).equals(pseud))
                                   {
                    boolean flag=false;
                     
                                 for(int i=0;i<fenetrePrivee.size();i++)
            {
                priveeMessage Temp=(priveeMessage)fenetrePrivee.get(i);
                if(Temp.pseudo.equals((String)listc.getSelectedValue()))
                {
    
                    onglet.setSelectedComponent(onglet.getComponentAt(onglet.indexOfComponent(Temp)));
                     Temp.centre.add(Temp.est,BorderLayout.EAST);
                     Temp.est.repaint();
                    flag=true;
                    break;
                }
            }
           if(!flag)
           {
            
               
                fenetrePrivee.add(new priveeMessage((String)listc.getSelectedValue()," ",socket,pseud,myself,fen));
            priveeMessage Temp2=(priveeMessage)fenetrePrivee.get(fenetrePrivee.size()-1);
                onglet.add((String)listc.getSelectedValue(),Temp2);            
                    onglet.setSelectedComponent(onglet.getComponentAt(onglet.indexOfComponent(Temp2))); 
                    Temp2.centre.add(Temp2.est,BorderLayout.EAST);
                 
           
           }
                                   }
                                   else
                                   {
                                   
                                JOptionPane.showMessageDialog(null, "Vous ne pouvez pas vous ecrire un message privé", "Erreur de selection", JOptionPane.ERROR_MESSAGE);			
                                   }
                } catch (IOException ex) {
                  
                                JOptionPane.showMessageDialog(null, "Client introuvable", "Erreur", JOptionPane.ERROR_MESSAGE);			
                }
        outp.println("ape"+monpseudo+"~"+destinataire);
                     outp.flush();
                                                 
                     
}
            public  void  receptionMessageGeneral(String message)
    {
        
        messageGeneral= messageGeneral+emo.remplacer(message) +"<br>";
//             System.out.println(emo.remplacer(message)); 
           ChatBox.setText(messageGeneral);
           ChatBox.setCaretPosition((int)(1*ChatBox.getDocument().getLength()));
           
        
    }         

    }
