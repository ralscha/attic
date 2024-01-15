Ext.define('Addressbook.view.ContactsList', {
	extend: 'Ext.grid.Panel',
	inject: [ 'contactGroupsStore' ],
	requires: [ 'Addressbook.controller.Contacts', 'Addressbook.store.Contacts' ],
	controller: 'Addressbook.controller.Contacts',

	title: 'Contacts',
	closable: true,
	border: true,

	requires: [ 'Addressbook.ux.form.field.FilterField' ],

	initComponent: function() {

		var me = this;

		me.store = Ext.create('Addressbook.store.Contacts');

		me.columns = [ {
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
				text: 'Add',
				itemId: 'createButton',
				icon: 'resources/images/add.png'
			}, {
				text: 'Edit',
				itemId: 'editButton',
				disabled: true,
				icon: 'resources/images/edit.png'
			}, {
				text: 'Delete',
				itemId: 'destroyButton',
				disabled: true,
				icon: 'resources/images/delete.png'
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
				fieldLabel: 'Filter',
				labelWidth: 40,
				xtype: 'filterfield'
			} ]
		}, {
			xtype: 'pagingtoolbar',
			dock: 'bottom',
			store: me.store,
			displayInfo: true
		} ];

		me.listeners = {
			'selectionchange': function(view, records) {
				me.down('#destroyButton').setDisabled(!records.length);
				me.down('#editButton').setDisabled(!records.length);
			}
		};

		me.callParent(arguments);

	}

});