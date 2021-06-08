package ihm;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Menu;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;





import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import traitementImageProject.TraitementImagesUtils;

public class Result  extends JFrame implements ActionListener{


			private static final long serialVersionUID = 1L;
			private Image image; 
			private List<Image> resultImages = new ArrayList<>();
			private static String QUERY_PATH = "";
			
			
			
			
			public Result(String queryPath, List<String> resultPaths) {
				super("Result");
				QUERY_PATH= queryPath;
				try {
					image = ImageIO.read(new File(queryPath));
					for(String path : resultPaths) {
						resultImages.add(ImageIO.read(new File(path)));
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				int w = 800;
				int h = 500;
				defDimensions(w, h);
				init();
				setVisible(true);
			}
			
			
			
			/**************************************************************************
				Méthodes secondaires
			*************************************************************************/
			
			/**
			* Définition des dimensions de la fenêtre
			*/
			public void defDimensions(int w, int h) {
				Toolkit aTK = Toolkit.getDefaultToolkit();
				Dimension dim = aTK.getScreenSize();
				int x = (dim.width - w) / 2;
				int y = (dim.height - h) / 2;
				setBounds(x, y, w, h);
				setSize(w, h);
			}
			
			/**
			* Initialisation de la fenêtre
			*/
			public void init() {
				showUi();
			}
			
			/**
			* Permet de définir tous les éléments à afficher dans la fenêtre
			* @return		: Le contenu de la fenêtre
			*/
			public void showUi() {
				JPanel panel = new JPanel() {
					@Override
		            protected void paintComponent(Graphics g) {
		                super.paintComponent(g); //Never forget this line or you could break the paint chain

		                /* 
		                 * This method belongs to the Graphics class and draws an image, this is what we want the JPanel to draw as our background
		                 * The parameters are: the image to be drawn, the starting position (x, y) coords, the width and height and the observer
		                 */
		                g.drawImage(image, 400,70, 300, 150, this);
		                int nbPhoto=0;
		                int x=0;
		                int y=250;
		                for(int i=0; i< resultImages.size();i++) {
		                	if(nbPhoto ==5) {
		                		nbPhoto=0;
		                		x=0;
		                		y+=200;
		                	}else {
		                		if(i==0) x=0;
		                		else x+=200;
		                		nbPhoto++;
		                	}
		                	g.drawImage(resultImages.get(i), x, y, 150, 120, this);
		                }
		            }

		            /* 
		             * This method is part of the JPanel, we're overriding it's preferred size and return the size we want
		             */
		            @Override
		            public Dimension getPreferredSize() {
		                return new Dimension(1000, 800);
		            }
		        };
		        
		        panel.add(new JLabel("Requête :")); //We add a label to our jpanel
		        add(panel, BorderLayout.WEST); //We add the pane which has a size of 300 x 200 to the left part of our JFrame
		        pack(); //We pack the frame, so it takes its preferred size (and as we only added a single component to it (the JPanel)
		                        //As the panel has a size of 300 x 200, the frame will also have this size
		        setVisible(true); //We set the visibility of the frame
		        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
	
				
				
			}



			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			
}
