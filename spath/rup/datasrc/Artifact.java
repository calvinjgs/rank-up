package rup.datasrc;

public class Artifact extends Special {

	private String pts;

	public Artifact() {
		super();
	}

	public Artifact(String name) {
		super(name);
	}

	public Artifact clone() {
		Artifact clone = new Artifact();
		clone.setName(this.name());
		clone.setDescription(this.description());
		clone.setPts(this.pts());
		return clone;
	}

	public void applyUpdate(ArtifactUpdate aup) {
		if (!aup.newName().equals("")) {
			this.setName(aup.newName());
		}
		if (!aup.newDescription().equals("")) {
			this.setDescription(aup.newDescription());
		}
		if (!aup.newPts().equals("")) {
			this.setPts(aup.newPts());
		}
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

