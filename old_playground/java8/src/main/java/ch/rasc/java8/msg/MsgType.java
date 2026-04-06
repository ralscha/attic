package ch.rasc.java8.msg;

public enum MsgType {

	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), EIGHT(8), SIXTEEN(16),
	SEVENTEEN(17), THIRTYTWO(32), THIRTYTHREE(33), THIRTYFOUR(34), THIRTYFIVE(35),
	THIRTYSIX(36), FOURTYEIGHT(48), FOURTYNINE(49), FIFTY(50), SIXTYFOUR(64),
	SIXTYFIVE(65), SIXTYSIX(66), SIXTYSEVEN(67), SIXTYEIGHT(68), SIXTYNINE(69),
	SEVENTY(70);

	private final int typeId;

	private MsgType(int typeId) {
		this.typeId = typeId;
	}

	public int getTypeId() {
		return this.typeId;
	}

}
