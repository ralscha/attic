Ext.define('Addressbook.view.AppUsersList', {
	extend: 'Ext.grid.Panel',
	requires: [ 'Addressbook.controller.AppUsers', 'Addressbook.store.AppUsers' ],
	controller: 'Addressbook.controller.AppUsers',

	title: 'Users',
	closable: true,
	border: true,

	requires: [ 'Addressbook.ux.form.field.FilterField' ],

	initComponent: function() {

		var me = this;

		me.store = Ext.create('Addressbook.store.AppUsers');

		me.columns = [ {
			text: 'Username',
			dataIndex: 'userName',
			flex: 1
		}, {
			text: 'First Name',
			dataIndex: 'firstName',
			flex: 1
		}, {
			text: 'Last Name',
			dataIndex: 'name',
			flex: 1
		}, {
			text: 'Email',
			dataIndex: 'email',
			flex: 1
		}, {
			text: 'Enabled',
			dataIndex: 'enabled',
			width: 100,
			renderer: function(value) {
				if (value === true) {
					return 'Yes';
				}
				return 'No';
			}
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