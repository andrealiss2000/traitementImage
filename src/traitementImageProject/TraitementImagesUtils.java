package traitementImageProject;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import fr.unistra.pelican.ByteImage;
import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.io.ImageLoader;

public class TraitementImagesUtils {
	private static int SIZE_HISTO=10;
	private static String DB_PATH="database\\";
	private static String QUERY_PATH="query\\";
	private static int FACTOR=25;
	private static int FACTORHSV=36;




	/**
	 * Lecture d'une image
	 * @param absolutePath
	 * @return
	 */
	public static Image readImage(String absolutePath) {
		if(absolutePath == null) {
			System.out.println("Veuillez indiquer un chemin");
			return null; 
		}
		Image img = ImageLoader.exec(absolutePath);
		System.out.println("Image : " + img.getXDim() + "x"+ img.getYDim());
		System.out.println("Nombre de caneaux : "+ img.getBDim());
		return img; 
	}
	
	
	
	
	/**
	 * Filtre médian pour débruiter une image	
	 * @param img
	 * @return
	 */
	public static Image filtreMedian(Image img) {
			
			int ligne = img.getXDim();
			int colonne = img.getYDim();
			
			for(int i = 1; i < ligne-1; i++) {
				for(int j = 1; j < colonne-1; j++) {
					int p00 = img.getPixelXYByte(i-1, j-1);
					int r00 = img.getPixelXYBByte(i-1, j-1, 0);
					int g00 = img.getPixelXYBByte(i-1, j-1, 1);
					int b00 = img.getPixelXYBByte(i-1, j-1, 2);
					
					int p01 = img.getPixelXYByte(i-1, j);
					int r01 = img.getPixelXYBByte(i-1, j, 0);
					int g01 = img.getPixelXYBByte(i-1, j, 1);
					int b01 = img.getPixelXYBByte(i-1, j, 2);
					
					int p02 = img.getPixelXYByte(i-1, j+1);
					int r02 = img.getPixelXYBByte(i-1, j+1, 0);
					int g02 = img.getPixelXYBByte(i-1, j+1, 1);
					int b02 = img.getPixelXYBByte(i-1, j+1, 2);
					
					int p10 = img.getPixelXYByte(i, j-1);
					int r10 = img.getPixelXYBByte(i, j-1, 0);
					int g10 = img.getPixelXYBByte(i, j-1, 1);
					int b10 = img.getPixelXYBByte(i, j-1, 2);
					
					int p11 = img.getPixelXYByte(i, j);
					int r11 = img.getPixelXYBByte(i, j, 0);
					int g11 = img.getPixelXYBByte(i, j, 1);
					int b11 = img.getPixelXYBByte(i, j, 2);
					
					int p12 = img.getPixelXYByte(i, j+1);
					int r12 = img.getPixelXYBByte(i, j+1, 0);
					int g12 = img.getPixelXYBByte(i, j+1, 1);
					int b12 = img.getPixelXYBByte(i, j+1, 2);
					
					int p20 = img.getPixelXYByte(i+1, j-1);
					int r20 = img.getPixelXYBByte(i+1, j-1, 0);
					int g20 = img.getPixelXYBByte(i+1, j-1, 1);
					int b20 = img.getPixelXYBByte(i+1, j-1, 2);
					
					int p21 = img.getPixelXYByte(i+1, j);
					int r21 = img.getPixelXYBByte(i+1, j, 0);
					int g21 = img.getPixelXYBByte(i+1, j, 1);
					int b21 = img.getPixelXYBByte(i+1, j, 2);
					
					int p22 = img.getPixelXYByte(i+1, j+1);	
					int r22 = img.getPixelXYBByte(i+1, j+1, 0);
					int g22 = img.getPixelXYBByte(i+1, j+1, 1);
					int b22 = img.getPixelXYBByte(i+1, j+1, 2);
					
					int[] pArray = {p00,p01,p02,p10,p11,p12,p20,p21,p22};
					int[] rArray = {r00,r01,r02,r10,r11,r12,r20,r21,r22};
					int[] gArray = {g00,g01,g02,g10,g11,g12,g20,g21,g22};
					int[] bArray = {b00,b01,b02,b10,b11,b12,b20,b21,b22};
					
					Arrays.sort(pArray);
					Arrays.sort(rArray);
					Arrays.sort(gArray);
					Arrays.sort(bArray);
	 
					int medianIndex = pArray.length/2;
					int medianrIndex = rArray.length/2;
					int mediangIndex = gArray.length/2;
					int medianbIndex = bArray.length/2;
	 
					img.setPixelXYBByte(i, j, 0, rArray[medianrIndex]);
					img.setPixelXYBByte(i, j, 1, gArray[mediangIndex]);
					img.setPixelXYBByte(i, j, 2, bArray[medianbIndex]);
					
					//img.setPixelXYByte(i, j, pArray[medianIndex]);
				}
			}
			
			return img;
			
		}
		
