package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.ruesrc.*;
import rup.tools.*;

public class EditUnitMenu extends EditorPanel implements ActionListener {

	private Unit unit;

	private TextBox nameField;
	private DropDownMenu<RUData.UNIT_TYPE> typeMenu;

	private ItemList<UnitSize> sizeList;
	private ItemList<SpecialRef> specialList;
	private ItemList<UnitOption> optionList;

	private ListEditButtons sizeButtons, specialButtons, optionButtons;

	private JButton saveButton;


	public EditUnitMenu(JFrame parent, Unit u) {
		super(new BorderLayout(), parent);
		this.unit = u;
		this.frame().setTitle(RUData.RUE_TITLE + " - Unit");


		//name field
		JPanel npan = new JPanel();
		this.nameField = new TextBox("name:");
		npan.add(this.nameField);

		this.add(npan, BorderLayout.NORTH);
		this.nameField.setText(this.unit.name());
		this.nameField.addActionListener(this);

		//type menu
		this.typeMenu = new DropDownMenu("Type", RUData.UNIT_TYPE.values());
		this.typeMenu.setSelectedItem(this.unit.type());
		npan.add(this.typeMenu);
		this.typeMenu.addActionListener(this);


		JPanel listspan = new JPanel();
		listspan.setLayout(new GridLayout(0, 3, 10, 10));
		//build force list panel
		JPanel lpan1 = new JPanel();
		lpan1.setLayout(new BoxLayout(lpan1, BoxLayout.Y_AXIS));
		this.sizeList = new ItemList("Sizes:", this.unit.sizes());
		lpan1.add(this.sizeList);
		this.sizeButtons = new ListEditButtons();
		this.sizeButtons.addActionListener(this);
		lpan1.add(this.sizeButtons);
		listspan.add(lpan1);
		//build specials list panel
		JPanel lpan2 = new JPanel();
		lpan2.setLayout(new BoxLayout(lpan2, BoxLayout.Y_AXIS));
		this.specialList = new ItemList("Specials:", this.unit.specials());
		lpan2.add(this.specialList);
		this.specialButtons = new ListEditButtons();
		this.specialButtons.addActionListener(this);
		lpan2.add(this.specialButtons);
		listspan.add(lpan2);
		//build option list panel
		JPanel lpan3 = new JPanel();
		lpan3.setLayout(new BoxLayout(lpan3, BoxLayout.Y_AXIS));
		this.optionList = new ItemList("Options:", this.unit.options());
		lpan3.add(this.optionList);
		this.optionButtons = new ListEditButtons();
		this.optionButtons.addActionListener(this);
		lpan3.add(this.optionButtons);
		listspan.add(lpan3);

		listspan.setBorder(new EmptyBorder(25, 1, 25, 1));
		this.add(listspan, BorderLayout.CENTER);

		//build save button
		JPanel savePanel = new JPanel();
		this.saveButton = new JButton(RUData.html("Save", RUData.titleSize*2));
		savePanel.add(this.saveButton);
		this.add(savePanel, BorderLayout.SOUTH);
		this.saveButton.addActionListener(this);

		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right
		this.frame().add(this);
		this.frame().setSize(450, 500);
		this.frame().pack();

	}

	public void actionPerformed(ActionEvent e) {
		if (this.sizeButtons.isAddButtonEvent(e)) {
			UnitSize newSize = new UnitSize();
			this.sizeList.addItem(newSize);
			EditSizeMenu esm = new EditSizeMenu(this.frame(), newSize);
			this.switchTo(esm);
		} else if (this.sizeButtons.isEditButtonEvent(e)) {
			UnitSize thisSize = this.sizeList.selectedItem();
			EditSizeMenu esm = new EditSizeMenu(this.frame(), thisSize);
			this.switchTo(esm);
		} else if (this.sizeButtons.isDeleteButtonEvent(e)) {
			this.sizeList.removeSelectedItem();
		} else if (this.sizeButtons.isUpButtonEvent(e)) {
			this.sizeList.moveUp();
		} else if (this.sizeButtons.isDownButtonEvent(e)) {
			this.sizeList.moveDown();

		} else if (this.specialButtons.isAddButtonEvent(e)) {
			SpecialRef newSpecial = new SpecialRef();
			newSpecial.setSpecName("new special");
			this.specialList.addItem(newSpecial);
			EditSpecialRefMenu esm = new EditSpecialRefMenu(this.frame(), newSpecial);
			this.switchTo(esm);
		} else if (this.specialButtons.isEditButtonEvent(e)) {
			SpecialRef thisSpecial = this.specialList.selectedItem();
			EditSpecialRefMenu esm = new EditSpecialRefMenu(this.frame(), thisSpecial);
			this.switchTo(esm);
		} else if (this.specialButtons.isDeleteButtonEvent(e)) {
			this.specialList.removeSelectedItem();
		} else if (this.specialButtons.isUpButtonEvent(e)) {
			this.specialList.moveUp();
		} else if (this.specialButtons.isDownButtonEvent(e)) {
			this.specialList.moveDown();

		} else if (this.optionButtons.isAddButtonEvent(e)) {
			UnitOption newOption = new UnitOption();
			this.optionList.addItem(newOption);
			EditOptionMenu eom = new EditOptionMenu(this.frame(), this.unit, newOption);
			this.switchTo(eom);
		} else if (this.optionButtons.isEditButtonEvent(e)) {
			UnitOption thisOption = this.optionList.selectedItem();
			EditOptionMenu eom = new EditOptionMenu(this.frame(), this.unit, thisOption);
			this.switchTo(eom);
		} else if (this.optionButtons.isDeleteButtonEvent(e)) {
			this.optionList.removeSelectedItem();
		} else if (this.optionButtons.isUpButtonEvent(e)) {
			this.optionList.moveUp();
		} else if (this.optionButtons.isDownButtonEvent(e)) {
			this.optionList.moveDown();
		} else if (e.getSource() == saveButton) {
			//save name
			String newName = this.nameField.getText();
			if(!("".equals(newName))) {
				this.unit.setName(newName);
			}
			//save type
			RUData.UNIT_TYPE type = this.typeMenu.selectedItem();
			this.unit.setType(type);
			//save sizes
			UnitSize[] sizes = new UnitSize[this.sizeList.length()];
			for (int i = 0; i < sizes.length; i++) {
				sizes[i] = this.sizeList.item(i);
			}
			this.unit.setSizes(sizes);
			//save specials
			SpecialRef[] specials = new SpecialRef[this.specialList.length()];
			for (int i = 0; i < specials.length; i++) {
				specials[i] = this.specialList.item(i);
			}
			this.unit.setSpecials(specials);
			//save options
			UnitOption[] options = new UnitOption[this.optionList.length()];
			for (int i = 0; i < options.length; i++) {
				options[i] = this.optionList.item(i);
			}
			this.unit.setOptions(options);
			this.frame().dispatchEvent(new WindowEvent(this.frame(), WindowEvent.WINDOW_CLOSING));

		}

	}


	public void panelShown() {}
	public void panelHidden() {saveSpecials();}
	public void windowClosing() {}

	public void saveSpecials() {
		//save specials
		SpecialRef[] specials = new SpecialRef[this.specialList.length()];
		for (int i = 0; i < specials.length; i++) {
			specials[i] = this.specialList.item(i);
		}
		this.unit.setSpecials(specials);
	}

	//return things
	public JFrame frame() {
		return this.frame;
	}
}