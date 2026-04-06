package ch.rasc.java8;

import ch.rasc.java8.msg.Msg;
import ch.rasc.java8.msg.Msg1;
import ch.rasc.java8.msg.Msg16;
import ch.rasc.java8.msg.Msg17;
import ch.rasc.java8.msg.Msg2;
import ch.rasc.java8.msg.Msg3;
import ch.rasc.java8.msg.Msg32;
import ch.rasc.java8.msg.Msg33;
import ch.rasc.java8.msg.Msg34;
import ch.rasc.java8.msg.Msg35;
import ch.rasc.java8.msg.Msg36;
import ch.rasc.java8.msg.Msg4;
import ch.rasc.java8.msg.Msg48;
import ch.rasc.java8.msg.Msg49;
import ch.rasc.java8.msg.Msg5;
import ch.rasc.java8.msg.Msg6;
import ch.rasc.java8.msg.Msg64;
import ch.rasc.java8.msg.Msg65;
import ch.rasc.java8.msg.Msg66;
import ch.rasc.java8.msg.Msg67;
import ch.rasc.java8.msg.Msg68;
import ch.rasc.java8.msg.Msg69;
import ch.rasc.java8.msg.Msg70;
import ch.rasc.java8.msg.Msg8;
import ch.rasc.java8.msg.MsgType;

public class MsgBenchmark {
	private final static Msg m70 = new Msg70();
	private final static Msg m1 = new Msg1();

	// @State(Scope.Benchmark)
	public static class BenchmarkState {
		public Msg m6 = new Msg6();
	}

	// @Benchmark
	// @BenchmarkMode(Mode.Throughput)
	// @OutputTimeUnit(TimeUnit.MICROSECONDS)
	public boolean instanceOfOne(BenchmarkState bs) {
		return bs.m6 instanceof Msg6;
	}

	// @Benchmark
	// @BenchmarkMode(Mode.Throughput)
	// @OutputTimeUnit(TimeUnit.MICROSECONDS)
	public boolean enumOne(BenchmarkState bs) {
		return bs.m6.getType() == MsgType.SIX;
	}

	// @Benchmark
	// @BenchmarkMode(Mode.Throughput)
	// @OutputTimeUnit(TimeUnit.MICROSECONDS)
	public boolean instanceOfTest70() {

		if (m70 instanceof Msg1) {
			return false;
		}
		else if (m70 instanceof Msg2) {
			return false;
		}
		else if (m70 instanceof Msg3) {
			return false;
		}
		else if (m70 instanceof Msg4) {
			return false;
		}
		else if (m70 instanceof Msg5) {
			return false;
		}
		else if (m70 instanceof Msg6) {
			return false;
		}
		else if (m70 instanceof Msg8) {
			return false;
		}
		else if (m70 instanceof Msg16) {
			return false;
		}
		else if (m70 instanceof Msg17) {
			return false;
		}
		else if (m70 instanceof Msg32) {
			return false;
		}
		else if (m70 instanceof Msg33) {
			return false;
		}
		else if (m70 instanceof Msg34) {
			return false;
		}
		else if (m70 instanceof Msg35) {
			return false;
		}
		else if (m70 instanceof Msg36) {
			return false;
		}
		else if (m70 instanceof Msg48) {
			return false;
		}
		else if (m70 instanceof Msg49) {
			return false;
		}
		else if (m70 instanceof Msg64) {
			return false;
		}
		else if (m70 instanceof Msg65) {
			return false;
		}
		else if (m70 instanceof Msg66) {
			return false;
		}
		else if (m70 instanceof Msg67) {
			return false;
		}
		else if (m70 instanceof Msg68) {
			return false;
		}
		else if (m70 instanceof Msg69) {
			return false;
		}
		else if (m70 instanceof Msg70) {
			return true;
		}

		return false;
	}

