Ext.define('Proto.model.Role', {
	extend: 'Ext.data.Model',

	fields: [ {
		name: 'name',
		type: 'string'
	} ],
	proxy: {
		type: 'direct',
		directFn: 'userService.readRoles'
	}
});
