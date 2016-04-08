/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

/**
 *
 * @author Cenyo
 */
import java.net.Socket;

/**
 *
 * @author Cenyo
 */
 public class client {
  private  Socket sock;
   public String pseudo;
  private  String ipPseudo;
   private String nomPC;
   private String salon;
    public client(Socket s,String pseud,String ip,String nom,String sal)
    {
        sock=s;
        pseudo=pseud;
        ipPseudo=ip;
        nomPC=nom;
        salon=sal;
        
    }
    public void setSocket(Socket socket)
	{
		sock =  socket;
	}
    public String getSalon()
    {
        return salon;
    }
    public void setSalon(String sal)
    {
        salon=sal;
    }
	
	public void setUserName(String UserName)
	{
		pseudo = UserName;
	}
	
	
	
	public Socket getSocket()
	{
		return sock;
	}
        public String getnomPC()
	{
		return nomPC;
	}
        public String getIP()
	{
		return ipPseudo;
	}
	
	public String getUserName()
	{
		return pseudo;
	}
    
    
}
