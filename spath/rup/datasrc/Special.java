package rup.datasrc;

import rup.tools.*;

public class Special implements NamedInterface {

	private String name;
	private String description;

	public Special(String name) {
		this.name = name;
	}
	public Special() {
		this("");
	}



	public String toString() {
		return this.name;
	}

	public boolean equals(Object obj) {
		if (obj instanceof Special) {
			Special spec = (Special) obj;
			return (this.name.equals(spec.name()));
		}
		return false;
	}

	public Special clone() {
		Special clone = new Special();
		clone.setName(this.name());
		clone.setDescription(this.description());
		return clone;
	}

	public void applyUpdate(SpecialUpdate sup) {
		if (!sup.newName().equals("")) {
			this.name = sup.newName();
		}
		if (!sup.newDescription().equals("")) {
			this.description = sup.newDescription();
		}
	}

	//set things
	public void setName(String name) {
		this.name = name;
	}
	public void setDescription(String d) {
		this.description = d;
	}

	//return things
	public String name() {
		return this.name;
	}
	public String description() {
		return this.description;
	}
}