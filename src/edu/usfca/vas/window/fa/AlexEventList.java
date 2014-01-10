package edu.usfca.vas.window.fa;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.DropMode;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;


import edu.usfca.vas.graphics.fa.Constant;

public class AlexEventList extends JList {


	ArrayListTransferHandler arrayListHandler;

	public AlexEventList(DefaultListModel parent) {
		super(parent);

		this.setCellRenderer(new AlexEventListRenderer());
		Font displayFont = new Font("Serif", Font.BOLD, 14);
		this.setFont(displayFont);

		
		
		arrayListHandler = new ArrayListTransferHandler();

		


		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setTransferHandler(arrayListHandler);
		this.setDragEnabled(true);


	}


}

class ArrayListTransferHandler extends TransferHandler {
	DataFlavor localArrayListFlavor;

	String localArrayListType = DataFlavor.javaJVMLocalObjectMimeType
			+ ";class=java.util.ArrayList";

	JList source = null;

	int[] indices = null;

	int addIndex = -1; // Location where items were added

	int addCount = 0; // Number of items added

	public ArrayListTransferHandler() {
		try {
			localArrayListFlavor = new DataFlavor(localArrayListType);
		} catch (ClassNotFoundException e) {
			System.out
					.println("ArrayListTransferHandler: unable to create data flavor");
		}
	}

	public boolean importData(JComponent c, Transferable t) {
		JList target = null;
		ArrayList alist = null;
		if (!canImport(c, t.getTransferDataFlavors())) {
			return false;
		}
		try {
			target = (JList) c;
			if (hasLocalArrayListFlavor(t.getTransferDataFlavors())) {
				alist = (ArrayList) t.getTransferData(localArrayListFlavor);
			} else {
				return false;
			}
		} catch (UnsupportedFlavorException ufe) {
			System.out.println("importData: unsupported data flavor");
			return false;
		} catch (IOException ioe) {
			System.out.println("importData: I/O exception");
			return false;
		}

		// At this point we use the same code to retrieve the data
		// locally or serially.

		// We'll drop at the current selected index.
		int index = target.getSelectedIndex();

		// Prevent the user from dropping data back on itself.
		// For example, if the user is moving items #4,#5,#6 and #7 and
		// attempts to insert the items after item #5, this would
		// be problematic when removing the original items.
		// This is interpreted as dropping the same data on itself
		// and has no effect.
//		if (source.equals(target)) {
//			if (indices != null && index >= indices[0] - 1
//					&& index <= indices[indices.length - 1]) {
//				indices = null;
//				return true;
//			}
//		}

		DefaultListModel listModel = (DefaultListModel) target.getModel();
		int max = listModel.getSize();
		if (index < 0) {
			index = max;
		} else {
			index++;
			if (index > max) {
				index = max;
			}
		}
		addIndex = index;
		addCount = alist.size();

		if (indices[0] >= index) {
			for (int i = 0; i < alist.size(); i++) {
				listModel.add(index-1, alist.get(i));
				index++;
			}

		} else {
			for (int i = 0; i < alist.size(); i++) {
				listModel.add(index++, alist.get(i));
			}
		}
		return true;
	}

	protected void exportDone(JComponent c, Transferable data, int action) {
		if ((action == MOVE) && (indices != null)) {
			DefaultListModel model = (DefaultListModel) source.getModel();

			// If we are moving items around in the same list, we
			// need to adjust the indices accordingly since those
			// after the insertion point have moved.
			if (addCount > 0) {
				for (int i = 0; i < indices.length; i++) {
					if (indices[i] >= addIndex) {
						indices[i] += addCount;
					}
				}
			}
			for (int i = indices.length - 1; i >= 0; i--)
				model.remove(indices[i]);
		}
		indices = null;
		addIndex = -1;
		addCount = 0;
	}

	private boolean hasLocalArrayListFlavor(DataFlavor[] flavors) {
		if (localArrayListFlavor == null) {
			return false;
		}

		for (int i = 0; i < flavors.length; i++) {
			if (flavors[i].equals(localArrayListFlavor)) {
				return true;
			}
		}
		return false;
	}

	public boolean canImport(JComponent c, DataFlavor[] flavors) {
		if (hasLocalArrayListFlavor(flavors)) {
			return true;
		}

		return false;
	}

	protected Transferable createTransferable(JComponent c) {
		if (c instanceof JList) {
			source = (JList) c;
			indices = source.getSelectedIndices();
			Object[] values = source.getSelectedValues();
			if (values == null || values.length == 0) {
				return null;
			}
			ArrayList alist = new ArrayList(values.length);
			for (int i = 0; i < values.length; i++) {
				Object o = values[i];
				String str = o.toString();
				if (str == null)
					str = "";
				alist.add(str);
			}
			return new ArrayListTransferable(alist);
		}
		return null;
	}

	public int getSourceActions(JComponent c) {
		return MOVE;
	}

	public class ArrayListTransferable implements Transferable {
		ArrayList data;

		public ArrayListTransferable(ArrayList alist) {
			data = alist;
		}

		public Object getTransferData(DataFlavor flavor)
				throws UnsupportedFlavorException {
			if (!isDataFlavorSupported(flavor)) {
				throw new UnsupportedFlavorException(flavor);
			}
			return data;
		}

		public DataFlavor[] getTransferDataFlavors() {
			return new DataFlavor[] { localArrayListFlavor };
		}

		public boolean isDataFlavorSupported(DataFlavor flavor) {
			if (localArrayListFlavor.equals(flavor)) {
				return true;
			}

			return false;
		}
	}
}