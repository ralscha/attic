Ext.define('E4ds.model.User', {
	extend: 'Ext.data.Model',
	fields: [ {
		name: 'id',
		type: 'string'
	}, {
		name: 'locale',
		type: 'string'
	}, {
		name: 'userName',
		type: 'string'
	}, {
		name: 'name',
		type: 'string'
	}, {
		name: 'firstName',
		type: 'string'
	}, {
		name: 'email',
		type: 'string'
	}, {
		name: 'passwordHash',
		type: 'string'
	}, {
		name: 'enabled',
		type: 'bool'
	}, 'roles' ],

	proxy: {
		type: 'direct',
		api: {
			read: userService.load,
			destroy: userService.destroy
		},
		reader: {
			root: 'records'
		}
	}
});