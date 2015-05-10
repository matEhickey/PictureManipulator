import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;

//definit le comportement contraste:
//	si contraste maximum -> chaque pixel devient ou blanc ou noir en fonction de son intensité
//  si contraste minimum -> chaque pixel devient gris

public class Contraste extends JPanel implements Comportement{
	private JFrame frame;
	private ImagePix imagePix;
	private int [][][] oldImage;
	private JSlider slide;
	
	public Contraste(ImagePix im){
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
		frame = new JFrame();
		
		//annuler
		JButton annuler = new JButton("Annuler");
		annuler.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				for(int i = 0;i<imagePix.width;i++){
					for(int j=0;j<imagePix.height;j++){
						for(int x = 0; x<3;x++){
							imagePix.image[i][j][x]=oldImage[i][j][x];
							imagePix.actualise();
						}
					}
				}
			}
		});
		
		//slider de contraste
		slide = new JSlider(-127,127);
		slide.addChangeListener(new ChangeListener(){
        public void stateChanged(ChangeEvent e) {
          		  Contraste.this.agir();
        }
    });
		
		this.add(slide);
		this.add(annuler);
		frame.add(this);
		frame.pack();
		frame.setVisible(true);
	}
	
	
	public void agir(){
		for(int i = 0;i<imagePix.width;i++){
									for(int j=0;j<imagePix.height;j++){//pour chaque couleur de pixel
										for(int x = 0; x<3;x++){
										
												if(imagePix.image[i][j][x] >= 127){	//si >=127 -> augmenter
													imagePix.image[i][j][x] = oldImage[i][j][x]+Contraste.this.slide.getValue();
													if(imagePix.image[i][j][x]>255){
														imagePix.image[i][j][x] = 255;
													}
													else if(imagePix.image[i][j][x]<127){
														imagePix.image[i][j][x] = 127;
													}
												}
												else{	//si <127 -> diminuer
													imagePix.image[i][j][x] = oldImage[i][j][x]-Contraste.this.slide.getValue();
													if(imagePix.image[i][j][x]<0){
														imagePix.image[i][j][x] = 0;
													}
													else if(imagePix.image[i][j][x]>127){
														imagePix.image[i][j][x] = 127;
													}
												}
												imagePix.actualise();
										}
									}
			}
	}

}
