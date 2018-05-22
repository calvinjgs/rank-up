package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class ForceMenu extends EditorPanel implements ActionListener {

	ItemList<Force> list;
	JButton editButton, addButton;

	public ForceMenu(JFrame parent) {
		super(new BorderLayout(), parent);
		this.frame().setTitle(RUData.RUE_TITLE + " - Forces");
		//compile forces
		Force[] forces = Compile.compileAll();
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		this.list = new ItemList<Force>("Forces", forces);
		pan.add(this.list);
		this.editButton = new JButton(RUData.html("Edit", RUData.titleSize));
		editButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		pan.add(this.editButton);
		this.editButton.addActionListener(this);
		this.add(pan, BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right
		this.frame().add(this);
		this.frame().setSize(450, 500);
		this.frame().pack();

	}
/*
	public void setVisible(boolean b) {
		this.frame.setVisible(b);
	}
*/
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == editButton) {
			System.out.println("edit button clicked");
			if (this.list.selectedItem() != null) {
				EditForceMenu efm = new EditForceMenu(this.frame(), this.list.selectedItem());
				this.switchTo(efm);
			}

		}
	}

	//return things
	public JFrame frame() {
		return this.frame;
	}
}