package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;

public class ListEditButtons extends JPanel {


	private JButton addButton, editButton, deleteButton,
					upButton, downButton;

	public ListEditButtons() {
		super();

		this.setLayout(new GridLayout(0, 2, 5, 5));

		this.editButton = new JButton(RUData.html("Edit", RUData.titleSize));
		this.add(this.editButton);

		this.addButton = new JButton(RUData.html("Add", RUData.titleSize));
		this.add(this.addButton);

		JPanel udpan = new JPanel();
		udpan.setLayout(new GridLayout(0, 2, 3, 3));

		this.upButton = new JButton(RUData.html("\u2B06", RUData.titleSize));
		udpan.add(this.upButton);

		this.downButton = new JButton(RUData.html("\u2B07", RUData.titleSize));
		udpan.add(this.downButton);

		this.add(udpan);

		this.deleteButton = new JButton(RUData.html("Delete", RUData.titleSize));
		this.add(this.deleteButton);



	}

	public void addActionListener(ActionListener ep) {
		this.addButton.addActionListener(ep);
		this.editButton.addActionListener(ep);
		this.deleteButton.addActionListener(ep);
		this.upButton.addActionListener(ep);
		this.downButton.addActionListener(ep);
	}


	public boolean isAddButtonEvent(ActionEvent e) {
		return e.getSource() == this.addButton;
	}
	public boolean isEditButtonEvent(ActionEvent e) {
		return e.getSource() == this.editButton;
	}
	public boolean isDeleteButtonEvent(ActionEvent e) {
		return e.getSource() == this.deleteButton;
	}
	public boolean isUpButtonEvent(ActionEvent e) {
		return e.getSource() == this.upButton;
	}
	public boolean isDownButtonEvent(ActionEvent e) {
		return e.getSource() == this.downButton;
	}

	public void setEnabled(boolean b) {
		this.addButton.setEnabled(b);
		this.editButton.setEnabled(b);
		this.deleteButton.setEnabled(b);
		this.upButton.setEnabled(b);
		this.downButton.setEnabled(b);
	}

	//return things
	public JButton addButton() {
		return this.addButton;
	}
	public JButton editButton() {
		return this.editButton;
	}
	public JButton deleteButton() {
		return this.deleteButton;
	}
	public JButton upButton() {
		return this.upButton;
	}
	public JButton downButton() {
		return this.downButton;
	}

}