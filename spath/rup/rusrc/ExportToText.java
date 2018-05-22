package rup.rusrc;

import java.util.*;
import java.io.*;

import rup.tools.*;

public class ExportToText {

	public static final int headerSpaces = 30;
	public static final int statSpaces = 10;


	public static void exportArmy(SelectedUnit[][] army, ArmyRequirements armyReqs) {
		String fn = army[0][0].force().name() + " " + armyReqs.pointsMax();
		exportArmy(army, armyReqs, fn);
	}

	public static void exportArmy(SelectedUnit[][] army, ArmyRequirements armyReqs, String filename) {
		String a = "";
		//title
		a += army[0][0].force().name() + " " + armyReqs.pointsMax() + " Point Army List";
		a += RWFile.nl + RWFile.nl;
		//units
		for (int i = 0; i < army.length - 1; i++) {
			a += detachmentBlock(army[i]) + RWFile.nl + RWFile.nl;
		}
		a += detachmentBlock(army[army.length - 1]);
		a += RWFile.nl + RWFile.nl;
		//total points
		a += "Total points: " + armyReqs.pointsCurrent();

		RWFile.printToFile(filename, a);
	}

	private static String detachmentBlock(SelectedUnit[] det) {
		String db = "";
		for (int i = 0; i < det.length - 1; i++) {
			db += unitBlock(det[i]) + RWFile.nl + RWFile.nl;
		}
		db += unitBlock(det[det.length - 1]);
		return db;
	}

	private static String unitBlock(SelectedUnit su) {
		String u = unitHeader(su) + RWFile.nl;
		u += unitStatHeader() + RWFile.nl;
		u += unitStats(su) + RWFile.nl;
		u += unitSpecials(su);
		return u;
	}

	private static String unitHeader(SelectedUnit su) {
		String n = padSpaces(su.name(), headerSpaces);
		n += padSpaces(su.type().toString(), headerSpaces);
		n += su.sizeName();
		return (n);
	}

	private static String unitStatHeader() {
		String sl = padSpaces("Sp", statSpaces);
		sl += padSpaces("Me", statSpaces);
		sl += padSpaces("Ra", statSpaces);
		sl += padSpaces("De", statSpaces);
		sl += padSpaces("Att", statSpaces);
		sl += padSpaces("Ne", statSpaces);
		sl += "Pts";
		return sl;
	}

	private static String unitStats(SelectedUnit su) {
		String s = padSpaces(su.sp(), statSpaces);
		s += (su.me().equals("—")) ? padSpaces(su.me(), statSpaces) : padSpaces(su.me() + "+", statSpaces);
		s += (su.ra().equals("—")) ? padSpaces(su.ra(), statSpaces) : padSpaces(su.ra() + "+", statSpaces);
		s += (su.de().equals("—")) ? padSpaces(su.de(), statSpaces) : padSpaces(su.de() + "+", statSpaces);
		s += padSpaces(su.att(), statSpaces);
		s += padSpaces(su.neW() + "/" + su.neR(), statSpaces);
		s +=su.pts();
		return s;
	}

	private static String unitSpecials(SelectedUnit su) {
		String s = "Specials:";
		if (su.specials().length > 0) {
			s += " ";
			for (int i = 0; i < su.specials().length - 1; i++) {
				s += su.specials(i).toString();
				s += ", ";
			}
			s += su.specials(su.specials().length - 1).toString();
		}
		return s;
	}

	private static String padSpaces(String s, int spaces) {
		s = String.format("%-" + spaces + "s", s);
		return s;
	}


/*
	private static String uppercaseWords(String s) {
		//will work on later
		//s.replaceAll("\\b\\w", "");
		return s
	}
*/
}