package rup.rusrc;

import java.util.*;

import rup.datasrc.*;
import rup.tools.*;


public class ArmyRequirements {

	private int pointsMax; //maximum allowed points
	private double alliedPercent; //percentage of army allowed as allies
	private int alliedPointsMax;

	//maximum specific units (each detachment gets one set of these)
	private int[] troopMax;
	private int[] wildMax;
	private int[] warEngMax;
	private int[] monMax;
	private int[] heroMax;

	//current values of above
	private int pointsCurrent;
	private double alliedPercentCurrent;
	private int alliedPointsCurrent;
	private int[] troopCurrent;
	private int[] wildCurrent;
	private int[] warEngCurrent;
	private int[] monCurrent;
	private int[] heroCurrent;

	private boolean validAlignment;

	public ArmyRequirements() {

		resetValues();
	}

	public void resetValues() {
		this.alliedPercent = 25;
		troopMax = wildMax = warEngMax = monMax = heroMax = null;
		alliedPercentCurrent = 0.0;
		alliedPointsCurrent = 0;
		pointsCurrent = 0;
		troopCurrent = wildCurrent = warEngCurrent = monCurrent = heroCurrent = null;
	}

	public void buildRequirements(ArmyElement[][] dets) {
		resetValues();
		troopMax = new int[dets.length];
		wildMax = new int[dets.length];
		warEngMax = new int[dets.length];
		monMax = new int[dets.length];
		heroMax = new int[dets.length];
		troopCurrent = new int[dets.length];
		wildCurrent = new int[dets.length];
		warEngCurrent = new int[dets.length];
		monCurrent = new int[dets.length];
		heroCurrent = new int[dets.length];
		boolean isEvil, isGood;
		isEvil = isGood = false;
		for (int i = 0; i < dets.length; i++) {
			for(int j = 0; j < dets[i].length; j++) {
				if (dets[i][j] instanceof SelectedUnit) {
					SelectedUnit unit = (SelectedUnit) dets[i][j];
					//set alignment flags
					isEvil = isEvil || unit.force().alignment() == RUData.ALIGNMENT.EVIL;
					isGood = isGood || unit.force().alignment() == RUData.ALIGNMENT.GOOD;
					//reqs from unit
					//use some points
					pointsCurrent += Integer.parseInt(unit.pts());
					//set irregular boolean
					boolean isIrregular = unit.name().endsWith("*");
					//increase maximums for regiment
					if (unit.sizeName().toLowerCase().equals(RUData.UNIT_SIZE.REGIMENT.toString()) && !isIrregular) {

						troopMax[i] += 2;
						wildMax[i] += 1;

					}
					//increase maximums for horde or legion
					if ((unit.sizeName().toLowerCase().equals(RUData.UNIT_SIZE.HORDE.toString()) ||
						unit.sizeName().toLowerCase().equals(RUData.UNIT_SIZE.LEGION.toString())) && !isIrregular) {
							troopMax[i] += 4;
							warEngMax[i] += 1;
							monMax[i] += 1;
							heroMax[i] += 1;
					}
					//use troop slot
					if (unit.sizeName().toLowerCase().equals(RUData.UNIT_SIZE.TROOP.toString()) || isIrregular)
						troopCurrent[i]++;
					//use war engine slot
					if (unit.type() == RUData.UNIT_TYPE.WAR_ENGINE && !isIrregular)
						warEngCurrent[i]++;
					//use monster slot
					if (unit.type() == RUData.UNIT_TYPE.MONSTER && !isIrregular)
						monCurrent[i]++;
					//use hero slot (keep an eye on this, using ordinal() is dangerous)
					if (unit.type().ordinal() >= RUData.UNIT_TYPE.HERO_INFANTRY.ordinal() && !isIrregular)
						heroCurrent[i]++;
				} else if (dets[i][j] instanceof Formation) {
					Formation formation = (Formation) dets[i][j];
					//use some points
					pointsCurrent += Integer.parseInt(formation.pts());
				}

			}
			//fill wild slots using the other slots
			while (warEngCurrent[i] > warEngMax[i]) {
				wildCurrent[i] += 1;
				warEngCurrent[i] -= 1;

			}
			while (monCurrent[i] > monMax[i]) {
				wildCurrent[i] += 1;
				monCurrent[i] -= 1;
			}
			while (heroCurrent[i] > heroMax[i]) {
				wildCurrent[i] += 1;
				heroCurrent[i] -= 1;
			}


		}
		calculateAlliedPoints(dets);
		//set alignment validity
		validAlignment = !(isEvil && isGood);

	}

