Ext.define('Ab.view.contact.List', {
	extend: 'Ext.grid.Panel',
	inject: [ 'contactGroupsStore' ],
	requires: ['Ab.controller.Contact', 'Ab.store.Contacts', 'Ab.ux.form.field.FilterField' ],
	controller: 'Ab.controller.Contact',
	
	title: 'Contacts',
	closable: true,
	border: true,

	initComponent: function() {

		var me = this;

		me.store = Ext.create('Ab.store.Contacts');

		me.columns = [ {
			xtype: 'actioncolumn',
			width: 30,
			items: [ {
				icon: 'data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAQAAAC1+jfqAAAAK0lEQVR4AWMgBBhXyRFQsPI/xQoyCCgg7EgX2jkSYQWZAOFN2jtSjsKQBAD0NQ+N4ZAsdgAAAABJRU5ErkJggg=='
			} ]
		}, {
			text: 'First Name',
			dataIndex: 'firstName',
			flex: 1
		}, {
			text: 'Last Name',
			dataIndex: 'lastName',
			flex: 1
		}, {
			text: 'Notes',
			dataIndex: 'notes',
			flex: 3
		} ];

		me.dockedItems = [ {
			xtype: 'toolbar',
			dock: 'top',
			items: [ {
				text: i18n.create,
				itemId: 'createButton',
				glyph: 0xe807
			}, '->', {
				itemId: 'contactGroupFilter',
				fieldLabel: 'Group',
				xtype: 'clearcombo',
				store: me.contactGroupsStore,
				displayField: 'name',
				valueField: 'id',
				labelWidth: 40,
				queryMode: 'local',
				forceSelection: true
			}, {
				itemId: 'filterField',
				fieldLabel: i18n.filter,
				labelWidth: 40,
				xtype: 'filterfield'
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store
		} ];

		me.callParent(arguments);
	}

});