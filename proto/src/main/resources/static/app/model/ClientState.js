Ext.define("Proto.model.ClientState", {
	extend: 'Ext.data.Model',
	requires : [ 'Ext.data.identifier.Negative' ],	             
	identifier: "negative",
	fields: [ {
		name: 'id',
		type: 'string'
	}, {
		name: 'name',
		type: 'string'
	}, {
		name: 'value',
		type: 'string'
	} ]
});