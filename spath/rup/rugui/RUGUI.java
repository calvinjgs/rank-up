package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class RUGUI extends JPanel {

	private JFrame frame;

	private SelectPackagesButton packagesButton;
	private JLabel packagesLabel;

	private ForceListArea forceListArea;

	private UnitDisplayArea unitDisplayArea;
	private UnitOptionsArea unitOptionsArea;
	private SpecialDisplayArea specialDisplayArea;
	private DetachmentsArea detachmentsArea;
	private AddRemButton ARButton;
	private RequirementsArea requirementsArea;
	private OptionsArea optionsArea;


	private RUPackage[] selectedPackages;



	private SelectedUnit selectedUnit;
	private Formation selectedFormation;
	private Army army;
	private ArmyRequirements armyReqs;

	public RUGUI() {
		super();
		this.frame = new JFrame("Rank Up!");
		this.frame.add(this);
		this.frame.setDefaultCloseOperation(this.frame.DO_NOTHING_ON_CLOSE);

		this.frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				RUGUI.this.windowClosing();
			}
		});

		//sort rupackages from config file
		ConfigFile.read();
		String[] packagenames = ConfigFile.sortedRUPackageNames(Compile.getRUPackageNames());
		//compile all selected packages and merge them
		RUPackage[] selrups = new RUPackage[ConfigFile.selectedPackages.length];
		for (int i = 0; i < ConfigFile.selectedPackages.length; i++) {
			String filename = Compile.getRUPackageFileName(ConfigFile.selectedPackages[i]);
			selrups[i] = Compile.compileRUPackage(filename);
		}
		this.selectedPackages = selrups;
		RUData.WORKINGPACKAGE = RUPackage.merge(this.selectedPackages);
		RUData.WORKINGPACKAGE.applyUpdates();

		//create border layout
		this.setLayout(new BorderLayout());

		//NORTH
		JPanel north = new JPanel();
		north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));
		JPanel btnpan = new JPanel();
		btnpan.setLayout(new FlowLayout());
		this.packagesButton = new SelectPackagesButton();


		btnpan.add(this.packagesButton);
		this.packagesLabel = new JLabel(RUData.html("Packages selected: " + RUData.WORKINGPACKAGE.name()));
		north.add(btnpan);
		north.add(this.packagesLabel);
		this.add(north, BorderLayout.NORTH);


		//WEST
		//create force list area
		this.forceListArea = new ForceListArea(this);
		this.add(this.forceListArea, BorderLayout.WEST);

		//CENTER
		//split into top and bottom
		JPanel center = new JPanel(new GridLayout(2, 1));

		//create unit display area
		this.unitDisplayArea = new UnitDisplayArea(this);
		JScrollPane jsp = new JScrollPane(unitDisplayArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		center.add(jsp);
		//center.add(new Button("Button"));//for debuggin'

		JPanel centerbottom = new JPanel(new GridLayout(1, 2));
		//create unit options area
		this.unitOptionsArea = new UnitOptionsArea(this);
		centerbottom.add(new JScrollPane(this.unitOptionsArea));
		//divide center-bottom-right into top and bottom
		JPanel cbr = new JPanel(new BorderLayout());

		//create special rules display area
		this.specialDisplayArea = new SpecialDisplayArea(this);
		cbr.add(this.specialDisplayArea);
		//cbr.add(new Button("Button"), BorderLayout.CENTER);//for debuggin'
		//create add/remove button
		this.ARButton = new AddRemButton();
		cbr.add(this.ARButton, BorderLayout.NORTH);
		centerbottom.add(cbr);
		center.add(centerbottom);



		this.add(center, BorderLayout.CENTER);

		//SOUTH
		this.armyReqs = new ArmyRequirements();
		this.requirementsArea = new RequirementsArea(this);
		this.add(this.requirementsArea, BorderLayout.SOUTH);

		//EAST
		JPanel east = new JPanel();
		east.setLayout(new BoxLayout(east, BoxLayout.X_AXIS));

		this.army = new Army();
		this.detachmentsArea = new DetachmentsArea(this);
		east.add(this.detachmentsArea);
		this.detachmentsArea.setAlignmentY(Component.TOP_ALIGNMENT);

		this.optionsArea = new OptionsArea(this);
		this.optionsArea.setAlignmentY(Component.TOP_ALIGNMENT);

		east.add(this.optionsArea);

		this.add(east, BorderLayout.EAST);




		//this.frame().pack();
		this.frame().setBounds(ConfigFile.RUGUIX, ConfigFile.RUGUIY,
									ConfigFile.RUGUIWidth, ConfigFile.RUGUIHeight);
	}

	public void windowClosing() {
		System.out.println("RUGUI closing");
		//write to config file
		ConfigFile.RUGUIX = this.frame().getLocation().x;
		ConfigFile.RUGUIY = this.frame().getLocation().y;
		ConfigFile.RUGUIWidth = this.frame().getSize().width;
		ConfigFile.RUGUIHeight = this.frame().getSize().height;
		String[] selpackagenames = new String[this.selectedPackages.length];
		for (int i = 0; i < this.selectedPackages.length; i++)
			selpackagenames[i] = this.selectedPackages[i].name();
		ConfigFile.selectedPackages = selpackagenames;
		ConfigFile.write();

		System.exit(0);
	}

	//define PackagesButton
	class SelectPackagesButton extends JPanel implements ActionListener {

		public SelectPackagesButton() {
			super();
			JButton btn = new JButton(RUData.html("select packages"));
			this.add(btn);
			btn.addActionListener(this);
		}

		public void actionPerformed(ActionEvent e) {
			//spawn a SelectPackagesDialog window
			SelectPackagesDialog spd = new SelectPackagesDialog(RUGUI.this);
			spd.setVisible(true);
		}
	}



	//define AddRemButton
	class AddRemButton extends JPanel implements ActionListener {
		private boolean isAdd;
		private JButton button;
		public AddRemButton() {
			super(new GridLayout(1, 1));
			this.button = new JButton();
			this.add(this.button);
			this.button.addActionListener(this);
		}
		public void setAdd() {
			this.isAdd = true;
			this.button.setText(RUData.html("ADD<br><center>&rarr</center>", RUData.titleSize));
		}
		public void setRem() {
			this.isAdd = false;
			this.button.setText(RUData.html("REMOVE<br><center>&larr</center>", RUData.titleSize));
		}

		public void actionPerformed(ActionEvent e) {
			//because det area depends on army, det area must be modified first
			if (isAdd) {
				if (RUGUI.this.selectedUnit != null) {
					SelectedUnit cp = RUGUI.this.selectedUnit.copy();//creates new SelectedUnit
					RUGUI.this.selectedUnit.setArtefact(null);//only 1 arty allowed
					RUGUI.this.detachmentsArea().addUnit(cp);
					RUGUI.this.army.addUnit(cp);
				} else if (RUGUI.this.selectedFormation != null) {
					RUGUI.this.detachmentsArea().addUnit(RUGUI.this.selectedFormation);
					RUGUI.this.army.addUnit(RUGUI.this.selectedFormation);
				}
			} else {//isRemove
				if (RUGUI.this.selectedUnit != null) {
					SelectedUnit su = RUGUI.this.selectedUnit;
					RUGUI.this.detachmentsArea().removeUnit(su);
					RUGUI.this.army.removeUnit(su);
				} else if (RUGUI.this.selectedFormation != null) {
					Formation f = RUGUI.this.selectedFormation;
					RUGUI.this.detachmentsArea().removeUnit(f);
					RUGUI.this.army.removeUnit(f);
				}
			}
			//update army requirements
			RUGUI.this.armyReqs().buildRequirements(RUGUI.this.army.detachments());
			//call to refresh rugui's displays
			RUGUI.this.refreshDisplays();
		}
	}









	//set things
	public void setSelectedPackages(RUPackage[] rups) {
		this.selectedPackages = rups;
		RUData.WORKINGPACKAGE = RUPackage.merge(this.selectedPackages);
		RUData.WORKINGPACKAGE.applyUpdates();
		this.packagesLabel.setText(RUData.html("Packages selected: " + RUData.WORKINGPACKAGE.name()));
		this.forceListArea.refreshTree();
	}



	public void setSelectedUnit(SelectedUnit su) {
		this.selectedUnit = su;
		this.selectedFormation = null;
	}

	public void setSelectedUnit(Unit u) {
		SelectedUnit su = new SelectedUnit();
		su.setUnit(u);
		setSelectedUnit(su);
	}

	public void setSelectedFormation(Formation f) {
		this.selectedFormation = f;
		this.selectedUnit = null;
	}

	public void refreshDisplays() {
		if (this.selectedUnit != null) {
			unitDisplayArea.displaySelectedUnit();
		} else if (this.selectedFormation != null) {
			unitDisplayArea.displaySelectedFormation();
		}
		unitOptionsArea.displayUnitOptions();
		requirementsArea.displayRequirements();
	}

	//return things
	public JFrame frame() {
		return this.frame;
	}
	public RUPackage[] selectedPackages() {
		return this.selectedPackages;
	}
	public RUPackage selectedPackages(int i) {
		return this.selectedPackages[i];
	}



	public SelectedUnit selectedUnit() {
		return this.selectedUnit;
	}
	public Formation selectedFormation() {
		return this.selectedFormation;
	}

	public Army army() {
		return this.army;
	}
	public ForceListArea forceListArea() {
		return this.forceListArea;
	}
	public SpecialDisplayArea specialDisplayArea() {
		return this.specialDisplayArea;
	}
	public ArmyRequirements armyReqs() {
		return this.armyReqs;
	}

	public DetachmentsArea detachmentsArea() {
		return this.detachmentsArea;
	}
	public AddRemButton ARButton() {
		return this.ARButton;
	}

	public RequirementsArea requirementsArea() {
		return this.requirementsArea;
	}
	public OptionsArea optionsArea() {
		return this.optionsArea;
	}

}