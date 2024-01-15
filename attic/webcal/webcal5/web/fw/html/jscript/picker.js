/***********************************************************************
 * (C) 2004 SCC Informationssysteme GmbH                               *
 *     www.scc-gmbh.com / www.common-controls.com                      *
 *                                                                     *
 * Note: This file belongs to the Common Controls Presentation         *
 *       Framework. Permission is given to use this script only        *
 *       together with the Common Controls Presentation Framework      *
 ***********************************************************************/

/*
+ ---------------------------------------------------------------------------------+
| Object....: ColorPalette
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 02.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
ColorPalette = new Object();
ColorPalette.SAFE    = 0;
ColorPalette.WINDOWS = 1;
ColorPalette.GRAY    = 2;
ColorPalette.parse   = ColorPalette_parse;
function ColorPalette_parse(value) {
	var p = parseInt(value);
	
	switch(p) {
		case 0: return ColorPalette.SAFE;
		case 1: return ColorPalette.WINDOWS;
		case 2: return ColorPalette.GRAY;
	}
}


/*
+ ---------------------------------------------------------------------------------+
| Object....: 
| Function..: 
| Arguments.: 
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 02.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function ColorPicker(id) {
	this.id            = id;
	this.palette       = 0;
	this.locale        ='EN';
	this.color         = null;

	this.winOpener     = null;
	this.fieldId       = null;
	this.closeOnSelect = true;
}
function ColorPicker_getId() {
	return this.id;
}
function ColorPicker_setColor(value) {
	this.color = value;
}
function ColorPicker_getColor() {
	return this.color;
}
function ColorPicker_setLocale(locale) {
	this.locale = locale;
}
function ColorPicker_getLocale() {
	return this.locale;
}
function ColorPicker_setSelectedPalette(palette) {
	if (palette instanceof Object) {
		this.palette = palette;
	} else {
		this.palette = ColorPalette.parse(palette);
	}
}
function ColorPicker_getSelectedPalette() {
	return this.palette;
}
function ColorPicker_setFieldId(fieldId) {
	this.fieldId = fieldId;
}
function ColorPicker_setCloseOnSelect(flag) {
	this.closeOnSelect = flag;
}
function ColorPicker_setOpener(opener) {
	this.winOpener = opener;
}
function ColorPicker_close() {
	if (null == this.winOpener) {
		return;
	} else {
		// get the field
		var input = this.winOpener.document.getElementById(this.fieldId);
		
		if (null != input) {
			input.value = '#' + this.color.toUpperCase();
		}
		
		window.close();
	}
}
function ColorPicker_toString() {
	var out = '';
	out += '******* ColorPicker *********' + LF
	out += 'Id.............: ' + this.id + LF;
	out += 'Palette........: ' + this.palette + LF;
	out += 'Locale.........: ' + this.locale + LF;
	out += 'FieldId........: ' + this.fieldId + LF;
	out += 'CloseOnSelect..: ' + this.closeOnSelect + LF;
	return out;
}
new ColorPicker();
ColorPicker.prototype.getId               = ColorPicker_getId;
ColorPicker.prototype.setColor            = ColorPicker_setColor;
ColorPicker.prototype.getColor            = ColorPicker_getColor;
ColorPicker.prototype.setLocale           = ColorPicker_setLocale;
ColorPicker.prototype.getLocale           = ColorPicker_getLocale;
ColorPicker.prototype.setSelectedPalette  = ColorPicker_setSelectedPalette;
ColorPicker.prototype.getSelectedPalette  = ColorPicker_getSelectedPalette;
ColorPicker.prototype.setFieldId          = ColorPicker_setFieldId;
ColorPicker.prototype.setCloseOnSelect    = ColorPicker_setCloseOnSelect;
ColorPicker.prototype.close               = ColorPicker_close;
ColorPicker.prototype.setOpener           = ColorPicker_setOpener;
ColorPicker.prototype.toString            = ColorPicker_toString;


/*
+ ---------------------------------------------------------------------------------+
| Object....: 
| Function..: 
| Arguments.: 
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 02.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function ColorPickerData(colpicker, resPath, locale) {
	this.colpicker   = colpicker;
	this.resPath     = resPath;
	this.locale      = locale;
	this.IMG_SPACER  = 'spacer.gif';
	
	this.CSS_CPICKER = 'colpicker';
}
new ColorPickerData();

/*
+ ---------------------------------------------------------------------------------+
| Object....: 
| Function..: 
| Arguments.: 
|
| Date        Author            Note
| ----------  ----------------  ----------------------------------------------------
| 02.05.2004  G.Schulz (SCC)    Initial version
|
+ ---------------------------------------------------------------------------------+
*/
function ColorPickerPainter() {
	CPP_PREFIX   = 'col_';
	ARR_PALETTES = new Array();
}
function ColorPickerPainter_render(cpData) {
	var colPicker = cpData['colpicker'];
	var row  = null;
	var cell = null;

/*
	if (ARR_PALETTES.length == 0) {
		// preload palettes
		this.init(cpData);
	}
*/
	// Root
	var doc = document.createElement('Span');

	// create outher table
	var table = document.createElement('Table');
	table.cellSpacing = 0;
	table.cellPadding = 0;
	table.border = 0;
	table.className = cpData['CSS_CPICKER'];
	doc.appendChild(table);
	
	// 1) render option list
	row = table.insertRow(-1);                   // BUG in Safari with: table.rows.length 
	cell = row.insertCell(row.cells.length);
	this.doCreateOptionList(cpData, cell);

	// 2) render palette
	row = table.insertRow(-1);
	cell = row.insertCell(row.cells.length);
	this.doCreatePalette(cpData, cell);

	// 3) render preview
	row = table.insertRow(-1);
	cell = row.insertCell(row.cells.length);
	this.doCreatePreviewArea(cpData, cell);

	// get the DIV-Element from the form, where the
	// Calendar should be inserted / replaced
	var span = this.getRoot(colPicker.getId());

	// print colorpicker
	if (span.hasChildNodes()) {
		span.innerHTML = '';
		span.appendChild(doc);
	} else {
		span.appendChild(doc);
	}
}
function ColorPickerPainter_init(cpData) {
	ARR_PALETTES[ColorPalette.SAFE]    = this.doCreateSafe(cpData);
	ARR_PALETTES[ColorPalette.WINDOWS] = this.doCreateWinSystem(cpData);
	ARR_PALETTES[ColorPalette.GRAY]    = this.doCreateGray(cpData);
}
function ColorPickerPainter_doCreateOptionList(cpData, node) {
	var colPicker  = cpData['colpicker'];
	var arrPalette = ColorPickerMsgResources['OPTIONS_' + colPicker.getLocale()];

	var select = document.createElement('select');
	// add options
	for (var i=0; i < arrPalette.length; i++) {
		select.options[select.options.length] = new Option(arrPalette[i], i);
	}
	// set selected month
	select.selectedIndex = colPicker.getSelectedPalette();
	
	// add onchange handler
	select.onchange = function() {
			// set selected month
			colPicker.setSelectedPalette(this.selectedIndex);
			// render calendar
			ColorPickerPainter.render(cpData);
		}
	select.id = 'palsel';
	select.style.width = '100%';
	
	// add option list
	node.appendChild(select);
}

