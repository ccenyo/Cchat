package cchat1.Client.Fichier;

import cchat1.Client.FenetresPrincipaux.priveeMessage;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Date;
import java.util.Vector;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;



/**
 * Classe de transfert de fichier via Socket (protocole TCP).<br />
 * Pour transf�rer un fichier, l'exp�diteur ou le destinataire devra cr�er un serveur et l'autre s'y connecter.<br /><br />
 * Exemple : le destinateur cr�� le serveur<br />
 * Description du protocole du destinataire :<br />
 * <ul>
 * <li>Cr�ation du serveur</li>
 * <li>Attente de connexion</li>
 * <li>Cr�ation des flux</li>
 * <li>Attendre la taille du buffer</li>
 * <li>Attendre le nom & la taille du fichier</li>
 * <li>R�ception du fichier</li>
 * <li>Fermeture des flux</li>
 * </ul>
 * Description du protocole de l'exp�diteur :<br />
 * <ul>
 * <li>Connexion au serveur</li>
 * <li>Cr�ation des flux</li>
 * <li>Envoi de la taille du buffer</li>
 * <li>Envoi du nom & de la taille du fichier</li>
 * <li>Envoi du fichier</li>
 * <li>Fermeture des flux</li>
 * </ul>
 *
 * @author Alexandre SCHMIDT
 * @version 2.0
 */

public class Transfert
{
    public    JLabel infoEnvoi=new JLabel();
 public   JLabel progression=new JLabel();
public JPanel cont=new JPanel();
    private static final int OK=1, STOP=2;
    private boolean transfert = false;

    private Vector<TransfertListener> listeners = new Vector<TransfertListener>();

    private int buffer_size;               // Taille du buffer

    private ServerSocket ss;               // ServerSocket
    private int port_serveur;
	
    private Socket socket;                 // Socket de r�ception et d'envoi
    private DataInputStream input;         // Flux d'entr�e pour la r�ception et l'envoi
    private DataOutputStream output;       // Flux de sortie pour la r�ception et l'envoi

    private long taille_traite;            // Taille trait�e
    private String vitesse = "";           // Vitesse de transfert

