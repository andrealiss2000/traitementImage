package appli;

import java.util.Map;

import javax.media.jai.operator.MinDescriptor;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;
import traitementImageProject.HistogramTools;
import traitementImageProject.Indexation;
import traitementImageProject.NoiseTools;
import traitementImageProject.QualityEvaluator;
import traitementImageProject.TraitementImagesUtils;

public class Appli {
	
	private static String QUERY_CATEGORY="motos";
	
	public static void main(String[] args) {
		if(args.length!=0) {
			QUERY_CATEGORY = args[0];
		}else {
			System.out.println("Veuillez indiquer en paramètre la catégorie de l'image de requête");
			//return;
		}
		
		
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
			       
	
		Indexation.writeHistograms();
		result = TraitementImagesUtils.getImageQuery();
		if(result.getBDim() == 3) {
			Map<Double, String> similar = TraitementImagesUtils.getSimilarImages(result);
			for (Map.Entry<Double, String> entry : similar.entrySet()) {
		        System.out.println("Distance : " + entry.getKey() + " Fichier : " + entry.getValue());
		    }
			
			System.out.println("Qualité du système : "+ QualityEvaluator.evaluate(QUERY_CATEGORY, similar) + "% de fiabilité");
		}
		
	
	}

}
