package rup.rusrc;

import java.util.*;
import java.io.*;

import rup.datasrc.*;
import rup.tools.*;

public class ExportToHTML {

	public static boolean includeSpecialDescriptions;

	public static void exportArmy(ArmyElement[][] army, ArmyRequirements armyReqs) {
		String fn = army[0][0].force().name() + " " + armyReqs.pointsMax();
		fn = RUData.ARMY_LIST_PATH + RWFile.fs + fn + ".htm";
		exportArmy(army, armyReqs, fn);
	}

	public static void exportArmy(ArmyElement[][] army, ArmyRequirements armyReqs, String filename) {
		String a = "<html>";
		//head
		a += "<head>";
		//browser title
		a += "<title>" + army[0][0].force().name() + " " + armyReqs.pointsMax() + " Point Army List — RankUp</title>";
		a += RWFile.nl;
		//style stuff
		a += "<style>" + RWFile.nl;
		a += "* {" + RWFile.nl;
		a += "\tfont-family: verdana;" + RWFile.nl;
		a += "\tfont-size: 12px;" + RWFile.nl;
		a += "}" + RWFile.nl;
		a += ".stat {" + RWFile.nl;
		a += "\ttext-align: center;" + RWFile.nl;
		a += "\twidth: 5%;" + RWFile.nl;
		a += "}" + RWFile.nl;
		a += ".stathead {" + RWFile.nl;
		a += "\ttext-align: center;" + RWFile.nl;
		a += "\tfont-weight: bold;" + RWFile.nl;
		a += "\twidth: 10%;" + RWFile.nl;
		a += "}" + RWFile.nl;
		a += ".namehead {" + RWFile.nl;
		a += "\tbackground-color: #dddddd;" + RWFile.nl;
		a += "\ttext-align: center;" + RWFile.nl;
		a += "\tfont-weight: bold;" + RWFile.nl;
		a += "}" + RWFile.nl;
		a += "h1 {" + RWFile.nl;
		a += "\tfont-size: 16px;" + RWFile.nl;
		a += "\ttext-align: center;" + RWFile.nl;
		a += "\tfont-weight: bold;" + RWFile.nl;
		a += "}" + RWFile.nl;

		a += "</style>" + RWFile.nl;
		a += "</head>" + RWFile.nl;
		//body
		a += "<body>" + RWFile.nl;
		//page title
		a += "<h1>" + army[0][0].force().name() + " " + armyReqs.pointsMax() + " Point Army List</h1>" + RWFile.nl;
		a += "<br><br><br>" + RWFile.nl;
		//unit stat tables
		for (int i = 0; i < army.length; i++) {
			for (int j = 0; j < army[i].length; j++) {
				if (army[i][j] instanceof SelectedUnit) {
					SelectedUnit su = (SelectedUnit) army[i][j];
					a += unitTable(su);
				} else if (army[i][j] instanceof Formation) {
					Formation f = (Formation) army[i][j];
					a += formationTable(f);
				}
				if (!((i == army.length - 1) && (j == army[i].length - 1))) a += "<br><br>" + RWFile.nl;
			}
		}
		a += RWFile.nl;

		if (includeSpecialDescriptions) a += specialDescriptions(army);

		a += "</body>";
		a += "</html>";



		RWFile.printToFile(filename, a);
	}


