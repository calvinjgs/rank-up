package rup.datasrc;

import java.util.*;
import java.io.*;

import rup.tools.*;

import java.nio.file.*;
public class Compile {


	public static File getPackageDir() {
		File Pdir = new File(RUData.PACKAGES_PATH);
		if (!Pdir.exists()) {//consider throwing file not found exception.
			System.err.println(RUData.PACKAGES_PATH + " directory not found.");
		}
		return Pdir;
	}

	public static String[] getRUPackageFileNames() {
		return getRUPackageFileNames(getPackageDir());
	}

	public static String[] getRUPackageFileNames(File Pdir) {
		String[] filelist = Pdir.list();
		DynamicArray<String> Plist = new DynamicArray<String>();
		for(int i = 0; i < filelist.length; i++) {
			if (filelist[i].endsWith(RUData.RUPACKAGE_EXT))
				Plist.add(filelist[i]);
		}
		Plist.trim();
		return Plist.storage();
	}

	public static String getRUPackageFileName(String pname) {
		String[] fnames = getRUPackageFileNames();
		for(int i = 0; i < fnames.length; i++) {
			String thisfname = fnames[i];
			String thispname = getRUPackageName(fnames[i]);
			if (thispname.equals(pname)) return thisfname;
		}
		return null;
	}

	public static String[] getRUPackageNames() {
		String[] fnames = getRUPackageFileNames();
		String[] pnames = new String[fnames.length];
		for(int i = 0; i < fnames.length; i++) {
			pnames[i] = getRUPackageName(fnames[i]);
		}
		return pnames;
	}

	public static String getRUPackageName(String pfilename) {
		File Pdir = getPackageDir();
		File PL;
		PL = new File(Pdir.getPath() + RWFile.fs + pfilename);
		if (PL.canRead() && PL.isFile()) {
			Scanner scnr = RWFile.getFileScanner(Pdir.getPath() + RWFile.fs + pfilename);
			//get name
			String name = nextArg(scnr, RUData.beginName, RUData.endName);
			scnr.close();
			return name;
		}
		return null;

	}

