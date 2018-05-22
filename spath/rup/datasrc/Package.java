package rup.datasrc;

public class RUPackage {

	private String name;
	private Force[] forces;
	private Special[] specials;
	private Artifact[] artifacts;

	public RUPackage(String n) {
		this.name = n;
	}
	public RUPackage() {
		this.name = "";
	}

	//set things

	public void setName(Strin n) {
		this.name = n;
	}

	public void setForces(Force[] f) {
		this.forces = f;
	}

	public void setForces(Special[] s) {
		this.specials = s;
	}

	public void setForces(UnitOption[] a) {
		this.artifacts = a;
	}

	//return things

	public String name() {
		return this.name;
	}
	public Force[] forces() {
		return this.forces;
	}
	public Special[] specials() {
		return this.specials;
	}
	public UnitOption[] artifacts() {
		return this.artifacts;
	}
}