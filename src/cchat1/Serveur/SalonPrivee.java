/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

/**
 *
 * @author cenyo
 */
public class SalonPrivee extends Salon{
   public String motDePasse;
    public SalonPrivee(String name,String passe)
    {
        super(name);
        motDePasse=passe;
    }
    public String getmotDePasse()
    {
        return motDePasse;
    }
}
