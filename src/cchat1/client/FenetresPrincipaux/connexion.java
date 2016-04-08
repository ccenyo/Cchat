/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.FenetresPrincipaux;

/**
 *
 * @author SODJINOU
 */
import cchat1.Serveur.Rechercher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 *
 * @author SansadoOre
 */
public class connexion extends JFrame{
  private  JPanel pan0=new JPanel();
   private JPanel pan1=new JPanel(new BorderLayout());
   GridLayout grid=new GridLayout(1,3);
   public JList list=new JList();
   
   public JLabel listeServ=new JLabel("Liste des serveurs disponibles !!!");
   public Vector vec=new Vector();
   private JPanel pan2=new JPanel(grid);
  private  JButton valider=new JButton("VALIDER");
  private  JLabel ip=new JLabel("IP DU SERVEUR ");
  //private  JLabel port=new JLabel("PORT ");
  private  JLabel pseudo=new JLabel();
 private   JLabel ere=new JLabel();
 public   JTextField text1=new JTextField("127.0.0.1");
 //public   JTextField text2=new JTextField("5050");
 private   JTextField text3=new JTextField("");
 private   Font font=new Font("Script MT Bold",Font.ITALIC,20);
   private Socket socket,socket2 ;
 private   PrintWriter outp  ;
 private   Thread t1;
 public Rechercher rec;
 public JButton actualiser=new JButton(new ImageIcon("image/Refresh1.png"));
private connexion parent;
public Object[][] ligne={};
public String[] colonne={"Nom Serveur","Nbre de Connecte","Nbre de Salon","Adresse IP","Commentaire"};
public JTable table=new JTable(ligne,colonne);
public JScrollPane scrol=new JScrollPane(table);
public JButton bouton=new JButton("Connexion Manuelle");
 FileOutputStream fichier;
Properties reglages ;
Calendar dt=Calendar.getInstance();
 Calendar dt2=Calendar.getInstance();                     
    public connexion(){
        dt.set(2013, 07, 17);
                       
//                       System.out.println(dt.getTime());
//                        System.out.println(dt2.getTime());
        try {
             Properties parDéfaut = new Properties();
           parDéfaut.put("IP", "127.0.0.1");
           parDéfaut.put("PSEUDO", "");      
      reglages = new Properties(parDéfaut);
             reglages.load(new FileInputStream("réglages.properties"));
//             System.out.println(reglages.getProperty("IP"));
//              System.out.println(reglages.getProperty("PSEUDO"));
           // lecture du fichier de propriétés
              Image icone = Toolkit.getDefaultToolkit().getImage("image/meet_me.png");
    this.setIconImage(icone); 
  //  actualiser.setIcon(new ImageIcon("image/Refresh.ico")); 
           text3.setText(reglages.getProperty("PSEUDO"));
            text1.setText(reglages.getProperty("IP"));
             rec=new Rechercher(this);
             Thread t2=new Thread(rec);
             t2.start();
             this.setTitle("PAGE DE CONNEXION");
             this.setSize(550,400);
             this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
             this.setLocationRelativeTo(null);
            //actualiser.setSize(new Dimension(20,20));
             list.setListData(vec);
             parent=this;
             grid.setHgap(5);
             
             text1.setPreferredSize(new Dimension(150,30));
             //text2.setPreferredSize(new Dimension(150,30));
             text3.setPreferredSize(new Dimension(150,30));
             
             ip.setForeground(Color.WHITE);
             pan0.add(ip);
             //port.setForeground(Color.WHITE);    
             pan0.add(text1);
             pan0.add(bouton);
             bouton.addActionListener(new ActionListener()
                     {

                 @Override
                 public void actionPerformed(ActionEvent e) {
                       if(!text3.getText().equals(""))
                         {
                             if(!text1.getText().equals(""))
                             {
                                 if(!(text3.getText().contains("~")||text3.getText().contains("^")||text3.getText().contains("¨")))
                                 {
                            String gt= text3.getText();
               //             int i=Integer.valueOf(text2.getText()).intValue();
                         validation(gt,text1.getText(),5050);
                                 }
                                 else
                                     JOptionPane.showMessageDialog(null, "Presence de caractere(s) indesirable(s)\nVeuillez verifiez votre pseudo", "Erreur", JOptionPane.ERROR_MESSAGE);
                             }
                         }
                      
                 }
                         
                     });
             
             text1.addKeyListener(new KeyListener()
             {
                 
                 @Override
                 public void keyTyped(KeyEvent e) {
                    
                 }

                 @Override
                 public void keyPressed(KeyEvent e) {
                     
                 }

                 @Override
                 public void keyReleased(KeyEvent e) {
                     if(e.getKeyCode()==10)
                     {
                          if(!text3.getText().equals(""))
                         {
                             if(!text1.getText().equals(""))
                             {
                                 if(!(text3.getText().contains("~")||text3.getText().contains("^")||text3.getText().contains("¨")))
                                 {
                            String gt= text3.getText();
               //             int i=Integer.valueOf(text2.getText()).intValue();
                         validation(gt,text1.getText(),5050);
                                 }
                                 else
                                     JOptionPane.showMessageDialog(null, "Presence de caractere(s) indesirable(s)\nVeuillez verifiez votre pseudo", "Erreur", JOptionPane.ERROR_MESSAGE);
                             }
                         }
                     }
                 }
                 
             });
             
             
             pan0.add(actualiser);
             actualiser.addActionListener(new ActionListener() {

                 @Override
                 public void actionPerformed(ActionEvent e) {
                     rec.run();
                 }
             });
             //pan0.add(port);
             //pan0.add(text2);
             pan0.setBackground(Color.getHSBColor(150, 255, 173));
             
             pseudo.setFont(font);
             pseudo.setText("Votre Pseudonyme : ");
             pseudo.setForeground(Color.WHITE);
             //pan1.add(pseudo);
             //pan1.add(text3);
             //JLabel pan=new JLabel(new ImageIcon("image/Cchat1.png"));
             //pan.setPreferredSize(new Dimension(300,200));
            // pan1.add(pan,BorderLayout.CENTER);
             pan1.add(listeServ,BorderLayout.NORTH);
             pan1.add(scrol,BorderLayout.CENTER);
             pan1.setBackground(Color.WHITE);
             Container containe=new Container();
             
             
             
             
             containe.setLayout(new BorderLayout());
             containe.add(pan0,BorderLayout.NORTH);
             containe.add(pan1,BorderLayout.CENTER);
             containe.add(ere,BorderLayout.EAST);
           
             pan2.setBackground(Color.getHSBColor(150, 255, 173));
             containe.setBackground(Color.getHSBColor(160, 260, 153));
             valider.setPreferredSize(new Dimension(50,30));
             valider.addActionListener(new ActionListener()
                     {

                 @Override
                 public void actionPerformed(ActionEvent e) {
                      
                    if(!text3.getText().equals(""))
                    {
                        if(!(text3.getText().contains("~")||text3.getText().contains("^")||text3.getText().contains("¨")))
                        {
                        String ip=(String)table.getValueAt(table.getSelectedRow(), 3);
                        if(ip!=null)
                        {
                            String gt= text3.getText();
               //             int i=Integer.valueOf(text2.getText()).intValue();
                         validation(gt,ip,5050);
                        }
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Presence de caractere(s) indesirable(s)\nVeuillez verifiez votre pseudo", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                 }
                         
                     });
             
             
             text3.addKeyListener(new KeyListener()
             {

                 @Override
                 public void keyTyped(KeyEvent e) {
                    
                 }

                 @Override
                 public void keyPressed(KeyEvent e) {
                     
                 }

                 @Override
                 public void keyReleased(KeyEvent e) {
                     if(e.getKeyChar()=='~' || e.getKeyChar()=='#' || e.getKeyChar()=='"' || e.getKeyChar()=='{' || e.getKeyChar()=='(' || e.getKeyChar()=='[' || e.getKeyChar()=='-' || e.getKeyChar()=='|' || e.getKeyChar()=='_' || e.getKeyChar()==')' || e.getKeyChar()=='=' || e.getKeyChar()=='}' || e.getKeyChar()==']' || e.getKeyChar()=='$' || e.getKeyChar()=='*' || e.getKeyChar()=='!' || e.getKeyChar()==':' || e.getKeyChar()=='/' || e.getKeyChar()=='.' || e.getKeyChar()==';' || e.getKeyChar()==',' || e.getKeyChar()=='?')
                     {
                         text3.setText(text3.getText().replace(String.valueOf(e.getKeyChar()),""));
                     }
                     if(e.getKeyCode()==10)
                     {
                         if(!text3.getText().equals(""))
                    {
                        if(!(text3.getText().contains("~")||text3.getText().contains("^")||text3.getText().contains("¨")))
                        {
                        String ip=(String)table.getValueAt(table.getSelectedRow(), 3);
                        if(ip!=null)
                        {
                            String gt= text3.getText();
               //             int i=Integer.valueOf(text2.getText()).intValue();
                         validation(gt,ip,5050);
                        }
                        }
                        else
                            JOptionPane.showMessageDialog(null, "Presence de caractere(s) indesirable(s)\nVeuillez verifiez votre pseudo", "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                     }
                 }
                 
             });
             pan2.add(pseudo);
             pan2.add(text3);
             pan2.add(valider);
             containe.add(pan2,BorderLayout.SOUTH);
             this.setContentPane(containe);
             this.setResizable(false);
             this.setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void validation(String pseudo,String ip,int port)
    {
        if(dt2.get(dt2.YEAR) <=dt.get(dt.YEAR))
            
        {
//            System.out.println(dt2.get(dt2.YEAR)+"<="+dt.get(dt.YEAR));
            if(dt2.get(dt2.MONTH)<dt.get(dt.MONTH))
            {
//                System.out.println(dt2.get(dt2.MONTH)+"<="+dt.get(dt.MONTH));
              
                    
        try {
            socket = new Socket(ip,port);
               valider.setEnabled(false);
                        InetAddress adrLocale = InetAddress.getLocalHost(); 
                       outp = new PrintWriter(socket.getOutputStream());
                       outp.println(pseudo+"~"+adrLocale.getHostAddress()+":"+adrLocale.getHostName());
                       outp.flush();
                       MaFenetre f = new MaFenetre(pseudo,socket,parent);
                       setVisible(false);
                       reglages.put("IP", ip);
                       reglages.put("PSEUDO", pseudo);
                       
                     //  reglages.put("date", dt.toString());
                       fichier = new FileOutputStream("réglages.properties");                       
                      reglages.store(fichier, "Réglages de Meet me");
        } catch (UnknownHostException ex) {
            valider.setEnabled(true);
            
				JOptionPane.showMessageDialog(null, "Connexion impossible\nVerifier si le Ip et le Port sont correct", "Serveur introuvable", JOptionPane.ERROR_MESSAGE);	
                                valider.setEnabled(true);
        } catch (IOException ex) {
            
           
				JOptionPane.showMessageDialog(null, "Connexion impossible\nVerifier si le Ip et le Port sont correct", "Erreur", JOptionPane.ERROR_MESSAGE);	
                                valider.setEnabled(true);
            
        }
    
                   }
            else if(dt2.get(dt2.DAY_OF_MONTH)<=dt.get(dt.DAY_OF_MONTH) && dt2.get(dt2.MONTH)==dt.get(dt.MONTH))
            {
//                        System.out.println(dt2.get(dt2.DAY_OF_MONTH)+"<="+dt.get(dt.DAY_OF_MONTH));
        try {
            socket = new Socket(ip,port);
               valider.setEnabled(false);
                        InetAddress adrLocale = InetAddress.getLocalHost(); 
                       outp = new PrintWriter(socket.getOutputStream());
                       outp.println(pseudo+"~"+adrLocale.getHostAddress()+":"+adrLocale.getHostName());
                       outp.flush();
                       MaFenetre f = new MaFenetre(pseudo,socket,parent);
                       setVisible(false);
                       reglages.put("IP", ip);
                       reglages.put("PSEUDO", pseudo);
                       
                     //  reglages.put("date", dt.toString());
                       fichier = new FileOutputStream("réglages.properties");                       
                      reglages.store(fichier, "Réglages de Meet me");
        } catch (UnknownHostException ex) {
            valider.setEnabled(true);
            
				JOptionPane.showMessageDialog(null, "Connexion impossible\nVerifier si le Ip et le Port sont correct", "Serveur introuvable", JOptionPane.ERROR_MESSAGE);	
                                valider.setEnabled(true);
        } catch (IOException ex) {
            
           
				JOptionPane.showMessageDialog(null, "Connexion impossible\nVerifier si le Ip et le Port sont correct", "Erreur", JOptionPane.ERROR_MESSAGE);	
                                valider.setEnabled(true);
            
        }
            }
            else
        JOptionPane.showMessageDialog(null, "La période d'essaie est terminée,pour plus de renseignement, contacter nous au 96 12 00 49 ou 96 72 55 90", "Validation", JOptionPane.ERROR_MESSAGE);
         

        }
        else
        JOptionPane.showMessageDialog(null, "La période d'essaie est terminée,pour plus de renseignement, contacter nous au 96 12 00 49 ou 96 72 55 90", "Validation", JOptionPane.ERROR_MESSAGE);    
    }
   
}

