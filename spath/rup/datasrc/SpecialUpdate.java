package rup.datasrc;

import rup.tools.*;

public class SpecialUpdate extends Reference<Special> {

	private String newSpecialName;
	private String newDescription;

	public SpecialUpdate() {
		super();
		this.newSpecialName = "";
		this.newDescription = "";
	}


	//set things
	public void setSpecial(Special spec) {
		super.setObject(spec);
	}
	public void setName(String n) {
		super.setObjName(n);
	}
	public void setNewName(String n) {
		this.newSpecialName = n;
	}
	public void setNewDescription(String d) {
		this.newDescription = d;
	}

	//return things
	public Special special() {
		return super.object();
	}
	public Special special(Special[] specs) {
		return super.object(specs);
	}
	public String newName() {
		return this.newSpecialName;
	}
	public String newDescription() {
		return this.newDescription;
	}
}