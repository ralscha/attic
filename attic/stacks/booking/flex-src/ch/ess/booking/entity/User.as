/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Fri Jun 13 15:35:41 CEST 2008.
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package org.jboss.seam.example.booking {

    [Bindable]
    [RemoteClass(alias="org.jboss.seam.example.booking.User")]
    public class User extends UserBase {

        private var _loggedIn:Boolean = false;
        private var _statusMessage:String;

        public function User() {
        }

        public function get loggedIn():Boolean {
            return this._loggedIn;
        }

        public function set loggedIn(loggedIn:Boolean):void {
            this._loggedIn = loggedIn;
        }

        public function get statusMessage():String {
            return this._statusMessage;
        }

        public function set statusMessage(statusMessage:String):void {
            this._statusMessage = statusMessage;
        }
    }
}