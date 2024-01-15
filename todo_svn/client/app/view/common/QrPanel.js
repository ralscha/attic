// ==========================================
// ExtJS user extension interface
// (c) 2012 by Volker Kinkelin
//  V1.1 June 27 2012
//  see http://code.google.com/p/qrext/
// Licensed under the MIT license:
//   http://www.opensource.org/licenses/mit-license.php
// ==========================================

Ext.define('Todo.view.common.QrPanel', {

	extend : 'Ext.panel.Panel',
	alias : 'widget.qrpanel',
	layout: 'hbox',

	initComponent : function () {
		var me = this;

		me.callParent(arguments);

		// establish reasonable default parameters
		Ext.applyIf(me, {

			qrBackgroundColor : '#000', // CSS color for background. black
			qrForegroundColor : '#fff', // CSS color for foreground. white = max contrast
			qrNoClickHandler : false, // true, if clicks shouldnt be handeled by qrpanel (click = start fullscreen mode)
			qrDisplayTextWhenFullscreen : true, // display textToEncode when in full screen mode
			qrBlocksize : 4, // width [pixels] of an individual block
			qrRenderMethod : (Ext.supports.Canvas ? 'canvas' : 'divs'), // prefer canvas rendering, if supported
			qrCssTempl : '.QRContainer {display:inline-block} #{3} .QB, #{3} .QW {background-color:{0}; width:{1}; height:{1}; float:left;} #{3} .QW {background-color:{2};} .QRE {clear:both}',

			typeNumber : -1, // defines the number of blocks in the code. Number = typeNumber * 4 + 17 blocks.
			// If set too small, the library throws an error and displays nothing (watch JS console).

			qrErrorCorrectLevel : 'L', // defines the redundancy in the code. One of L, M, Q, H = most redundant

			textToEncode : '' // ...should be overwritten by caller

		});

		// no Type Number given, find one yourself
		if (me.typeNumber == -1)
			me.typeNumber = me.getMinTypeNumber();

		// still no type number, use 10
		if (me.typeNumber == -1)
			me.typeNumber = 10;

		me.qrModuleCount = this.typeNumber * 4 + 17; // = no of blocks required

		me.width = me.qrBlocksize * me.qrModuleCount; // required panel width in pixels
		me.height = me.width; // QRs are always qadratic

		// use relative size when rendering into DIVs
		if (me.qrRenderMethod == 'divs')
			me.qrBlocksize = (100.0 / me.qrModuleCount) + '%'; // specify width in percent

		me.bodyStyle = 'border:none;'; // no frills

	}, // initComponent : function () {

	setTextToEncode : function(value) {
		if(value) {
			var me = this;
			me.textToEncode = value.substring(0, Math.min(272, value.length));
			me.typeNumber = me.getMinTypeNumber();
	
			me.qrModuleCount = this.typeNumber * 4 + 17; // = no of blocks required
	
			me.width = me.qrBlocksize * me.qrModuleCount; // required panel width in pixels
			me.height = me.width; // QRs are always qadratic
	
			// use relative size when rendering into DIVs
			if (me.qrRenderMethod == 'divs')
				me.qrBlocksize = (100.0 / me.qrModuleCount) + '%'; // specify width in percent
	
				
			// setup new object
			var objQRCode = qrcode(me.typeNumber, me.qrErrorCorrectLevel);
	
			// now draw QR code on canvas
			objQRCode.drawOnCanvas({
	
				code : me.textToEncode, // code to be encoded
				divCanvas : me.qrCanvas, // where to draw
				dotSize : parseInt(me.qrBlocksize), // a block's dimension
				dotFillStyle : me.qrBackgroundColor, // CSS color for foreground
				divContainerAttributes : { // container DIV's HTML attributes, optional
					style : 'background-color:' + me.qrForegroundColor
	
				}
	
			});
		} else {
			this.initComponent();
		}
		
	},
	
	afterRender : function () {

		var me = this;

		me.callParent(arguments);

		// adapt panel overall height in case a header is defined
		if (me.header) {

			// wait until header box size is set
			// this event is new in Ext 4.1
			me.header.on('boxready', function () {

				// add panel header height to overall height
				me.setHeight(me.height + me.header.getHeight());

			}, me);

		}

		// what render method is chosen?
		switch (me.qrRenderMethod) {

		case 'divs':
			//
			// render QR as a series of HTML DIVs:
			//

			// add CSS for the blocks, if not present
			if (Ext.util.CSS.getRule('#' + me.id + ' .QB', true) == undefined) {

				// setup CSS rule...
				var temp = Ext.String.format(me.qrCssTempl, me.qrBackgroundColor, me.qrBlocksize, me.qrForegroundColor, me.id);
				// ...save ID for later reference...
				me.qrCssId = me.id + 'QrCss';
				// ...and add it to the document
				Ext.util.CSS.createStyleSheet(temp, me.qrCssId);

			}

			// now inject QR as DIV HTML-markup
			var objQRCode = qrcode(me.typeNumber, me.qrErrorCorrectLevel);

			objQRCode.getDOMSubTree({

				typeNumber : me.typeNumber, // defines the number of blocks in the code. blocks = typeNumber * 4 + 17 blocks, see http://www.denso-wave.com/qrcode/vertable1-e.html
				errorCorrectLevel : me.qrErrorCorrectLevel, // defines the redundancy in the code. One of .L, .M, .Q, .H = most redundant
				nodeParent : me.body.dom, // ID of the DIV to contain the code
				code : me.textToEncode // text to be encoded

			});

			break;

		case 'canvas':

			//
			// render as HTML5 CANVAS element:
			//

			// inject a canvas & save reference
			me.qrCanvas = Ext.DomHelper.append(me.body.dom, {

					tag : 'canvas',
					id : me.id + '-qrcanv' // allows easy later selection for CSS rules etc

				});

			// setup new object
			var objQRCode = qrcode(me.typeNumber, me.qrErrorCorrectLevel);

			// now draw QR code on canvas
			objQRCode.drawOnCanvas({

				code : me.textToEncode, // code to be encoded
				divCanvas : me.qrCanvas, // where to draw
				dotSize : parseInt(me.qrBlocksize), // a block's dimension
				dotFillStyle : me.qrBackgroundColor, // CSS color for foreground
				divContainerAttributes : { // container DIV's HTML attributes, optional
					style : 'background-color:' + me.qrForegroundColor

				}

			});

			break;

		case 'gif':

			//
			// render as GIF element:
			//

			// create the code
			var objQRCode = qrcode(me.typeNumber, me.qrErrorCorrectLevel);
			objQRCode.addData(me.textToEncode);
			objQRCode.make();

			// create base64 encoded image markup
			var b64 = objQRCode.createImgTag(parseInt(me.qrBlocksize), 0);

			// add GIF image to panel
			me.add({
				xtype : 'image',
				src : b64.split('"')[1], // extract src url from '<img src="data:image/gif;base64,R0lGO...">
				id : me.id + '-qrgif', // allows easy later selection for CSS rules etc
				width : '100%',
				height : '100%'
			});
			break;

		} // switch(me.qrRenderMethod) {

		// establish click hander, if not opted-out
		if (!me.qrNoClickHandler) {

			// display fullscreen QR code when clicked
			me.body.on('click', me.onQRClick, me);

		}; // if(! me.qrNoClickHandler)

	}, // afterRender : function () {

	// free ressources when destroyed
	onDestroy : function () {

		var me = this;

		me.callParent(arguments);

		// remove the now obsolete CSS rules, if any
		if (me.qrCssId) {

			Ext.util.CSS.removeStyleSheet(me.qrCssId);
			delete me.qrCssId;

		}

	}, // onDestroy : function () {

	// find minimum Type Number for a string or me.textToEncode
	getMinTypeNumber : function (text) {

		// use property if no text given
		if (!text)
			text = this.textToEncode;

		// currently available only for this level
		if (this.qrErrorCorrectLevel == 'L') {

			if (text.length < 18)
				return 1;

			if (text.length < 33)
				return 2;

			if (text.length < 54)
				return 3;

			if (text.length < 79)
				return 4;

			if (text.length < 107)
				return 5;

			if (text.length < 135)
				return 6;

			if (text.length < 155)
				return 7;

			if (text.length < 193)
				return 8;

			if (text.length < 231)
				return 9;

			if (text.length < 272)
				return 10;

		} // if(this.qrErrorCorrectLevel == 'L') {

		return -1; // = can't find out

	}, // getMinTypeNumber: function( text ) {

	// handle click into QRcode
	onQRClick : function () {

		var me = this,
		vpSize = Ext.getDoc().getViewSize(), // = browser window size
		smallerDimension = (vpSize.height < vpSize.width ? vpSize.height : vpSize.width),
		posOld = me.el.getBox(); // = current QR code position & size

		// create white full screen mask showing only a big QR
		// opacity is set later by fadeIn()
		// mask size is VP size, not document size!
		var mask = Ext.create('Ext.container.Container', {

				floating : true,
				shadow : false,
				style : 'border:none; background-color:#fff; opacity:0;',
				width : vpSize.width,
				height : vpSize.height

			});

		// this is the QR code that maximizes
		var qrFullscreen = Ext.create('Todo.view.common.QrPanel', {

				shadow : false,
				preventHeader : true,
				textToEncode : me.textToEncode,
				typeNumber : me.typeNumber,
				qrRenderMethod : (Ext.supports.Canvas ? 'canvas' : me.qrRenderMethod),
				qrErrorCorrectLevel : me.qrErrorCorrectLevel,
				qrBlocksize : Math.floor(smallerDimension / (me.qrModuleCount + 2)),
				qrNoClickHandler : true

			});

		// place it on small version
		qrFullscreen.setSize({
			width : posOld.width,
			height : posOld.height
		});

		qrFullscreen.setPosition(posOld.left, posOld.top);

		// add QR and show it
		mask.add(qrFullscreen);
		mask.show();

		// use 0.5 second transitions
		var duration = 500; 

		// reusable fullscreen terminator function
		var terminate = function () {

			// have a smooth end too
			qrFullscreen.animate({
				duration : duration,
				to : {
					opacity : 0,
					width : posOld.width,
					height : posOld.height,
					top : posOld.top,
					left : posOld.left
				}
			});

			// unmask old content
			mask.el.fadeOut({

				duration : duration,
				callback : function () {
					// remove mask from DOM tree after fade
					mask.destroy();

				}

			});

		}; // var terminate = function() {

		// calc left position of the final qr
		var posleft = (vpSize.width - qrFullscreen.qrBlocksize * me.qrModuleCount) / 2;

		// we like it smooth...
		mask.el.fadeIn({
			duration : duration,
			callback : function () {

				// click || keypress = end fullscreen display
				mask.focus();
				mask.mon(mask.el, 'click', terminate);
				mask.mon(Ext.getDoc(), 'keypress', terminate);

				// show readable textToEncode?
				if (me.qrDisplayTextWhenFullscreen) {

					// CSS in all cases
					var style = 'position:absolute; text-align:center; overflow:hidden; opacity:0; ';

					// display textToEncode left or below?
					if (smallerDimension == vpSize.height) {
						// display to the left
						style += 'top:45%; left:20px; width:' + (posleft - 30) + 'px; margin:auto;';
					} else {
						// display below
						style += 'top:' + ((qr.qrBlocksize + 2) * me.qrModuleCount) + 'px; left:5%; width:90%;';

					}

					// create text
					var text = Ext.String.htmlEncode(me.textToEncode);

					// turn URLs into clickable links
					if (me.textToEncode.substr(0, 7) == 'http://')
						text = '<a target="_blank" href="' + me.textToEncode + '">' + text + '</a>';

					// insert text
					mask.el.appendChild(Ext.DomHelper.createDom({
							tag : 'div',
							style : style,
							html : text
						})).fadeIn();

				} // if( me.qrDisplayTextWhenFullscreen ) {

			} // callback : function ()

		}); // mask.el.fadeIn({

		// animate QR growth
		qrFullscreen.animate({
			duration : duration,
			to : {
				opacity : 1,
				width : qrFullscreen.qrBlocksize * me.qrModuleCount,
				height : qrFullscreen.qrBlocksize * me.qrModuleCount,
				top : qrFullscreen.qrBlocksize,
				left : posleft
			}
		});

	} // onClick: function() {

}); // Ext.define('Todo.view.common.QrPanel', {
