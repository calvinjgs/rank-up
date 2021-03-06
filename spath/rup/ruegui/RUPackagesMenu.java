package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.ruesrc.*;
import rup.tools.*;

public class RUPackagesMenu extends EditorPanel implements ActionListener {

	ItemList<String> list;
	ListEditButtons listButtons;
	public RUPackagesMenu() {
		super(new BorderLayout(), null);
		this.frame().setTitle(RUData.RUE_TITLE + " - Packages");

		//sort rupackages from config file
		ConfigFile.read();
		String[] packagenames = ConfigFile.sortedRUPackageNames(Compile.getRUPackageNames());


		//list package file names
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		this.list = new ItemList("Packages", packagenames);
		pan.add(this.list);
		this.listButtons = new ListEditButtons();
		this.listButtons.addActionListener(this);
		pan.add(this.listButtons);

		this.add(pan, BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right

		//build frame
		//this.frame().setSize(350, 300);
		if (ConfigFile.RUEditorX < 0) ConfigFile.RUEditorX = 0;
		if (ConfigFile.RUEditorY < 0) ConfigFile.RUEditorY = 0;
		this.frame().setLocation(ConfigFile.RUEditorX, ConfigFile.RUEditorY);
		this.frame().pack();
		this.frame.setDefaultCloseOperation(this.frame.EXIT_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e) {
		if (listButtons.isEditButtonEvent(e)) {
			if (this.list.selectedItem() != null) {
				String filename = Compile.getRUPackageFileName(this.list.selectedItem());
				RUPackage pkg = Compile.compileRUPackage(filename);
				EditPackageMenu epm = new EditPackageMenu(this.frame(), pkg);
				this.switchTo(epm);
			}

		} else if (listButtons.isAddButtonEvent(e)) {
			RUPackage newpackage = new RUPackage();

			SavePackage.save(newpackage);
			this.list.addItem(newpackage.name());

		} else if (listButtons.isDeleteButtonEvent(e)) {
			Compile.delete(Compile.getRUPackageFileName(this.list.selectedItem()));
			this.list.removeSelectedItem();

		} else if (listButtons.isUpButtonEvent(e)) {
			this.list.moveUp();
		} else if (listButtons.isDownButtonEvent(e)) {
			this.list.moveDown();
		}
	}


	public void panelShown() {
		//update package order
		String pkgnames[] = new String[this.list.length()];
		for (int i = 0; i < pkgnames.length; i++) {
			pkgnames[i] = this.list.item(i);
		}
		ConfigFile.rupackageList = pkgnames;
		this.list.clear();
		String[] packagenames = ConfigFile.sortedRUPackageNames(Compile.getRUPackageNames());
		this.list.addItems(packagenames);

	}

	public void panelHidden() {
	}

	public void windowClosing() {
		ConfigFile.RUEditorX = this.frame().getLocation().x;
		ConfigFile.RUEditorY = this.frame().getLocation().y;
		String pkgnames[] = new String[this.list.length()];
		for (int i = 0; i < pkgnames.length; i++) {
			System.out.println(this.list.item(i));
			pkgnames[i] = this.list.item(i);
		}
		ConfigFile.rupackageList = pkgnames;
		ConfigFile.write();
		System.exit(0);
	}

}