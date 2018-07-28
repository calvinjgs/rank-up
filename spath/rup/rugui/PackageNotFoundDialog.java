package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class PackageNotFoundDialog extends JDialog implements ActionListener {

	private RUGUI ui;
	private String[] rupsReallyNotFound;
	private String[] rupsNeeded;
	private String filename;
	private JLabel message;
	private JButton loadWithPackagesButton, loadAnywayButton, cancelButton;


	public PackageNotFoundDialog(RUGUI ui, String filename, String[] rupsNeeded, String[] rupsNotFound) {
		super(ui.frame(), RUData.RU_TITLE + " - Package not found", Dialog.ModalityType.DOCUMENT_MODAL);
		this.ui = ui;
		this.rupsNeeded = rupsNeeded;
		this.filename = filename;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
			}
		});

		Container cntnr = this.getContentPane();

		DynamicArray<String> prnfda = new DynamicArray(new String[0]);
		String[] pkgs = Compile.getRUPackageNames();
		for (int i = 0; i < rupsNotFound.length; i++) {
			boolean isFound = false;
			for (int j = 0; j < pkgs.length; j++) {
				if (pkgs[j].equals(rupsNotFound[i])) {isFound = true; break;}
			}
			if (!isFound) prnfda.add(rupsNotFound[i]);
		}
		prnfda.trim();
		this.rupsReallyNotFound = prnfda.storage();

		String msg = "<div style='text-align: center;'>The following packages were not already loaded:<br>";
		msg += arrayToString(rupsNotFound);
		if (this.rupsReallyNotFound.length > 0) {
			//actually can't find some packages
			msg += "And these packages are not even in the packages directory:<br>";
			msg += arrayToString(this.rupsReallyNotFound);
			msg += "So don't even bother trying to load up these packages first.<br>";
			msg += "Attempting to load this .army file may have missing elements.<br>";
		} else {
			//packages can be loaded.
			msg += "If the appropriate packages are not loaded, then this .army file may have missing elements.<br>";
		}

		msg += "</div>";
		this.message = new JLabel(RUData.html(msg), SwingConstants.CENTER);
		cntnr.add(this.message, BorderLayout.CENTER);

		JPanel conpan = new JPanel();
		this.loadWithPackagesButton = new JButton(RUData.html("Load with appropriate packages", RUData.titleSize));
		conpan.add(this.loadWithPackagesButton);
		if (this.rupsReallyNotFound.length > 0) this.loadWithPackagesButton.setEnabled(false);
		this.loadWithPackagesButton.addActionListener(this);
		this.loadAnywayButton = new JButton(RUData.html("Load with current packages", RUData.titleSize));
		conpan.add(this.loadAnywayButton);
		this.loadAnywayButton.addActionListener(this);
		this.cancelButton = new JButton(RUData.html("Cancel", RUData.titleSize));
		conpan.add(this.cancelButton);
		this.cancelButton.addActionListener(this);




		conpan.setBorder(new EmptyBorder(10, 40, 10, 40));
		cntnr.add(conpan, BorderLayout.SOUTH);

		this.setBounds(ui.frame().getLocation().x, ui.frame().getLocation().y, 300, 200);
		this.pack();


	}

	private String arrayToString(String[] array) {
		String s = "";
		for (int i = 0; i < array.length; i++) {
			s += array[i];
			if (i < array.length - 1) s += ", ";
		}
		s += ".<br>";
		return s;
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.loadWithPackagesButton) {
			RUPackage[] rups = new RUPackage[this.rupsNeeded.length];
			for (int i = 0; i < this.rupsNeeded.length; i++) {
				System.out.println(this.rupsNeeded[i]);
				rups[i] = Compile.compileRUPackage(Compile.getRUPackageFileName(this.rupsNeeded[i]));
				System.out.println(rups[i]);
			}
			this.ui.setSelectedPackages(rups);
			this.ui.optionsArea().loadActually(this.filename);
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} else if (e.getSource() == this.loadAnywayButton) {
			this.ui.optionsArea().loadActually(this.filename);
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		} else if (e.getSource() == this.cancelButton) {
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}



}