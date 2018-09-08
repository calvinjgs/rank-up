package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditArtifactUpdateListMenu extends EditorPanel implements ActionListener {

	ArtifactUpdate[] artifactUpdates;
	private ItemList<ArtifactUpdate> artifactUpdateList;
	private ListEditButtons uuButtons;
	private JButton saveButton;


	public EditArtifactUpdateListMenu(JFrame parent, ArtifactUpdate[] uus) {
		super(new BorderLayout(), parent);
		this.artifactUpdates = uus;
		this.frame().setTitle(RUData.RUE_TITLE + " - Artefact Updates");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

		//artifact update list
		JPanel uupan = new JPanel();
		uupan.setLayout(new BoxLayout(uupan, BoxLayout.Y_AXIS));
		this.artifactUpdateList = new ItemList<ArtifactUpdate>("Artefact updates", artifactUpdates);
		uupan.add(this.artifactUpdateList);
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
			ArtifactUpdate newArtifactUpdate = new ArtifactUpdate();
			newArtifactUpdate.setName("new artifact update");
			this.artifactUpdateList.addItem(newArtifactUpdate);
			EditArtifactUpdateMenu euum = new EditArtifactUpdateMenu(this.frame(), newArtifactUpdate);
			this.switchTo(euum);
		} else if (this.uuButtons.isEditButtonEvent(e)) {
			if (this.artifactUpdateList.selectedItem() != null) {
				ArtifactUpdate thisArtifactUpdate = this.artifactUpdateList.selectedItem();
				EditArtifactUpdateMenu euum = new EditArtifactUpdateMenu(this.frame(), thisArtifactUpdate);
				this.switchTo(euum);
			}
		} else if (this.uuButtons.isDeleteButtonEvent(e)) {
			this.artifactUpdateList.removeSelectedItem();
		} else if (this.uuButtons.isUpButtonEvent(e)) {
			this.artifactUpdateList.moveUp();
		} else if (this.uuButtons.isDownButtonEvent(e)) {
			this.artifactUpdateList.moveDown();
		} else if (e.getSource() == this.saveButton) {
			//save artifact updates
			ArtifactUpdate[] artifactUpdates = new ArtifactUpdate[this.artifactUpdateList.length()];
			for (int i = 0; i < artifactUpdates.length; i++) {
				artifactUpdates[i] = this.artifactUpdateList.item(i);
			}
			this.artifactUpdates = artifactUpdates;
			RUData.WORKINGPACKAGE.setArtifactUpdates(this.artifactUpdates);
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