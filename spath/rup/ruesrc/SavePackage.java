package rup.ruesrc;
import rup.tools.*;
import rup.datasrc.*;
public class SavePackage {

	public static void save(RUPackage rupackage) {
		String filepath = RUData.PACKAGES_PATH + RWFile.fs + rupackage.name() + RUData.RUPACKAGE_EXT;
		String s = RUData.beginPackage + packageString(rupackage) + RUData.endPackage;
		RWFile.printToFile(filepath, s);
	}

	public static String packageString(RUPackage rupackage) {
		String s = "";
		//save name
		s += RUData.beginName + rupackage.name() + RUData.endName;
		//save forces
		for (int i = 0; i < rupackage.forces().length; i++) {
			Force force = rupackage.forces(i);
			s += RUData.beginForce + forceString(force) + RUData.endForce;
		}
		//save specials
		for (int i = 0; i < rupackage.specials().length; i++) {
			Special special = rupackage.specials(i);
			s += RUData.beginSpecial + specialString(special) + RUData.endSpecial;
		}
		//save artifacts
		for (int i = 0; i < rupackage.artifacts().length; i++) {
			Artifact artifact = rupackage.artifacts(i);
			s += RUData.beginArtifact + artifactString(artifact) + RUData.endArtifact;
		}

		return s;
	}

	public static String forceString(Force force) {
		String s = "";
		//save name
		s += RUData.beginName + force.name() + RUData.endName;
		//save alignment
		s += RUData.beginAlignment + force.alignment().toString() + RUData.endAlignment;
		//save units
		for (int i = 0; i < force.units().length; i++) {
			Unit unit = force.units(i);
			s += RUData.beginUnit + unitString(unit) + RUData.endUnit;
		}
		//save formations
		for (int i = 0; i < force.formations().length; i++) {
			Formation formation = force.formations(i);
			s += RUData.beginFormation + formationString(formation) + RUData.endFormation;
		}

		return s;
	}

	public static String unitString(Unit unit) {
		String s = "";
		//save name
		s += RUData.beginName + unit.name() + RUData.endName;
		//save type
		s += RUData.beginType + unit.type().toString() + RUData.endType;
		//save sizes
		for (int i = 0; i < unit.sizes().length; i++) {
			UnitSize size = unit.sizes(i);
			s += RUData.beginSize + sizeString(size) + RUData.endSize;
		}

		//save specials
		for (int i = 0; i < unit.specials().length; i++) {
			SpecialRef special = unit.specials(i);
			s += RUData.beginSpecialRef + specialRefString(special) + RUData.endSpecialRef;
		}

		//save options
		for (int i = 0; i < unit.options().length; i++) {
			UnitOption option = unit.options(i);
			s += RUData.beginOption + optionString(option) + RUData.endOption;
		}

		return s;

	}

	public static String sizeString(UnitSize size) {
		String s = "";
		//save name
		s += RUData.beginName + size.name().toString() + RUData.endName;
		//save sp, me, ra, de, att, neW, neR, pts
		s += RUData.beginSp + size.sp() + RUData.endSp;
		s += RUData.beginMe + size.me() + RUData.endMe;
		s += RUData.beginRa + size.ra() + RUData.endRa;
		s += RUData.beginDe + size.de() + RUData.endDe;
		s += RUData.beginAtt + size.att() + RUData.endAtt;
		s += RUData.beginNeW + size.neW() + RUData.endNeW;
		s += RUData.beginNeR + size.neR() + RUData.endNeR;
		s += RUData.beginPts + size.pts() + RUData.endPts;

		return s;
	}

	public static String specialRefString(SpecialRef special) {
		String s = "";
		s += RUData.beginPackageName + special.pkgName() + RUData.endPackageName;
		s += RUData.beginSpecialName + special.specName() + RUData.endSpecialName;
		if (special.n() != null) s += RUData.beginN + special.n() + RUData.endN;
		return s;
	}

	public static String optionString(UnitOption option) {
		String s = "";
		for (int i = 0; i < option.flavours().length; i++) {
			Flavour flavour = option.flavours(i);
			s += RUData.beginFlavour + flavourString(flavour) + RUData.endFlavour;
		}
		return s;
	}

	public static String flavourString(Flavour flavour) {
		String s = "";
		//save name
		s += RUData.beginName + flavour.name() + RUData.endName;
		//save type
		if (flavour.type() != null)
			s += RUData.beginType + flavour.type().toString() + RUData.endType;
		//save addSpecials
		for (int i = 0; i < flavour.addSpecials().length; i++) {
			SpecialRef special = flavour.addSpecials(i);
			s += RUData.beginAdd + specialRefString(special) + RUData.endAdd;
		}

		//save removeSpecials
		for (int i = 0; i < flavour.removeSpecials().length; i++) {
			SpecialRef special = flavour.removeSpecials(i);
			s += RUData.beginRemove + specialRefString(special) + RUData.endRemove;
		}

		//save sp, me, ra, de, att, neW, neR, pts
		if (!"".equals(flavour.sp())) s += RUData.beginSp + flavour.sp() + RUData.endSp;
		if (!"".equals(flavour.me())) s += RUData.beginMe + flavour.me() + RUData.endMe;
		if (!"".equals(flavour.ra())) s += RUData.beginRa + flavour.ra() + RUData.endRa;
		if (!"".equals(flavour.de())) s += RUData.beginDe + flavour.de() + RUData.endDe;
		if (!"".equals(flavour.att())) s += RUData.beginAtt + flavour.att() + RUData.endAtt;
		if (!"".equals(flavour.neW())) s += RUData.beginNeW + flavour.neW() + RUData.endNeW;
		if (!"".equals(flavour.neR())) s += RUData.beginNeR + flavour.neR() + RUData.endNeR;
		if (!"".equals(flavour.pts())) s += RUData.beginPts + flavour.pts() + RUData.endPts;

		//save description
		//s += RUData.beginDesc + flavour.description() + RUData.endDesc;

		return s;
	}

	public static String formationString(Formation formation) {
		String s = "";
		//save name
		s += RUData.beginName + formation.name() + RUData.endName;
		//save pts
		s += RUData.beginPts + formation.pts() + RUData.endPts;
		//save description
		s += RUData.beginDesc + formation.description() + RUData.endDesc;

		return s;
	}

	public static String specialString(Special special) {
		String s = "";
		//save name
		s += RUData.beginName + special.name() + RUData.endName;
		//save description
		s += RUData.beginDesc + special.description() + RUData.endDesc;

		return s;
	}

	public static String artifactString(Artifact artifact) {
		String s = "";
		//save name
		s += RUData.beginName + artifact.name() + RUData.endName;
		//save description
		s += RUData.beginDesc + artifact.description() + RUData.endDesc;
		//save points
		s += RUData.beginPts + artifact.pts() + RUData.endPts;

		return s;
	}

}