// Boyer.java fast string search.

package common.util;

/*
Fast string search (indexOf) using the Boyer-Moore
algorithm.

use:
import cmp.Boyer.Boyer;
...
Boyer b = new Boyer("dogcatwombat");
int where = b.indexOf("cat");
or
Boyer b = new Boyer();
b.setText("cowpighorse");
b.setPattern("pig");
int where = b.indexOf();
or
int where = Boyer.indexOf("dogcatwombat","cat");

Boyer-Moore is about twice as fast as String.indexOf when
the string you are searching in is 2K or over and the
pattern you are searching for is 4 characters or longer.

String.indexOf is particularly slow when the pattern begins
with a common letter such as "e". Boyer-Moore is fastest
when the pattern is long and composed only of uncommon
letters, e.g. "z" or "^". If you use a char[] instead of
String for your text to be searched, it will run an
additional 33% faster.

You don't have to worry which is faster. Boyer automatically
reverts to String.indexOf when that would be faster.


*/

/**
 * @author  copyright (c) 1998 Roedy Green of Canadian Mind Products
 * may be copied and used freely for any purpose but military.
 *
 * Roedy Green
 * Canadian Mind Products
 * 5317 Barker Avenue
 * Burnaby, BC Canada V5H 2N6
 * tel: (604) 435-3016
 * mailto:roedy@mindprod.com
 * http://mindprod.com
 *
 * version 1.1 1999 January 10
 *             - use simple String.indexOf for short patterns and texts
 *             - lazy evaluation of skip[] array, to avoid work of calculating it.
 *             - more comments.
 *             - lenPat and lenText now local variables.
 *             - more efficient code to catch the degenerate cases of null and 0-length strings.
 *             - unravel main loop slightly to avoid extra charAt.
 *             - now throw NullPointerExceptions on null arguments.
 *             - also support searches of char arrays.
 *
 * version 1.0 1999 January 8
 *
 */

/*
Futures:
- search given an InputStream
- search starting at a given offset
*/

import java.io.*;

public class Boyer {

	/**
	 * Pattern length under which might as well use String.indexOf
	 */
	private static final int breakEvenLenPat = 4;

	/**
	 * Text length under which might as well use String.indexOf
	 */
	private static final int breakEvenLenText = 2048;

	/**
	 * what we search for
	 */
	private String pattern;

	private String prevPattern;

	/**
	 * store pattern as a char array for efficient access.
	 */
	private char[] pat;


	/**
	 * what we search in
	 */
	private String text;

	/**
	 * what we search in, alternate form
	 */
	private char[] textArray;

	/**
	 * how much we can skip to right
	 * based on letter we find in the text corresponding to the
	 * end of the pattern after we find a mismatch.
	 */
	private int [] skip;

	/**
	 * default constructor.  Must use setText and setPattern afterward.
	 */
	public Boyer () {

	}

	/**
	  * constructor that also
	  * sets text to search in for subsequent indexOf searches.
	  * Must also provide a pattern before using indexOf with setPattern.
	  *
	  * @param text String to search in. may be "" but not null.
	  *
	  */
	public Boyer (String text) {
		setText(text);
	}

	/**
	  * constructor that also
	  * sets text to search in for subsequent indexOf searches.
	  * Must also provide a pattern before using indexOf with setPattern.
	  *
	  * @param text char array to search in. may be 0-length but not null.
	  *
	  */
	public Boyer (char[] text) {
		setText(text);
	}


	/**
	  * Set text to search in for subsequent indexOf searches.
	  *
	  * @param text String to search in. May be "" but not null.
	  */
	public void setText (String text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.text = text;
		this.textArray = null;
	}

	/**
	  * Set pattern to use for subsequent indexOf searches.
	  *
	  * @param pattern String to search for. May be "" but not null..
	  */
	public void setPattern (String pattern) {
		if (pattern == null) {
			throw new NullPointerException();
		}
		this.pattern = pattern;
	}

	/**
	  * Calculate how many chars you can skip
	  * to the right if you find a mismatch.
	  * It depends on what character is at
	  * the end of the word when you find a mismatch.
	  * We must match the pattern, char by char, right to left.
	  * Only called after degenerate cases,
	  * e.g. null, zero-length and 1-length Pattern are eliminated.
	  */
	private final void analysePattern () {
		if (pattern.equals(prevPattern)) {
			return;
		}
		int lenPat = pattern.length();
		// get pattern in fast-to-access charArray form
		pat = pattern.toCharArray();
		// Calculate how many slots we can skip to the right
		// depending on which char is at the end of the word
		// when we find a match.
		// Recycle old array if possible.
		if (skip == null)
			skip = new int [256];
		for (int i = 0; i < 256; i++) {
			skip[i] = lenPat;
		} // end for

		for (int i = 0; i < lenPat - 1; i++) {
			// The following line is the key to the whole algorithm.
			// It also deals with repeating letters in the pattern.
			// It works conservatively, considering only the last
			// instance of repeating letter.
			// We exclude the last letter of the pattern, because we are
			// only concerned with what to do on a mismatch.
			skip[pat[i] & 0xff] = lenPat - i - 1;
		} // end for
		prevPattern = pattern;
	} // end analysePattern

	/**
	 * Search for given pattern in string.
	 *
	 * @param text String to search in. May be "" but not null.
	 *
	 * @param pattern String to search for. May be "" but not null.
	 *
	 * @return 0-based offset in text, just like String.indexOf.
	 * -1 means not found.
	 */
	public static int indexOf(String text, String pattern) {
		return new Boyer(text).indexOf(pattern);
	} // end indexOf

