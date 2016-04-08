package cchat1.Client.Apel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;
public final class fenetreApel extends JPanel{
  private  JPanel pan=new JPanel();
  public  JLabel jj=new JLabel();
   public JComboBox list=new JComboBox();
  public  JButton arreter=new JButton("Racrocher");
  public  JButton partage=new JButton("partage d'écran");
  public JLabel photo=new JLabel();
 public JPanel sud=new JPanel(new GridLayout(1,2));
  private  Font  police=new Font("Arial",Font.BOLD,16);
 public compteurApel c;
 public boolean commence=false;
  public  fenetreApel()
    {
       partage.setEnabled(false); 
        setSize(300,150);
        pan.setLayout(new BorderLayout());
        jj.setFont(police);     
        pan.setBackground(Color.WHITE); 
         sud.add(partage);
         sud.add(arreter);
        
         
        this.add(pan);
 
    }
    public void start(fenetreApel f)
    {
      //   c=new compteurApel(f);
    }
}
