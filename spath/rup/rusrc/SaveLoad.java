package rup.rusrc;

import java.util.*;
import java.io.*;

import rup.datasrc.*;
import rup.tools.*;

public class SaveLoad {

	public static void save(String filepath, ArmyElement[][] dets, ArmyRequirements ar, RUPackage[] rupsUsed) {
		String s = "";

		//packages used
		s += RUData.beginPackage;
		for (int p = 0; p < rupsUsed.length; p++) {
			s += RUData.beginPackageName + rupsUsed[p].name() + RUData.endPackageName;
		}
		s += RUData.endPackage;

	//total points
		s += RUData.beginPoints + ar.pointsMax() + RUData.endPoints;
		for (int d = 0; d < dets.length; d++) {
			s += RUData.beginForce;
			//force name
			s += RUData.beginName + dets[d][0].force().name() + RUData.endName;
			//units
			for (int u = 0; u < dets[d].length; u++) {
				s += RUData.beginArmyElement;
				if (dets[d][u] instanceof SelectedUnit) {
					SelectedUnit su = (SelectedUnit) dets[d][u];
					s += RUData.beginUnit;
					//unit name and size
					s += RUData.beginName + su.name() + RUData.endName;
					s += RUData.beginSize + su.sizeIndex() + RUData.endSize;
					//selected options
					for (int o = 0; o < su.selectedOptions().length; o++) {
						if (su.selectedOptions(o) != -1) {
							s += RUData.beginSelectedOption;
							s += o + "," + su.selectedOptions(o);
							s += RUData.endSelectedOption;
						}
					}
					//artefact
					if (su.artefact() != null) {
						s += RUData.beginArtifact + su.artefact().name() + RUData.endArtifact;
					}
					s += RUData.endUnit;
				} else if (dets[d][u] instanceof Formation) {
					Formation f = (Formation) dets[d][u];
					s += RUData.beginFormation;
					s += RUData.beginName;
					s += f.name();
					s += RUData.endName;
					s += RUData.endFormation;
				}
				s += RUData.endArmyElement;
			}
			s += RUData.endForce;
		}
		RWFile.printToFile(filepath, s);
	}

	public static String[] loadUsedPackages(String filepath) {
		Scanner fscn = RWFile.getFileScanner(filepath);
		DynamicArray<String> usedrups = new DynamicArray(new String[0]);
		String rupsString = Compile.nextArg(fscn, RUData.beginPackage, RUData.endPackage);
		Scanner argscn = new Scanner(rupsString);
		String argString = Compile.nextArg(argscn, RUData.beginPackageName, RUData.endPackageName);
		while (argString != null) {
			usedrups.add(argString);
			argString = Compile.nextArg(argscn, RUData.beginPackageName, RUData.endPackageName);
		}
		usedrups.trim();
		return usedrups.storage();
	}

	public static ArmyElement[][] load(String filepath, Force [] forces, ArmyRequirements ar) {
		DynamicArray<ArmyElement[]> detsDA = new DynamicArray(new ArmyElement[1][]);
		Scanner fscn = RWFile.getFileScanner(filepath);
		String points = Compile.nextArg(fscn, RUData.beginPoints, RUData.endPoints);
		int pts = Integer.parseInt(points);
		ar.setPointsMax(pts);
		String farg = "";//not null
		while (farg != null) {
			farg = Compile.nextArg(fscn, RUData.beginForce, RUData.endForce);
			if (farg != null) {

				ArmyElement[] det = loadDetachment(farg, forces);
				if (det != null) detsDA.add(det);
			}
		}
		System.out.println("returning army");
		detsDA.trim();
		return detsDA.storage();
	}

