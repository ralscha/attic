Ext.define('E4desk.view.Viewport', {
	extend: 'Ext.Viewport',
	requires: ['E4desk.view.Desktop'],
	layout: 'fit',
	
	initComponent: function() {
		this.items = [ Ext.create('E4desk.view.Desktop') ];
		this.callParent(arguments);
	}

});