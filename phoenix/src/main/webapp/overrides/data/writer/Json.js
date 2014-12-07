Ext.define('overrides.data.writer.Json', {
	override: 'Ext.data.writer.Json',

	getRecordData: function(record, operation) {
		var me = this, i, association, childStore, data = {};
		data = me.callParent([ record, operation ]);

		if (record.phantom && record.clientIdProperty) {
			data[record.clientIdProperty] = record.internalId;
		}
		
		/* Iterate over all the hasMany associations */
		for (i = 0; i < record.associations.length; i++) {
			association = record.associations.get(i);
			if (association.type === 'hasMany') {
				data[association.name] = [];
				childStore = record[association.name]();

				// Iterate over all the children in the current association
				childStore.each(function(childRecord) {
					// Recursively get the record data for children (depth first)
					var childData = this.getRecordData(childRecord, operation);
					data[association.name].push(childData);
				}, me);

			}
		}

		return data;
	}

});