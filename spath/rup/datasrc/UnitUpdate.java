package rup.datasrc;

import rup.tools.*;

public class UnitUpdate extends Reference<Unit> {

	private String forceName;
	private String newUnitName;
	private RUData.UNIT_TYPE newUnitType;

	private String unitName;

	private UnitSize[] addSizes;
	private UnitSize[] editSizes;
	private UnitSize[] removeSizes;
	private SpecialRef[] addSpecials;
	private SpecialRef[] removeSpecials;
	private UnitOption[] addOptions;
	private UnitOption[] editOptions;//not implemented yet.
	private UnitOption[] removeOptions;

	public UnitUpdate() {
		super();
		this.newUnitName = "";
		this.unitName = "";
		this.addSizes = new UnitSize[0];
		this.editSizes = new UnitSize[0];
		this.removeSizes = new UnitSize[0];
		this.addSpecials = new SpecialRef[0];
		this.removeSpecials = new SpecialRef[0];
		this.addOptions = new UnitOption[0];
		this.editOptions = new UnitOption[0];
		this.removeOptions = new UnitOption[0];
	}

	//set things



	public void setUnit(Unit unit) {
		super.setObject(unit);
	}

	public void setForceName(String fn) {
		this.forceName = fn;
	}

	public void setUnitName(String un) {
		super.setObjName(un);
	}
/*
	public void setUnitName(String un) {
		this.setUnitName(un);
	}
*/
	public void setNewUnitName(String un) {
		this.newUnitName = un;
	}
	public void setAddSizes(UnitSize[] uss) {
		this.addSizes = uss;
	}
	public void setEditSizes(UnitSize[] uss) {
			this.editSizes = uss;
	}
	public void setRemoveSizes(UnitSize[] uss) {
		this.removeSizes = uss;
	}
	public void setAddSpecials(SpecialRef[] srs) {
		this.addSpecials = srs;
	}
	public void setRemoveSpecials(SpecialRef[] srs) {
		this.removeSpecials = srs;
	}
	public void setAddOptions(UnitOption[] uos) {
		this.addOptions = uos;
	}
	public void setEditOptions(UnitOption[] uos) {
		this.editOptions = uos;
	}
	public void setRemoveOptions(UnitOption[] uos) {
		this.removeOptions = uos;
	}
	public void setType(RUData.UNIT_TYPE nut) {
		this.newUnitType = nut;
	}


	//return things
	public String forceName() {
		return this.forceName;
	}

	public Unit unit() {
		return super.object();
	}

	public Unit unit(Unit[] units) {
		return super.object(units);
	}

	public String newUnitName() {
		return this.newUnitName;
	}
	public String unitName() {
		return super.objName();
	}
	public UnitSize[] addSizes() {
		return this.addSizes;
	}
	public UnitSize addSizes(int i) {
		return this.addSizes[i];
	}
	public UnitSize[] editSizes() {
		return this.editSizes;
	}
	public UnitSize editSizes(int i) {
		return this.editSizes[i];
	}
	public UnitSize[] removeSizes() {
		return this.removeSizes;
	}
	public UnitSize removeSizes(int i) {
		return this.removeSizes[i];
	}

	public SpecialRef[] addSpecials() {
		return this.addSpecials;
	}
	public SpecialRef addSpecials(int i) {
		return this.addSpecials[i];
	}
	public SpecialRef[] removeSpecials() {
		return this.removeSpecials;
	}
	public SpecialRef removeSpecials(int i) {
		return this.removeSpecials[i];
	}

	public UnitOption[] addOptions() {
		return this.addOptions;
	}
	public UnitOption addOptions(int i) {
		return this.addOptions[i];
	}
	public UnitOption[] editOptions() {
		return this.editOptions;
	}
	public UnitOption editOptions(int i) {
		return this.editOptions[i];
	}
	public UnitOption[] removeOptions() {
		return this.removeOptions;
	}
	public UnitOption removeOptions(int i) {
		return this.removeOptions[i];
	}

	public RUData.UNIT_TYPE type() {
		return this.newUnitType;
	}

}