	private void calculateAlliedPercent(ArmyElement[][] dets) {
		if (dets.length > 1) {
			double alliedPoints = 0.0;
			for (int i = 1; i < dets.length; i++) {
				for (int j = 0; j < dets[i].length; j++) {
					//use allied percentage
					alliedPoints += Double.parseDouble(dets[i][j].pts());
				}
			}
			alliedPercentCurrent = (alliedPoints/((double) pointsMax))*100.0;
		} else {
			alliedPercentCurrent = 1.0/0.0;
		}
	}

	private void calculateAlliedPoints(ArmyElement[][] dets) {
		if (dets.length > 1) {
			int alliedPoints = 0;
			for (int i = 1; i < dets.length; i++) {
				for (int j = 0; j < dets[i].length; j++) {
					//use allied percentage
					alliedPoints += Integer.parseInt(dets[i][j].pts());
				}
			}
			alliedPointsCurrent = alliedPoints;
		} else {
			alliedPointsCurrent = 0;
		}

	}

	//validation
	public boolean validAlignment() {
		return validAlignment;
	}
	public boolean validPoints() {
		return pointsCurrent <= pointsMax;
	}
	public boolean validAlliedPercent() {
		return alliedPercentCurrent <= alliedPercent;
	}
	public boolean validTroop() {
		boolean valid = true;
		for (int i = 0; i < troopMax.length; i++) {
			valid = valid && troopCurrent[i] <= troopMax[i];
		}
		return valid;
	}
	public boolean validWild() {
		boolean valid = true;
		for (int i = 0; i < wildMax.length; i++) {
			valid = valid && wildCurrent[i] <= wildMax[i];
		}
		return valid;
	}
	public boolean validWarEng() {
		boolean valid = true;
		for (int i = 0; i < warEngMax.length; i++) {
			valid = valid && warEngCurrent[i] <= warEngMax[i];
		}
		return valid;
	}
	public boolean validMon() {
		boolean valid = true;
		for (int i = 0; i < monMax.length; i++) {
			valid = valid && monCurrent[i] <= monMax[i];
		}
		return valid;
	}
	public boolean validHero() {
		boolean valid = true;
		for (int i = 0; i < heroMax.length; i++) {
			valid = valid && heroCurrent[i] <= heroMax[i];
		}
		return valid;
	}


	//set things
	public void setPointsMax(int v) {
		this.pointsMax = v;
		this.alliedPointsMax = (int) (pointsMax*(alliedPercent/100.0));
	}
	public void setAlliedPercent(double v) {
		this.alliedPercent = v;
	}
	public void setPointsCurrent(int v) {
		this.pointsCurrent = v;
	}
	public void setAlliedPercentCurrent(double v) {
		this.alliedPercentCurrent = v;
	}




	//return things
	public int pointsMax() {
		return this.pointsMax;
	}
	public double alliedPercent() {
		return this.alliedPercent;
	}
	public int alliedPointsMax() {
		return this.alliedPointsMax;
	}
	public int[] troopMax() {
		return this.troopMax;
	}
	public int[] wildMax() {
		return this.wildMax;
	}
	public int[] warEngMax() {
		return this.warEngMax;
	}
	public int[] monMax() {
		return this.monMax;
	}
	public int[] heroMax() {
		return this.heroMax;
	}
	public int troopMax(int i) {
		return this.troopMax[i];
	}
	public int wildMax(int i) {
		return this.wildMax[i];
	}
	public int warEngMax(int i) {
		return this.warEngMax[i];
	}
	public int monMax(int i) {
		return this.monMax[i];
	}
	public int heroMax(int i) {
		return this.heroMax[i];
	}


	public int pointsCurrent() {
		return this.pointsCurrent;
	}
	public double alliedPercentCurrent() {
		return this.alliedPercentCurrent;
	}
	public int alliedPointsCurrent() {
		return this.alliedPointsCurrent;
	}
	public int[] troopCurrent() {
		return this.troopCurrent;
	}
	public int[] wildCurrent() {
		return this.wildCurrent;
	}
	public int[] warEngCurrent() {
		return this.warEngCurrent;
	}
	public int[] monCurrent() {
		return this.monCurrent;
	}
	public int[] heroCurrent() {
		return this.heroCurrent;
	}
	public int troopCurrent(int i) {
		return this.troopCurrent[i];
	}
	public int wildCurrent(int i) {
		return this.wildCurrent[i];
	}
	public int warEngCurrent(int i) {
		return this.warEngCurrent[i];
	}
	public int monCurrent(int i) {
		return this.monCurrent[i];
	}
	public int heroCurrent(int i) {
		return this.heroCurrent[i];
	}

}