function ColorPickerPainter_doCreatePalette(cpData, node) {
	var colPicker  = cpData['colpicker'];
	var palette    = colPicker.getSelectedPalette();
	var table      = null;

	switch (palette) {
		case ColorPalette.SAFE:
			table = this.doCreateSafe(cpData);
			break;
		case ColorPalette.WINDOWS:
			table = this.doCreateWinSystem(cpData);
			break;
		case ColorPalette.GRAY:
			table = this.doCreateGray(cpData);
			break;
	}
	node.appendChild(table);
}

function ColorPickerPainter_doCreateSafe(cpData) {
	var table = document.createElement('Table');
	table.cellSpacing = 1;
	table.cellPadding = 0;
	table.border = 0;
	table.id = 'safe';

	for (var i = 0; i < 12; i++) {
		// create new row
		var row = table.insertRow(-1);	// table.rows.length
		
		for (var j = 0; j < 3; j++) {
			for (var k = 0; k <= 5; k++) {
				var r = j * 51 + (i % 2) * 51 * 3;
				var g = Math.floor(i / 2) * 51;
				var b = k * 51;
				var cell = this.doCreateCell(r,g,b, cpData, row, 'sp');
				row.appendChild(cell);
			}
		}
	}
	
	return table;
}
function ColorPickerPainter_doCreateWinSystem(cpData) {
	var table = document.createElement('Table');
	table.cellSpacing = 1;
	table.cellPadding = 0;
	table.border = 0;
	table.id = 'system';
	
	for (var i = 0; i < 12; i ++) {
		// create new row
		var row = table.insertRow(-1);	// table.rows.length
		
		for (var j = 0; j < 3; j ++) {
			for (k = 0; k <= 5; k++) {
				var r = k * 51;
				var g = j * 51 + (i % 2) * 51 * 3;
				var b = Math.floor(i / 2) * 51;
				var cell = this.doCreateCell(r,g,b, cpData, row, 'wsp');
				row.appendChild(cell);
			}
		}
	}
	
	return table;
}

