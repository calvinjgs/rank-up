package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class DropDownMenu<T> extends JPanel {

	private String name;
	private JComboBox<itemEntry> list;
	T selectedItem;

	public DropDownMenu (String n, T[] objs) {
		super(new FlowLayout());
		this.name = n;
		this.add(new JLabel(RUData.html(n)));
		itemEntry<T>[] entries = new itemEntry[objs.length];
		for (int i = 0; i < entries.length; i++)
			entries[i] = new itemEntry(objs[i]);
		this.list = new JComboBox(entries);
		this.list.setMaximumSize(new Dimension(200, 50));
		this.add(this.list);
	}

	//define item entry for list
	class itemEntry<E> {
		public E item;
		public itemEntry(E item) {
			this.item = item;
		}
		public String toString() {
			return RUData.html(this.item.toString());
		}
		public boolean equals(Object obj) {
			if (obj instanceof itemEntry) {
				itemEntry ie = (itemEntry) obj;
				return this.item.equals(ie.item);
			} else {
				return false;
			}
		}
	}

	public void addActionListener(ActionListener ep) {
		this.list.addActionListener(ep);
	}
	public boolean caused(ActionEvent e) {
		return e.getSource() == this.list;
	}
	public void setSelectedItem(T item) {
		this.list.setSelectedItem(new itemEntry(item));
		this.selectedItem = item;
	}

	public void addItem(T item) {
		this.list.addItem(new itemEntry(item));
	}
	public void addItems(T[] items) {
		for (int i = 0; i < items.length; i++) {
			this.addItem(items[i]);
		}
	}
	public void setItems(T[] items) {
		removeAllItems();
		addItems(items);
	}
	public void removeAllItems() {
		this.list.removeAllItems();
	}
	public void removeSelectedItem() {
		this.list.removeItemAt(this.list.getSelectedIndex());
	}

	public void setPrototypeDisplayValue(String prototypeText) {
		this.list.setPrototypeDisplayValue(new itemEntry(prototypeText));
	}
	//return things
	public T selectedItem() {
		itemEntry ie = this.list.getItemAt(this.list.getSelectedIndex());
		if (ie == null) return null;//for uninitialized list.
		return (T) ie.item;
	}
	public int itemCount() {
		return this.list.getItemCount();
	}
	public T itemAt(int i) {
		return (T) this.list.getItemAt(i).item;
	}
}