	public static RUPackage compileRUPackage(String pfilename) {
		File Pdir = getPackageDir();
		File PL;
		PL = new File(Pdir.getPath() + RWFile.fs + pfilename);
		if (PL.canRead() && PL.isFile()) {
			Scanner scnr = RWFile.getFileScanner(Pdir.getPath() + RWFile.fs + pfilename);
			String pkgString = nextArg(scnr, RUData.beginPackage, RUData.endPackage);
			scnr.close();
			RUPackage pkg = new RUPackage();
			//get name
			scnr = new Scanner(pkgString);
			String name = nextArg(scnr, RUData.beginName, RUData.endName);
			pkg.setName(name);
			//get all forces
			scnr = new Scanner(pkgString);
			String forceString = nextArg(scnr, RUData.beginForce, RUData.endForce);
			DynamicArray<Force> forces = new DynamicArray(new Force[0]);
			while (forceString != null) {
				Force force = compileForce(forceString);
				forces.add(force);
				forceString = nextArg(scnr, RUData.beginForce, RUData.endForce);
			}
			forces.trim();
			pkg.setForces(forces.storage());

			//get all specials
			scnr = new Scanner(pkgString);
			String specialString = nextArg(scnr, RUData.beginSpecial, RUData.endSpecial);
			DynamicArray<Special> specials = new DynamicArray(new Special[0]);
			while (specialString != null) {
				Special special = compileSpecial(specialString);
				specials.add(special);
				specialString = nextArg(scnr, RUData.beginSpecial, RUData.endSpecial);
			}
			specials.trim();
			pkg.setSpecials(specials.storage());


			//get all artifacts
			scnr = new Scanner(pkgString);
			String artifactString = nextArg(scnr, RUData.beginArtifact, RUData.endArtifact);
			DynamicArray<Artifact> artifacts = new DynamicArray(new Artifact[0]);
			while (artifactString != null) {
				Artifact artifact = compileArtifact(artifactString);
				artifacts.add(artifact);
				artifactString = nextArg(scnr, RUData.beginArtifact, RUData.endArtifact);
			}
			artifacts.trim();
			pkg.setArtifacts(artifacts.storage());

			//get all unit updates
			scnr = new Scanner(pkgString);
			String unitUpdateString = nextArg(scnr, RUData.beginUnitUpdate, RUData.endUnitUpdate);
			DynamicArray<UnitUpdate> unitUpdates = new DynamicArray(new UnitUpdate[0]);
			while (unitUpdateString != null) {
				UnitUpdate unitUpdate = compileUnitUpdate(unitUpdateString);
				unitUpdates.add(unitUpdate);
				unitUpdateString = nextArg(scnr, RUData.beginUnitUpdate, RUData.endUnitUpdate);
			}
			unitUpdates.trim();
			pkg.setUnitUpdates(unitUpdates.storage());

			//get all special updates
			scnr = new Scanner(pkgString);
			String specialUpdateString = nextArg(scnr, RUData.beginSpecialUpdate, RUData.endSpecialUpdate);
			DynamicArray<SpecialUpdate> specialUpdates = new DynamicArray(new SpecialUpdate[0]);
			while (specialUpdateString != null) {
				SpecialUpdate specialUpdate = compileSpecialUpdate(specialUpdateString);
				specialUpdates.add(specialUpdate);
				specialUpdateString = nextArg(scnr, RUData.beginSpecialUpdate, RUData.endSpecialUpdate);
			}
			specialUpdates.trim();
			pkg.setSpecialUpdates(specialUpdates.storage());

			//get all artifact updates
			scnr = new Scanner(pkgString);
			String artifactUpdateString = nextArg(scnr, RUData.beginArtifactUpdate, RUData.endArtifactUpdate);
			DynamicArray<ArtifactUpdate> artifactUpdates = new DynamicArray(new ArtifactUpdate[0]);
			while (artifactUpdateString != null) {
				ArtifactUpdate artifactUpdate = compileArtifactUpdate(artifactUpdateString);
				artifactUpdates.add(artifactUpdate);
				artifactUpdateString = nextArg(scnr, RUData.beginArtifactUpdate, RUData.endArtifactUpdate);
			}
			artifactUpdates.trim();
			pkg.setArtifactUpdates(artifactUpdates.storage());


			//TODO: get all formation updates

			return pkg;
		}
		return null;
	}

	public static Force compileForce(String forceString) {
		Scanner scnr = new Scanner(forceString);
		Force force = new Force();

		//get name
		String name = nextArg(scnr, RUData.beginName, RUData.endName);
		force.setName(name);

		//get alignment
		scnr = new Scanner(forceString);
		String ali = nextArg(scnr, RUData.beginAlignment, RUData.endAlignment);
		force.setAlignment(RUData.ALIGNMENT.valueOf(ali.toUpperCase()));

		//get all units
		scnr = new Scanner(forceString);
		String unitString = nextArg(scnr, RUData.beginUnit, RUData.endUnit);
		DynamicArray<Unit> units = new DynamicArray(new Unit[0]);
		while (unitString != null) {
			Unit unit = compileUnit(unitString);
			unit.setForce(force);
			units.add(unit);
			unitString = nextArg(scnr, RUData.beginUnit, RUData.endUnit);
		}
		units.trim();
		force.setUnits(units.storage());

		//get all formations
		scnr = new Scanner(forceString);
		String formationString = nextArg(scnr, RUData.beginFormation, RUData.endFormation);
		DynamicArray<Formation> formations = new DynamicArray(new Formation[0]);
		while (formationString != null) {
			Formation formation = compileFormation(formationString);
			formation.setForce(force);
			formations.add(formation);
			formationString = nextArg(scnr, RUData.beginFormation, RUData.endFormation);
		}
		formations.trim();
		force.setFormations(formations.storage());


		return force;
	}

