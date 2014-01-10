package edu.usfca.vas.window.fa;

import edu.usfca.vas.app.Localized;
import edu.usfca.vas.data.DataWrapperFA;
import edu.usfca.vas.graphics.fa.*;
import edu.usfca.vas.machine.fa.FAMachine;
import edu.usfca.vas.window.WindowMachineAbstract;
import edu.usfca.vas.window.tools.DesignToolsFA;
import edu.usfca.xj.appkit.frame.XJFrame;

import javax.swing.*;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragGestureRecognizer;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WindowMachineFA extends WindowMachineAbstract {

	protected WindowMachineFASettings settings = null;

	protected JTextField alphabetTextField;
	protected JTextField stringTextField;
	// protected JComboBox typeComboBox;
	protected JPanel mainPanel;
	protected JSplitPane mainPanelSplit;
	protected JScrollPane mainPanelScrollPane;

	protected DesignToolsFA designToolFA;

	protected WindowMachineFAOverlay overlay;
	protected boolean overlayVisible;

	protected JComboBox eventComboBox;

	AlexViewList viewList;
	AlexEventList eventList;

	public WindowMachineFA(XJFrame parent) {
		super(parent);
	}

	public void init() {
		setGraphicPanel(new GViewFAMachine(parent));
		getFAGraphicPanel().setDelegate(this);
		getFAGraphicPanel().setMachine(getDataWrapperFA().getGraphicMachine());
		getFAGraphicPanel().setRealSize(getDataWrapperFA().getSize());

		setLayout(new BorderLayout());
		add(createUpperPanel(), BorderLayout.NORTH);
		add(createAutomataPanel(), BorderLayout.CENTER);
		add(createLeftPanel(), BorderLayout.WEST);
		add(createRightPanel(), BorderLayout.EAST);
		overlay = new WindowMachineFAOverlay(parent.getJFrame(), mainPanel);
		overlay.setStringField(stringTextField);
	}

	public JPanel createLeftPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// To keep this panel's size from changing, I would set all three
		// available size constraints
		panel.setPreferredSize(new Dimension(200, 900));
		panel.setMinimumSize(new Dimension(200, 900));
		panel.setMaximumSize(new Dimension(200, 900));

		// Create a list that allows adds and removes

		viewList = new AlexViewList(Constant.viewModel);

		// Initialize the list with items
		String[] items = {};
		for (int i = 0; i < items.length; i++) {
			Constant.viewModel.add(i, items[i]);
		}

		// // Append an item
		// int pos = list.getModel().getSize();
		// model.add(pos, "E");
		//
		// // Insert an item at the beginning
		// pos = 0;
		// model.add(pos, "a");

		JScrollPane scrollPane = new JScrollPane(viewList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel.add(scrollPane);

		return panel;

	}

	public JPanel createRightPanel() {
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		// To keep this panel's size from changing, I would set all three
		// available size constraints
		panel.setPreferredSize(new Dimension(160, 900));
		panel.setMinimumSize(new Dimension(160, 900));
		panel.setMaximumSize(new Dimension(160, 900));

		// Create a list that allows adds and removes

		eventList = new AlexEventList(Constant.eventModel);

		eventList.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {

					if (eventList.getSelectedValue() != null) {
						Constant.currentEvent = eventList.getSelectedValue()
								.toString();
						Constant.currentEventIndex = eventList
								.getSelectedIndex();
					} else {
						Constant.currentEventIndex = -1;
					}
				}
			}
		});

		// Initialize the list with items
		// String[] items = {};
		// for (int i=0; i<items.length; i++) {
		// Constant.eventModel.add(i, items[i]);
		// }
		//
		// // // Append an item
		// int pos = Constant.eventModel.getSize();
		// Constant.eventModel.add(pos, "1");
		// pos = Constant.eventModel.getSize();
		// Constant.eventModel.add(pos, "2");
		// pos = Constant.eventModel.getSize();
		// Constant.eventModel.add(pos, "3");
		// pos = Constant.eventModel.getSize();
		// Constant.eventModel.add(pos, "4");

		JScrollPane scrollPane = new JScrollPane(eventList,
				JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		panel.add(scrollPane);

		return panel;

	}

	public WindowFA getWindowFA() {
		return (WindowFA) getWindow();
	}

	public DataWrapperFA getDataWrapperFA() {
		return (DataWrapperFA) getDataWrapper();
	}

	public GViewFAMachine getFAGraphicPanel() {
		return (GViewFAMachine) getGraphicPanel();
	}

	public JPanel createUpperPanel() {
		JPanel panel = new JPanel(new BorderLayout());

		panel.setMaximumSize(new Dimension(99999, 30));
		panel.add(new AlexFolderChooser(), BorderLayout.WEST);
		panel.add(designToolFA = new DesignToolsFA(), BorderLayout.CENTER);
		panel.add(createControlPanel(), BorderLayout.EAST);

		getFAGraphicPanel().setDesignToolsPanel(designToolFA);

		return panel;
	}

	public JPanel createControlPanel() {
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(99999, 30));

		// panel.add(new JLabel(Localized.getString("faWMAutomaton")));
		// typeComboBox = new JComboBox(new String[] {
		// Localized.getString("DFA"),
		// Localized.getString("NFA") });
		// typeComboBox.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// int type = typeComboBox.getSelectedIndex();
		// if (type != getDataWrapperFA().getMachineType()) {
		// getDataWrapperFA().setMachineType(type);
		// changeOccured();
		// }
		// }
		// });
		// panel.add(typeComboBox);

		//panel.add(new JLabel("event"));
		eventComboBox = new JComboBox(new String[] { "e1", "e2" });
		eventComboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int type = eventComboBox.getSelectedIndex();
				if (type != getDataWrapperFA().getMachineType()) {
					getDataWrapperFA().setMachineType(type);
					changeOccured();
				}
			}
		});

		//panel.add(eventComboBox);

		//panel.add(new JLabel(Localized.getString("faWMAlphabet")));

		alphabetTextField = new JTextField(getDataWrapperFA()
				.getSymbolsString());
		alphabetTextField.setPreferredSize(new Dimension(100, 20));
		alphabetTextField.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				handleAlphabetTextFieldEvent();
			}

		});

		alphabetTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleAlphabetTextFieldEvent();
			}
		});

		//panel.add(alphabetTextField);

		//panel.add(new JLabel(Localized.getString("faWMString")));

		stringTextField = new JTextField("");
		stringTextField.setPreferredSize(new Dimension(100, 20));
		stringTextField.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				handleStringTextFieldEvent();
			}

		});

		stringTextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				handleStringTextFieldEvent();
			}
		});

		//panel.add(stringTextField);
		
		
		
		panel.add(Constant.labelScript);
		
		JButton script = new JButton("script");
		script.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				JFileChooser chooser;
        		chooser = new JFileChooser();
        		chooser.setCurrentDirectory(new java.io.File("."));
        		chooser.setDialogTitle("Select the test script file");
        		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        		//
        		// disable the "All files" option.
        		//
        		chooser.setAcceptAllFileFilterUsed(true);
        		
        		chooser.addChoosableFileFilter(new FileNameExtensionFilter("TXT", "txt"));
 
        
        		if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
        			Constant.labelScript.setText(chooser.getSelectedFile().toString());
        			try {
						BufferedReader br;
						br = new BufferedReader(new FileReader(chooser.getSelectedFile().toString()));
						StringBuilder sb = new StringBuilder();
        		        String line = br.readLine();

        		        while (line != null) {
        		            sb.append(line);
        		            sb.append("\n");
        		            line = br.readLine();
        		        }
						Constant.wholeScript=sb.toString();
						br.close();
				
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					

        		 
        		    
        		} else {
        			//System.out.println("No Selection ");
        		}
        		
			
			}
		});
		panel.add(script);
		
		JButton run = new JButton("auto");
		run.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getWindowFA().debug();
			
				getWindowFA().debugProceed();
			}
		});
		panel.add(run);

		JButton start = new JButton("manu");
		start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getWindowFA().debug();
			}
		});

		panel.add(start);

		JButton addEvent = new JButton("add");
		addEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String event = (String) JOptionPane.showInputDialog(null,
						"Enter a name for this new event", "New Event",
						JOptionPane.QUESTION_MESSAGE, null, null, null);
				if(event!=null)
					Constant.eventModel.add(Constant.eventModel.getSize(), event);
			}
		});

		panel.add(addEvent);

		JButton removeEvent = new JButton("del");
		removeEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Constant.currentEventIndex != -1)
					Constant.eventModel.remove(Constant.currentEventIndex);

			}
		});

		panel.add(removeEvent);

		JButton editEvent = new JButton("edit");
		editEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (Constant.currentEventIndex != -1) {
					String event = (String) JOptionPane.showInputDialog(null,
							"Enter a name for this new event", "New Event",
							JOptionPane.QUESTION_MESSAGE, null, null,
							Constant.currentEvent);
					if (event != null) {
						Constant.eventModel.remove(Constant.currentEventIndex);
						Constant.eventModel.add(Constant.eventModel.getSize(),
								event);
					}
				}
			}
		});

		panel.add(editEvent);

		return panel;
	}

	public JComponent createAutomataPanel() {
		mainPanelScrollPane = new JScrollPane(getGraphicPanel());
		mainPanelScrollPane.setPreferredSize(new Dimension(640, 480));
		mainPanelScrollPane.setWheelScrollingEnabled(true);

		mainPanelSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
		mainPanelSplit.setContinuousLayout(true);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(mainPanelScrollPane, BorderLayout.CENTER);

		return mainPanel;
	}

	public boolean supportsOverlay() {
		return true;
	}

	public void setVisible(boolean visible) {
		super.setVisible(visible);
		if (visible && overlayVisible)
			overlay.setVisible(true);
		else if (!visible && overlayVisible)
			overlay.setVisible(false);
	}

	public boolean isOverlayVisible() {
		return overlay.isVisible();
	}

	public void toggleOverlayVisibility() {
		overlay.setVisible(!overlay.isVisible());
		overlayVisible = overlay.isVisible();
	}

	// *** Event methods

	public void handleAlphabetTextFieldEvent() {
		String s = alphabetTextField.getText();
		if (!s.equals(getDataWrapperFA().getSymbolsString())) {
			getDataWrapperFA().setSymbolsString(s);
			changeOccured();
		}
	}

	public void handleStringTextFieldEvent() {
		String s = stringTextField.getText();
		if (!s.equals(getDataWrapperFA().getString())) {
			getDataWrapperFA().setString(s);
			overlay.textChanged();
			changeOccured();
		}
	}

	public String getString() {
		//return stringTextField.getText();
		return Constant.wholeScript;
	}

	// *** Public methods

	public FAMachine convertNFA2DFA() {
		return getDataWrapperFA().getMachine().convertNFA2DFA();
	}

	public void setFAMachine(FAMachine machine) {
		getDataWrapperFA().setMachine(machine);
		getDataWrapperFA().getGraphicMachine().setMachine(machine);
		getDataWrapperFA().getGraphicMachine().reconstruct();

		getFAGraphicPanel().setMachine(getDataWrapperFA().getGraphicMachine());
		getFAGraphicPanel().centerAll();
		getFAGraphicPanel().repaint();
	}

	public void rebuild() {
		super.rebuild();
		getFAGraphicPanel().setMachine(getDataWrapperFA().getGraphicMachine());
		// typeComboBox.setSelectedIndex(getDataWrapperFA().getMachineType());
		alphabetTextField.setText(getDataWrapperFA().getSymbolsString());
		stringTextField.setText(getDataWrapperFA().getString());
	}

	public void setTitle(String title) {
		getWindowFA().setWindowMachineTitle(this, title);
		getDataWrapperFA().setName(title);
		changeOccured();
	}

	public String getTitle() {
		return getWindowFA().getWindowMachineTitle(this);
	}

	public void displaySettings() {
		if (settings == null)
			settings = new WindowMachineFASettings(this);

		settings.display();
	}

	public void setGraphicsSize(int dx, int dy) {
		getGraphicPanel().setRealSize(dx, dy);
		getDataWrapperFA().setSize(new Dimension(dx, dy));
		changeOccured();
	}

	public Dimension getGraphicSize() {
		return getGraphicPanel().getRealSize();
	}

	public void setDebugInfo(String remaining) {
		String original = stringTextField.getText();
		overlay.setString(original, original.length() - remaining.length());
	}

	public void viewSizeDidChange() {
		// do nothing
	}

}
