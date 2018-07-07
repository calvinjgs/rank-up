package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditUnitUpdateListMenu extends EditorPanel implements ActionListener {

	UnitUpdate[] unitUpdates;
	private ItemList<UnitUpdate> unitUpdateList;
	private ListEditButtons uuButtons;
	private JButton saveButton;


	public EditUnitUpdateListMenu(JFrame parent, UnitUpdate[] uus) {
		super(new BorderLayout(), parent);
		this.unitUpdates = uus;
		this.frame().setTitle(RUData.RUE_TITLE + " - Unit Updates");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

		//unit update list
		JPanel uupan = new JPanel();
		uupan.setLayout(new BoxLayout(uupan, BoxLayout.Y_AXIS));
		this.unitUpdateList = new ItemList<UnitUpdate>("Unit updates", unitUpdates);
		uupan.add(this.unitUpdateList);
		this.uuButtons = new ListEditButtons();
		this.uuButtons.addActionListener(this);
		uupan.add(this.uuButtons);
		pan.add(uupan);
		this.add(pan, BorderLayout.CENTER);


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
		if (this.uuButtons.isAddButtonEvent(e)) {
			UnitUpdate newUnitUpdate = new UnitUpdate();
			newUnitUpdate.setUnitName("new unit update");
			this.unitUpdateList.addItem(newUnitUpdate);
			EditUnitUpdateMenu euum = new EditUnitUpdateMenu(this.frame(), newUnitUpdate);
			this.switchTo(euum);
		} else if (this.uuButtons.isEditButtonEvent(e)) {
			if (this.unitUpdateList.selectedItem() != null) {
				UnitUpdate thisUnitUpdate = this.unitUpdateList.selectedItem();
				EditUnitUpdateMenu euum = new EditUnitUpdateMenu(this.frame(), thisUnitUpdate);
				this.switchTo(euum);
			}
		} else if (this.uuButtons.isDeleteButtonEvent(e)) {
			this.unitUpdateList.removeSelectedItem();
		} else if (this.uuButtons.isUpButtonEvent(e)) {
			this.unitUpdateList.moveUp();
		} else if (this.uuButtons.isDownButtonEvent(e)) {
			this.unitUpdateList.moveDown();
		} else if (e.getSource() == this.saveButton) {
			//save unit updates
			UnitUpdate[] unitUpdates = new UnitUpdate[this.unitUpdateList.length()];
			for (int i = 0; i < unitUpdates.length; i++) {
				unitUpdates[i] = this.unitUpdateList.item(i);
			}
			this.unitUpdates = unitUpdates;
			RUData.WORKINGPACKAGE.setUnitUpdates(this.unitUpdates);
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