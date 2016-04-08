/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cchat1.Client;

import java.awt.FileDialog;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.swing.JFrame;
import sun.audio.*;

/**
 *
 * @author Cenyo
 */
public class gestionDuSon {
// Le fichier "xxx.au" à ouvrir
 private File fichier = null;
 private String chemin;
 // les données audio…
private AudioData donnees = null;
 // le flux d’entrée pour ces données
private InputStream inputStr = null;
private JFrame fenetre;
public gestionDuSon(String chemins,JFrame f)
{
    fenetre =f;
    chemin=chemins;
}
public void open() {
 // "this" si la classe dans laquelle se trouve la méthode étend de Jframe ou Frame, ce qui est le cas classique
// FileDialog fd = new FileDialog(fenetre, "Sélectionner un fichier AU");
 // afficher le FileChooser…
// fd.show();
 try {
 // assigner à notre fichier de départ, qui était "null" jusqu’à présent, un fichier réel
 fichier = new File(chemin);
 // si l’assignation a bien fonctionné, le fichier n’est plus "null", donc:
 if (fichier != null) {
 // ouvrir notre flux d’entrée sur ce fichier
 FileInputStream fis = new FileInputStream(fichier);
 // créer un flux d’entrée spécial pour les fichiers audio
 AudioStream as = new AudioStream(fis);
 // et rediriger ce flux vers notre objet AudioData
 donnees = as.getData();
 
 }
 }
 catch (IOException e) {
 System.err.println(e);
 }
 }
 /*
 * Cette méthode permet de jouer le son enregistré dans notre fichier
 */
 public void play() {
 // si une lecture est déjà en cours, l’arrêter
 stop();
 // s’il n’y a aucun son à jouer, ouvrir un fichier
 if (donnees == null) open();
 // s’il y a des données à jouer:
 if (donnees != null) {
 // Créer un flux de données audio, lancer la lecture de ce flux, et le rediriger dans notre flux d’entrée
 AudioDataStream ads = new AudioDataStream(donnees);
 // lancer la lecture:
 AudioPlayer.player.start(ads);
 inputStr = ads;
 }
 }
 /*
 * Arrete de jouer le son en cours
 */
 public void stop() {
 if (inputStr != null) {
 AudioPlayer.player.stop(inputStr);
 inputStr = null;
 }
 }
 /*
 * Jouer le son de manière continue (facultatif)
 */
 public void loop() {
 stop();
 if (donnees == null) open();
 if (donnees != null) {
 // pour jouer un son de manière continue, on a besoin d’un flux spécial:
 // le ContinuousAudiodataStream, créé avec notre objet AudioData comme paramètre
 ContinuousAudioDataStream cads = new ContinuousAudioDataStream(donnees);
 // lancer la lecture:
 AudioPlayer.player.start(cads);
 inputStr = cads;
 }
 } 

}