    private File file;

priveeMessage priv;
    /**
     * Constructeur principal
     * @param buffer taille du buffer d'envoi
     * @param port port sur lequel on souhaite cr�er un serveur
     */
public JProgressBar prog=new JProgressBar(0,100);
public   JButton cancel=new JButton("Annuler");
 private  JLabel reception=new JLabel();
    public Transfert(int buffer, int port)
    {
	buffer_size = buffer;
	port_serveur = port;
       
                     prog.setBounds(5,395,290,10);
        infoEnvoi.setBounds(5, 405, 230, 15);
        reception.setBounds(5, 405, 230, 15);
        progression.setBounds(240, 405, 100, 15);
        cancel.setBounds(240, 415, 60, 15);
        cancel.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
             
                   closeFlux();
                
            }
        } );
    }
    public void initPanel(priveeMessage p)
    {
         priv=p;
    }


    /**
     * Constructeur principal
     * @param port port sur lequel on souhaite cr�er un serveur
     */
    public Transfert(int port)
    {
	port_serveur = port;
    }


    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************
    //
    //                                      GESTION DES FLUX
    //
    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************


    /**
     * Cr�ation du ServeurSocket
     * @return true si la cr�ation s'est bien pass�
     */
    public boolean openServeur()
    {
	boolean b = true;
	try{
	    ss = new ServerSocket(port_serveur);
	}catch(Exception e){
	    b = false;
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
	return b;
    }


    /**
     * Attente de connexion
     */
    public boolean waitConnect()
    {
	boolean b = true;
	try{
	    socket = ss.accept();
	}catch(SocketException e){
	    b = false;
	    fireTransfertEvent( "stop" );
	}catch(Exception e){
	    b = false;
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
	return b;
    }


    /**
     * Connexion au serveur
     * @param ip adresse IP du serveur
     * @return true si la connexion c'est bien pass�
     */
    public boolean connect(String ip)
    {
	boolean b = true;
	try{
	    socket = new Socket(ip, port_serveur);
	}catch(Exception e){
	    b = false;
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
	return b;
    }


    /**
     * Ouverture des flux d'entr�e et sortie
     */
    public void openFlux()
    {
	try{
	    input = new DataInputStream(socket.getInputStream());
	    output = new DataOutputStream(socket.getOutputStream());
	}catch(Exception e){
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
    }


    /**
     * Fermer les flux
     */
    public void closeFlux()
    {
        
	try{
	    if( input!=null )
		input.close();      // Fermeture du flux d'entr�e
	    if( output!=null )
		output.close();     // Fermeture du flux de sortie
	    if( socket!=null)
		socket.close();     // Fermeture du socket
	    if( ss!=null )
		ss.close();         // Fermeture du serveur
	}catch(Exception e){
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
        
    }


    /**
     * Stopper la transfert
     */
    public void stopTransfert()
    {
	if(!transfert && ss!=null)
	    closeFlux();
	else
	    transfert = false;
    }


    /**
     * Supprimer le fichier re�u lors d'un �chec
     * @return true si le suppression s'est bien pass�e
     */
    private boolean delFile()
    {
	return file.delete();
    }


    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************
    //
    //                                      RECEPTION DU FICHIER
    //
    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************


    /**
     * Attendre la taille du buffer de l'envoyeur
     */
    public boolean waitTailleBuffer()
    {
	boolean ok = true;
	try{
	    signalerOK(); // Dire que l'on est pret � lire

	    buffer_size = input.readInt();
	}catch(Exception e){
	    fireTransfertEvent( "erreur" );
	    ok = false;
	}

	return ok;
    }


    /**
     * Attente du nom du fichier
     * @return le nom du fichier qui va �tre envoy�
     */
    public String waitNom()
    {
	String fileName="";
	try{
	    // R�ception du nom dans un tableau de bytes et conversion en chaine de caract�re
	    byte[] recu = new byte[buffer_size];
	    
	    signalerOK(); // Dire que l'on est pret � lire
	    
	    int i=input.read(recu, 0, recu.length);
	    fileName = new String(recu, 0, i);
	    
	}catch(Exception e){
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
	
	return fileName;
    }
    
    
    /**
     * Attente de la taille du fichier
     * @return la taille du fichier qui va �tre envoy�
     */
    public long waitTaille()
    {
	long taille=0;
	try{
	    signalerOK(); // Dire que l'on est pret � lire
	    
	    taille = input.readLong();
	}catch(Exception e){
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
	
	return taille;
    }
    
    
    /**
     * R�ception du fichier
     * @param dest R�pertoire de destination du fichier
     * @param fileName nom du fichier t�l�charg�
     */
    public void reception(String dest, String fileName,long tai)
    {
        initComposants("Reception");
       // priv.prog=prog;
       infoEnvoi.setText("reception de "+fileName); 
        prog.setMaximum((int)tai);
	transfert = true;
	try{
	    PrintStream ps = new PrintStream(new FileOutputStream( file = new File(dest+File.separator+fileName)));

	    long t1=new Date().getTime();
	    
	    boolean stoppe = false;
	    String mess="";
	    byte[] recu = null;
	    int i=0;
	    // Tant que l'on ne re�oit pas le message de fin de reception
	    taille_traite = 0;
	    while( !mess.equalsIgnoreCase("[END]") && !mess.equalsIgnoreCase("[STOP]") )
	    {
		if(transfert)
		{
		    signalerOK(); // Dire que l'on est pret � lire
						
		    recu = new byte[buffer_size]; // Cr�ation d'un nouveau tableau
		    i=input.read(recu, 0, recu.length); // Lecture des donn�es
		    taille_traite += i;
                  prog.setValue((int)this.getTailleTraite()); 
                   
		    // Calcul de la vitesse de transfert
		    calculerVitesse(t1);
		   progression.setText(vitesse); 
		    if(i>0) // Si on a lu des donn�es
		    {
			mess = new String(recu, 0, i);
			if( mess.equalsIgnoreCase("[END]") ) // Si c'est la fin de l'envoi
                        {
                               closeFlux();
                               removeComposants();
                            fireTransfertEvent("end");
                        }
			else if( mess.equalsIgnoreCase("[STOP]") ) // Si l'envoi est int�rrompu
			{
                            closeFlux();
                               removeComposants();
			    fireTransfertEvent("stop");
			    stoppe = true;
			}
			else // Sinon �criture dans le fichier
			{
			    ps.write(recu,0,i);
			    ps.flush();
			}
		    }
		}
		else
		{
		    signalerSTOP();
		    transfert = true;
		    stoppe = true;
		}
	    }
	    
	    // Fermeture des flux vers le fichier
	    ps.close();
	    if( stoppe )
		delFile();
	}catch(Exception e){
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	    delFile();
	}
           closeFlux();
         removeComposants();
    }
    
    
    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************
    //
    //                                       ENVOI DU FICHIER
    //
    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************
    

    /**
     * Envoi de la taille du buffer
     * @return true sur l'envoi s'est bien pass�
     */
    public boolean envoiTailleBuffer()
    {
	boolean b = true;
	try{
	    switch( attendreOK() )
	    {
	    case Transfert.OK:
		output.writeInt( buffer_size );
		output.flush();
		break;
	    case Transfert.STOP:
		fireTransfertEvent( "stop" );
		break;
	    default:
		break;
	    }
	}catch(Exception e){
	    b = false;
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
	return b;
    }


    /**
     * Envoi du nom du fichier
     * @param file fichier qui sera envoy�
     * @return true si l'envoi s'est bien pass�
     */
    public boolean envoiNom(File file)
    {
         System.out.println("envoi du nom");
	boolean b = true;
	try{
	    switch( attendreOK() )
	    {
	    case Transfert.OK:
		// Conversion du nom en un tableau de byte puis envoi
		output.write( (file.getName()).getBytes() );
		output.flush();
		break;
	    case Transfert.STOP:
		fireTransfertEvent( "stop" );
		break;
	    default:
		break;
	    }
	}catch(Exception e){
	    b = false;
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
	return b;
    }
    
    
    /**
     * Envoie de la taille du fichier
     * @param file fichier que sera envoy�
     * @return true si l'envoie c'est bien pass�
     */
    public boolean envoiTaille(File file)
    {
         System.out.println("envoi taille");
	boolean b = true;
	try{
	    switch( attendreOK() )
	    {
	    case Transfert.OK:
		output.writeLong( file.length() );
		output.flush();
		break;
	    case Transfert.STOP:
		fireTransfertEvent( "stop" );
		break;
	    default:
		break;
	    }
	}catch(Exception e){
	    b = false;
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
	return b;
    }
    
    
    /**
     * Envoi du fichier
     * @param file Fichier qui va �tre envoy�
     */
    public void envoi(File file)
    {
        this.initComposants("Envoie");
	transfert = true;
       infoEnvoi.setText("Envoie de "+file.getName()); 
prog.setMaximum((int)file.length());
	FileInputStream fin = null;
	byte[] envoi = null;
	int i = 0;
	try{
	    fin = new FileInputStream(file);
	    
	    long t1=new Date().getTime();

	    // Tant qu'on est pas � la fin du fichier
	    taille_traite = 0;
	    while( transfert && fin.available()!=0 )
	    {
		envoi = new byte[buffer_size];
		i = fin.read(envoi, 0, envoi.length);
		taille_traite += i;
               // System.out.println("avancement "+this.getTailleTraite());
               prog.setValue((int)this.getTailleTraite()); 
                progression.setText(vitesse); 
		// Calcul de la vitesse de transfert
		calculerVitesse(t1);

		if( i>0 ) // Si le tableau n'est pas vide en envoi les donn�es
		{
		    switch( attendreOK() )
		    {
		    case Transfert.OK:
			output.write(envoi,0,i);
			output.flush();
			break;
		    case Transfert.STOP:
			stopTransfert();
			break;
		    default:
			break;
		    }
		}
	    }

	    attendreOK();

	    String mess = "";
	    if( transfert )
	    {
		mess = "[END]";
		fireTransfertEvent("end");
	    }
	    else
	    {
		mess = "[STOP]";
                closeFlux();
                               removeComposants();
		fireTransfertEvent("stop");
	    }
            
	 //    removeComposants();
	    // Envoi du message de fin d'envoi
	    output.write(new String(mess).getBytes());
	    output.flush();
	    
	    fin.close();
            System.out.println("envoi terminer");
	}catch(Exception e){
	    e.printStackTrace();
	    fireTransfertEvent( "erreur" );
	}
     closeFlux();
         removeComposants();
      
    }
    
    
    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************
    //
    //                                          FONCTIONS
    //
    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************


    /**
     * Mettre en forme la taille d'un fichier
     * @param f fichier dont on souhaite connaitre la taille
     * @return la taille sous forme de chaine de caract�re
     */
    public static String getTailleFile(File f)
    {
	return Transfert.getTailleFile(f.length());
    }
    
    
    /**
     * Mettre en forme la taille d'un fichier
     * @param taille taille de fichier en oct�
     * @return la taille sous forme de chaine de caract�re
     */
    public static String getTailleFile(long taille)
    {
	if(taille >= 1024*1024*1024)
	    return ""+(taille/1024/1024/1024)+"."+(taille/1024/1024-1024*(taille/1024/1024/1024))/100+" Go";
	else if(taille >= 1024*1024)
	    return ""+(taille/1024/1024)+"."+(taille/1024-1024*(taille/1024/1024))/100+" Mo";
	else if(taille >= 1024)
	    return ""+(taille/1024)+"."+(taille-1024*(taille/1024))/100+" Ko";
	return ""+taille+" oct";
    }
    
    
    /**
     * Attendre confirmation de reception du paquet
     * @return Transfert.OK ou Transfert.STOP
     */
    public int attendreOK() throws Exception
    {
	return input.readInt();
    }
    
    
    /**
     * Signaler que l'on est pr�t � recevoir le paquet
     */
    public void signalerOK() throws Exception
    {
	output.writeInt(Transfert.OK);
	output.flush();
    }


    /**
     * Signaler que l'on stop l'envoi
     */
    public void signalerSTOP() throws Exception
    {
	output.writeInt(Transfert.STOP);
	output.flush();
    }
    
    
    /**
     * Connaitre la taille d�j� trait�e
     * @return un long
     */
    public long getTailleTraite()
    {
	return taille_traite;
    }


    /**
     * Calculer la vitesse de transfert
     */
    private void calculerVitesse(long t1)
    {
	long t2, t, v;
	t2 = new Date().getTime();
	t = (t2 - t1)/1000;
	if(t==0) t = 1;
	v = taille_traite/t;
	vitesse = getTailleFile(v)+"/s";
    }


    /**
     * Connaitre la vitesse de transfert
     * @return la vitesse dans un chaine de caract�re
     */
    public String getVitesse()
    {
	return vitesse;
    }
    
    
    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************
    //
    //                                          EVENEMENTS
    //
    // ******************************************************************************************
    // ******************************************************************************************
    // ******************************************************************************************
	
	
    /**
     * Ajouter une listener
     * @param l TransfertListener � ajouter
     */
    public void addTransfertListener(TransfertListener l)
    {
	listeners.add(l);
    }
    
    
    /**
     * Supprimer un listener
     * @param l TransfertListener � supprimer
     */
    public void removeTransfertListener(TransfertListener l)
    {
	listeners.remove(l);
    }
    
    
    /**
     * Supprimer tous les listeners
     */
    public void removeAllListener()
    {
	listeners.clear();
    }
    
    
    /**
     * D�clencher le listener
     * @param type "end" si find de transfert / "stop" si arr�t de transfert / "erreur" si une erreur survient erreur
     */
    protected void fireTransfertEvent(String type)
    {
	TransfertListener listener = null;
	for( int i = 0; i < listeners.size(); i++ )
	{
            listener = listeners.elementAt( i );
	    if( type.equalsIgnoreCase("end") )
		listener.end( new TransfertEvent( this ) );
	    else if( type.equalsIgnoreCase("stop") )
		listener.stop( new TransfertEvent( this ) );
	    else if( type.equalsIgnoreCase("erreur") )
		listener.erreur( new TransfertEvent( this ) );
        }
    }
    public void initComposants(String msg)
    {
        
        System.out.println("initiation des composants");
        cont.add(infoEnvoi);
              cont.add(prog);
              cont.add(progression);
               cont.add(cancel);
              prog.setStringPainted(true);
               cont.repaint();
               
                priv.prive.Tab.addTab(msg,cont);
             priv.prive.Tab.setBackground(Color.GRAY); 
	prog.setIndeterminate(false);
        priv.prive.Tab.setVisible(true);
        priv.prive.Tab.repaint();
    }
    public void removeComposants()
    {
        cont.remove(infoEnvoi);
              cont.remove(prog);
             cont.remove(progression);
               cont.remove(cancel);
             cont.repaint();
               priv.prive.Tab.removeTabAt(priv.prive.Tab.indexOfComponent(cont)); 
               if(priv.prive.Tab.getTabCount()==0)
                priv.prive.Tab.setVisible(false);
                priv.prive.Tab.repaint();
    }
}