	public static Unit compileUnit(String unitString) {
		Unit unit = new Unit();

		//get name
		Scanner scnr = new Scanner(unitString);
		String name = nextArg(scnr, RUData.beginName, RUData.endName);
		unit.setName(name);

		//get type
		scnr = new Scanner(unitString);
		String type = nextArg(scnr, RUData.beginType, RUData.endType);
		type = type.replaceAll("\\s+", "_").toUpperCase();
		unit.setType(RUData.UNIT_TYPE.valueOf(type));

		//get sizes
		scnr = new Scanner(unitString);
		String sizeString = nextArg(scnr, RUData.beginSize, RUData.endSize);
		DynamicArray<UnitSize> sizes = new DynamicArray(new UnitSize[0]);
		while (sizeString != null) {
			UnitSize size = compileSize(sizeString);
			sizes.add(size);
			sizeString = nextArg(scnr, RUData.beginSize, RUData.endSize);
		}
		sizes.trim();
		unit.setSizes(sizes.storage());

		//get specials
		scnr = new Scanner(unitString);
		String specialString = nextArg(scnr, RUData.beginSpecialRef, RUData.endSpecialRef);
		DynamicArray<SpecialRef> specials = new DynamicArray(new SpecialRef[0]);
		while (specialString != null) {
			SpecialRef special = compileSpecialRef(specialString);
			specials.add(special);
			specialString = nextArg(scnr, RUData.beginSpecialRef, RUData.endSpecialRef);
		}
		specials.trim();
		unit.setSpecials(specials.storage());

		//get options
		scnr = new Scanner(unitString);
		String optionString = nextArg(scnr, RUData.beginOption, RUData.endOption);
		DynamicArray<UnitOption> options = new DynamicArray(new UnitOption[0]);
		while (optionString != null) {
			UnitOption option = compileOption(optionString);
			options.add(option);
			optionString = nextArg(scnr, RUData.beginOption, RUData.endOption);
		}
		options.trim();
		unit.setOptions(options.storage());

		return unit;
	}


	public static UnitSize compileSize(String sizeString) {
		UnitSize size = new UnitSize();
		//get name
		Scanner scnr = new Scanner(sizeString);
		String name = nextArg(scnr, RUData.beginName, RUData.endName);
		name = name.replaceAll("\\s+", "_").toUpperCase();
		size.setName(RUData.UNIT_SIZE.valueOf(name));
		//get sp, me, ra, de, att, neW, neR, pts
		scnr = new Scanner(sizeString);
		size.setSp(nextArg(scnr, RUData.beginSp, RUData.endSp));
		size.setMe(nextArg(scnr, RUData.beginMe, RUData.endMe));
		size.setRa(nextArg(scnr, RUData.beginRa, RUData.endRa));
		size.setDe(nextArg(scnr, RUData.beginDe, RUData.endDe));
		size.setAtt(nextArg(scnr, RUData.beginAtt, RUData.endAtt));
		size.setNeW(nextArg(scnr, RUData.beginNeW, RUData.endNeW));
		size.setNeR(nextArg(scnr, RUData.beginNeR, RUData.endNeR));
		size.setPts(nextArg(scnr, RUData.beginPts, RUData.endPts));
		return size;
	}

	public static SpecialRef compileSpecialRef(String specialString) {
		SpecialRef special = new SpecialRef();
		Scanner scnr = new Scanner(specialString);
		special.setPkgName(nextArg(scnr, RUData.beginPackageName, RUData.endPackageName));
		special.setSpecName(nextArg(scnr, RUData.beginSpecialName, RUData.endSpecialName));
		//get n
		scnr = new Scanner(specialString);
		String n = nextArg(scnr, RUData.beginN, RUData.endN);
		special.setN(n);
		return special;
	}

	public static UnitOption compileOption(String optionString) {
		UnitOption option = new UnitOption();
		Scanner scnr = new Scanner(optionString);
		String flavourString = nextArg(scnr, RUData.beginFlavour, RUData.endFlavour);
		DynamicArray<Flavour> flaves = new DynamicArray(new Flavour[0]);
		while (flavourString != null) {
			Flavour flave = compileFlavour(flavourString);
			flaves.add(flave);
			flavourString = nextArg(scnr, RUData.beginFlavour, RUData.endFlavour);
		}
		flaves.trim();
		option.setFlavours(flaves.storage());
		return option;
	}

