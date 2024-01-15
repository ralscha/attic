/*
 * Smart GWT (GWT for SmartClient)
 * Copyright 2008 and beyond, Isomorphic Software, Inc.
 *
 * Smart GWT is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License version 3
 * as published by the Free Software Foundation.  Smart GWT is also
 * available under typical commercial license terms - see
 * http://smartclient.com/license
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 */

package ch.ess.ex4u.client.gui;

import com.smartgwt.client.types.Overflow;
import com.smartgwt.client.widgets.HTMLPane;

public class InfoPane extends HTMLPane {
  
  static final String contents = 
    "Vivamus semper justo varius est congue consequat! Phasellus faucibus tincidunt adipiscing. Praesent tincidunt fringilla nunc, in tempor mauris sagittis volutpat. Duis felis sapien, malesuada faucibus semper elementum, congue eget est. In sit amet erat sit amet diam elementum iaculis a ac quam. Maecenas fermentum erat vel diam luctus sed porttitor elit pretium. In auctor est vitae tellus auctor auctor! Sed dui arcu, dictum et lacinia in; egestas vitae sem. Vivamus ut nisl quam. Mauris erat tortor, hendrerit eu convallis in, rhoncus nec ligula. Cras molestie eleifend risus vel viverra? Vestibulum at velit ac quam egestas gravida eu non quam. Quisque ullamcorper mauris sit amet lorem imperdiet sodales. Ut suscipit odio ac neque lobortis congue. Ut ut sapien augue, ac cursus dolor. Phasellus pulvinar purus sit amet augue viverra placerat. Aenean mattis turpis ut quam ultricies lacinia. Phasellus ullamcorper massa at nisi tincidunt a tincidunt urna placerat.\n" +
    "Fusce in orci luctus dui ultrices imperdiet. Sed fermentum, sem vel posuere eleifend, elit erat pulvinar nisi; in commodo mauris sem vitae nisl. Sed dictum consectetur mi! Donec hendrerit pretium dapibus. Nam iaculis diam in sapien vulputate ut convallis ante facilisis. Donec porttitor enim dictum sem ultricies molestie. Nunc vel adipiscing risus! Sed ultrices commodo eros, eget aliquet sapien pharetra at. Duis vitae leo nulla. Nunc sed lacus cursus lacus mattis consequat. Nunc pellentesque lorem eu arcu cursus interdum. Mauris vehicula tellus eget nulla porttitor ullamcorper. Donec in eros mauris?\n" +
    "Curabitur vel velit augue, non luctus purus. Vivamus justo quam, ultricies eget consectetur at; iaculis quis leo. Proin elementum tincidunt lacus eu ultrices. Proin nibh nisl, iaculis sit amet luctus eu, eleifend eget felis. Aliquam erat volutpat. Aenean nulla dui, tincidunt ac luctus et, iaculis nec purus. Aliquam erat volutpat. Donec placerat, metus id elementum tincidunt, leo ligula tincidunt nisl, lobortis convallis quam neque sit amet nulla. Vivamus ullamcorper dapibus risus. Mauris est risus; ullamcorper sit amet laoreet at, dapibus nec neque. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Phasellus tortor turpis, molestie lacinia dictum id, bibendum in tortor. Vivamus volutpat pharetra nisi id pellentesque. Proin auctor enim interdum nibh lacinia non elementum libero posuere. Nam ultrices sollicitudin sem. Nullam sed ante nec urna interdum ullamcorper quis ac nulla. Etiam at nibh metus. Nullam et dolor ac orci venenatis commodo non a sem. Donec ut iaculis augue.";

  public InfoPane() {

    //setContentsURL("http://code.google.com/webtoolkit/");
    
    setContents(contents);

    setOverflow(Overflow.AUTO);
    setStyleName("defaultBorder");
    setPadding(10);
  }
}