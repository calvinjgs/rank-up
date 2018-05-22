package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.ruesrc.*;
import rup.tools.*;

public class EditOptionMenu extends EditorPanel implements ActionListener {

	private UnitOption option;
	private Unit unit;

	private ItemList<Flavour> flavourList;
	private ListEditButtons flavourButtons;

	private JButton saveButton;


	public EditOptionMenu(JFrame parent, Unit u, UnitOption o) {
		super(new BorderLayout(), parent);
		this.unit = u;
		this.option = o;
		this.frame().setTitle(RUData.RUE_TITLE + " - Option");

		//build force list panel
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		this.flavourList = new ItemList("Mutually exclusive options:", this.option.flavours());
		pan.add(this.flavourList);
		this.flavourButtons = new ListEditButtons();
		this.flavourButtons.addActionListener(this);
		pan.add(this.flavourButtons);

		pan.setBorder(new EmptyBorder(25, 1, 25, 1));
		this.add(pan, BorderLayout.CENTER);

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
		if (this.flavourButtons.isAddButtonEvent(e)) {
			Flavour newFlavour = new Flavour("new option");
			this.flavourList.addItem(newFlavour);
			EditFlavourMenu efm = new EditFlavourMenu(this.frame(), this.unit, newFlavour);
			this.switchTo(efm);
		} else if (this.flavourButtons.isEditButtonEvent(e)) {
			Flavour thisFlavour = this.flavourList.selectedItem();
			EditFlavourMenu efm = new EditFlavourMenu(this.frame(), this.unit, thisFlavour);
			this.switchTo(efm);
		} else if (this.flavourButtons.isDeleteButtonEvent(e)) {
			this.flavourList.removeSelectedItem();
		} else if (this.flavourButtons.isUpButtonEvent(e)) {
			this.flavourList.moveUp();
		} else if (this.flavourButtons.isDownButtonEvent(e)) {
			this.flavourList.moveDown();

		} else if (e.getSource() == saveButton) {
			//save sizes
			Flavour[] flaves = new Flavour[this.flavourList.length()];
			for (int i = 0; i < flaves.length; i++) {
				flaves[i] = this.flavourList.item(i);
			}
			this.option.setFlavours(flaves);
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