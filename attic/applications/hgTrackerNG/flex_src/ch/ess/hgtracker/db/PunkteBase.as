/**
 * Generated by Gas3 v1.0.0 (Granite Data Services) on Mon Jan 21 05:58:50 CET 2008.
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT WILL BE OVERRIDDEN EACH TIME YOU USE
 * THE GENERATOR. CHANGE INSTEAD THE INHERITED CLASS (Punkte.as).
 */

package ch.ess.hgtracker.db {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class PunkteBase implements IExternalizable {

        private var __laziness:String = null;

        private var _id:Number;
        private var _nr1:Boolean;
        private var _nr2:Boolean;
        private var _nr3:Boolean;
        private var _nr4:Boolean;
        private var _nr5:Boolean;
        private var _nr6:Boolean;
        private var _rangpunkte:Number;
        private var _reihenfolge:Number;
        private var _ries1:Number;
        private var _ries2:Number;
        private var _ries3:Number;
        private var _ries4:Number;
        private var _ries5:Number;
        private var _ries6:Number;
        private var _spiel:Spiel;
        private var _spieler:Spieler;
        private var _totalStrich:Number;
        private var _ueberzaehligeSpieler:Boolean;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __laziness === null;

            var property:* = this[name];
            return (
                (!(property is Punkte) || (property as Punkte).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set id(value:Number):void {
            _id = value;
        }
        public function get id():Number {
            return _id;
        }

        public function set nr1(value:Boolean):void {
            _nr1 = value;
        }
        public function get nr1():Boolean {
            return _nr1;
        }

        public function set nr2(value:Boolean):void {
            _nr2 = value;
        }
        public function get nr2():Boolean {
            return _nr2;
        }

        public function set nr3(value:Boolean):void {
            _nr3 = value;
        }
        public function get nr3():Boolean {
            return _nr3;
        }

        public function set nr4(value:Boolean):void {
            _nr4 = value;
        }
        public function get nr4():Boolean {
            return _nr4;
        }

        public function set nr5(value:Boolean):void {
            _nr5 = value;
        }
        public function get nr5():Boolean {
            return _nr5;
        }

        public function set nr6(value:Boolean):void {
            _nr6 = value;
        }
        public function get nr6():Boolean {
            return _nr6;
        }

        public function set rangpunkte(value:Number):void {
            _rangpunkte = value;
        }
        public function get rangpunkte():Number {
            return _rangpunkte;
        }

        public function set reihenfolge(value:Number):void {
            _reihenfolge = value;
        }
        public function get reihenfolge():Number {
            return _reihenfolge;
        }

        public function set ries1(value:Number):void {
            _ries1 = value;
        }
        public function get ries1():Number {
            return _ries1;
        }

        public function set ries2(value:Number):void {
            _ries2 = value;
        }
        public function get ries2():Number {
            return _ries2;
        }

        public function set ries3(value:Number):void {
            _ries3 = value;
        }
        public function get ries3():Number {
            return _ries3;
        }

        public function set ries4(value:Number):void {
            _ries4 = value;
        }
        public function get ries4():Number {
            return _ries4;
        }

        public function set ries5(value:Number):void {
            _ries5 = value;
        }
        public function get ries5():Number {
            return _ries5;
        }

        public function set ries6(value:Number):void {
            _ries6 = value;
        }
        public function get ries6():Number {
            return _ries6;
        }

        public function set spiel(value:Spiel):void {
            _spiel = value;
        }
        public function get spiel():Spiel {
            return _spiel;
        }

        public function set spieler(value:Spieler):void {
            _spieler = value;
        }
        public function get spieler():Spieler {
            return _spieler;
        }

        public function set totalStrich(value:Number):void {
            _totalStrich = value;
        }
        public function get totalStrich():Number {
            return _totalStrich;
        }

        public function set ueberzaehligeSpieler(value:Boolean):void {
            _ueberzaehligeSpieler = value;
        }
        public function get ueberzaehligeSpieler():Boolean {
            return _ueberzaehligeSpieler;
        }

        public function readExternal(input:IDataInput):void {
            __laziness = input.readObject() as String;
            if (meta::isInitialized()) {
                _id = input.readObject() as Number;
                _nr1 = input.readObject() as Boolean;
                _nr2 = input.readObject() as Boolean;
                _nr3 = input.readObject() as Boolean;
                _nr4 = input.readObject() as Boolean;
                _nr5 = input.readObject() as Boolean;
                _nr6 = input.readObject() as Boolean;
                _rangpunkte = input.readObject() as Number;
                _reihenfolge = input.readObject() as Number;
                _ries1 = input.readObject() as Number;
                _ries2 = input.readObject() as Number;
                _ries3 = input.readObject() as Number;
                _ries4 = input.readObject() as Number;
                _ries5 = input.readObject() as Number;
                _ries6 = input.readObject() as Number;
                _spiel = input.readObject() as Spiel;
                _spieler = input.readObject() as Spieler;
                _totalStrich = input.readObject() as Number;
                _ueberzaehligeSpieler = input.readObject() as Boolean;
            }
            else {
                _id = input.readObject() as Number;
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__laziness);
            if (meta::isInitialized()) {
                output.writeObject(_id);
                output.writeObject(_nr1);
                output.writeObject(_nr2);
                output.writeObject(_nr3);
                output.writeObject(_nr4);
                output.writeObject(_nr5);
                output.writeObject(_nr6);
                output.writeObject(_rangpunkte);
                output.writeObject(_reihenfolge);
                output.writeObject(_ries1);
                output.writeObject(_ries2);
                output.writeObject(_ries3);
                output.writeObject(_ries4);
                output.writeObject(_ries5);
                output.writeObject(_ries6);
                output.writeObject(_spiel);
                output.writeObject(_spieler);
                output.writeObject(_totalStrich);
                output.writeObject(_ueberzaehligeSpieler);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}