		/**
		 * Histogramme d'une image
		 * @param greyImage
		 * @return
		 */
		public static double[][] getHistogram(Image image) {
			ByteImage img = (ByteImage) image;
			double[][] histogram = new double[256][3];
			
			//initialisation de toutes les cases à 0
			for(int i=0; i < histogram.length; i++) {
					histogram[i][0] =0;
					histogram[i][1] =0;
					histogram[i][2] =0;
			}
			
			//parcourir l'ensemble des pixels 
			for(int x=0; x<img.getXDim(); x++) {
				for(int y=0; y<img.getYDim();y++) {
					int r = img.getPixelXYBByte(x, y, 0); 
					int g = img.getPixelXYBByte(x, y, 1); 
					int b = img.getPixelXYBByte(x, y, 2); 
					
					histogram[r][0] +=1; 
					histogram[g][1] +=1; 
					histogram[b][2] +=1; 
					
				}
			}
			
			return histogram;
			
		}
		
		/**
		 * Histogramme d'une image
		 * @param greyImage
		 * @return
		 */
		public static double[][] getHistogramHSV(Image image) {
			ByteImage img = (ByteImage) image;
			double[][] histogram = new double[361][3];
			
			//initialisation de toutes les cases à 0
			for(int i=0; i < histogram.length; i++) {
					histogram[i][0] = 0;
					histogram[i][1] = 0;
					histogram[i][2] = 0;
			}
			
			
			//parcourir l'ensemble des pixels 
			for(int x=0; x<img.getXDim(); x++) {
				for(int y=0; y<img.getYDim();y++) {
					int r = img.getPixelXYBByte(x, y, 0); 
					int g = img.getPixelXYBByte(x, y, 1); 
					int b = img.getPixelXYBByte(x, y, 2); 
					
					List<Double> hsvList = convertToHSV(r,g,b);
					
					int h = (int) Math.round(hsvList.get(0)); // valeur entre 0 et 360
					int s = (int) Math.round(hsvList.get(1)*100); // 95 puis diviser par 100 pour avoir une valeur entre 0 et 1
					int v = (int) Math.round(hsvList.get(2)*100); // 15 puis diviser par 100 pour avoir une valeur entre 0 et 1 
			 
					histogram[h][0] +=1;  
					histogram[s][1] +=1; 
					histogram[v][2] +=1; 
				}
			}
			return histogram;
		}
		
		
		private static List<Double> convertToHSV(int r, int g, int b) {
			List<Double> hsvList = new ArrayList<Double>();
			
			double rHSV = r/255.0; 
			double gHSV = g/255.0; 
			double bHSV = b/255.0;  
			
			double max = Math.max(r, Math.max(g, b));
			double min = Math.min(r, Math.max(g, b));
			double h = 0.0;
			double s = 0.0;
			double v = 0.0;
			
			double sqrt = Math.pow(r, 2) + Math.pow(g, 2) + Math.pow(b, 2) - r*g - r*b - g*b; 
			double gD = g/2.0;
			double bD = b/2.0;  
			
			double acos = Math.toDegrees(Math.acos(((r-gD-bD)/Math.sqrt(sqrt))));
			 
			if(g >= b) {
				h = Math.round(acos);
			}else if(b > g) {
				h = Math.round(360-acos);
			}
			
			if(max > 0) {
				s = 1-(min/max);
			}else if(max == 0) {
				s = 0;
			}
			
			v = max/255;  
			
			hsvList.add(h);
			hsvList.add(s);
			hsvList.add(v);
			
			return hsvList;
		} 
	
		
		public static double[][] discretize(double[][] histogram){
			double[][] newHistogram = new double [SIZE_HISTO][3];
			
			int start = 0;
			int stop = FACTOR;
			for(int i=0; i < newHistogram.length; i++) {
				double r = getRvalues(histogram, start, stop);
				double g = getGvalues(histogram, start, stop);
				double b = getBvalues(histogram, start, stop);
				newHistogram[i][0] = r;
				newHistogram[i][1] = g;
				newHistogram[i][2] = b;
				start+=FACTOR; 
				stop+=FACTOR;
				
			}
			
		
			return newHistogram;
		}
		
		
		/**
		 * Obtenir les valeurs dans un interval donné
		 * @param histogram
		 * @param start
		 * @param stop
		 * @return
		 */
		public static double getRvalues(double[][] histogram,int start,  int stop) {
			double cpt = 0; 
			if(start >=0 ){
				for(int i=start;i<stop;i++) {
					cpt+= histogram[i][0];
				}
				return cpt;
			}
			return 0.0;
		}
		
