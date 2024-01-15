Ext.define('Sales.view.Header', {
	extend: 'Sales.toolbar.Toolbar',
	alias: 'widget.myapp-header',
	height: 35,
	items: [ {
		text: 'MyApp',
		action: 'dashboard'
	}, '->', {
		text: 'MyAccount',
		action: 'myaccount'
	}, {
		text: 'Log Out',
		action: 'logout'
	} ]
});