	public static Flavour compileFlavour(String flavourString) {
		Flavour flavour = new Flavour();

		//get name
		Scanner scnr = new Scanner(flavourString);
		String name = nextArg(scnr, RUData.beginName, RUData.endName);
		flavour.setName(name);
		//get type
		scnr = new Scanner(flavourString);
		String type = nextArg(scnr, RUData.beginType, RUData.endType);
		if (type != null) {
			type = type.replaceAll("\\s+", "_").toUpperCase();
			flavour.setType(RUData.UNIT_TYPE.valueOf(type));
		}

		//get addSpecials
		scnr = new Scanner(flavourString);
		String specialString = nextArg(scnr, RUData.beginAdd, RUData.endAdd);
		DynamicArray<SpecialRef> specials = new DynamicArray(new SpecialRef[0]);
		while (specialString != null) {
			SpecialRef special = compileSpecialRef(specialString);
			specials.add(special);
			specialString = nextArg(scnr, RUData.beginAdd, RUData.endAdd);
		}
		specials.trim();
		flavour.setAddSpecials(specials.storage());

		//get removeSpecials
		scnr = new Scanner(flavourString);
		specialString = nextArg(scnr, RUData.beginRemove, RUData.endRemove);
		specials = new DynamicArray(new SpecialRef[0]);
		while (specialString != null) {
			SpecialRef special = compileSpecialRef(specialString);
			specials.add(special);
			specialString = nextArg(scnr, RUData.beginRemove, RUData.endRemove);
		}
		specials.trim();
		flavour.setRemoveSpecials(specials.storage());

		//get sp, me, ra, de, att, neW, neR, pts
		scnr = new Scanner(flavourString);
		flavour.setSp(nextArg(scnr, RUData.beginSp, RUData.endSp));
		flavour.setMe(nextArg(scnr, RUData.beginMe, RUData.endMe));
		flavour.setRa(nextArg(scnr, RUData.beginRa, RUData.endRa));
		flavour.setDe(nextArg(scnr, RUData.beginDe, RUData.endDe));
		flavour.setAtt(nextArg(scnr, RUData.beginAtt, RUData.endAtt));
		flavour.setNeW(nextArg(scnr, RUData.beginNeW, RUData.endNeW));
		flavour.setNeR(nextArg(scnr, RUData.beginNeR, RUData.endNeR));
		flavour.setPts(nextArg(scnr, RUData.beginPts, RUData.endPts));

		//get description
		scnr = new Scanner(flavourString);
		String desc = nextArg(scnr, RUData.beginDesc, RUData.endDesc);
		flavour.setDescription(desc);

		return flavour;
	}

	public static Formation compileFormation(String formationString) {
		Formation formation = new Formation();
		//get name
		Scanner scnr = new Scanner(formationString);
		String name = nextArg(scnr, RUData.beginName, RUData.endName);
		formation.setName(name);
		//get pts
		scnr = new Scanner(formationString);
		String pts = nextArg(scnr, RUData.beginPts, RUData.endPts);
		formation.setPts(pts);
		//get description
		scnr = new Scanner(formationString);
		String desc = nextArg(scnr, RUData.beginDesc, RUData.endDesc);
		formation.setDescription(desc);

		return formation;
	}

	public static Special compileSpecial(String specialString) {
		Special special = new Special();
		//get name
		Scanner scnr = new Scanner(specialString);
		String name = nextArg(scnr, RUData.beginName, RUData.endName);
		special.setName(name);
		//get description
		scnr = new Scanner(specialString);
		String desc = nextArg(scnr, RUData.beginDesc, RUData.endDesc);
		special.setDescription(desc);

		return special;
	}

	public static Artifact compileArtifact(String artifactString) {
		Artifact artifact = new Artifact();
		//get name
		Scanner scnr = new Scanner(artifactString);
		String name = nextArg(scnr, RUData.beginName, RUData.endName);
		artifact.setName(name);
		//get description
		scnr = new Scanner(artifactString);
		String desc = nextArg(scnr, RUData.beginDesc, RUData.endDesc);
		artifact.setDescription(desc);
		//get points
		scnr = new Scanner(artifactString);
		String pts = nextArg(scnr, RUData.beginPts, RUData.endPts);
		artifact.setPts(pts);

		return artifact;
	}

