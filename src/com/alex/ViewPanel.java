package com.alex;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class ViewPanel extends JFrame {

	private JLabel lblImg;
	private Icon icon;

	/** Constructor to set up the GUI */
	public ViewPanel() {
		// Set up a panel for the buttons

		JPanel btnPanel = new JPanel(new FlowLayout());
		
		JButton btnLeft = new JButton("Left");
		btnPanel.add(btnLeft);
		btnLeft.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				icon = new ImageIcon(
						"D://131025HMI_tool//workspace//demo//t1.png");

				lblImg.setIcon(icon);
				requestFocus(); // change the focus to JFrame to receive
								// KeyEvent
			}
		});
		
		JButton btnRight = new JButton("Right");
		btnPanel.add(btnRight);
		btnRight.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				icon = new ImageIcon(
						"D://131025HMI_tool//workspace//demo//t2.png");

				lblImg.setIcon(icon);
				requestFocus(); // change the focus to JFrame to receive
								// KeyEvent
			}
		});

		
		lblImg = new JLabel();

		// Add both panels to this JFrame
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		cp.add(lblImg, BorderLayout.CENTER);
		cp.add(btnPanel, BorderLayout.SOUTH);
		icon = new ImageIcon("D://131025HMI_tool//workspace//demo//t1.png");

		lblImg.setIcon(icon);
		// "this" JFrame fires KeyEvent
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent evt) {
				switch (evt.getKeyCode()) {
				case KeyEvent.VK_LEFT:

					icon = new ImageIcon(
							"D://131025HMI_tool//workspace//demo//t1.png");

					lblImg.setIcon(icon);
					break;
				case KeyEvent.VK_RIGHT:

					icon = new ImageIcon(
							"D://131025HMI_tool//workspace//demo//t2.png");

					lblImg.setIcon(icon);
					break;
				}
			}
		});

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Handle the CLOSE
														// button
		setTitle("ViewPanel");
		pack(); // pack all the components in the JFrame
		setVisible(false); // show it
		requestFocus(); // set the focus to JFrame to receive KeyEvent
	}

	public void setV(boolean V)
	{
		setVisible(V);
	}
	public void updateImage(String imgName)
	{
		icon = new ImageIcon(
				"D://131025HMI_tool//workspace//hmitool//"+imgName+".png");

		lblImg.setIcon(icon);
	}
}