package cchat1.Client.Fichier;

import java.util.EventObject;


/**
 * Ev�nement du transfert de fichier
 * @author Alexandre SCHMIDT
 * @version 1.0
 */
public class TransfertEvent extends EventObject
{

	/**
	* Constructeur principal
	* @param source source de l'�v�nement
	*/
	public TransfertEvent(Object source)
	{
		super(source);
	}
}