		/**
		 * Obtenir les valeurs dans un interval donné
		 * @param histogram
		 * @param start
		 * @param stop
		 * @return
		 */
		public static double getGvalues(double[][] histogram,int start,  int stop) {
			double cpt = 0; 
			if(start >=0 ){
				for(int i=start;i<stop;i++) {
					cpt+= histogram[i][1];
				}
				return cpt;
			}
			return 0.0;
		}
		
		/**
		 * Obtenir les valeurs dans un interval donné
		 * @param histogram
		 * @param start
		 * @param stop
		 * @return
		 */
		public static double getBvalues(double[][] histogram,int start,  int stop) {
			double cpt = 0; 
			if(start >=0){
				for(int i=start;i<stop;i++) {
					cpt+= histogram[i][2];
				}
				return cpt;
			}
			return 0.0;
		}
		
		/**
		 * Normalisation d'un histogramme
		 * @param histogram
		 * @param nbPixels
		 */
		public static double[][] normalise(double[][] histogram, int nbPixels) {
			double[][] newHistogram = new double[SIZE_HISTO][3];
			for(int i=0; i< histogram.length; i++) {
				for(int j =0; j <histogram[0].length; j++) {
					newHistogram[i][j]= histogram[i][j]/nbPixels;
					
				}
			}
			
			return newHistogram;
		}
		
		
		public static Map<Double, String> getSimilarImages(Image query, String type) {
			//récupération des images
			List<File> dbFiles = new ArrayList<>();//liste de toutes les images à comparer
			//récupérer toutes les images 
			 File directoryPath = new File(DB_PATH);
			//List of all files and directories
			 File filesList[] = directoryPath.listFiles();
			 for(File file : filesList) {
				 File innerFilesList[]; 
				 if(file.isDirectory()) {
					 dbFiles.addAll(Arrays.asList(file.listFiles()));
				 }else if(file.isFile()) dbFiles.add(file);
		      }
			 Map<Double,String> result = new TreeMap<>();
             Map<Double,String> result2 = new TreeMap<>();
          //Map<Double,String> distances = processImages(dbFiles, query);
          Map<Double,String> distances = processImagesFile(query, type);

          for (Map.Entry<Double, String> entry : distances.entrySet()) {
                  result.put(entry.getKey(), entry.getValue());

             }
          int cpt =0; 
          for (Map.Entry<Double, String> entry : result.entrySet()) {
              if(cpt < 10) {
                  result2.put(entry.getKey(), entry.getValue());
                  cpt++;
              }

             }

         return result2;

		}
		
	
	private static Map processImages(List<File> dbFiles, Image queryImage) {
		double[][] queryHistogram = getHistogram(queryImage);
		queryHistogram = normalise(discretize(getHistogram(queryImage)), queryImage.getNumberOfPresentPixel());
		Map<Double,String> distances = new TreeMap<>();
		//pré-traitement des images à comparer 
			for(File file : dbFiles) {
				Image img = readImage(file.getAbsolutePath());
				if(img.getBDim() == 3) {
					Image filteredImage = filtreMedian(img);
					double[][] histogram = normalise(discretize(getHistogram(filteredImage)), img.getNumberOfPresentPixel());
					double dist = getDistance(queryHistogram, histogram);
					distances.put(dist, file.getName()); 
				} 
			} 
			return distances;
		}

