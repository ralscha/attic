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

package ch.ess.booking.control
{
    import ch.ess.booking.entity.Booking;
    
    import mx.collections.ArrayCollection;
    import mx.controls.Alert;
    
    import org.granite.tide.events.TideContextEvent;
    import org.granite.tide.events.TideEvent;
    import org.granite.tide.events.TideLoginEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.seam.Context;


    [Bindable]
    public class BookingsCtl {

        [In]
        public var bookings:ArrayCollection;
        
        [In]
        public var bookingList:Object;


        public function BookingsCtl(componentName:String, context:Context):void {
            context.addContextEventListener(TideLoginEvent.LOGIN, loggedInHandler);
            
            // Flex 2
            context.addContextEventListener("bookingConfirmed", bookingConfirmedHandler);
            
            context.bind(this, "bookings");
            context.bind(this, "bookingList");
        }


        private function loggedInHandler(event:TideEvent):void {
            getBookings();
        }

        [Observer("bookingConfirmed")]
        private function bookingConfirmedHandler(event:TideContextEvent):void {
            // No need for remote call, event has been dispatched on the server and list is outjected
        }


        private function getBookings():void {
            bookingList.getBookings(bookingsResult);
        }

        private function bookingsResult(event:TideResultEvent):void {
        }


        public function cancelBooking(booking:Booking):void {
            bookingList.booking = booking;
            bookingList.cancel(cancelBookingResult);
        }

        private function cancelBookingResult(event:TideResultEvent) : void {
            Alert.show("Booking cancelled.");
        }
    }
}
