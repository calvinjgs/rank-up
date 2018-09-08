package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditSpecialUpdateListMenu extends EditorPanel implements ActionListener {

	SpecialUpdate[] specialUpdates;
	private ItemList<SpecialUpdate> specialUpdateList;
	private ListEditButtons uuButtons;
	private JButton saveButton;


	public EditSpecialUpdateListMenu(JFrame parent, SpecialUpdate[] sups) {
		super(new BorderLayout(), parent);
		this.specialUpdates = sups;
		this.frame().setTitle(RUData.RUE_TITLE + " - Special Updates");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

		//special update list
		JPanel uupan = new JPanel();
		uupan.setLayout(new BoxLayout(uupan, BoxLayout.Y_AXIS));
		this.specialUpdateList = new ItemList<SpecialUpdate>("Special updates", specialUpdates);
		uupan.add(this.specialUpdateList);
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
			SpecialUpdate newSpecialUpdate = new SpecialUpdate();
			newSpecialUpdate.setName("new special update");
			this.specialUpdateList.addItem(newSpecialUpdate);
			EditSpecialUpdateMenu euum = new EditSpecialUpdateMenu(this.frame(), newSpecialUpdate);
			this.switchTo(euum);
		} else if (this.uuButtons.isEditButtonEvent(e)) {
			if (this.specialUpdateList.selectedItem() != null) {
				SpecialUpdate thisSpecialUpdate = this.specialUpdateList.selectedItem();
				EditSpecialUpdateMenu euum = new EditSpecialUpdateMenu(this.frame(), thisSpecialUpdate);
				this.switchTo(euum);
			}
		} else if (this.uuButtons.isDeleteButtonEvent(e)) {
			this.specialUpdateList.removeSelectedItem();
		} else if (this.uuButtons.isUpButtonEvent(e)) {
			this.specialUpdateList.moveUp();
		} else if (this.uuButtons.isDownButtonEvent(e)) {
			this.specialUpdateList.moveDown();
		} else if (e.getSource() == this.saveButton) {
			//save special updates
			SpecialUpdate[] specialUpdates = new SpecialUpdate[this.specialUpdateList.length()];
			for (int i = 0; i < specialUpdates.length; i++) {
				specialUpdates[i] = this.specialUpdateList.item(i);
			}
			this.specialUpdates = specialUpdates;
			RUData.WORKINGPACKAGE.setSpecialUpdates(this.specialUpdates);
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