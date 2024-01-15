function Stack(size) {
	this.st = new Array(size);
	this.sp = 0;
	this.pop = function(){
		if (this.sp > 0) return this.st[--this.sp];
		else return "Err: stack underflow";
	};
	this.push = function(value){
		if (this.sp < this.st.length) this.st[this.sp++] = value;
		else alert("Err: stack overflow");
	};
}

function Spreadsheet(rows,columns,name) {
	var i;
	this.rows       = rows;
	this.columns    = columns;
	this.name       = name;
	this.c          = new Array(rows);
	for (i=0; i<this.rows; i++) this.c[i] = new Array(columns);
	this.addCell    = function(cell) {
		if (cell.row >= 0 && cell.column >= 0) this.c[cell.row][cell.column] = cell;
		if (cell.name != null) eval("namedCells." + cell.name + " = cell");
	}
	this.clearReady = function() {
		var i; var J;
		for (i=0; i<this.rows; i++) for (j=0; j<this.columns; j++) if (this.c[i][j]) this.c[i][j].ready = false;
	}
	this.show       = function() {
		var i; var J;
		for (i=0; i<this.rows; i++) for (j=0; j<this.columns; j++) if (this.c[i][j]) this.c[i][j].show();
	}
	spreadsheets.add(this);
}

function SpreadsheetCell(spreadsheet,row,column,name,value,formula,action,nFormat,nPrecision) {
	this.spreadsheet = spreadsheet;
	this.row         = row;
	this.column      = column;
	this.name        = name;
	this.value       = value;
	this.formula     = formula;
	this.action      = action;
	this.isACell     = true;
	this.ready       = true;
	this.spreadsheet.addCell(this);
	if (this.name != null) this.formElement = document.forms[0].elements[this.name];
	else                   this.formElement = document.forms[0].elements[this.spreadsheet.name + "_r" + this.row + "c" + this.column];
	this.format      = nFormat;
	if (nPrecision != null) {
		this.precision   = nPrecision;
	}
	else {
		var f = format.match(/[\.:]\d*$/)[0];
		if (f == null) f = "";
		{if (f.length > 1) this.precision = Math.round(1 / parseFloat("0." + f.substr(2) + "1")); else p = 1;}
	}
	this.getValue    = function() {
		if (!this.ready) {
			this.ready = true;
			if (this.formula) this.formula();
		}
		return this.value;
	}
	this.set         = function() {this.setValue(this.formElement.value);}
	this.setValue    = function(value,noShow,noAct,rejectNull,noActNull,rejectNaN,noActNaN) {
		var v = value;
		if (v != null) if (v.isACell) v = v.getValue();
		if ("" + v == "") v = null;
		if (this.format != null && this.format.indexOf(":") >= 0 && ("" + v).substring(0,3) != "Err") v = ("" + v).replace(/:/,".");
		if (!((rejectNull != null && v == null) || (rejectNaN != null && issNaN(v)))) this.value = v;
		if (this.action != null && (noAct == null) && !((noActNull != null && v == null) || (noActNaN != null && issNaN(v)))) {
			this.action();
		}
		if (noShow == null) {
			spreadsheets.invoke("clearReady");
			spreadsheets.invoke("show");
		}
	}
	this.putValue    = function(value) {this.setValue(value,1,1,1,1,1,1);}
	this.putValueX   = function(value) {this.setValue(value,1,null,1,1,1,1);}
	this.assValue    = function(value) {this.setValue(value,1,1);}
	this.show        = function() {
		if (this.formElement != null) {
			if (this.getValue() == null) this.formElement.value = "";
			else if (issNaN(this.getValue())) this.formElement.value = this.getValue();
			else this.formElement.value = format(this.getValue(), this.format, this.precision);
		}
	}
	this.fcs         = function() {activeCell = this; activeVal = this.formElement.value;}
	this.blr         = function() {if (this.formElement.value != activeVal) this.set(this.formElement.value); activeCell = null; activeVal = null;}
}
var activeCell = null;
var activeVal  = null;

