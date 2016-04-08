/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

import cchat1.Serveur.init;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.*;

/**
 *
 * @author cenyo
 */
public  class fn extends JFrame {
   public  ObjectOutputStream out2;
  
   public  init d;
   private  Thread t1;
  	public	ServerSocket socketserver  ;
	public	Socket socketduserveur ;
	public	BufferedReader in;
	private	PrintWriter out;
private ArrayList cli=new ArrayList();
         private JPanel container = new JPanel();
    public JLabel label = new JLabel("Start pour démarrer le serveur");
    private JLabel label1 = new JLabel("");
    public JButton stop=new JButton("Arreter");
   private JLabel lis=new JLabel("Liste des connectés");
  public  JButton blok=new JButton("Déconnecté ce client");
   private JPanel  centre=new JPanel();
    //private JButton commence=new JButton("Start");
    private JLabel icon=new JLabel(new ImageIcon("image/meetme.png"));
   private Socket socket;
    private Thread t;
   public int nombre=0;
   public JList scrol=new JList();
    
  public  Vector connecte=new Vector();
  public Salon [] salo=new Salon[50];
 public ArrayList list=new ArrayList();
 public JList scro=new JList();
 public JButton info=new JButton("Ajouter un salon");
  public  JPanel boutons=new JPanel();
   public int n=1;
   public JPanel cent;
   public JPanel []salon=new JPanel[50];
   public JList []scrols=new JList[50];
   public Vector []connect=new Vector[50];
    //JScrollPane jss=new JScrollPane(scrols[]);
   public JTabbedPane onglet;
private String[] salons;
public Heberger heb;
private fn moimm;
    /** Creates a new instance of heberge */
    public fn(Heberger h) {
        heb=h;
        onglet = new JTabbedPane();
d=new init(list,this);
            this.setTitle("Hébergement");
                  this.setResizable(false);
        this.setSize(600, 500);
        this.setLocationRelativeTo(null);
         
         Image icone = Toolkit.getDefaultToolkit().getImage("image/meet_me.png");
    this.setIconImage(icone);
        moimm=this;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        stop.setEnabled(false);
         boutons.setLayout(new GridLayout(1,2));
            boutons.add(info);
            boutons.add(blok);
                centre.setLayout(new BorderLayout()); 
                centre.add(lis,BorderLayout.NORTH);
                
               // salons=new String[10];
                salo[0]=new Salon("General");
               // salons[0]="General";
                //connect[0]=connecte;
                salo[0].setVector(connecte);
                cent=new JPanel(new BorderLayout());
                scrols[0]=scrol;
                JScrollPane js=new JScrollPane(scrols[0]);
                cent.add(js,BorderLayout.CENTER);
                salon[0]=cent;
                onglet.add(salo[0].getNom(),salon[0]);                                     
                
                
                centre.add(onglet,BorderLayout.CENTER);
                centre.add(boutons,BorderLayout.SOUTH);
         info.setBackground(Color.WHITE);
         info.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
             n++;
           //  Vector temp=new Vector();
             JOptionPane jop = new JOptionPane();
               String nom = jop.showInputDialog(null, "nom du nouveau salon!", "création de salon !", JOptionPane.QUESTION_MESSAGE);
           if(nom!=null)
           {
               salo[n-1]=new Salon(nom);
               JList ss=new JList();
               scrols[n-1]=ss;
                JScrollPane js=new JScrollPane(scrols[n-1]);
                JPanel cent2=new JPanel(new BorderLayout());
                cent2.add(js,BorderLayout.CENTER);
                salon[n-1]=cent2;
                //connect[n-1]=temp;
                onglet.add(salo[n-1].getNom(),salon[n-1]);
                d.EnvoiListGeneral("]"+d.ListSalon());
            }
           else
               d.EnvoiListGeneral("]"+d.ListSalon());
            }
             
         });
          container.setBackground(Color.white);
           centre.setBackground(Color.white);
            container.setLayout(new BorderLayout());
            stop.setBackground(Color.getHSBColor(150, 255, 173));
            blok.setBackground(Color.getHSBColor(150, 255, 173));
             container.setBackground(Color.WHITE);
               //commence.setBackground(Color.WHITE);    
            //Définition d'une police d'écriture
            Font police = new Font("Arial", Font.ITALIC, 13 );
            //On applique celle-ci aux JLabelst
            label.setFont(police);
            label1.setFont(police);
           
           
            blok.addActionListener(new ActionListener()
            {

            @Override
            public void actionPerformed(ActionEvent e) {
                client c=d.getClient((String)scrol.getSelectedValue());
                d.envoiMessageClient(c.getSocket(),"jvmVous avez été déconnecté par le serveur !!!");
               
                d.retirer((String)scrol.getSelectedValue());
                 d.EnvoideconnecteGeneral(c.getUserName());
                d.EnvoiList();
            }
                
            });
            
             scrol.setSelectionBackground(Color.blue)  ;
         scrol.setSelectionForeground(Color.lightGray); 
           stop.addActionListener(new ActionListener()
            {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    socketserver.close();
                } catch (IOException ex) {
                   JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "Erreur de fermeture", "Erreur ", JOptionPane.ERROR_MESSAGE);			
                }
                    //commence.setEnabled(true);
                    stop.setEnabled(false);
               Heberger h=new Heberger() ;
               setVisible(false);
               
            }
                
            });
            //On change la couleur de police
            label.setForeground(Color.getHSBColor(150, 255, 173));
            //Et on change l'alignement du texte grâce aux attributs static de la classe JLabel
            label.setHorizontalAlignment(JLabel.CENTER);
          //  label.setBounds(50,40,400,90);
            JPanel b=new JPanel();
            b.setLayout(new GridLayout(1,2));
           // b.add(commence);
            b.add(stop);
            
            container.add(label,BorderLayout.NORTH);
            container.add(centre,BorderLayout.CENTER);
            container.add(icon,BorderLayout.EAST);
            container.add(b,BorderLayout.SOUTH);
            this.setContentPane(container);
             
         //this.setVisible(true);
    }
   
          
  public void actualiser(){
      if(heb.commentaire.getText().equals(""))
                    {
                        heb.info=" "+heb.nomServ.getText()+"|"+heb.sanCom+"*"+heb.fenetre.d.getNnbrClient()+"/"+heb.fenetre.d.getNbrSalon();
                    }
                    else
                        heb.info=" "+heb.nomServ.getText()+"|"+heb.commentaire.getText()+"*"+heb.fenetre.d.getNnbrClient()+"/"+heb.fenetre.d.getNbrSalon();
            
  }
    
   
    }

