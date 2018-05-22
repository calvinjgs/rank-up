package rup.datasrc;

import rup.tools.*;

//Force list which lists unit entries.
public class Force {

	private String name;
	private RUData.ALIGNMENT alignment;

	private Unit[] units;
	private Formation[] formations;

	public Force() {
		this.name = "";
		this.alignment = RUData.ALIGNMENT.NEUTRAL;
		this.units = new Unit[0];
		this.formations = new Formation[0];
	}

	//set things
	public void setName(String n) {
		this.name = n;
	}
	public void setAlignment(RUData.ALIGNMENT al) {
		this.alignment = al;
	}
	public void setUnits(Unit[] us) {
		this.units = us;
	}
	public void setFormations(Formation[] fs) {
		this.formations = fs;
	}

	//return things
	public String name() {
		return this.name;
	}
	public RUData.ALIGNMENT alignment() {
		return this.alignment;
	}
	public Unit[] units() {
		return this.units;
	}
	public Unit units(int index) {
		return this.units[index];
	}
	public Formation[] formations() {
		return this.formations;
	}
	public Formation formations(int index) {
		return this.formations[index];
	}

	public String toString() {
		return this.name();
	}


}