package rup.datasrc;

import rup.tools.*;

public class UnitOption {

	private Flavour[] flavours;
	public UnitOption() {
		this.flavours = new Flavour[0];
	}

	public UnitOption clone() {
		UnitOption clone = new UnitOption();
		Flavour[] flaves = new Flavour[this.flavours().length];
		for (int i = 0; i < this.flavours().length; i++) {
			flaves[i] = this.flavours(i).clone();
		}
		clone.setFlavours(flaves);
		return clone;
	}

	public String toString() {
		if (this.flavours.length == 0) return "undefined option";
		if (this.flavours.length == 1) return this.flavours[0].toString();
		else return this.flavours[0] + " or...";
	}

	//set things
	public void setFlavours(Flavour[] fs) {
		this.flavours = fs;
	}

	//return things
	public Flavour[] flavours() {
		return this.flavours;
	}
	public Flavour flavours(int i) {
		return this.flavours[i];
	}

}