	// @Benchmark
	// @BenchmarkMode(Mode.Throughput)
	// @OutputTimeUnit(TimeUnit.MICROSECONDS)
	public boolean instanceOfTest1() {

		if (m1 instanceof Msg1) {
			return false;
		}
		else if (m1 instanceof Msg2) {
			return false;
		}
		else if (m1 instanceof Msg3) {
			return false;
		}
		else if (m1 instanceof Msg4) {
			return false;
		}
		else if (m1 instanceof Msg5) {
			return false;
		}
		else if (m1 instanceof Msg6) {
			return false;
		}
		else if (m1 instanceof Msg8) {
			return false;
		}
		else if (m1 instanceof Msg16) {
			return false;
		}
		else if (m1 instanceof Msg17) {
			return false;
		}
		else if (m1 instanceof Msg32) {
			return false;
		}
		else if (m1 instanceof Msg33) {
			return false;
		}
		else if (m1 instanceof Msg34) {
			return false;
		}
		else if (m1 instanceof Msg35) {
			return false;
		}
		else if (m1 instanceof Msg36) {
			return false;
		}
		else if (m1 instanceof Msg48) {
			return false;
		}
		else if (m1 instanceof Msg49) {
			return false;
		}
		else if (m1 instanceof Msg64) {
			return false;
		}
		else if (m1 instanceof Msg65) {
			return false;
		}
		else if (m1 instanceof Msg66) {
			return false;
		}
		else if (m1 instanceof Msg67) {
			return false;
		}
		else if (m1 instanceof Msg68) {
			return false;
		}
		else if (m1 instanceof Msg69) {
			return false;
		}
		else if (m1 instanceof Msg70) {
			return true;
		}

		return false;
	}

	// @Benchmark
	// @BenchmarkMode(Mode.Throughput)
	// @OutputTimeUnit(TimeUnit.MICROSECONDS)
	public boolean enumTest1() {
		MsgType type = m1.getType();
		if (type == MsgType.ONE) {
			return true;
		}
		else if (type == MsgType.TWO) {
			return false;
		}
		else if (type == MsgType.THREE) {
			return false;
		}
		else if (type == MsgType.FOUR) {
			return false;
		}
		else if (type == MsgType.FIVE) {
			return false;
		}
		else if (type == MsgType.SIX) {
			return false;
		}
		else if (type == MsgType.EIGHT) {
			return false;
		}
		else if (type == MsgType.SIXTEEN) {
			return false;
		}
		else if (type == MsgType.SEVENTEEN) {
			return false;
		}
		else if (type == MsgType.THIRTYTWO) {
			return false;
		}
		else if (type == MsgType.THIRTYTHREE) {
			return false;
		}
		else if (type == MsgType.THIRTYFOUR) {
			return false;
		}
		else if (type == MsgType.THIRTYFIVE) {
			return false;
		}
		else if (type == MsgType.THIRTYSIX) {
			return false;
		}
		else if (type == MsgType.FOURTYEIGHT) {
			return false;
		}
		else if (type == MsgType.FOURTYNINE) {
			return false;
		}
		else if (type == MsgType.SIXTYFOUR) {
			return false;
		}
		else if (type == MsgType.SIXTYFIVE) {
			return false;
		}
		else if (type == MsgType.SIXTYSIX) {
			return false;
		}
		else if (type == MsgType.SIXTYSEVEN) {
			return false;
		}
		else if (type == MsgType.SIXTYEIGHT) {
			return false;
		}
		else if (type == MsgType.SIXTYNINE) {
			return false;
		}
		else if (type == MsgType.SEVENTY) {
			return false;
		}

		return false;
	}

