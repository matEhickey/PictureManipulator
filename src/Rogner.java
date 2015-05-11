import java.awt.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.util.*;
import java.lang.*;
//definit le comportement de coloration de la zone selectionné

public class Rogner extends JButton implements Comportement{
	
	private ImagePix imagePix;
	
	public Rogner(ImagePix im){
		super("Rogner");
		imagePix = im;
		this.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				Rogner.this.agir();
			}
		});
	}
	
	public void agir(){
		
		
		//recuperation matrice selectionné
		int imax = Math.max(imagePix.selection[0],imagePix.selection[2]);
		int imin = Math.min(imagePix.selection[0],imagePix.selection[2]);
		int ymax = Math.max(imagePix.selection[1],imagePix.selection[3]);
		int ymin = Math.min(imagePix.selection[1],imagePix.selection[3]);
		int [][][] tamp = new int[imax-imin][ymax-ymin][3];
		
		System.out.println("imax "+imax + " imin "+imin+" ymax "+ymax + " ymin "+ymin);
		int i2=0;
		int j2=0;
		for(int i=imin;i<imax;i++){
				for(int j = ymin;j<ymax;j++){
					for(int z = 0;z<3;z++){
						tamp[i2][j2][z] = imagePix.image[i][j][z];
						//System.out.println(imagePix.image[i][j][z]);
						
					}
					j2++;
				}
					j2 = 0;
					i2++;
		}
		
		for(int i=0;i<Math.abs(imagePix.selection[2]-imagePix.selection[0]);i++){
			for(int j = 0;j<Math.abs(imagePix.selection[3]-imagePix.selection[1]);j++){
				for(int z = 0;z<3;z++){
					imagePix.image[i][j][z] = tamp[i][j][z];
				}
			}
		}
		imagePix.width = Math.abs(imagePix.selection[2]-imagePix.selection[0]);
		imagePix.height = Math.abs(imagePix.selection[1]-imagePix.selection[3]);
		imagePix.actualise();
		imagePix.frame.setSize(imagePix.width*imagePix.pixelSize, imagePix.height*imagePix.pixelSize);
	}

}
