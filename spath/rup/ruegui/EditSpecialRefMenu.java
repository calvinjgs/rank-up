package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.tools.*;

public class EditSpecialRefMenu extends EditorPanel implements ActionListener {

	private SpecialRef specialref;
	private DropDownMenu<String> packageMenu;
	private DropDownMenu<Special> specialMenu;
	private ItemList<Special> specialList;
	private JLabel specialListButtonsLabel;
	private ListEditButtons specialListButtons;
	private TextBox nField;
	private JButton saveButton, newSpecialButton;


	public EditSpecialRefMenu(JFrame parent, SpecialRef s) {
		super(new BorderLayout(), parent);
		this.specialref = s;
		this.frame().setTitle(RUData.RUE_TITLE + " - Special");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.X_AXIS));


		JPanel leftpan = new JPanel();
		leftpan.setLayout(new BoxLayout(leftpan, BoxLayout.Y_AXIS));

		//package menu
		String[] pkgnames = ConfigFile.sortedRUPackageNames(Compile.getRUPackageNames());
		this.packageMenu = new DropDownMenu("from Package:", pkgnames);
		leftpan.add(this.packageMenu);
		this.packageMenu.addActionListener(this);

		//special list
		this.specialList = new ItemList("select a special", new Special[0]);
		leftpan.add(this.specialList);

		//n field
		JPanel npan = new JPanel();
		npan.setLayout(new BoxLayout(npan, BoxLayout.Y_AXIS));
		this.nField = new TextBox("(n) value:");
		this.nField.setColumns(4);
		npan.setBorder(new EmptyBorder(10, 40, 10, 40));
		npan.add(this.nField);
		leftpan.add(npan);
		this.nField.setText(this.specialref.n());

		pan.add(leftpan);

		//specials edit buttons
		JPanel rightpan = new JPanel();
		rightpan.setLayout(new BoxLayout(rightpan, BoxLayout.Y_AXIS));
		//rightpan.setLayout(new FlowLayout());
		JLabel seditlbl= new JLabel(RUData.html("edit specials list"));
		this.specialListButtonsLabel = seditlbl;
		rightpan.add(seditlbl);
		seditlbl.setHorizontalAlignment(SwingConstants.CENTER);
		seditlbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		JPanel span = new JPanel();
		span.setLayout(new FlowLayout());
		this.specialListButtons = new ListEditButtons();
		this.specialListButtons.addActionListener(this);
		span.add(this.specialListButtons);
		rightpan.add(span);
		this.packageMenu.setSelectedItem(this.specialref.pkgName());
		this.specialList.setSelectedItem(new Special(this.specialref.specName()));
		pan.add(rightpan);


		this.packageMenu.setSelectedItem(this.packageMenu.itemAt(0));

		pan.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right
		this.add(pan, BorderLayout.CENTER);


		//save button
		JPanel savePanel = new JPanel();
		this.saveButton = new JButton(RUData.html("Save", RUData.titleSize*2));
		savePanel.add(this.saveButton);
		this.add(savePanel, BorderLayout.SOUTH);
		this.saveButton.addActionListener(this);


		this.frame().add(this);
		//this.frame().setSize(400, 400);
		this.frame().pack();
	}

	public void actionPerformed(ActionEvent e) {
		System.out.println("action performed");
		if (this.packageMenu.caused(e)) {
			System.out.println("package menu caused it");
			updateSpecialMenuList();
		} else if (this.specialListButtons.isAddButtonEvent(e)) {
			addSpecial();
		} else if (this.specialListButtons.isEditButtonEvent(e)) {
			editSpecial();
		} else if (this.specialListButtons.isDeleteButtonEvent(e)) {
			this.specialList.removeSelectedItem();
			saveSpecials();
		} else if (this.specialListButtons.isUpButtonEvent(e)) {
			this.specialList.moveUp();
			saveSpecials();
		} else if (this.specialListButtons.isDownButtonEvent(e)) {
			this.specialList.moveDown();
			saveSpecials();
		} else if (e.getSource() == this.saveButton) {
			//save n
			String newN = this.nField.getText();
			if(!("".equals(newN))) {
				this.specialref.setN(newN);
			}
			//save pkgName
			String newPN = this.packageMenu.selectedItem();
			this.specialref.setPkgName(newPN);
			//save specName
			Special newSpec = this.specialList.selectedItem();
			this.specialref.setSpecName(newSpec.name());
			this.frame().dispatchEvent(new WindowEvent(this.frame(), WindowEvent.WINDOW_CLOSING));

		}
	}

	public void updateSpecialMenuList() {
		System.out.println("updating special menu list");
		String pkgN = this.packageMenu.selectedItem();
		if (pkgN.equals(RUData.WORKINGPACKAGE.name())) {
			this.specialList.setItems(RUData.WORKINGPACKAGE.specials());
			this.specialListButtons.setEnabled(true);
			this.specialListButtonsLabel.setEnabled(true);
		} else {
			String pkgFN = Compile.getRUPackageFileName(pkgN);
			RUPackage pkg = Compile.compileRUPackage(pkgFN);
			this.specialList.setItems(pkg.specials());
			this.specialListButtons.setEnabled(false);
			this.specialListButtonsLabel.setEnabled(false);
		}
	}

	public void createNewSpecial() {
		Special newSpecial = new Special();
		this.packageMenu.setSelectedItem(RUData.WORKINGPACKAGE.name());
		//automatically calls update
		this.specialMenu.addItem(newSpecial);
		EditSpecialMenu esm = new EditSpecialMenu(this.frame(), newSpecial);
		this.switchTo(esm);
		saveSpecials();
	}

	public void addSpecial() {
		Special newSpecial = new Special();
		newSpecial.setName("new special");
		this.specialList.addItem(newSpecial);
		EditSpecialMenu esm = new EditSpecialMenu(this.frame(), newSpecial);
		this.switchTo(esm);
		saveSpecials();
	}

	public void saveSpecials() {
		Special[] specials = new Special[this.specialList.length()];
		for (int i = 0; i < specials.length; i++) {
			specials[i] = this.specialList.item(i);
		}
		RUData.WORKINGPACKAGE.setSpecials(specials);

	}

	public void editSpecial() {
		if (this.specialList.selectedItem() != null) {
			Special thisSpecial = this.specialList.selectedItem();
			EditSpecialMenu esm = new EditSpecialMenu(this.frame(), thisSpecial);
			this.switchTo(esm);
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