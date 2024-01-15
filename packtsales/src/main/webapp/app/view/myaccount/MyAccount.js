Ext.define('Sales.view.myaccount.MyAccount', {
	extend: 'Sales.panel.Screen',
	alias: 'widget.myapp-myaccount',
	requires: [ 'Sales.view.myaccount.Edit' ],
	itemId: 'screen-myaccount',
	title: 'MyAccount',
	layout: 'card',
	items: [ {
		xtype: 'myapp-myaccount-edit',
		border: false
	} ]
});
