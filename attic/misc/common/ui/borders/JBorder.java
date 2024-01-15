package common.ui.borders;


import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.border.*;

public class JBorder {
	protected static Hashtable table = new Hashtable();

	////////// UTILITY METHODS //////////

	private static String makeKey(String name) {
		return name + "()";
	}

	private static String makeKey(String name, int arg) {
		return name + "(" + arg + ")";
	}

	private static String makeKey(String name, int arg1, int arg2) {
		return name + "(" + arg1 + "," + arg2 + ")";
	}

	private static String makeKey(String name, int arg1, int arg2, int arg3) {
		return name + "(" + arg1 + "," + arg2 + "," + arg3 + ")";
	}

	private static String makeKey(String name, int arg1, int arg2, int arg3, int arg4) {
		return name + "(" + arg1 + "," + arg2 + "," + arg3 + "," + arg4 + ")";
	}

	private static String makeKey(String name, int arg1, int arg2, int arg3, int arg4,
                          		int arg5) {
		return name + "(" + arg1 + "," + arg2 + "," + arg3 + "," + arg4 + "," + arg5 + ")";
	}

	private static String makeKey(String name, int arg1, int arg2, int arg3, int arg4,
                          		int arg5, int arg6) {
		return name + "(" + arg1 + "," + arg2 + "," + arg3 + "," + arg4 + "," + arg5 +
       		"," + arg6 + ")";
	}

	private static int hash(Object object) {
		if (object == null)
			return 0;
		return object.hashCode();
	}

	////////// GROUP BORDERS //////////

	public static GroupBorder createGroupBorder() {
		return new GroupBorder();
	}

	public static Border createGroupBorder(Border[] list) {
		return new GroupBorder(list);
	}

	////////// STYLE BORDERS //////////

	public static Border createStyleBorder() {
		String key = makeKey("StyleBorder");
		if (!table.containsKey(key))
			table.put(key, new StyleBorder());
		return (Border) table.get(key);
	}

	public static Border createStyleBorder(int thickness) {
		String key = makeKey("StyleBorder", thickness);
		if (!table.containsKey(key))
			table.put(key, new StyleBorder(thickness));
		return (Border) table.get(key);
	}

	public static Border createStyleBorder(int thickness, int cap, int join) {
		String key = makeKey("StyleBorder", thickness, cap, join);
		if (!table.containsKey(key))
			table.put(key, new StyleBorder(thickness, cap, join));
		return (Border) table.get(key);
	}

	public static Border createStyleBorder(float[] dash) {
		String key = makeKey("StyleBorder", hash(dash));
		if (!table.containsKey(key))
			table.put(key, new StyleBorder(dash));
		return (Border) table.get(key);
	}

	public static Border createStyleBorder(int thickness, float[] dash) {
		String key = makeKey("StyleBorder", thickness, hash(dash));
		if (!table.containsKey(key))
			table.put(key, new StyleBorder(thickness, dash));
		return (Border) table.get(key);
	}

	public static Border createStyleBorder(int thickness, float[] dash, Color color) {
		String key = makeKey("StyleBorder", thickness, hash(dash), hash(color));
		if (!table.containsKey(key))
			table.put(key, new StyleBorder(thickness, dash, color));
		return (Border) table.get(key);
	}

	public static Border createStyleBorder(int thickness, int cap, int join, float[] dash,
                                   		Color color) {
		String key = makeKey("StyleBorder", thickness, cap, join, hash(dash), hash(color));
		if (!table.containsKey(key))
			table.put(key, new StyleBorder(thickness, cap, join, dash, color));
		return (Border) table.get(key);
	}

	////////// GROOVE BORDERS //////////

	public static Border createGrooveBorder() {
		String key = makeKey("GrooveBorder");
		if (!table.containsKey(key))
			table.put(key, new GrooveBorder());
		return (Border) table.get(key);
	}

	public static Border createGrooveBorder(int width) {
		String key = makeKey("GrooveBorder", width);
		if (!table.containsKey(key))
			table.put(key, new GrooveBorder(width));
		return (Border) table.get(key);
	}

