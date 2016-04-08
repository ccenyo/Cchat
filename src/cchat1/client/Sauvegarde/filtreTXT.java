/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client.Sauvegarde;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Cenyo
 */
public class filtreTXT extends FileFilter{
    private String extension = ".txt", description = "Fichier texte";
	
	public filtreTXT(String ext, String descrip){
		this.extension = ext;
		this.description = descrip;
	}
	
	public boolean accept(File file){
		return (file.isDirectory() || file.getName().endsWith(this.extension));
	}
	
	public String getDescription(){
		return this.extension + " - " + this.description;
	}	
}

