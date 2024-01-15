package ch.ess.hgtracker.components {
  import flash.display.Graphics;
  import flash.display.Sprite;
  import flash.events.TimerEvent;
  import flash.text.TextField;
  import flash.text.TextFieldAutoSize;
  import flash.text.TextFormat;
  import flash.utils.Timer;
  
  import mx.core.UIComponent;

  public class TestSprite extends UIComponent {
    
    private var _progress:Sprite = new Sprite();
    private var progressValue:int = 0;
    private var status:TextField = new TextField();
    private var format:TextFormat = new TextFormat();
    private var headerText:TextField = new TextField();
    
    public function TestSprite() {
      super();
    }
    
    public function initBall():void {
      addChild(_progress);
      
      format.font = "Arial";
      format.size = 15;
      format.bold = true;      
     
      
      status.x = 90;
      status.y = 45;

      addChild(status);        
      
      headerText.x = 0;
      headerText.y = 0;
      headerText.autoSize = TextFieldAutoSize.LEFT;
      headerText.text = "HG Verwaltung wird geladen";
      addChild(headerText);
      
      
      
      var headerFormat:TextFormat = new TextFormat();
      headerFormat.font = "Arial";
      headerFormat.size = 20;
      headerFormat.bold = true;
      headerText.setTextFormat(headerFormat, 0, headerText.text.length);
      
      
      
      
      drawProgress();
      var timer:Timer = new Timer(2000, 4); 
      
      timer.addEventListener(TimerEvent.TIMER, timerHandler);
      timer.addEventListener(TimerEvent.TIMER_COMPLETE, completeHandler); 
      
      timer.start();  
      
        
    }
    
    private function timerHandler(e:TimerEvent):void {
      progressValue += 50;
      drawProgress();
    }
    
    private function completeHandler(e:TimerEvent):void {
      drawProgress();
    }
    
    private function drawProgress():void {
      var g:Graphics = _progress.graphics;
      
      g.clear();
      
      var perC:Number = progressValue * 100 / 500;
      
        status.text = perC.toFixed(0) + " % ";
        status.setTextFormat(format);
      
      
      g.beginFill(0x00CC33);
      g.drawRect(0, 40, progressValue, 31);
      g.endFill();
      g.lineStyle(1, 0x00CC33, 1);
      g.drawRect(progressValue, 40, 500-progressValue, 30);
      
      
      
//      _progress.graphics.lineStyle(0, 0, 1);
//      _progress.graphics.drawCircle(0, 0, 20);
//      
//      
//      _progress.graphics.moveTo(0, 0);
//      _progress.graphics.lineTo(0, 20);
//      _progress.graphics.moveTo(0, 0);
//      var angle:Number = progressValue / 200 * Math.PI * 2;
//      var newX:Number = Math.sin(angle) * 20;
//      var newY:Number = Math.cos(angle) * 20;
//      _progress.graphics.lineTo(newX, newY);
       
    }
    
    
    
    
  }
}