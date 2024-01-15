Ext.define('Dinmu.view.Main', {
	extend: 'Ext.Panel',
	
	xtype: 'main',
	requires: [ 'Ext.Panel', 'Ext.layout.Card', 'Ext.TitleBar', 'Ext.Toolbar', 'Dinmu.view.SettingsView' ],
	config: {
		layout: 'card',
		
		items: [ {
			xtype: 'titlebar',
			cls: 'title',
			docked: 'top',
			title: 'Do I need my Umbrella?',
			items: [ {
				cls: 'back',
				hidden: true,
				ui: 'back',
				action: 'back',
				align: 'left',
				text: 'Back'
			}, {
				iconCls: 'settings',
				action: 'settings',
				ui: 'plain',
				align: 'right'
			}]
		}, {
			xtype: 'settingsview'
		}, {
			itemId: 'mainview',
			cls: 'textview'
		}, {
			xtype: 'toolbar',
			cls: 'footer',
			ui: 'light',
			docked: 'bottom',
			html: '<span>Powered by &copy; Sencha Touch</span>'
		} ]
	}
});
