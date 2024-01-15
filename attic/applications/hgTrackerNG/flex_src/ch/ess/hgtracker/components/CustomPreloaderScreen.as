
package ch.ess.hgtracker.components {
    import flash.display.Graphics;
    import flash.display.Shape;
    import flash.display.Sprite;
    import flash.events.Event;
    import flash.events.ProgressEvent;
    import flash.text.TextField;
    import flash.text.TextFieldAutoSize;
    import flash.text.TextFormat;
    
    import mx.events.FlexEvent;
    import mx.preloaders.IPreloaderDisplay;
    import mx.preloaders.Preloader;

    public class CustomPreloaderScreen extends Sprite implements IPreloaderDisplay {

        private var progress:Shape;
        private var headerText:TextField;

        private var _preloader:Preloader;
        private var _backgroundAlpha:Number;
        private var _backgroundColor:uint;
        private var _backgroundImage:Object;
        private var _backgroundSize:String;
        private var _stageHeight:Number;
        private var _stageWidth:Number;

        public function set preloader(value:Sprite):void {
          _preloader = value as Preloader;
          value.addEventListener(ProgressEvent.PROGRESS, progressEventHandler);
          value.addEventListener(FlexEvent.INIT_COMPLETE, initCompleteEventHandler);
        }

        public function set backgroundAlpha(value:Number):void {
            _backgroundAlpha = value;
        }

        public function get backgroundAlpha(  ):Number {
            return 1;
        }

        public function set backgroundColor(value:uint):void {
            _backgroundColor = value;
        }

        public function get backgroundColor(  ):uint {
            return 0xffffffff;
        }

        public function set backgroundImage(value:Object):void {
            _backgroundImage = value;
        }

        public function get backgroundImage(  ):Object {
            return null;
        }

        public function set backgroundSize(value:String):void {
            _backgroundSize = value;
        }

        public function get backgroundSize(  ):String {
            return "auto";
        }

        public function set stageWidth(value:Number):void {
            _stageWidth = value;
        }

        public function get stageWidth():Number {
            return _stageWidth;
        }

        public function set stageHeight(value:Number):void {
            _stageHeight = value;
        }

        public function get stageHeight(  ):Number {
            return _stageHeight;
        }

        public function CustomPreloaderScreen() {
          progress = new Shape();
          headerText = new TextField();

          headerText.text = "HG Verwaltung wird geladen";
          headerText.autoSize = TextFieldAutoSize.LEFT;
          addChild(headerText);
          addChild(progress);
          
          var headerFormat:TextFormat = new TextFormat();
          headerFormat.font = "Arial";
          headerFormat.size = 20;
          headerFormat.bold = true;
          headerText.setTextFormat(headerFormat, 0, headerText.text.length);      
          

              
        }

        private function progressEventHandler(event:ProgressEvent):void {
          
          var g:Graphics = progress.graphics;
          
          this.graphics.clear();
          this.graphics.beginFill(0xFFFFFF, 1);
          this.graphics.drawRect(0, 0, _stageWidth, _stageHeight);
          this.graphics.endFill();
      
          var progressPercent:Number = event.bytesLoaded / event.bytesTotal;          
          var progressInPixel:int = progressPercent * 500;
          
          g.beginFill(0x00CC33);
          g.drawRect(0, 40, progressInPixel, 31);
          g.endFill();
          g.lineStyle(1, 0x00CC33, 1);
          g.drawRect(progressInPixel, 40, 500-progressInPixel, 30);
          
          g.lineStyle(1, 0x000000, 1);
          g.drawRect(0, 40, 500, 31);
        }
        
        
        public function initialize():void {
            progress.x = (stage.stageWidth / 2) - 250;
            progress.y = stage.stageHeight / 2;
            
            backgroundColor = 0xFFFFFF;
            backgroundAlpha = 1; 
                        
            headerText.x = progress.x+130;                        
            headerText.y = progress.y - 50;           
        }

        private function initCompleteEventHandler(event:FlexEvent):void {
            dispatchEvent(new Event(Event.COMPLETE));
        }

    }
}
