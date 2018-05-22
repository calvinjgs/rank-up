package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class SpecialDisplayArea extends JPanel {

	RUGUI ui;
	JEditorPane area;

	public SpecialDisplayArea(RUGUI ui) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.ui = ui;
		this.area = new JEditorPane();
		this.area.setContentType("text/html");
		this.area.setEditable(false);
		//this.area.setPreferredSize(new Dimension(tableTextSize*20, 1000));
		//this.area.setMaximumSize(new Dimension(tableTextSize*20, 1000));
		JScrollPane jsp = new JScrollPane(this.area, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp);


	}

	public void displaySelectedSpecial(Special special) {
		String a = "";

		a += "<b>" + special.name() + "</b><br>";
		a += special.description();

		this.area.setText(RUData.html(a, RUData.titleSize));

	}










}