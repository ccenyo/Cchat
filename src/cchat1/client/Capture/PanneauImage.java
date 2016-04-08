/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Capture;

/**
 *
 * @author Cenyo
 */
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class PanneauImage extends JComponent {
  private BufferedImage image;
  private double ratio;
  private JPanel f;
  PanneauImage(JPanel fra)
  {
      f=fra;
  }
   
  public void change(BufferedImage image) {
    if (image!=null) {
      this.image = image;
      ratio = (double)image.getWidth()/image.getHeight();  
    //   f.setSize(image.getWidth(), image.getHeight());
      repaint();
    }
  }
   
  @Override
  protected void paintComponent(Graphics surface) {
    if (image!=null)
    {
       

      surface.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);  
    }
    
  }   
}