	public static UnitUpdate compileUnitUpdate(String unitUpdateString) {
		UnitUpdate unitUpdate = new UnitUpdate();
		//get package, force, and unit names
		Scanner scnr = new Scanner(unitUpdateString);
		String name = nextArg(scnr, RUData.beginPackageName, RUData.endPackageName);
		unitUpdate.setPkgName(name);
		scnr = new Scanner(unitUpdateString);
		name = nextArg(scnr, RUData.beginUpdateForce, RUData.endUpdateForce);
		unitUpdate.setForceName(name);
		scnr = new Scanner(unitUpdateString);
		name = nextArg(scnr, RUData.beginUnit, RUData.endUnit);
		unitUpdate.setUnitName(name);
		scnr = new Scanner(unitUpdateString);
		name = nextArg(scnr, RUData.beginNewUnitName, RUData.endNewUnitName);
		unitUpdate.setNewUnitName(name);


		//get addSizes
		scnr = new Scanner(unitUpdateString);
		String sizeString = nextArg(scnr, RUData.beginAddSize, RUData.endAddSize);
		DynamicArray<UnitSize> sizes = new DynamicArray(new UnitSize[0]);
		while (sizeString != null) {
			UnitSize size = compileSize(sizeString);
			sizes.add(size);
			sizeString = nextArg(scnr, RUData.beginAddSize, RUData.endAddSize);
		}
		sizes.trim();
		unitUpdate.setAddSizes(sizes.storage());
		//get editSizes
		scnr = new Scanner(unitUpdateString);
		sizeString = nextArg(scnr, RUData.beginEditSize, RUData.endEditSize);
		sizes = new DynamicArray(new UnitSize[0]);
		while (sizeString != null) {
			UnitSize size = compileSize(sizeString);
			sizes.add(size);
			sizeString = nextArg(scnr, RUData.beginEditSize, RUData.endEditSize);
		}
		sizes.trim();
		unitUpdate.setEditSizes(sizes.storage());
		//get removeSizes
		scnr = new Scanner(unitUpdateString);
		sizeString = nextArg(scnr, RUData.beginRemoveSize, RUData.endRemoveSize);
		sizes = new DynamicArray(new UnitSize[0]);
		while (sizeString != null) {
			UnitSize size = compileSize(sizeString);
			sizes.add(size);
			sizeString = nextArg(scnr, RUData.beginRemoveSize, RUData.endRemoveSize);
		}
		sizes.trim();
		unitUpdate.setRemoveSizes(sizes.storage());

		//get addSpecials
		scnr = new Scanner(unitUpdateString);
		String specialString = nextArg(scnr, RUData.beginAddSpecial, RUData.endAddSpecial);
		DynamicArray<SpecialRef> specials = new DynamicArray(new SpecialRef[0]);
		while (specialString != null) {
			SpecialRef special = compileSpecialRef(specialString);
			specials.add(special);
			specialString = nextArg(scnr, RUData.beginAddSpecial, RUData.endAddSpecial);
		}
		specials.trim();
		unitUpdate.setAddSpecials(specials.storage());
		//get removeSpecials
		scnr = new Scanner(unitUpdateString);
		specialString = nextArg(scnr, RUData.beginRemoveSpecial, RUData.endRemoveSpecial);
		specials = new DynamicArray(new SpecialRef[0]);
		while (specialString != null) {
			SpecialRef special = compileSpecialRef(specialString);
			specials.add(special);
			specialString = nextArg(scnr, RUData.beginRemoveSpecial, RUData.endRemoveSpecial);
		}
		specials.trim();
		unitUpdate.setRemoveSpecials(specials.storage());

		//get addOptions
		scnr = new Scanner(unitUpdateString);
		String optionString = nextArg(scnr, RUData.beginAddOption, RUData.endAddOption);
		DynamicArray<UnitOption> options = new DynamicArray(new UnitOption[0]);
		while (optionString != null) {
			UnitOption option = compileOption(optionString);
			options.add(option);
			optionString = nextArg(scnr, RUData.beginAddOption, RUData.endAddOption);
		}
		options.trim();
		unitUpdate.setAddOptions(options.storage());
		//get editOptions
		scnr = new Scanner(unitUpdateString);
		optionString = nextArg(scnr, RUData.beginEditOption, RUData.endEditOption);
		options = new DynamicArray(new UnitOption[0]);
		while (optionString != null) {
			UnitOption option = compileOption(optionString);
			options.add(option);
			optionString = nextArg(scnr, RUData.beginEditOption, RUData.endEditOption);
		}
		options.trim();
		unitUpdate.setEditOptions(options.storage());
		//get removeOptions
		scnr = new Scanner(unitUpdateString);
		optionString = nextArg(scnr, RUData.beginRemoveOption, RUData.endRemoveOption);
		options = new DynamicArray(new UnitOption[0]);
		while (optionString != null) {
			UnitOption option = compileOption(optionString);
			options.add(option);
			optionString = nextArg(scnr, RUData.beginRemoveOption, RUData.endRemoveOption);
		}
		options.trim();
		unitUpdate.setRemoveOptions(options.storage());

		return unitUpdate;
	}

