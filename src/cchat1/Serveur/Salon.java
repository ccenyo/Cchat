/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

import java.util.Vector;

/**
 *
 * @author cenyo
 */
public class Salon {
   public String nom;
  public  Vector list;
  public String phrase;
    Salon(String nam)
    {
        phrase="admBienvenue dans le salon du groupe "+nam;
        nom=nam;
        list=new Vector();
    }
    void setPhrase(String p)
    {
        phrase=p;
    }
    String getPhrase()
    {
        return phrase;
    }
    void ajouter(String no)
    {
        list.add(no); 
    }
    void retirer(String no)
    {
        list.remove(no); 
    }
    Vector getList()
    {
        return list;
    }
    String getNom()
    {
        return nom;
    }
    void setVector(Vector no)
    {
        list=no;
    }
    void setNom(String no)
    {
        nom=no;
    }
    
}
