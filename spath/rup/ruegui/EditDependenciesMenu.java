package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditDependenciesMenu extends EditorPanel implements ActionListener {

	String[] deps;
	private ItemList<String> depsList;
	private ListEditButtons depsButtons;
	private JButton saveButton;


	public EditDependenciesMenu(JFrame parent, String[] s) {
		super(new BorderLayout(), parent);
		this.deps = s;
		this.frame().setTitle(RUData.RUE_TITLE + " - Dependencies");

		//deps list


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

		}
	}

	//return things
	public JFrame frame() {
		return this.frame;
	}
}