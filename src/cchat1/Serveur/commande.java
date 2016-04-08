/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

import cchat1.Serveur.init;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Cenyo
 */
public class commande implements Runnable
{
 private   init i;
private BufferedReader in;
private String commande="";
private Thread t;
private Socket ss;
Vector v=new Vector();
commande(init a,Socket s) throws IOException
{
    ss=s;
    i=a;
    in = new BufferedReader(new InputStreamReader(s.getInputStream()));
    t = new Thread(this);
    t.start();
}
    @Override
    public void run() {
        try {
            while((commande=in.readLine())!=null)
            {
                
                String inst=commande.substring(0, 3);
                if(inst.equals("yes"))
                {
                    v.add(commande.substring(3)); 
                    System.out.println("dans yes "+v.toString());
                }
                if(inst.equals("pra"))
                {
                   i.envoiAnd(commande.substring(commande.indexOf("~")+1,commande.indexOf(":")), commande.substring(commande.indexOf(":")+1,commande.indexOf("|")), commande.substring(3,commande.indexOf("~")),commande.substring(commande.indexOf("|")+1));
                }
                if(inst.equals("IP4"))
                {
                    i.envoiIp4(commande.substring(3,commande.indexOf(":")),commande.substring(commande.indexOf(":")+1,commande.indexOf("?")),commande.substring(commande.indexOf("?")+1));
                }
                if(inst.equals("???"))
                {
                    i.changeSalon(commande.substring(3,commande.indexOf("|")), commande.substring(commande.indexOf("|")+1,commande.indexOf("*")), commande.substring(commande.indexOf("*")+1)); 
                }
                if(inst.equals("<*>"))
                {
                    i.AjouterSalonPrivee(commande.substring(3,commande.indexOf("|")), commande.substring(commande.indexOf("|")+1,commande.indexOf("~")), commande.substring(commande.indexOf("~")+1));
                }
                if(inst.equals("<~>"))
                {
                    i.AjouterSalon(commande.substring(3,commande.indexOf("|")) ,commande.substring(commande.indexOf("|")+1) );
                }
                if(inst.equalsIgnoreCase("pub"))
                {
                    
                   i.EnvoiMessageGeneral(commande.substring(commande.indexOf(":")+1,commande.length()),commande.substring(3, commande.indexOf(":")));
                }
                else if(inst.equalsIgnoreCase("pri"))
                {
                    
                    i.EnvoiMessagePrivee(commande.substring(3,commande.lastIndexOf("~")),commande.substring(commande.lastIndexOf("~")+1),commande.substring(3, commande.indexOf(":")));
               
                }
                else if(inst.equalsIgnoreCase("qui"))
                {
                    i.retirer(commande.substring(3));
                    i.EnvoideconnecteGeneral(commande.substring(3));
                }
                else if(inst.equalsIgnoreCase("tra"))
                {
                   System.out.println("tra receptionner ");    
                    i.Envoifichier(commande.substring(3,commande.indexOf("~")),commande.substring(commande.indexOf("~")+1)); 
                }
                if(inst.equalsIgnoreCase("pro"))
                {
                    System.out.println("pro demaré");
                    System.out.println(commande.substring(commande.indexOf("~")+1,commande.indexOf(":"))+" "+commande.substring(commande.indexOf(":")+1)+" "+commande.substring(3,commande.indexOf("~")));
                    i.envoipret(commande.substring(commande.indexOf("~")+1,commande.indexOf(":")), commande.substring(commande.indexOf(":")+1,commande.indexOf("|")), commande.substring(3,commande.indexOf("~")),commande.substring(commande.indexOf("|")+1));
                }
                if(inst.equalsIgnoreCase("CSA"))
                {
                    i.changeSalon(commande.substring(3,commande.indexOf("^")),commande.substring(commande.indexOf("^")+1));
                }
                if(inst.equalsIgnoreCase("ape"))
                {
                    i.demandeDeConnexion(commande.substring(3,commande.indexOf("~")),commande.substring(commande.indexOf("~")+1)); 
                }
                if(inst.equalsIgnoreCase("RAI"))
                {
                    System.out.println(commande);
                    i.ApelCommencer(commande.substring(commande.indexOf("~")+1,commande.indexOf(":")), commande.substring(commande.indexOf(":")+1,commande.indexOf("?")), commande.substring(3,commande.indexOf("~")),commande.substring(commande.indexOf("?")+1));
                }
                if(inst.equalsIgnoreCase("pho"))
                {
                    i.envoiIp(commande.substring(commande.indexOf("~")+1),commande.substring(3,commande.indexOf("~")));
                }
                 if(inst.equalsIgnoreCase("par"))
                {
                    i.envoiIp2(commande.substring(3,commande.indexOf("~")),commande.substring(commande.indexOf("~")+1));
                }
                 if(inst.equalsIgnoreCase("sar"))
                {
                    i.stopVideo(commande.substring(3,commande.indexOf("~")));
                }
                if(inst.equalsIgnoreCase("fin"))
                {
                    String dest=commande.substring(commande.indexOf("~")+1);
                    client c=i.getClient(dest);
                    System.out.println("le pseudo est "+c.pseudo);
                    
                   i.envoiMessageClient(c.getSocket(),"fin");
                }
                if(inst.equalsIgnoreCase("fi2"))
                {
                    String dest=commande.substring(commande.indexOf("~")+1);
                    client c=i.getClient(commande.substring(3,commande.indexOf("~")));
                    client c2=i.getClient(dest);
                    
                    System.out.println("le pseudo est "+c.pseudo);
                    
                 //  i.envoiMessageClient(c.getSocket(),"fi2"+dest);
                   i.envoiMessageClient(c2.getSocket(),"fi2"+commande.substring(3,commande.indexOf("~")));
                   
                } 
                if(inst.equalsIgnoreCase("tpf"))
                {
                    i.envoiIp3(commande.substring(commande.indexOf(":")+1), commande.substring(commande.indexOf("~")+1,commande.indexOf(":")));
                }
                
                
               
                    
                
            }
        } catch (IOException ex) {
          //  System.out.println("client deconnecté");
                i.RemoveUserWhenException(ss);
                //i.EnvoiListGeneral(i.ListConnecte());
              //  i.EnvoiList();
            
           // Logger.getLogger(commande.class.getName()).log(Level.SEVERE, null, ex);
        }
         
    }
    
}
