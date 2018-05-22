package rup.datasrc;

import java.util.*;
import java.io.*;

import rup.tools.*;

public class ConfigFile {


	//this flag is true when the config file has been read at least once
	private static boolean isReady = false;
	public static boolean isReady(){return isReady;}

	//text size for html ui display
	public static int textSize;

	//editor frame position
	public static int RUEditorX;
	public static int RUEditorY;

	//rugui frame position
	public static int RUGUIX;
	public static int RUGUIY;
	public static int RUGUIWidth;
	public static int RUGUIHeight;


	//package list ordering
	public static String[] rupackageList;
	//selected packages
	public static String[] selectedPackages;

	//argument tags
	public static final String textSizeTag = "text size";
	public static final String RUEditorXTag = "ru editor pos. x";
	public static final String RUEditorYTag = "ru editor pos. y";
	public static final String RUGUIXTag = "ru pos. x";
	public static final String RUGUIYTag = "ru pos. y";
	public static final String RUGUIWidthTag = "ru width";
	public static final String RUGUIHeightTag = "ru height";
	public static final String rupackageListTag = "package list order";
	public static final String rupackageNameTag = "name";
	public static final String selectedPackagesTag = "selected packages";

	public static boolean exists() {
		File cf = new File(RUData.CONFIG_FILE);
		return cf.exists();
	}

	public static String wrap(String tag, String contents) {
		return "<" + tag + ">" + contents + "</" + tag + ">";
	}
	public static String wrapln(String tag, String contents) {
		return wrap(tag, contents) + RWFile.nl;
	}

	public static String boilerplate() {
		String s = "";
		s += "Configuration file for Rank Up and Rank Up Editor";
		return s;
	}


	public static void write() {
		String s = "";
		s += boilerplate() + RWFile.nl;
		if (textSize > 0) s += wrapln(textSizeTag, textSize +"");
		if (RUEditorX > 0) s += wrapln(RUEditorXTag, RUEditorX + "");
		if (RUEditorY > 0) s += wrapln(RUEditorYTag, RUEditorY + "");
		if (RUGUIX > 0) s += wrapln(RUGUIXTag, RUGUIX + "");
		if (RUGUIY > 0) s += wrapln(RUGUIYTag, RUGUIY + "");
		if (RUGUIWidth > 0) s += wrapln(RUGUIWidthTag, RUGUIWidth + "");
		if (RUGUIHeight > 0) s += wrapln(RUGUIHeightTag, RUGUIHeight + "");
		if (rupackageList != null) {
			String names = "";
			for (int i = 0; i < rupackageList.length; i++)
				names += wrapln(rupackageNameTag, rupackageList[i]);
			s += wrapln(rupackageListTag, names);
		}
		if (selectedPackages != null) {
			String names = "";
			for (int i = 0; i < selectedPackages.length; i++)
				names += wrapln(rupackageNameTag, selectedPackages[i]);
			s += wrapln(selectedPackagesTag, names);
		}

		String filepath = RUData.CONFIG_FILE;
		RWFile.printToFile(filepath, s);
	}



	public static String nextArg(Scanner scn, String tag) {
		String begArg = "\\<" + tag + "\\>";
		String endArg = "\\</" + tag + "\\>";
		//regex: contains <arg>, then anything, then </arg>.
		String regex = "(?<=" + begArg + ")(?s).*?(?=" + endArg + ")";
		String arg = scn.findWithinHorizon(regex, 0);
		int begIndex = begArg.length();
		int endIndex = endArg.length();
		return arg;
	}

	public static int stringToInt(String s) {
		int i = -1;
		try {
			i = Integer.parseInt(s);
		} catch (NumberFormatException e ) {
			i = -1;
		}
		return i;
	}

	public static void read() {
		if (!exists()) {
			RWFile.printToFile(RUData.CONFIG_FILE, "");
		}
		Scanner scnr = RWFile.getFileScanner(RUData.CONFIG_FILE);
		//get text size
		String s = nextArg(scnr, textSizeTag);
		int integer = stringToInt(s);
		if (integer > 0) textSize = integer;
		//get editor positions
		s = nextArg(scnr, RUEditorXTag);
		integer = stringToInt(s);
		if (integer > -1) RUEditorX = integer;
		s = nextArg(scnr, RUEditorYTag);
		integer = stringToInt(s);
		if (integer > -1) RUEditorY = integer;
		//get rugui positions/dimensions
		s = nextArg(scnr, RUGUIXTag);
		integer = stringToInt(s);
		if (integer > -1) RUGUIX = integer;
		s = nextArg(scnr, RUGUIYTag);
		integer = stringToInt(s);
		if (integer > -1) RUGUIY = integer;
		s = nextArg(scnr, RUGUIWidthTag);
		integer = stringToInt(s);
		if (integer > -1) RUGUIWidth = integer;
		s = nextArg(scnr, RUGUIHeightTag);
		integer = stringToInt(s);
		if (integer > -1) RUGUIHeight = integer;


		//get package name order
		s = nextArg(scnr, rupackageListTag);

		DynamicArray<String> namesda = new DynamicArray(new String[0]);
		if (s != null) {
			Scanner namescnr = new Scanner(s);
			String namestr = nextArg(namescnr, rupackageNameTag);
			while (namestr != null) {
				namesda.add(namestr);
				namestr = nextArg(namescnr, rupackageNameTag);
			}
		}
		namesda.trim();
		rupackageList = namesda.storage();

		//get selected packages
		s = nextArg(scnr, selectedPackagesTag);

		namesda = new DynamicArray(new String[0]);
		if (s != null) {
			Scanner namescnr = new Scanner(s);
			String namestr = nextArg(namescnr, rupackageNameTag);
			while (namestr != null) {
				namesda.add(namestr);
				namestr = nextArg(namescnr, rupackageNameTag);
			}
		}
		namesda.trim();
		selectedPackages = namesda.storage();



		//all done, ConfigFile is now ready
		isReady = true;

	}




	//sorts any rupackages found in givenRups according to the order in
	//ConfigFile.rupackageList, putting any unsorted rups at the end in
	//their original order.
	public static String[] sortedRUPackageNames(String[] givenRups) {
		if (!ConfigFile.isReady()) ConfigFile.read();
		String[] rupOrder =  ConfigFile.rupackageList;
		String[] packagenames = new String[givenRups.length];
		int orderedlength = 0;
		//add in sorted packages
		for (int i = 0; i < rupOrder.length; i++) {
			for (int j = 0; j < givenRups.length; j++) {
				if (givenRups[j].equals(rupOrder[i])) {
					packagenames[orderedlength] = givenRups[j];
					orderedlength++;
					break;
				}
			}
		}
		//add in unsorted packages
		for (int i = 0; i < givenRups.length; i++) {
			boolean isIn = false;
			for (int j = 0; j < orderedlength; j++) {
				if (givenRups[i].equals(packagenames[j])) {
					isIn = true;
					break;
				}
			}
			if (!isIn) {
				packagenames[orderedlength] = givenRups[i];
				orderedlength++;
			}
		}
		return packagenames;
	}
}