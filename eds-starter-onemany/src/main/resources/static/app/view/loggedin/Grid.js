Ext.define('Starter.view.loggedin.Grid', {
	extend: 'Ext.grid.Panel',

	requires: [ 'Starter.view.loggedin.Controller', 'Starter.view.loggedin.ViewModel' ],

	title: i18n.loggedin_users,
	icon:'resources/images/users5.png',

	controller: {
		xclass: 'Starter.view.loggedin.Controller'
	},

	viewModel: {
		xclass: 'Starter.view.loggedin.ViewModel'
	},

	listeners: {
		close: 'onClose',
		deactivate: 'onDeactivate',
		activate: 'onActivate'
	},

	bind: '{loggedInUsers}',

	columns: {
		items: [ {
			text: i18n.accesslog_user,
			dataIndex: 'loginName',
			width: 200
		}, {
			text: i18n.accesslog_ip,
			dataIndex: 'ipAddress',
			flex: 1
		}, {
			text: i18n.accesslog_location,
			dataIndex: 'location',
			flex: 1
		}, {
			text: i18n.accesslog_userAgent,
			dataIndex: 'userAgentName',
			flex: 1
		}, {
			text: i18n.accesslog_userAgentVersion,
			dataIndex: 'userAgentVersion',
			width: 100
		}, {
			text: i18n.accesslog_operatingSystem,
			dataIndex: 'operatingSystem',
			width: 200
		}, {
			text: i18n.accesslog_login,
			dataIndex: 'loginTimestamp',
			width: 170,
			xtype: 'datecolumn',
			format: 'Y-m-d H:i:s'
		} ],
		defaults: {
			menuDisabled: true
		}

	}

});