/**
 * Generated by Gas3 v1.0.0 (Granite Data Services) on Fri Jan 11 13:24:23 CET 2008.
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT WILL BE OVERRIDDEN EACH TIME YOU USE
 * THE GENERATOR. CHANGE INSTEAD THE INHERITED CLASS (AbstractEntity.as).
 */

package ch.ess.issue.entity {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import mx.core.IUID;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class AbstractEntityBase implements IExternalizable, IUID {

        private var __laziness:String = null;

        private var _id:Number;
        private var _uid:String;
        private var _version:Number;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __laziness === null;

            var property:* = this[name];
            return (
                (!(property is AbstractEntity) || (property as AbstractEntity).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function get id():Number {
            return _id;
        }

        /**
         * Warning: entity uids should be generated by the server (collision risk).
         */
        public function set uid(value:String):void {
            _uid = value;
        }
        public function get uid():String {
            return _uid;
        }

        public function get version():Number {
            return _version;
        }

        public function readExternal(input:IDataInput):void {
            __laziness = input.readObject() as String;
            if (meta::isInitialized()) {
                _id = input.readObject() as Number;
                _uid = input.readObject() as String;
                _version = input.readObject() as Number;
            }
            else {
                _id = input.readObject() as Number;
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__laziness);
            if (meta::isInitialized()) {
                output.writeObject(_id);
                output.writeObject(_uid);
                output.writeObject(_version);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}