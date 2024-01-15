Ext.define('Addressbook.view.ContactInfosList', {
	extend: 'Ext.grid.Panel',
	inject: [ 'contactGroupsStore' ],
	requires: [ 'Addressbook.controller.ContactInfos', 'Addressbook.store.ContactInfos', 'Addressbook.store.Countries' ],
	controller: 'Addressbook.controller.ContactInfos',

	title: 'Contact Infos',
	closable: true,
	border: true,

	initComponent: function() {

		var me = this;

		me.store = Ext.create('Addressbook.store.ContactInfos');
		me.countryStore = Ext.create('Addressbook.store.Countries');
			
		me.columns = [ {
			text: 'Type',
			dataIndex: 'infoType',
			flex: 1
		}, {
			text: 'Phone',
			dataIndex: 'phone',
			flex: 1
		}, {
			text: 'Email',
			dataIndex: 'email',
			flex: 1
		}, {
			text: 'Address',
			dataIndex: 'address',
			flex: 2
		}, {
			text: 'City',
			dataIndex: 'city',
			flex: 1
		}, {
			text: 'State',
			dataIndex: 'state',
			flex: 0.5
		}, {
			text: 'Zip',
			dataIndex: 'zip',
			flex: 0.5
		}, {
			text: 'Country',
			dataIndex: 'country',
			flex: 1,
			defaultRenderer: function(value, metaData, record) {
				var r =  me.countryStore.findRecord('iso', value);
				if (r) {
					return r.get('name');
				}
				return value;
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
			} ]
		}];

		me.listeners = {
			'selectionchange': function(view, records) {
				me.down('#destroyButton').setDisabled(!records.length);
				me.down('#editButton').setDisabled(!records.length);
			}
		};

		me.callParent(arguments);

	}

});