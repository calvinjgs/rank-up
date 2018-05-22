package rup.datasrc;

public class Artifact extends Special {

	private String pts;

	public Artifact() {
		super();
	}

	public Artifact(String name) {
		super(name);
	}

	//set things
	public void setPts(String pts) {
		this.pts = pts;
	}
	//return things
	public String pts() {
		return this.pts;
	}
	public Flavour asFlavour() {
		Flavour flave = new Flavour();
		SpecialRef[] specs = new SpecialRef[1];
		specs[0] = new SpecialRef();
		specs[0].setSpecName(this.name());
		specs[0].match(this);
		flave.setAddSpecials(specs);
		flave.setPts(this.pts());
		return flave;
	}
}

