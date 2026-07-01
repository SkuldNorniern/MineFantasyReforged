package minefantasy.mfr.util;

public class Functions {

	public static int getIntervalWave1_i(int x, int p, int a, int c) {
		double fx = Math.cos(x * Math.PI / (p / 2)) * ((a - c) / 2) + ((a - c) / 2) + c;
		return (int) fx;
	}

	public static double getIntervalWave1_d(double x, double p, double a, double c) {
		return Math.cos(x * Math.PI / (p / 2)) * ((a - c) / 2) + ((a - c) / 2) + c;
	}
}
