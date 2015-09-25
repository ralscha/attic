Ext.define('${jsAppNamespace}.store.LogLevels', {
	extend: 'Ext.data.ArrayStore',
	fields: [ 'level' ],
	data: [ [ 'ERROR' ], [ 'WARN' ], [ 'INFO' ], [ 'DEBUG' ] ]
});