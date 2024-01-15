package ch.ess.hgtracker.components {
  import mx.controls.DataGrid;
  import mx.controls.dataGridClasses.DataGridColumn;
  import mx.controls.dataGridClasses.DataGridItemRenderer;
  import mx.controls.listClasses.BaseListData;

  public class RiesItemRenderer extends DataGridItemRenderer {
    
    private var _riesNo:String = null;
    
    public override function set listData(value:BaseListData):void {
    
      if (_riesNo == null) {
        var dg:DataGrid = DataGrid(value.owner);
        var col:DataGridColumn = dg.columns[value.columnIndex];
        
        if (col.dataField == 'ries1') {
          _riesNo = 'nr1';
        } else if (col.dataField == 'ries2') {
          _riesNo = 'nr2';
        } else if (col.dataField == 'ries3') {
          _riesNo = 'nr3';
        } else if (col.dataField == 'ries4') {
          _riesNo = 'nr4';
        } else if (col.dataField == 'ries5') {
          _riesNo = 'nr5';
        } else if (col.dataField == 'ries6') {
          _riesNo = 'nr6';
        }
      }
      
      super.listData = value;
    }
    
    
    public override function set data(value:Object):void {      
      if (_riesNo != null) {
        if (value[_riesNo]) {
          this.backgroundColor = 0xFF0000;   
          this.background = true;
        } else {
          this.background = false;
        }
      }      
      super.data = value;            
    } 
    
  }
}