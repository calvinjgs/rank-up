package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
import rup.ruesrc.*;
import rup.tools.*;

public class EditPackageMenu extends EditorPanel implements ActionListener {

	private String oldName;
	private RUPackage rupackage;

	private TextBox nameField;

	private ItemList<Force> forceList;
	private ItemList<Special> specialList;
	private ItemList<Artifact> artifactList;

	private ListEditButtons flButtons, slButtons, alButtons;

	private JButton saveButton, dependenciesButton;
	String[] dependencies;

	public EditPackageMenu(JFrame parent, RUPackage rup) {
		super(new BorderLayout(), parent);
		this.rupackage = rup;
		this.dependencies = new String[0];
		this.oldName = this.rupackage.name();
		this.frame().setTitle(RUData.RUE_TITLE + " - Package");

		//set RUData working package for child menus to use.
		RUData.WORKINGPACKAGE = this.rupackage;

		//build edit package name field
		JPanel npan = new JPanel();
		//npan.setLayout(new BoxLayout(npan, BoxLayout.X_AXIS));
		this.nameField = new TextBox("package name:");
		npan.add(this.nameField);

		this.add(npan, BorderLayout.NORTH);
		this.nameField.setText(this.rupackage.name());
		this.nameField.addActionListener(this);

		JPanel listspan = new JPanel();
		listspan.setLayout(new GridLayout(0, 3, 10, 10));
		//build force list panel
		JPanel fpan = new JPanel();
		fpan.setLayout(new BoxLayout(fpan, BoxLayout.Y_AXIS));
		this.forceList = new ItemList("Forces:", this.rupackage.forces());
		fpan.add(this.forceList);
		this.flButtons = new ListEditButtons();
		this.flButtons.addActionListener(this);
		fpan.add(this.flButtons);
		listspan.add(fpan);
		//build specials list panel
		JPanel span = new JPanel();
		span.setLayout(new BoxLayout(span, BoxLayout.Y_AXIS));
		this.specialList = new ItemList("Specials:", this.rupackage.specials());
		span.add(this.specialList);
		this.slButtons = new ListEditButtons();
		this.slButtons.addActionListener(this);
		span.add(this.slButtons);
		listspan.add(span);
		//build artefacts list panel
		JPanel apan = new JPanel();
		apan.setLayout(new BoxLayout(apan, BoxLayout.Y_AXIS));
		this.artifactList = new ItemList("Artifacts:", this.rupackage.artifacts());
		apan.add(this.artifactList);
		this.alButtons = new ListEditButtons();
		this.alButtons.addActionListener(this);
		apan.add(this.alButtons);
		listspan.add(apan);

		listspan.setBorder(new EmptyBorder(25, 1, 25, 1));
		this.add(listspan, BorderLayout.CENTER);

		//build save button
		JPanel savePanel = new JPanel();
		this.saveButton = new JButton(RUData.html("Save", RUData.titleSize*2));
		savePanel.add(this.saveButton);
		this.add(savePanel, BorderLayout.SOUTH);
		this.saveButton.addActionListener(this);

		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right
		this.frame().add(this);
		this.frame().setSize(450, 500);
		this.frame().pack();

	}

