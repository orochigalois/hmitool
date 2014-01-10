package edu.usfca.vas.window.fa;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import edu.usfca.vas.graphics.fa.Constant;

public class AlexSimulatorViewFrame extends JFrame {

	public AlexSimulatorViewFramePanel bgPanel;
	private JLabel label;
	private Icon icon;

	/** Constructor to set up the GUI */
	public AlexSimulatorViewFrame() {

		bgPanel = new AlexSimulatorViewFramePanel();

		label = new JLabel();
		icon = new ImageIcon();

		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());

		cp.add(bgPanel, BorderLayout.CENTER);

		bgPanel.setLayout(null);
		bgPanel.add(label);

		label.setIcon(icon);

		setTitle("ViewPanel");

		Dimension d = new Dimension(1024, 768);
		setPreferredSize(d);
		setResizable(false);

		pack();
		setVisible(false);
	}

	public void setV(boolean V) {
		setVisible(V);
	}

	public void updateImage(String imgName) {

		icon = (ImageIcon) Constant.iconTable.get(imgName + ".png");
		
			label.setIcon(icon);
			label.setSize(icon.getIconWidth(), icon.getIconHeight());
		
		
	}

	public void resetBg(String file) {
		
		try {
			BufferedImage img = ImageIO.read(new File(file));
			Dimension d = new Dimension(img.getWidth(), img.getHeight());
			setSize(d);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		};
		bgPanel.resetIMG(file);
	}
	
	
	public void adjustImgPosition(int x,int y)
	{
		label.setLocation(x, y);
	}
	
	public void writeBG(String path)
	{
		bgPanel.writeBG(path);
	}

}
