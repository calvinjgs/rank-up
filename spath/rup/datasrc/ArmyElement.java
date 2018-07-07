package rup.datasrc;

import rup.tools.*;
//anything that can be purchased at the army scope;
//so basically just SelectedUnits and Formations.
public abstract class ArmyElement {
	public abstract void setForce(Force f);
	public abstract Force force();
	public abstract void setName(String n);
	public abstract String name();
	public abstract void setPts(String n);
	public abstract String pts();

}