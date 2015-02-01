Ext.define('E4ds.view.user.Container', {
	extend: 'Ext.container.Container',
	requires: [ 'E4ds.controller.User', 'E4ds.view.user.Grid', 'E4ds.view.user.Form' ],
	controller: 'E4ds.controller.User',

	title: i18n.user_users,
	closable: true,

	layout: {
		type: 'border'
	},

	defaults: {
		split: true
	},

	initComponent: function() {
		this.items = [ Ext.create('E4ds.view.user.Grid', {
			region: 'center'
		}) ];
		this.callParent(arguments);
	}

});
