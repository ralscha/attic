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
    import ch.ess.booking.entity.User;
    
    import flash.display.DisplayObject;
    import flash.events.Event;
    
    import mx.controls.Alert;
    import mx.logging.Log;
    import mx.logging.targets.TraceTarget;
    import mx.managers.PopUpManager;
    
    import org.granite.tide.events.TideFaultEvent;
    import org.granite.tide.events.TideResultEvent;
    import org.granite.tide.seam.Context;
    import org.granite.tide.seam.security.Identity;


    [Bindable]
    public class UserCtl {
    
        [In]
        public var identity:Identity = null; 

        [In]
        public var user:User = null; 
        
        [In]
        public var register:Object = null; 
        
        [In]
        public var changePassword:Object = null; 

        public var welcomeMessage:String = null;


        public function UserCtl(name:String, context:Context):void {
            // Flex 2
            context.bind(this, "identity");
            context.bind(this, "user");
            context.bind(this, "register");
            context.bind(this, "changePassword");
        }

        
        public function init():void {
            var t:TraceTarget = new TraceTarget();
            t.filters = ["org.granite.*"];
            Log.addTarget(t);
        }
        

        public function showLoginForm(form:DisplayObject):void {
            var loginPopUpWindow:loginPopUp = PopUpManager.createPopUp(form, loginPopUp, true) as loginPopUp;
            loginPopUpWindow.userCtl = this;
            PopUpManager.centerPopUp(loginPopUpWindow);
        }

        public function showRegistrationForm(form:DisplayObject):void {
            var registerPopUpWindow:RegistrationPopUp = PopUpManager.createPopUp(form, RegistrationPopUp, true) as RegistrationPopUp;
            registerPopUpWindow.userCtl = this;
            PopUpManager.centerPopUp(registerPopUpWindow);
        }

        public function showChangePasswordForm(form:DisplayObject) : void {
            var changePasswordPopUpWindow:ChangePasswordPopUp = PopUpManager.createPopUp(form, ChangePasswordPopUp, true) as ChangePasswordPopUp;
            changePasswordPopUpWindow.userCtl = this;
            PopUpManager.centerPopUp(changePasswordPopUpWindow);
        }

        public function authenticate(username:String, password:String):void {
            identity.username = username;
            identity.password = password;
            var tmp:* = identity.loggedIn;
            identity.login(authenticateResult, authenticateFault);
        }

        private function authenticateResult(event:TideResultEvent):void {
            welcomeMessage = event.context.messages.getItemAt(0).summary;
            dispatchEvent(new Event("loggedIn"));
        }

        private function authenticateFault(event:TideFaultEvent):void {
            if (event.context.messages && event.context.messages.length > 0)
                Alert.show("Authentication error : " + event.context.messages.getItemAt(0).summary);
        }


        public function logout():void	{
            identity.logout();
        }


        public function registerUser(username:String, password:String, verify:String, name:String):void	{
            user.name = name;
            user.username = username;
            user.password = password;
            register.verify = verify;
            var tmp:* = register.registered;    // Force evaluation of registered flag
            register.register(registerResult, registerFault);
        }

        public function registerResult(event:TideResultEvent):void {
            Alert.show(event.context.messages.getItemAt(0).summary);
            dispatchEvent(new Event("userRegistered"));
        }

        private function registerFault(event:TideFaultEvent):void {
            if (event.context.messages)
                Alert.show(event.context.messages.getItemAt(0).summary);
            else
                Alert.show(event.fault.toString());
        }


        public function changeUserPassword(password:String, verify:String):void {
            user.password = password;
            changePassword.verify = verify;
            changePassword.changePassword(changePasswordResult, changePasswordFault);
        }

        private function changePasswordResult(event:TideResultEvent):void {
            Alert.show(event.context.messages.getItemAt(0).summary);
            dispatchEvent(new Event("passwordChanged"));
        }

        private function changePasswordFault(event:TideFaultEvent):void {
            Alert.show(event.context.messages.getItemAt(0).summary);
        }
    }
}
