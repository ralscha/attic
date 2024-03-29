/**
 * Generated by Gas3 v1.0.0 (Granite Data Services) on Mon Jan 21 12:46:49 CET 2008.
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT WILL BE OVERRIDDEN EACH TIME YOU USE
 * THE GENERATOR. CHANGE INSTEAD THE INHERITED CLASS (HomepageTemplate.as).
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
    public class HomepageTemplateBase implements IExternalizable {

        private var __laziness:String = null;

        private var _homepagePages:ArrayCollection;
        private var _id:Number;
        private var _tplMain:String;
        private var _tplRight:String;

        meta function isInitialized(name:String = null):Boolean {
            if (!name)
                return __laziness === null;

            var property:* = this[name];
            return (
                (!(property is HomepageTemplate) || (property as HomepageTemplate).meta::isInitialized()) &&
                (!(property is IPersistentCollection) || (property as IPersistentCollection).isInitialized())
            );
        }

        public function set homepagePages(value:ArrayCollection):void {
            _homepagePages = value;
        }
        public function get homepagePages():ArrayCollection {
            return _homepagePages;
        }

        public function set id(value:Number):void {
            _id = value;
        }
        public function get id():Number {
            return _id;
        }

        public function set tplMain(value:String):void {
            _tplMain = value;
        }
        public function get tplMain():String {
            return _tplMain;
        }

        public function set tplRight(value:String):void {
            _tplRight = value;
        }
        public function get tplRight():String {
            return _tplRight;
        }

        public function readExternal(input:IDataInput):void {
            __laziness = input.readObject() as String;
            if (meta::isInitialized()) {
                _homepagePages = input.readObject() as ArrayCollection;
                _id = input.readObject() as Number;
                _tplMain = input.readObject() as String;
                _tplRight = input.readObject() as String;
            }
            else {
                _id = input.readObject() as Number;
            }
        }

        public function writeExternal(output:IDataOutput):void {
            output.writeObject(__laziness);
            if (meta::isInitialized()) {
                output.writeObject(_homepagePages);
                output.writeObject(_id);
                output.writeObject(_tplMain);
                output.writeObject(_tplRight);
            }
            else {
                output.writeObject(_id);
            }
        }
    }
}