	public static String unitTable(SelectedUnit su) {
		String s = "<table cellSpacing=\"0\" cellPadding=\"2\" width=\"100%\" border=\"1\" style=\"display: inline-table; page-break-after: avoid; page-break-before: auto;\">";
		s += "<tr>";
		s += "<th class=\"namehead\" colspan=\"3\">" + su.name() + "</td>";
		s += "<th class=\"namehead\" colspan=\"3\">" + capitalize(su.type().toString()) + "</td>";
		s += "<th class=\"namehead\" colspan=\"3\">" + su.sizeName() + "</td>";
		s += "</tr>";
		s += "<tr>";
		s += "<th class=\"stathead\">Sp</td>";
		s += "<th class=\"stathead\">Me</td>";
		s += "<th class=\"stathead\">Ra</td>";
		s += "<th class=\"stathead\">De</td>";
		s += "<th class=\"stathead\">Att</td>";
		s += "<th class=\"stathead\" colspan=\"2\">Ne</td>";
		s += "<th class=\"stathead\">Pts</td>";
		s += "</tr>";
		s += "<tr>";
		s += "<th class=\"stat\">" + RUData.displayStat(su.sp()) + "</td>";
		s += "<th class=\"stat\">" + RUData.displayStatPlus(su.me()) + "</td>";
		s += "<th class=\"stat\">" + RUData.displayStatPlus(su.ra()) + "</td>";
		s += "<th class=\"stat\">" + RUData.displayStatPlus(su.de()) + "</td>";
		s += "<th class=\"stat\">" + RUData.displayStat(su.att()) + "</td>";
		s += "<th class=\"stat\">" + RUData.displayStat(su.neW()) + "</td>";
		s += "<th class=\"stat\">" + RUData.displayStat(su.neR()) + "</td>";
		s += "<th class=\"stat\">" + RUData.displayStat(su.pts()) + "</td>";
		s += "</tr>";
		s += "<tr>";
		s += "<td class=\"stathead\"> Specials:</td>";
		s += "<td colspan=\"7\">";
		if (su.specials().length > 0) {
			for (int i = 0; i < su.specials().length - 1; i++) {
				s += su.specials(i).toStringNoPkg();
				s += ", ";
			}
			s += su.specials(su.specials().length - 1).toStringNoPkg();
		}
		s += "</td>";
		s += "</tr>";
		s += "</table>";
		return s;
	}

	public static String formationTable(Formation f) {
		String s = "<table cellSpacing=\"0\" cellPadding=\"2\" width=\"100%\" border=\"1\" style=\"display: inline-table; page-break-after: avoid; page-break-before: auto;\">";
		s += "<tr>";
		s += "<th class=\"namehead\" colspan=\"1\">" + f.name() + "</td>";
		s += "<th class=\"namehead\" colspan=\"1\">" + "Formation" + "</td>";
		s += "</tr>";
		s += "<tr>";
		s += "<td colspan=\"1\" rowspan=\"2\">";
		s += f.description();
		s += "</td>";
		s += "<th class=\"stathead\">Pts</td>";
		s += "</tr>";
		s += "<tr>";
		s += "<th class=\"stat\">" + RUData.displayStat(f.pts()) + "</td>";
		s += "</tr>";
		s += "</table>";
		return s;

	}

	public static String capitalize(String s) {
		String[] sarr = s.split("\\b");
		s = "";
		for (int i = 0; i < sarr.length; i++) {
			char[] charr = sarr[i].toCharArray();
			if (charr[0] >= 'a' && charr[0] <= 'z') charr[0] = (char) (('A' - 'a') + charr[0]);
			s += s.copyValueOf(charr);
		}
		return s;

	}

	public static String specialDescriptions(ArmyElement[][] army) {
		String s = "";

		//gather all specials used in this army.
		DynamicArray<Special> includedSpecs = new DynamicArray(new Special[0]);
		for (int i = 0; i < army.length; i++) {
			for (int j = 0; j < army[i].length; j++) {
				if (!(army[i][j] instanceof SelectedUnit)) continue;
				SelectedUnit unit = (SelectedUnit) army[i][j];
				for (int k = 0; k < unit.specials().length; k++) {
					Special spec = unit.specials(k).special(RUData.WORKINGPACKAGE.specials());
					boolean specIn = false;
					for (int m = 0; m < includedSpecs.size(); m++) {
						if (spec.equals(includedSpecs.storage(m))) specIn = true;
					}
					if (!specIn) includedSpecs.add(spec);
				}
			}
		}
		//print descriptions
		s += "<br><br><br>";
		for (int m = 0; m < includedSpecs.size(); m++) {
			Special spec = includedSpecs.storage(m);
			s += "<b>" + spec.name() + "</b><br>" + RWFile.nl;
			s += spec.description();
			if (m != includedSpecs.size() - 1)
				s += "<br><br>" + RWFile.nl;
		}




		return s;
	}

	public static String pageBreak() {
		return "";

	}

}