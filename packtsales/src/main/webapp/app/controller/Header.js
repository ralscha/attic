Ext.define('Sales.controller.Header', {
	extend: 'Sales.controller.Abstract',
	init: function() {
		var me = this;
		me.control({
			'myapp-header [action=dashboard]': {
				click: function() {
					console.log('dashboard');
				}
			},
			'myapp-header [action=myaccount]': {
				click: function() {
					console.log('myaccount');
				}
			},
			'myapp-header [action=logout]': {
				click: function() {
					console.log('logout');
					TestAction.doEcho('message!', function(result) {
						console.log(result);
					});
				}
			}
		});
	}
});
