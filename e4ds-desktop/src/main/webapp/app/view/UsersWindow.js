Ext.define('E4desk.view.UsersWindow', {
	extend: 'Ext.window.Window',
	requires: [ 'E4desk.view.UsersGrid', 'E4desk.view.UserEdit' ],
	controller: 'E4desk.controller.User',
	stateId: 'E4desk.view.UsersWindow',
	title: i18n.user,
	width: 1070,
	height: 500,
	iconCls: 'users-icon',

	layout: {
		type: 'border'
	},

	defaults: {
		split: true
	},

	initComponent: function() {
		this.items = [ Ext.create('E4desk.view.UsersGrid', {
			itemId: 'grid',
			region: 'center',
			store: this.getController().store
		}), Ext.create('E4desk.view.UserEdit', {
			itemId: 'form',
			hidden: true,
			region: 'east'
		}) ];

		this.callParent(arguments);
	}

});
