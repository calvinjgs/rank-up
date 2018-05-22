package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditorMenu extends JPanel implements ActionListener {

	private JFrame frame;
	private JButton EditForcesButton, EditSpecialsButton, EditArtefactsButton;

	public EditorMenu() {
		//create box layout
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel lbl = new JLabel(RUData.html("Editor", RUData.titleSize));
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		this.add(lbl);
		this.EditForcesButton = new JButton(RUData.html("Edit Forces", RUData.titleSize));
		this.add(this.EditForcesButton);
		this.EditForcesButton.addActionListener(this);
		this.EditSpecialsButton = new JButton(RUData.html("Edit Specials", RUData.titleSize));
		this.add(this.EditSpecialsButton);
		this.EditSpecialsButton.addActionListener(this);
		this.EditArtefactsButton = new JButton(RUData.html("Edit Artefacts", RUData.titleSize));
		this.add(this.EditArtefactsButton);
		this.EditArtefactsButton.addActionListener(this);
		this.setBorder(new EmptyBorder(10, 25, 10, 25));
		//set up frame
		this.frame = new JFrame(RUData.RUE_TITLE);
		this.frame.add(this);
		this.frame.pack();
		this.frame.setSize(450, 225);
		this.frame.setResizable(false);
		this.frame.setDefaultCloseOperation(this.frame.EXIT_ON_CLOSE);
	}


	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == EditForcesButton) {
			System.out.println("edit force button clicked");
			startForceMenu();
		} else if (e.getSource() == EditSpecialsButton) {
			;
		} else if (e.getSource() == EditArtefactsButton) {
			;
		}
	}

	//build and show force editor, hides this menu
	public void startForceMenu() {
		this.frame.setVisible(false);
		ForceMenu fm = new ForceMenu(this.frame());
		fm.frame().setVisible(true);

	}


	//return things
	public JFrame frame() {
		return this.frame;
	}


}