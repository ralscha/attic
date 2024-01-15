/*
  GRANITE DATA SERVICES
  Copyright (C) 2007-2008 ADEQUATE SYSTEMS SARL

  This file is part of Granite Data Services.

  Granite Data Services is free software; you can redistribute it and/or modify
  it under the terms of the GNU Lesser General Public License as published by
  the Free Software Foundation; either version 3 of the License, or (at your
  option) any later version.

  Granite Data Services is distributed in the hope that it will be useful, but
  WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
  FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
  for more details.

  You should have received a copy of the GNU Lesser General Public License
  along with this library; if not, see <http://www.gnu.org/licenses/>.
*/

package ch.ess.booking.view.extendedTab
{
    import mx.skins.ProgrammaticSkin;
    import flash.display.Graphics;
    import mx.utils.ColorUtil;


    public class extended_TabDropIndicator extends ProgrammaticSkin
    {
        public function extended_TabDropIndicator()
        {
            super();

        }

        override protected function updateDisplayList(w:Number, h:Number):void{
            super.updateDisplayList(w, h);

            //eventually use a style setting
            var dropIndicatorColor:uint =0x000000;

            graphics.lineStyle(0, 0, 0);
            graphics.beginFill(dropIndicatorColor);

            graphics.moveTo (4, 0);
            graphics.lineTo (0, 4);
            graphics.lineTo (-4, 0);
            graphics.lineTo (4,0);
            graphics.endFill();



        }
    }
}