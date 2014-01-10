package edu.usfca.vas.window.fa;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;


public class AlexSimulatorViewFramePanel extends JPanel {
	  private BufferedImage img;
	 
	  public AlexSimulatorViewFramePanel() {
	  }
	  public void resetIMG(String file)
	  {
		  try {
		      img = ImageIO.read(new File(file));
		      
		      repaint();
		    } catch(IOException e) {
		      e.printStackTrace();
		    }
	  
	  }
	  public void writeBG(String path)
	  {
		  try {
			  if(img!=null)
			ImageIO.write(img, "png", new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  }
	  
	 
	  @Override
	  protected void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    // paint the background image and scale it to fill the entire space
	    if(img!=null)
	    {
	    	 g.drawImage(img, 0, 0, img.getWidth(), img.getHeight(), this);
	    }
	   
	  }
	}