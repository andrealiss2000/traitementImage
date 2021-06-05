package traitementImageProject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.unistra.pelican.Image;

public class Indexation {
	
	private static String INDEXES_PATH = "indexes.txt";
	private static String FILE_SEPARATOR=";";


	private static String RGB_SEPARATOR = " ";
	
	
	public static void writeHistograms() {
		//récupération des images
		List<File> dbFiles = new ArrayList<>();//liste de toutes les images à comparer
		//récupérer toutes les images 
		 File directoryPath = new File(TraitementImagesUtils.getDB_PATH());
		//List of all files and directories
		 File filesList[] = directoryPath.listFiles();
		 for(File file : filesList) {
			 File innerFilesList[]; 
			 if(file.isDirectory()) {
				 dbFiles.addAll(Arrays.asList(file.listFiles()));
			 }else if(file.isFile()) dbFiles.add(file);
	      }
		getHistograms(dbFiles);
		 
	}
	


	public static void getHistograms(List<File> dbFiles) {
		try {
			 File directoryPath = new File(INDEXES_PATH);
			 if(!directoryPath.exists()) {
				 FileWriter myWriter = new FileWriter(INDEXES_PATH);
				for(File file : dbFiles) {
					Image img = TraitementImagesUtils.readImage(file.getAbsolutePath());
					if(img.getBDim() == 3) {
						Image filteredImage = TraitementImagesUtils.filtreMedian(img);
						double[][] histogram = TraitementImagesUtils.normalise(TraitementImagesUtils.discretize(TraitementImagesUtils.getHistogram(filteredImage)), img.getNumberOfPresentPixel());
						String line = file.getAbsolutePath();
				    	line+=FILE_SEPARATOR;
				    	for(int i =0; i< histogram.length;i++) {
				    	    	  line+=(histogram[i][0]);
				    	    	  line+=(RGB_SEPARATOR ); 
				    	    	  line+=(histogram[i][1]);
				    	    	  line+=(RGB_SEPARATOR); 
				    	    	  line+=(histogram[i][2]);
				    	    	  if(i < histogram.length -1)
				    	    		  line+=(FILE_SEPARATOR);
				    	    	  
				    	      }
								line += "\n";
				    	      myWriter.write(line);
				    	      
				    	      System.out.println("Successfully wrote to the file.");
				    	      
					}
				
			}
			myWriter.close();
			 }
			
		 } catch (IOException e) {
   	      System.out.println("An error occurred.");
   	      e.printStackTrace();
   	    }
	}
	
	public static String getINDEXES_PATH() {
		return INDEXES_PATH;
	}
	
	public static String getFILE_SEPARATOR() {
		return FILE_SEPARATOR;
	}



	public static String getRGB_SEPARATOR() {
		return RGB_SEPARATOR;
	}

}
