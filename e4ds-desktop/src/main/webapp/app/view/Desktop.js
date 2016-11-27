Ext.define('E4desk.view.Desktop', {
	extend: 'Ext.panel.Panel',
	controller: 'E4desk.controller.Desktop',
	requires: [ 'E4desk.view.WindowBar', 'E4desk.view.Wallpaper', 'E4desk.store.DesktopStore', 'E4desk.view.TopBar', 'E4desk.view.module.OnlineUsers',
			'E4desk.view.module.Notepad', 'E4desk.view.module.TabWindow', 'E4desk.view.module.GridWindow', 'E4desk.view.module.SystemStatus',
			'E4desk.view.UsersWindow', 'E4desk.view.LoggingEventsWindow', 'E4desk.view.AccessLogWindow', 'E4desk.view.ConfigurationWindow' ],

	border: false,
	html: '&#160;',
	layout: 'fit',

	contextMenu: Ext.create('Ext.menu.Menu', {
		items: [ {
			text: i18n.desktop_closeall,
			action: 'closeall',
			minWindows: 1
		}, '-', {
			text: i18n.desktop_minimizeall,
			action: 'minimizeall',
			minWindows: 1
		}, '-', {
			text: i18n.desktop_tile,
			action: 'tile',
			minWindows: 2
		}, {
			text: i18n.desktop_cascade,
			action: 'cascade',
			minWindows: 2
		}, {
			text: i18n.desktop_fithorizontal,
			action: 'fithorizontal',
			minWindows: 1
		}, {
			text: i18n.desktop_fitvertical,
			action: 'fitvertical',
			minWindows: 1
		} ]
	}),

	initComponent: function() {
		this.dockedItems = [ Ext.create('E4desk.view.WindowBar', {
			dock: 'bottom'
		}), Ext.create('E4desk.view.TopBar', {
			dock: 'top'
		}) ];

		this.wallpaper = Ext.create('E4desk.view.Wallpaper');

		this.items = [
				this.wallpaper,
				{
					xtype: 'dataview',
					itemId: 'shortcutView',
					overItemCls: 'view-over',
					itemSelector: 'div.desktop-shortcut',
					trackOver: true,
					store: Ext.create('E4desk.store.DesktopStore'),
					style: {
						position: 'absolute'
					},
					tpl: [ '<tpl for=".">', '<div class="desktop-shortcut" id="{name}-shortcut">', '<div class="desktop-shortcut-icon {iconCls}-shortcut">',
							'<img src="', Ext.BLANK_IMAGE_URL, '" title="{name}">', '</div>', '<span class="desktop-shortcut-text">{name}</span>', '</div>',
							'</tpl>', '<div class="x-clear"></div>' ]
				} ];

		this.callParent(arguments);
	}
});