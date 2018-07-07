package rup.datasrc;

import rup.tools.*;
import java.awt.Color;

public class RUData {//are you?

	//file paths
	public static final String FORCE_LIST_PATH = "force lists";
	public static final String PACKAGES_PATH = "packages";
	public static final String DATA_PATH = "data";
	public static final String ARMY_LIST_PATH = "army lists";
	public static final String IMAGE_PATH = "images";
	public static final String SAVES_PATH = "saved armies";
	public static final String CONFIG_FILE = "config.txt";
	public static final String ARTEFACT_FILE = "artefacts.dat";

	public static final String RUPACKAGE_EXT = ".rup";

	//read/write keywords
	public static final String beginForce = "<force>";
	public static final String beginUnit = "<unit>";
	public static final String beginSize = "<size>";
	public static final String beginSpecial = "<special>";
	public static final String beginOption = "<option>";
	public static final String beginFlavour = "<flavour>";
	public static final String beginPackage = "<package>";
	public static final String beginAlignment = "<alignment>";
	public static final String beginFormation = "<formation>";
	public static final String beginAdd = "<add>";
	public static final String beginRemove = "<remove>";
	public static final String beginType = "<type>";
	public static final String beginPackageName = "<packagename>";
	public static final String beginSpecialName = "<specialname>";
	public static final String beginSpecialRef = "<specialref>";
	public static final String beginDependencies = "<deps>";
	public static final String beginUnitUpdate = "<unitupdate>";
	public static final String beginAddSpecial = "<addspecial>";
	public static final String beginRemoveSpecial = "<removespecial>";
	public static final String beginAddSize = "<addsize>";
	public static final String beginEditSize = "<editsize>";
	public static final String beginRemoveSize = "<removesize>";
	public static final String beginAddOption = "<addoption>";
	public static final String beginEditOption = "<editoption>";
	public static final String beginRemoveOption = "<removeoption>";
	public static final String beginUpdateForce = "<updateforce>";
	public static final String beginNewUnitName = "<newunitname>";
	public static final String beginArmyElement = "<armyelement>";


	public static final String endForce = "</force>";
	public static final String endUnit = "</unit>";
	public static final String endSize = "</size>";
	public static final String endSpecial = "</special>";
	public static final String endOption = "</option>";
	public static final String endFlavour = "</flavour>";
	public static final String endPackage = "</package>";
	public static final String endAlignment = "</alignment>";
	public static final String endFormation = "</formation>";
	public static final String endAdd = "</add>";
	public static final String endRemove = "</remove>";
	public static final String endType = "</type>";
	public static final String endPackageName = "</packagename>";
	public static final String endSpecialName = "</specialname>";
	public static final String endSpecialRef = "</specialref>";
	public static final String endDependencies = "</deps>";
	public static final String endUnitUpdate = "</unitupdate>";
	public static final String endAddSpecial = "</addspecial>";
	public static final String endRemoveSpecial = "</removespecial>";
	public static final String endAddSize = "</addsize>";
	public static final String endEditSize = "</editsize>";
	public static final String endRemoveSize = "</removesize>";
	public static final String endAddOption = "</addoption>";
	public static final String endEditOption = "</editoption>";
	public static final String endRemoveOption = "</removeoption>";
	public static final String endUpdateForce = "</updateforce>";
	public static final String endNewUnitName = "</newunitname>";
	public static final String endArmyElement = "</armyelement>";


	public static final String beginSp = "<sp>";
	public static final String endSp = "</sp>";
	public static final String beginMe = "<me>";
	public static final String endMe = "</me>";
	public static final String beginRa = "<ra>";
	public static final String endRa = "</ra>";
	public static final String beginDe = "<de>";
	public static final String endDe = "</de>";
	public static final String beginAtt = "<att>";
	public static final String endAtt = "</att>";
	public static final String beginNeW = "<new>";
	public static final String endNeW = "</new>";
	public static final String beginNeR = "<ner>";
	public static final String endNeR = "</ner>";
	public static final String beginPts = "<pts>";
	public static final String endPts = "</pts>";

	public static final String beginN = "<n>";
	public static final String endN = "</n>";


	public static final String tagName = "<name>";
	public static final String tagType = "<type>";
	public static final String tagAlignment = "<alignment>";
	public static final String tagSp = "<sp>";
	public static final String tagMe = "<me>";
	public static final String tagRa = "<ra>";
	public static final String tagDe = "<de>";
	public static final String tagAtt = "<att>";
	public static final String tagNe = "<ne>";
	public static final String tagPts = "<pts>";
	public static final String tagN = "<n>";
	public static final String tagRemove = "<remove>";

	public static final String beginArtifact = "<artifact>";
	public static final String endArtifact = "</artifact>";
	public static final String beginDesc = "<desc>";
	public static final String endDesc= "</desc>";
	public static final String beginName = "<name>";
	public static final String endName = "</name>";
	public static final String beginSelectedOption = "<seloption>";
	public static final String endSelectedOption = "</seloption>";
	public static final String beginPoints = "<points>";
	public static final String endPoints = "</points>";

	//frame titles
	public static final String RU_TITLE = "Rank Up!";
	public static final String RUE_TITLE = "Rank Up! Editor";

	//config settings
	public static int textSize = 9;
	public static int titleSize = textSize + 3;

	//package stuff
	public static RUPackage WORKINGPACKAGE;


	//unit related stuff
	public enum ALIGNMENT {EVIL, NEUTRAL, GOOD};
	public enum UNIT_TYPE {
		INFANTRY, LARGE_INFANTRY, CAVALRY, LARGE_CAVALRY,
		MONSTER, WAR_ENGINE, HERO_INFANTRY, HERO_LARGE_INFANTRY,
		HERO_CAVALRY, HERO_LARGE_CAVALRY, HERO_MONSTER, HERO_WAR_ENGINE;

		public String toString() {
			return name().replace('_', ' ').toLowerCase();
		}
		public static UNIT_TYPE toType(String s) {
			return valueOf(s.replace(' ', '_').toUpperCase());
		}
	}

	public enum UNIT_SIZE {
		ONE, TROOP, REGIMENT, HORDE, LEGION;

		public String toString() {
			return name().toLowerCase();
		}
	}

	//text display related stuff
	public static final String DASH_PTRN = "[-—]?";
	public static final String EMDASH = "—";

	public static String displayStatPlus(String stat) {
		return ((stat.matches(DASH_PTRN)) ? EMDASH : stat + "+");
	}
	public static String displayStat(String stat) {
		return ((stat.matches(DASH_PTRN)) ? EMDASH : stat);
	}
	//return string in html at default size
	public static String html(String s) {
		return html(s, textSize);
	}

	//return string in html at specified size (colours should be like 'rrggbb'
	//alpha values don't work in html 3; so we're left with this
	public static String html(String s, int size, String bgclr, String fgclr) {
		//the style stuff
		String style = "<style>body{font-size:" + size + "px;";
		if (bgclr != null) style += "background-color:#" + bgclr + ");";
		if (fgclr != null) style += "color:#" + fgclr + ");";
		style += "}</style>";
		String start = "<html><head>" + style + "</head><body>";
		String end = "</body></html>";
		return start + s + end;
	}

	//return string in html at specified size and default colours
	public static String html(String s, int size) {
		return html(s, size, null, null);
	}

}