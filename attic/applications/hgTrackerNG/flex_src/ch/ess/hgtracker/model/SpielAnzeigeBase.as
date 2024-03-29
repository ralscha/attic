/**
 * Generated by Gas3 v1.0.0 (Granite Data Services) on Mon Jan 21 05:58:50 CET 2008.
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT WILL BE OVERRIDDEN EACH TIME YOU USE
 * THE GENERATOR. CHANGE INSTEAD THE INHERITED CLASS (SpielAnzeige.as).
 */

package ch.ess.hgtracker.model {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;

    [Bindable]
    public class SpielAnzeigeBase implements IExternalizable {

        private var _art:String;
        private var _datum:Date;
        private var _gegner:String;
        private var _id:Number;
        private var _ort:String;
        private var _schlagPunkte:Number;
        private var _schlagPunkteGegner:Number;
        private var _totalNr:Number;
        private var _totalNrGegner:Number;
        private var _wochentag:String;

        public function set art(value:String):void {
            _art = value;
        }
        public function get art():String {
            return _art;
        }

        public function set datum(value:Date):void {
            _datum = value;
        }
        public function get datum():Date {
            return _datum;
        }

        public function set gegner(value:String):void {
            _gegner = value;
        }
        public function get gegner():String {
            return _gegner;
        }

        public function set id(value:Number):void {
            _id = value;
        }
        public function get id():Number {
            return _id;
        }

        public function set ort(value:String):void {
            _ort = value;
        }
        public function get ort():String {
            return _ort;
        }

        public function set schlagPunkte(value:Number):void {
            _schlagPunkte = value;
        }
        public function get schlagPunkte():Number {
            return _schlagPunkte;
        }

        public function set schlagPunkteGegner(value:Number):void {
            _schlagPunkteGegner = value;
        }
        public function get schlagPunkteGegner():Number {
            return _schlagPunkteGegner;
        }

        public function set totalNr(value:Number):void {
            _totalNr = value;
        }
        public function get totalNr():Number {
            return _totalNr;
        }

        public function set totalNrGegner(value:Number):void {
            _totalNrGegner = value;
        }
        public function get totalNrGegner():Number {
            return _totalNrGegner;
        }

        public function set wochentag(value:String):void {
            _wochentag = value;
        }
        public function get wochentag():String {
            return _wochentag;
        }

        public function readExternal(input:IDataInput):void {
            _art = input.readObject() as String;
            _datum = input.readObject() as Date;
            _gegner = input.readObject() as String;
            _id = input.readObject() as Number;
            _ort = input.readObject() as String;
            _schlagPunkte = input.readObject() as Number;
            _schlagPunkteGegner = input.readObject() as Number;
            _totalNr = input.readObject() as Number;
            _totalNrGegner = input.readObject() as Number;
            _wochentag = input.readObject() as String;
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(_art);
            output.writeObject(_datum);
            output.writeObject(_gegner);
            output.writeObject(_id);
            output.writeObject(_ort);
            output.writeObject(_schlagPunkte);
            output.writeObject(_schlagPunkteGegner);
            output.writeObject(_totalNr);
            output.writeObject(_totalNrGegner);
            output.writeObject(_wochentag);
        }
    }
}