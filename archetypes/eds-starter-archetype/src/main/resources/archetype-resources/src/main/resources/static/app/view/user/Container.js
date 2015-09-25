Ext.define('${jsAppNamespace}.view.user.Container', {
	extend: 'Ext.container.Container',
	requires: [ '${jsAppNamespace}.controller.User', '${jsAppNamespace}.view.user.Grid', '${jsAppNamespace}.view.user.Form' ],
	controller: '${jsAppNamespace}.controller.User',

	title: i18n.user_users,
	closable: true,

	layout: {
		type: 'border'
	},

	defaults: {
		split: true
	},

	initComponent: function() {
		this.items = [ Ext.create('${jsAppNamespace}.view.user.Grid', {
			region: 'center'
		}) ];
		this.callParent(arguments);
	}

});
