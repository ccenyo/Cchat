/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Cenyo
 */
 public class init
           {
       
    private fn ss;
   private  Thread t1;
  private		ServerSocket socketserver  ;
private		Socket socketduserveur ;
private		BufferedReader in;
private		ObjectOutputStream out2;
private ArrayList cli;

       init(ArrayList c,fn f)
               
       {
           cli=c;
           ss=f;
          System.out.println("constructeur init demarrer"); 
       }
       public void AjouterSalon(String nomSalon,String nomCreateur)
       {
           ss.n++;
            ss.salo[ss.n-1]=new Salon(nomSalon);
            ss.salo[ss.n-1].setPhrase(ss.salo[ss.n-1].getPhrase()+" créer par "+nomCreateur);  
               JList s=new JList();
               ss.scrols[ss.n-1]=s;
                JScrollPane js=new JScrollPane(ss.scrols[ss.n-1]);
                JPanel cent2=new JPanel(new BorderLayout());
                cent2.add(js,BorderLayout.CENTER);
                ss.salon[ss.n-1]=cent2;
                //connect[n-1]=temp;
                ss.onglet.add(ss.salo[ss.n-1].getNom(),ss.salon[ss.n-1]);
                 changeSalon(nomCreateur,nomSalon)   ;
                ss.d.EnvoiListGeneral("]"+ss.d.ListSalon());
       }
       public void AjouterSalonPrivee(String nomSalon,String nomCreateur,String pw)
       {
           ss.n++;
            ss.salo[ss.n-1]=new SalonPrivee(nomSalon,pw);
            ss.salo[ss.n-1].setPhrase(ss.salo[ss.n-1].getPhrase()+" créer par "+nomCreateur);  
               JList s=new JList();
               ss.scrols[ss.n-1]=s;
                JScrollPane js=new JScrollPane(ss.scrols[ss.n-1]);
                JPanel cent2=new JPanel(new BorderLayout());
                cent2.add(js,BorderLayout.CENTER);
                ss.salon[ss.n-1]=cent2;
                //connect[n-1]=temp;
                ss.onglet.add(ss.salo[ss.n-1].getNom(),ss.salon[ss.n-1]);
                 changeSalon(nomSalon,nomCreateur,pw)   ;
                ss.d.EnvoiListGeneral("]"+ss.d.ListSalon());
            
       }
       public boolean veificationPseudo(String pseudo,String ip,Socket s)
       { 
           boolean re=false;
           for(int i=0;i<ss.n;i++)
           {
            
           if(ss.salo[i].list.contains(pseudo))
           {
               for(int o=0;o<ss.salo[i].list.size();o++)
               {
                   client cl=(client)getClient((String)ss.salo[i].list.get(o));
                   if(cl.getUserName().equals(pseudo))
                   {
                   if(cl.getIP().equals(ip))
                   {
                       retirer(cl.getUserName());
                       envoiMessageClient(cl.getSocket(),"jvmVous avez été déconnecté par le serveur !!!");
                       this.EnvoiList();
                       re=false;
                       break;
                   }
                   else
                       re=true;
                   }
                   
               }
                
           }
              
          
           }
           return re;
       }
       
       protected void RemoveUserWhenException(Socket clientsocket)
	{
			
			client temp;
			for(int i = 0; i < cli.size(); i++)
			{
				temp = (client) cli.get(i);
				if(temp.getSocket().equals(clientsocket))
				{
					String m_RemoveUserName = temp.getUserName();
					String m_RemoveRoomName = temp.getSalon();
					retirer(m_RemoveUserName);					
					
					
					EnvoideconnecteGeneral(m_RemoveUserName);
					return;	
				}	
			}
	}
    public void Ajout(Socket s,String ps,String ip,String nom,String salon)
    {
        client c=new client(s,ps,ip,nom,salon);
          
        ss.salo[0].list.add(ps);        
           cli.add(c);
        ss.actualiser();
         
        
    }
    public int getNnbrClient(){
        return cli.size();
    }
    public int getNbrSalon(){
        return ss.n;
    }
   
    public String ListSalon()
    {
        String ret=ss.salo[0].getNom();
        for(int i=1;i<ss.n;i++)
        {
            ret=ret+"/"+ss.salo[i].getNom();
        }
        return ret; 
    }
    public void retirer(String p)
    {
        cli.remove(getClient(p));
        ss.connecte.remove(p);
        ss.connecte.trimToSize();
        ss.scrol.setListData(ss.connecte);
        cli.trimToSize();
                  for(int i=0;i<ss.n;i++)
           {
               
                   
                   ss.salo[i].list.remove(p);                    
                   ss.scrols[i].setListData(ss.salo[i].list); 
                   
               
                
           }
                  this.EnvoiList();
                  ss.actualiser();
    }
 public void EnvoiMessageGeneral(String msg,String pseudo)
  {
     client c;
      client env=getClient(pseudo);   
                int m_userListSize =cli.size();
				for(int i = 0; i < m_userListSize; i++)
		{ 
                       
			c = (client) cli.get(i);
                        if(env.getSalon().equals(c.getSalon()))
                        {
				System.out.println("Envoi  message à:"+c.pseudo); 			
				envoiMessageClient(c.getSocket(),"pub"+pseudo+":"+msg);
                        }
				
		}
		
		
		
  }
 public void EnvoideconnecteGeneral(String msg)
  {
     client c;
    // System.out.println("EnvoiMessageGeneral de marrer message:"+msg);   
                int m_userListSize =cli.size();
				for(int i = 0; i < m_userListSize; i++)
		{
			c = (client) cli.get(i);
                        
				System.out.println("Envoi  message à:"+c.pseudo); 			
				envoiMessageClient(c.getSocket(),"dec"+msg);	
				
		}
		
		
		
  }
 public void verificationGeneral()
  {
   client c;
      int m_userListSize =cli.size();
				for(int i = 0; i < m_userListSize; i++)
		{
			c = (client) cli.get(i);
                        envoiMessageClient(c.getSocket(),"ping");
                }
  }
  public void EnvoiconnecteGeneral(String msg)
  {
     client c;
              
                int m_userListSize =cli.size();
				for(int i = 0; i < m_userListSize; i++)
		{
			c = (client) cli.get(i);
				System.out.println("Envoi  message à:"+c.pseudo); 			
				envoiMessageClient(c.getSocket(),"con"+msg);	
				
		}
		
		
		
  }
   public void Envoisal(String msg,String pseudo)
  {
     client c;
      client env=getClient(pseudo);   
                int m_userListSize =cli.size();
				for(int i = 0; i < m_userListSize; i++)
		{
                       
			c = (client) cli.get(i);
                        if(env.getSalon().equals(c.getSalon()))
                        {
                            if(!pseudo.equals(c.getUserName()))
                            {
				System.out.println("Envoi  message à:"+c.pseudo); 			
				envoiMessageClient(c.getSocket(),msg);
                            }
                        }
				
		}
		
		
		
  }
  public void changeSalon(String pseudo,String nvoSalon)
  {
      
       EnvoiList();
      client c=getClient(pseudo);
String anc=c.getSalon();

     Salon nS=getSalon(nvoSalon);
  //   Object s=nS.getClass();
    if(nS.getClass().toString().equals("class cchat1.Serveur.SalonPrivee")) 
            {
            System.out.println("c'est privée");
          SalonPrivee  mS=(SalonPrivee)nS;
            System.out.println("mot de passe : "+mS.getmotDePasse());
            envoiMessageClient(c.getSocket(),"???"+nvoSalon+"|"+pseudo);
            }
    else
    {
        System.out.println("c'est public");
     Envoisal("adm"+pseudo+" à quitter ce salon",pseudo);
      c.setSalon(nvoSalon); 
      envoiMessageClient(c.getSocket(),nS.getPhrase());
      Envoisal("adm"+pseudo+" est dans ce salon",pseudo);
                for(int i=0;i<ss.n;i++)
           {
               if(ss.salo[i].getNom().equals(c.getSalon()))
               {
                   if(!ss.salo[i].getNom().contains(pseudo))
                   {
                   ss.salo[i].list.add(pseudo);
                   
                   ss.scrols[i].setListData(ss.salo[i].list); 
                   }
               }
                
           }
                     for(int i=0;i<ss.n;i++)
           {
               if(ss.salo[i].getNom().equals(anc))
               {
                   
                   ss.salo[i].list.remove(pseudo);                    
                   ss.scrols[i].setListData(ss.salo[i].list); 
                   
               }
                
           }
                     EnvoiList();
    }
                     
  }
  public void changeSalon(String nvoSalon,String pseudo,String pw)
  {
      System.out.println("salon 2");
      System.out.println("nom salon "+nvoSalon);
      System.out.println("nom "+pseudo);
      System.out.println("mot de passe "+pw);
        
     SalonPrivee nS=(SalonPrivee)getSalon(nvoSalon);
     System.out.println("mot de passe2 "+nS.getmotDePasse());
     if(nS.getmotDePasse().equalsIgnoreCase(pw))
     {
         EnvoiList();
      client c=getClient(pseudo);
String anc=c.getSalon();
     System.out.println("ancien salon "+anc);
      c.setSalon(nvoSalon); 
      envoiMessageClient(c.getSocket(),nS.getPhrase());
      Envoisal("adm"+pseudo+" est dans ce salon",pseudo);
                for(int i=0;i<ss.n;i++)
           {
               if(ss.salo[i].getNom().equals(c.getSalon()))
               {
                   if(!ss.salo[i].getNom().contains(pseudo))
                   {
                       System.out.println(pseudo+" ajouter a "+ss.salo[i].getNom());
                   ss.salo[i].list.add(pseudo);
                   
                   ss.scrols[i].setListData(ss.salo[i].list); 
                   }
               }
                
           }
                     for(int i=0;i<ss.n;i++)
           {
               if(ss.salo[i].getNom().equals(anc))
               {
                   System.out.println(pseudo+" retirer a "+ss.salo[i].getNom());
                   ss.salo[i].list.remove(pseudo);                    
                   ss.scrols[i].setListData(ss.salo[i].list); 
                   
               }
                
           }
     }
     else
     {
         //envoiMessageClient(c.getSocket(),"admMot de passe incorrect");
     }
      //EnvoiList();
      
  }
  
   public String ListConnecte()
    {
        return ss.salo[0].list.toString();
    } 
  public void EnvoiListGeneral(String msg)
  {
     client c;
     System.out.println("EnvoiMessageGeneral de marrer message:"+msg);   
                int m_userListSize =cli.size();
				for(int i = 0; i < m_userListSize; i++)
		{
			c = (client) cli.get(i);
				System.out.println("Envoi  message à:"+c.pseudo); 
                                   
				envoiMessageClient(c.getSocket(),msg);
                                
				
		}
		
		
		
  }
  public void EnvoiList()
  {
     client c;
    // System.out.println("EnvoiMessageGeneral de marrer message:"+msg);   
                int m_userListSize =cli.size();
				for(int i = 0; i < m_userListSize; i++)
		{
			c = (client) cli.get(i);
				System.out.println("Envoi  message à:"+c.pseudo); 
                                   for(int n=0;n<ss.n;n++)
                                   {
                                       if(c.getSalon().equals(ss.salo[n].getNom()))
                                       {
				envoiMessageClient(c.getSocket(),ss.salo[n].list.toString());
                                       }
                                   }
				
		}
		
		
		
  }
        public  void envoiMessageClient(Socket client,String message)
    {
        try {
          PrintWriter  out2 = new PrintWriter(client.getOutputStream());
            out2.println(message);
            out2.flush();
            System.out.println("envoiMessageClient demarrer message:"+message); 
        } catch (IOException ex) {
            
        }

    }
        public  client getClient(String pseud)
   {
       client clien=null;
       client TempoClient;
       int nbconecte=cli.size();
       for(int i=0;i<nbconecte;i++)
       {
           TempoClient=(client)cli.get(i);
           if(TempoClient.getUserName().equalsIgnoreCase(pseud))
           {
               clien=TempoClient;
               break;
           }
       }
       return clien;
   }
         public  Salon getSalon(String nom)
   {
       Salon clien=null;
       Salon TempoClient;
       int nbconecte=cli.size();
       for(int i=0;i<ss.n;i++)
       {
           TempoClient=ss.salo[i];
           if(TempoClient.getNom().equalsIgnoreCase(nom))
           {
               clien=TempoClient;
               break;
           }
       }
       return clien;
   }
 public void EnvoiMessagePrivee(String Message,String Destinataire,String envoyeur)
  {
      client c = getClient(Destinataire);
      
		if(c != null)
		{
			envoiMessageClient(c.getSocket(),"pri"+Message+"~"+Destinataire);	
		}
                
  }
 public void Envoifichier(String Destinataire,String dest)
 {
     client c = getClient(dest);
      
		if(c != null)
		{
			envoiMessageClient(c.getSocket(),"rec"+Destinataire+":"+dest);	
                        System.out.println("tra receptionner "+"rec"+Destinataire+":"+dest);    
		}
 }
 public void envoipret(String destinataire,String recev,String adress,String port)
 {
     client c = getClient(destinataire);
      
		if(c != null)
		{
			envoiMessageClient(c.getSocket(),"Env"+adress+"~"+recev+"|"+port);
                        System.out.println("adresse dans envoipret "+adress);
                        System.out.println("envoyeur dans envoipret "+recev);
                        
		}
 }
  public void envoiAnd(String destinataire,String recev,String adress,String port)
 {
     client c = getClient(destinataire);
      
		if(c != null)
		{
			envoiMessageClient(c.getSocket(),"Anv"+adress+"~"+recev+"|"+port);
                        System.out.println("adresse dans envoipret "+adress);
                        System.out.println("envoyeur dans envoipret "+recev);
                        
		}
 }
public void demandeDeConnexion(String Destinataire,String dest)
{
     client c = getClient(dest);
      
		if(c != null)
		{
			envoiMessageClient(c.getSocket(),"rea"+Destinataire+":"+dest);	
		}
}
public void ApelCommencer(String destinataire,String recev,String adress,String port)
{
     client c = getClient(destinataire);
      
		if(c != null)
		{
			envoiMessageClient(c.getSocket(),"AEC"+adress+"~"+recev+"?"+port);
                        System.out.println("adresse dans envoipret "+adress);
                        System.out.println("envoyeur dans envoipret "+recev);
                        
		}
}
public void quitterApel(String dest)
{
     client c = getClient(dest);
      
		if(c != null)
		{
			envoiMessageClient(c.getSocket(),"fin");
                        
                        
		}
}
public void envoiIp(String envoyer,String envoyeur)
{
    client c1=getClient(envoyer);
    client c2=getClient(envoyeur);
    envoiMessageClient(c1.getSocket(),"OIP"+c2.getUserName());
    envoiMessageClient(c2.getSocket(),"ipe"+c1.getIP()+"~"+c1.getUserName());
     
}
public void envoiIp2(String envoyer,String envoyeur)
{
    client c1=getClient(envoyer);
    client c2=getClient(envoyeur);
    envoiMessageClient(c1.getSocket(),"OI2"+c2.getUserName());
    envoiMessageClient(c2.getSocket(),"ip2"+c1.getIP()+"~"+c1.getUserName());
     
}
public void envoiIp3(String receveur,String envoyeur)
{
    System.out.println(receveur);
    System.out.println(envoyeur);
    client c1=getClient(receveur);
    client c2=getClient(envoyeur);
    envoiMessageClient(c1.getSocket(),"IP3:"+c2.getIP()+"~"+c2.getUserName());
  
     
}
public void stopVideo(String pseud)
{
     client c1=getClient(pseud);
     envoiMessageClient(c1.getSocket(),"sto");
     
}
public void envoiIp4(String envoyer,String port,String pseud)
{
    client c1=getClient(envoyer);
    client c2=getClient(pseud);
    envoiMessageClient(c1.getSocket(),"IPA"+port+"~"+c2.getIP()+"?"+pseud);
     
}
       
   }
