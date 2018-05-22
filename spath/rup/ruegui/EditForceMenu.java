package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditForceMenu extends EditorPanel implements ActionListener {

	Force force;
	TextBox nameField;
	DropDownMenu<RUData.ALIGNMENT> alignmentMenu;
	private ItemList<Unit> unitList;
	private ItemList<Formation> formationList;
	private ListEditButtons ulButtons, flButtons;
	private JButton saveButton;


	public EditForceMenu(JFrame parent, Force f) {
		super(new BorderLayout(), parent);
		this.force = f;
		this.frame().setTitle(RUData.RUE_TITLE + " - Force");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		//name and alignment properties
		this.nameField = new TextBox("Name");
		this.nameField.setText(this.force.name());
		pan.add(this.nameField);
		this.nameField.addActionListener(this);

		this.alignmentMenu = new DropDownMenu("Alignment", RUData.ALIGNMENT.values());
		this.alignmentMenu.setSelectedItem(this.force.alignment());
		pan.add(this.alignmentMenu);
		this.alignmentMenu.addActionListener(this);

		JPanel listspan = new JPanel();
		listspan.setLayout(new GridLayout(0, 2, 10, 10));
		//unit list
		JPanel upan = new JPanel();
		upan.setLayout(new BoxLayout(upan, BoxLayout.Y_AXIS));
		this.unitList = new ItemList<Unit>("Units", force.units());
		upan.add(this.unitList);
		this.ulButtons = new ListEditButtons();
		this.ulButtons.addActionListener(this);
		upan.add(this.ulButtons);
		listspan.add(upan);

		JPanel fpan = new JPanel();
		fpan.setLayout(new BoxLayout(fpan, BoxLayout.Y_AXIS));
		this.formationList = new ItemList<Formation>("Formations", force.formations());
		fpan.add(this.formationList);
		this.flButtons = new ListEditButtons();
		this.flButtons.addActionListener(this);
		fpan.add(this.flButtons);
		listspan.add(fpan);

		pan.add(listspan);


		JPanel savePanel = new JPanel();
		this.saveButton = new JButton(RUData.html("Save", RUData.titleSize*2));
		savePanel.add(this.saveButton);
		this.add(savePanel, BorderLayout.SOUTH);
		this.saveButton.addActionListener(this);


		this.add(pan, BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right


		this.frame().add(this);
		this.frame().setSize(450, 500);
		this.frame().pack();
	}

	public void actionPerformed(ActionEvent e) {
		if (this.ulButtons.isAddButtonEvent(e)) {
			Unit newUnit = new Unit();
			newUnit.setName("new unit");
			this.unitList.addItem(newUnit);
			EditUnitMenu eum = new EditUnitMenu(this.frame(), newUnit);
			this.switchTo(eum);
		} else if (this.ulButtons.isEditButtonEvent(e)) {
			if (this.unitList.selectedItem() != null) {
				Unit thisUnit = this.unitList.selectedItem();
				EditUnitMenu eum = new EditUnitMenu(this.frame(), thisUnit);
				this.switchTo(eum);
			}
		} else if (this.ulButtons.isDeleteButtonEvent(e)) {
			this.unitList.removeSelectedItem();
		} else if (this.ulButtons.isUpButtonEvent(e)) {
			this.unitList.moveUp();
		} else if (this.ulButtons.isDownButtonEvent(e)) {
			this.unitList.moveDown();
		} else if (this.flButtons.isAddButtonEvent(e)) {
			Formation newFormation = new Formation();
			newFormation.setName("new formation");
			this.formationList.addItem(newFormation);
			EditFormationMenu efm = new EditFormationMenu(this.frame(), newFormation);
			this.switchTo(efm);
		} else if (this.flButtons.isEditButtonEvent(e)) {
			Formation thisFormation = this.formationList.selectedItem();
			EditFormationMenu efm = new EditFormationMenu(this.frame(), thisFormation);
			this.switchTo(efm);
		} else if (this.flButtons.isDeleteButtonEvent(e)) {
			this.formationList.removeSelectedItem();
		} else if (this.flButtons.isUpButtonEvent(e)) {
			this.formationList.moveUp();
		} else if (this.flButtons.isDownButtonEvent(e)) {
			this.formationList.moveDown();
		} else if (e.getSource() == this.saveButton) {
			//save name
			String newName = this.nameField.getText();
			if(!("".equals(newName))) {
				this.force.setName(newName);
			}
			//save alignment
			RUData.ALIGNMENT alignment = this.alignmentMenu.selectedItem();
			this.force.setAlignment(alignment);
			//save units
			Unit[] units = new Unit[this.unitList.length()];
			for (int i = 0; i < units.length; i++) {
				units[i] = this.unitList.item(i);
			}
			this.force.setUnits(units);
			//save formations
			Formation[] formations = new Formation[this.formationList.length()];
			for (int i = 0; i < formations.length; i++) {
				formations[i] = this.formationList.item(i);
			}
			this.force.setFormations(formations);
			this.frame().dispatchEvent(new WindowEvent(this.frame(), WindowEvent.WINDOW_CLOSING));

		}
	}



	public void panelShown() {System.out.println("EditForceMenu shown");}
	public void panelHidden() {System.out.println("EditForceMenu hidden");}
	public void windowClosing() {}

	//return things
	public JFrame frame() {
		return this.frame;
	}
}