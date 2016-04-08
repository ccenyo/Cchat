/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JOptionPane;

/**
 *
 * @author Cenyo
 */
public class discussion{
      private  Socket sock;
      private  BufferedReader in2;
        private PrintWriter out;
      private  init f;
      private  Thread t;
      private  Vector v=new Vector();
        
       
        discussion(Socket socket,init s,String d,String ip,String nom,String salon)
        {
        try {
            f=s;
            sock=socket;
            System.out.println("constructeur discussion demarrer");
            out=new PrintWriter(sock.getOutputStream());
            s.EnvoiList();
          //  s.EnvoiListGeneral(s.ListConnecte());
            s.EnvoiListGeneral("]"+s.ListSalon());
            System.out.println(s.ListSalon());
            /* s.ss.info.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
             f.ss.n++;
             JOptionPane jop = new JOptionPane();
               String nom = jop.showInputDialog(null, "nom du nouveau salon!", "création de salon !", JOptionPane.QUESTION_MESSAGE);
               f.ss.salons[f.ss.n-1]=nom;
               JList ssa=new JList();
               f.ss.scrols[f.ss.n-1]=ssa;
                JScrollPane js=new JScrollPane(f.ss.scrols[f.ss.n-1]);
                JPanel cent2=new JPanel(new BorderLayout());
                cent2.add(js,BorderLayout.CENTER);
                f.ss.salon[f.ss.n-1]=cent2;
                f.ss.onglet.add(f.ss.salons[f.ss.n-1],f.ss.salon[f.ss.n-1]);
                f.EnvoiListGeneral("]"+f.ListSalon());
            }
             
         });*/
            if(f.veificationPseudo(d,ip,socket))
            {
                out.println("ex~");
                out.flush();
                s.EnvoiList();
                s.EnvoiListGeneral("]"+s.ListSalon());
             //   s.EnvoiListGeneral(s.ListConnecte());
                
            }
            else
            {
                
                s.EnvoiconnecteGeneral(d+ " s'est connecté");
             f.Ajout(sock, d,ip,nom,salon);
            
            
            out.println("admConnexion reussie");
            out.flush();
            out.println("admBienvenue sur l'interface de communication de la CEB ");
            out.flush();
            s.EnvoiList();
             s.EnvoiListGeneral("]"+s.ListSalon());
           //  s.EnvoiListGeneral(s.ListConnecte());
            System.out.println(s.ListConnecte());
            new commande(f,sock);
            }
        } catch (IOException ex) {
          JOptionPane jop3 = new JOptionPane();
				jop3.showMessageDialog(null, "Erreur", "Erreur ", JOptionPane.ERROR_MESSAGE);			
        }

        }

               
      }
                
         

        
       

    