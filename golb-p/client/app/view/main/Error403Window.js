Ext.define('Golb.view.main.Error403Window', {
	extend: 'Golb.view.base.LockingWindow',

	cls: 'error-page-container',
	title: '<i class="x-fa fa-rss"></i> ' + i18n.app_name + ': 403',

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
			cls: 'error-page-top-text',
			text: '403'
		}, {
			xtype: 'label',
			cls: 'error-page-desc',
			html: '<div>' + i18n.main_403_descr + '</div>'
		}, {
			xtype: 'label',
			cls: 'error-page-desc',
			html: '<div><a href="#auth.signin">' + i18n.main_backtohome + '</a></div>'
		} ]
	} ]
});
