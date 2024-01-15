Ext.define('Sales.store.Navigation', {
	extend: 'Ext.data.TreeStore',
	storeId: 'Navigation',
	root: {
		expanded: true,
		children: [ {
			text: 'Dashboard',
			hrefTarget: '#!/dashboard',
			leaf: true
		}, {
			text: 'Quotation',
			hrefTarget: '#!/quotation',
			leaf: true
		}, {
			text: 'Bill',
			hrefTarget: '#!/bill',
			leaf: true
		}, {
			text: 'MyAccount',
			hrefTarget: '#!/myaccount',
			leaf: true
		} ]
	}
});
