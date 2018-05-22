package rup.datasrc;

import rup.tools.*;

//Unit entry in a force list. Prototype for a selected unit, before modifiers.
public class Unit {

	private String name;
	private Force force;
	private RUData.UNIT_TYPE type;
	private UnitSize[] sizes;
	private SpecialRef[] specials;
	private UnitOption[] options;

	public Unit() {
		this.name = "";
		this.type = RUData.UNIT_TYPE.INFANTRY;
		this.sizes = new UnitSize[0];
		this.specials = new SpecialRef[0];
		this.options = new UnitOption[0];
	}



	//set things
	public void setName(String s) {
		this.name = s;
	}
	public void setForce(Force f) {
		this.force = f;
	}
	public void setType(RUData.UNIT_TYPE t) {
		this.type = t;
	}
	public void setSizes(UnitSize[] us) {
		this.sizes = us;
	}
	public void setSpecials(SpecialRef[] spesh) {
		this.specials = spesh;
	}
	public void setOptions(UnitOption[] uo) {
		this.options = uo;
	}


	//return things
	public String name() {
		return this.name;
	}
	public Force force() {
		return this.force;
	}
	public RUData.UNIT_TYPE type() {
		return this.type;
	}
	public UnitSize[] sizes() {
		return this.sizes;
	}
	public UnitSize sizes(int index) {
		return this.sizes[index];
	}
	public SpecialRef[] specials() {
		return this.specials;
	}
	public SpecialRef specials(int i) {
		return this.specials[i];
	}
	public UnitOption[] options() {
		return this.options;
	}
	public UnitOption options(int j) {
		return this.options[j];
	}
	public Flavour options(int j, int i) {
		return this.options[j].flavours(i);
	}

	public String toString() {
		return this.name();
	}


}