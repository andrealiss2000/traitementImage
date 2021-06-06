package appli;

import java.util.Map;

import javax.media.jai.operator.MinDescriptor;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;
import traitementImageProject.HistogramTools;
import traitementImageProject.Indexation;
import traitementImageProject.NoiseTools;
import traitementImageProject.TraitementImagesUtils;

public class Appli {
	
	public static void main(String[] args) {
		
		//test de lecture d'image 
		//TraitementImagesUtils.readImage("E:/Licence IOT/PERIODE E/Programmation Avancée/workspace/traitementImage/traitementImageProject/database/broad/0001.png");
		
		//Test filtre médian  
		Image couleur = TraitementImagesUtils.readImage("C:\\Users\\d_vig\\Downloads\\kurtz_image\\maldive.jpg"); 
		Image newImage = NoiseTools.addNoise(couleur, 0.2);
		//Viewer2D.exec(newImage);
		
		Image result = TraitementImagesUtils.filtreMedian(newImage);
		Viewer2D.exec(result);
		
		//double[][] histo = TraitementImagesUtils.getHistogram(result);
		//double[][] newHisto = TraitementImagesUtils.discretize(histo); 
		//TraitementImagesUtils.displayHistogram(newHisto);
		
		//double[][] histoHSV = TraitementImagesUtils.getHistogramHSV(result);
		//double[][] newHistoHSV = TraitementImagesUtils.discretizeHSV(histoHSV); 
		//TraitementImagesUtils.displayHistogram(newHistoHSV);
		
		
		//test normalisation 
		int nbPixels = result.getNumberOfPresentPixel();
		//TraitementImagesUtils.displayHistogram(TraitementImagesUtils.normalise(newHisto, nbPixels));  
		//TraitementImagesUtils.displayHistogram(TraitementImagesUtils.normalise(newHistoHSV, nbPixels));
		
		//test similarité
		//Indexation.writeHistograms("rgb");
		Indexation.writeHistograms("hsv");
		result = TraitementImagesUtils.getImageQuery();
		//Map<Double, String> similar = TraitementImagesUtils.getSimilarImages(result, "rgb");
		Map<Double, String> similar = TraitementImagesUtils.getSimilarImages(result, "hsv");
		for (Map.Entry<Double, String> entry : similar.entrySet()) {
	        System.out.println("Distance : " + entry.getKey() + " Fichier : " + entry.getValue()); 
	        Image az = TraitementImagesUtils.readImage(entry.getValue());
	        Viewer2D.exec(az);
	    }
		
	
	
	}

}
