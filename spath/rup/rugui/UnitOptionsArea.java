package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class UnitOptionsArea extends JPanel implements ActionListener{

	private RUGUI ui;

	private JComboBox sizelist;
	private JComboBox[] optionlist;
	private JComboBox artefactlist;
	private int maxBoxHeight = RUData.textSize*2 + 16;
	private DynamicArray<Artifact> artefactsTaken;

	public UnitOptionsArea(RUGUI ui) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.ui = ui;
		this.artefactsTaken = new DynamicArray(new UnitOption[0]);

	}

	public void displayUnitOptions() {
		//first remove all comboboxes from the list
		this.removeAll();
		this.revalidate();
		this.repaint();

		//then start building the new ones
		SelectedUnit unit = this.ui.selectedUnit();
		if (unit == null) return;

		//combobox for unit size
		JLabel lbl = new JLabel(RUData.html("Unit Size:"));
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(lbl);
		sizeEntry[] sizes = new sizeEntry[unit.unit().sizes().length];
		for (int s = 0; s < unit.unit().sizes().length; s++) {
			sizes[s] = new sizeEntry(unit.unit().sizes(s).name().toString(), s);
		}
		JComboBox sz = new JComboBox(sizes);
		sz.setSelectedIndex(unit.sizeIndex());
		sz.addActionListener(this);
		sz.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxBoxHeight));
		this.sizelist = sz;
		this.add(sz);

		//comboboxes for options
		this.optionlist = new JComboBox[unit.unit().options().length];
		if (unit.unit().options().length != 0) {
			lbl = new JLabel(RUData.html("Options:"));
			lbl.setHorizontalAlignment(SwingConstants.CENTER);
			lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
			this.add(lbl);

		}
		for (int o = 0; o < unit.unit().options().length; o++) {
			//create new combo box with all mutually exlcusive flavours
			flavourEntry[] flaves = new flavourEntry[unit.unit().options(o).flavours().length + 1];
			//add 'none' flavour
			flaves[0] = new flavourEntry("none", o, -1);
			int sf = -1;
			for (int f = 0; f < unit.unit().options(o).flavours().length; f++) {
				//add flavour
				flaves[f + 1] = new flavourEntry(unit.unit().options(o).flavours(f).name(), o, f);
				//set possible selected flavour index
				if (unit.selectedOptions(o) == f) sf = f + 1;
			}
			JComboBox optn = new JComboBox(flaves);
			optn.setSelectedIndex(sf);
			optn.addActionListener(this);
			optn.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxBoxHeight));
			this.optionlist[o] = optn;
			this.add(optn);
		}

		//combobox for magical artefacts
		buildArtefactsTaken();
		lbl = new JLabel(RUData.html("Magical Artifacts:"));
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(lbl);
		DynamicArray<artefactEntry> arts = new DynamicArray(new artefactEntry[0]);
		arts.add(new artefactEntry("none", -1));
		int sa = -1;
		for (int a = 0; a < RUData.WORKINGPACKAGE.artifacts().length; a++) {
			boolean include = true;
			for (int at = 0; at < artefactsTaken.size(); at++) {
				include = include && RUData.WORKINGPACKAGE.artifacts(a) != artefactsTaken.storage(at);
			}
			include = include || RUData.WORKINGPACKAGE.artifacts(a) == unit.artefact();
			if (include) {//leave out taken artefacts, but include unit's artefact
				arts.add(new artefactEntry(RUData.WORKINGPACKAGE.artifacts(a).name(), a));
			}
		}
		//set possible selected artefact index
		for (int at = 1; at < arts.size(); at++) {
			if (RUData.WORKINGPACKAGE.artifacts(arts.storage(at).i) == unit.artefact()) {
				sa = at;
				break;
			}
		}
		arts.trim();
		this.artefactlist = new JComboBox(arts.storage());
		artefactlist.setSelectedIndex(sa);
		artefactlist.addActionListener(this);
		artefactlist.setMaximumSize(new Dimension(Integer.MAX_VALUE, maxBoxHeight));
		this.add(artefactlist);
	}

	public void actionPerformed(ActionEvent e) {
		JComboBox cb = (JComboBox) e.getSource();
		Object obj = cb.getSelectedItem();
		SelectedUnit su = this.ui.selectedUnit();
		if (obj instanceof flavourEntry) {
			flavourEntry fe = (flavourEntry) obj;
			//deselect all flavours in this option
			su.setSelectedOptions(fe.i, -1);
			//select this option
			if (fe.j != -1) su.setSelectedOptions(fe.i, fe.j);
		} else if (obj instanceof sizeEntry) {
			sizeEntry se = (sizeEntry) obj;
			//set the unit size
			su.setSize(se.i);
		} else if (obj instanceof artefactEntry) {
			artefactEntry ae = (artefactEntry) obj;
			//add artefact
			if (ae.i != -1) {
				su.setArtefact(RUData.WORKINGPACKAGE.artifacts(ae.i));
			} else {
				su.setArtefact(null);//'none' selected
			}
		}
		//apply options/size, recalculate reqs, and update rugui
		su.reapply();
		this.ui.armyReqs().buildRequirements(this.ui.army().detachments());
		this.ui.refreshDisplays();
	}

	//define flavour entry for comboboxes
	class flavourEntry {
		String option;
		public int i, j;
		public flavourEntry(String option, int i, int j) {
			this.option = option;
			this.i = i;
			this.j = j;
		}
		public String toString() {
			return RUData.html(option);
		}
	}

	//define unit size entry for combobox
	class sizeEntry {
		String size;
		public int i;
		public sizeEntry(String size, int i) {
			this.size = size;
			this.i = i;
		}
		public String toString() {
			return RUData.html(size);
		}
	}

	//define unit artefact entry for combobox
	class artefactEntry {
		String artefact;
		public int i;
		public artefactEntry(String artefact, int i) {
			this.artefact = artefact;
			this.i = i;
		}
		public String toString() {
			return RUData.html(artefact);
		}
	}

	public void buildArtefactsTaken() {
		this.artefactsTaken = new DynamicArray(new Artifact[0]);

		ArmyElement[][] dets = this.ui.army().detachments();
		for (int d = 0; d < dets.length; d++) {
			for (int u = 0; u < dets[d].length; u++) {
				if (!(dets[d][u] instanceof SelectedUnit)) return;
				SelectedUnit unit = (SelectedUnit) dets[d][u];
				if (unit.artefact() != null) {
					this.artefactsTaken.add(unit.artefact());
				}
			}
		}


	}

}