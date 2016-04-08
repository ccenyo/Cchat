/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Serveur;

import cchat1.Client.FenetresPrincipaux.connexion;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Dayane
 */
public class Rechercher implements Runnable{

    public connexion con;
    public int nbrServeur=0;
    public Object[][] cellule;
    
    public Rechercher(connexion c){
        con=c;
    }
    @Override
    public void run() {
        try {
            cellule=new Object[16][5];
            nbrServeur=0;
            InetAddress adrLocale = InetAddress.getLocalHost();
            String ip=adrLocale.getHostAddress();
            String reso=ip.substring(0,ip.lastIndexOf(".")+1);
//            System.out.println(reso);
            for(int i=1;i<255;i++)
        {
           new Thread(new connect(con,reso+i,this)).start();
        }
        } catch (UnknownHostException ex) {
            
        }
        
    }
    
}
class connect implements Runnable{
    String ip;
    connexion con;
    public PrintWriter out;
    public BufferedReader in;
    public String nom;
    public String nbrClient;
    public String nbrSalon;
    public String commentaire;
    public Vector vis=new Vector();
    public Rechercher rec;
    
    Socket s;
    
    connect(connexion c,String i,Rechercher r){
        rec=r;
        ip=i;
        con=c;
        
    }

    @Override
    public void run() {
         System.out.println(ip);
                try {
                    s= new Socket(ip,5050);
                    if(s.isConnected())
                {
               
//                    System.out.println("Connecte");
                    out=new PrintWriter(s.getOutputStream());
                    out.println("~~~");
                    out.flush();
                    in=new BufferedReader(new InputStreamReader(s.getInputStream()));
                    String message=in.readLine()+"!"+ip;
                    
                    nom=message.substring(message.indexOf(" "), message.indexOf("|"));
                    commentaire=message.substring(message.indexOf("|")+1, message.indexOf("*"));
                    nbrClient=message.substring(message.indexOf("*")+1, message.indexOf("/"));
                    nbrSalon=message.substring(message.indexOf("/")+1,message.indexOf("!"));                    
                    if(con.vec.size()!=0){
                    for(int i=0;i<rec.nbrServeur;i++){
                    String tmp=(String)rec.cellule[i][0];
                    System.out.println(tmp);
                    if(tmp.equals(nom))
                    {
                        con.vec.remove(con.vec.get(i));
                        con.vec.add(message);
                        con.list.setListData(con.vec);
                        rec.cellule[i][0]=nom;
                    rec.cellule[i][1]=nbrClient;
                    rec.cellule[i][2]=nbrSalon;
                    rec.cellule[i][3]=ip;
                    rec.cellule[i][4]=commentaire;
                    
                    con.ligne=rec.cellule;
                    table t=new table(rec.cellule,con.colonne);
                    con.table=new JTable(t);
                    con.scrol.setViewportView(con.table);
                    con.scrol.repaint();
                    
                        
                    }
                    else
                    {
                     con.vec.add(message);
                        con.list.setListData(con.vec);   
                        rec.cellule[rec.nbrServeur][0]=nom;
                    rec.cellule[rec.nbrServeur][1]=nbrClient;
                    rec.cellule[rec.nbrServeur][2]=nbrSalon;
                    rec.cellule[rec.nbrServeur][3]=ip;
                    rec.cellule[rec.nbrServeur][4]=commentaire;
                    
                    con.ligne=rec.cellule;
                    table t=new table(rec.cellule,con.colonne);
                    con.table=new JTable(t);
                    con.scrol.setViewportView(con.table);
                    con.scrol.repaint();
                    rec.nbrServeur++;
                    }
                }
                    }
                    else
                    {
                        con.vec.add(message);
                        con.list.setListData(con.vec);
                        rec.cellule[rec.nbrServeur][0]=nom;
                    rec.cellule[rec.nbrServeur][1]=nbrClient;
                    rec.cellule[rec.nbrServeur][2]=nbrSalon;
                    rec.cellule[rec.nbrServeur][3]=ip;
                    rec.cellule[rec.nbrServeur][4]=commentaire;
                    
                    con.ligne=rec.cellule;
                    table t=new table(rec.cellule,con.colonne);
                    con.table=new JTable(t);
                    con.scrol.setViewportView(con.table);
                    con.scrol.repaint();
                    rec.nbrServeur++;
                    }
                    System.out.println(message);
                    
                }
                } catch (IOException ex) {
                   
                }
                
    }
    
    
}
class table extends AbstractTableModel{
    private Object[][] data ;
    private String[] title;
    
    public table(Object [][] data,String [] title){
        this.data=data;
        this.title=title;
    }
    @Override
    public int getRowCount() {
        return this.data.length;
    }

    @Override
    public int getColumnCount() {
        return this.title.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return this.data[rowIndex][columnIndex];
    }
    @Override
    public String getColumnName(int col){
        return this.title[col];
    }
}