package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class ForceListArea extends JPanel implements TreeSelectionListener {

	RUGUI ui;

	JTree tree;

	public ForceListArea(RUGUI ui) {
		super(new BorderLayout());
		this.ui = ui;
		add(new JLabel(RUData.html("Forces:")), BorderLayout.NORTH);
		this.tree = new JTree(buildTree());
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		tree.setRootVisible(false);
		JScrollPane jsp = new JScrollPane(tree);
		jsp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.add(jsp, BorderLayout.CENTER);
		}

	private DefaultMutableTreeNode buildTree() {
		Force[] forcelist = RUData.WORKINGPACKAGE.forces();
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		DefaultMutableTreeNode force = null;
		DefaultMutableTreeNode unit = null;
		for (int f = 0; f < forcelist.length; f++) {
			force = new DefaultMutableTreeNode(new forceEntry(forcelist[f]));
			for (int u = 0; u < forcelist[f].units().length; u++) {
				unit = new DefaultMutableTreeNode(new unitEntry(forcelist[f].units(u)));
				force.add(unit);
			}
			//TODO add formations nodes.
			root.add(force);
		}
		return root;
	}

	public void refreshTree() {
		DefaultTreeModel dtm = (DefaultTreeModel) this.tree.getModel();
		dtm.setRoot(buildTree());
	}

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

        if (node == null) return;//if nothing is selected, do nothing
        //only one of these two should be selected at one time
		this.ui.detachmentsArea().clearSelection();

		Object obj = node.getUserObject();
		if (node.isLeaf()) {
			unitEntry ue = (unitEntry) (obj);
			ui.setSelectedUnit(ue.unit);
			this.ui.ARButton().setAdd();//switch add/remove btn to add
		}
		//call to refresh ui's displays
		this.repaint();
		this.revalidate();
		this.ui.refreshDisplays();

	}

    public void clearSelection() {
		this.tree.clearSelection();
	}

    //define class that holds Force object w/ formatted toString
    class forceEntry {
		public Force force;
		public forceEntry(Force f) {
			this.force = f;
		}
		public String toString() {
			return RUData.html(force.name(), RUData.titleSize);
		}
	}
    //define class that holds Unit object w/ formatted toString
    class unitEntry {
		public Unit unit;
		public unitEntry(Unit u) {
			this.unit = u;
		}
		public String toString() {
			return RUData.html(unit.name());
		}
	}
}