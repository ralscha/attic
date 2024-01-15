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

import java.net.MalformedURLException;
import java.util.List;
import com.caucho.hessian.client.HessianProxyFactory;

/**
 * A small wrapper around the WordService client proxy.  This class
 * initializes the proxy and does some minor formatting.
 **/
public class WordClient {

  /** An instance of the client used by the JavaFX script. */
  public static WordClient CLIENT = new WordClient("http://localhost:8080/words");

  /** The URL of the service. */
  private String _url;

  /** The client proxy for the WordService. */
  private WordService _service;

  /** A constructor which takes a URL string. */
  private WordClient(String url) {
    _url = url;
  }

  /** Sets the static client instance's server URL. */
  public static void setServerURL(String server) {
    CLIENT = new WordClient(server);
  }

  /**
   * Retrieves the WordService client proxy, creating it if necessary.
   **/
  private WordService getService() {
    if (_service == null) {
      HessianProxyFactory factory = new HessianProxyFactory();

      try {
        _service = (WordService)factory.create(WordService.class, _url);
      } catch (MalformedURLException e) {
        e.printStackTrace();
      }
    }

    return _service;
  }

  /**
   * Wraps the submit call on the WordService by extracting the used words
   * from a WordSet.
   **/
  public void submit(WordSet words) {
    List<Word> used = words.getUsedWords();

    if (used.size() > 0)
      getService().submit(used);
  }

  /**
   * Gets the recent words using the WordService proxy.
   * 
   **/
  public List<WordSet> getRecent() {
    return getService().getRecent();
  }
}
