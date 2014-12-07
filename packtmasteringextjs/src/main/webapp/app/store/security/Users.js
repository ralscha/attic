Ext.define('Packt.store.security.Users', {
	extend: 'Ext.data.Store',
	alias: 'store.users',
	requires: [ 'Packt.model.security.User' ],
	model: 'Packt.model.security.User'
});