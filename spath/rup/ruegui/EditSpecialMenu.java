package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.tools.*;

public class EditSpecialMenu extends EditorPanel implements ActionListener {

	private Special special;
	private TextBox nameField;
	private TextBoxArea descriptionArea;
	private JButton saveButton;


	public EditSpecialMenu(JFrame parent, Special s) {
		super(new BorderLayout(), parent);
		this.special = s;
		this.frame().setTitle(RUData.RUE_TITLE + " - Special");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		//name field
		this.nameField = new TextBox("Name");
		this.nameField.setText(this.special.name());
		pan.add(this.nameField);
		this.nameField.addActionListener(this);

		this.add(pan, BorderLayout.NORTH);
		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right

		//description text area
		this.descriptionArea = new TextBoxArea("description");
		this.descriptionArea.setText(this.special.description());
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
				this.special.setName(newName);
			}
			//save description
			String newDesc = this.descriptionArea.getText();
			if(!("".equals(newDesc))) {
				this.special.setDescription(newDesc);
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