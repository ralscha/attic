Ext.define('Changelog.view.log.Customers', {
	extend: 'Ext.grid.Panel',

	itemId: 'logCustomers',
	
	title: i18n.customer_customers,
	minHeight: 250,
	
	initComponent: function() {
		var me = this;

		me.store = Ext.getStore('customersAllStore');
		
		me.columns = [ {
			dataIndex: 'shortName',
			flex: 1,
			text: i18n.customer_shortName,
			hideable: false
		} ];

		me.callParent(arguments);
	}
});