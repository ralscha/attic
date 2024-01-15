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

package ch.ess.booking.control {

    import ch.ess.booking.entity.Hotel;
    
    import mx.collections.ArrayCollection;
    import mx.containers.Form;
    import mx.containers.TabNavigator;
    import mx.controls.Alert;
    import mx.events.FlexEvent;
    
    import org.granite.tide.events.TideLoginEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.seam.Context;
    import org.granite.tide.seam.security.Identity;


    [Bindable]
    public class HotelsCtl {

        public var pageSizes:ArrayCollection = new ArrayCollection([ 5, 10, 20 ]);

        public var tabNav:TabNavigator;
        public var selectedHotels:Array = new Array();

        [In]
        public var hotels:ArrayCollection;
        
        [In]
        public var hotelSearch:Object;
        
        [In]
        public var hotelBooking:Object;


        public function HotelsCtl(name:String, context:Context):void {
            context.addContextEventListener(TideLoginEvent.LOGOUT, logoutHandler);
            // Needed for Flex 2 (no support for In annotation)
            context.bind(this, "hotels");
            context.bind(this, "hotelSearch");
            context.bind(this, "hotelBooking");
        }


        public function searchForHotels(searchString:String, pageSize:uint):void	{
            // create a worker who will go get some data
            // pass it a reference to this command so the delegate knows where to return the data
            hotelSearch.searchString = searchString;
            hotelSearch.pageSize = pageSize;
            hotelSearch.find();
        }


        public function selectHotel(tabNav:TabNavigator, hotel:Hotel):void {
            this.tabNav = tabNav;

            if (hotel != null) {
                if (Identity.instance().loggedIn) {
                    var hotelForm:HotelSelection = selectedHotels[hotel.id] as HotelSelection;
                    if (hotelForm != null) {
                        tabNav.selectedChild = hotelForm;
                        return;
                    }

                    hotelBooking.selectHotel(hotel, selectHotelResult);
                }
                else
                    Alert.show("You need to login first to proceed..");
            }
            else {
                Alert.show("Click on the hotel record to proceed..");
            }
        }


        private function selectHotelResult(event:TideResultEvent):void {
            var hotelSelectionForm:HotelSelection = new HotelSelection();
              //Finally add VBOX to TabNavigator
            tabNav.addChild(hotelSelectionForm);
            hotelSelectionForm.label = event.context.hotel.name;
            var bookingProcessCtl:BookingProcessCtl = event.context.bookingProcessCtl as BookingProcessCtl;
            //Initialize the data
            hotelSelectionForm.initializeData("images/hotel_thumbs/hotel1.jpg", bookingProcessCtl);
            
            selectedHotels[bookingProcessCtl.hotel.id] = hotelSelectionForm;
            hotelSelectionForm.addEventListener(FlexEvent.REMOVE, function(event:FlexEvent):void {
                selectedHotels[event.target.bookingProcessCtl.hotel.id] = null;
            });

            //Make the selected hotel as default
            tabNav.selectedChild = hotelSelectionForm;
        }
        
        
        private function logoutHandler(event:TideLoginEvent):void {
            for each (var form:Form in selectedHotels) {
                if (form)
                    tabNav.removeChild(form);
            }
            
            selectedHotels = new Array();
        }
    }
}