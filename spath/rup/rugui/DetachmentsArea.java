package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class DetachmentsArea extends JPanel implements TreeSelectionListener {

	private RUGUI ui;
	JTree tree;

	public DetachmentsArea(RUGUI ui) {
		super(new BorderLayout());
		this.ui = ui;
		JLabel label = new JLabel(RUData.html("Army:"));
		label.setPreferredSize(new Dimension(150, RUData.textSize + 6));//kinda hack-y right now
		this.add(label, BorderLayout.NORTH);
		JPanel center = new JPanel();
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root");
		buildTree(root);
		this.tree = new JTree(root);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.addTreeSelectionListener(this);
		tree.setRootVisible(false);
		tree.setExpandsSelectedPaths(true);
		JScrollPane jsp = new JScrollPane(tree);
		center.add(jsp);
		this.add(center, BorderLayout.CENTER);

	}

	//changes root, by building tree from the ui's army.
	private void buildTree(DefaultMutableTreeNode root) {
		DefaultMutableTreeNode detachment = null;
		DefaultMutableTreeNode unit = null;
		SelectedUnit[][] units = this.ui.army().detachments();
		Force[] forces = this.ui.army().forces();
		for (int d = 0; d < units.length; d++) {
			//build detchment node
			detachment = new DefaultMutableTreeNode(RUData.html(forces[d].name(), RUData.titleSize));
			for (int u = 0; u < units[d].length; u++) {
				//build unit node
			unit = new DefaultMutableTreeNode(new unitEntry(units[d][u]));
			detachment.add(unit);
			}
			root.add(detachment);
		}
	}

	//add SelectedUnit to tree
	public void addUnit(SelectedUnit unit) {
		//find detachment index
		boolean foundDet = false;
		int detIndex = -1;
		SelectedUnit[][] units = this.ui.army().detachments();
		for (int d = 0; d < units.length; d++) {
			if (units[d].length > 0) {
				foundDet = unit.force() == units[d][0].force();
				if (foundDet) {
					//add unit to existing detachment
					detIndex = d;
					break;
				}
			}
		}

		DefaultTreeModel model = (DefaultTreeModel) this.tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		DefaultMutableTreeNode unitNode = new DefaultMutableTreeNode(new unitEntry(unit));
		if (!foundDet) {
			//build detachment node and add unit to that
			DefaultMutableTreeNode detachmentNode = new DefaultMutableTreeNode(RUData.html(unit.force().name(), RUData.titleSize));
			detachmentNode.add(unitNode);
			root.add(detachmentNode);
			detIndex = root.getChildCount() - 1;
		} else {
			DefaultMutableTreeNode detachmentNode = (DefaultMutableTreeNode) root.getChildAt(detIndex);
			detachmentNode.add(unitNode);
		}
		//set selected detachment index for requirements area
		this.ui.requirementsArea().setSdIndex(detIndex);

		model.reload();
		this.revalidate();
		this.repaint();
		tree.scrollPathToVisible(new TreePath(unitNode.getPath()));
	}

	//removes unit from the tree
	public void removeUnit(SelectedUnit unit) {

		//find correct indices
		SelectedUnit[][] units = this.ui.army().detachments();
		int detIndex = -1;
		int unitIndex = -1;
		for (int d = 0; d < units.length; d++) {
			for (int u = 0; u < units[d].length; u++) {
				if (unit == units[d][u]) {
					detIndex = d;
					unitIndex = u;
				}
			}
		}

		DefaultTreeModel model = (DefaultTreeModel) this.tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		DefaultMutableTreeNode detachmentNode = null;
		if (detIndex != -1 && unitIndex != -1) {
			//remove unit node
			detachmentNode = (DefaultMutableTreeNode) root.getChildAt(detIndex);
			detachmentNode.remove(unitIndex);
			//if detachment node is empty remove it too
			if (detachmentNode.isLeaf()) {
				root.remove(detIndex);
				detIndex = -1;
			}
		}

		model.reload();
		this.revalidate();
		this.repaint();

		if (detIndex != -1 && unitIndex != -1) {
			//select some other unit node in the same detachment
			unitIndex = Math.min(unitIndex, detachmentNode.getChildCount() - 1);
			DefaultMutableTreeNode unitNode = (DefaultMutableTreeNode) detachmentNode.getChildAt(unitIndex);
			TreePath tpath = new TreePath(unitNode.getPath());
			tree.scrollPathToVisible(tpath);
			tree.setSelectionPath(tpath);
			tree.makeVisible(tpath);
		}
		//set selected detachment index for requirements area
		this.ui.requirementsArea().setSdIndex(detIndex);
	}

    public void valueChanged(TreeSelectionEvent e) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (node == null) return;//if nothing is selected, do nothing
		//only one of these two should be selected at one time
		this.ui.forceListArea().clearSelection();

		DefaultMutableTreeNode det = null;//selected detachment
		Object obj = node.getUserObject();
		if (node.isLeaf()) {
			unitEntry ue = (unitEntry) (obj);
			ui.setSelectedUnit(ue.unit);
			this.ui.ARButton().setRem();//switch add/remove btn to remove

			det = (DefaultMutableTreeNode) node.getParent();
		} else {//node must refer to detachment
			det = node;
		}
		//set selected detachment index for requirements area
		this.ui.requirementsArea().setSdIndex(det.getParent().getIndex(det));
		//call to refresh ui's displays
		this.ui.refreshDisplays();
    }

    public void clearSelection() {
		this.tree.clearSelection();
	}

	public void selectNode(int d, int u) {
		DefaultTreeModel model = (DefaultTreeModel) this.tree.getModel();
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
		DefaultMutableTreeNode detachmentNode = (DefaultMutableTreeNode) root.getChildAt(d);
		DefaultMutableTreeNode unitNode = (DefaultMutableTreeNode) detachmentNode.getChildAt(u);
		TreePath tpath = new TreePath(unitNode.getPath());
		tree.scrollPathToVisible(tpath);
		tree.setSelectionPath(tpath);
		tree.makeVisible(tpath);
	}

    //define class that holds SelectedUnit object w/ formatted toString
    class unitEntry {
		public SelectedUnit unit;
		public unitEntry(SelectedUnit u) {
			this.unit = u;
		}
		public String toString() {
			return RUData.html(unit.name());
		}
	}
}