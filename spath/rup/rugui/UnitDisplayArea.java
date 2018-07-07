package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class UnitDisplayArea extends JPanel implements ListSelectionListener {

	RUGUI ui;

	private JEditorPane stats;
	private JList<specialEntry> speclist;
	private int tableTextSize = RUData.textSize + 6;
	private SpecialRef selectedSpecialRef;

	public UnitDisplayArea(RUGUI ui) {
		//super(new GridLayout(1, 2));
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.ui = ui;
		//CENTER name, and stats text area
		this.stats = new JEditorPane();
		this.stats.setContentType("text/html");
		this.stats.setEditable(false);
		this.stats.setPreferredSize(new Dimension(tableTextSize*20, 1000));
		this.stats.setMaximumSize(new Dimension(tableTextSize*20, 1000));
		this.add(this.stats);
		//EAST specials list
		JPanel east = new JPanel(new BorderLayout());
		east.add(new JLabel(RUData.html("Specials:")), BorderLayout.NORTH);
		this.speclist = new JList(new Special[0]);
		this.speclist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.speclist.setLayoutOrientation(JList.VERTICAL_WRAP);
		this.speclist.setVisibleRowCount(-1);
		east.add(this.speclist, BorderLayout.CENTER);
		this.add(east);
		this.speclist.addListSelectionListener(this);

	}

	public void displaySelectedUnit() {
		SelectedUnit unit = this.ui.selectedUnit();
		if (unit == null) return;
		String a = "";

		a += unit.name() + "<br>";
		a += unit.type() + "<br>";
		a += unit.sizeName() + "<br>";

		//statline
		//headings
		a += "<table border=0 class=\"text\"><tr><th>Sp</th><th>Me</th><th>Ra</th><th>De</th>";
		a += "<th>Att</th><th>Ne</th><th>Pts</th></tr>";
		//values
		a += "<tr><td>" + RUData.displayStat(unit.sp()) + "</td>";
		a += "<td>" + RUData.displayStatPlus(unit.me()) + "</td>";
		a += "<td>" + RUData.displayStatPlus(unit.ra()) + "</td>";
		a += "<td>" + RUData.displayStatPlus(unit.de()) + "</td>";
		a += "<td>" + RUData.displayStat(unit.att()) + "</td>";
		a += "<td>" + RUData.displayStat(unit.neW()) + "/" + unit.neR() + "</td>";
		a += "<td>" + RUData.displayStat(unit.pts()) + "</td></tr></table>";

		this.stats.setText(RUData.html(a, tableTextSize));

		//specials list
		specialEntry[] specentries = new specialEntry[unit.specials().length];
		for (int s = 0; s < unit.specials().length; s++) {
			specentries[s] = new specialEntry(unit.specials(s));
		}
		this.speclist.setListData(specentries);
	}

	public void displaySelectedFormation() {
		Formation formation = this.ui.selectedFormation();
		if (formation == null) return;
		String a = "";

		a += formation.name() + "<br>";
		a += formation.pts() + "pts<br><br>";
		a += formation.description();
		this.stats.setText(RUData.html(a, tableTextSize));

		//formations have no inherent specials
		this.speclist.setListData(new specialEntry[0]);
	}

	public void valueChanged(ListSelectionEvent e) {
		specialEntry se = this.speclist.getSelectedValue();
		if (se != null) {
			SpecialRef specref = se.special;
			Special special = specref.special(RUData.WORKINGPACKAGE.specials());
			if (special != null)
				this.ui.specialDisplayArea().displaySelectedSpecial(special);
		}
	}

	//define special entry for comboboxes
	class specialEntry {
		SpecialRef special;
		public specialEntry(SpecialRef special) {
			this.special = special;
		}
		public String toString() {
			return RUData.html(special.toString());
		}
	}


}