package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public abstract class EditorPanel extends JPanel {
	JFrame parentFrame;
	JFrame frame;


	public EditorPanel(JFrame parent) {
		super();
		construct(parent);
	}

	public EditorPanel(LayoutManager layout, JFrame parent) {
		super(layout);
		construct(parent);
	}

	private void construct(JFrame parent) {
		this.parentFrame = parent;
		this.frame = new JFrame();
		this.frame.add(this);
		this.frame.setDefaultCloseOperation(this.frame.DO_NOTHING_ON_CLOSE);
		if (parent != null) this.frame.setLocation(this.parentFrame.getLocation());
		this.frame.setResizable(false);

		this.frame.addComponentListener (new ComponentAdapter() {
			public void componentShown(ComponentEvent e) {
				System.out.println("componentShown");
				panelShown();
			}
			public void componentHidden(ComponentEvent e) {
				System.out.println("componentHidden");
				panelHidden();
			}
		});

		this.frame.addWindowListener(new java.awt.event.WindowAdapter() {
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				EditorPanel.this.frame.setVisible(false);
				System.out.println("editor panel invisible");
				if (EditorPanel.this.parentFrame != null) EditorPanel.this.parentFrame.setVisible(true);
				System.out.println("parent panel visible");
				EditorPanel.this.frame.dispose();
				System.out.println("editor panel disposed.");

				EditorPanel.this.windowClosing();
			}
		});
	}

	public void switchTo(EditorPanel ep) {
		this.frame.setVisible(false);
		ep.frame().setVisible(true);
	}

	//to be overridden by subclasses
	public abstract void panelShown();
	public abstract void panelHidden();
	public abstract void windowClosing();

	//return things
	public JFrame frame() {
		return this.frame;
	}
}