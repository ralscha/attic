Ext.define('Sales.view.bill.Bill', {
	extend: 'Sales.panel.Screen',
	alias: 'widget.myapp-bill',
	requires: [ 'Sales.view.bill.List', 'Sales.view.bill.Edit' ],
	itemId: 'screen-bill',
	title: 'Bill',
	layout: 'card',
	items: [ {
		xtype: 'myapp-bill-list',
		border: false
	}, {
		xtype: 'myapp-bill-edit',
		border: false
	} ]
});