function ColorPickerPainter_doCreateGray(cpData) {
	var table = document.createElement('Table');
	table.cellSpacing = 1;
	table.cellPadding = 0;
	table.border = 0;
	table.id = 'gray';
	
	for (j = 0; j <= 15; j ++) {
		// create new row
		var row = table.insertRow(-1);	// table.rows.length
		
		for (k = 0; k <= 15; k ++) {
			var g = Math.floor((k + j * 16) % 256);
			var cell = this.doCreateCell(g, g, g, cpData, row, 'gp');
			row.appendChild(cell);
		}
	}
	
	return table;
}
function ColorPickerPainter_doCreateCell (r, g, b, cpData, row, className) {
	var colPicker = cpData['colpicker'];
	var cell      = row.insertCell(row.cells.length);
	var color     = this.dec2hex((r << 16) + (g << 8) + b);

	var a = document.createElement('A');
	a.href = '#';
	a.onclick = function() {
		colPicker.setColor(color);
		colPicker.close();
	};
	a.onmouseover = function() {
		var col = '#' + color.toUpperCase();
		var txt = col +  ' RGB(' + r +  ',' + g + ',' + b + ')';
		var span = document.getElementById('prevarea');
		span.innerHTML = '';
		span.appendChild(document.createTextNode(txt));
		span.style.backgroundColor = col;

		var top = 52;

		if (parseInt(color, 16) < parseInt('ABABAB', 16)) {
			span.style.color = '#FFFFFF';
		} else {
			span.style.color = '#000000';
		}
	};
	
	var img = document.createElement('Img');
	img.src = cpData['IMG_SPACER'];
	img.border = 0;
	img.className= className;
	a.appendChild(img);
	cell.bgColor = color;
	cell.appendChild(a);
	
	return cell;
}
function ColorPickerPainter_dec2hex(value) {
	value = value.toString(16);
	for(; value.length < 6; value = '0' + value);
	return value;
}
function ColorPickerPainter_doCreatePreviewArea(cpData, node) {
	var table = document.createElement('Table');
	table.cellSpacing = 1;
	table.cellPadding = 0;
	table.border = 1;
	table.width  = '100%';
	table.height = 20;
	node.appendChild(table);

	var row = table.insertRow(table.rows.length);
	var cell = row.insertCell(row.cells.length);
	
	var span = document.createElement('Span');
	span.id = 'prevarea';
	span.className = 'prevarea';
	cell.appendChild(span);
}
function ColorPickerPainter_getRoot(id) {
	return document.getElementById(CPP_PREFIX + id);
}
new ColorPickerPainter();
ColorPickerPainter.render              = ColorPickerPainter_render;
ColorPickerPainter.init                = ColorPickerPainter_init;
ColorPickerPainter.doCreateOptionList  = ColorPickerPainter_doCreateOptionList;
ColorPickerPainter.doCreatePalette     = ColorPickerPainter_doCreatePalette;
ColorPickerPainter.doCreateSafe        = ColorPickerPainter_doCreateSafe;
ColorPickerPainter.doCreateWinSystem   = ColorPickerPainter_doCreateWinSystem;
ColorPickerPainter.doCreateGray        = ColorPickerPainter_doCreateGray;
ColorPickerPainter.doCreateCell        = ColorPickerPainter_doCreateCell;
ColorPickerPainter.dec2hex             = ColorPickerPainter_dec2hex;
ColorPickerPainter.doCreatePreviewArea = ColorPickerPainter_doCreatePreviewArea;
ColorPickerPainter.getRoot             = ColorPickerPainter_getRoot;

