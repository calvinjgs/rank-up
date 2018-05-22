package rup.datasrc;

import rup.tools.*;

public class Special {

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