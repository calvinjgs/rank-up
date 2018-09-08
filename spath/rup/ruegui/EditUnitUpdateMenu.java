package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditUnitUpdateMenu extends EditorPanel implements ActionListener {

	private UnitUpdate unitUpdate;
	private Unit unitWithUpdates;

	private DropDownMenu<String> packageNameMenu;
	private DropDownMenu<Force> forceNameMenu;
	private DropDownMenu<Unit> unitNameMenu;
	private JButton editStatsButton;
	private JButton saveButton;


	public EditUnitUpdateMenu(JFrame parent, UnitUpdate uu) {
		super(new BorderLayout(), parent);
		this.unitUpdate = uu;
		this.frame().setTitle(RUData.RUE_TITLE + " - Unit Update");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

		//name drop down menus
		String[] pkgnames = ConfigFile.sortedRUPackageNames(Compile.getRUPackageNames());
		this.packageNameMenu = new DropDownMenu("Package:", pkgnames);
		pan.add(this.packageNameMenu);
		this.packageNameMenu.addActionListener(this);


		this.forceNameMenu = new DropDownMenu("Force:", new Force[0]);
		pan.add(this.forceNameMenu);
		this.forceNameMenu.addActionListener(this);

		this.unitNameMenu = new DropDownMenu("Unit:", new Unit[0]);
		pan.add(this.unitNameMenu);
		this.unitNameMenu.addActionListener(this);

		this.packageNameMenu.setSelectedItem(this.packageNameMenu.itemAt(0));

		//stats button
		this.editStatsButton = new JButton(RUData.html("Edit unit data", RUData.titleSize));
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
		//this.frame().setSize(450, 500);
		this.frame().pack();
	}

	public void actionPerformed(ActionEvent e) {
		if (this.packageNameMenu.caused(e)) {
			updateForceList();
			this.frame().pack();
		} else if (this.forceNameMenu.caused(e)) {
			updateUnitList();
			this.frame().pack();
		} else if (e.getSource() == this.editStatsButton) {
			this.unitWithUpdates = unitNameMenu.selectedItem().clone();
			this.unitWithUpdates.applyUpdate(this.unitUpdate);
			EditUnitMenu eum = new EditUnitMenu(this.frame(), this.unitWithUpdates);
			this.switchTo(eum);
		} else if (e.getSource() == this.saveButton) {
			//save names
			String newName = this.packageNameMenu.selectedItem();
			this.unitUpdate.setPkgName(newName);
			newName = this.forceNameMenu.selectedItem().name();
			this.unitUpdate.setForceName(newName);
			newName = this.unitNameMenu.selectedItem().name();
			this.unitUpdate.setUnitName(newName);
			//save units changes
			Unit thisUnit = this.unitNameMenu.selectedItem();
			//name
			if (!thisUnit.name().equals(this.unitWithUpdates.name())) {
				this.unitUpdate.setNewUnitName(this.unitWithUpdates.name());
			}
			//type
			if (!(thisUnit.type() == (this.unitWithUpdates.type()))) {
				this.unitUpdate.setType(this.unitWithUpdates.type());
			}
			//sizes
			//removes
			DynamicArray<UnitSize> remSizes = new DynamicArray(new UnitSize[0]);
			for (int tus = 0; tus < thisUnit.sizes().length; tus++) {
				boolean removed = true;
				for (int uwus = 0; uwus < this.unitWithUpdates.sizes().length; uwus++) {
					if (thisUnit.sizes(tus).name() == this.unitWithUpdates.sizes(uwus).name()) {
						removed = false;
						break;
					}
				}
				if (removed) remSizes.add(thisUnit.sizes(tus));
			}
			remSizes.trim();
			this.unitUpdate.setRemoveSizes(remSizes.storage());
			//edits
			DynamicArray<UnitSize> editSizes = new DynamicArray(new UnitSize[0]);
			for (int tus = 0; tus < thisUnit.sizes().length; tus++) {
				for (int uwus = 0; uwus < this.unitWithUpdates.sizes().length; uwus++) {
					if (thisUnit.sizes(tus).name() == this.unitWithUpdates.sizes(uwus).name()) {
						UnitSize thisSize = thisUnit.sizes(tus);
						UnitSize sizeWithUpdates = this.unitWithUpdates.sizes(uwus);
						boolean samesp = thisSize.sp().equals(sizeWithUpdates.sp());
						boolean sameme = thisSize.me().equals(sizeWithUpdates.me());
						boolean samera = thisSize.ra().equals(sizeWithUpdates.ra());
						boolean samede = thisSize.de().equals(sizeWithUpdates.de());
						boolean sameatt = thisSize.att().equals(sizeWithUpdates.att());
						boolean sameneW = thisSize.neW().equals(sizeWithUpdates.neW());
						boolean sameneR = thisSize.neR().equals(sizeWithUpdates.neR());
						boolean samepts = thisSize.pts().equals(sizeWithUpdates.pts());
						if (!(samesp && sameme && samera && samede && sameatt && sameneW && sameneR && samepts)) {
							//carry out edit
							UnitSize theUpdate = new UnitSize();
							theUpdate.setName(thisSize.name());
							if (!samesp) theUpdate.setSp(sizeWithUpdates.sp());
							if (!sameme) theUpdate.setMe(sizeWithUpdates.me());
							if (!samera) theUpdate.setRa(sizeWithUpdates.ra());
							if (!samede) theUpdate.setDe(sizeWithUpdates.de());
							if (!sameatt) theUpdate.setAtt(sizeWithUpdates.att());
							if (!sameneW) theUpdate.setNeW(sizeWithUpdates.neW());
							if (!sameneR) theUpdate.setNeR(sizeWithUpdates.neR());
							if (!samepts) theUpdate.setPts(sizeWithUpdates.pts());
							editSizes.add(theUpdate);
						}
					}
				}
			}
			editSizes.trim();
			this.unitUpdate.setEditSizes(editSizes.storage());

			//adds
			DynamicArray<UnitSize> addSizes = new DynamicArray(new UnitSize[0]);
			for (int uwus = 0; uwus < this.unitWithUpdates.sizes().length; uwus++) {
				boolean added = true;
				for (int tus = 0; tus < thisUnit.sizes().length; tus++) {
					if (thisUnit.sizes(tus).name() == this.unitWithUpdates.sizes(uwus).name()) {
						added = false;
						break;
					}
				}
				if (added) addSizes.add(this.unitWithUpdates.sizes(uwus));
			}
			addSizes.trim();
			this.unitUpdate.setAddSizes(addSizes.storage());

			//specials
			//removes
			DynamicArray<SpecialRef> remSpecials = new DynamicArray(new SpecialRef[0]);
			for (int tus = 0; tus < thisUnit.specials().length; tus++) {
				boolean removed = true;
				for (int uwus = 0; uwus < this.unitWithUpdates.specials().length; uwus++) {
					if (thisUnit.specials(tus).specName().equals(this.unitWithUpdates.specials(uwus).specName())) {
						removed = false;
						break;
					}
				}
				if (removed) remSpecials.add(thisUnit.specials(tus));
			}
			remSpecials.trim();
			this.unitUpdate.setRemoveSpecials(remSpecials.storage());

			//adds
			DynamicArray<SpecialRef> addSpecials = new DynamicArray(new SpecialRef[0]);
			for (int uwus = 0; uwus < this.unitWithUpdates.specials().length; uwus++) {
				boolean added = true;
				for (int tus = 0; tus < thisUnit.specials().length; tus++) {
					if (thisUnit.specials(tus).specName().equals(this.unitWithUpdates.specials(uwus).specName())) {
						added = false;
						break;
					}
				}
				if (added) addSpecials.add(this.unitWithUpdates.specials(uwus));
			}
			addSpecials.trim();
			this.unitUpdate.setAddSpecials(addSpecials.storage());


			//options
			//removes
			DynamicArray<UnitOption> remOptions = new DynamicArray(new UnitOption[0]);
			for (int tus = 0; tus < thisUnit.options().length; tus++) {
				boolean removed = true;
				for (int uwus = 0; uwus < this.unitWithUpdates.options().length; uwus++) {
					if (thisUnit.options(tus).toString().equals(this.unitWithUpdates.options(uwus).toString())) {
						removed = false;
						break;
					}
				}
				if (removed) remOptions.add(thisUnit.options(tus));
			}
			remOptions.trim();
			this.unitUpdate.setRemoveOptions(remOptions.storage());


			//edits TODO

			//adds
			DynamicArray<UnitOption> addOptions = new DynamicArray(new UnitOption[0]);
			for (int uwus = 0; uwus < this.unitWithUpdates.options().length; uwus++) {
				boolean added = true;
				for (int tus = 0; tus < thisUnit.options().length; tus++) {
					if (thisUnit.options(tus).toString().equals(this.unitWithUpdates.options(uwus).toString())) {
						added = false;
						break;
					}
				}
				if (added) addOptions.add(unitWithUpdates.options(uwus));
			}
			addOptions.trim();
			this.unitUpdate.setAddOptions(addOptions.storage());




			this.frame().dispatchEvent(new WindowEvent(this.frame(), WindowEvent.WINDOW_CLOSING));
		}
	}

	public void updateForceList() {
		if (this.packageNameMenu.selectedItem() == null) return;
		String pkgN = this.packageNameMenu.selectedItem();
		String pkgFN = Compile.getRUPackageFileName(pkgN);
		RUPackage pkg = Compile.compileRUPackage(pkgFN);
		this.forceNameMenu.setItems(pkg.forces());
		updateUnitList();
	}

	public void updateUnitList() {
		if (this.forceNameMenu.selectedItem() == null) return;
		Force force = this.forceNameMenu.selectedItem();
		this.unitNameMenu.setItems(force.units());
	}


	public void panelShown() {this.frame().pack();}
	public void panelHidden() {}
	public void windowClosing() {}

	//return things
	public JFrame frame() {
		return this.frame;
	}
}