function chkPendCellUpdate() {
	if (activeCell != null) {
		if (activeCell.formElement.value != activeVal) activeCell.set(activeCell.formElement.value);
	}
}

function issNaN(p) {
	return ("" + parseFloat(p)) == "NaN";
}

function prepare(nullReplacement) {
	var p = stack.pop();
	if ("" + p == "") p = null;
	if (p == null) p = nullReplacement;
	else if (p.isACell) p = p.getValue();
	if ("" + p == "") p = null;
	if (p == null) p = nullReplacement;
	if (p != null) {
		if (issNaN(p)) {if (p.substr(0,3) != "Err")	p = "Err:" + p;}
		else p = parseFloat(p);
	}
	return p;
}

function format(value,fmt,prc){
	var f = fmt.match(/[\.:]\d*$/)[0];
	if (f == null) f = "";
	var v = "" + (Math.round(value * prc) / prc);
	var t = "";
	var m = v.match(/\.\d*/);
	if (m == null) t = f;
	else if (m.length < f.length) {m += "";t = f.substr((""+m).length);}
	v = v+t;
	var s = f.match(/[\.:]/);
	v = v.replace(/\./, s);
	return v;
}

function unaryNumOp(op,nullReplacement) {
	var p = this.prepare(nullReplacement);
	var r;
	if (issNaN(p)) {
		if (p == null) r = null;
		else r = p;
	}
	else {
		r = eval(op);
		if (("" + r) == "NaN") r = "Err:" + op.replace(/\bp\b/,""+p);
	}
	stack.push(r);
}
function binNumOp(op,nullReplacement) {
	var p2 = this.prepare(nullReplacement);
	var p1 = this.prepare(nullReplacement);
	var r;
	if (issNaN(p1) || issNaN(p2)) {
		if (p1 == null || p2 == null) r = null;
		else {
			if (issNaN(p1)) r = p1; else r = p2;
		}
	}
	else {
		r = eval(op);
		if (("" + r) == "NaN") r = "Err:" + (op.replace(/\bp1\b/,""+p1).replace(/\bp2\b/,""+p2));
	}
	stack.push(r);
}
var splitFloat = "var s = (p < 0.0 ? -1 : 1); p=p*s; var ip = Math.floor(p); var fp = p-ip;";
function iAdd(nullReplacement) {binNumOp("p1 + p2",nullReplacement);}
function iSub(nullReplacement) {binNumOp("p1 - p2",nullReplacement);}
function iMul(nullReplacement) {binNumOp("p1 * p2",nullReplacement);}
function iDiv(nullReplacement) {binNumOp("p1 / p2",nullReplacement);}
function iHMtoD(nullReplacement) {unaryNumOp("{" + splitFloat + "(ip + (Math.round(fp/0.006)/100)) * s;}",nullReplacement);}
function iDtoHM(nullReplacement) {unaryNumOp("{" + splitFloat + "(ip + (Math.round(fp*60)   /100)) * s;}",nullReplacement);}
function iGet(name) {var cell = eval(name); stack.push(cell);}
function iLit(value){stack.push(value);}
function iSav (){var cell = stack.pop();var v = stack.pop();if (cell.isACell) cell.savValue(v);}
function iPut (){var cell = stack.pop();var v = stack.pop();if (cell.isACell) cell.putValue(v);}
function iPutX(){var cell = stack.pop();var v = stack.pop();if (cell.isACell) cell.putValueX(v);}
function iAss (){var cell = stack.pop();var v = stack.pop();if (cell.isACell) cell.assValue(v);}

function Container(){}

function Collection() {
	this.members = new Container();
	this.add = function(member) {
		if (eval("this.members." + member.name)) eval("error_" + member.name + "_already_exists");
		else eval("this.members." + member.name + " = member");
	}
	this.invoke = function(method) {
		var i;
		for (i in this.members) eval("this.members." + i + "." + method + "()");
	}
}

var namedCells = new Container;
var spreadsheets = new Collection();

