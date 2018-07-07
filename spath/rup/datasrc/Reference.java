package rup.datasrc;

public abstract class Reference<T extends NamedInterface> {

	private String pkgName;
	private String objName;
	private T object;

	public Reference() {
		this.pkgName = "";
		this.objName = "";
	}

	public String toString() {
		String s = this.objName;
		if (this.pkgName != null && !this.pkgName.equals("")) s = s + " [" + this.pkgName + "]";
		return s;
	}

	public String toStringNoPkg() {
		return this.objName;
	}

	public boolean equals(Object obj) {
		if (!(obj instanceof Reference)) return false;
		Reference ref = (Reference) obj;
		return this.toString().equals(ref.toString());
	}

	public boolean hasMatch() {
		return isMatch(this.object);
	}
	public boolean isMatch(T obj) {
		return obj != null && this.objName.equals(obj.name());
	}
	public void match(T[] objs) {
		for (int i = 0; i < objs.length; i++) {
			if (this.objName.equals(objs[i].name())) {
				this.object = objs[i];
				break;
			}
		}
	}
	public void match(T obj) {
		if (this.objName.equals(obj.name())) {
			this.object = obj;
		}
	}

	//set things
	public void setPkgName(String pkgN) {
		this.pkgName = pkgN;
	}
	public void setObjName(String objN) {
		this.objName = objN;
	}
	public void setObject(T obj) {
		if (isMatch(obj)) this.object = obj;
	}

	//return things
	public String pkgName() {
		return this.pkgName;
	}
	public String objName() {
		return this.objName;
	}
	public T object() {
		if (hasMatch())
		return this.object;
		return null;
	}
	public T object(T[] objs) {
		if (hasMatch()) return this.object;
		match(objs);
		return this.object;
	}

}