	private static ArmyElement[] loadDetachment(String farg, Force[] forces) {
		System.out.println("load detachment");
		Scanner fscn = new Scanner(farg);
		String fname = Compile.nextArg(fscn, RUData.beginName, RUData.endName);
		int findex = -1;
		if (fname != null) {
			//get force index.
			for (int f = 0; f < forces.length; f++) {
				if (fname.equals(forces[f].name())) {
					findex = f;
					break;
				}
			}
			String aearg = "";
			fscn = new Scanner(farg);
			if (findex > -1) {
				DynamicArray<ArmyElement> det = new DynamicArray(new ArmyElement[0]);
				SelectedUnit su;
				aearg = Compile.nextArg(fscn, RUData.beginArmyElement, RUData.endArmyElement);
				while (aearg != null) {
					//scan for unit
					Scanner aescn = new Scanner(aearg);
					String uarg = Compile.nextArg(aescn, RUData.beginUnit, RUData.endUnit);
					if (uarg != null) {
						su = loadUnit(uarg, forces[findex]);
						if (su != null) det.add(su);
					}
					//scan for formation
					aescn = new Scanner(aearg);
					uarg = Compile.nextArg(aescn, RUData.beginFormation, RUData.endFormation);
					if (uarg != null) {
						Formation f = loadFormation(uarg, forces[findex]);
						if (f != null) det.add(f);
					}
					aearg = Compile.nextArg(fscn, RUData.beginArmyElement, RUData.endArmyElement);
				}
				System.out.println("returning det");
				det.trim();
				return det.storage();
			}
		}
		System.out.println("no det found");
		return null;//no force was found.
	}

	private static SelectedUnit loadUnit(String uarg, Force force) {
		System.out.println("load unit");
		SelectedUnit su = new SelectedUnit();
		Scanner uscn;
		String uname;
		uscn = new Scanner(uarg);
		System.out.println("uarg:");
		System.out.println(uarg);
		System.out.println("end uarg");
		uname = Compile.nextArg(uscn, RUData.beginName, RUData.endName);
		int uindex = -1;
		System.out.println("uname = " + uname);
		if (uname != null) {
			//get unit index.
			for (int u = 0; u < force.units().length; u++) {
				if (uname.equals(force.units(u).name())) {
					uindex = u;
					break;
				}
			}
		}
		System.out.println("uindex = " + uindex);
		if (uindex > -1) {
			//set unit
			su.setUnit(force.units(uindex));
			uscn = new Scanner(uarg);
			String size =  Compile.nextArg(uscn, RUData.beginSize, RUData.endSize);
			System.out.println("size = " + size);
			int si = Integer.parseInt(size);
			su.setSize(si);
			String option = "";
			uscn = new Scanner(uarg);
			System.out.println("option != null = " + option != null);
			while (option != null) {
				System.out.println("");
				option = Compile.nextArg(uscn, RUData.beginSelectedOption, RUData.endSelectedOption);
				int o = -1;
				int f = -1;
				if (option != null) {
					//might throw errors, consider handling them.
					o = Integer.parseInt(option.substring(0,1));
					f = Integer.parseInt(option.substring(2,3));
					System.out.println("options: " + o + ", " + f);
					su.setSelectedOptions(o, f);
				}
			}
			//get artefact
			uscn = new Scanner(uarg);
			String artefact = Compile.nextArg(uscn, RUData.beginArtifact, RUData.endArtifact);
			if (artefact != null) {
				for (int a = 0; a < RUData.WORKINGPACKAGE.artifacts().length; a++) {
					if (artefact.equals(RUData.WORKINGPACKAGE.artifacts(a).name())) {
						System.out.println("artefact = " + artefact);
						su.setArtefact(RUData.WORKINGPACKAGE.artifacts(a));
						break;
					}
				}
			}
			System.out.println("returning unit");
			return su;
		}
		System.out.println("no unit found");
		return null;//unit wasn't found.
	}

private static Formation loadFormation(String farg, Force force) {
		Formation f = new Formation();
		Scanner fscn;
		String fname;
		fscn = new Scanner(farg);
		fname = Compile.nextArg(fscn, RUData.beginName, RUData.endName);
		if (fname != null) {
			for (int i = 0; i < force.formations().length; i++) {
				if (fname.equals(force.formations(i).name())) {
					return force.formations(i);
				}
			}
		}
		return null;
	}

}
