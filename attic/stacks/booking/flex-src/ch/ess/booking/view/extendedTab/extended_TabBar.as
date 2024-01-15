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
    import flash.display.DisplayObject;
    import flash.events.Event;
    import flash.events.MouseEvent;

    import mx.controls.Button;
    import mx.controls.Menu;
    import mx.controls.TabBar;
    import mx.core.ClassFactory;
    import mx.core.DragSource;
    import mx.core.IFlexDisplayObject;
    import mx.core.mx_internal;
    import mx.events.DragEvent;
    import mx.events.MenuEvent;
    import mx.managers.DragManager;

    use namespace mx_internal;

    public class extended_TabBar extends TabBar
    {

        private var listPopup:extendedPopUpMenuButton;

        public function extended_TabBar()
        {
            super();

            //set the factory to our specific tab
            navItemFactory = new ClassFactory(extended_Tab);
            addEventListener("creationComplete", handleCreationComplete );

        }

        //store an internal array of tabs to work with
        private var _itemData:Array = [];
        private function set itemData(value:Array):void{
            _itemData = value;
        }
        private function get itemData():Array{
            return _itemData
        }


        private function handleCreationComplete (event:Event):void{
            //make sure we get informed on the parent resize for the
            //popup positioning
            parent.addEventListener("resize", handleParentReSize);
            listPopup.addEventListener(MenuEvent.ITEM_CLICK,handleListDataItemClick);
        }

        private function handleListDataCreationComplete(event:Event):void{
            Menu(listPopup.popUp).dragEnabled = true;
            Menu(listPopup.popUp).dragMoveEnabled = true;
            Menu(listPopup.popUp).addEventListener(MouseEvent.MOUSE_MOVE,handleListDataMouseMove);
        }

        private function handleListDataMouseMove(event:MouseEvent):void{
            if (event.buttonDown && !DragManager.isDragging){
                DragFromItemList(event,"tabButton");
            }
        }

        /**
        * Set up a proxy and begin the drag operation.
        **/
        private function DragFromItemList(event:MouseEvent, format:String):void {


            var dragInitiator:Button=Button(getChildAt(event.currentTarget.selectedIndex));

            var ds:DragSource = new DragSource();
            ds.addData(dragInitiator,format);

            //create a proxy drag image based on current dragged tab
            var dragProxy:extended_Tab = new extended_Tab();
            dragProxy.label=dragInitiator.label;
            dragProxy.setStyle("icon",dragInitiator.getStyle("icon"));
            dragProxy.width = dragInitiator.width;
            dragProxy.height = dragInitiator.height;

            //get X position
            var proxyPointX:int =(dragInitiator.x+(dragInitiator.width/2))-event.stageX;

            //get Y position
            var proxyPointY:int;
            if(dragInitiator.contentMouseY > 0){
                //popup below
                proxyPointY=(dragInitiator.y - (event.currentTarget.contentMouseY+(dragInitiator.height/2)));
            }
            else{
                 //popup above
                 proxyPointY= event.currentTarget.height-(event.currentTarget.contentMouseY-(dragInitiator.height/2));
            }

            //start the drag operation
            DragManager.doDrag(dragInitiator, ds, event, dragProxy,proxyPointX,proxyPointY);

        }

        //on the menu click update both the tab bar and the parent (when tab not visible)
        private function handleListDataItemClick(event:MenuEvent):void{

            selectedIndex = event.index;

            if (getChildAt(event.index).visible==true){
                selectedIndex = event.index;
            }
            else{

                setChildIndex(getChildAt(event.index),0);
                selectedIndex = 0;

                validateVisibleTabs();

                if (owner is extended_TabNavigator){
                    extended_TabNavigator(owner).setChildIndex(owner.getChildAt(event.index),0);
                    extended_TabNavigator(owner).selectedIndex = 0;
                }
            }
        }




        override protected function createNavItem( label:String, icon:Class = null):IFlexDisplayObject{

            var navItem:IFlexDisplayObject = super.createNavItem(label,icon);
            reBuildItemData();


            return navItem;

        }

        public override function addChild(child:DisplayObject):DisplayObject{
            return super.addChild(child);
        }

        public override function setChildIndex(child:DisplayObject, newIndex:int):void{
            super.setChildIndex(child,newIndex);
            reBuildItemData();
        }

        public override function removeChildAt(index:int):DisplayObject{

            itemData.splice(index,1);
            listPopup.dataProvider = itemData;

            return super.removeChildAt(index);


        }

        //update the item data array
        private function reBuildItemData():void{

            itemData = new Array();
            for (var i:int;i<numChildren;i++){
                itemData.push({label:Button(getChildAt(i)).label, icon: Button(getChildAt(i)).getStyle("icon")})
            }

            listPopup.dataProvider = itemData;

        }

        //add the popupmenu button and position it
        override protected function createChildren():void{
            super.createChildren();

            listPopup = new extendedPopUpMenuButton();
            listPopup.styleName = "extendedPopUpMenuButton"
            listPopup.visible=false;
            listPopup.dataProvider = itemData;
            listPopup.showRoot =true;
            listPopup.width =17;
            listPopup.addEventListener("creationComplete",handleListDataCreationComplete);
            rawChildren.addChild(listPopup);


        }

        private function handleParentReSize(event:Event):void{
            validateVisibleTabs();
        }

        //validate visible tabs based on the parent width and update the
        //menupopup position
        public function validateVisibleTabs():void{

            listPopup.move(parent.width-listPopup.width-1, 0);
            listPopup.height = height;

            for (var i:int;i<numChildren;i++){
                var currTab:Button = Button(getChildAt(i));
                if ((currTab.x + currTab.width) >= parent.width-listPopup.width){
                    currTab.visible=false;
                    listPopup.visible = true;
                }
                else{
                    currTab.visible=true;
                    listPopup.visible = false;
                    listPopup.close();
                }
            }
        }

        //validate on display updates
        override protected function updateDisplayList(w:Number, h:Number):void{
            super.updateDisplayList(w,h);
            validateVisibleTabs();
        }

    }
}