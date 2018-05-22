package rup.ruegui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.border.*;

import rup.datasrc.*;
//import rup.ruesrc.*;
import rup.tools.*;

public class ItemList<T> extends JPanel implements ListSelectionListener {

	private String name;
	private DefaultListModel<itemEntry> listModel;
	private JList<itemEntry> list;
	T selectedItem;

	public ItemList (String n, T[] objs) {
		this.name = n;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JLabel label = new JLabel(RUData.html(n));
		this.add(label);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.listModel = new DefaultListModel();
		for (int i = 0; i < objs.length; i++)
			this.listModel.addElement(new itemEntry(objs[i]));
		this.list = new JList<itemEntry>(this.listModel);
		this.list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.list.setVisibleRowCount(10);
		this.list.addListSelectionListener(this);
		JScrollPane jsp = new JScrollPane(this.list);
		this.add(jsp);
	}

	//define item entry for list
	class itemEntry<T> {
		public T item;
		public itemEntry(T item) {
			this.item = item;
		}
		public String toString() {
			return RUData.html(this.item.toString());
		}
	}

	public void addItem(T item) {
		itemEntry<T> ie = new itemEntry(item);
		this.listModel.addElement(ie);
	}

	public void addItems(T[] objs) {
		for (int i = 0; i < objs.length; i++)
			this.listModel.addElement(new itemEntry(objs[i]));
	}
	public void setItems(T[] objs) {
		this.clear();
		this.addItems(objs);
	}

	public void removeSelectedItem() {
		this.listModel.removeElement(this.list.getSelectedValue());
	}

	public void clear() {
		this.listModel.clear();
	}

	public void swap(int i, int j) {
		itemEntry iei = this.listModel.get(i);
		itemEntry iej = this.listModel.get(j);
		this.listModel.set(j, iei);
		this.listModel.set(i, iej);
	}

	public void moveUp() {
		int index = this.selectedItemIndex();
		if (index > 0) {
			this.swap(index, index - 1);
			this.list.setSelectedIndex(index - 1);
		}
	}
	public void moveDown() {
		int index = this.selectedItemIndex();
		if (index != -1 && index < this.length() - 1) {
			this.swap(index, index + 1);
			this.list.setSelectedIndex(index + 1);
		}
	}


	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting() == false) {
            if (list.getSelectedIndex() != -1) {
				this.selectedItem = (T) this.list.getSelectedValue().item;
			}
		}
	}

	//set things
	public void setSelectedItem(T item) {
		for (int i = 0; i < length(); i++) {
			if (item.equals(item(i))) {
				this.list.setSelectedIndex(i);
			}
		}
	}
	public void setRows(int r) {
		this.list.setVisibleRowCount(r);
	}

	//return things
	public T selectedItem() {
		if (this.list.getSelectedValue() == null) return null;
		return (T) this.list.getSelectedValue().item;
	}
	public int selectedItemIndex() {
		return this.list.getSelectedIndex();
	}
	public T item(int i) {
		return (T) this.listModel.elementAt(i).item;
	}
	public int length() {
		return this.listModel.getSize();
	}
/*
	public Object[] allItems(Class c) {
		 T[] items = (T[]) Array.newInstance(c, length());
		for (int i = 0; i < items.length; i++) {
			items[i] = this.listModel.elementAt(i).item;
		}
		return items;
	}
	*/
}