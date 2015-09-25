Ext.define('${jsAppNamespace}.store.Departments', {
	extend: 'Ext.data.Store',
	requires: ['${jsAppNamespace}.model.Department'],
	model: '${jsAppNamespace}.model.Department',
	autoLoad: true
});