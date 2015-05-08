import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
/**
Classe definnissant un panel, permettant de modifier la luminosite de l'image
*/
public class Luminosite extends JPanel implements Comportement{
	
	private ImagePix imagePix;
	public int[][][] oldImage;
	private JButton annuler;
	private JButton appliquer;
	private JButton enlever;
	
	/**
	constructeur
	besoin d'un imagePix a manipuler
	*/
	public Luminosite(ImagePix image){
		super();
		this.imagePix=image;
		oldImage = new int[image.width][image.height][3];
		
		for(int i = 0;i<image.width;i++){
			for(int j=0;j<image.height;j++){
				for(int x = 0; x<3;x++){
					oldImage[i][j][x]=imagePix.image[i][j][x];
				}
			}
		}
		
		//Bouttons
		appliquer = new JButton("Plus");
		enlever = new JButton("Moins");
		annuler = new JButton("Annuler");
		
		
		//evenement clik des boutons	
		annuler.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					for(int i = 0;i<Luminosite.this.imagePix.width;i++){
						for(int j=0;j<Luminosite.this.imagePix.height;j++){
							for(int x = 0; x<3;x++){
								Luminosite.this.imagePix.image[i][j][x]=Luminosite.this.oldImage[i][j][x];
							}
						}
					}
					
					Luminosite.this.imagePix.actualise();
				}
				
			});
			
			
			appliquer.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Luminosite.this.agir();//augmente la luminosité
				}
				
			});
			
			enlever.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Luminosite.this.agir2();//diminue la luminosité
				}
				
			});
		
		this.add(appliquer);
		this.add(enlever);
		this.add(annuler);
	}
	
	
	
	
	//augmente la liminosité
	public void agir(){
			for(int i=0;i<imagePix.width;i++){
				for(int j=0 ; j<imagePix.height;j++){
					for(int x=0;x<3;x++){
						if(!imagePix.reverse){
						imagePix.image[i][j][x] += 3;
						}
						else{imagePix.image[i][j][x] -= 3;}
					}
				}
			}
			imagePix.actualise();
	}
	//diminue la liminosité
	public void agir2(){
			for(int i=0;i<imagePix.width;i++){
				for(int j=0 ; j<imagePix.height;j++){
					for(int x=0;x<3;x++){
						if(!imagePix.reverse){
						imagePix.image[i][j][x] -= 3;
						}
						else{imagePix.image[i][j][x] += 3;}
					}
				}
			}
			imagePix.actualise();
	}
	
	
}
