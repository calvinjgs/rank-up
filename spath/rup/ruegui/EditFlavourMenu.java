package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.ruesrc.*;
import rup.tools.*;

public class EditFlavourMenu extends EditorPanel implements ActionListener {

	private Unit unit;
	private Flavour flavour;

	private TextBox nameField;
	private DropDownMenu<String> typeMenu;
	private static final String NO_TYPE = "no type change";
	private TextBox spField, meField, raField, deField, attField, neWField, neRField, ptsField;
	private ItemList<SpecialRef> addSpecList, removeSpecList;
	private DropDownMenu<SpecialRef> addRemoveMenu;
	private JButton addAddSpecButton, deleteAddSpecButton;
	private JButton addRemoveSpecButton, deleteRemoveSpecButton;

	private JButton saveButton;


	public EditFlavourMenu(JFrame parent, Unit u, Flavour f) {
		super(new BorderLayout(), parent);
		this.unit = u;
		this.flavour = f;
		this.frame().setTitle(RUData.RUE_TITLE + " - Option");

		//north panel
		JPanel npan = new JPanel();
		this.nameField = new TextBox("name:");
		npan.add(this.nameField);
		this.nameField.setText(this.flavour.name());
		this.nameField.addActionListener(this);

		this.ptsField = new TextBox("points:");
		npan.add(this.ptsField);
		this.ptsField.setText(this.flavour.pts());
		this.ptsField.addActionListener(this);
		this.ptsField.setColumns(4);
		this.add(npan, BorderLayout.NORTH);

		//center panel
		JPanel cpan = new JPanel();
		cpan.setLayout(new BoxLayout(cpan, BoxLayout.Y_AXIS));
		//stat modifier panel
		JLabel modlabel = new JLabel(RUData.html("modifiers (leave blank for no modification)"));
		modlabel.setHorizontalAlignment(SwingConstants.CENTER);
		modlabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		cpan.add(modlabel);
		//type menu
		this.typeMenu = new DropDownMenu("Type", new String[0]);
		this.typeMenu.addItem(NO_TYPE);//the "blank" option
		for (int i = 0; i < RUData.UNIT_TYPE.values().length; i++)
			this.typeMenu.addItem(RUData.UNIT_TYPE.values()[i].toString());
		if (this.flavour.type() != null)
			this.typeMenu.setSelectedItem(this.flavour.type().toString());
		cpan.add(this.typeMenu);
		this.typeMenu.addActionListener(this);

		//stats panel

		JPanel statspanX = new JPanel();
		statspanX.setLayout(new BoxLayout(statspanX, BoxLayout.X_AXIS));

		JPanel statpan;
		JLabel label;

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("sp"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.spField = new TextBox("");
		this.spField.setText(this.flavour.sp());
		this.spField.setColumns(4);
		statpan.add(this.spField);
		this.spField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("me"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.meField = new TextBox("");
		this.meField.setText(this.flavour.me());
		this.meField.setColumns(4);
		statpan.add(this.meField);
		this.meField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("ra"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.raField = new TextBox("");
		this.raField.setText(this.flavour.ra());
		this.raField.setColumns(4);
		statpan.add(this.raField);
		this.raField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("de"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.deField = new TextBox("");
		this.deField.setText(this.flavour.de());
		this.deField.setColumns(4);
		statpan.add(this.deField);
		this.deField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("att"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.attField = new TextBox("");
		this.attField.setText(this.flavour.att());
		this.attField.setColumns(4);
		statpan.add(this.attField);
		this.attField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("nerve"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		JPanel nervepan = new JPanel();
		nervepan.setLayout(new GridLayout(0, 2, 0, 0));
		this.neWField = new TextBox("");
		this.neWField.setText(this.flavour.neW());
		this.neWField.setColumns(4);
		nervepan.add(this.neWField);
		this.neWField.addActionListener(this);
		this.neRField = new TextBox("");
		this.neRField.setText(this.flavour.neR());
		this.neRField.setColumns(4);
		nervepan.add(this.neRField);
		this.neRField.addActionListener(this);
		statpan.add(nervepan);
		statspanX.add(statpan);

		cpan.add(statspanX);
		//this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right





		//add/remove specials panel
		JPanel listspan = new JPanel();
		listspan.setLayout(new GridLayout(0, 2, 10, 10));
		//build add specials list panel
		JPanel lpan1 = new JPanel();
		lpan1.setLayout(new BoxLayout(lpan1, BoxLayout.Y_AXIS));
		this.addSpecList = new ItemList("add specials:", this.flavour.addSpecials());
		this.addSpecList.setRows(3);
		lpan1.add(this.addSpecList);
		this.addAddSpecButton = new JButton(RUData.html("Add"));
		this.deleteAddSpecButton = new JButton(RUData.html("Delete"));
		this.addAddSpecButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.deleteAddSpecButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.addAddSpecButton.addActionListener(this);
		this.deleteAddSpecButton.addActionListener(this);
		lpan1.add(this.addAddSpecButton);
		lpan1.add(this.deleteAddSpecButton);
		listspan.add(lpan1);
		//build remove specials list panel
		JPanel lpan2 = new JPanel();
		lpan2.setLayout(new BoxLayout(lpan2, BoxLayout.Y_AXIS));
		this.removeSpecList = new ItemList("remove specials:", this.flavour.removeSpecials());
		this.removeSpecList.setRows(3);
		lpan2.add(this.removeSpecList);
		this.addRemoveMenu = new DropDownMenu("select a special to add to list", new SpecialRef[0]);
		//add specials to menu that are not to be removed.
		for (int i = 0; i < this.unit.specials().length; i++) {
			boolean isIn = false;
			for (int j = 0; j < this.flavour.removeSpecials().length; j++) {
				if (this.unit.specials(i).equals(this.flavour.removeSpecials(j))) {
					isIn = true; break;
				}
			}
			if (!isIn) this.addRemoveMenu.addItem(this.unit.specials(i));
		}
		this.addRemoveMenu.setPrototypeDisplayValue("XXXXXXXXXXXXXXXXXXXXXXXXXXXX");
		lpan2.add(this.addRemoveMenu);
		this.addRemoveMenu.addActionListener(this);
		this.addRemoveSpecButton = new JButton(RUData.html("Add"));
		this.deleteRemoveSpecButton = new JButton(RUData.html("Delete"));
		this.addRemoveSpecButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.deleteRemoveSpecButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.addRemoveSpecButton.addActionListener(this);
		this.deleteRemoveSpecButton.addActionListener(this);
		lpan2.add(this.addRemoveSpecButton);
		lpan2.add(this.deleteRemoveSpecButton);
		listspan.add(lpan2);

		listspan.setBorder(new EmptyBorder(25, 1, 25, 1));
		cpan.add(listspan);



		this.add(cpan, BorderLayout.CENTER);


		//build save button
		JPanel savePanel = new JPanel();
		this.saveButton = new JButton(RUData.html("Save", RUData.titleSize*2));
		savePanel.add(this.saveButton);
		this.add(savePanel, BorderLayout.SOUTH);
		this.saveButton.addActionListener(this);

		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right
		this.frame().add(this);
		//this.frame().setSize(450, 500);
		this.frame().pack();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addAddSpecButton) {
			SpecialRef newSpecial = new SpecialRef("new special","");
			this.addSpecList.addItem(newSpecial);
			EditSpecialRefMenu esm = new EditSpecialRefMenu(this.frame(), newSpecial);
			this.switchTo(esm);
		} else if (e.getSource() == deleteAddSpecButton) {
			this.addSpecList.removeSelectedItem();
		} else if (e.getSource() == addRemoveSpecButton) {
			SpecialRef toRemove = this.addRemoveMenu.selectedItem();
			this.addRemoveMenu.removeSelectedItem();
			this.removeSpecList.addItem(toRemove);
		} else if (e.getSource() == deleteRemoveSpecButton) {
			SpecialRef notRemove = this.removeSpecList.selectedItem();
			this.removeSpecList.removeSelectedItem();
			this.addRemoveMenu.addItem(notRemove);
		} else if (e.getSource() == saveButton) {
			//save name
			String newName = this.nameField.getText();
			if(!("".equals(newName))) {
				this.flavour.setName(newName);
			}
			//save type
			if (this.typeMenu.selectedItem().equals(NO_TYPE)) {
				this.flavour.setType(null);
			} else {
				RUData.UNIT_TYPE type = RUData.UNIT_TYPE.toType(this.typeMenu.selectedItem());
				this.flavour.setType(type);
			}
			//save stats
			String stat = this.spField.getText();
			this.flavour.setSp(stat);

			stat = this.meField.getText();
			this.flavour.setMe(stat);
			stat = this.raField.getText();
			this.flavour.setRa(stat);
			stat = this.deField.getText();
			this.flavour.setDe(stat);
			stat = this.attField.getText();
			this.flavour.setAtt(stat);
			stat = this.neWField.getText();
			this.flavour.setNeW(stat);
			stat = this.neRField.getText();
			this.flavour.setNeR(stat);
			stat = this.ptsField.getText();
			this.flavour.setPts(stat);


			//save addSpecials
			SpecialRef[] addSpecs = new SpecialRef[this.addSpecList.length()];
			for (int i = 0; i < addSpecs.length; i++) {
				addSpecs[i] = this.addSpecList.item(i);
			}
			this.flavour.setAddSpecials(addSpecs);

			//save removeSpecials
			SpecialRef[] removeSpecials = new SpecialRef[this.removeSpecList.length()];
			for (int i = 0; i < removeSpecials.length; i++) {
				removeSpecials[i] = this.removeSpecList.item(i);
			}
			this.flavour.setRemoveSpecials(removeSpecials);
			this.frame().dispatchEvent(new WindowEvent(this.frame(), WindowEvent.WINDOW_CLOSING));

		}

	}


	public void panelShown() {}
	public void panelHidden() {}
	public void windowClosing() {}


	//return things
	public JFrame frame() {
		return this.frame;
	}
}