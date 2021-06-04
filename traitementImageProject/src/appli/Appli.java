package appli;

import java.util.Map;

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
		Image couleur = TraitementImagesUtils.readImage("C:\\Users\\Andréa\\Desktop\\maldive.jpg");
		Image newImage = NoiseTools.addNoise(couleur, 0.2);
		//Viewer2D.exec(newImage);
		
		Image result = TraitementImagesUtils.filtreMedian(newImage);
		//Viewer2D.exec(result);
		
		double[][] histo = TraitementImagesUtils.getHistogram(result);
		double[][] newHisto = TraitementImagesUtils.discretize(histo); 
		TraitementImagesUtils.displayHistogram(newHisto);
		
		//test normalisation 
		int nbPixels = result.getNumberOfPresentPixel();
		TraitementImagesUtils.displayHistogram(TraitementImagesUtils.normalise(newHisto, nbPixels));
		
		//test similarité
		
		Map<Double, String> similar = TraitementImagesUtils.getSimilarImages(result);
		 for (Map.Entry<Double, String> entry : similar.entrySet()) {
		        System.out.println("Distance : " + entry.getKey() + " Fichier : " + entry.getValue());
		    }
		       
		
		
		
	
	
	}

}
