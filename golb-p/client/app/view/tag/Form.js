Ext.define('Golb.view.tag.Form', {
	extend: 'Ext.form.Panel',
	defaultFocus: 'textfield[name=name]',

	reference: 'editPanel',

	cls: 'shadow',
	defaultType: 'textfield',
	bodyPadding: '0 20 0 20',

	defaults: {
		labelAlign: 'top',
		anchor: '100%'
	},

	items: [ {
		name: 'name',
		fieldLabel: 'Name',
		allowBlank: false
	} ],

	dockedItems: [ {
		xtype: 'toolbar',
		dock: 'top',
		items: [ {
			text: i18n.back,
			handler: 'back',
			iconCls: 'x-fa fa-arrow-left'
		}, {
			text: Golb.Util.underline(i18n.save, 'S'),
			accessKey: 's',
			ui: 'soft-green',
			iconCls: 'x-fa fa-floppy-o',
			formBind: true,
			handler: 'save'
		}, {
			xtype: 'tbseparator',
			hidden: true,
			bind: {
				hidden: '{isPhantomObject}'
			}
		}, {
			text: i18n.destroy,
			iconCls: 'x-fa fa-trash-o',
			handler: 'erase',
			ui: 'soft-red',
			hidden: true,
			bind: {
				hidden: '{isPhantomObject}'
			}
		} ]
	} ]

});