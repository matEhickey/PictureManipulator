import java.awt.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.event.*;
import java.io.*;
import javax.imageio.*;
import java.awt.event.*;
import java.util.*;


public class UI extends JPanel{
	private ImagePix imagePix;
	JSlider slide;
	
	
	
	public UI(ImagePix imagePix){
			this.imagePix = imagePix;
			
			this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
			//-----Bouton inverser couleur
			JButton buttonInverser = new JButton("Inverser Couleurs");
			buttonInverser.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
						UI.this.imagePix.reverse = !UI.this.imagePix.reverse;
						UI.this.imagePix.actualise();
				}
			});
			this.add(buttonInverser);
			
			JButton buttonNormal = new JButton("Normal Map");
			buttonNormal.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
						UI.this.imagePix.normalMap = !UI.this.imagePix.normalMap;
						UI.this.imagePix.actualise();
				}
			});
			this.add(buttonNormal);
			
			JButton buttonCrenelage = new JButton("Anti Crenelage");
			buttonCrenelage.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
						//UI.this.imagePix.anticrenelage = !UI.this.imagePix.anticrenelage;
						//UI.this.imagePix.actualise();
						//System.out.println("crenelage");
						JFrame frame = new JFrame("Anti Crenelage");
						frame.setSize(200,100);
						frame.setContentPane((JPanel)new Anticrenelage(UI.this.imagePix));
						frame.setVisible(true);
				}
			});
			this.add(buttonCrenelage);
			
			JButton buttonGrey = new JButton("Nuances de Gris");
			buttonGrey.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					UI.this.imagePix.grey = !UI.this.imagePix.grey;
					UI.this.imagePix.actualise();
				}
			});
			this.add(buttonGrey);
			
			JButton buttonLumin = new JButton("Luminosite");
			buttonLumin.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
						//UI.this.imagePix.anticrenelage = !UI.this.imagePix.anticrenelage;
						//UI.this.imagePix.actualise();
						System.out.println("Luminosite");
						JFrame frame = new JFrame();
						frame.setSize(200,100);
						frame.setContentPane((JPanel)new Luminosite(UI.this.imagePix));
						frame.setVisible(true);
				}
			});
			this.add(buttonLumin);
			
			this.slide = new JSlider(100,900);
        
        slide.addChangeListener(new ChangeListener(){
            public void stateChanged(ChangeEvent e) {
          		           		
                //nouvelle taille et actualise
                UI.this.imagePix.pixelSize = UI.this.slide.getValue()/100;
                UI.this.imagePix.actualise();
            }
        });
        
        this.add(slide);
			
			
			
			//bouttons plus ou moins couleurs
			JPanel redButtons = new JPanel();
			
			redButtons.add(new JLabel("Rouge"));
				JButton moreR = new JButton("+");
							moreR.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){ajouterCouleur(5,'R');} });
				JButton lessR = new JButton("-");
							lessR.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){ajouterCouleur(-5,'R');} });
				redButtons.add(moreR);
				redButtons.add(lessR);
			JPanel greenButtons = new JPanel();
			greenButtons.add(new JLabel("Vert"));
					JButton moreG = new JButton("+");
							moreG.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){ajouterCouleur(5,'G');} });
					JButton lessG = new JButton("-");
							lessG.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){ajouterCouleur(-5,'G');} });
				  greenButtons.add(moreG);
				  greenButtons.add(lessG);
			JPanel blueButtons= new JPanel();
			blueButtons.add(new JLabel("Bleu"));
						JButton moreB = new JButton("+");
							moreB.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){ajouterCouleur(5,'B');} });
						JButton lessB = new JButton("-");
							lessB.addActionListener(new ActionListener(){public void actionPerformed(ActionEvent e){ajouterCouleur(-5,'B');} });
					blueButtons.add(moreB);
					blueButtons.add(lessB);
					
			this.add(redButtons);
			this.add(greenButtons);
			this.add(blueButtons);
			
			
			
			
			//boutton copier/coller -> WIP
			JPanel copyPaste = new JPanel();
			JButton copier = new JButton("Copier");
			JButton coller = new JButton("Coller");
			
			copier.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						copier();
					}
				});
			
			coller.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						coller();
					}
				});
			copyPaste.add(new JLabel("WIP"));
			copyPaste.add(copier);
			copyPaste.add(coller);
			coller.setEnabled(false);//<----set clickable = setEnabled
			this.add(copyPaste);
			
			
			JButton coloration = new JButton("Coloration Partielle");
			coloration.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e){
						new Coloration(UI.this.imagePix);
					}
				}
			);
			this.add(coloration);
			
			this.add(new Rogner(imagePix));
			
	}
	
	
	public void copier(){
		
		
		java.util.List liste = new java.util.LinkedList();
		imagePix.tailleSelectX=Math.abs(imagePix.selection[0]-imagePix.selection[2]);
		imagePix.tailleSelectY=Math.abs(imagePix.selection[1]-imagePix.selection[3]);
		for(int i=0;i<imagePix.width;i++){
			for(int j=0;j<imagePix.height;j++){
				if(	//si le pixel est dans la zone de selection ajout dans une list
						(i*imagePix.pixelSize>imagePix.selection[0])&&
						(i*imagePix.pixelSize<imagePix.selection[2])&&
						(j*imagePix.pixelSize>imagePix.selection[1])&&
						(j*imagePix.pixelSize<imagePix.selection[3]))
				{
					for(int z=0;z<3;z++){
						liste.add(imagePix.image[i][j][z]);
					}
				}
			}
		}
		
		//imagePix.imageSelection[][][]
		
		imagePix.imageSelection = new int[imagePix.tailleSelectX][imagePix.tailleSelectY][3];
		
		Iterator ite_color = liste.iterator();
		int tamp;
		int i=0;
		int j=0;
		int rgb=0;//itere jusqua 2, puis reviens a 0, pour depiler la liste
		while(ite_color.hasNext()){
			
			tamp = (Integer)(ite_color.next());
			
			imagePix.imageSelection[j][i][rgb] = tamp;
			
			rgb++;
			if(rgb == 3){
				rgb = 0;
				j++;
				if(j==imagePix.tailleSelectY){
					i++;
					j=0;
					if(i==imagePix.tailleSelectX){
						//si here -> bug
					}
				}
			}			
		}

	}
	
	
	
	public void coller(){
		System.out.println("coller");
		for(int i=0;i<imagePix.tailleSelectX;i++){
			for(int j=0;j<imagePix.tailleSelectY;j++){
				for(int k=0;k<3;k++){
				//System.out.println(imagePix.imageSelection[i][j][k]);
				imagePix.image[i][j][k]=imagePix.imageSelection[i][j][k];
				
				}
			}
		}
		imagePix.actualise();
	}
	
	
	
	/**
	*@param color R,G,ou B
	*
	*/
	
	public void ajouterCouleur(int value,char color){	
		if(color == 'R'){
				//System.out.println("Rouge :"+value);
				imagePix.morered += value;
				
		}
		else if(color == 'G'){
				//System.out.println("Vert :"+value);
				imagePix.moregreen += value;
		}
		else{//color == B
				//System.out.println("Bleu :"+value);			
				imagePix.moreblue += value;
		}
		imagePix.actualise();
		
	}
	
	public void setFrame(JFrame frame){
		frame.setSize(imagePix.width*imagePix.pixelSize,imagePix.height*imagePix.pixelSize);
	}
	
	public static void main(String[] args){
		JFrame f = new JFrame("Commandes");
		JFrame fImage = new JFrame("Image");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fImage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//fImage.setSize(800,1000);
		try{
			ImagePix im;
			if(args.length == 0 ){
					im = new ImagePix(ImageIO.read(new File("../Images/yoo.jpg")));
			}
			else{
					im = new ImagePix(ImageIO.read(new File(args[0])));
			}
			//JScrollPane myJScrollPane = new JScrollPane();
         //myJScrollPane.add(im);
			fImage.setContentPane(im);
			UI ui = new UI(im);
			ui.setFrame(fImage);
			f.add(ui);
			f.pack();
		}
		catch(Exception e){
			System.out.println(e);
		}
		fImage.setVisible(true);
		f.setVisible(true);
		
	}

}
