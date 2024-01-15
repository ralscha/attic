package common.ui.borders;


public interface BorderConstants {
	// We use same integers values as EtchedBorder
	public static final int RAISED = 0;
	public static final int LOWERED = 1;

	public static final int ROUNDED = 0;
	public static final int PLATEAU = 1;

	public static final int NONE = 0;

	public static final int N_SIDE = 2;
	public static final int S_SIDE = 4;
	public static final int E_SIDE = 8;
	public static final int W_SIDE = 16;

	public static final int NW_CORNER = 32;
	public static final int SW_CORNER = 64;
	public static final int NE_CORNER = 128;
	public static final int SE_CORNER = 256;

	public static final int ALL_SIDES = N_SIDE + S_SIDE + E_SIDE + W_SIDE;
	public static final int ALL_CORNERS = NW_CORNER + SW_CORNER + NE_CORNER + SE_CORNER;
}