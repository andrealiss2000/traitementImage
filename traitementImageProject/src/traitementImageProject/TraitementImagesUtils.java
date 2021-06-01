package traitementImageProject;

import java.util.Arrays;

import fr.unistra.pelican.ByteImage;
import fr.unistra.pelican.Image;
import fr.unistra.pelican.algorithms.io.ImageLoader;

public class TraitementImagesUtils {
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
		public static double[][][] getHistogram(Image image) {
			ByteImage img = (ByteImage) image;
			double[][][] histogram = new double[256][256][256];
			
			//initialisation de toutes les cases à 0
			for(int i=0; i < histogram[0].length; i++) {
				for(int j =0;j<histogram[1].length; j++) {
					for(int k=0;k< histogram[2].length; k++) {
						histogram[i][j][k] =0;
						//System.out.println(histogram[i][j][k]); 
					}
				}
				
			}
			
			
			//parcourir l'ensemble des pixels 
			for(int x=0; x<img.getXDim(); x++) {
			for(int y=0; y<img.getYDim();y++) {
				int r = img.getPixelXYBByte(x, y, 0); 
				int g = img.getPixelXYBByte(x, y, 1); 
				int b = img.getPixelXYBByte(x, y, 2); 
				histogram[r][g][b] += 1;
				}
			}
			
			
				

			return histogram;
			
		}
	
		
		public static double[][][] discretize(double[][][] histogram){
			double[][][] newHistogram = new double[88][88][88];
			
			
			//rgb(5,5,5) + rgb(85,15,65) + rgb(56,55,53) 
			//rgb(1,1,1) 
			
			//16 777 216 de barres => 16 millions de valeurs rgb
			//671 089 barres 
			//racine cube de 671 089 = 88
			
			//diviser par 10 ce nombre de barres
			int start = 0;
			int stop = 25;
			for(int i=0; i < newHistogram[0].length; i++) {
				for(int j =0;j<newHistogram[1].length; j++) {
					for(int k=0;k< newHistogram[2].length; k++) {
						double r = get25values(histogram, start, stop); 
						newHistogram[i][j][k] = r; 
						System.out.println("START : "+ start + " STOP : "+ stop + " = " + newHistogram[i][j][k]);
						
						//System.out.println(newHistogram[i][j][k]);
						start +=25; 
						stop +=25;
						
						
					}
				}
				
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
		public static double get25values(double[][][] histogram,int start,  int stop) {
			double cpt = 0; 
			for(int i=start;i<stop;i++) {
				for(int j=start;j<stop;j++) {
					for(int k=start; k<stop;k++) {
						cpt+= histogram[i][j][k];
						System.out.println("LES INDICES : " + i+ " "+ j+ " "+ k);
					}
				}
			}
			return cpt;
		}
		
	
	
	public static void displayHistogram(double[][][] histogram) {
		
		//initialisation de toutes les cases à 0
		StringBuilder s = new StringBuilder();
		for(int i=0; i < histogram[0].length; i++) {
			for(int j =0;j<histogram[1].length; j++) {
				for(int k=0;k< histogram[2].length; k++) {
					s.append(histogram[i][j][k]);
					s.append(" ");
				}
				s.append("\n");
			}
			s.append("\n");
		}
		System.out.println(s);
		
	}
	
	
	
	
	
	
}
