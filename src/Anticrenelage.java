import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;

public class Anticrenelage extends JPanel implements Comportement{
	
	private ImagePix imagePix;
	public int[][][] oldImage;
	private JButton annuler;
	private JButton appliquer;
	
	public Anticrenelage(ImagePix image){
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
		
		appliquer = new JButton("Appliquer");
		annuler = new JButton("Annuler");
		
		
			
		annuler.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					for(int i = 0;i<Anticrenelage.this.imagePix.width;i++){
						for(int j=0;j<Anticrenelage.this.imagePix.height;j++){
							for(int x = 0; x<3;x++){
								Anticrenelage.this.imagePix.image[i][j][x]=Anticrenelage.this.oldImage[i][j][x];
							}
						}
					}
					
					Anticrenelage.this.imagePix.actualise();
				}
				
			});
			appliquer.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					Anticrenelage.this.agir();
				}
				
			});
		
		this.add(appliquer);
		this.add(annuler);
	}
	public void agir(){
		int moyr;
		int moyg;
		int moyb;
		int[][][] newimage = new int[imagePix.width][imagePix.height][3];
		for(int i=1;i<imagePix.width-1;i++){
			for(int j=1 ; j<imagePix.height-1;j++){
					moyr = moyg = moyb = 0;
					for(int x=-1;x<1;x++){
						for(int y=-1;y<1;y++){
							moyr+=imagePix.image[i+x][j+y][0];
							moyg+=imagePix.image[i+x][j+y][1];
							moyb+=imagePix.image[i+x][j+y][2];
						}
					}
					newimage[i][j][0] = moyr/5;		
					newimage[i][j][1] = moyg/5;	
					newimage[i][j][2] = moyb/5;	
					
			}
		}
		//effet de bord
		
		
		int z;
		int a;
		for(a=0;a<imagePix.width;a++){
				for(z = 0;z<3;z++){
					newimage[a][0][z]=imagePix.image[a][0][z];
					newimage[a][imagePix.height-1][z]=imagePix.image[a][imagePix.height-1][z];
				}
		}
		
		for(a=0;a<imagePix.height;a++){
				for(z = 0;z<3;z++){
					newimage[0][a][z]=imagePix.image[0][a][z];
					newimage[imagePix.width-1][a][z]=imagePix.image[imagePix.width-1][a][z];
				}
		}
			
		
		
		
		
		
		
		
		for(int i=0;i<imagePix.width;i++){
			for(int j=0 ; j<imagePix.height;j++){
				for(int x = 0;x<3;x++){
					imagePix.image[i][j][x]=newimage[i][j][x];
				}
			}
		}
		imagePix.actualise();
	}

}
