Ext.define('Ext.data.PortalStore', {
	extend: 'Ext.data.Store',

	autoSync: false,

	constructor: function() {
		this.callParent(arguments);
		var me = this;

		portal.find().on(me.model.proxy.api.created, function(data) {
			me.insert(0, data);
			me.sort();
		});

		portal.find().on(me.model.proxy.api.updated, function(data) {
			for ( var r = 0; r < data.length; r++) {
				var record = me.getById(data[r][me.model.prototype.idProperty]);
				if (record) {
					record.set(data[r]);
					record.commit();
				}
			}
		});

		portal.find().on(me.model.proxy.api.destroyed, function(data) {
			for ( var i = 0; i < data.length; i++) {
				var record = me.getById(data[i]);
				me.remove(record);
			}
		});

	}
});