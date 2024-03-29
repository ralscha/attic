/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Wed Jul 16 16:57:30 CEST 2008.
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERRIDDEN EACH TIME YOU USE
 * THE GENERATOR. CHANGE INSTEAD THE INHERITED CLASS (User.as).
 */

package ch.ess.issue.entity {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import org.granite.meta;
    import org.granite.tide.IPropertyHolder;

    use namespace meta;

    [Managed]
    public class UserBase extends AbstractEntity {

        private var _email:String;
        private var _enabled:Boolean;
        private var _firstName:String;
        private var _lastName:String;
        private var _password:String;
        private var _rememberMeToken:String;
        private var _role:String;
        private var _username:String;

        public function set email(value:String):void {
            _email = value;
        }
        public function get email():String {
            return _email;
        }

        public function set enabled(value:Boolean):void {
            _enabled = value;
        }
        public function get enabled():Boolean {
            return _enabled;
        }

        public function set firstName(value:String):void {
            _firstName = value;
        }
        public function get firstName():String {
            return _firstName;
        }

        public function set lastName(value:String):void {
            _lastName = value;
        }
        public function get lastName():String {
            return _lastName;
        }

        public function set password(value:String):void {
            _password = value;
        }
        public function get password():String {
            return _password;
        }

        public function set rememberMeToken(value:String):void {
            _rememberMeToken = value;
        }
        public function get rememberMeToken():String {
            return _rememberMeToken;
        }

        public function set role(value:String):void {
            _role = value;
        }
        public function get role():String {
            return _role;
        }

        public function set username(value:String):void {
            _username = value;
        }
        public function get username():String {
            return _username;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            if (meta::isInitialized()) {
                _email = input.readObject() as String;
                _enabled = input.readObject() as Boolean;
                _firstName = input.readObject() as String;
                _lastName = input.readObject() as String;
                _password = input.readObject() as String;
                _rememberMeToken = input.readObject() as String;
                _role = input.readObject() as String;
                _username = input.readObject() as String;
            }
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            if (meta::isInitialized()) {
                if (_email is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_email).object);
                else
                    output.writeObject(_email);
                if (_enabled is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_enabled).object);
                else
                    output.writeObject(_enabled);
                if (_firstName is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_firstName).object);
                else
                    output.writeObject(_firstName);
                if (_lastName is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_lastName).object);
                else
                    output.writeObject(_lastName);
                if (_password is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_password).object);
                else
                    output.writeObject(_password);
                if (_rememberMeToken is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_rememberMeToken).object);
                else
                    output.writeObject(_rememberMeToken);
                if (_role is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_role).object);
                else
                    output.writeObject(_role);
                if (_username is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_username).object);
                else
                    output.writeObject(_username);
            }
        }
    }
}
