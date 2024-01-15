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
 * An enum listing all the words used to compose poems.  Each element has
 * the actual text of the word as well as the default starting position.
 * Using an enum allows us to check that no one is slipping in their own
 * words.  Hessian handles enums easily.
 **/
public enum WordValue {
  I(5, 5, "I"), YOU(20, 5, "you"), AM(55, 5, "am"), ARE(90, 5, "are"), HAPPY(120, 5, "happy"), SAD(170, 5, "sad"),

  GOOD(5, 30, "good"), BAD(45, 30, "bad"), JUMP(80, 30, "jump"), OVER(130, 30, "over"), LEFT(170, 30, "left"),

  RIGHT(5, 60, "right"), UNDER(45, 60, "under"), HE(90, 60, "he"), SHE(115, 60, "she"), HIS(145, 60, "his"), HER(175,
      60, "her"),

  CAT(5, 90, "cat"), DOG(35, 90, "dog"), CAR(75, 90, "car"), APOS_S(105, 90, "'s"), LY(125, 90, "ly"), PHONE(145, 90,
      "phone"), QUESTION(190, 90, "?"),

  RUN(5, 120, "run"), S(35, 120, "s"), ING(55, 120, "ing"), THE(85, 120, "the"), IS(115, 120, "is"), IN(135, 120, "in"), WITH(
      155, 120, "with"), A(190, 120, "a"),

  IT(5, 150, "it"), AND(25, 150, "and"), HOUSE(60, 150, "house"), TABLE(105, 150, "table"), PUT(145, 150, "put"), DID(
      175, 150, "did");

  /** The default initial x value for this word. */
  private final int _x;

  /** The default initial y value for this word. */
  private final int _y;

  /** The text of this word. */
  private final String _text;

  /** The constructor used by the elements. */
  private WordValue(int x, int y, String text) {
    _x = x;
    _y = y;
    _text = text;
  }

  /** 
   * Gets the default initial x value for this word. 
   **/
  public int getX() {
    return _x;
  }

  /** 
   * Gets the default initial y value for this word. 
   **/
  public int getY() {
    return _y;
  }

  /** 
   * Gets the text of this word.
   **/
  public String getText() {
    return _text;
  }
}
