/**
 * Generated by Gas3 v1.0.0 (Granite Data Services) on Mon Jan 21 05:58:50 CET 2008.
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT WILL BE OVERRIDDEN EACH TIME YOU USE
 * THE GENERATOR. CHANGE INSTEAD THE INHERITED CLASS (Art.as).
 */

package ch.ess.hgtracker.db {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import mx.collections.ArrayCollection;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class ArtBase implements IExternalizable {

        private var __laziness:String = null;

        private var _anzahlSpiele:int;
        private var _club:Club;
        private var _id:Number;
        private var _meisterschaft:Boolean;
        private var _spielArt:String;
        private var _spiele:ArrayCollection;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __laziness === null;

            var property:* = this[name];
            return (
                (!(property is Art) || (property as Art).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set anzahlSpiele(value:int):void {
            _anzahlSpiele = value;
        }
        public function get anzahlSpiele():int {
            return _anzahlSpiele;
        }

        public function set club(value:Club):void {
            _club = value;
        }
        public function get club():Club {
            return _club;
        }

        public function set id(value:Number):void {
            _id = value;
        }
        public function get id():Number {
            return _id;
        }

        public function set meisterschaft(value:Boolean):void {
            _meisterschaft = value;
        }
        public function get meisterschaft():Boolean {
            return _meisterschaft;
        }

        public function set spielArt(value:String):void {
            _spielArt = value;
        }
        public function get spielArt():String {
            return _spielArt;
        }

        public function set spiele(value:ArrayCollection):void {
            _spiele = value;
        }
        public function get spiele():ArrayCollection {
            return _spiele;
        }

        public function readExternal(input:IDataInput):void {
            __laziness = input.readObject() as String;
            if (meta::isInitialized()) {
                _anzahlSpiele = input.readObject() as int;
                _club = input.readObject() as Club;
                _id = input.readObject() as Number;
                _meisterschaft = input.readObject() as Boolean;
                _spielArt = input.readObject() as String;
                _spiele = input.readObject() as ArrayCollection;
            }
            else {
                _id = input.readObject() as Number;
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__laziness);
            if (meta::isInitialized()) {
                output.writeObject(_anzahlSpiele);
                output.writeObject(_club);
                output.writeObject(_id);
                output.writeObject(_meisterschaft);
                output.writeObject(_spielArt);
                output.writeObject(_spiele);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}