	public static SpecialUpdate compileSpecialUpdate(String specialUpdateString) {
		SpecialUpdate specialUpdate = new SpecialUpdate();

		Scanner scnr = new Scanner(specialUpdateString);
		String name = nextArg(scnr, RUData.beginPackageName, RUData.endPackageName);
		specialUpdate.setPkgName(name);
		scnr = new Scanner(specialUpdateString);
		name = nextArg(scnr, RUData.beginName, RUData.endName);
		specialUpdate.setObjName(name);
		scnr = new Scanner(specialUpdateString);
		name = nextArg(scnr, RUData.beginNewSpecialName, RUData.endNewSpecialName);
		specialUpdate.setNewName(name);
		scnr = new Scanner(specialUpdateString);
		String desc = nextArg(scnr, RUData.beginDesc, RUData.endDesc);
		specialUpdate.setNewDescription(desc);

		return specialUpdate;
	}

	public static ArtifactUpdate compileArtifactUpdate(String artifactUpdateString) {
		ArtifactUpdate artifactUpdate = new ArtifactUpdate();

		Scanner scnr = new Scanner(artifactUpdateString);
		String name = nextArg(scnr, RUData.beginPackageName, RUData.endPackageName);
		artifactUpdate.setPkgName(name);
		scnr = new Scanner(artifactUpdateString);
		name = nextArg(scnr, RUData.beginName, RUData.endName);
		artifactUpdate.setObjName(name);
		scnr = new Scanner(artifactUpdateString);
		name = nextArg(scnr, RUData.beginNewArtifactName, RUData.endNewArtifactName);
		artifactUpdate.setNewName(name);
		scnr = new Scanner(artifactUpdateString);
		String desc = nextArg(scnr, RUData.beginDesc, RUData.endDesc);
		artifactUpdate.setNewDescription(desc);
		scnr = new Scanner(artifactUpdateString);
		String newpts = nextArg(scnr, RUData.beginPts, RUData.endPts);
		artifactUpdate.setNewPts(newpts);

		return artifactUpdate;
	}





	//scans to begArg, and returns the sub string between first occurance of
	//begArg and endArg.
	//The scanner will advance to endArg.
	public static String nextArg(Scanner scn, String begArg, String endArg) {
		//regex: contains begArg, then anything, then endArg.
		String regex = "(?<=" +  begArg + ")(?s).*?(?=" + endArg + ")";
		String arg = scn.findWithinHorizon(regex, 0);
		int begIndex = begArg.length();
		int endIndex = endArg.length();
		return arg;
	}



	public static void delete(String fname) {
		File Pdir = getPackageDir();
		File pfile = new File(Pdir.getPath() + RWFile.fs + fname);
		System.out.println("attempting to delete file:");
		System.out.println(pfile.toPath());
		try {
			Files.delete(pfile.toPath());
		} catch (IOException e) {
			System.err.println(e);
		}
	}

}