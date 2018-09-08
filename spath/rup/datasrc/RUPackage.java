package rup.datasrc;
import rup.tools.*;

public class RUPackage {

	private String name;
	private String[] dependencies;
	private Force[] forces;
	private Special[] specials;
	private Artifact[] artifacts;

	private UnitUpdate[] unitUpdates;
	private SpecialUpdate[] specialUpdates;
	private ArtifactUpdate[] artifactUpdates;
	private boolean updatesApplied;

	public RUPackage() {
		this.name = "unnamed package";
		this.dependencies = new String[0];
		this.forces = new Force[0];
		this.specials = new Special[0];
		this.artifacts = new Artifact[0];

		this.unitUpdates = new UnitUpdate[0];
		this.specialUpdates = new SpecialUpdate[0];
		this.artifactUpdates = new ArtifactUpdate[0];
		this.updatesApplied = false;
	}

	public String toString() {
		return this.name;
	}


	//merges multiple packages
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

		DynamicArray<Force> forces = new DynamicArray(new Force[0]);
		DynamicArray<Special> specials = new DynamicArray(new Special[0]);
		DynamicArray<Artifact> artifacts = new DynamicArray(new Artifact[0]);
		DynamicArray<UnitUpdate> unitUpdates = new DynamicArray(new UnitUpdate[0]);
		DynamicArray<SpecialUpdate> specialUpdates = new DynamicArray(new SpecialUpdate[0]);
		DynamicArray<ArtifactUpdate> artifactUpdates = new DynamicArray(new ArtifactUpdate[0]);

		for (int i = 0; i < rups.length; i++) {

			//merge forces
			for (int rf = 0; rf < rups[i].forces().length; rf++) {
				Force thisForce = rups[i].forces(rf);
				//merge one force into the other if they have the same name
				boolean forceMerged = false;
				for (int f = 0; f < forces.size(); f++) {
					if (forces.storage(f).name().equals(thisForce.name())) {
						mergeForcesBintoA(forces.storage(f), thisForce);
						forceMerged = true;
						break;
					}
				}
				if (!forceMerged) forces.add(thisForce);
			}

			//merge other stuff.
			specials.add(rups[i].specials());
			artifacts.add(rups[i].artifacts());
			unitUpdates.add(rups[i].unitUpdates());
			specialUpdates.add(rups[i].specialUpdates());
			artifactUpdates.add(rups[i].artifactUpdates());

		}
		forces.trim(); specials.trim(); artifacts.trim();
		mergedRup.setForces(forces.storage());
		mergedRup.setSpecials(specials.storage());
		mergedRup.setArtifacts(artifacts.storage());
		mergedRup.setUnitUpdates(unitUpdates.storage());
		mergedRup.setSpecialUpdates(specialUpdates.storage());
		mergedRup.setArtifactUpdates(artifactUpdates.storage());

		return mergedRup;
	}

	private static void mergeForcesBintoA(Force forceA, Force forceB) {
		DynamicArray<Unit> uda = new DynamicArray(forceA.units());
		uda.add(forceB.units());
		uda.trim();
		forceA.setUnits(uda.storage());
		DynamicArray<Formation> fda = new DynamicArray(forceA.formations());
		fda.add(forceB.formations());
		fda.trim();
		forceA.setFormations(fda.storage());

	}

	public void applyUpdates() {
		if (this.updatesApplied) return;

		//update units
		for (int i = 0; i < this.unitUpdates.length; i++) {
			UnitUpdate ua = this.unitUpdates[i];
			//find appropriate unit
			for (int j = 0; j < this.forces.length; j++) {
				if (ua.forceName().equals(this.forces[j].name())) {
					//match and apply, or keep looking.
					if (ua.unit(this.forces[j].units()) != null) {
						ua.unit().applyUpdate(ua);
						break;
					}
				}
			}
		}

		//update specials
		for (int i = 0; i < this.specialUpdates.length; i++) {
			SpecialUpdate su = this.specialUpdates[i];
			//match and apply, or keep looking.
			if (su.special(this.specials()) != null) {
				su.special().applyUpdate(su);
			}
		}

		//update artefacts
		for (int i = 0; i < this.artifactUpdates.length; i++) {
			ArtifactUpdate au = this.artifactUpdates[i];
			//match and apply, or keep looking.
			if (au.artifact(this.artifacts()) != null) {
				au.artifact().applyUpdate(au);
			}
		}


		//TODO: update formations.

		this.updatesApplied = true;
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
	public void setUnitUpdates(UnitUpdate[] uas) {
		this.unitUpdates = uas;
	}
	public void setSpecialUpdates(SpecialUpdate[] sups) {
		this.specialUpdates = sups;
	}
	public void setArtifactUpdates(ArtifactUpdate[] sups) {
		this.artifactUpdates = sups;
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

	public UnitUpdate[] unitUpdates() {
		return this.unitUpdates;
	}
	public UnitUpdate unitUpdates(int i) {
		return this.unitUpdates[i];
	}
	public SpecialUpdate[] specialUpdates() {
		return this.specialUpdates;
	}
	public SpecialUpdate specialUpdates(int i) {
		return this.specialUpdates[i];
	}
	public ArtifactUpdate[] artifactUpdates() {
		return this.artifactUpdates;
	}
	public ArtifactUpdate artifactUpdates(int i) {
		return this.artifactUpdates[i];
	}


}