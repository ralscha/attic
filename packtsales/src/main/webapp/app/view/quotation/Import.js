Ext.define('Sales.view.quotation.Import', {
	extend: 'Sales.form.Panel',
	alias: 'widget.myapp-quotation-import',
	itemId: 'screen-quotation-import',
	api: {
		submit: 'MyAppQuotation.importData'
	},
	paramOrder: [ 'importfile' ],
	initComponent: function() {
		var me = this, store = Ext.data.StoreManager.lookup('QuotationImport'), itemstore = Ext.data.StoreManager.lookup('QuotationImportItems');

		// Items
		Ext.apply(me, {
			layout: {
				type: 'border',
				padding: 5
			},
			items: [ {
				region: 'north',
				xtype: 'grid',
				itemId: 'quotationgrid',
				split: true,
				title: 'Quotation',
				store: store,
				columns: [ {
					text: 'id',
					dataIndex: 'id',
					width: 50
				}, {
					text: 'status',
					dataIndex: 'status',
					width: 50
				}, {
					text: 'customer',
					dataIndex: 'customer'
				}, {
					text: 'note',
					dataIndex: 'note',
					flex: 1
				}, {
					text: 'modified',
					dataIndex: 'modified'
				}, {
					text: 'created',
					dataIndex: 'created'
				} ],
				flex: 1
			}, {
				region: 'center',
				xtype: 'grid',
				title: 'Quotation Items',
				itemId: 'quotationitemsgrid',
				store: itemstore,
				columns: [ {
					text: 'id',
					dataIndex: 'id',
					width: 50
				}, {
					text: 'status',
					dataIndex: 'status',
					width: 50
				}, {
					text: 'parent',
					dataIndex: 'parent',
					width: 50
				}, {
					text: 'description',
					dataIndex: 'description',
					flex: 1
				}, {
					text: 'qty',
					dataIndex: 'qty'
				}, {
					text: 'price',
					dataIndex: 'price'
				}, {
					text: 'sum',
					dataIndex: 'sum'
				}, {
					text: 'modified',
					dataIndex: 'modified'
				}, {
					text: 'created',
					dataIndex: 'created'
				} ],
				flex: 1
			} ]
		});

		// TopToolbar
		Ext.apply(me, {
			tbar: [ {
				text: 'Cancel',
				action: 'cancel'
			}, '-', {
				xtype: 'filefield',
				name: 'importfile',
				buttonText: 'Upload',
				buttonOnly: true,
				hideLabel: true,
				action: 'upload'
			}, {
				// Add button.
				text: 'Execute',
				disabled: true,
				action: 'execute'
			} ]
		});

		me.callParent(arguments);
	}
});
