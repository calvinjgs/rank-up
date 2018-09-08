package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditSpecialUpdateMenu extends EditorPanel implements ActionListener {

	private SpecialUpdate specialUpdate;
	private Special specialWithUpdates;

	private DropDownMenu<String> packageNameMenu;
	private DropDownMenu<Force> forceNameMenu;
	private DropDownMenu<Special> specialNameMenu;
	private JButton editStatsButton;
	private JButton saveButton;


	public EditSpecialUpdateMenu(JFrame parent, SpecialUpdate uu) {
		super(new BorderLayout(), parent);
		this.specialUpdate = uu;
		this.frame().setTitle(RUData.RUE_TITLE + " - Special Update");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

		//name drop down menus
		String[] pkgnames = ConfigFile.sortedRUPackageNames(Compile.getRUPackageNames());
		this.packageNameMenu = new DropDownMenu("Package:", pkgnames);
		pan.add(this.packageNameMenu);
		this.packageNameMenu.addActionListener(this);

		this.specialNameMenu = new DropDownMenu("Special:", new Special[0]);
		pan.add(this.specialNameMenu);
		this.specialNameMenu.addActionListener(this);

		this.packageNameMenu.setSelectedItem(this.packageNameMenu.itemAt(0));
		this.packageNameMenu.setSelectedItem(this.specialUpdate.pkgName());
		if (!(this.specialUpdate.objName() == null || this.specialUpdate.objName().equals("")))
			this.specialNameMenu.setSelectedItem(new Special(this.specialUpdate.objName()));//equality is by name only

		//stats button
		this.editStatsButton = new JButton(RUData.html("Edit special data", RUData.titleSize));
		pan.add(this.editStatsButton);
		this.editStatsButton.addActionListener(this);

		//save button
		JPanel savePanel = new JPanel();
		this.saveButton = new JButton(RUData.html("Save", RUData.titleSize*2));
		savePanel.add(this.saveButton);
		this.add(savePanel, BorderLayout.SOUTH);
		this.saveButton.addActionListener(this);

		this.add(pan, BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right


		this.frame().add(this);
		this.frame().pack();

	}

	public void actionPerformed(ActionEvent e) {
		if (this.packageNameMenu.caused(e)) {
			updateSpecialList();
			this.frame().pack();
		} else if (e.getSource() == this.editStatsButton) {
			this.specialWithUpdates = specialNameMenu.selectedItem().clone();
			this.specialWithUpdates.applyUpdate(this.specialUpdate);
			EditSpecialMenu eum = new EditSpecialMenu(this.frame(), this.specialWithUpdates);
			this.switchTo(eum);
		} else if (e.getSource() == this.saveButton) {
			Special thisSpecial = this.specialNameMenu.selectedItem();
			//if (!thisSpecial.name().equals(this.specialWithUpdates.name())) {

				String name = this.packageNameMenu.selectedItem();
				this.specialUpdate.setPkgName(name);
				name = this.specialNameMenu.selectedItem().name();
				this.specialUpdate.setObjName(name);
				System.out.println("special update name is now " + this.specialUpdate);


				String newName = this.specialWithUpdates.name();
				this.specialUpdate.setNewName(newName);
				String newDesc = this.specialWithUpdates.description();
				this.specialUpdate.setNewDescription(newDesc);
			//}


			this.frame().dispatchEvent(new WindowEvent(this.frame(), WindowEvent.WINDOW_CLOSING));
		}
	}

	public void updateSpecialList() {
		if (this.packageNameMenu.selectedItem() == null) return;
		String pkgN = this.packageNameMenu.selectedItem();
		String pkgFN = Compile.getRUPackageFileName(pkgN);
		RUPackage pkg = Compile.compileRUPackage(pkgFN);
		this.specialNameMenu.setItems(pkg.specials());
	}


	public void panelShown() {this.frame().pack();}
	public void panelHidden() {}
	public void windowClosing() {}

	//return things
	public JFrame frame() {
		return this.frame;
	}
}