	public static Border createGrooveBorder(int height, int width) {
		String key = makeKey("GrooveBorder", height, width);
		if (!table.containsKey(key))
			table.put(key, new GrooveBorder(height, width));
		return (Border) table.get(key);
	}

	public static Border createGrooveBorder(int type, int height, int width) {
		String key = makeKey("GrooveBorder", type, height, width);
		if (!table.containsKey(key))
			table.put(key, new GrooveBorder(type, height, width));
		return (Border) table.get(key);
	}

	////////// DRAG BORDERS //////////

	public static Border createDragBorder() {
		String key = makeKey("DragBorder");
		if (!table.containsKey(key))
			table.put(key, new DragBorder());
		return (Border) table.get(key);
	}

	public static Border createDragBorder(Color color) {
		String key = makeKey("DragBorder", hash(color));
		if (!table.containsKey(key))
			table.put(key, new DragBorder(color));
		return (Border) table.get(key);
	}

	public static Border createDragBorder(Color color, int thickness) {
		String key = makeKey("DragBorder", hash(color), thickness);
		if (!table.containsKey(key))
			table.put(key, new DragBorder(color, thickness));
		return (Border) table.get(key);
	}

	public static Border createDragBorder(Color color, int thickness, boolean corners,
                                  		boolean sides) {
		String key =
  		makeKey("DragBorder", hash(color), thickness, corners ? 1 : 0, sides ? 1 : 0);
		if (!table.containsKey(key))
			table.put(key, new DragBorder(color, thickness, corners, sides));
		return (Border) table.get(key);
	}

	////////// PATTERN BORDERS //////////

	public static Border createPatternBorder() {
		String key = makeKey("PatternBorder");
		if (!table.containsKey(key))
			table.put(key, new PatternBorder());
		return (Border) table.get(key);
	}

	public static Border createPatternBorder(int[] pattern) {
		String key = makeKey("PatternBorder", hash(pattern));
		if (!table.containsKey(key))
			table.put(key, new PatternBorder(pattern));
		return (Border) table.get(key);
	}

	public static Border createPatternBorder(int[] pattern, Color color) {
		String key = makeKey("PatternBorder", hash(pattern), hash(color));
		if (!table.containsKey(key))
			table.put(key, new PatternBorder(pattern, color));
		return (Border) table.get(key);
	}

	public static Border createPatternBorder(int[] pattern, Color color, int thickness) {
		String key = makeKey("PatternBorder", hash(pattern), hash(color), thickness);
		if (!table.containsKey(key))
			table.put(key, new PatternBorder(pattern, color, thickness));
		return (Border) table.get(key);
	}

	public static Border createPatternBorder(int top, int left, int bottom, int right,
    		int[] pattern) {
		String key = makeKey("PatternBorder", top, left, bottom, right, hash(pattern));
		if (!table.containsKey(key))
			table.put(key, new PatternBorder(top, left, bottom, right, pattern));
		return (Border) table.get(key);
	}

	public static Border createPatternBorder(int top, int left, int bottom, int right,
    		int[] pattern, Color color) {
		String key = makeKey("PatternBorder", top, left, bottom, right, hash(pattern),
                     		hash(color));
		if (!table.containsKey(key))
			table.put(key, new PatternBorder(top, left, bottom, right, pattern, color));
		return (Border) table.get(key);
	}

	////////// SHADOW BORDERS //////////

	public static Border createShadowBorder() {
		String key = makeKey("ShadowBorder");
		if (!table.containsKey(key))
			table.put(key, new ShadowBorder());
		return (Border) table.get(key);
	}

	public static Border createShadowBorder(int shadow) {
		String key = makeKey("ShadowBorder", shadow);
		if (!table.containsKey(key))
			table.put(key, new ShadowBorder(shadow));
		return (Border) table.get(key);
	}

	public static Border createShadowBorder(int shadow, Color color) {
		String key = makeKey("ShadowBorder", shadow, hash(color));
		if (!table.containsKey(key))
			table.put(key, new ShadowBorder(shadow, color));
		return (Border) table.get(key);
	}

	public static Border createShadowBorder(int shadow, Color color, int corner) {
		String key = makeKey("ShadowBorder", shadow, hash(color), corner);
		if (!table.containsKey(key))
			table.put(key, new ShadowBorder(shadow, color, corner));
		return (Border) table.get(key);
	}

