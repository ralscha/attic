/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (Info.as).
 */

package ch.ess.startstop.entity {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;

    [Bindable]
    public class InfoBase implements IExternalizable {

        private var _free:Number;
        private var _total:Number;
        private var _used:Number;

        public function set free(value:Number):void {
            _free = value;
        }
        public function get free():Number {
            return _free;
        }

        public function set total(value:Number):void {
            _total = value;
        }
        public function get total():Number {
            return _total;
        }

        public function set used(value:Number):void {
            _used = value;
        }
        public function get used():Number {
            return _used;
        }

        public function readExternal(input:IDataInput):void {
            _free = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            _total = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
            _used = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_free);
            output.writeObject(_total);
            output.writeObject(_used);
        }
    }
}