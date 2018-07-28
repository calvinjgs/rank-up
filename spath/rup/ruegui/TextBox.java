package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.tools.*;

public class TextBox extends JPanel {

	String name;
	JTextField field;

	public TextBox(String n) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.name = n;
		this.field = new JTextField();
		this.field.setFont(new Font(null, Font.PLAIN, RUData.titleSize));
		JLabel label = new JLabel(RUData.html(n));
		this.add(label);
		label.setHorizontalAlignment(SwingConstants.TRAILING);
		this.setColumns(10);

		this.add(this.field);
	}


	public void setText(String t) {
		this.field.setText(t);
	}

	public void setColumns(int c) {
		this.field.setColumns(c);
	}

	public void addActionListener(ActionListener ep) {
		this.field.addActionListener(ep);
	}

	public boolean caused(ActionEvent e) {
		return e.getSource() == this.field;
	}
	//return things
	public JTextField field() {
		return this.field;
	}

	public String getText() {
		return this.field.getText();
	}
}