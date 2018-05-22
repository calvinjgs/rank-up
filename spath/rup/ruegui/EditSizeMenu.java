package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class EditSizeMenu extends EditorPanel implements ActionListener {

	private UnitSize size;
	private DropDownMenu<RUData.UNIT_SIZE> nameMenu;
	private TextBox spField, meField, raField, deField, attField, neWField, neRField, ptsField;
	private JButton saveButton;


	public EditSizeMenu(JFrame parent, UnitSize s) {
		super(new BorderLayout(), parent);
		this.size = s;
		this.frame().setTitle(RUData.RUE_TITLE + " - Unit Size");
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		//name menu
		this.nameMenu = new DropDownMenu("Size", RUData.UNIT_SIZE.values());
		this.nameMenu.setSelectedItem(this.size.name());
		this.add(this.nameMenu, BorderLayout.NORTH);
		this.nameMenu.addActionListener(this);


		JPanel statspanX = new JPanel();
		statspanX.setLayout(new BoxLayout(statspanX, BoxLayout.X_AXIS));

		JPanel statpan;
		JLabel label;

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("sp"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.spField = new TextBox("");
		this.spField.setText(this.size.sp());
		this.spField.setColumns(4);
		statpan.add(this.spField);
		this.spField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("me"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.meField = new TextBox("");
		this.meField.setText(this.size.me());
		this.meField.setColumns(4);
		statpan.add(this.meField);
		this.meField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("ra"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.raField = new TextBox("");
		this.raField.setText(this.size.ra());
		this.raField.setColumns(4);
		statpan.add(this.raField);
		this.raField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("de"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.deField = new TextBox("");
		this.deField.setText(this.size.de());
		this.deField.setColumns(4);
		statpan.add(this.deField);
		this.deField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("att"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.attField = new TextBox("");
		this.attField.setText(this.size.att());
		this.attField.setColumns(4);
		statpan.add(this.attField);
		this.attField.addActionListener(this);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("nerve"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		JPanel nervepan = new JPanel();
		nervepan.setLayout(new GridLayout(0, 2, 3, 0));
		this.neWField = new TextBox("");
		this.neWField.setText(this.size.neW());
		this.neWField.setColumns(4);
		nervepan.add(this.neWField);
		this.neWField.addActionListener(this);
		this.neRField = new TextBox("");
		this.neRField.setText(this.size.neR());
		this.neRField.setColumns(4);
		nervepan.add(this.neRField);
		this.neRField.addActionListener(this);
		statpan.add(nervepan);
		statspanX.add(statpan);

		statpan = new JPanel();
		statpan.setLayout(new BoxLayout(statpan, BoxLayout.Y_AXIS));
		label = new JLabel(RUData.html("pts"));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		statpan.add(label);
		this.ptsField = new TextBox("");
		this.ptsField.setText(this.size.pts());
		this.ptsField.setColumns(4);
		statpan.add(this.ptsField);
		this.ptsField.addActionListener(this);
		statspanX.add(statpan);

		this.add(statspanX, BorderLayout.CENTER);
		this.setBorder(new EmptyBorder(10, 40, 10, 40));//top,left,bottom,right


		//save button
		JPanel savePanel = new JPanel();
		this.saveButton = new JButton(RUData.html("Save", RUData.titleSize*2));
		savePanel.add(this.saveButton);
		this.add(savePanel, BorderLayout.SOUTH);
		this.saveButton.addActionListener(this);


		this.frame().add(this);
		this.frame().pack();
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == this.saveButton) {
			//save name
			RUData.UNIT_SIZE sz = this.nameMenu.selectedItem();
			this.size.setName(sz);
			//save stats
			String stat = this.spField.getText();
			if(!("".equals(stat))) {
				this.size.setSp(stat);
			}
			stat = this.meField.getText();
			this.size.setMe(stat);
			stat = this.raField.getText();
			this.size.setRa(stat);
			stat = this.deField.getText();
			this.size.setDe(stat);
			stat = this.attField.getText();
			this.size.setAtt(stat);
			stat = this.neWField.getText();
			this.size.setNeW(stat);
			stat = this.neRField.getText();
			this.size.setNeR(stat);
			stat = this.ptsField.getText();
			this.size.setPts(stat);
			this.frame().dispatchEvent(new WindowEvent(this.frame(), WindowEvent.WINDOW_CLOSING));

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