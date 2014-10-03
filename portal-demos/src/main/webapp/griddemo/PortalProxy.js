"use strict";

Ext.define('Ext.data.proxy.PortalProxy', {
	extend: 'Ext.data.proxy.Proxy',
	alias: 'proxy.portal',

	batchActions: false,

	create: function(operation, callback, scope) {
		var me = this, r;
		operation.setStarted();

		var records = operation.records;
		var datas = [];
		for (r = 0; r < records.length; r++) {
			datas.push(records[r].data);
		}

		portal.find().send(this.api.create, datas, function(res) {

			for (r = 0; r < res.length; r++) {
				records[r].phantom = false;
				records[r].setId(res[r].id);
				records[r].commit();
			}

			operation.setCompleted();
			operation.setSuccessful();

			if (typeof callback === 'function') {
				callback.call(scope || me, operation);
			}
		}, function(err) {
			console.log(err);
			operation.setException(err.message);
			me.fireEvent('exception', me, err, operation);

			if (typeof callback === 'function') {
				callback.call(scope || me, operation);
			}

		});

	},

	update: function(operation, callback, scope) {
		var me = this, r;
		operation.setStarted();

		var records = operation.records;
		var datas = [];
		for (r = 0; r < records.length; r++) {
			datas.push(records[r].data);
		}

		portal.find().send(this.api.update, datas, function(res) {

			for (r = 0; r < res.length; r++) {
				records[r].set(res[r]);
				records[r].commit();
			}

			operation.setCompleted();
			operation.setSuccessful();

			if (typeof callback === 'function') {
				callback.call(scope || me, operation);
			}
		}, function(err) {
			console.log(err);
			operation.setException(err.message);
			me.fireEvent('exception', me, err, operation);

			if (typeof callback === 'function') {
				callback.call(scope || me, operation);
			}
		});
	},

	destroy: function(operation, callback, scope) {
		var me = this, r;

		operation.setStarted();

		var records = operation.records;
		var ids = [];
		for (r = 0; r < records.length; r++) {
			ids.push(records[r].getId());
		}

		portal.find().send(this.api.destroy, ids, function(res) {
			operation.setCompleted();
			operation.setSuccessful();

			if (typeof callback === 'function') {
				callback.call(scope || me, operation);
			}
		}, function(err) {
			console.log(err);
			operation.setException(err.message);
			me.fireEvent('exception', me, err, operation);

			if (typeof callback === 'function') {
				callback.call(scope || me, operation);
			}
		});

	},

	read: function(operation, callback, scope) {

		var me = this;
		var i;

		var params = Ext.applyIf(operation.params || {}, me.extraParams || {});

		// paging parameters
		if (operation.start !== undefined) {
			params.start = operation.start;
		}
		if (operation.limit !== undefined) {
			params.limit = operation.limit;
		}

		// sorting parameters
		if (operation.sorters && operation.sorters.length > 0) {
			params.sorters = [];
			for (i = 0; i < operation.sorters.length; ++i) {
				params.sorters.push({
					property: operation.sorters[i].property,
					direction: operation.sorters[i].direction
				});
			}
		}

		// filtering parameters
		if (operation.filters && operation.filters.length > 0) {
			params.filters = [];
			for (i = 0; i < operation.filters.length; ++i) {
				params.filters.push({
					property: operation.filters[i].property,
					value: operation.filters[i].value
				});
			}
		}

		// grouping parameters
		if (operation.groupers && operation.groupers.length > 0) {
			params.groupers = [];
			for (i = 0; i < operation.groupers.length; ++i) {
				params.groupers.push({
					property: operation.groupers[i].property,
					direction: operation.groupers[i].direction || 'ASC'
				});
			}
		}
		
		operation.setStarted();

		portal.find().send(this.api.read, params, function(res) {
			var reader = me.getReader();
			reader.applyDefaults = true;
			operation.resultSet = reader.read(res);

			operation.setCompleted();
			operation.setSuccessful();

			if (typeof callback === 'function') {
				callback.call(scope || me, operation);
			}
		}, function(err) {
			operation.setException(err.message);
			me.fireEvent('exception', me, err, operation);

			if (typeof callback === 'function') {
				callback.call(scope || me, operation);
			}
			
		});
		
	}
});