		public static Map processImagesFile(Image queryImage, String type) {
			double[][] queryHistogram = getHistogram(queryImage);
			
			if(type == "rgb") {
				queryHistogram = normalise(discretize(getHistogram(queryImage)), queryImage.getNumberOfPresentPixel());
			}else if(type == "hsv") {
				queryHistogram = normalise(discretizeHSV(getHistogramHSV(queryImage)), queryImage.getNumberOfPresentPixel());
			} 
			
			Map<Double,String> distances = new TreeMap<>();
			
			//lecture du fichier 
			try {
				File file = null;
				if(type == "rgb") {
					file = new File(Indexation.getINDEXES_PATH());
				}else if(type == "hsv") {
					file = new File(Indexation.getINDEXES_PATH_HSV());
				}
			    
				Scanner myReader = new Scanner(file);
			      while (myReader.hasNextLine()) {
			        String line = myReader.nextLine();
			        String[] data = line.split(Indexation.getFILE_SEPARATOR());
			        if(data!=null) {
			        	 String fileName = data[0]; 
			        	 double[][] histogram = new double[SIZE_HISTO][3];
			        	 int cpt=0;
			        	 for(int i=1; i<data.length; i++) {
			        		 histogram[cpt][0] = Double.valueOf(data[i].split(Indexation.getRGB_SEPARATOR())[0]);
			        		 histogram[cpt][1] = Double.valueOf(data[i].split(Indexation.getRGB_SEPARATOR())[1]);
			        		 histogram[cpt][2] = Double.valueOf(data[i].split(Indexation.getRGB_SEPARATOR())[2]);
			        		 
			        		 cpt++;
			        	 }
			        	 double dist = 0.0;
			        	 if(type == "rgb") {
			        		 dist = getDistance(queryHistogram, histogram);
			        	 }else if(type == "hsv") {
			        		 dist = getDistanceHSV(queryHistogram, histogram);
			        	 }
		     			 distances.put(dist, fileName);
			        }
			       
			      }
			      myReader.close();
			    } catch (FileNotFoundException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    } 
			return distances;
		}
		
		public static Map processImagesFileHSV(Image queryImage) {
			double[][] queryHistogram = getHistogramHSV(queryImage);
			queryHistogram = normalise(discretizeHSV(getHistogramHSV(queryImage)), queryImage.getNumberOfPresentPixel());
			Map<Double,String> distances = new TreeMap<>();
			
			//lecture du fichier 
			try {
			      File file = new File(Indexation.getINDEXES_PATH_HSV());
			      Scanner myReader = new Scanner(file);
			      while (myReader.hasNextLine()) {
			        String line = myReader.nextLine();
			        String[] data = line.split(Indexation.getFILE_SEPARATOR());
			        if(data!=null) {
			        	 String fileName = data[0]; 
			        	 double[][] histogram = new double[SIZE_HISTO][3];
			        	 int cpt=0;
			        	 for(int i=1; i<data.length; i++) {
			        		 histogram[cpt][0] = Double.valueOf(data[i].split(Indexation.getRGB_SEPARATOR())[0]);
			        		 histogram[cpt][1] = Double.valueOf(data[i].split(Indexation.getRGB_SEPARATOR())[1]);
			        		 histogram[cpt][2] = Double.valueOf(data[i].split(Indexation.getRGB_SEPARATOR())[2]);
			        		 
			        		 cpt++;
			        	 }
			        	 double dist = getDistanceHSV(queryHistogram, histogram);
		     			 distances.put(dist, fileName);
			        }
			       
			      }
			      myReader.close();
			    } catch (FileNotFoundException e) {
			      System.out.println("An error occurred.");
			      e.printStackTrace();
			    }
			
			
			return distances; 
			
		}
		
		
		/**
		 * Calcul de la distance entre deux histogrammes
		 * @param histogramQuery
		 * @param histogramImage
		 * @return
		 */
		private static double getDistanceHSV(double[][] histogramQuery, double[][] histogramImage) {
			double sumH = 0.0; // H
			double sumS = 0.0; // S
			double sumV = 0.0; // V
			
			// METHODE  1
			
			if(histogramImage.length == histogramQuery.length) {
				for(int i = 0; i <histogramQuery[0].length; i++) {
					sumH += Math.pow(histogramQuery[i][0]-histogramImage[i][0], 2);
				}
				
				for (int j = 0; j < histogramQuery[1].length; j++) {
					sumS += Math.pow(histogramQuery[j][1]-histogramImage[j][1], 2);
					sumV += Math.pow(histogramQuery[j][2]-histogramImage[j][2], 2);
				}
				return Math.sqrt(sumH+sumS+sumV); 
			}
			 
			
			// METHODE 2
			/*
			if(histogramImage.length == histogramQuery.length) {
				for(int i = 0; i <histogramQuery.length; i++) {

					double x1 = histogramQuery[i][1]*Math.cos(histogramQuery[i][0]);
					double x2 = histogramImage[i][1]*Math.cos(histogramImage[i][0]);
					double y1 = histogramQuery[i][1]*Math.sin(histogramQuery[i][0]);
					double y2 = histogramImage[i][1]*Math.sin(histogramImage[i][0]);
					double z1 = histogramQuery[i][2];
					double z2 = histogramImage[i][2];
					
					sumH+=Math.pow(x2-x1, 2);
					sumS+=Math.pow(y2-y1, 2);
					sumV+=Math.pow(z2-z1, 2); 
					
				}
				double distanceH = Math.sqrt(sumH); 
				double distanceS = Math.sqrt(sumS); 
				double distanceV = Math.sqrt(sumV); 
				
				double distance = distanceH+distanceS+distanceV;
				
				return distance; 
			}
			*/
			
			// METHODE 3
			/*
			if(histogramImage.length == histogramQuery.length) {
				for(int i = 0; i <histogramQuery.length; i++) {
					 
					double dh = Math.min(Math.abs(histogramImage[i][0]-histogramQuery[i][0]), 360-Math.abs(histogramImage[i][0]-histogramQuery[i][0]))/180.0;
					double ds = Math.abs(histogramImage[i][1]-histogramQuery[i][1]);
					double dv = Math.abs(histogramImage[i][2]-histogramQuery[i][2])/255.0;
					
					sumH+=dh*dh;
					sumS+=ds*ds;
					sumV+=dv*dv; 
				} 
				double distance = Math.sqrt(sumH+sumS+sumV);
				
				return distance; 
			}
			*/
			
			
			return 0.0;
			
		}	
		
		
		
