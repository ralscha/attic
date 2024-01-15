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
    import ch.ess.booking.entity.Hotel;
    import ch.ess.booking.entity.User;
    
    import flash.display.DisplayObject;
    
    import mx.containers.Form;
    import mx.controls.Alert;
    import mx.core.Application;
    import mx.events.ValidationResultEvent;
    import mx.managers.PopUpManager;
    import mx.validators.Validator;
    
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.seam.Context;


    [Bindable]
    public class BookingProcessCtl {
        
        private var wizard:HotelBookingWizard;
        
        private var selectionForm:Form;

        [In]
        public var hotel:Hotel;
        
        [In]
        public var user:User;
        
        [In]
        public var booking:Booking;
        
        [In]
        public var hotelBooking:Object;


        public function BookingProcessCtl(name:String, context:Context):void {
            // Needed for Flex 2 (no support for In annotation)
            context.bind(this, "hotelBooking");
            context.bind(this, "booking");
            context.bind(this, "hotel");
            context.bind(this, "user");
        }


        public function startBookingProcess(form:Form):void {
            this.selectionForm = form;
            hotelBooking.bookHotel(bookResult);
        }

        private function bookResult(event:TideResultEvent):void {
            wizard = PopUpManager.createPopUp(Application.application as DisplayObject, HotelBookingWizard, true) as HotelBookingWizard;
            wizard.bookingProcessCtl = this;

            PopUpManager.centerPopUp(wizard);
        }


        public function confirmBooking(step3:HotelBookingWizardStep3):void {
            if (validateStep3(step3))
                hotelBooking.confirm(confirmBookingResult);
        }

        private function confirmBookingResult(event:TideResultEvent):void {
            endBookingProcess();
            Alert.show(event.context.messages.getItemAt(0).summary);
        }


        public function cancelBooking():void {
            hotelBooking.cancel(cancelBookingResult);
        }

        private function cancelBookingResult(event:TideResultEvent):void {
            endBookingProcess();
        }
        
        public function endBookingProcess():void {
            if (wizard)
                PopUpManager.removePopUp(wizard);
            var form:Form = selectionForm;
            selectionForm = null;    // Must be before removeChild to avoid infinite loop
            if (form)
                form.parent.removeChild(form);
            wizard = null;
        }


        public function validateStep1(step1:HotelBookingWizardStep1) : Boolean {
            var validationResult:Boolean;

            validationResult = validate(step1.checkinDateValidator);
            validationResult = validationResult && validate(step1.checkoutDateValidator);
              if (booking.checkinDate > booking.checkoutDate) {
                  validationResult = false;
                  Alert.show("Checkout date can not be before checkin date.");
              }
            return validationResult;
        }

        public function validateStep2(step2:HotelBookingWizardStep2) : Boolean {
            var validationResult:Boolean;

            validationResult = validate(step2.address1Validator);
            validationResult = validate(step2.zipValidator) && validationResult;
            validationResult = validate(step2.phoneValidator) && validationResult;
            validationResult = validate(step2.emailValidator) && validationResult;
            return validationResult;
        }

        public function validateStep3(step3:HotelBookingWizardStep3) : Boolean {
            var validationResult:Boolean;

            validationResult = validate(step3.cardNamevalidator);
            validationResult = validate(step3.cardNumValidator) && validationResult;
            validationResult = validate(step3.creditcardExpMonthValidator) && validationResult;
            validationResult = validate(step3.creditcardExpYearValidator) && validationResult;

            return validationResult;
        }


        public function validate(validator:Validator):Boolean
        {
            // Get a reference to the component that is the
            // source of the validator.
             var validatorSource:DisplayObject = validator.source as DisplayObject;

            // Carry out validation. Returns a ValidationResultEvent.
            // Passing null for the first parameter makes the validator
            // use the property defined in the property tag of the
            // <mx:Validator> tag.
            var event:ValidationResultEvent = validator.validate(null, false);

            // Check if validation passed and return a boolean value accordingly
             var currentControlIsValid:Boolean = (event.type == ValidationResultEvent.VALID);

            return currentControlIsValid;
        }
    }
}
