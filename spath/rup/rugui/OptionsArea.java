package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.*;
import java.io.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class OptionsArea extends JPanel implements ActionListener {

	private RUGUI ui;

	private JButton clearButton, openButton, saveButton;
	private JButton exportToHTMLButton, quickExportButton, ExportToTXTButton;
	private JButton exportOptionsButton;
	private JFileChooser fc;
	private String selectedFileName;

	public OptionsArea(RUGUI ui) {
		super();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		this.ui = ui;

		this.clearButton = new JButton(RUData.html("Clear"));
		this.clearButton.setMinimumSize(new Dimension(this.getWidth(), clearButton.getMinimumSize().height));
		this.clearButton.addActionListener(this);
		this.add(clearButton);

		this.openButton = new JButton(RUData.html("Open"));
		this.openButton.setMinimumSize(new Dimension(this.getWidth(), openButton.getMinimumSize().height));
		this.openButton.addActionListener(this);
		this.add(openButton);

		this.saveButton = new JButton(RUData.html("Save"));
		this.saveButton.setMinimumSize(new Dimension(this.getWidth(), saveButton.getMinimumSize().height));
		this.saveButton.addActionListener(this);
		this.add(saveButton);

		this.exportToHTMLButton = new JButton(RUData.html("Export to HTML"));
		this.exportToHTMLButton.setMinimumSize(new Dimension(this.getWidth(), exportToHTMLButton.getMinimumSize().height));
		this.exportToHTMLButton.addActionListener(this);
		this.add(exportToHTMLButton);

		this.quickExportButton = new JButton(RUData.html("Quick Export to HTML"));
		this.quickExportButton.setMinimumSize(new Dimension(this.getWidth(), quickExportButton.getMinimumSize().height));
		this.quickExportButton.addActionListener(this);
		this.add(quickExportButton);

		this.ExportToTXTButton = new JButton(RUData.html("Export to .txt"));
		this.ExportToTXTButton.setMinimumSize(new Dimension(this.getWidth(), ExportToTXTButton.getMinimumSize().height));
		this.ExportToTXTButton.addActionListener(this);
		this.add(ExportToTXTButton);

		this.exportOptionsButton = new JButton(RUData.html("Export Options"));
		this.exportOptionsButton.setMinimumSize(new Dimension(this.getWidth(), exportOptionsButton.getMinimumSize().height));
		this.exportOptionsButton.addActionListener(this);
		this.add(exportOptionsButton);


		this.fc = new JFileChooser();

	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == clearButton) {
			clear();
		} else if (e.getSource() == openButton) {
			open();
		} else if (e.getSource() == saveButton) {
			save();
		} else if (e.getSource() == exportToHTMLButton) {
			exportToHTML();
		} else if (e.getSource() == quickExportButton) {
			quickExportToHTML();
		} else if (e.getSource() == ExportToTXTButton) {
			exportToTXT();
		} else if (e.getSource() == exportOptionsButton) {
			exportOptions();
		}
	}

	//removes every unit in the army, similar mashing the remove button
	//a number of times equal to the number of units in ui.army().detachments.
	public void clear() {
		System.out.println("***");
		System.out.println("begin clear");
		ArmyElement[][] dets = this.ui.army().detachments();
		if (dets.length > 0) {//army is not empty
			this.ui.detachmentsArea().selectNode(0, 0);
			System.out.println("dets.length = " + dets.length);
			for (int d = 0; d < dets.length; d++) {
				System.out.println("dets[d].length = " + dets[d].length);
				this.ui.detachmentsArea().selectNode(0, 0);
				for (int u = 0; u < dets[d].length; u++) {
					this.ui.ARButton().actionPerformed(null);
				}
			}
		}
		//update army requirements
		this.ui.armyReqs().buildRequirements(this.ui.army().detachments());
		//call to refresh rugui's displays
		this.ui.refreshDisplays();
	}


	public void open() {
		fc.setCurrentDirectory(RWFile.getFile(RUData.SAVES_PATH + RWFile.fs));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".army files", "army");
		fc.setFileFilter(filter);

		if (this.selectedFileName != null) {
			String fname = this.selectedFileName.substring(0, this.selectedFileName.lastIndexOf(".")) + ".army";
			fc.setSelectedFile(new File(fname));
		}

		int returnVal = fc.showOpenDialog(this);
		fc.resetChoosableFileFilters();//remove file filter
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			String filename = fc.getCurrentDirectory().getAbsolutePath() + RWFile.fs + file.getName();

			String[] armyPackages = SaveLoad.loadUsedPackages(filename);
			DynamicArray<String> rupsNotFound = new DynamicArray(new String[0]);
			for (int i = 0; i < armyPackages.length; i++) {
				boolean pkgFound = false;
				for (int j = 0; j < this.ui.selectedPackages().length; j++) {
					if (this.ui.selectedPackages(j).name().equals(armyPackages[i])) {
						pkgFound = true; break;
					}
				}
				if (!pkgFound) {
					rupsNotFound.add(armyPackages[i]);
				}
			}
			if (rupsNotFound.size() > 0) {
				//TODO show package not found dialogue box.
				return;
			}
			loadActually(filename);

		}
	}


	//the actually loading part of the load method. So it can be
	//called by a dialogue box as well.
	public void loadActually(String filename) {
		ArmyElement[][] dets = SaveLoad.load(filename, RUData.WORKINGPACKAGE.forces(), this.ui.armyReqs());

		System.out.println("clearing");
		clear();//clear out the current army list.
		//add in units one-by-one
		for (int d = 0; d < dets.length; d++) {
			for (int u = 0; u < dets[d].length; u++) {
				if ((dets[d][u] instanceof SelectedUnit)) {
					SelectedUnit unit = (SelectedUnit) dets[d][u];
					this.ui.setSelectedUnit(unit);
					System.out.println("selected: " + this.ui.selectedUnit().name());
				} else if (dets[d][u] instanceof Formation) {
					Formation formation = (Formation) dets[d][u];
					this.ui.setSelectedFormation(formation);
				}

				this.ui.ARButton().setAdd();
				System.out.println("ARButton action performed");
				this.ui.ARButton().actionPerformed(null);
			}
		}

		if (fc.getSelectedFile() != null) this.selectedFileName = fc.getSelectedFile().getName();
		System.out.println("updating reqs and displays");
		//update army requirements
		this.ui.armyReqs().buildRequirements(this.ui.army().detachments());
		//call to refresh rugui's displays
		this.ui.refreshDisplays();
		System.out.println("open done");
	}

	public void save() {
		fc.setCurrentDirectory(RWFile.getFile(RUData.SAVES_PATH + RWFile.fs));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".army files", "army");
		fc.setFileFilter(filter);

		if (this.selectedFileName != null) {
			String fname = this.selectedFileName.substring(0, this.selectedFileName.lastIndexOf(".")) + ".army";
			fc.setSelectedFile(new File(fname));
		}

		int returnVal = fc.showSaveDialog(this);
		fc.resetChoosableFileFilters();//remove file filter
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (this.ui.army().detachments().length > 0) {
				String filename = fc.getCurrentDirectory().getAbsolutePath() + RWFile.fs + file.getName();
				if (!filename.endsWith(".army")) {
					filename += ".army";
				}
				SaveLoad.save(filename, this.ui.army().detachments(), this.ui.armyReqs(), this.ui.selectedPackages());
			}
		}
		if (fc.getSelectedFile() != null) this.selectedFileName = fc.getSelectedFile().getName();

	}


	public void exportToHTML() {
		fc.setCurrentDirectory(RWFile.getFile(RUData.ARMY_LIST_PATH + RWFile.fs));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("HTML files", "htm", "html");
		fc.setFileFilter(filter);

		if (this.selectedFileName != null) {
			String fname = this.selectedFileName.substring(0, this.selectedFileName.lastIndexOf(".")) + ".htm";
			fc.setSelectedFile(new File(fname));
		}

		int returnVal = fc.showDialog(this, "Export");
		fc.resetChoosableFileFilters();//remove file filter
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (this.ui.army().detachments().length > 0) {
				String filename = fc.getCurrentDirectory().getAbsolutePath() + RWFile.fs + file.getName();
				if (!filename.endsWith(".htm") && !filename.endsWith(".html")) {
					filename += ".htm";
				}
				ExportToHTML.exportArmy(this.ui.army().detachments(), this.ui.armyReqs(), filename);
			}
		}
		if (fc.getSelectedFile() != null) this.selectedFileName = fc.getSelectedFile().getName();

	}

	public void quickExportToHTML() {
		if (this.ui.army().detachments().length > 0) {
			ExportToHTML.exportArmy(this.ui.army().detachments(), this.ui.armyReqs());
		}
	}

	public void exportToTXT() {
		fc.setCurrentDirectory(RWFile.getFile(RUData.ARMY_LIST_PATH + RWFile.fs));
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".txt files", "txt");
		fc.setFileFilter(filter);

		if (this.selectedFileName != null) {
			String fname = this.selectedFileName.substring(0, this.selectedFileName.lastIndexOf(".")) + ".txt";
			fc.setSelectedFile(new File(fname));
		}

		int returnVal = fc.showDialog(this, "Export");
		fc.resetChoosableFileFilters();//remove file filter
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fc.getSelectedFile();
			if (this.ui.army().detachments().length > 0) {
				String filename = fc.getCurrentDirectory().getAbsolutePath() + RWFile.fs + file.getName();
				if (!filename.endsWith(".txt")) {
					filename += ".txt";
				}
				ExportToText.exportArmy(this.ui.army().detachments(), this.ui.armyReqs(), filename);
			}
		}
		if (fc.getSelectedFile() != null) this.selectedFileName = fc.getSelectedFile().getName();


	}

	public void exportOptions() {
		//spawn an ExportOptionsDialog window
		ExportOptionsDialog eod = new ExportOptionsDialog(this.ui);
		eod.setVisible(true);
	}

}