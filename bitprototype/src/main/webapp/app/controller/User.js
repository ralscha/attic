Ext.define('BitP.controller.User', {
	extend: 'BitP.controller.CrudBase',
	requires: [ 'BitP.view.user.Edit' ],

	control: {
		exportButton: true
	},

	destroyConfirmMsg: function(record) {
		return record.get('username') + ' ' + i18n.reallyDestroy;
	},

	destroyFailureCallback: function() {
		BitP.ux.window.Notification.error(i18n.error, i18n.user_lastAdminUserError);
	},

	editWindowClass: 'BitP.view.user.Edit',

	createModel: function() {
		return Ext.create('BitP.model.User');
	},

	buildContextMenuItems: function(record) {
		var me = this;
		var items = this.callParent(arguments);

		items.push({
			xtype: 'menuseparator'
		});
		items.push({
			text: i18n.user_switchto,
			handler: Ext.bind(me.switchTo, me, [ record ])
		});

		return items;
	},

	onFilterField: function(field, newValue) {
		this.callParent(arguments);

		if (newValue) {
			this.getExportButton().setParams({
				filter: newValue
			});
		} else {
			this.getExportButton().setParams();
		}
	},

	switchTo: function(record) {
		if (record) {
			securityService.switchUser(record.data.id, function(ok) {
				if (ok) {
					History.pushState({}, i18n.app_title, "?");
					window.location.reload();
				}
			}, this);
		}
	}

});