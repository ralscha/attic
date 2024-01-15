// Copyright 1999 MageLang Institute
// $Id: //depot/main/src/edu/modules/Collections/magercises/WordCount/Solution/WordCount.java#1 $
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

public class WordCount {
	final static Integer ONE = new Integer(1);
	final static String DELIMITERS = " \t\n\r\f.,<>\"\'=/";

	// Define a Map and create a HashMap
	Map wordMap = new HashMap();

	// Return the map
	public Map getMap() {
		return wordMap;
	}

	// Clear the map
	public void clear() {
		wordMap.clear();
	}

	public void readURL(URL url) {
		InputStream is = null;
		try {
			// Get InputStream
			is = url.openStream();
			// Convert to Reader
			Reader reader = new InputStreamReader(is);
			// Buffer Reader
			BufferedReader br = new BufferedReader(reader);
			// For each line
			String line = null;
			while ((line = br.readLine()) != null) {
				addWords(line);
			}
		} catch (IOException ioException) {
			System.err.println("Error processing: " + url);
		}
		finally { if (is != null) {
      			try {
          				is.close();
      			} catch (IOException ioException) {}
          		}
        		} }

	private void addWords(String line) {
		// Get map
		Map map = getMap();
		// Split words
		StringTokenizer tokenizer = new StringTokenizer(line, DELIMITERS);
		// For each word
		while (tokenizer.hasMoreTokens()) {
			// Get word
			String key = tokenizer.nextToken();

			Integer frequency = (Integer) map.get(key);
			if (frequency == null) {
				frequency = ONE;
			} else {
				int value = frequency.intValue();
				frequency = new Integer(value + 1);
			}
			map.put(key, frequency);			
		}
	}
}
