Ext.define('Golb.view.binary.Form', {
	extend: 'Ext.form.Panel',
	requires: [ 'Ext.form.field.File', 'Ext.form.field.Display' ],
	defaultFocus: 'textfield[name=name]',

	reference: 'editPanel',

	cls: 'shadow',
	defaultType: 'textfield',
	defaults: {
		anchor: '50%'
	},
	bodyPadding: 20,

	items: [ {
		xtype: 'filefield',
		anchor: '10%',
		buttonText: 'Select Document...',
		buttonOnly: true,
		listeners: {
			change: 'onFileFieldChange'
		}
	}, {
		xtype: 'displayfield',
		name: 'name',
		fieldLabel: 'Filename'
	}, {
		xtype: 'displayfield',
		name: 'size',
		fieldLabel: 'Size',
		renderer: function(value, field) {
			if (value) {
				return Golb.Util.filesize(value);
			}
			return value;
		}
	}, {
		xtype: 'displayfield',
		renderer: Ext.util.Format.dateRenderer('Y-m-d H:i:s'),
		name: 'lastModifiedDate',
		fieldLabel: 'Last Modified'
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
			handler: 'save',
			reference: 'saveButton'
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