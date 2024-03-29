/**
 * Generated by Gas3 v1.1.0 (Granite Data Services) on Wed Jul 09 04:52:20 CEST 2008.
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERRIDDEN EACH TIME YOU USE
 * THE GENERATOR. CHANGE INSTEAD THE INHERITED CLASS (Hotel.as).
 */

package org.jboss.seam.example.booking {

    import flash.events.EventDispatcher;
    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;
    import org.granite.tide.IEntity;
    import org.granite.tide.IEntityManager;
    import org.granite.tide.IPropertyHolder;

    use namespace meta;

    [Managed]
    public class HotelBase implements IExternalizable {

        protected var _em:IEntityManager = null;

        private var __laziness:String = null;

        private var _address:String;
        private var _city:String;
        private var _country:String;
        private var _description:String;
        private var _id:Number;
        private var _name:String;
        private var _price:Number;
        private var _state:String;
        private var _zip:String;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __laziness === null;

            var property:* = this[name];
            return (
                (!(property is Hotel) || (property as Hotel).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        [Transient]
        public function meta_getEntityManager():IEntityManager {
            return _em;
        }
        public function meta_setEntityManager(em:IEntityManager):void {
            _em = em;
        }

        public function meta_mergeVersion(entity:IEntity):void {
            _id = HotelBase(entity).id;
        }

        public function set address(value:String):void {
            _address = value;
        }
        public function get address():String {
            return _address;
        }

        public function set city(value:String):void {
            _city = value;
        }
        public function get city():String {
            return _city;
        }

        public function set country(value:String):void {
            _country = value;
        }
        public function get country():String {
            return _country;
        }

        public function set id(value:Number):void {
            _id = value;
        }
        public function get id():Number {
            return _id;
        }

        public function set name(value:String):void {
            _name = value;
        }
        public function get name():String {
            return _name;
        }

        public function set price(value:Number):void {
            _price = value;
        }
        public function get price():Number {
            return _price;
        }

        public function set state(value:String):void {
            _state = value;
        }
        public function get state():String {
            return _state;
        }

        public function set zip(value:String):void {
            _zip = value;
        }
        public function get zip():String {
            return _zip;
        }

        public function set uid(value:String):void {
            // noop...
        }
        public function get uid():String {
            if (isNaN(_id))
                return "org.jboss.seam.example.booking.Hotel";
            return "org.jboss.seam.example.booking.Hotel:" + String(_id);
        }

        public function readExternal(input:IDataInput):void {
            __laziness = input.readObject() as String;
            if (meta::isInitialized()) {
                _address = input.readObject() as String;
                _city = input.readObject() as String;
                _country = input.readObject() as String;
                _description = input.readObject() as String;
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _name = input.readObject() as String;
                _price = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
                _state = input.readObject() as String;
                _zip = input.readObject() as String;
            }
            else {
                _id = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__laziness);
            if (meta::isInitialized()) {
                if (_address is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_address).object);
                else
                    output.writeObject(_address);
                if (_city is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_city).object);
                else
                    output.writeObject(_city);
                if (_country is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_country).object);
                else
                    output.writeObject(_country);
                if (_description is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_description).object);
                else
                    output.writeObject(_description);
                if (_id is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_id).object);
                else
                    output.writeObject(_id);
                if (_name is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_name).object);
                else
                    output.writeObject(_name);
                if (_price is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_price).object);
                else
                    output.writeObject(_price);
                if (_state is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_state).object);
                else
                    output.writeObject(_state);
                if (_zip is IPropertyHolder)
                    output.writeObject(IPropertyHolder(_zip).object);
                else
                    output.writeObject(_zip);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}
