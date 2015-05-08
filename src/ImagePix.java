import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.*;

public class ImagePix extends JPanel
{

    public int width;
    public int height;
    private boolean hasAlphaChannel;
    private int pixelLength;
    public byte[] pixels;
    public int max = 0;

    
    public int[][][] image;//x,y puis trois cases int pour rgb
    
    public int[] selection;	//coordonnées selection
    public int [][][] imageSelection; //image copié
    public int tailleSelectX;
    public int tailleSelectY;
    
    
    public boolean anticrenelage;
    public boolean reverse;
    public boolean normalMap;
    public boolean grey;
    public int pixelSize;
    
    public Comportement comportement;
    
    
    public int moreblue;
    public int morered;
    public int moregreen;

    public ImagePix(BufferedImage imagePix)
    {
				reverse = false;
				normalMap = false;
				grey = false;
				pixelSize = 5;

				anticrenelage = false;
				
				selection = new int[4];
				
				
        pixels = ((DataBufferByte) imagePix.getRaster().getDataBuffer()).getData();
        width = imagePix.getWidth();
        height = imagePix.getHeight();
        hasAlphaChannel = imagePix.getAlphaRaster() != null;
        pixelLength = 3;
        if (hasAlphaChannel)
        {
            pixelLength = 4;
        }
        
        image = new int[width][height][3];
						
						//init matrice rgb
						for(int i =0;i<width;i++){
							for(int j =0;j<height;j++){
								getRGB(i,j);
							}
						}
					
					//selection partielle de l'image
				this.addMouseListener(new MouseListener(){
					 public void mousePressed(MouseEvent e) {
					 		System.out.println("X:"+e.getX()+" Y:"+e.getY());
					 		ImagePix.this.selection[0]=e.getX()/pixelSize;
					 		ImagePix.this.selection[1]=e.getY()/pixelSize;
					 }
					 public void mouseReleased(MouseEvent e) {
					 		System.out.println("X:"+e.getX()+" Y:"+e.getY());
					 		ImagePix.this.selection[2]=e.getX()/pixelSize;
					 		ImagePix.this.selection[3]=e.getY()/pixelSize;
					 		ImagePix.this.actualise();
					 }
					 public void mouseEntered(MouseEvent e) {}
					 public void mouseExited(MouseEvent e) {}
					 public void mouseClicked(MouseEvent e) {}
				});
					

    }

   
   //permet de recuperer un pixel dans l'image de depart
   int getRGB(int x, int y)
    {
        int pos = (y * pixelLength * width) + (x * pixelLength);

        int argb = -16777216; // 255 alpha
        if (hasAlphaChannel)
        {
            argb = (((int) pixels[pos++] & 0xff) << 24); // alpha
        }
				
				int blue = ((int) pixels[pos]& 0xff);
        argb += ((int) pixels[pos++] & 0xff); // blue
        
        int green = ((int) pixels[pos] & 0xff);
        argb += (((int) pixels[pos++] & 0xff) << 8); // green
        
        int red = ((int) pixels[pos]& 0xff) ;
        argb += (((int) pixels[pos++] & 0xff) << 16); // red
        
        image[x][y][0] = red;
        image[x][y][1] = green;
        image[x][y][2] = blue;
        
        return argb;
    }
    
    
    public void paint(Graphics g){
					try{
						
						int red;
						int green;
						int blue;
						
						 g.clearRect(0, 0, getWidth(), getHeight());
						
						for(int i =0;i<width;i++){
							for(int j =0;j<height;j++){//itere chaque case de l'image
								//recupere r g b
								red = image[i][j][0];
								green = image[i][j][1];
								blue  = image[i][j][2];
								
								//inverse les couleurs
								 if(reverse){
						  		red = 255-red;
						  		blue = 255-blue;
						  		if(!normalMap){
						  			green = 255-green;
						  		}
						  		else{
						  			green = 0;
						  		}
						  	}
								
								
								//transforme en normal map
								if(normalMap){
						  		int total = red+blue+green;
						  		int moyenne = total/3;
						  		green = 0;
						  		if(moyenne<128){
						  			blue = 128+moyenne;
						  			red= 128-moyenne;
						  		}
						  		else{
						  			red = moyenne-128;
						  			blue= 255-moyenne;
						  		}
						  	}
						  	
						  	//converti en noir et blanc
						  	if(grey){
						  			int total = red+blue+green;
						  			int moyenne = total/3;
						  			red = green = blue = moyenne;
						  	}
								
								//ajoute des composante rgb
								red += morered;
								blue += moreblue;
								green += moregreen;
								
								
								
								//gestion des effets de bords couleurs
								if(green<0){green=0;}
								if(green>255){green=255;}
								if(red<0){red=0;}
								if(red>255){red=255;}
								if(blue<0){blue=0;}
								if(blue>255){blue=255;}
								g.setColor(new Color(red,green,blue));
								
								
								//affiche le pixel
								g.fillRect(i*pixelSize,j*pixelSize,pixelSize,pixelSize);
							}
						}
						
						//trace les ligne de selection
						g.drawLine(selection[0]*pixelSize,selection[1]*pixelSize,selection[0]*pixelSize,selection[3]*pixelSize);
						g.drawLine(selection[2]*pixelSize,selection[1]*pixelSize,selection[2]*pixelSize,selection[3]*pixelSize);
						g.drawLine(selection[0]*pixelSize,selection[1]*pixelSize,selection[2]*pixelSize,selection[1]*pixelSize);
						g.drawLine(selection[0]*pixelSize,selection[3]*pixelSize,selection[2]*pixelSize,selection[3]*pixelSize);
						
						
					}
					catch(Exception e){
						System.out.println(e);
						
					}
				}
				
				
				
				public void actualise(){
					repaint();
				}
				
				
    
    
     
}
