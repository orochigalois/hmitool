package edu.usfca.vas.window.fa;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import edu.usfca.vas.graphics.fa.Constant;

public class AlexFolderChooser extends JPanel implements ActionListener {
	JButton go;
	JFileChooser chooser;


	public AlexFolderChooser() {
		go = new JButton("Load PNGs into ViewList");
		go.addActionListener(this);
		add(go);

	}

	public void actionPerformed(ActionEvent e) {
		
		chooser = new JFileChooser();
		chooser.setCurrentDirectory(new java.io.File("."));
		chooser.setDialogTitle("Select the resource folder");
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		//
		// disable the "All files" option.
		//
		chooser.setAcceptAllFileFilterUsed(false);
		//
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			System.out.println("getCurrentDirectory(): "
					+ chooser.getCurrentDirectory());
			System.out.println("getSelectedFile() : "
					+ chooser.getSelectedFile());
	
			
			File folder = new File(chooser.getSelectedFile().toString());
			File[] listOfFiles = folder.listFiles();

			    for (int i = 0; i < listOfFiles.length; i++) {
			      if (listOfFiles[i].isFile()) {
			    	if(Constant.getExtensionName(listOfFiles[i].getName()).toLowerCase().equals("png"))
			    	{
			    	  Constant.viewModel.add(Constant.viewModel.getSize(), listOfFiles[i].getName());
			    	  Constant.filenameTable.put(listOfFiles[i].getName(), listOfFiles[i].getAbsolutePath());
			    	  System.out.println(listOfFiles[i].getAbsolutePath());
			    	}
			       
			      } 
			    }
		
			
		} else {
			System.out.println("No Selection ");
		}
	}

}