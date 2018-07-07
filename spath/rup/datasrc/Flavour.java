package rup.datasrc;

import rup.tools.*;

public class Flavour {

	private static final String STAT_PTRN = "^(\\d+|[-—])$";

	private String name;
	private RUData.UNIT_TYPE type;
	private SpecialRef[] addSpecials;
	private SpecialRef[] removeSpecials;
	private String sp, me, ra, de, att, neW, neR, pts;
	private String description;

	public Flavour(String n) {
		this.name = n;
		this.type = null;
		sp = me = ra = de = att = neW = neR = pts = "";
		this.addSpecials = new SpecialRef[0];
		this.removeSpecials = new SpecialRef[0];
		this.description = "";
	}
	public Flavour() {
		this("");
	}

	public Flavour clone() {
		Flavour clone = new Flavour();
		clone.setName(new String(this.name()));
		clone.setType(this.type());
		//Elements in these arrays are not cloned.
		//Haven't decided if it is important or not.
		clone.setAddSpecials(this.addSpecials());
		clone.setRemoveSpecials(this.removeSpecials());
		clone.setDescription(this.description());
		clone.setSp(this.sp());
		clone.setMe(this.me());
		clone.setRa(this.ra());
		clone.setDe(this.de());
		clone.setAtt(this.att());
		clone.setNeW(this.neW());
		clone.setNeR(this.neR());
		clone.setPts(this.pts());
		return clone;
	}

	public String toString() {
		return this.name;
	}

	//set things
	public void setName(String n) {
		this.name = n;
	}
	public void setType(RUData.UNIT_TYPE t) {
		this.type = t;
	}
	public void setAddSpecials(SpecialRef[] as) {
		this.addSpecials = as;
	}
	public void setRemoveSpecials(SpecialRef[] rs) {
		this.removeSpecials = rs;
	}
	public void setSp(String s) {
		if (s == null) this.sp = "";
		else this.sp = s;
	}
	public void setMe(String s) {
		if (s == null) this.me = "";
		else this.me = s;
	}
	public void setRa(String s) {
		if (s == null) this.ra = "";
		else this.ra = s;
	}
	public void setDe(String s) {
		if (s == null) this.de = "";
		else this.de = s;
	}
	public void setAtt(String s) {
		if (s == null) this.att = "";
		else this.att = s;
	}
	public void setNeW(String s) {
		if (s == null) this.neW = "";
		else this.neW = s;
	}
	public void setNeR(String s) {
		if (s == null) this.neR = "";
		else this.neR = s;
	}
	public void setPts(String s) {
		if (s == null) this.pts = "";
		else this.pts = s;
	}
	public void setDescription(String s) {
		if (s == null) this.description = "";
		else this.description = s;
	}
	//return things
	public String name() {
		return this.name;
	}
	public String description() {
		return this.description;
	}
	public RUData.UNIT_TYPE type() {
		return this.type;
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
}