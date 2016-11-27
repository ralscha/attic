Ext.define('Starter.RemoteStateProvider', {
	extend: 'Ext.state.Provider',
	requires: [ 'Starter.store.ClientState' ],
	store: null,

	constructor: function() {
		this.store = new Starter.store.ClientState();
		this.store.load();

		this.callParent(arguments);

		this.on({
			'statechange': {
				scope: this,
				buffer: 8000,
				fn: this.sync
			}
		});
	},

	get: function(name, defaultValue) {		
		var pos = this.store.findExact('name', name);
		if (pos > -1) {
			return this.decodeValue(this.store.getAt(pos).get('value'));
		}
		return defaultValue;
	},

	clear: function(name) {
		var pos = this.store.findExact('name', name);
		if (pos > -1) {
			this.store.removeAt(pos);
			this.fireEvent('statechange', this, name, null);
		}
	},

	set: function(name, value) {
		var pos = this.store.findExact('name', name);

		if (pos > -1) {
			this.store.getAt(pos).set('value', this.encodeValue(value));
		}
		else {
			this.store.add({
				name: name,
				value: this.encodeValue(value)
			});
		}

		this.fireEvent('statechange', this, name, value);
	},

	sync: function() {
		this.store.sync();
	}

});