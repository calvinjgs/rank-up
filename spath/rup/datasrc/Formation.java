package rup.datasrc;

public class Formation extends ArmyElement {

	private String name;
	private Force force;
	private String pts;
	private String description;

	public Formation() {
		this.name = "";
		this.description = "";
	}

	public String toString() {
		return this.name;
	}

	//set things
	public void setName(String n) {
		this.name = n;
	}
	public void setForce(Force f) {
		this.force = f;
	}
	public void setPts(String p) {
		this.pts = p;
	}
	public void setDescription(String d) {
		this.description = d;
	}

	//return things
	public String name() {
		return this.name;
	}
	public Force force() {
		return this.force;
	}
	public String pts() {
		return this.pts;
	}
	public String description() {
		return this.description;
	}

}