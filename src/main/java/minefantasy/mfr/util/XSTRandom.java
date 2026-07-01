package minefantasy.mfr.util;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

public class XSTRandom extends Random {

	private static final long serialVersionUID = 6208727693524452904L;
	private static final double DOUBLE_UNIT = 0x1.0p-53;
	private static final float FLOAT_UNIT = 0x1.0p-24f;
	private static final AtomicLong seedUniquifier = new AtomicLong(8682522807148012L);
	boolean haveNextNextGaussian = false;
	double nextNextGaussian = 0;
	private long seed;
	private long last;

	public XSTRandom() {
		this(seedUniquifier() ^ System.nanoTime());
	}

	public XSTRandom(long seed) {
		this.seed = seed;
	}

	private static long seedUniquifier() {
		for (; ; ) {
			long current = seedUniquifier.get();
			long next = current * 181783497276652981L;
			if (seedUniquifier.compareAndSet(current, next)) {
				return next;
			}
		}
	}

	public boolean nextBoolean() {
		return next(1) != 0;
	}

	public double nextDouble() {
		return (((long) (next(26)) << 27) + next(27)) * DOUBLE_UNIT;
	}

	public synchronized long getSeed() {
		return seed;
	}

	public synchronized void setSeed(long seed) {
		this.seed = seed;
	}

	@Override
	public XSTRandom clone() {
		return new XSTRandom(getSeed());
	}

	public int next(int nbits) {
		long x = seed;
		x ^= (x << 21);
		x ^= (x >>> 35);
		x ^= (x << 4);
		seed = x;
		x &= ((1L << nbits) - 1);
		return (int) x;
	}

	synchronized public double nextGaussian() {
		if (haveNextNextGaussian) {
			haveNextNextGaussian = false;
			return nextNextGaussian;
		} else {
			double v1, v2, s;
			do {
				v1 = 2 * nextDouble() - 1;
				v2 = 2 * nextDouble() - 1;
				s = v1 * v1 + v2 * v2;
			} while (s >= 1 || s == 0);
			double multiplier = StrictMath.sqrt(-2 * StrictMath.log(s) / s);
			nextNextGaussian = v2 * multiplier;
			haveNextNextGaussian = true;
			return v1 * multiplier;
		}
	}

	public int nextInt(int bound) {
		last = seed ^ (seed << 21);
		last ^= (last >>> 35);
		last ^= (last << 4);
		seed = last;
		int out = (int) last % bound;
		return (out < 0) ? -out : out;
	}

	public int nextInt() {
		return next(32);
	}

	public float nextFloat() {
		return next(24) * FLOAT_UNIT;
	}

	public long nextLong() {
		return ((long) (next(32)) << 32) + next(32);
	}

	public void nextBytes(byte[] bytes_arr) {
		for (int iba = 0, lenba = bytes_arr.length; iba < lenba; )
			for (int rndba = nextInt(),
				 nba = Math.min(lenba - iba, Integer.SIZE / Byte.SIZE);
				 nba-- > 0; rndba >>= Byte.SIZE)
				bytes_arr[iba++] = (byte) rndba;
	}
}
