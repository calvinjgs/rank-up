package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class ExportOptionsDialog extends JDialog implements ActionListener {

	private RUGUI ui;
	private CheckList optionCheckList;
	//check box labels
	private int includeSpecialsIndex;
	private String includeSpecialsString;

	private JButton confirmButton;

	public ExportOptionsDialog(RUGUI ui) {
		super(ui.frame(), RUData.RU_TITLE + " - Export Options", Dialog.ModalityType.DOCUMENT_MODAL);
		this.ui = ui;
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				e.getWindow().dispose();
			}
		});

		Container cntnr = this.getContentPane();
		JPanel listpan = new JPanel();
		listpan.setLayout(new BoxLayout(listpan, BoxLayout.Y_AXIS));

		JLabel lbl = new JLabel(RUData.html("Options for exporting to HTML file"));
		listpan.add(lbl);
		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setAlignmentX(Component.CENTER_ALIGNMENT);


		int orderTrack = 0;
		String[] stringlist = new String[1];


		this.includeSpecialsString = "Include special rules descriptions";
		stringlist[orderTrack] = this.includeSpecialsString;
		this.includeSpecialsIndex = orderTrack;
		orderTrack++;


		this.optionCheckList = new CheckList(stringlist);
		listpan.add(this.optionCheckList);

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
			ExportToHTML.includeSpecialDescriptions = optionCheckList.isChecked(includeSpecialsIndex);


			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));

		}
	}
}