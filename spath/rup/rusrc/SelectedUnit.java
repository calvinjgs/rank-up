package rup.rusrc;

import rup.tools.*;
import rup.datasrc.*;

import java.util.*;
//Selected unit for an army list. Includes all options and modifiers.
public class SelectedUnit extends ArmyElement {

	private Force force;
	private Unit unit;
	private String name;
	private RUData.UNIT_TYPE type;
	private String sizeName;
	private int sizeIndex;
	private String sp, me, ra, de, att, neW, neR, pts;
	private SpecialRef[] specials;
	private int[] selectedOptions;
	private Artifact artefact;

	public SelectedUnit() {
		this.name = "";
		this.type = RUData.UNIT_TYPE.INFANTRY;
		this.sizeName = "";
		this.sizeIndex = 0;
		sp = me = ra = de = att = neW = neR = pts = "-1";
		this.specials = new SpecialRef[0];
	}

	public SelectedUnit copy() {
		SelectedUnit su = new SelectedUnit();
		su.setUnit(this.unit());
		su.setSize(this.sizeIndex);
		su.setForce(this.force());
		su.setSpecials(Arrays.copyOf(this.unit.specials(), this.unit.specials().length));
		//copy of selectedOptions
		int[] cp = new int[this.selectedOptions.length];
		for (int i = 0; i < cp.length; i++) {
			cp[i] = this.selectedOptions[i];
		}
		su.setSelectedOptions(cp);
		su.setArtefact(this.artefact);
		//reapply options
		su.reapply();

		return su;
	}



	//revert to original unit stats
	public void reapply() {
		this.specials = Arrays.copyOf(this.unit.specials(), this.unit.specials().length);
		this.type = this.unit.type();
		setSize(this.sizeIndex);
		applyOptions();
		applyArtefact();
	}

	public void initializeSelectedOptions(Unit u) {
		this.selectedOptions = new int[u.options().length];
		for (int i = 0; i < this.selectedOptions.length; i++) {
			this.selectedOptions[i] = -1;
		}
	}

	public void applyOptions() {
		for (int i = 0; i < this.selectedOptions.length; i++) {
			if (this.selectedOptions[i] != -1) {
				this.apply(this.unit.options(i, this.selectedOptions[i]));
			}
		}
	}

	public void applyArtefact() {
		if (this.artefact != null) {
			this.apply(this.artefact.asFlavour());
		}
	}


	public void apply(Flavour uo) {
		if (uo.removeSpecials().length > 0) {
			//remove specials from existing specials
			DynamicArray<SpecialRef> temp = new DynamicArray(this.specials());
			for (int j = 0; j < uo.removeSpecials().length; j++) {
				for (int i = 0; i < temp.size(); i++) {
					if (temp.storage(i).specName().equals(uo.removeSpecials(j).specName())) {
						temp.remove(i);
					}
				}
			}
			temp.trim();
			this.setSpecials(temp.storage());
		}
		if (uo.addSpecials().length > 0) {
			//add specials to existing specials
			DynamicArray<SpecialRef> temp = new DynamicArray(this.specials());
			temp.add(uo.addSpecials());
			temp.trim();
			this.setSpecials(temp.storage());
		}
		if (uo.type() != null) {
			this.setType(uo.type());
		}

		if (!"".equals(uo.sp())) this.setSp(uo.sp());
		if (!"".equals(uo.me())) this.setMe(uo.me());
		if (!"".equals(uo.ra())) this.setRa(uo.ra());
		if (!"".equals(uo.de())) this.setDe(uo.de());
		if (!"".equals(uo.att())) this.setAtt(uo.att());
		if (!"".equals(uo.neW())) this.setNeW(uo.neW());
		if (!"".equals(uo.neR())) this.setNeR(uo.neR());

		if (uo.pts().matches("^\\d+$")) {
			int newPts = Integer.parseInt(this.pts()) + Integer.parseInt(uo.pts());
			this.setPts(Integer.toString(newPts));
		}


	}


	//set things
	public void setName(String s) {
		this.name = s;
	}
	public void setType(RUData.UNIT_TYPE t) {
		this.type = t;
	}
	public void setSizeName(String us) {
		this.sizeName = us;
	}
	public void setSp(String s) {
		this.sp = s;
	}
	public void setMe(String s) {
		this.me = s;
	}
	public void setRa(String s) {
		this.ra = s;
	}
	public void setDe(String s) {
		this.de = s;
	}
	public void setAtt(String s) {
		this.att = s;
	}
	public void setNeW(String s) {
		this.neW = s;
	}
	public void setNeR(String s) {
		this.neR = s;
	}
	public void setPts(String s) {
		this.pts = s;
	}

	public void setForce(Force f) {
		this.force = f;
	}
	public void setUnit(Unit u) {
		this.unit = u;
		this.force = u.force();
		this.name = this.unit.name();
		this.type = this.unit.type();
		setSize(0);
		this.specials = Arrays.copyOf(this.unit.specials(), this.unit.specials().length);
		initializeSelectedOptions(this.unit);
	}
	public void setSize(int index) {
		this.sizeIndex = index;
		this.sizeName = this.unit.sizes(sizeIndex).name().toString();
		this.sp = this.unit.sizes(sizeIndex).sp();
		this.me = this.unit.sizes(sizeIndex).me();
		this.ra = this.unit.sizes(sizeIndex).ra();
		this.de = this.unit.sizes(sizeIndex).de();
		this.att = this.unit.sizes(sizeIndex).att();
		this.neW = this.unit.sizes(sizeIndex).neW();
		this.neR = this.unit.sizes(sizeIndex).neR();
		this.pts = this.unit.sizes(sizeIndex).pts();
	}
	public void setSpecials(SpecialRef[] sp) {
		this.specials = sp;
	}

	public void setSelectedOptions(int[] b) {
		this.selectedOptions = b;
	}
	public void setSelectedOptions(int o, int f) {
		this.selectedOptions[o] = f;
	}
	public void setArtefact(Artifact art) {
		this.artefact = art;
	}


	//return things
	public String name() {
		return this.name;
	}
	public RUData.UNIT_TYPE type() {
		return this.type;
	}
	public String sizeName() {
		return this.sizeName;
	}
	public int sizeIndex() {
		return this.sizeIndex;
	}
	public String sp() {
		return this.sp;
	}
	public String me() {
		return this.me;
	}
	public String ra() {
		return this.ra;
	}
	public String de() {
		return this.de;
	}
	public String att() {
		return this.att;
	}
	public String neW() {
		return this.neW;
	}
	public String neR() {
		return this.neR;
	}
	public String pts() {
		return this.pts;
	}
	public Force force() {
		return this.force;
	}
	public Unit unit() {
		return this.unit;
	}
	public SpecialRef[] specials() {
		return this.specials;
	}
	public SpecialRef specials(int i) {
		return this.specials[i];
	}
	public int[] selectedOptions() {
		return this.selectedOptions;
	}
	public int selectedOptions(int j) {
		return this.selectedOptions[j];
	}
	public Artifact artefact() {
		return this.artefact;
	}

}