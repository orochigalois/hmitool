package edu.usfca.vas.window.fa;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;

import edu.usfca.vas.graphics.fa.Constant;


public class AlexEventListRenderer extends DefaultListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, hasFocus);

//		ImageIcon icon = new ImageIcon(
//				(String) Constant.filenameTable.get(value.toString()));
//		Constant.iconTable.put(value.toString(), icon);
//		label.setIcon(icon);
		label.setText(value.toString());

		return (label);
	}
}