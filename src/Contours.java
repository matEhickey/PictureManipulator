import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;

//definit le comportement contraste:
//	si contraste maximum -> chaque pixel devient ou blanc ou noir en fonction de son intensité
//  si contraste minimum -> chaque pixel devient gris

public class Contours extends JPanel implements Comportement{
	private JFrame frame;
	private ImagePix imagePix;
	private JSlider slide;
	private int [][][] oldImage;
	
	public Contours(ImagePix im){
		super();
		
		imagePix = im;
		
		oldImage = new int[imagePix.width][imagePix.height][3];
		
		//stockage image debut
		for(int i = 0;i<imagePix.width;i++){
			for(int j=0;j<imagePix.height;j++){
				for(int x = 0; x<3;x++){
					oldImage[i][j][x]=imagePix.image[i][j][x];
				}
			}
		}
		
		imagePix = im;
		frame = new JFrame("Contours");
		
		slide = new JSlider(0,255);
		slide.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
          		  Contours.this.agir();
        }
		});
		
		JButton annuler = new JButton("annuler");
		annuler.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i = 0;i<imagePix.width;i++){
					for(int j=0;j<imagePix.height;j++){
						for(int x = 0; x<3;x++){
							imagePix.image[i][j][x]=oldImage[i][j][x];
						}
					}
				}
				imagePix.actualise();
			}
		});
		agir();
		this.add(slide);
		this.add(annuler);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	public void agir(){
		int [][] tab_moy = new int[imagePix.width][imagePix.height];
		int moyenne;
		int [][] tamp = new int[imagePix.width][imagePix.height];
		for(int i =1;i<imagePix.width-1;i++){
				for(int j=1;j<imagePix.height-1;j++){//recuperer la moyenne de chaque pixel
					moyenne = 0;
						for(int x = 0; x<3;x++){
							moyenne += oldImage[i][j][x];
						}
						tab_moy[i][j] = moyenne/3;
				}
		}
		
		for(int i =1;i<imagePix.width-1;i++){
				for(int j=1;j<imagePix.height-1;j++){
							
							if(Math.abs(tab_moy[i-1][j]-tab_moy[i+1][j])>slide.getValue()){
									for(int x = 0; x<3;x++){
										tamp[i][j]=255;
									} 
							}
							else if(Math.abs(tab_moy[i][j-1]-tab_moy[i][j+1])>slide.getValue()){
								 	tamp[i][j]=255;
							}
							else {
									for(int x = 0; x<3;x++){
										tamp[i][j]=0;
									}
							}
						
				}
		}
		
		for(int i =0;i<imagePix.width;i++){
				for(int j=0;j<imagePix.height;j++){
					for(int x = 0; x<3;x++){
						imagePix.image[i][j][x] = tamp[i][j];
					}
				}
		}
		imagePix.actualise();
		
	}
	
	
}
