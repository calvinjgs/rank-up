package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.tools.*;

public class EditFormationMenu extends EditorPanel implements ActionListener {

	private Formation formation;
	private TextBox nameField, ptsField;
	private TextBoxArea descriptionArea;
	private JButton saveButton;


	public EditFormationMenu(JFrame parent, Formation f) {
		super(new BorderLayout(), parent);
		this.formation = f;
		this.frame().setTitle(RUData.RUE_TITLE + " - Formation");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		//name field
		this.nameField = new TextBox("Name");
		this.nameField.setText(this.formation.name());
		pan.add(this.nameField);
		this.nameField.addActionListener(this);
		//points field
		this.ptsField = new TextBox("pts");
		this.ptsField.setText(this.formation.pts());
		pan.add(this.ptsField);
		this.ptsField.addActionListener(this);

		this.add(pan, BorderLayout.NORTH);
		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right

		//description text area
		this.descriptionArea = new TextBoxArea("description");
		this.descriptionArea.setText(this.formation.description());
		this.descriptionArea.setColumns(40);
		this.add(this.descriptionArea, BorderLayout.CENTER);


		//save button
		JPanel savePanel = new JPanel();
		this.saveButton = new JButton(RUData.html("Save", RUData.titleSize*2));
		savePanel.add(this.saveButton);
		this.add(savePanel, BorderLayout.SOUTH);
		this.saveButton.addActionListener(this);


		this.frame().add(this);
		//this.frame().setSize(350, 400);
		this.frame().pack();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.saveButton) {
			//save name
			String newName = this.nameField.getText();
			if(!("".equals(newName))) {
				this.formation.setName(newName);
			}
			//save points
			String newPts = this.ptsField.getText();
			if(!("".equals(newPts))) {
				this.formation.setPts(newPts);
			}
			//save description
			String newDesc = this.descriptionArea.getText();
			if(!("".equals(newDesc))) {
				this.formation.setDescription(newDesc);
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