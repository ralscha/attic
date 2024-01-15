/*
 * Copyright (c) 1998-2007 Caucho Technology -- all rights reserved
 *
 * This file is part of Resin(R) Open Source
 *
 * Each copy or derived work must preserve the copyright notice and this
 * notice unmodified.
 *
 * Resin Open Source is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Resin Open Source is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE, or any warranty
 * of NON-INFRINGEMENT.  See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Resin Open Source; if not, write to the
 *
 *   Free Software Foundation, Inc.
 *   59 Temple Place, Suite 330
 *   Boston, MA 02111-1307  USA
 *
 * @author Emil Ong
 */

package com.caucho.ria.examples.words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * A set of words.  Used by the WordService implementation to compare incoming
 * poems to make sure that duplicates are not submitted.  Used by the client
 * to extract only the words used in the poem to send to the server.
 * Instances of this class may be sent over the wire using Hessian.
 **/
public class WordSet implements java.io.Serializable {

  /** The Words in the set. */
  private final ArrayList<Word> _words;

  /** Indicates whether this set has been sorted for purposes of comparison. */
  private transient boolean _sorted = false;

  /** The IP address of the submitter, semi-anonymized. */
  private String _submitter;

  /**
   * Default constructor.
   **/
  public WordSet() {
    _words = new ArrayList<Word>();
  }

  /**
   * Basically a copy constructor used by the server.
   **/
  public WordSet(List<Word> list, String submitter) {
    if (list instanceof ArrayList) {
      _words = (ArrayList<Word>)list;
    } else {
      _words = new ArrayList<Word>();
      _words.addAll(list);
    }

    sort();

    setSubmitter(submitter);
  }

  /**
   * Sets the submitter.  Performs semi-anonymization by dropping the last
   * two bytes of the IP.
   **/
  public void setSubmitter(String submitter) {
    String[] components = submitter.split("\\.");

    if (components.length != 4)
      _submitter = submitter;

    else
      _submitter = components[0] + "." + components[1] + ".x.x";
  }

  /**
   * Gets the submitter.
   **/
  public String getSubmitter() {
    return _submitter;
  }

  /**
   * Adds a word to this set.
   **/
  public void add(Word word) {
    _words.add(word);
    _sorted = false;
  }

  /**
   * Returns a list of only the used Words in this set.  Used by the client
   * to send only the words used in a poem to the server.
   **/
  public List<Word> getUsedWords() {
    List<Word> used = new ArrayList<Word>();

    for (Word word : _words) {
      if (word.isUsed())
        used.add(word);
    }

    return used;
  }

  /**
   * Gets the Words in the set.
   **/
  public Iterator<Word> getWords() {
    sort();

    return _words.iterator();
  }

  /**
   * Gets the number of Words in the set.
   **/
  public int size() {
    return _words.size();
  }

  /**
   * Checks this WordSet against another for equality.
   **/
  @Override
  public boolean equals(Object o) {
    if (!(o instanceof WordSet))
      return false;

    if (o == this)
      return true;

    WordSet other = (WordSet)o;

    if (other._words.size() != _words.size())
      return false;

    sort();
    other.sort();

    for (int i = 0; i < _words.size(); i++) {
      Word word1 = _words.get(i);
      Word word2 = other._words.get(i);

      if (!word1.equals(word2))
        return false;
    }

    return true;
  }

  /**
   * Sorts the set if necessary for comparison purposes.
   **/
  private void sort() {
    if (!_sorted)
      Collections.sort(_words);

    _sorted = true;
  }
}
