package appli;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.media.jai.operator.MinDescriptor;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.visualisation.Viewer2D;
import ihm.Result;
import traitementImageProject.HistogramTools;
import traitementImageProject.Indexation;
import traitementImageProject.NoiseTools;
import traitementImageProject.QualityEvaluator;
import traitementImageProject.TraitementImagesUtils;

public class Appli {
	
	private static String QUERY_CATEGORY="motos";
	private static String MODE="hsv";
	
	public static void main(String[] args) {
		if(args.length!=0) {
			QUERY_CATEGORY = args[0];
			MODE = args[1];
		}else {
			System.out.println("Veuillez indiquer en paramètre la catégorie de l'image de requête + le mode de calcul des distances");
			//return;
		}
		
		//test similarité
		Image result;       
		List<String> imagesResultat = new ArrayList<>();
		Indexation.writeHistograms();
		Indexation.writeHistogramsHSV();
		result = TraitementImagesUtils.getImageQuery();
		if(result.getBDim() == 3) {
			Map<Double, String> similar = TraitementImagesUtils.getSimilarImages(result, MODE);
			for (Map.Entry<Double, String> entry : similar.entrySet()) {
		        System.out.println("Distance : " + entry.getKey() + " Fichier : " + entry.getValue());
		        imagesResultat.add(entry.getValue());
		    }
			
			System.out.println("Qualité du système : "+ QualityEvaluator.evaluate(QUERY_CATEGORY, similar) + "% de fiabilité dans l'espace "+ MODE);
		}
		
		Result r = new Result(TraitementImagesUtils.getImageQueryPath(), imagesResultat);

	}

	
	public static String getMode() {
		return MODE;
	}
}
