Ext.define('E4desk.view.Wallpaper', {
	extend: 'Ext.Component',
	cls: 'wallpaper',
	html: '<img src="' + Ext.BLANK_IMAGE_URL + '">',

	picturePosition: 'center',
	wallpaper: null,
	imageHeight: null,
	imageWidth: null,
	backgroundColor: '#3d71b8',
	containerWidth: null,
	containerHeight: null,

	initComponent: function() {
		this.listeners = {
			boxready: function(cmp, width, height) {
				this.setSize(width, height);
			},
			resize: function(cmp, width, height) {
				this.setSize(width, height);
			},
			scope: this
		};

		this.callParent(arguments);
	},

	afterRender: function() {
		this.callParent();
		this.refreshWallpaper();
	},

	setSize: function(width, height) {
		if (width && height) {
			this.containerWidth = width;
			this.containerHeight = height;
			this.refreshWallpaper();
		}
	},
	
	refreshWallpaper: function() {
		this.setWallpaper(this.wallpaper, this.imageWidth, this.imageHeight, this.picturePosition,
						this.backgroundColor);
	},

	setWallpaper: function(wallpaper, width, height, pos, bkgcolor) {
		var imgEl, bkgnd;

		this.wallpaper = wallpaper;
		this.imageWidth = width;
		this.imageHeight = height;
		this.picturePosition = pos || 'center';
		this.backgroundColor = bkgcolor || '#3d71b8';

		if (this.rendered) {
			imgEl = this.el.dom.firstChild;

			if (!wallpaper || wallpaper === Ext.BLANK_IMAGE_URL) {
				Ext.fly(imgEl).hide();
				this.el.removeCls('wallpaper-center');
				this.el.removeCls('wallpaper-tile');
			} else if (this.picturePosition === 'center') {
				Ext.fly(imgEl).hide();
				bkgnd = 'url(' + wallpaper + ')';
				this.el.removeCls('wallpaper-tile');
				this.el.addCls('wallpaper-center');
			} else if (this.picturePosition === 'tile') {
				Ext.fly(imgEl).hide();
				bkgnd = 'url(' + wallpaper + ')';
				this.el.removeCls('wallpaper-center');
				this.el.addCls('wallpaper-tile');
			} else if (this.picturePosition === 'fit') {
				this.el.removeCls('wallpaper-center');
				this.el.removeCls('wallpaper-tile');
				imgEl.src = wallpaper;

				var hratio = this.containerHeight / this.imageHeight;
				var wratio = this.containerWidth / this.imageWidth;

				var ratio = Math.min(hratio, wratio);

				var w;
				var h;
				if (ratio < 1) {
					w = Math.ceil(this.imageWidth * ratio);
					h = Math.ceil(this.imageHeight * ratio);
				} else {
					w = this.imageWidth;
					h = this.imageHeight;
				}

				Ext.fly(imgEl).setStyle({
					width: w + 'px',
					height: h + 'px'
				}).show();
				
			} else if (this.picturePosition === 'stretch') {
				this.el.removeCls('wallpaper-center');
				this.el.removeCls('wallpaper-tile');

				imgEl.src = wallpaper;
				Ext.fly(imgEl).setStyle({
					width: '100%',
					height: '100%'
				}).show();
			}

			this.el.setStyle({
				backgroundImage: bkgnd || '',
				backgroundColor: this.backgroundColor
			});

		}
	}
});