	////////// EDGE BORDERS //////////

	public static Border createEdgeBorder() {
		String key = makeKey("EdgeBorder");
		if (!table.containsKey(key))
			table.put(key, new EdgeBorder());
		return (Border) table.get(key);
	}

	public static Border createEdgeBorder(int edge) {
		String key = makeKey("EdgeBorder", edge);
		if (!table.containsKey(key))
			table.put(key, new EdgeBorder(edge));
		return (Border) table.get(key);
	}

	public static Border createEdgeBorder(int type, int edge) {
		String key = makeKey("EdgeBorder", type, edge);
		if (!table.containsKey(key))
			table.put(key, new EdgeBorder(type, edge));
		return (Border) table.get(key);
	}

	////////// PAINT BORDERS //////////

	public static Border createPaintBorder() {
		String key = makeKey("PaintBorder");
		if (!table.containsKey(key))
			table.put(key, new PaintBorder());
		return (Border) table.get(key);
	}

	public static Border createPaintBorder(Paint paint, int thickness) {
		String key = makeKey("PaintBorder", hash(paint), thickness);
		if (!table.containsKey(key))
			table.put(key, new PaintBorder(paint, thickness));
		return (Border) table.get(key);
	}

	////////// GRADIENT BORDERS //////////

	public static Border createGradientBorder() {
		String key = makeKey("GradientBorder");
		if (!table.containsKey(key))
			table.put(key, new GradientBorder());
		return (Border) table.get(key);
	}

	public static Border createGradientBorder(int thickness) {
		String key = makeKey("GradientBorder", thickness);
		if (!table.containsKey(key))
			table.put(key, new GradientBorder(thickness));
		return (Border) table.get(key);
	}

	public static Border createGradientBorder(int thickness, Color outside, Color inside) {
		String key = makeKey("GradientBorder", thickness, hash(outside), hash(inside));
		if (!table.containsKey(key))
			table.put(key, new GradientBorder(thickness, outside, inside));
		return (Border) table.get(key);
	}

	////////// THREE D BORDERS //////////

	public static Border createThreeDBorder() {
		String key = makeKey("ThreeDBorder");
		if (!table.containsKey(key))
			table.put(key, new ThreeDBorder());
		return (Border) table.get(key);
	}

	public static Border createThreeDBorder(int type) {
		String key = makeKey("ThreeDBorder", type);
		if (!table.containsKey(key))
			table.put(key, new ThreeDBorder(type));
		return (Border) table.get(key);
	}

	public static Border createThreeDBorder(int type, int thickness) {
		String key = makeKey("ThreeDBorder", type, thickness);
		if (!table.containsKey(key))
			table.put(key, new ThreeDBorder(type, thickness));
		return (Border) table.get(key);
	}

	public static Border createThreeDBorder(int type, int thickness, Color highlight,
                                    		Color shadow) {
		String key = makeKey("ThreeDBorder", type, thickness, hash(highlight), hash(shadow));
		if (!table.containsKey(key))
			table.put(key, new ThreeDBorder(type, thickness, highlight, shadow));
		return (Border) table.get(key);
	}

	////////// ROUNDED BORDERS //////////

	public static Border createRoundedBorder() {
		String key = makeKey("RoundedBorder");
		if (!table.containsKey(key))
			table.put(key, new RoundedBorder());
		return (Border) table.get(key);
	}

	public static Border createRoundedBorder(int thickness) {
		String key = makeKey("RoundedBorder", thickness);
		if (!table.containsKey(key))
			table.put(key, new RoundedBorder(thickness));
		return (Border) table.get(key);
	}

	public static Border createRoundedBorder(Color color, int thickness) {
		String key = makeKey("RoundedBorder", hash(color), thickness);
		if (!table.containsKey(key))
			table.put(key, new RoundedBorder(color, thickness));
		return (Border) table.get(key);
	}

	public static Border createRoundedBorder(Color foreground, Color background,
    		int thickness, int corners, int sides) {
		String key = makeKey("RoundedBorder", hash(foreground), hash(background), thickness,
                     		corners, sides);
		if (!table.containsKey(key))
			table.put(key,
          			new RoundedBorder(foreground, background, thickness, corners, sides));
		return (Border) table.get(key);
	}

