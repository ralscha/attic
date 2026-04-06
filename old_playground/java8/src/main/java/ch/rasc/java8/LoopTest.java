package ch.rasc.java8;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//@State(Scope.Thread)
public class LoopTest {
	private final List<Shape> shapes = new ArrayList<>();

	// @Setup(Level.Invocation)
	public void init() {
		Color[] twoColors = new Color[] { Color.BLACK, Color.BLUE };
		for (int i = 0; i < 1_000_000; i++) {
			this.shapes.add(new Shape(twoColors[i % 2]));
		}
	}

	// @Benchmark
	public void loop() throws Exception {
		for (Shape s : this.shapes) {
			if (s.getColor() == Color.BLUE) {
				s.setColor(Color.RED);
			}
		}
	}

	// @Benchmark
	public void iterator() throws Exception {
		Iterator<Shape> it = this.shapes.iterator();
		while (it.hasNext()) {
			Shape s = it.next();
			if (s.getColor() == Color.BLUE) {
				s.setColor(Color.RED);
			}
		}
	}

	// @Benchmark
	public void lambdaStreamEmbeddedIf() throws Exception {
		this.shapes.stream().forEach(s -> {
			if (s.getColor() == Color.BLUE) {
				s.setColor(Color.RED);
			}
		});
	}

	// @Benchmark
	public void lambdaStream() throws Exception {
		this.shapes.stream().filter(s -> s.getColor() == Color.BLUE)
				.forEach(s -> s.setColor(Color.RED));
	}

	// @Benchmark
	public void lambdaParallel() throws Exception {
		this.shapes.parallelStream().filter(s -> s.getColor() == Color.BLUE)
				.forEach(s -> s.setColor(Color.RED));
	}
}