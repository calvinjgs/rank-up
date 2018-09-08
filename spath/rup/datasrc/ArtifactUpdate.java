package rup.datasrc;

import rup.tools.*;

public class ArtifactUpdate extends Reference<Artifact> {

	private String newArtifactName;
	private String newDescription;
	private String newPts;

	public ArtifactUpdate() {
		super();
		this.newArtifactName = "";
		this.newDescription = "";
		this.newPts = "";
	}

	//set things
	public void setArtifact(Artifact art) {
		super.setObject(art);
	}
	public void setName(String n) {
		super.setObjName(n);
	}
	public void setNewName(String n) {
		this.newArtifactName = n;
	}
	public void setNewDescription(String d) {
		this.newDescription = d;
	}
	public void setNewPts(String p) {
		this.newPts= p;
	}

	//return things
	public Artifact artifact() {
		return super.object();
	}
	public Artifact artifact(Artifact[] arties) {
		return super.object(arties);
	}
	public String newName() {
		return this.newArtifactName;
	}
	public String newDescription() {
		return this.newDescription;
	}
	public String newPts() {
		return this.newPts;
	}

}