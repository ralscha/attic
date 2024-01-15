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

/**
 * A word described its current position and text.  This class implements
 * Serializable, but is actually serialized by Hessian when sent over the
 * wire, not the Java serializer.
 **/
public class Word implements java.io.Serializable, Comparable<Word> {

  /** The current x value. */
  private int _x;

  /** The current y value. */
  private int _y;

  /** The WordValue on which the Word is based. */
  private WordValue _value;

  /** A marker to indicate whether this word is used in the current poem. 
   *  If it is used, it will be sent over the wire.  The server does not
   *  need this value, so it is marked transient and not serialized with
   *  the rest of the object. */
  private transient boolean _used;

  /**
   * The constructor used by getDefaultWords() to create the initial set
   * of words.  
   **/
  private Word(WordValue value) {
    _x = value.getX();
    _y = value.getY();
    _value = value;
    _used = false;
  }

  /**
   * Default constructor.  Hessian requires a default, zero-argument 
   * constructor to create new instances when deserializing.  Hessian may
   * use constructors and methods of any access level, so we make this
   * one private to ensure that only Hessian uses it.
   **/
  private Word() {
    //no action
  }

  /**
   * Sets the WordValue for this Word.  Normally this is only done once in
   * the constructor, but for the purposes of Hessian deserialization, we
   * add this setter method.  Again, Hessian can use methods of any access
   * level, so we make this one private to ensure only Hessian can use it.
   **/
  private void setValue(WordValue value) {
    _value = value;
    _used = false;
  }

  /**
   * Gets the current x position.
   **/
  public int getX() {
    return _x;
  }

  /**
   * Sets the current x position.
   **/
  public void setX(int x) {
    _x = x;
  }

  /**
   * Gets the current y position.
   **/
  public int getY() {
    return _y;
  }

  /**
   * Sets the current y position.
   **/
  public void setY(int y) {
    _y = y;
  }

  /**
   * Gets the text of the word.
   **/
  public String getText() {
    return _value.getText();
  }

  /**
   * Indicates if this word is used in the current poem.
   **/
  public boolean isUsed() {
    return _used;
  }

  /**
   * Sets whether this word is used in the current poem.
   **/
  public void setUsed(boolean used) {
    _used = used;
  }

  /**
   * Resets the word to its initial position.
   **/
  public void reset() {
    _used = false;
    _x = _value.getX();
    _y = _value.getY();
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof Word))
      return false;

    if (o == this)
      return true;

    Word word = (Word)o;

    if (word.getX() != getX() || word.getY() != getY() || !word.getText().equals(getText()))
      return false;

    return true;
  }

  /**
   * Compares Words based on their WordValue.
   **/
  public int compareTo(Word word) {
    return getText().compareTo(word.getText());
  }

  /**
   * Creates a set of default words and places them in a WordSet.
   * The Words are initialized from all the elements in the WordValue enum.
   **/
  public static WordSet getDefaultWords() {
    WordSet wordSet = new WordSet();

    for (WordValue value : WordValue.values())
      wordSet.add(new Word(value));

    return wordSet;
  }
}
