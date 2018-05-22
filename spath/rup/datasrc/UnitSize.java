package rup.datasrc;

import rup.tools.*;

public class UnitSize {

	private RUData.UNIT_SIZE name;
	private String sp, me, ra, de, att, neW, neR, pts;


	public UnitSize() {
		this.name = RUData.UNIT_SIZE.REGIMENT;
		sp = me = ra = de = att = neW = neR = pts = "";
	}

	public String toString() {
		return this.name.toString();
	}

	//set things
	public void setName(RUData.UNIT_SIZE s) {
		this.name = s;
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

	//return things
	public RUData.UNIT_SIZE name() {
		return this.name;
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