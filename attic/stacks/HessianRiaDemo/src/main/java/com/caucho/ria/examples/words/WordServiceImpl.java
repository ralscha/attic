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
import java.util.List;
import javax.servlet.ServletRequest;
import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import com.caucho.services.server.ServiceContext;

/**
 * An implementation of the WordService.  The WordService accepts poems from
 * clients and if they are well-formed, they are added to a list of 10 most
 * recent poems.  Once 10 poems have been collected, any new poem accepted
 * replaces one of the old poems.
 **/
@Name("wordService")
@Scope(ScopeType.APPLICATION)
public class WordServiceImpl implements WordService {

  /** The list of recent poems. */
  private List<WordSet> _recent = Collections.synchronizedList(new ArrayList<WordSet>());

  /** The number of words defined in our enum. */
  private static final int NUMBER_OF_WORDS = WordValue.values().length;

  public void submit(List<Word> words) {
    System.out.println("SUBMIT");
    // Check for illegal submissions
    if (words.size() == 0)
      return;

    if (words.size() > NUMBER_OF_WORDS)
      return;

    for (Word word : words) {
      if (word.getX() < 202 || word.getX() > 404)
        return;

      if (word.getY() < 0 || word.getY() > 199)
        return;

      word.setX(word.getX() - 202);
    }

    ServletRequest request = ServiceContext.getContextRequest();

    WordSet wordSet = new WordSet(words, request.getRemoteAddr());

    for (int i = 0; i < _recent.size(); i++) {
      if (_recent.get(i).equals(wordSet))
        return;
    }

    if (_recent.size() >= 10)
      _recent.remove(0);

    _recent.add(wordSet);
  }

  public List<WordSet> getRecent() {
    return _recent;
  }
}
