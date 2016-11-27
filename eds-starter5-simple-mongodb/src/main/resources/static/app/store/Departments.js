Ext.define('SimpleApp.store.Departments', {
	extend: 'Ext.data.Store',
	requires: ['SimpleApp.model.Department'],
	model: 'SimpleApp.model.Department',
	autoLoad: true
});