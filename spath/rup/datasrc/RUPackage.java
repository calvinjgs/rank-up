package rup.datasrc;
import rup.tools.*;

public class RUPackage {

	private String name;
	private String[] dependencies;
	private Force[] forces;
	private Special[] specials;
	private Artifact[] artifacts;


	public RUPackage() {
		this.name = "unnamed package";
		this.dependencies = new String[0];
		this.forces = new Force[0];
		this.specials = new Special[0];
		this.artifacts = new Artifact[0];
	}

	public String toString() {
		return this.name;
	}


	//merges multiple packages
	//currently does not replace objects (forces etc.) with the same name
	public static RUPackage merge(RUPackage[] rups) {
		RUPackage mergedRup = new RUPackage();
		//merge names
		String name = "";
		for (int i = 0; i < rups.length; i++) {
			name += rups[i].name();
			if (i < rups.length - 2) {
				name += ", ";
			} else if (i == rups.length - 2) {
				name += ", and ";
			}
		}
		mergedRup.setName(name);
		//merge forces
		DynamicArray<Force> forces = new DynamicArray(new Force[0]);
		DynamicArray<Special> specials = new DynamicArray(new Special[0]);
		DynamicArray<Artifact> artifacts = new DynamicArray(new Artifact[0]);
		for (int i = 0; i < rups.length; i++) {
			forces.add(rups[i].forces());
			specials.add(rups[i].specials());
			artifacts.add(rups[i].artifacts());
		}
		forces.trim(); specials.trim(); artifacts.trim();
		mergedRup.setForces(forces.storage());
		mergedRup.setSpecials(specials.storage());
		mergedRup.setArtifacts(artifacts.storage());
		return mergedRup;
	}

	//set things
	public void setName(String n) {
		this.name = n;
	}
	public void setForces(Force[] f) {
		this.forces = f;
	}
	public void setSpecials(Special[] s) {
		this.specials = s;
	}
	public void setArtifacts(Artifact[] a) {
		this.artifacts = a;
	}
	public void setDependencies(String[] deps) {
		this.dependencies = deps;
	}

	//return things
	public String name() {
		return this.name;
	}
	public Force[] forces() {
		return this.forces;
	}
	public Force forces(int i) {
		return this.forces[i];
	}
	public Special[] specials() {
		return this.specials;
	}
	public Special specials(int i) {
		return this.specials[i];
	}
	public Artifact[] artifacts() {
		return this.artifacts;
	}
	public Artifact artifacts(int i) {
		return this.artifacts[i];
	}
	public String[] dependencies() {
		return this.dependencies;
	}
	public String dependencies(int i) {
		return this.dependencies[i];
	}



}