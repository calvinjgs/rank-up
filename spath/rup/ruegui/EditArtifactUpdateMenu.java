package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditArtifactUpdateMenu extends EditorPanel implements ActionListener {

	private ArtifactUpdate artifactUpdate;
	private Artifact artifactWithUpdates;

	private DropDownMenu<String> packageNameMenu;
	private DropDownMenu<Artifact> artifactNameMenu;
	private JButton editStatsButton;
	private JButton saveButton;


	public EditArtifactUpdateMenu(JFrame parent, ArtifactUpdate uu) {
		super(new BorderLayout(), parent);
		this.artifactUpdate = uu;
		this.frame().setTitle(RUData.RUE_TITLE + " - Artifact Update");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));

		//name drop down menus
		String[] pkgnames = ConfigFile.sortedRUPackageNames(Compile.getRUPackageNames());
		this.packageNameMenu = new DropDownMenu("Package:", pkgnames);
		pan.add(this.packageNameMenu);
		this.packageNameMenu.addActionListener(this);

		this.artifactNameMenu = new DropDownMenu("Artifact:", new Artifact[0]);
		pan.add(this.artifactNameMenu);
		this.artifactNameMenu.addActionListener(this);

		this.packageNameMenu.setSelectedItem(this.packageNameMenu.itemAt(0));


		//stats button
		this.editStatsButton = new JButton(RUData.html("Edit artifact data", RUData.titleSize));
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
			updateArtifactList();
			this.frame().pack();
		} else if (e.getSource() == this.editStatsButton) {
			this.artifactWithUpdates = artifactNameMenu.selectedItem().clone();
			this.artifactWithUpdates.applyUpdate(this.artifactUpdate);
			EditArtifactMenu eum = new EditArtifactMenu(this.frame(), this.artifactWithUpdates);
			this.switchTo(eum);
		} else if (e.getSource() == this.saveButton) {
			Artifact thisArtifact = this.artifactNameMenu.selectedItem();
			//if (!thisArtifact.name().equals(this.artifactWithUpdates.name())) {

				String name = this.packageNameMenu.selectedItem();
				this.artifactUpdate.setPkgName(name);
				name = this.artifactNameMenu.selectedItem().name();
				this.artifactUpdate.setObjName(name);

				String newName = this.artifactWithUpdates.name();
				this.artifactUpdate.setNewName(newName);
				String newDesc = this.artifactWithUpdates.description();
				this.artifactUpdate.setNewDescription(newDesc);
				String newPts = this.artifactWithUpdates.pts();
				this.artifactUpdate.setNewPts(newPts);
			//}


			this.frame().dispatchEvent(new WindowEvent(this.frame(), WindowEvent.WINDOW_CLOSING));
		}
	}

	public void updateArtifactList() {
		if (this.packageNameMenu.selectedItem() == null) return;
		String pkgN = this.packageNameMenu.selectedItem();
		String pkgFN = Compile.getRUPackageFileName(pkgN);
		RUPackage pkg = Compile.compileRUPackage(pkgFN);
		this.artifactNameMenu.setItems(pkg.artifacts());
	}


	public void panelShown() {this.frame().pack();}
	public void panelHidden() {}
	public void windowClosing() {}

	//return things
	public JFrame frame() {
		return this.frame;
	}
}