	////////// CURVED BORDERS //////////

	public static Border createCurvedBorder() {
		String key = makeKey("CurvedBorder");
		if (!table.containsKey(key))
			table.put(key, new CurvedBorder());
		return (Border) table.get(key);
	}

	public static Border createCurvedBorder(int thickness) {
		String key = makeKey("CurvedBorder", thickness);
		if (!table.containsKey(key))
			table.put(key, new CurvedBorder(thickness));
		return (Border) table.get(key);
	}

	public static Border createCurvedBorder(int type, int thickness) {
		String key = makeKey("CurvedBorder", type, thickness);
		if (!table.containsKey(key))
			table.put(key, new CurvedBorder(type, thickness));
		return (Border) table.get(key);
	}

	public static Border createCurvedBorder(int type, int curve, int thickness) {
		String key = makeKey("CurvedBorder", type, curve, thickness);
		if (!table.containsKey(key))
			table.put(key, new CurvedBorder(type, curve, thickness));
		return (Border) table.get(key);
	}

	public static Border createCurvedBorder(int type, int curve, int thickness, int percent) {
		String key = makeKey("CurvedBorder", type, curve, thickness, percent);
		if (!table.containsKey(key))
			table.put(key, new CurvedBorder(type, curve, thickness, percent));
		return (Border) table.get(key);
	}

	public static Border createCurvedBorder(int type, int curve, int thickness,
                                    		int percent, Color start) {
		String key = makeKey("CurvedBorder", type, curve, thickness, percent, hash(start));
		if (!table.containsKey(key))
			table.put(key, new CurvedBorder(type, curve, thickness, percent, start));
		return (Border) table.get(key);
	}

	// --------------------------------------------------------
	//  METHODS FOR BACKWARD-COMPATIBILITY WITH BorderFactory
	// --------------------------------------------------------

	////////// COMPOUND BORDERS //////////

	public static Border createCompoundBorder() {
		return new CompoundBorder();
	}

	public static Border createCompoundBorder(Border outside, Border inside) {
		return new CompoundBorder(outside, inside);
	}

	////////// BEVEL BORDERS //////////

	public static Border createBevelBorder() {
		return createBevelBorder(BevelBorder.LOWERED);
	}

	// For Swing compatibility
	public static Border createLoweredBevelBorder() {
		return createBevelBorder(BevelBorder.LOWERED);
	}

	// For Swing compatibility
	public static Border createRaisedBevelBorder() {
		return createBevelBorder(BevelBorder.RAISED);
	}

	public static Border createBevelBorder(int type) {
		String key = makeKey("BevelBorder", type);
		if (!table.containsKey(key))
			table.put(key, new BevelBorder(type));
		return (Border) table.get(key);
	}

	public static Border createBevelBorder(int type, Color highlight, Color shadow) {
		String key = makeKey("BevelBorder", type, hash(highlight), hash(shadow));
		if (!table.containsKey(key))
			table.put(key, new BevelBorder(type, highlight, shadow));
		return (Border) table.get(key);
	}

	public static Border createBevelBorder(int type, Color highlightOuter,
                                   		Color highlightInner, Color shadowOuter, Color shadowInner) {
		String key = makeKey("BevelBorder", type, hash(highlightOuter), hash(highlightInner),
                     		hash(shadowOuter), hash(shadowInner));
		if (!table.containsKey(key))
			table.put(key,
          			new BevelBorder(type, highlightOuter, highlightInner, shadowOuter,
                          			shadowInner));
		return (Border) table.get(key);
	}

	////////// EMPTY BORDERS //////////

	public static Border createEmptyBorder() {
		String key = makeKey("EmptyBorder");
		if (!table.containsKey(key))
			table.put(key, new EmptyBorder(0, 0, 0, 0));
		return (Border) table.get(key);
	}

	// Same size all around
	public static Border createEmptyBorder(int thickness) {
		String key = makeKey("EmptyBorder", thickness);
		if (!table.containsKey(key))
			table.put(key, new EmptyBorder(thickness, thickness, thickness, thickness));
		return (Border) table.get(key);
	}

