package rup.rusrc;

import rup.datasrc.*;
import rup.tools.*;

//List of selected units with a points total
public class Army {
	private int pointsTotal;
	private int pointsTarget;

	private String[] packagesUsed;
	private DynamicArray<DynamicArray<ArmyElement>> detachments;
	private DynamicArray<Force> forces;

	public Army() {
		this.detachments = new DynamicArray(new DynamicArray[0]);
		this.forces = new DynamicArray(new Force[0]);
	}

	public void removeUnit(ArmyElement unit) {
		for (int d = 0; d < this.detachments.size(); d++) {
			DynamicArray<ArmyElement> det = this.detachments.storage(d);
			for (int u = 0; u < this.detachments.storage(d).size(); u++) {
				if (det.storage(u) == unit) {
					det.remove(u);
					//remove from this.forces if last unit in force
				if (this.detachments.storage(d).size() == 0) {
					this.detachments.remove(d);
					this.forces.remove(d);
				}
					break;
				}
			}
		}
	}

	public void addUnit(ArmyElement u) {
		boolean foundDet = false;
		for (int d = 0; d < this.detachments.size(); d++) {
			if (this.detachments.storage(d).size() > 0) {
				foundDet = u.force() == this.detachments.storage(d).storage(0).force();
				if (foundDet) {
					//add unit to existing detachment
					this.detachments.storage(d).add(u);
					break;
				}
			}
		}
		if (!foundDet) {
			//create new detachment and add unit to that
			this.detachments.add(new DynamicArray(u));
			this.forces.add(u.force());
		}
	}

	//set things
	public void setPackagesUsed(String[] rups) {
		this.packagesUsed = rups;
	}

	//return things
	public ArmyElement[][] detachments() {
		this.detachments.trim();//make sure array is proper size
		DynamicArray<ArmyElement>[]  detsA = this.detachments.storage();
		ArmyElement[][] dets = new ArmyElement[detsA.length][];
		for (int d = 0; d < dets.length; d++) {
			DynamicArray<ArmyElement> detdDA = detsA[d];
			dets[d] = new ArmyElement[detdDA.size()];
			for (int u = 0; u < detdDA.size(); u++) {
				dets[d][u] = detdDA.storage(u);
			}
		}
		return dets;
	}

	public Force[] forces() {
		DynamicArray<Force> fcs = this.forces;
		fcs.trim();//ditto
		return fcs.storage();
	}

	public Force forces(int i) {
		return forces()[i];
	}

	public String[] packagesUsed() {
		return this.packagesUsed;
	}
	public String packagesUsed(int i) {
		return this.packagesUsed[i];
	}

}