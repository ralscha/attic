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
    import flash.events.MouseEvent;
    import mx.controls.PopUpMenuButton;
    import mx.core.mx_internal;
    import mx.controls.Menu;
    import mx.core.ClassFactory;
    import mx.events.MenuEvent;
    import flash.events.Event;

    [Event(name="menuItemClick", type="mx.events.MenuEvent")]
    public class extendedPopUpMenuButton extends PopUpMenuButton
    {

        public function extendedPopUpMenuButton():void{
            super();
            openAlways=true;

            addEventListener("open",handleOpenClose);
            addEventListener("close",handleOpenClose);

        }

        public var isOpen:Boolean;

        private function handleOpenClose(event:Event):void{

            switch (event.type){
                case "open":
                    isOpen=true;
                    break;
                case "close":
                    isOpen=false;
                    this.styleChanged("upSkin");
                    break;
            }

        }

        override public function close():void{
            super.close();

        }
        override public function open():void{
            super.open();

        }

        [Inspectable]
        public var iconField:String;

        override protected function clickHandler(event:MouseEvent):void
        {
            super.clickHandler(event);
        }


        override public function set dataProvider(value:Object):void{

            super.dataProvider = value;

        }

        private function handelItemClick(event:MenuEvent):void{
            //dispatch a specific menu event
            //bubble this so the parent navigator can receive it
            var menuEvent:MenuEvent = new MenuEvent("menuItemClick");
            menuEvent.menu = Menu(popUp);
            menuEvent.menu.selectedIndex = Menu(popUp).selectedIndex;
            menuEvent.item =  Menu(popUp).selectedItem
            menuEvent.itemRenderer = Menu(popUp).itemToItemRenderer(Menu(popUp).selectedItem);
            menuEvent.index = Menu(popUp).selectedIndex;
            dispatchEvent(menuEvent);

        }

        //override to block the default
        override public function setStyle(styleProp:String, newValue:*):void{}


    }
}