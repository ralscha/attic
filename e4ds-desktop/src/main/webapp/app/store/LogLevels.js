Ext.define('E4desk.store.LogLevels', {
	extend: 'Ext.data.ArrayStore',
	fields: [ 'level' ],
	data: [ [ 'ERROR' ], [ 'WARN' ], [ 'INFO' ], [ 'DEBUG' ] ]
});
