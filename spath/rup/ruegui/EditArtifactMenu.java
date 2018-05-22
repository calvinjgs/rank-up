package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.tools.*;

public class EditArtifactMenu extends EditorPanel implements ActionListener {

	private Artifact artifact;
	private TextBox nameField, ptsField;
	private TextBoxArea descriptionArea;
	private JButton saveButton;


	public EditArtifactMenu(JFrame parent, Artifact a) {
		super(new BorderLayout(), parent);
		this.artifact = a;
		this.frame().setTitle(RUData.RUE_TITLE + " - Artifact");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		//name field
		this.nameField = new TextBox("Name");
		this.nameField.setText(this.artifact.name());
		pan.add(this.nameField);
		this.nameField.addActionListener(this);
		//points field
		this.ptsField = new TextBox("pts");
		this.ptsField.setText(this.artifact.pts());
		pan.add(this.ptsField);
		this.ptsField.addActionListener(this);

		this.add(pan, BorderLayout.NORTH);
		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right

		//description text area
		this.descriptionArea = new TextBoxArea("description");
		this.descriptionArea.setText(this.artifact.description());
		this.descriptionArea.setColumns(40);
		this.add(this.descriptionArea, BorderLayout.CENTER);


		//save button
		JPanel savePanel = new JPanel();
		this.saveButton = new JButton(RUData.html("Save", RUData.titleSize*2));
		savePanel.add(this.saveButton);
		this.add(savePanel, BorderLayout.SOUTH);
		this.saveButton.addActionListener(this);


		this.frame().add(this);
		this.frame().pack();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.saveButton) {
			//save name
			String newName = this.nameField.getText();
			if(!("".equals(newName))) {
				this.artifact.setName(newName);
			}
			//save points
			String newPts = this.ptsField.getText();
			if(!("".equals(newPts))) {
				this.artifact.setPts(newPts);
			}
			//save description
			String newDesc = this.descriptionArea.getText();
			if(!("".equals(newDesc))) {
				this.artifact.setDescription(newDesc);
			}
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