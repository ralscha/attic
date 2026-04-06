package ch.rasc.java8.array;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import it.unimi.dsi.fastutil.ints.IntArrayList;

@State(Scope.Thread)
public class Grow {

	private final Random rand = new Random();

	public static void main(String... args) throws RunnerException {
		Options opt = new OptionsBuilder().include(Grow.class.getSimpleName()).forks(1)
				.build();

		new Runner(opt).run();
	}

	@Benchmark
	public void useArrayList(Blackhole bh) {
		int testSize = this.rand.nextInt(30);
		List<Integer> array = new ArrayList<>(16);
		for (int i = 0; i < testSize; i++) {
			array.add(Integer.valueOf(i));
		}

		Integer[] finishedArray = array.toArray(new Integer[array.size()]);
		// int[] finishedArray = new int[testSize];
		// int i = 0;
		// for (Integer n : array) {
		// finishedArray[i++] = n;
		// }

		bh.consume(finishedArray);
	}

	@Benchmark
	public void useFastUtil(Blackhole bh) {
		int testSize = this.rand.nextInt(30);
		IntArrayList array = new IntArrayList(16);
		for (int i = 0; i < testSize; i++) {
			array.add(i);
		}

		int[] finishedArray = array.toIntArray();

		bh.consume(finishedArray);
	}

	@Benchmark
	public void selfBuiltArray(Blackhole bh) {
		int aCap = 16;
		int aIx;
		int[] aArray = new int[aCap];

		int testSize = this.rand.nextInt(30);
		// System.out.println(testSize);

		for (aIx = 0; aIx < testSize; aIx++) {
			if (aIx == aCap) {
				int[] newArray = new int[aCap *= 2];
				System.arraycopy(aArray, 0, newArray, 0, aArray.length);
				aArray = newArray;
			}
			aArray[aIx] = 1;
		}

		int[] aFinalArray = new int[aIx];
		System.arraycopy(aArray, 0, aFinalArray, 0, aIx);
		bh.consume(aFinalArray);

		// for (int i = 0; i < finishedArray.length; i++) {
		// System.out.printf("[%2d]: %d\n", i, finishedArray[i]);
		// }
	}

	@Benchmark
	public void selfBuiltArrayInteger(Blackhole bh) {
		int capacity = 16;
		int currentLocation = 0;
		Integer[] array = new Integer[capacity];

		int testSize = this.rand.nextInt(30);
		// System.out.println(testSize);

		for (int i = 0; i < testSize; i++) {
			if (currentLocation == capacity) {
				Integer[] newArray = new Integer[capacity *= 2];
				System.arraycopy(array, 0, newArray, 0, array.length);
				array = newArray;
			}
			array[currentLocation++] = Integer.valueOf(i);
		}

		Integer[] finishedArray = new Integer[testSize];
		System.arraycopy(array, 0, finishedArray, 0, testSize);
		bh.consume(finishedArray);

		// for (int i = 0; i < finishedArray.length; i++) {
		// System.out.printf("[%2d]: %d\n", i, finishedArray[i]);
		// }
	}
}
