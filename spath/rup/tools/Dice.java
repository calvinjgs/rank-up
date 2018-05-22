package rup.tools;

import java.util.*;

public class Dice {
	private static long seed;
	private static Random r = new Random();

	public static int Roll1Dx(int NumberOfFaces) {
		return r.nextInt(NumberOfFaces) + 1;
	}

	public static int RollyDx(int NumberOfDice, int NumberOfFaces) {
		int currentRoll = 0;
		for (int r = 1; r <= NumberOfDice; r++) {
			currentRoll += Roll1Dx(NumberOfFaces);
		}
		return currentRoll;
	}

	public static int Roll2D6() {
		return RollyDx(2, 6);
	}

	public static int Roll1D6() {
		return RollyDx(1, 6);
	}

	//returns and integer (decision) randomly, but weighted based
	//on an array of integers.
	private static int MakeDecision(int[] probs, int mod) {
		int sum = 0;
		for (int i = 0; i < probs.length; i++) {
			sum += probs[i];
		}
		int roll = Roll1Dx(sum) + mod;
		int match = 0;
		for (int i = 0; i < probs.length; i++) {
			match += probs[i];
			if (roll <= match) {
				return i;
			}
		}

		//if error
		return -1;
	}

	public static int Decide(int... probs) {
		return MakeDecision(probs, 0);
	}

	public static int Decide(int[] probs, int mod) {
		return MakeDecision(probs, mod);
	}

	public static double Range(double a, double b, int decPlaces) {
		for (int i = 0; i < decPlaces; i++) {
			a *= 10;
			b *= 10;
		}
		double reRange = Roll1Dx((int) (b - a + 1)) - 1 + a;
		for (int i = 0; i < decPlaces; i++) {
			reRange /= 10.0;
		}
		return reRange;
	}

	public static double Range(double a, double b) {
		return Range(a, b, 3);
	}

	public static double Gaussian(double x, double A, double B) {
		return A*Math.pow(Math.E, -(1.0/(2*B))*(x*x));
	}

	public static double Gaussian(double x) {
		return Gaussian(x, 1.0, 1.0);
	}

	public static int GDecide(int outcomes) {
		int[] weights = new int[outcomes];
		for (int w = 0; w < weights.length; w++) {
			weights[w] = (int) Round.normal(Gaussian(w - (weights.length/2.0), 100.0, outcomes/2.0));
		}
		return Dice.Decide(weights, 0);
	}

	public static void setSeed(long s){
		seed = s;
		r = new Random(s);
	}

	public static void setRandom(Random rand){
		r = rand;
	}

	public static long seed(){
		return seed;
	}

	public static Random r(){
		return r;
	}



	public static void main(String[] args) {
		System.out.println("Dice compiled successfully.");
		System.out.println("Range 1.00 to 2.00 = " + Range(1, 2));
		System.out.println("Range 1.00 to 2.00 at precision 6 = " + Range(1, 2, 6));
		System.out.println("Range 1.00 to 2.00 at precision 1 = " + Range(1, 2, 1));
	}
}