package rup.datasrc;

public class Formation {

	private String name;
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
	public String pts() {
		return this.pts;
	}
	public String description() {
		return this.description;
	}

}