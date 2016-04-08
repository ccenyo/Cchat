package cchat1.Client.Fichier;

import java.util.EventListener;


/**
 * Listener des �v�nements du tranfert
 * @author Alexandre SCHMIDT
 * @version 1.0
 */
public interface TransfertListener extends EventListener
{

	/**
	* Ev�nement de fin de fichier
	* @param e ev�nement
	*/
	public void end(TransfertEvent e);
	
	
	/**
	* Ev�nement d'arr�t de transfert
	* @param e ev�nement
	*/
	public void stop(TransfertEvent e);
	
	
	/**
	* Ev�nement d'erreur de transfert
	* @param e ev�nement
	*/
	public void erreur(TransfertEvent e);
}
