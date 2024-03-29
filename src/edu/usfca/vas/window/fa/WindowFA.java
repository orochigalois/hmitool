/*

[The "BSD licence"]
Copyright (c) 2004 Jean Bovet
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:

1. Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer in the
documentation and/or other materials provided with the distribution.
3. The name of the author may not be used to endorse or promote products
derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

*/

package edu.usfca.vas.window.fa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
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
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;




















import edu.usfca.vas.app.Localized;
import edu.usfca.vas.data.DataWrapperAbstract;
import edu.usfca.vas.data.DataWrapperFA;
import edu.usfca.vas.debug.DebugWrapper;
import edu.usfca.vas.graphics.fa.Constant;
import edu.usfca.vas.graphics.fa.GElementFAMachine;
import edu.usfca.vas.graphics.fa.GElementFAState;
import edu.usfca.vas.machine.fa.FAMachine;
import edu.usfca.vas.window.WindowAbstract;
import edu.usfca.vas.window.WindowMachineAbstract;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GLink;
import edu.usfca.xj.appkit.menu.XJMainMenuBar;
import edu.usfca.xj.appkit.menu.XJMenu;
import edu.usfca.xj.appkit.menu.XJMenuItem;
import edu.usfca.xj.appkit.utils.XJAlert;
import edu.usfca.xj.foundation.XJUtils;

public class WindowFA extends WindowAbstract {
                 
    private static final int MI_DISPLAYTRANSITIONS = 100;
    private static final int MI_NFA_TO_DFA = 101;

    private JTabbedPane tabbedComponent;
    private XJMenu menuTools;
    
  

    public AlexSimulatorController simulatorController=new AlexSimulatorController();

    
    public class AlexSimulatorController extends JFrame {

