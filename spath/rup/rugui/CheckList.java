package rup.rugui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import rup.rusrc.*;
import rup.datasrc.*;
import rup.tools.*;

public class CheckList<T> extends JPanel implements ItemListener {


	private boolean[] isChecked;
	private T[] itemList;
	private JCheckBox[] checkBoxList;

	public CheckList(T[] items) {
		super();
		this.setLayout(new FlowLayout());
		this.itemList = items;
		this.isChecked = new boolean[items.length];
		this.checkBoxList = new JCheckBox[items.length];
		JPanel pan = new JPanel();
		pan.setLayout(new BoxLayout(pan, BoxLayout.Y_AXIS));
		for (int i = 0; i < items.length; i++) {
			this.checkBoxList[i] = new JCheckBox(this.itemList[i].toString());
			pan.add(this.checkBoxList[i]);
			this.checkBoxList[i].addItemListener(this);
			this.isChecked[i] = false;
		}
		JScrollPane jsp = new JScrollPane();
		jsp.add(pan);
		//this.add(jsp);
		this.add(pan);
	}

	 public void itemStateChanged(ItemEvent e) {
		 for (int i = 0; i < this.checkBoxList.length; i++) {
			 if (e.getItemSelectable() == this.checkBoxList[i]) {
				 this.isChecked[i] = e.getStateChange() == ItemEvent.SELECTED;
				 break;
			 }
		 }
	 }


	//set things
	public void setChecked(int i) {
		if (!this.isChecked[i]) this.checkBoxList[i].doClick();
	}
	public void setUnchecked(int i) {
		if (this.isChecked[i]) this.checkBoxList[i].doClick();
	}


	//return things
	public boolean isChecked(int i) {
		return this.isChecked[i];
	}
	public boolean isChecked(T item) {
		for (int i = 0; i < itemCount(); i++) {
			if (this.itemList[i].equals(item))
				return this.isChecked(i);
		}
		return false;
	}
	public int itemCount() {
		return this.checkBoxList.length;
	}
	public T itemAt(int i) {
		return this.itemList[i];
	}
}