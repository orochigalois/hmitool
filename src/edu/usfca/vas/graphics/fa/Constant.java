package edu.usfca.vas.graphics.fa;

import java.io.File;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.JLabel;

import edu.usfca.vas.window.fa.AlexSimulatorViewFrame;


public class Constant {
	
	/////View list DnD
	public static String s;
	
	/////View list
	public static DefaultListModel viewModel = new DefaultListModel();
	public static Hashtable filenameTable = new Hashtable();
	public static Hashtable iconTable = new Hashtable();
	
	/////Event list
	public static DefaultListModel eventModel = new DefaultListModel();
	public static int currentEventIndex=-1;
	public static String currentEvent;
	
	/////Simulator BG
	public static AlexSimulatorViewFrame simulatorViewFrame=new AlexSimulatorViewFrame();
	public static int simulator_x;
	public static int simulator_y;
	public static String simulator_bg;
	
	
	/////script
	public static JLabel labelScript = new JLabel();
	public static String wholeScript="";
	
	
	/////Control Panel
	public static String removeExtention(String filePath) {
	    // These first few lines the same as Justin's
	    File f = new File(filePath);

	    // if it's a directory, don't remove the extention
	    if (f.isDirectory()) return filePath;

	    String name = f.getName();

	    // Now we know it's a file - don't need to do any special hidden
	    // checking or contains() checking because of:
	    final int lastPeriodPos = name.lastIndexOf('.');
	    if (lastPeriodPos <= 0)
	    {
	        // No period after first character - return name as it was passed in
	        return filePath;
	    }
	    else
	    {
	        // Remove the last period and everything after it
	        File renamed = new File(f.getParent(), name.substring(0, lastPeriodPos));
	        return renamed.getPath();
	    }
	}
	public static String getExtensionName(String fileName)
	{
		String extension = "";

		int i = fileName.lastIndexOf('.');
		if (i > 0) {
		    extension = fileName.substring(i+1);
		    return extension;
		}
		else{
			return "";
		}
	}
	
	

}