	/**
	 * Search for given pattern in char array
	 *
	 * @param text char array to search in. May be "" but not null.
	 *
	 * @param pattern String to search for. May be "" but not null.
	 *
	 * @return 0-based offset in text, just like String.indexOf.
	 * -1 means not found.
	 */
	public static int indexOf(char [] text, String pattern) {
		return new Boyer(text).indexOf(pattern);
	} // end indexOf


	/**
	 * Set text to search in for subsequent indexOf searches.
	 *
	 * @param text char array to search in. May be empty but not null.
	 */
	public void setText (char[] text) {
		if (text == null) {
			throw new NullPointerException();
		}
		this.textArray = text;
		this.text = null;
	}

	/**
	   * Search for given pattern in string.
	   * Text must have been set previously by the constructor or setText.
	   *
	   * @param pattern String to search for. May be "" but not null.
	   *
	   * @return 0-based offset in text, just like String.indexOf.
	   * -1 means not found.
	   */
	public int indexOf(String pattern) {
		setPattern(pattern);
		return indexOf();
	} // end indexOF



	/**
	 * Search for given pattern in String or char array.
	 * Presume Pattern and Text have been previously set either
	 * with the constructor or with setText setPattern.
	 *
	 * @return 0-based offset in text, just like String.indexOf.
	 * -1 means not found.
	 */
	public final int indexOf() {
		if (text != null) {
			return indexOfviaText();
		} else {
			return indexOfviaTextArray();
		}

	} // end indexOf

	/**
	 * Search for given pattern in String.
	 * Presume Pattern and Text have been previously set either
	 * with the constructor or with setText setPattern.
	 *
	 * @return 0-based offset in text, just like String.indexOf.
	 * -1 means not found.
	 */
	private final int indexOfviaText() {
		// If either of text or pattern is null,
		// we will throw a NullPointerException
		int lenText = text.length();
		int lenPat = pattern.length();

		// Deal with cases that don't rate the full
		// Boyer-Moore treatment.
		if (lenText <= breakEvenLenText || lenPat <= breakEvenLenPat) {
			// this way we are consistent with
			// String.indexOf for "".indexOf("")
			// which is -1 in JDK 1.1
			// and 0 in JDK 1.2. See Bug Parade entry 4096273.
			// "".indexOf("abc") is always -1
			return text.indexOf(pattern);
		} // end if

		analysePattern();

		// At this point we have the pattern, and have skip[] calculated
		// We are commited to calculated the indexOf via Boyer-Moore.

		// tforward works left to right through the text, skipping depending
		// on what char it found in the text corresponding to the end of the pattern,
		// not to the place of the mismatch.
		char testChar = 0;
		final int lastPatChar = pat[lenPat - 1];
		outer:
		for (int tforward = lenPat - 1; tforward < lenText; tforward += skip[testChar & 0xff]) {
			// compare working right to left through both pattern and text
			testChar = text.charAt(tforward);
			if (testChar != lastPatChar) {
				continue outer;
			}
			// step back through pattern
			// step back through text
			inner:
			for (int tback = tforward - 1, pback = lenPat - 2 ; pback >= 0 ; tback--, pback--) {
				if (text.charAt(tback) != pat[pback])
					continue outer;
			} // end inner for
			// we stepped all the way back through the pattern comparing
			// without finding a mismatch. We found it!
			return tforward - lenPat + 1;
		} // end outer for
		// stepped through entire text without finding it.
		return -1;
	} // end indexOf

	/**
	 * Search for given pattern in charArray.
	 * presume Pattern and Text have been previously set either
	 * with the constructor or with setText setPattern.
	 *
	 * @return 0-based offset in text, just like String.indexOf.
	 * -1 means not found.
	 */
	private final int indexOfviaTextArray() {
		// If either of text or pattern is null,
		// we will throw a NullPointerException
		int lenText = textArray.length;
		int lenPat = pattern.length();

		// Deal with cases that don't rate the full
		// Boyer-Moore treatment.
		if (lenText <= breakEvenLenText / 2 || lenPat <= breakEvenLenPat) {
			// this way we are consistent with
			// String.indexOf for "".indexOf("")
			// which is -1 in JDK 1.1
			// and 0 in JDK 1.2
			// "".indexOf("abc") is always -1
			return new String (textArray).indexOf(pattern);
		} // end if

		analysePattern();

		// At this point we have the pattern, and have skip[] calculated
		// We are commited to calculated the indexOf via Boyer-Moore.

		// tforward works left to right through the text, skipping depending
		// on what char it found in the text corresponding to the end of the pattern,
		// not to the place of the mismatch.
		char testChar = 0;
		final int lastPatChar = pat[lenPat - 1];
		outer:
		for (int tforward = lenPat - 1; tforward < lenText; tforward += skip[testChar & 0xff]) {
			// compare working right to left through both pattern and text
			testChar = textArray[tforward];
			if (testChar != lastPatChar) {
				continue outer;
			}
			// step back through pattern
			// step back through text
			inner:
			for (int tback = tforward - 1, pback = lenPat - 2 ; pback >= 0 ; tback--, pback--) {
				if (textArray[tback] != pat[pback])
					continue outer;
			} // end inner for
			// we stepped all the way back through the pattern comparing
			// without finding a mismatch. We found it!
			return tforward - lenPat + 1;
		} // end outer for
		// stepped through entire text without finding it.
		return -1;
	} // end indexOf

} // end class Boyer




