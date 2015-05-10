import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
//definit le comportement de coloration de la zone selectionné

public class Coloration extends JPanel implements Comportement{
	private Color couleur;
	private JFrame frame;
	private JFrame colorframe;
	private ImagePix imagePix;
	private JColorChooser colorChooser;
	
	private int[][][] oldImage;
	
	public Coloration(ImagePix im){
		super();
		imagePix =im;
		//stock une ancienne version de l'image
		oldImage = new int[imagePix.width][imagePix.height][3];
		for(int i = 0;i<imagePix.width;i++){
			for(int j=0;j<imagePix.height;j++){
				for(int x = 0; x<3;x++){
					oldImage[i][j][x]=imagePix.image[i][j][x];
				}
			}
		}
		
		colorChooser = new JColorChooser(new Color(0,0,0));
		colorframe = new JFrame("Choix Couleur");
		
		
		frame= new JFrame("Coloration Partielle");
		
		frame.setContentPane(this);
		
		//appele la fonction agir
		JButton valider = new JButton("Valider");
		valider.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				System.out.println(Coloration.this.colorChooser.getColor());
				Coloration.this.couleur = Coloration.this.colorChooser.getColor();
				Coloration.this.agir();
			}
		});
		this.add(valider);
		
		//ouvre une fenetre de selection de couleur
		JButton choisir = new JButton("Choisir couleur");
		choisir.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				colorframe = new JFrame("Choix de la couleur");
				colorframe.setContentPane(Coloration.this.colorChooser);
				colorframe.pack();
				colorframe.setVisible(true);
			}
		});
		this.add(choisir);
				
				
		//retour a la version stocké precedement
		JButton annuler = new JButton("Annuler");
		annuler.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i = 0;i<Coloration.this.imagePix.width;i++){
						for(int j=0;j<Coloration.this.imagePix.height;j++){
							for(int x = 0; x<3;x++){
								Coloration.this.imagePix.image[i][j][x]=Coloration.this.oldImage[i][j][x];
							}
						}
					}
					Coloration.this.imagePix.actualise();
					Coloration.this.colorFrame.setVisible(false);
			}
		});
		this.add(annuler);
		
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void agir(){
			
			//colorie la surface de selection
			for(int i=imagePix.selection[0];i<imagePix.selection[2];i++){
				for(int j = imagePix.selection[1];j<imagePix.selection[3];j++){
					imagePix.image[i][j][0] = couleur.getRed();
					imagePix.image[i][j][1] = couleur.getGreen();
					imagePix.image[i][j][2] = couleur.getBlue();
				}
			}
			Coloration.this.imagePix.actualise();
	}
}