	// Same top/bottom, left/right
	public static Border createEmptyBorder(int width, int height) {
		String key = makeKey("EmptyBorder", width, height);
		if (!table.containsKey(key))
			table.put(key, new EmptyBorder(height, width, height, width));
		return (Border) table.get(key);
	}

	public static Border createEmptyBorder(int top, int left, int bottom, int right) {
		String key = makeKey("EmptyBorder", top, left, bottom, right);
		if (!table.containsKey(key))
			table.put(key, new EmptyBorder(top, left, bottom, right));
		return (Border) table.get(key);
	}

	////////// ETCHED BORDERS //////////

	public static Border createEtchedBorder() {
		String key = makeKey("EtchedBorder");
		if (!table.containsKey(key))
			table.put(key, new EtchedBorder());
		return (Border) table.get(key);
	}

	// Missing from BorderFactory
	public static Border createEtchedBorder(int lighting) {
		String key = makeKey("EtchedBorder", lighting);
		if (!table.containsKey(key))
			table.put(key, new EtchedBorder(lighting));
		return (Border) table.get(key);
	}

	public static Border createEtchedBorder(Color highlight, Color shadow) {
		String key = makeKey("EtchedBorder", hash(highlight), hash(shadow));
		if (!table.containsKey(key))
			table.put(key, new EtchedBorder(highlight, shadow));
		return (Border) table.get(key);
	}

	// Missing from Borderfactory
	public static Border createEtchedBorder(int lighting, Color highlight, Color shadow) {
		String key = makeKey("EtchedBorder", lighting, hash(highlight), hash(shadow));
		if (!table.containsKey(key))
			table.put(key, new EtchedBorder(lighting, highlight, shadow));
		return (Border) table.get(key);
	}

	////////// LINE BORDERS //////////

	public static Border createLineBorder() {
		String key = makeKey("LineBorder");
		if (!table.containsKey(key))
			table.put(key, new LineBorder(Color.black));
		return (Border) table.get(key);
	}

	public static Border createLineBorder(Color color) {
		String key = makeKey("LineBorder", hash(color));
		if (!table.containsKey(key))
			table.put(key, new LineBorder(color));
		return (Border) table.get(key);
	}

	public static Border createLineBorder(Color color, int thickness) {
		String key = makeKey("LineBorder", hash(color), thickness);
		if (!table.containsKey(key))
			table.put(key, new LineBorder(color, thickness));
		return (Border) table.get(key);
	}

	////////// MATTE BORDERS //////////

	public static Border createMatteBorder(int top, int left, int bottom, int right,
                                   		Color color) {
		String key = makeKey("MatteBorder", top, left, bottom, right, hash(color));
		if (!table.containsKey(key))
			table.put(key, new MatteBorder(top, left, bottom, right, color));
		return (Border) table.get(key);
	}

	public static Border createMatteBorder(int top, int left, int bottom, int right,
                                   		Icon icon) {
		String key = makeKey("MatteBorder", top, left, bottom, right, hash(icon));
		if (!table.containsKey(key))
			table.put(key, new MatteBorder(top, left, bottom, right, icon));
		return (Border) table.get(key);
	}

	////////// TITLED BORDERS //////////

	public static Border createTitledBorder(Border border) {
		return new TitledBorder(border);
	}

	public static Border createTitledBorder(Border border, String title) {
		return new TitledBorder(border, title);
	}

	public static Border createTitledBorder(Border border, String title,
                                    		int titleJustification, int titlePosition) {
		return new TitledBorder(border, title, titleJustification, titlePosition);
	}

	public static Border createTitledBorder(Border border, String title,
                                    		int titleJustification, int titlePosition, Font titleFont) {
		return new TitledBorder(border, title, titleJustification, titlePosition, titleFont);
	}

	public static Border createTitledBorder(Border border, String title,
                                    		int titleJustification, int titlePosition, Font titleFont, Color titleColor) {
		return new TitledBorder(border, title, titleJustification, titlePosition,
                        		titleFont, titleColor);
	}

	public static Border createTitledBorder(String title) {
		return new TitledBorder(title);
	}
}
