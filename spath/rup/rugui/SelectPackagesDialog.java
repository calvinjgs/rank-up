package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class SelectPackagesDialog extends JDialog implements ActionListener {

	private RUGUI ui;
	private CheckList<String> packageCheckList;
	private JButton confirmButton;

	public SelectPackagesDialog(RUGUI ui) {
		super(ui.frame(), RUData.RU_TITLE + " - Select Packages", Dialog.ModalityType.DOCUMENT_MODAL);
		this.ui = ui;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
			}
		});

		Container cntnr = this.getContentPane();
		JPanel listpan = new JPanel();
		listpan.setLayout(new BoxLayout(listpan, BoxLayout.Y_AXIS));
		JLabel lbl = new JLabel(RUData.html("Select packages to load"));
		listpan.add(lbl);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
		String[] packagenames = ConfigFile.sortedRUPackageNames(Compile.getRUPackageNames());
		this.packageCheckList = new CheckList(packagenames);
		for (int i = 0; i < packagenames.length; i++) {
			for (int j = 0; j < ui.selectedPackages().length; j++) {
				if (packagenames[i].equals(ui.selectedPackages(j).name())) {
					this.packageCheckList.setChecked(i);
					break;
				}
			}
		}
		listpan.add(packageCheckList);
		cntnr.add(listpan, BorderLayout.CENTER);
		JPanel conpan = new JPanel();
		this.confirmButton = new JButton(RUData.html("Confirm", RUData.titleSize));
		conpan.add(this.confirmButton);
		this.confirmButton.addActionListener(this);
		conpan.setBorder(new EmptyBorder(10, 40, 10, 40));
		cntnr.add(conpan, BorderLayout.SOUTH);

		this.setBounds(ui.frame().getLocation().x, ui.frame().getLocation().y, 300, 200);
		this.pack();


	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.confirmButton) {
			//clear out stuff that depends on the old packages
			this.ui.optionsArea().clear();
			SelectedUnit su = null;
			this.ui.setSelectedUnit(su);

			//merge new packages
			DynamicArray<RUPackage> rupda = new DynamicArray(new RUPackage[0]);
			for (int i = 0; i < this.packageCheckList.itemCount(); i++) {
				if (this.packageCheckList.isChecked(i)) {
					String pkgname = this.packageCheckList.itemAt(i);
					String fname = Compile.getRUPackageFileName(pkgname);
					rupda.add(Compile.compileRUPackage(fname));
				}
			}
			rupda.trim();
			this.ui.setSelectedPackages(rupda.storage());

			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

		}
	}
}