package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class AboutDialog extends JDialog {

	private RUGUI ui;

	public AboutDialog(RUGUI ui) {
		super(ui.frame(), RUData.RU_TITLE + " - About", Dialog.ModalityType.DOCUMENT_MODAL);
		this.ui = ui;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
			}
		});

		Container cntnr = this.getContentPane();

		String versionString = "Rank Up version " + RUData.VERSION + " by " + RUData.AUTHOR;
		JLabel versionLabel = new JLabel(RUData.html(versionString), SwingConstants.CENTER);
		cntnr.add(versionLabel, BorderLayout.CENTER);
		versionLabel.setBorder(new EmptyBorder(20, 80, 20, 80));

		this.setBounds(ui.frame().getLocation().x, ui.frame().getLocation().y, 300, 200);
		this.pack();


	}

}