		public static double[][] discretizeHSV(double[][] histogramHSV){
			double[][] newHistogram = new double [SIZE_HISTO][3];
			
			int start = 0;
			int stop = FACTORHSV;  
			for(int i=0; i < newHistogram.length; i++) { 
				double h = getRvalues(histogramHSV, start, stop);
				double s = getGvalues(histogramHSV, start, stop);
				double v = getBvalues(histogramHSV, start, stop);
				newHistogram[i][0] = h;
				newHistogram[i][1] = s;
				newHistogram[i][2] = v;
				start+=FACTORHSV; 
				stop+=FACTORHSV;
 
			}  
			return newHistogram;
		}

	/**
	 * Calcul de la distance entre deux histogrammes
	 * @param histogramQuery
	 * @param histogramImage
	 * @return
	 */
	private static double getDistance(double[][] histogramQuery, double[][] histogramImage) {
		double sumR = 0; 
		double sumG = 0;
		double sumB = 0;
		if(histogramImage.length == histogramQuery.length) {
			for(int i = 0; i <histogramQuery.length; i++) {
				sumR+=Math.pow((histogramImage[i][0] - histogramQuery[i][0]), 2);
				sumG+=Math.pow((histogramImage[i][1] - histogramQuery[i][1]), 2);
				sumB+=Math.pow((histogramImage[i][2] - histogramQuery[i][2]), 2);
				
			}
			double distanceR = Math.sqrt(sumR); 
			double distanceG = Math.sqrt(sumG); 
			double distanceB = Math.sqrt(sumB); 
			return distanceR+distanceG+distanceB;
		}
		
		return 0.0;
	}

	public static void displayHistogram(double[][] histogram) {
		
		//initialisation de toutes les cases à 0
		StringBuilder s = new StringBuilder();
		for(int i=0; i < histogram.length; i++) {
			for(int j =0;j<histogram[1].length; j++) {
					s.append(histogram[i][j]);
					s.append(" ");
			}
			s.append("\n");
		}
		
	}
	
	
	public static Image getImageQuery() {
		 File directoryPath = new File(QUERY_PATH);
			//List of all files and directories
			 File filesList[] = directoryPath.listFiles();
			 for(File file : filesList) {
				 if(!file.isDirectory()) {
					return readImage(file.getAbsolutePath());
				 }
		      }
			 System.out.println("Problème lors du chargement de l'image requête");
			 return null;
		
	}
	
	
	public static String getImageQueryPath() {
		 File directoryPath = new File(QUERY_PATH);
			//List of all files and directories
			 File filesList[] = directoryPath.listFiles();
			 for(File file : filesList) {
				 if(!file.isDirectory()) {
					return file.getAbsolutePath();
				 }
		      }
			 System.out.println("Problème lors du chargement de l'image requête");
			 return null;
		
	}
	
	
	public static String getDB_PATH() {
		return DB_PATH;
	}




 


	
	
	
}
