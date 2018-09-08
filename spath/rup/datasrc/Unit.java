package rup.datasrc;

import rup.tools.*;

//Unit entry in a force list. Prototype for a selected unit, before modifiers.
public class Unit implements NamedInterface {

	private String name;
	private Force force;
	private RUData.UNIT_TYPE type;
	private UnitSize[] sizes;
	private SpecialRef[] specials;
	private UnitOption[] options;

	public Unit() {
		this.name = "";
		this.type = RUData.UNIT_TYPE.INFANTRY;
		this.sizes = new UnitSize[0];
		this.specials = new SpecialRef[0];
		this.options = new UnitOption[0];
	}

	public Unit clone() {
		Unit clone = new Unit();
		clone.setName(new String(this.name));
		clone.setType(this.type);
		UnitSize[] usa = new UnitSize[this.sizes.length];
		for (int i = 0; i < this.sizes.length; i++) {
			usa[i] = this.sizes[i].clone();
		}
		clone.setSizes(usa);

		SpecialRef[] sra = new SpecialRef[this.specials.length];
		for (int i = 0; i < this.specials.length; i++) {
			sra[i] = this.specials[i].clone();
		}
		clone.setSpecials(sra);

		UnitOption[] uoa = new UnitOption[this.options.length];
		for (int i = 0; i < this.options.length; i++) {
			uoa[i] = this.options[i].clone();
		}
		clone.setOptions(uoa);

		return clone;
	}

	public void applyUpdate(UnitUpdate uup) {
		if (!uup.newUnitName().equals("")) {
			this.name = uup.newUnitName();
		}
		if (uup.type() != null) {
			this.setType(uup.type());
		}
		if (uup.removeSizes().length > 0) {
			//remove sizes from existing sizes
			DynamicArray<UnitSize> temp = new DynamicArray(this.sizes());
			for (int j = 0; j < uup.removeSizes().length; j++) {
				for (int i = 0; i < temp.size(); i++) {
					if (temp.storage(i).name() == (uup.removeSizes(j).name())) {
						temp.remove(i);
					}
				}
			}
			temp.trim();
			this.setSizes(temp.storage());
		}
		if (uup.editSizes().length > 0) {
			//edit sizes from existing sizes
			for (int j = 0; j < uup.editSizes().length; j++) {
				for (int i = 0; i < this.sizes.length; i++) {
					if (this.sizes(i).name() == (uup.editSizes(j).name())) {
						UnitSize thisSize = this.sizes(i);
						UnitSize updateSize = uup.editSizes(j);
						if (!"".equals(updateSize.sp())) thisSize.setSp(updateSize.sp());
						if (!"".equals(updateSize.me())) thisSize.setMe(updateSize.me());
						if (!"".equals(updateSize.ra())) thisSize.setRa(updateSize.ra());
						if (!"".equals(updateSize.de())) thisSize.setDe(updateSize.de());
						if (!"".equals(updateSize.att())) thisSize.setAtt(updateSize.att());
						if (!"".equals(updateSize.neW())) thisSize.setNeW(updateSize.neW());
						if (!"".equals(updateSize.neR())) thisSize.setNeR(updateSize.neR());
						if (!"".equals(updateSize.pts())) thisSize.setPts(updateSize.pts());
					}
				}
			}
		}
		if (uup.addSizes().length > 0) {
			//add sizes to existing sizes
			DynamicArray<UnitSize> temp = new DynamicArray(this.sizes());
			temp.add(uup.addSizes());
			temp.trim();
			this.setSizes(temp.storage());
		}

		if (uup.removeSpecials().length > 0) {
			//remove specials from existing specials
			DynamicArray<SpecialRef> temp = new DynamicArray(this.specials());
			for (int j = 0; j < uup.removeSpecials().length; j++) {
				for (int i = 0; i < temp.size(); i++) {
					if (temp.storage(i).specName().equals(uup.removeSpecials(j).specName())) {
						temp.remove(i);
					}
				}
			}
			temp.trim();
			this.setSpecials(temp.storage());
		}
		if (uup.addSpecials().length > 0) {
			//add specials to existing specials
			DynamicArray<SpecialRef> temp = new DynamicArray(this.specials());
			temp.add(uup.addSpecials());
			temp.trim();
			this.setSpecials(temp.storage());
		}






	}

	//set things
	public void setName(String s) {
		this.name = s;
	}
	public void setForce(Force f) {
		this.force = f;
	}
	public void setType(RUData.UNIT_TYPE t) {
		this.type = t;
	}
	public void setSizes(UnitSize[] us) {
		this.sizes = us;
	}
	public void setSpecials(SpecialRef[] spesh) {
		this.specials = spesh;
	}
	public void setOptions(UnitOption[] uo) {
		this.options = uo;
	}


	//return things
	public String name() {
		return this.name;
	}
	public Force force() {
		return this.force;
	}
	public RUData.UNIT_TYPE type() {
		return this.type;
	}
	public UnitSize[] sizes() {
		return this.sizes;
	}
	public UnitSize sizes(int index) {
		return this.sizes[index];
	}
	public SpecialRef[] specials() {
		return this.specials;
	}
	public SpecialRef specials(int i) {
		return this.specials[i];
	}
	public UnitOption[] options() {
		return this.options;
	}
	public UnitOption options(int j) {
		return this.options[j];
	}
	public Flavour options(int j, int i) {
		return this.options[j].flavours(i);
	}

	public String toString() {
		return this.name();
	}


}