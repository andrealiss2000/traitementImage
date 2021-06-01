package appli;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;
import traitementImageProject.HistogramTools;
import traitementImageProject.NoiseTools;
import traitementImageProject.TraitementImagesUtils;

public class Appli {
	
	public static void main(String[] args) {
		
		//test de lecture d'image 
		//TraitementImagesUtils.readImage("E:/Licence IOT/PERIODE E/Programmation Avancée/workspace/traitementImage/traitementImageProject/database/broad/0001.png");
		
		//Test filtre médian 
		Image couleur = TraitementImagesUtils.readImage("E:\\Licence IOT\\PERIODE E\\Programmation Avancée\\workspace\\traitementImageOld\\traitementImageProject\\database\\maldive.jpg");
		Image newImage = NoiseTools.addNoise(couleur, 0.2);
		//Viewer2D.exec(newImage);
		
		Image result = TraitementImagesUtils.filtreMedian(newImage);
		//Viewer2D.exec(result);
		
		double[][] histo = TraitementImagesUtils.getHistogram(result);
		double[][] newHisto = TraitementImagesUtils.discretize(histo); 
		TraitementImagesUtils.displayHistogram(newHisto);
		
		
	
	
	}

}
