Ext.define('Packt.view.mail.MailContainer', {
	extend: 'Ext.container.Container',
	alias: 'widget.mailcontainer',
	requires: [ 'Packt.view.mail.MailList', 'Packt.view.mail.MailPreview', 'Packt.view.mail.MailMenu' ],
	layout: {
		type: 'border'
	},
	initComponent: function() {
		var me = this;
		var mailPreview = {
			xtype: 'mailpreview',
			autoScroll: true
		};
		me.items = [ {
			xtype: 'container',
			region: 'center',
			itemId: 'mailpanel',
			layout: {
				type: 'border'
			},
			items: [ {
				xtype: 'maillist',
				collapsible: false,
				region: 'center'
			}, {
				xtype: 'container',
				itemId: 'previewSouth',
				height: 300,
				hidden: false,
				collapsible: false,
				region: 'south',
				split: true,
				layout: 'fit',
				items: [ mailPreview ]
			}, {
				xtype: 'container',
				width: 400,
				itemId: 'previewEast',
				hidden: true,
				collapsible: false,
				region: 'east',
				split: true,
				layout: 'fit',
				items: [ mailPreview ]
			} ]
		}, {
			xtype: 'mailMenu',
			region: 'west'
		} ];
		me.callParent(arguments);
	}
});