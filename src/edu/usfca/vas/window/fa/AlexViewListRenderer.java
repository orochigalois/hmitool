package edu.usfca.vas.window.fa;

import javax.swing.*;

import edu.usfca.vas.graphics.fa.Constant;

import java.awt.*;

public class AlexViewListRenderer extends DefaultListCellRenderer {

	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean hasFocus) {
		JLabel label = (JLabel) super.getListCellRendererComponent(list, value,
				index, isSelected, hasFocus);

		ImageIcon icon = new ImageIcon(
				(String) Constant.filenameTable.get(value.toString()));
		Constant.iconTable.put(value.toString(), icon);
		label.setIcon(icon);

		return (label);
	}
}