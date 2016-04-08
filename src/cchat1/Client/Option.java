/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client;

import cchat1.Client.FenetresPrincipaux.MaFenetre;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Properties;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 *
 * @author cenyo
 */
public class Option extends JDialog{
    Properties parDefaut = new Properties();
   public Properties reglages = new Properties(parDefaut);
   private JPanel centre=new JPanel(new BorderLayout());
   private JLabel image=new JLabel();
    public FileDialog f;
   private JButton choisir=new JButton("choix");
   private JButton ok=new JButton("Appliquer");
   private JDialog parent;
 private   MaFenetre ff;
 private   Image i;
 public Image ima;
    private JButton appliquer=new JButton("Appliquer");
  private  JList list=new JList();
  private  JLabel img=new JLabel();
   private JLabel txt=new JLabel("choisisser le type de fenetre");
  //  JFrame parent;
  private  JPanel choix=new JPanel(new BorderLayout());
   private JTabbedPane principal;
   Option parent2;
   String lien;
   int index;
    FileOutputStream fichier;
 public Option(MaFenetre tr)
 {
       
           // fichier = new FileOutputStream("configimage.properties");
                  parDefaut.put("image", "image/defaultIm.png");
                 parDefaut.put("theme", "1");
                
               Image icone = Toolkit.getDefaultToolkit().getImage("image/meet_me.png");
         this.setIconImage(icone);
                 parent2=this;
                 setSize(500,300);
                 this.setLocationRelativeTo(null);
                // this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                 this.setTitle("Option");
                 centre.add(choisir,BorderLayout.EAST);
                 centre.add(image);
                 centre.add(ok,BorderLayout.SOUTH);
                 parent=this;
                 ff=tr;
               //  this.setAlwaysOnTop(true);
               this.setModal(true);
                  
                 JPanel droite=new JPanel(new BorderLayout());
                 droite.add(list,BorderLayout.CENTER);
                 
                 droite.add(appliquer,BorderLayout.SOUTH);
                 droite.setSize(new Dimension(500,300));
                   choix.add(droite,BorderLayout.EAST);
                   choix.add(txt,BorderLayout.NORTH);
                   choix.add(img,BorderLayout.CENTER);
                   principal=new JTabbedPane();
                   principal.add(centre,"photo profil");
                   principal.add(choix,"type de fenetre");
                UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();
                Vector temp=new Vector();
                for(int x=0;x<lafInfo.length;x++)
                {             
                    temp.add(lafInfo[x].getName()); 
                    list.setListData(temp);  
                    
                }
              
                   list.addMouseListener(new MouseListener() {

                        @Override
                        public void mouseClicked(MouseEvent e) {
                        }

                        @Override
                        public void mousePressed(MouseEvent e) {
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                           if(list.getSelectedIndex()==0)
                               img.setIcon(new ImageIcon("image/Metal.jpg")); 
                           if(list.getSelectedIndex()==1)
                               img.setIcon(new ImageIcon("image/Nimbus.jpg")); 
                           if(list.getSelectedIndex()==2)
                               img.setIcon(new ImageIcon("image/CDE_Motif.jpg")); 
                           if(list.getSelectedIndex()==3)
                               img.setIcon(new ImageIcon("image/Windows.jpg")); 
                           if(list.getSelectedIndex()==4)
                               img.setIcon(new ImageIcon("image/Windows Classic.jpg")); 
                            
                           
                        }

                        @Override
                        public void mouseEntered(MouseEvent e) {
                        }

                        @Override
                        public void mouseExited(MouseEvent e) {
                        }
                    }); 
                   
                   
                   
                   appliquer.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                         try {
                             //Recherche des look&fell disponible
                             UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();

                             
                                
                                 
                                 final String className = lafInfo[list.getSelectedIndex()].getClassName();
                                 System.out.println(lafInfo[list.getSelectedIndex()].getClassName());
                             index=list.getSelectedIndex();
                                 //Ajout des actions sur ce bouton
                               Rectangle tem= ff.getBounds();
                                         
                                     updateSkin(className,parent);
                                     updateSkin(className,ff);
                                     ff.setBounds(tem);
                                 setSize(500,300);
                           String cast="";
                           cast=cast.valueOf(index);
                            reglages.put("theme", cast); 
                         
                             fichier = new FileOutputStream("configimage.properties");
                 reglages.store(fichier, "Réglages du Thème et de l'image");
                         } catch (IOException ex) {
                             Logger.getLogger(Option.class.getName()).log(Level.SEVERE, null, ex);
                         }
                        
                        }
                 });
                 choisir.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            try {
                                FileDialog f=new FileDialog(parent2,"choisir la tof");
                                f.show();
                                lien=f.getDirectory()  + f.getFile();
                                 BufferedImage photo = ImageIO.read(new File(lien));
                                 if(photo!=null)
                                 {
                                  i=photo;
                                  ima=i;
                                 i=scale(i,400,270);
                                 image.setIcon(new ImageIcon(i)); 
                                 }
                            } catch (IOException ex) {
                              //  Logger.getLogger(Option.class.getName()).log(Level.SEVERE, null, ex);
                            }
                              
                             
                        }
                 
                 });
                 ok.addActionListener(new ActionListener(){

                        @Override
                        public void actionPerformed(ActionEvent e) {
                            if(i!=null)
                            {
                             try {
                                 i=scale(i,120,100);
                                 ff.icon2.setIcon(new ImageIcon(i));
                                  
                                    reglages.put("image", lien);
                             fichier = new FileOutputStream("configimage.properties");
                 reglages.store(fichier, "Réglages du Thème et de l'image");
                             } catch (IOException ex) {
                                 Logger.getLogger(Option.class.getName()).log(Level.SEVERE, null, ex);
                             }
                        
                            }
                        }
                 
                 });
                 this.setContentPane(principal); 
             //    setVisible(true);
         
             
        
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
      private boolean updateSkin(String s, JFrame e)
    {
	if(s.equalsIgnoreCase(""))
	    return true;

	boolean ok = false;
	try {
	    UIManager.setLookAndFeel(s);
	    SwingUtilities.updateComponentTreeUI(e);
	
	    ok=true;
	} 
	catch (Exception exc) {
	    System.err.println("Could not load LookAndFeel: " + s);
	}
	return ok;
    }
      private boolean updateSkin(String s, JDialog e)
    {
	if(s.equalsIgnoreCase(""))
	    return true;

	boolean ok = false;
	try {
	    UIManager.setLookAndFeel(s);
	    SwingUtilities.updateComponentTreeUI(e);
	
	    ok=true;
	} 
	catch (Exception exc) {
	    System.err.println("Could not load LookAndFeel: " + s);
	}
	return ok;
    }
      public void recharger()
      {
        try {
           
             reglages.load(new FileInputStream("configimage.properties"));
             System.out.println(reglages.getProperty("image"));
             System.out.println(reglages.getProperty("theme"));
             int ind=Integer.valueOf(reglages.getProperty("theme")).intValue();
             String imag=reglages.getProperty("image");
             BufferedImage photo = ImageIO.read(new File(imag));
               if(photo!=null)
                            {
                            Image im=photo;
                             System.out.println("image recharger");
                            im=scale(im,120,100);
                            ff.icon2.setIcon(new ImageIcon(im)); 
                            }
               
             UIManager.LookAndFeelInfo[] lafInfo = UIManager.getInstalledLookAndFeels();

                        
                           
                            
                            final String className = lafInfo[ind].getClassName();
                            System.out.println(lafInfo[ind].getClassName());
              Rectangle tem= ff.getBounds();
                                    
                                updateSkin(className,parent);
                                updateSkin(className,ff);
                                ff.setBounds(tem);
                            setSize(500,300);
                            ff.onglet.setBackground(Color.red); 
        } catch (IOException ex) {
            Logger.getLogger(Option.class.getName()).log(Level.SEVERE, null, ex);
        }

      }
      
}
