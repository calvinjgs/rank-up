package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.tools.*;

public class TextBoxArea extends JPanel {

	String name;
	JTextArea area;

	public TextBoxArea(String n) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.name = n;
		this.area = new JTextArea();
		this.area.setFont(new Font(null, Font.PLAIN, RUData.titleSize));
		JLabel label = new JLabel(RUData.html(n));
		this.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.area.setLineWrap(true);
		this.setColumns(10);
		this.setRows(10);
		JScrollPane scrollpane = new JScrollPane(area);
		this.add(scrollpane);
	}


	public void setText(String t) {
		this.area.setText(t);
	}

	public void setColumns(int c) {
		this.area.setColumns(c);
	}
	public void setRows(int c) {
		this.area.setRows(c);
	}

	//return things
	public JTextArea area() {
		return this.area;
	}

	public String getText() {
		return this.area.getText();
	}
}