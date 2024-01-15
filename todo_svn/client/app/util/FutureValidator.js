Ext.define('Todo.util.FutureValidator', {
	extend: 'Ext.data.validator.Validator',
	alias: 'data.validator.future',
	type: 'future',

	config: {
		message: 'Must be in the future'
	},

	validate: function(value) {
		if (Ext.isEmpty(value)) {
			return true;
		}
		var inputDate = Ext.Date.parse(value, 'Y-m-d');
		var now = new Date();
		if (inputDate < now) {
			return this.getMessage();
		}

		return true;
	}
});