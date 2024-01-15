Ext.define('Sales.view.quotation.Quotation', {
	extend: 'Sales.panel.Screen',
	alias: 'widget.myapp-quotation',
	requires: [ 'Sales.view.quotation.List', 'Sales.view.quotation.Edit', 'Sales.view.quotation.Import' ],
	itemId: 'screen-quotation',
	title: 'Quotation',
	layout: 'card',
	items: [ {
		xtype: 'myapp-quotation-list',
		border: false
	}, {
		xtype: 'myapp-quotation-edit',
		border: false
	}, {
		xtype: 'myapp-quotation-import',
		border: false
	} ]
});