	// @Benchmark
	// @BenchmarkMode(Mode.Throughput)
	// @OutputTimeUnit(TimeUnit.MICROSECONDS)
	public boolean enumTest70() {
		MsgType type = m70.getType();
		if (type == MsgType.ONE) {
			return false;
		}
		else if (type == MsgType.TWO) {
			return false;
		}
		else if (type == MsgType.THREE) {
			return false;
		}
		else if (type == MsgType.FOUR) {
			return false;
		}
		else if (type == MsgType.FIVE) {
			return false;
		}
		else if (type == MsgType.SIX) {
			return false;
		}
		else if (type == MsgType.EIGHT) {
			return false;
		}
		else if (type == MsgType.SIXTEEN) {
			return false;
		}
		else if (type == MsgType.SEVENTEEN) {
			return false;
		}
		else if (type == MsgType.THIRTYTWO) {
			return false;
		}
		else if (type == MsgType.THIRTYTHREE) {
			return false;
		}
		else if (type == MsgType.THIRTYFOUR) {
			return false;
		}
		else if (type == MsgType.THIRTYFIVE) {
			return false;
		}
		else if (type == MsgType.THIRTYSIX) {
			return false;
		}
		else if (type == MsgType.FOURTYEIGHT) {
			return false;
		}
		else if (type == MsgType.FOURTYNINE) {
			return false;
		}
		else if (type == MsgType.SIXTYFOUR) {
			return false;
		}
		else if (type == MsgType.SIXTYFIVE) {
			return false;
		}
		else if (type == MsgType.SIXTYSIX) {
			return false;
		}
		else if (type == MsgType.SIXTYSEVEN) {
			return false;
		}
		else if (type == MsgType.SIXTYEIGHT) {
			return false;
		}
		else if (type == MsgType.SIXTYNINE) {
			return false;
		}
		else if (type == MsgType.SEVENTY) {
			return true;
		}

		return false;
	}

	// @Benchmark
	// @BenchmarkMode(Mode.Throughput)
	// @OutputTimeUnit(TimeUnit.MICROSECONDS)
	public boolean switchEnum70() {
		switch (m70.getType()) {
		case ONE:
			return false;
		case TWO:
			return false;
		case THREE:
			return false;
		case FOUR:
			return false;
		case FIVE:
			return false;
		case SIX:
			return false;
		case EIGHT:
			return false;
		case SIXTEEN:
			return false;
		case SEVENTEEN:
			return false;
		case THIRTYTWO:
			return false;
		case THIRTYTHREE:
			return false;
		case THIRTYFOUR:
			return false;
		case THIRTYFIVE:
			return false;
		case THIRTYSIX:
			return false;
		case FOURTYEIGHT:
			return false;
		case FOURTYNINE:
			return false;
		case FIFTY:
			return false;
		case SIXTYFOUR:
			return false;
		case SIXTYFIVE:
			return false;
		case SIXTYSIX:
			return false;
		case SIXTYSEVEN:
			return false;
		case SIXTYEIGHT:
			return false;
		case SIXTYNINE:
			return false;
		case SEVENTY:
			return true;
		default:
			return false;
		}
	}

	// @Benchmark
	// @BenchmarkMode(Mode.Throughput)
	// @OutputTimeUnit(TimeUnit.MICROSECONDS)
	public boolean switchEnum1() {
		switch (m1.getType()) {
		case ONE:
			return true;
		case TWO:
			return false;
		case THREE:
			return false;
		case FOUR:
			return false;
		case FIVE:
			return false;
		case SIX:
			return false;
		case EIGHT:
			return false;
		case SIXTEEN:
			return false;
		case SEVENTEEN:
			return false;
		case THIRTYTWO:
			return false;
		case THIRTYTHREE:
			return false;
		case THIRTYFOUR:
			return false;
		case THIRTYFIVE:
			return false;
		case THIRTYSIX:
			return false;
		case FOURTYEIGHT:
			return false;
		case FOURTYNINE:
			return false;
		case FIFTY:
			return false;
		case SIXTYFOUR:
			return false;
		case SIXTYFIVE:
			return false;
		case SIXTYSIX:
			return false;
		case SIXTYSEVEN:
			return false;
		case SIXTYEIGHT:
			return false;
		case SIXTYNINE:
			return false;
		case SEVENTY:
			return false;
		default:
			return false;
		}
	}
}
