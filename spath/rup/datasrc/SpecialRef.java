package rup.datasrc;

public class SpecialRef {

	private String pkgName;
	private String specName;
	private String n;
	private Special special;

	public SpecialRef(String pkgN, String specN) {
		this.pkgName = pkgN;
		this.specName = specN;
	}

	public SpecialRef() {
		this("","");
	}


	public String toString() {
		String s = this.specName;
		if (this.n != null && !this.n.equals("") && !this.n.equals("null")) s = s + " (" + this.n + ")";
		if (this.pkgName != null && !this.pkgName.equals("")) s = s + " [" + this.pkgName + "]";
		return s;
	}

	public String toStringNoPkg() {
		String s = this.specName;
		if (this.n != null && !this.n.equals("") && !this.n.equals("null")) s = s + " (" + this.n + ")";
		return s;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof SpecialRef)) return false;
		SpecialRef sr = (SpecialRef) obj;
		return this.toString().equals(sr.toString());
	}

	public boolean hasMatch() {
		return isMatch(this.special);
	}
	public boolean isMatch(Special spec) {
		return spec != null && this.specName.equals(spec.name());
	}
	public void match(Special[] specs) {
		for (int i = 0; i < specs.length; i++) {
			if (this.specName.equals(specs[i].name())) {
				this.special = specs[i];
				break;
			}
		}
	}
	public void match(Special spec) {
		Special[] specs = new Special[1];
		specs[0] = spec;
		match(specs);
	}

	//set things
	public void setPkgName(String pkgN) {
		this.pkgName = pkgN;
	}
	public void setSpecName(String specN) {
		this.specName = specN;
	}
	public void setN(String enn) {
		this.n = enn;
	}
	public void setSpecial(Special spec) {
		if (isMatch(spec)) this.special = spec;
	}

	//return things
	public String pkgName() {
		return this.pkgName;
	}
	public String specName() {
		return this.specName;
	}
	public String n() {
		if (this.n == null || this.n.equals("null")) return "";
		return this.n;
	}
	public Special special() {
		if (hasMatch())
		return this.special;
		return null;
	}
	public Special special(Special[] specs) {
		if (hasMatch()) return this.special;
		match(specs);
		return this.special;
	}

}