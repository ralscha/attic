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
    import mx.skins.halo.HaloBorder;

    public class extended_TabListToolButtonSkin extends HaloBorder
    {
        private var offset:Number=0;

        public function extended_TabListToolButtonSkin()
        {
            super();

        }

        override protected function updateDisplayList(w:Number, h:Number):void{
            var alpha:Number =getStyle("alpha");
            var borderColor:uint =getStyle("borderColor");
            graphics.beginFill(borderColor,alpha);
            graphics.drawRect(0,0,w-offset,h);
            drawArrow(w,h);
        }

        private function drawArrow(w:Number, h:Number):void{
            if (this.parent is extendedPopUpMenuButton){

                //draw the drop down arrow taking into account the right padding
                //and use the text color for the arrow color
                var PaddingRight:Number = getStyle("paddingRight");
                var arrowColor:uint = getStyle("color");

                graphics.lineStyle(1,arrowColor,0);
                graphics.beginFill(arrowColor,1);
                graphics.moveTo((w-PaddingRight)-7/2, h/2+3);
                graphics.lineTo(w-PaddingRight, 9);
                graphics.lineTo((w-PaddingRight)-7, 9);
                graphics.lineTo((w-PaddingRight)-7/2,h/2+3);
                graphics.endFill();

            }

        }


    }
}