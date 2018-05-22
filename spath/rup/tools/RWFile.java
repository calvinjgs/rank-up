package rup.tools;

import java.util.*;
import java.io.*;

public class RWFile {

	public static final String nl = System.getProperty("line.separator");
	public static final String fs = File.separator;

	public static PrintStream getFilePrintStream (String filePath) {
		try {
			return new PrintStream(new File(filePath));
		} catch (FileNotFoundException e) {
			System.err.println("File not found.\n" + e);
			return null;
		}
	}

	public static File getFile (String filePath) {
		return new File(filePath);
	}

	public static Scanner getFileScanner (String filePath) {
		try {
			return new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			System.err.println("File not found.\n" + e);
			return null;
		}
	}

	//this is was lazily made to allow specifying the charset encoding
	public static Scanner getFileScanner (String filePath, String enc) {
		try {
			return new Scanner(new File(filePath), enc);
		} catch (FileNotFoundException e) {
			System.err.println("File not found.\n" + e);
			return null;
		}
	}


	public static void printToFile(String filePath, String s) {
		PrintStream p = getFilePrintStream(filePath);
		p.print(s);
		p.close();
	}


	public static void main(String[] args) {
		System.out.println("RWFile");
	}


}