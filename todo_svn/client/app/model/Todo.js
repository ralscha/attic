
Ext.define("Todo.model.Todo", {
	extend: "Ext.data.Model",
	requires: [ "Ext.data.identifier.Negative", "Ext.data.validator.Presence" ],
	identifier: "negative",
	idProperty: 'id',
	fields: [ {
		name: "type",
		type: "string",
		validators: [ {
			type: "presence"
		} ]
	}, {
		name: "due",
		type: "date",
		dateFormat: 'time',
		validators: [ {
			type: "future"
		} ]
	}, {
		name: "title",
		type: "string",
		validators: [ {
			type: "presence"
		} ]
	}, {
		name: "description",
		type: "string",
		validators: [ {
			type: "presence"
		} ]
	} ],
	proxy: {
		type: 'rest',
		url: 'todo',
		writer: {
			writeAllFields: true
		},
		reader: {
			rootProperty: 'root',
			messageProperty: 'message'
		}
	}
});