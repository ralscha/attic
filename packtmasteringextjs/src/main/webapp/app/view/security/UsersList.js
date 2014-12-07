Ext.define('Packt.view.security.UsersList', {
	requires: ['Packt.store.security.Users'],
	extend: 'Ext.grid.Panel',
	alias: 'widget.userslist',

	frame: true,

	store: {
		type: 'users'
	},

	columns: [ {
		width: 150,
		dataIndex: 'userName',
		text: 'Username'
	}, {
		width: 200,
		dataIndex: 'name',
		flex: 1,
		text: 'Name'
	}, {
		width: 250,
		dataIndex: 'email',
		text: 'Email'
	}, {
		width: 150,
		dataIndex: 'appGroupId',
		text: 'Group',
		renderer: function(value, metaData, record) {
			var groupsStore = Ext.getStore('groups');
			var group = groupsStore.findRecord('id', value);
			return group !== null ? group.get('name') : value;
		}
	} ]
});