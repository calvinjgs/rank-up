package rup.datasrc;
import rup.tools.*;

public class RUPackage {

	private String name;
	private String[] dependencies;
	private Force[] forces;
	private Special[] specials;
	private Artifact[] artifacts;

	private UnitUpdate[] unitUpdates;
	private boolean updatesApplied;

	public RUPackage() {
		this.name = "unnamed package";
		this.dependencies = new String[0];
		this.forces = new Force[0];
		this.specials = new Special[0];
		this.artifacts = new Artifact[0];

		this.unitUpdates = new UnitUpdate[0];
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
		//merge forces
		DynamicArray<Force> forces = new DynamicArray(new Force[0]);
		DynamicArray<Special> specials = new DynamicArray(new Special[0]);
		DynamicArray<Artifact> artifacts = new DynamicArray(new Artifact[0]);
		DynamicArray<UnitUpdate> unitUpdates = new DynamicArray(new UnitUpdate[0]);

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

		}
		forces.trim(); specials.trim(); artifacts.trim();
		mergedRup.setForces(forces.storage());
		mergedRup.setSpecials(specials.storage());
		mergedRup.setArtifacts(artifacts.storage());
		mergedRup.setUnitUpdates(unitUpdates.storage());

		return mergedRup;
	}

	private static void mergeForcesBintoA(Force forceA, Force forceB) {
		DynamicArray<Unit> uda = new DynamicArray(forceA.units());
		uda.add(forceB.units());
		uda.trim();
		forceA.setUnits(uda.storage());
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
						applyUnitUpdate(ua.unit(), ua);
						break;
					}
				}
			}
		}

		//TODO: update artefacts, fomations and specials.

		this.updatesApplied = true;
	}

	private static void applyUnitUpdate(Unit u, UnitUpdate uu) {

		if (uu.removeSizes().length > 0) {
			//remove sizes from existing sizes
			DynamicArray<UnitSize> temp = new DynamicArray(u.sizes());
			for (int j = 0; j < uu.removeSizes().length; j++) {
				for (int i = 0; i < temp.size(); i++) {
					if (temp.storage(i).name().equals(uu.removeSizes(j).name())) {
						temp.remove(i);
					}
				}
			}
			temp.trim();
			u.setSizes(temp.storage());
		}
		if (uu.editSizes().length > 0) {
			//edit sizes from existing sizes
			DynamicArray<UnitSize> temp = new DynamicArray(u.sizes());
			for (int j = 0; j < uu.editSizes().length; j++) {
				for (int i = 0; i < temp.size(); i++) {
					if (temp.storage(i).name().equals(uu.editSizes(j).name())) {
						UnitSize thisize = temp.storage(i);
						UnitSize updsize = uu.editSizes(j);
						if (!"".equals(updsize.sp())) thisize.setSp(updsize.sp());
						if (!"".equals(updsize.me())) thisize.setMe(updsize.me());
						if (!"".equals(updsize.ra())) thisize.setRa(updsize.ra());
						if (!"".equals(updsize.de())) thisize.setDe(updsize.de());
						if (!"".equals(updsize.att())) thisize.setAtt(updsize.att());
						if (!"".equals(updsize.neW())) thisize.setNeW(updsize.neW());
						if (!"".equals(updsize.neR())) thisize.setNeR(updsize.neR());
						if (!"".equals(updsize.pts())) thisize.setPts(updsize.pts());
					}
				}
			}

		}
		if (uu.addSizes().length > 0) {
			//add sizes to existing sizes
			DynamicArray<UnitSize> temp = new DynamicArray(u.sizes());
			temp.add(uu.addSizes());
			temp.trim();
			u.setSizes(temp.storage());
		}



		if (uu.removeSpecials().length > 0) {
			//remove specials from existing specials
			DynamicArray<SpecialRef> temp = new DynamicArray(u.specials());
			for (int j = 0; j < uu.removeSpecials().length; j++) {
				for (int i = 0; i < temp.size(); i++) {
					if (temp.storage(i).specName().equals(uu.removeSpecials(j).specName())) {
						temp.remove(i);
					}
				}
			}
			temp.trim();
			u.setSpecials(temp.storage());
		}
		if (uu.addSpecials().length > 0) {
			//add specials to existing specials
			DynamicArray<SpecialRef> temp = new DynamicArray(u.specials());
			temp.add(uu.addSpecials());
			temp.trim();
			u.setSpecials(temp.storage());
		}

		if (uu.removeOptions().length > 0) {
			//remove options from existing options
			DynamicArray<UnitOption> temp = new DynamicArray(u.options());
			for (int j = 0; j < uu.removeOptions().length; j++) {
				for (int i = 0; i < temp.size(); i++) {
					if (temp.storage(i).toString().equals(uu.removeOptions(j).toString())) {
						temp.remove(i);
					}
				}
			}
			temp.trim();
			u.setOptions(temp.storage());
		}
		//TODO apply editOptions
		if (uu.addOptions().length > 0) {
			//add options to existing options
			DynamicArray<UnitOption> temp = new DynamicArray(u.options());
			temp.add(uu.addOptions());
			temp.trim();
			u.setOptions(temp.storage());
		}


		if (uu.type() != null) {
			u.setType(uu.type());
		}


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



}