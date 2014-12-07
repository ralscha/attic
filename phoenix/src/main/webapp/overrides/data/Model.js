Ext.define('overrides.data.Model', {
	override: 'Ext.data.Model',

	copyFrom: function(sourceRecord) {
		var result = this.callParent(arguments);

		var n = 0;
		var me = this;

		if (sourceRecord && sourceRecord.associations.length) {
			for ( var assocCount = sourceRecord.associations.length; n < assocCount; n++) {
				var association = sourceRecord.associations.getAt(n);
				if (association.type === 'belongsTo') {
					break;
				}

				var storeName = association.storeName;
				if (typeof (me[storeName]) === 'undefined') {
					me[storeName] = Ext.clone(sourceRecord[storeName]);
				}
				// We don't want to replace the store if it already exists as it could have events bound already
				else {
					// The result could be without the store entirely since the server deemed nothing changed.
					// If the server want's to tell us that all items in the association have been removed it must send an empty array in json
					// response.
					if (sourceRecord[storeName]) {
						var toAdd = [], toRemove = [];

						// Add new records, update existing records (again existing records may have event handlers bound so we can't just
						// replace them)
						sourceRecord[storeName].each(function(record) {
							var meRecord = me[storeName].getById(record.get('id'));
							if (meRecord) {
								meRecord.copyFrom(record);
								meRecord.commit(true);
							} else {
								toAdd.push(record);
							}
						}, this);
						me[storeName].add(toAdd);

						// Remove removed records
						me[storeName].each(function(record) {
							if (sourceRecord[storeName].getById(record.get('id')) === null) {
								toRemove.push(record);
							}
						}, this);
						me[storeName].remove(toRemove);
					}
				}
			}
		}

		return result;
	}
});