    	private JLabel lblImg;
    	private Icon icon;
    	public JPanel btnPanel = new JPanel();
    	/** Constructor to set up the GUI */
    	public void createButtons(int i)
    	{
    		
    		//remove all components in panel.
    		btnPanel.removeAll(); 
            // refresh the panel.
 
    
    		btnPanel.setLayout(new java.awt.GridLayout(i, i));
            for (int j = 0; j < i; j++) {
                JButton b = new JButton((String) Constant.eventModel.get(j));
                b.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                    	
                    	getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().debugStepForwardByClick(e.getActionCommand());
        				String current=getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().getCurrentState();
        		        if(current!=null)
        		        {
	        				Constant.simulatorViewFrame.updateImage(current);
	        		        displayDebuggerInfo();
	        		        updateExecutionComponents();
        		        }
        		        else
        		        {
        		        	XJAlert.display(getJavaContainer(),"Warning","This transition is not correct,SM will reset" );
    
        		        }
        			//	requestFocus(); // change the focus to JFrame to receive
        								// KeyEvent
                    }
                });
                btnPanel.add(b);
            }
            
            btnPanel.updateUI();
    	
    	}
    	public AlexSimulatorController() {
    		// Set up a panel for the buttons
    
    		
    		
    	
    		
    		
    		Container cp = getContentPane();
    		cp.setLayout(new BorderLayout());
 
    		cp.add(btnPanel, BorderLayout.CENTER);

    	
    		setTitle("KeyPanel");
    		
    		Dimension d = new Dimension(300,300);
            setPreferredSize(d);
            setResizable(false);
        
            
            
    		pack(); // pack all the components in the JFrame
    		setVisible(false); // show it
    		requestFocus(); // set the focus to JFrame to receive KeyEvent
    		
    		addKeyListener(new KeyAdapter() {
    			@Override
    			public void keyPressed(KeyEvent evt) {
    				switch (evt.getKeyCode()) {
    				case KeyEvent.VK_LEFT:

    					
    					break;
    				case KeyEvent.VK_RIGHT:

//    					icon = new ImageIcon(
//    							"D://131025HMI_tool//workspace//demo//t2.png");
//
//    					lblImg.setIcon(icon);
    					break;
    				}
    			}
    		});
    	}

    	public void setV(boolean V)
    	{
    		setVisible(V);
    	}

    }

    public void awakeConcrete() {
        tabbedComponent = new JTabbedPane();
        tabbedComponent.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                WindowMachineFA visible = (WindowMachineFA) tabbedComponent.getSelectedComponent();
                for(int index = 0; index < tabbedComponent.getTabCount(); index++) {
                    WindowMachineFA wm = (WindowMachineFA) tabbedComponent.getComponentAt(index);
                    wm.setVisible(wm == visible);
                }

                currentWindowMachineIndex = tabbedComponent.getSelectedIndex();
                getAutomataDocument().getAutomataData().setCurrentWrapperIndex(currentWindowMachineIndex);
                getMainMenuBar().refresh();
                changeOccured();
            }
        });

        if(windowMachines.size() == 0)
            createNewMachine(null);
    }

    public JComponent getMachineComponent() {
        return tabbedComponent;
    }

    public void customizeMenuBarForMachine(XJMainMenuBar menubar) {
        menuTools = new XJMenu();
        menuTools.setTitle(Localized.getString("faMenuTools"));
        menuTools.addItem(new XJMenuItem(Localized.getString("faMIConvertNFADFA"), KeyEvent.VK_PERIOD, MI_NFA_TO_DFA, this));
        menuTools.addSeparator();
        menuTools.addItem(new XJMenuItem(Localized.getString("faMIDisplayTransitions"), KeyEvent.VK_T, MI_DISPLAYTRANSITIONS, this));

        menubar.addCustomMenu(menuTools);
    }

    public void refreshMenuMachine() {

    }

    public void rebuildMenuMachine() {

    }

    public void setWindowMachineTitle(WindowMachineAbstract wm, String title) {
        tabbedComponent.setTitleAt(windowMachines.indexOf(wm), title);
    }
 
    public String getWindowMachineTitle(WindowMachineAbstract wm) {
        return tabbedComponent.getTitleAt(windowMachines.indexOf(wm));
    }

    public void menuItemState(XJMenuItem item) {
        super.menuItemState(item);
        switch(item.getTag()) {
            case MI_CONTINUE:
                item.setEnabled(false);
                break;
        }
    }

    public void handleMenuEvent(XJMenu menu, XJMenuItem item) {
        super.handleMenuEvent(menu, item);
        switch(item.getTag()) {
            case MI_DISPLAYTRANSITIONS:
                XJAlert.display(getJavaContainer(), Localized.getString("faTransitionsTitle"), getCurrentWindowMachineFA().getDataWrapperFA().getMachine().getTransitions().toString());
                break;

            case MI_NFA_TO_DFA:
                convertNFA2DFA();
                break;
        }
    }

    public void createNewMachine(DataWrapperAbstract data) {
        if(data == null) {
            data = new DataWrapperFA();
            getAutomataDocument().getAutomataData().addWrapper(data);
        }

        WindowMachineFA wm = new WindowMachineFA(this);
        wm.setWindow(this);
        wm.setDataWrapper(data);
        wm.init();

        windowMachines.add(wm);
        tabbedComponent.addTab(data.getName(), wm);
        tabbedComponent.setSelectedComponent(wm);
    }

    public void duplicateCurrentMachine() {
        try {
            DataWrapperAbstract data = getAutomataDocument().getAutomataData().getDataWrapperAtIndex(currentWindowMachineIndex);
            DataWrapperAbstract copy = (DataWrapperAbstract)XJUtils.clone(data);
            getAutomataDocument().getAutomataData().addWrapper(copy);
            createNewMachine(copy);
        } catch(Exception e) {
            XJAlert.display(getJavaContainer(), Localized.getString("faDuplicateMachineTitle"), Localized.getString("faDuplicateMachineMessage")+"\n"+e);
        }
    }

    public void selectWindowMachineAtIndex(int index) {
        if(index<0 || index>=windowMachines.size())
            return;

        tabbedComponent.setSelectedIndex(index);
    }

    public void removeAllMachine() {
        windowMachines.clear();
        tabbedComponent.removeAll();
    }

    public void closeCurrentMachine() {
        getAutomataDocument().getAutomataData().removeDataWrapperAtIndex(currentWindowMachineIndex);

        windowMachines.remove(currentWindowMachineIndex);
        tabbedComponent.removeTabAt(currentWindowMachineIndex);

        getMainMenuBar().refresh();
        changeOccured();
    }

    public boolean canCloseWindowMachine() {
        return windowMachines.size()>1;
    }

    public WindowMachineFA getCurrentWindowMachineFA() {
        return (WindowMachineFA)getCurrentWindowMachineAbstract();
    }

    public WindowMachineAbstract getCurrentWindowMachineAbstract() {
        if(currentWindowMachineIndex>-1 && windowMachines.size()>currentWindowMachineIndex)
            return (WindowMachineAbstract)windowMachines.get(currentWindowMachineIndex);
        else
            return null;
    }

    public void convertNFA2DFA() {
        FAMachine m = getCurrentWindowMachineFA().convertNFA2DFA();

        int numberOfStates = m.getStateList().size();
        int numberOfTransitions = m.getTransitions().getTransitions().size();

        Object[] args = { new Integer(numberOfStates), new Integer(numberOfTransitions) };
        String message = Localized.getFormattedString("faNewDFAMessage",args);

        String[] buttons = {    Localized.getString("faNewDFACancel"),
                                Localized.getString("faNewDFAGenerateCurrentMachine"),
                                Localized.getString("faNewDFAGenerateNewMachine")
                                };
        int result = XJAlert.displayCustomAlert(getJavaContainer(), Localized.getString("faNewDFATitle"), message, buttons, 2);
        switch(result) {
            case 1:
                getCurrentWindowMachineFA().setFAMachine(m);
                break;
            case 2:
                createNewMachine(null);
                getCurrentWindowMachineFA().setFAMachine(m);
                break;
        }
    }

    public boolean isMachineStopped() {
        if(getCurrentWindowMachineFA() == null)
            return false;
        return getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().isStopped();
    }

    public boolean isMachineReady() {
        if(getCurrentWindowMachineFA() == null)
            return false;
        return getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().isReady();
    }

    public boolean isMachineRunning() {
        if(getCurrentWindowMachineFA() == null)
            return false;
        return getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().isRunning();
    }

    public boolean isMachinePaused() {
        if(getCurrentWindowMachineFA() == null)
            return false;
        return getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().isPaused();
    }

    public void run() {
        String s = getCurrentWindowMachineFA().getString();
        DataWrapperFA wrapper = getCurrentWindowMachineFA().getDataWrapperFA();
        wrapper.getMachine().setType(wrapper.getMachineType());
        wrapper.getGraphicMachine().run(s);
        updateExecutionComponents();
    }

    public void runContinue() {

    }

    public void debug() {
    	try
    	{
        String s = getCurrentWindowMachineFA().getString();
        getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().debugReset(s);
        String t = Localized.getFormattedString("faDebugReady", new Object[] { getDebugString() });
        t += "\n";
        setStateString(t);
        getStandardOutputDevice().clear();
        getStandardOutputDevice().println(t);
        getDebugger().reset();
        displayDebuggerInfo();
        updateExecutionComponents();
        String start=getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().getStartState();
        
        Constant.simulatorViewFrame.updateImage(start);
        Constant.simulatorViewFrame.setV(true);
        
        simulatorController.createButtons(Constant.eventModel.getSize());
        simulatorController.setV(true);
    	}
    	catch(Exception e) {
                XJAlert.display(getJavaContainer(), "Alert", "This state machine is not correct");
    	}
    }

    public boolean debugStepForward() {
    	try
    	{
        getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().debugStepForward();
        
        String current=getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().getCurrentState();
        Constant.simulatorViewFrame.updateImage(current);
        displayDebuggerInfo();
        updateExecutionComponents();
    	}
    	catch(Exception e) {
               
    	}
        return getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine().isPaused();
    	
    }

    public String getDebugString() {
        return getCurrentWindowMachineFA().getDataWrapperFA().getMachine().debugString();
    }

    public void updateExecutionComponents() {
        refreshMenuRun();
        getCurrentWindowMachineFA().getGraphicPanel().repaint();
    }

    public void displayDebuggerInfo() {
        FAMachine machine = getCurrentWindowMachineFA().getDataWrapperFA().getMachine();
        GElementFAMachine gmachine = getCurrentWindowMachineFA().getDataWrapperFA().getGraphicMachine();

        DebugWrapper wrapper = new DebugWrapper();

        wrapper.addElements(gmachine.debugLastStates());
        wrapper.addElements(gmachine.debugLastTransitions());

        getDebugger().reset();
        getDebugger().addHistory(wrapper);
        getDebugger().setHistoryGradientColor(machine.isAccepting()?Color.green:Color.red, Color.black);
        getDebugger().setHistoryPenSize(2);

        String t;

        String s = machine.debugString();
        if(s == null || s.length() == 0) {
            t = Localized.getString("faDebugStopped");
            setStateString(t);
        } else {
            Object[] args = { machine.debugLastSymbol(),
                              machine.debugString(),
                              machine.getStateSet().toString(),
                              Boolean.valueOf(machine.isAccepting()) };
            t = Localized.getFormattedString("faDebugInfo", args);
            setStateString(Localized.getFormattedString("faDebugRemaining", new Object[] { s }));
        }

        SimpleAttributeSet attr = new SimpleAttributeSet();
        StyleConstants.setFontSize(attr, 12);
        StyleConstants.setForeground(attr, machine.isAccepting()?Color.green:Color.red);

        getStandardOutputDevice().println(t, attr);

        //getCurrentWindowMachineFA().setDebugInfo(s);
        getCurrentWindowMachineAbstract().getGraphicPanel().repaint();
    }

    public void contextualHelp(GElement element) {
        if(element == null) {
            setHelpString(Localized.getString("faHelpNoElement"));
        } else if(element instanceof GElementFAState) {
            if(element.isSelected())
                setHelpString(Localized.getString("faHelpStateSelected"));
            else
                setHelpString(Localized.getString("faHelpStateDeselected"));
        } else if(element instanceof GLink) {
            setHelpString(Localized.getString("faHelpLink"));
        }
    }

}
