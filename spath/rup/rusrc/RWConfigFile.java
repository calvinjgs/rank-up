package rup.rusrc;

import java.util.*;
import java.io.*;

import rup.rugui.*;
import rup.datasrc.*;
import rup.tools.*;

public class RWConfigFile {


	//rewrites the config file, but replaces some options with
	//current ui values.
	public static void updateConfigFile(RUGUI ui) {
		String rewrite = "";
		String line = "";
		Scanner scnr = RWFile.getFileScanner(RUData.CONFIG_FILE);
		Scanner scnl;
		String[] splitln;
		while (scnr.hasNext()) {
			line = scnr.nextLine();
			splitln = line.split("\\s+");
			if (!splitln[0].startsWith("#")) {
				if (splitln[0].equals("x")) {
					int temp = (int) ui.frame().getLocation().getX();
					splitln[1] = String.valueOf(temp);
				}
				if (splitln[0].equals("y")) {
					int temp = (int) ui.frame().getLocation().getY();
					splitln[1] = String.valueOf(temp);
				}
				if (splitln[0].equals("xsize")) {
					int temp = (int) ui.frame().getWidth();;
					splitln[1] = String.valueOf(temp);
				}
				if (splitln[0].equals("ysize")) {
					int temp = (int) ui.frame().getHeight();
					splitln[1] = String.valueOf(temp);
				}
				//reconstruct line
				line = "";
				for (int i = 0; i < splitln.length; i++) {
					line += splitln[i];
					if (i != splitln.length - 1) line += " ";
				}
			}
			rewrite += line;
			if (scnr.hasNext()) rewrite += RWFile.nl;
		}
		//write to file
		RWFile.printToFile(RUData.CONFIG_FILE, rewrite);
	}

	public static void resetConfigFile() {
		String s = "# config file for Rank Up Kings of War army builder";
		s += RWFile.nl;
		//consider adding 'lock' option, and description for it
		//frame position
		s += "#x and y position of the window on startup" + RWFile.nl;
		s += "x 0" + RWFile.nl;
		s += "y 0" + RWFile.nl;
		s += "#x and y size of the window on startup" + RWFile.nl;
		s += "xsize 1000" + RWFile.nl;
		s += "ysize 550" + RWFile.nl;

		//text size
		s += "#the size of the text displayed." + RWFile.nl;
		s += "textsize " + RUData.textSize;
		//create last list
		//s += "#savelastlist creates an army list on close." + RWFile.nl;
		//s += "#0, 1, 2, 3 for none, .txt, .html, both respectively." + RWFile.nl;
		//s += "savelastlist 0";

		//write to file
		RWFile.printToFile(RUData.CONFIG_FILE, s);
	}

	public static void configRUGUI(RUGUI ui) {
		Scanner scnr = RWFile.getFileScanner("config.txt");
		String tag = "";
		String arg = "";
		int x = -1;
		int y = -1;
		int xsize = -1;
		int ysize = -1;
		int textsize = -1;
		while (scnr.hasNext()) {
			tag = scnr.next();
			if (tag.startsWith("#")) {
				scnr.nextLine();
				continue;
			}
			if (tag.equals("x")) {
				arg = scnr.next();
				try {
					x = Integer.parseInt(arg);
				} catch (NumberFormatException e ) {
					x = -1;
				}
			}
			if (tag.equals("y")) {
				arg = scnr.next();
				try {
					y = Integer.parseInt(arg);
				} catch (NumberFormatException e ) {
					y = -1;
				}
			}
			if (tag.equals("xsize")) {
				arg = scnr.next();
				try {
					xsize = Integer.parseInt(arg);
				} catch (NumberFormatException e ) {
					xsize = -1;
				}
			}
			if (tag.equals("ysize")) {
				arg = scnr.next();
				try {
					ysize = Integer.parseInt(arg);
				} catch (NumberFormatException e ) {
					ysize = -1;
				}
			}
			if (tag.equals("textsize")) {
				arg = scnr.next();
				try {
					textsize = Integer.parseInt(arg);
				} catch (NumberFormatException e ) {
					textsize = -1;
				}
			}
		}
		if (x <= -1) x = (int) ui.frame().getLocation().getX();
		if (y <= -1) y = (int) ui.frame().getLocation().getY();
		ui.frame().setLocation(x, y);
		if (xsize <= -1) xsize = (int) ui.frame().getWidth();
		if (ysize <= -1) ysize = (int) ui.frame().getHeight();
		ui.frame().setSize(xsize, ysize);
		if (textsize <= -1 || textsize > 40) textsize = RUData.textSize;
		RUData.textSize = textsize;
		RUData.titleSize = textsize + 3;
	}

	public static boolean configExists() {
		File cf = new File(RUData.CONFIG_FILE);
		return cf.exists();
	}
}