	public void actionPerformed(ActionEvent e) {
		if (this.flButtons.isAddButtonEvent(e)) {
			addForce();
		} else if (this.flButtons.isEditButtonEvent(e)) {
				editForce();
		} else if (this.flButtons.isDeleteButtonEvent(e)) {
			this.forceList.removeSelectedItem();
		} else if (this.flButtons.isUpButtonEvent(e)) {
			this.forceList.moveUp();
		} else if (this.flButtons.isDownButtonEvent(e)) {
			this.forceList.moveDown();
		} else if (this.slButtons.isAddButtonEvent(e)) {
			addSpecial();
		} else if (this.slButtons.isEditButtonEvent(e)) {
			editSpecial();
		} else if (this.slButtons.isDeleteButtonEvent(e)) {
			this.specialList.removeSelectedItem();
			saveSpecials();
		} else if (this.slButtons.isUpButtonEvent(e)) {
			this.specialList.moveUp();
			saveSpecials();
		} else if (this.slButtons.isDownButtonEvent(e)) {
			this.specialList.moveDown();
			saveSpecials();
		} else if (this.alButtons.isAddButtonEvent(e)) {
			addArtifact();
		} else if (this.alButtons.isEditButtonEvent(e)) {
			editArtifact();
		} else if (this.alButtons.isDeleteButtonEvent(e)) {
			this.artifactList.removeSelectedItem();
		} else if (this.alButtons.isUpButtonEvent(e)) {
			this.artifactList.moveUp();
		} else if (this.alButtons.isDownButtonEvent(e)) {
			this.artifactList.moveDown();
		} else if (e.getSource() == saveButton) {
			System.out.println("save button clicked");
			//save name
			String newName = this.nameField.getText();
			if(!("".equals(newName))) {
				this.rupackage.setName(newName);
				System.out.println("new name is: " + this.rupackage.name());
			}
			//save dependencies
			this.rupackage.setDependencies(dependencies);
			//save forces
			Force[] forces = new Force[this.forceList.length()];
			for (int i = 0; i < forces.length; i++) {
				forces[i] = this.forceList.item(i);
			}
			this.rupackage.setForces(forces);
			//save specials
			Special[] specials = new Special[this.specialList.length()];
			for (int i = 0; i < specials.length; i++) {
				specials[i] = this.specialList.item(i);
			}
			this.rupackage.setSpecials(specials);
			//save artifacts
			Artifact[] artifacts = new Artifact[this.artifactList.length()];
			for (int i = 0; i < artifacts.length; i++) {
				artifacts[i] = this.artifactList.item(i);
			}
			this.rupackage.setArtifacts(artifacts);

			Compile.delete(Compile.getRUPackageFileName(this.oldName));
			SavePackage.save(this.rupackage);
			this.frame().dispatchEvent(new WindowEvent(this.frame(), WindowEvent.WINDOW_CLOSING));
		}

	}


	public void addForce() {
		Force newForce = new Force();
		newForce.setName("new force");
		this.forceList.addItem(newForce);
		EditForceMenu efm = new EditForceMenu(this.frame(), newForce);
		this.switchTo(efm);
	}

	public void editForce() {
		if (this.forceList.selectedItem() != null) {
			Force thisForce = this.forceList.selectedItem();
			EditForceMenu efm = new EditForceMenu(this.frame(), thisForce);
			this.switchTo(efm);
		}
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
		//save specials
		Special[] specials = new Special[this.specialList.length()];
		for (int i = 0; i < specials.length; i++) {
			specials[i] = this.specialList.item(i);
		}
		this.rupackage.setSpecials(specials);
	}

	public void editSpecial() {
		if (this.specialList.selectedItem() != null) {
			Special thisSpecial = this.specialList.selectedItem();
			EditSpecialMenu esm = new EditSpecialMenu(this.frame(), thisSpecial);
			this.switchTo(esm);
		}
	}

	public void addArtifact() {
		Artifact newArtifact = new Artifact();
		newArtifact.setName("new artifact");
		this.artifactList.addItem(newArtifact);
		EditArtifactMenu eam = new EditArtifactMenu(this.frame(), newArtifact);
		this.switchTo(eam);
	}

	public void editArtifact() {
		if (this.artifactList.selectedItem() != null) {
			Artifact thisArtifact = this.artifactList.selectedItem();
			EditArtifactMenu eam = new EditArtifactMenu(this.frame(), thisArtifact);
			this.switchTo(eam);
		}
	}


	public void panelShown() {
		//refresh specials because other places can edit it.
		this.specialList.setItems(this.rupackage.specials());
	}
	public void panelHidden() {System.out.println("EditPackageMenu hidden");}
	public void windowClosing() {}

	//return things
	public JFrame frame() {
		return this.frame;
	}
}