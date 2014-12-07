Ext.define('Packt.view.Translation', {
	extend: 'Ext.button.Split',
	alias: 'widget.translation',

	menu: Ext.create('Ext.menu.Menu', {
		items: [ {
			xtype: 'menuitem',
			iconCls: 'en',
			text: 'English'
		}, {
			xtype: 'menuitem',
			iconCls: 'de',
			text: 'Deutsch'
		} ]
	})
});