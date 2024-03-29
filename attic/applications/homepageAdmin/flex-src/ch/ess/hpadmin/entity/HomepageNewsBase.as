/**
 * Generated by Gas3 v1.0.0 (Granite Data Services) on Mon Jan 21 12:46:49 CET 2008.
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT WILL BE OVERRIDDEN EACH TIME YOU USE
 * THE GENERATOR. CHANGE INSTEAD THE INHERITED CLASS (HomepageNews.as).
 */

package ch.ess.hpadmin.entity {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import flash.utils.IExternalizable;
    import mx.collections.ArrayCollection;
    import org.granite.collections.IPersistentCollection;
    import org.granite.meta;

    use namespace meta;

    [Bindable]
    public class HomepageNewsBase implements IExternalizable {

        private var __laziness:String = null;

        private var _author:String;
        private var _date:Date;
        private var _homepageNewsTexts:ArrayCollection;
        private var _id:Number;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __laziness === null;

            var property:* = this[name];
            return (
                (!(property is HomepageNews) || (property as HomepageNews).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set author(value:String):void {
            _author = value;
        }
        public function get author():String {
            return _author;
        }

        public function set date(value:Date):void {
            _date = value;
        }
        public function get date():Date {
            return _date;
        }

        public function set homepageNewsTexts(value:ArrayCollection):void {
            _homepageNewsTexts = value;
        }
        public function get homepageNewsTexts():ArrayCollection {
            return _homepageNewsTexts;
        }

        public function set id(value:Number):void {
            _id = value;
        }
        public function get id():Number {
            return _id;
        }

        public function readExternal(input:IDataInput):void {
            __laziness = input.readObject() as String;
            if (meta::isInitialized()) {
                _author = input.readObject() as String;
                _date = input.readObject() as Date;
                _homepageNewsTexts = input.readObject() as ArrayCollection;
                _id = input.readObject() as Number;
            }
            else {
                _id = input.readObject() as Number;
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__laziness);
            if (meta::isInitialized()) {
                output.writeObject(_author);
                output.writeObject(_date);
                output.writeObject(_homepageNewsTexts);
                output.writeObject(_id);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}