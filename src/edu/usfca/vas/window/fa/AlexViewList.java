package edu.usfca.vas.window.fa;

import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.util.Hashtable;

import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JList;
import javax.swing.TransferHandler;

import edu.usfca.vas.graphics.fa.Constant;

@SuppressWarnings("serial")
public class AlexViewList extends JList implements DragSourceListener,
		DragGestureListener {

	DragSource ds;
	StringSelection transferable;
	public Hashtable iconTable = new Hashtable();

	public AlexViewList(DefaultListModel parent) {
		super(parent);

		this.setCellRenderer(new AlexViewListRenderer());
		Font displayFont = new Font("Serif", Font.BOLD, 18);
		this.setFont(displayFont);
		this.setDragEnabled(true);
		this.setDropMode(DropMode.INSERT);
		this.setTransferHandler(new AlexListDropHandler(this));

		ds = new DragSource();
		ds.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY,
				this);

	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		// TODO Auto-generated method stub
		System.out.println("Drag Gesture Recognized!");
		transferable = new StringSelection(this.getSelectedValue().toString());
		ds.startDrag(dge, DragSource.DefaultCopyDrop, transferable, this);
		Constant.s = this.getSelectedValues()[0].toString();
		Constant.s = Constant.removeExtention(Constant.s);

	}

	@Override
	public void dragDropEnd(DragSourceDropEvent dsde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragEnter(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragExit(DragSourceEvent dse) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dragOver(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// TODO Auto-generated method stub

	}

}

@SuppressWarnings("serial")
class AlexListDropHandler extends TransferHandler {
	JList list;

	public AlexListDropHandler(JList list) {
		this.list = list;
	}

	public boolean canImport(TransferHandler.TransferSupport support) {
		if (!support.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return false;
		}
		JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
		if (dl.getIndex() == -1) {
			return false;
		} else {
			return true;
		}
	}

	// public boolean importData(TransferHandler.TransferSupport support) {
	// if (!canImport(support)) {
	// return false;
	// }
	//
	// Transferable transferable = support.getTransferable();
	// String indexString;
	// try {
	// indexString = (String) transferable
	// .getTransferData(DataFlavor.stringFlavor);
	// } catch (Exception e) {
	// return false;
	// }
	//
	// int index = Integer.parseInt(indexString);
	// JList.DropLocation dl = (JList.DropLocation) support.getDropLocation();
	// int dropTargetIndex = dl.getIndex();
	//
	// System.out.println(dropTargetIndex + " : ");
	// System.out.println("inserted");
	// return true;
	// }
}