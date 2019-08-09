Ext.define('Golb.view.auth.Signout', {
	extend: 'Golb.view.base.LockingWindow',

	title: '<i class="x-fa fa-rss"></i> ' + i18n.app_name + ': ' + i18n.auth_signout,

	cls: 'signout-page-container',
	items: [ {
		xtype: 'container',
		cls: 'error-page-inner-container',
		layout: {
			type: 'vbox',
			align: 'center',
			pack: 'center'
		},
		items: [ {
			xtype: 'label',
			cls: 'signout-page-top-text',
			text: i18n.auth_signout_success
		}, {
			xtype: 'label',
			cls: 'signout-page-desc',
			html: '<div><a href="#auth.signin">' + i18n.auth_backtosignin + '</a></div>'
		} ]
	} ]

});
